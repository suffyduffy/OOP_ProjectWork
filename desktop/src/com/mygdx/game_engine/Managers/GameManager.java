package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game_layer.Objects.Entities;
import com.mygdx.game_layer.Objects.HealthyFoodItem;
import com.mygdx.game_layer.Objects.TexturedObject;
import com.mygdx.game_layer.Objects.UnhealthyFoodItem;
import com.mygdx.game_layer.Scenes.MenuScene;
import com.mygdx.game_layer.Scenes.Scene;
import com.mygdx.game_layer.Scenes.GameScene;

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

    // Timing and entities appearance control
    private float timeSinceLastEntity = 0f;
    private float timeBetweenEntities = 2f; // 1 second between entities
    private int nextEntityIndex = 0;

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


    @Override
    public void create() {
        batch = new SpriteBatch();
        inputOutputManager = new InputOutputManager();
        playerControlManager = new PlayerControlManager(inputOutputManager);
        aiControlManager = new AIControlManager();
        entityManager = new EntityManager();
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
            timeSinceLastEntity += deltaTime;

            // Check if it's time to add the next entity
            if (timeSinceLastEntity >= timeBetweenEntities && nextEntityIndex < aiControlManager.aiControlledEntities.size()) {
                Entities entity = aiControlManager.aiControlledEntities.get(nextEntityIndex++);
                entityManager.addEntity(entity);
                timeSinceLastEntity = 0f;
            }
        }

        handlePlayerMovement(deltaTime);

        Iterator<Entities> iterator = entityManager.getEntitiesList().iterator();
        while (iterator.hasNext()) {
            Entities entity = iterator.next();
            for (TexturedObject texturedObject : entity.getTexturedObjects()) {
                // Make the food fall down by decreasing the y position
                texturedObject.getPosition().y -= 60 * deltaTime;

                // Define scale factors for the food entities, these should be the same values used when drawing them
                float foodScaleX = entity.getScaleFactorForType(); // Assume getScaleFactor() returns the scale for this food entity
                float foodScaleY = entity.getScaleFactorForType(); // Assuming uniform scaling for simplicity


                // Check if food item is colliding with the player
                if (checkCollision(texturedObject, foodScaleX, foodScaleY)) {
                    if (entity instanceof HealthyFoodItem) {
                        isSkinny = true;
                        isFat = false;
                    } else if (entity instanceof UnhealthyFoodItem) {
                        isSkinny = false;
                        isFat = true;
                    }
                    // Instead of removing, reposition the entity to the top
                    float initialX = random.nextFloat() * (Gdx.graphics.getWidth() - texturedObject.getTexture().getWidth());
                    texturedObject.setPosition(initialX, Gdx.graphics.getHeight());
                }

                // Reset the position to the top once it reaches the bottom
                if (texturedObject.getPosition().y < 0) {
                    float initialX = random.nextFloat() * (Gdx.graphics.getWidth() - texturedObject.getTexture().getWidth());
                    texturedObject.setPosition(initialX, Gdx.graphics.getHeight());
                }
            }
        }
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
