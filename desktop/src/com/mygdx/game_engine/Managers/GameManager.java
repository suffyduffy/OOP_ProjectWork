package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game_layer.Objects.*;
import com.mygdx.game_layer.Scenes.MenuScene;
import com.mygdx.game_layer.Scenes.Scene;
import com.mygdx.game_layer.Scenes.GameScene;
import com.badlogic.gdx.graphics.Color;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameManager extends Game {

    private PlayerControlManager playerControlManager;
    private AIControlManager aiControlManager;
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private InputOutputManager inputOutputManager;
    private SpriteBatch batch;
    private Music music;
    private Timer gameTimer; // New timer field


    // Additional image at the bottom
    private Texture fitManTexture;
    private Texture skinnyManTexture;
    private Texture fatManTexture;
    private Vector2 playerPosition;
    private boolean isSkinny = false;
    private boolean isFat = false;
    // Assume these are class-level variables
    private float playerScaleX = 1.0f; // Default scale factor for width
    private float playerScaleY = 1.0f; // Default scale factor for height
    private BitmapFont font; // Font for text rendering


    @Override
    public void create() {
        batch = new SpriteBatch();
        inputOutputManager = new InputOutputManager();
        playerControlManager = new PlayerControlManager(inputOutputManager);
        entityManager = new EntityManager();
        aiControlManager = new AIControlManager(entityManager);
        sceneManager = new SceneManager();

        // Initialize the start scene and add it to the scene manager
        MenuScene menuScene = new MenuScene(entityManager, sceneManager);
        sceneManager.addScene(menuScene);
        sceneManager.setCurrentScene(menuScene);

        // Load additional images
        fitManTexture = new Texture("Sprites/fitman.png");
        skinnyManTexture = new Texture("Sprites/skinnyman.png");
        fatManTexture = new Texture("Sprites/fatman.png");
        playerPosition = new Vector2(
                (Gdx.graphics.getWidth() - fitManTexture.getWidth()) / 2,
                0
        );

        // Initialize the timer with 10 seconds
        gameTimer = new Timer(100); // Adjust the initial time as needed

        // Initialize font
        font = new BitmapFont();
        font.setColor(Color.BLACK);

        // Prepare all entities
        aiControlManager.prepareEntities();
    }

    @Override
    public void render() {
        super.render();

        update(Gdx.graphics.getDeltaTime());

        batch.begin();
        Scene currentScene = sceneManager.getCurrentScene();
        if (currentScene != null) {
            currentScene.render(batch);

            // Draw player only if in the game scene
            if (currentScene instanceof GameScene) {
                Texture playerTexture = fitManTexture;
                float scaleX = 0.5f; // Default scale factor for width
                float scaleY = 0.5f; // Default scale factor for height

                if (isSkinny) {
                    playerTexture = skinnyManTexture;
                    // Define scale factors for the skinny state, for example:
                    scaleX = 0.5f;
                    scaleY = 0.5f;
                } else if (isFat) {
                    playerTexture = fatManTexture;
                    // Define scale factors for the fat state, for example:
                    scaleX = 0.5f;
                    scaleY = 0.5f;
                }

                // Render the timer at the top right corner
                font.draw(batch, "Time: " + (int) gameTimer.getTimeRemaining(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);

                // Check if time is up
                if (gameTimer.getTimeRemaining() <= 0) {
                    // Render "You Lose" text
                    font.draw(batch, "You Lose", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2);
                }

                // Apply scale factors when drawing the texture
                batch.draw(playerTexture,
                        playerPosition.x,
                        playerPosition.y,
                        playerTexture.getWidth() * scaleX,
                        playerTexture.getHeight() * scaleY);
            }
        }
        batch.end();
    }



    private void handlePlayerMovement(float deltaTime) {
        float playerSpeed = isSkinny ? 300f : (isFat ? 100f : 200f); // Adjust speed based on player state

        // Move player left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerPosition.x -= playerSpeed * deltaTime;
            // Ensure player does not go beyond left screen border
            playerPosition.x = Math.max(0, playerPosition.x);
        }
        // Move player right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            float playerWidth = getPlayerWidth();
            playerPosition.x += playerSpeed * deltaTime;
            // Ensure player does not go beyond right screen border
            playerPosition.x = Math.min(700, playerPosition.x);
        }
    }

    private float getPlayerWidth() {
        Texture playerTexture = fitManTexture;
        if (isSkinny) {
            playerTexture = skinnyManTexture;
        } else if (isFat) {
            playerTexture = fatManTexture;
        }
        return playerTexture.getWidth();
    }

    private void update(float deltaTime) {
        Scene currentScene = sceneManager.getCurrentScene();

//        System.out.println("DeltaTime: " + deltaTime); // Check that deltaTime is correct
//        System.out.println("Timer Running: " + gameTimer.isRunning()); // Check if the timer thinks it's running
//        System.out.println("Time Remaining: " + gameTimer.getTimeRemaining()); // Check the time remaining is decreasing

        // Update the scale factors based on the player's state
        if (isSkinny) {
            playerScaleX = 0.5f;
            playerScaleY = 0.5f;
        } else if (isFat) {
            playerScaleX = 0.6f; // Assuming you want the fat state to be slightly larger
            playerScaleY = 0.6f;
        } else {
            // Reset to default scale if neither skinny nor fat
            playerScaleX = 0.5f;
            playerScaleY = 0.5f;
        }

        // Check if the current scene is not StartScene before adding entities
        if (!(currentScene instanceof MenuScene)) {
            aiControlManager.updateEntityManagement(deltaTime);
            aiControlManager.updateEntities(deltaTime, playerPosition, playerScaleX, playerScaleY);

        }

        handlePlayerMovement(deltaTime);

        // Player collision logic and other player-specific updates
        checkCollisionsAndHandlePlayerState();

        // When the game scene starts, start the timer once
        if (currentScene instanceof GameScene && !gameTimer.isRunning()) {
            gameTimer.start();
        }

        // Timer logic remains here...
        gameTimer.update(deltaTime);
        if (gameTimer.getTimeRemaining() <= 0) {
            stopGame();
        }
    }
    // Method to check collisions and handle player state
    private void checkCollisionsAndHandlePlayerState() {
        for (Entities entity : entityManager.getEntitiesList()) {
            for (TexturedObject texturedObject : entity.getTexturedObjects()) {
                float foodScaleX = entity.getScaleFactorForType();
                float foodScaleY = entity.getScaleFactorForType();

                if (checkCollision(texturedObject, foodScaleX, foodScaleY)) {
                    if (entity instanceof HealthyFoodItem) {
                        isSkinny = true;
                        isFat = false;
                    } else if (entity instanceof UnhealthyFoodItem) {
                        isSkinny = false;
                        isFat = true;
                    }
                    // Reposition the entity to the top
                    float initialX = random.nextFloat() * (Gdx.graphics.getWidth() - texturedObject.getTexture().getWidth());
                    texturedObject.setPosition(initialX, Gdx.graphics.getHeight());
                }
            }
        }
    }
    private void stopGame() {
        // Stop the timer
        gameTimer.stop();

        // Other stop game actions...
    }


    private boolean checkCollision(TexturedObject foodItem, float foodScaleX, float foodScaleY) {
        // Get the scaled dimensions of the player
        float playerWidth = getPlayerWidth() * playerScaleX; // playerScaleX is the scale factor for the player's width
        float playerHeight = fitManTexture.getHeight() * playerScaleY; // playerScaleY is the scale factor for the player's height

        // Calculate player bounding box based on scaled dimensions
        float playerLeft = playerPosition.x;
        float playerRight = playerPosition.x + playerWidth;
        float playerTop = playerPosition.y + playerHeight;
        float playerBottom = playerPosition.y;

        // Get the scaled dimensions of the food item
        float foodWidth = foodItem.getTexture().getWidth() * foodScaleX; // foodScaleX is the scale factor for the food's width
        float foodHeight = foodItem.getTexture().getHeight() * foodScaleY; // foodScaleY is the scale factor for the food's height

        // Calculate food item bounding box based on scaled dimensions
        float foodLeft = foodItem.getPosition().x;
        float foodRight = foodItem.getPosition().x + foodWidth;
        float foodTop = foodItem.getPosition().y + foodHeight;
        float foodBottom = foodItem.getPosition().y;

        // Check for collision
        return playerRight >= foodLeft && playerLeft <= foodRight &&
                playerTop >= foodBottom && playerBottom <= foodTop;
    }

    @Override
    public void dispose() {
        if (sceneManager.getCurrentScene() != null) {
            sceneManager.getCurrentScene().dispose();
        }
        if (entityManager != null) {
            entityManager.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
        if (music != null) {
            music.dispose();
        }
        // Dispose additional image textures
        fitManTexture.dispose();
        skinnyManTexture.dispose();
        fatManTexture.dispose();
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
