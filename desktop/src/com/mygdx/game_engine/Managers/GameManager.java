package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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


import static com.badlogic.gdx.math.MathUtils.random;

public class GameManager extends Game {

    private PlayerControlManager playerControlManager;
    private AIControlManager aiControlManager;
    EntityManager entityManager;
    private SceneManager sceneManager;
    private InputOutputManager inputOutputManager;
    private SpriteBatch batch;
    private Music music;
    private Timer gameTimer; // New timer field
    private int healthyFoodEaten = 0;

    Texture fitManTexture;
    Texture skinnyManTexture;
    Texture fatManTexture;

    Vector2 playerPosition;
    boolean isSkinny = false;
    boolean isFat = false;
    float playerScaleX = 1.0f; // Default scale factor for width
    float playerScaleY = 1.0f; // Default scale factor for height
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

        // Load additional images from EntityManager
        fitManTexture = entityManager.getFitManTexture();
        skinnyManTexture = entityManager.getSkinnyManTexture();
        fatManTexture = entityManager.getFatManTexture();
        playerPosition = new Vector2(
                (Gdx.graphics.getWidth() - fitManTexture.getWidth()) / 2,
                0
        );

        entityManager.initializeFoodItems(); // This line initializes the food items

        // Initialize the timer with 10 seconds
        gameTimer = new Timer(100); // Adjust the initial time as needed

        // Initialize font
        font = new BitmapFont();
        font.setColor(Color.BLACK);

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
                float scaleX = 0.55f; // Default scale factor for width
                float scaleY = 0.55f; // Default scale factor for height

                if (isSkinny) {
                    playerTexture = skinnyManTexture;
                    // Define scale factors for the skinny state, for example:
                    scaleX = 0.5f;
                    scaleY = 0.5f;
                } else if (isFat) {
                    playerTexture = fatManTexture;
                    // Define scale factors for the fat state, for example:
                    scaleX = 0.6f;
                    scaleY = 0.6f;
                }

                // Render the timer at the top right corner
                font.draw(batch, "Time: " + (int) gameTimer.getTimeRemaining(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);

                //Render the scoreboard
                font.draw(batch, "Healthy Foods: " + healthyFoodEaten, 20, Gdx.graphics.getHeight() - 50);

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

    private void update(float deltaTime) {
        Scene currentScene = sceneManager.getCurrentScene();

        // Update the scale factors based on the player's state
        if (isSkinny) {
            playerScaleX = 0.5f;
            playerScaleY = 0.5f;
        } else if (isFat) {
            playerScaleX = 0.6f; // Assuming you want the fat state to be slightly larger
            playerScaleY = 0.6f;
        } else {
            // Reset to default scale if neither skinny nor fat
            playerScaleX = 0.55f;
            playerScaleY = 0.55f;
        }

        // Check if the current scene is not StartScene before adding entities
        if ((currentScene instanceof GameScene)) {
            aiControlManager.updateEntityManagement(deltaTime);
            aiControlManager.updateEntities(deltaTime, playerPosition, playerScaleX, playerScaleY);
        }

        playerControlManager.handlePlayerMovement(deltaTime, this);

        // Player collision logic and other player-specific updates
        playerControlManager.checkCollisionsAndHandlePlayerState(entityManager, this);

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

    private void stopGame() {
        // Stop the timer
        gameTimer.stop();
        // Other stop game actions...
    }
    public void increaseHealthyFoodEaten() {
        this.healthyFoodEaten++;
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
        // No need to dispose textures here as they are managed by EntityManager
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

}
