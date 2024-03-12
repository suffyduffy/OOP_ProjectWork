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
import java.util.List;
import java.util.Random;

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
    private List<Entities> allEntities = new ArrayList<>();

    // Additional image at the bottom
    private Texture fitManTexture;
    private Texture skinnyManTexture;
    private Texture fatManTexture;
    private Vector2 playerPosition;
    private boolean isSkinny = false;
    private boolean isFat = false;

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
        prepareEntities();
    }

    private void prepareEntities() {
        // Create and add all entities to the allEntities list with their respective scale factors
        UnhealthyFoodItem burger = new UnhealthyFoodItem("Food/Burger.png");
        burger.setScaleFactor(0.25f);
        allEntities.add(burger);

        UnhealthyFoodItem frenchFries = new UnhealthyFoodItem("Food/FrenchFries.png");
        frenchFries.setScaleFactor(0.5f);
        allEntities.add(frenchFries);

        UnhealthyFoodItem pizza = new UnhealthyFoodItem("Food/Pizza.png");
        pizza.setScaleFactor(0.25f);
        allEntities.add(pizza);

        UnhealthyFoodItem hotDog = new UnhealthyFoodItem("Food/Hotdog.png");
        hotDog.setScaleFactor(0.5f);
        allEntities.add(hotDog);

        HealthyFoodItem cookedChicken = new HealthyFoodItem("Food/CookedChicken.png");
        cookedChicken.setScaleFactor(0.25f);
        allEntities.add(cookedChicken);

        HealthyFoodItem salad = new HealthyFoodItem("Food/Salad.png");
        salad.setScaleFactor(0.5f);
        allEntities.add(salad);

        // Add more entities as needed
    }

    @Override
    public void render() {
        super.render();

        update(Gdx.graphics.getDeltaTime());

        batch.begin();
        Scene currentScene = sceneManager.getCurrentScene();
        if (currentScene != null) {
            currentScene.render(batch);

            // Draw player
            Texture playerTexture = fitManTexture;
            if (isSkinny) {
                playerTexture = skinnyManTexture;
            } else if (isFat) {
                playerTexture = fatManTexture;
            }
            batch.draw(playerTexture, playerPosition.x, playerPosition.y);

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
            playerPosition.x = Math.min(Gdx.graphics.getWidth() - playerWidth, playerPosition.x);
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

        // Check if the current scene is not StartScene before adding entities
        if (!(currentScene instanceof MenuScene)) {
            timeSinceLastEntity += deltaTime;

            // Check if it's time to add the next entity
            if (timeSinceLastEntity >= timeBetweenEntities && nextEntityIndex < allEntities.size()) {
                Entities entity = allEntities.get(nextEntityIndex++);
                entityManager.addEntity(entity);
                timeSinceLastEntity = 0f;
            }
        }

        handlePlayerMovement(deltaTime);

        // Update positions of food items
        for (Entities entity : entityManager.getEntitiesList()) {
            for (TexturedObject texturedObject : entity.getTexturedObjects()) {
                if (texturedObject.getPosition().y >= Gdx.graphics.getHeight()) {
                    Random random = new Random();
                    float initialX = random.nextFloat() * (Gdx.graphics.getWidth() - texturedObject.getTexture().getWidth());
                    texturedObject.setPosition(initialX, Gdx.graphics.getHeight());
                }

                // Make the food fall down by decreasing the y position
                texturedObject.getPosition().y -= 60 * deltaTime;

                // Reset the position to the top once it reaches the bottom
                if (texturedObject.getPosition().y < 0) {
                    texturedObject.getPosition().y = Gdx.graphics.getHeight();
                }

                // Check for collision with player
                if (checkCollision(texturedObject)) {
                    if (entity instanceof HealthyFoodItem) {
                        isSkinny = true;
                        isFat = false;
                    } else if (entity instanceof UnhealthyFoodItem) {
                        isSkinny = false;
                        isFat = true;
                    }
                    entityManager.removeEntity(entity);
                }
            }
        }
    }

    private boolean checkCollision(TexturedObject foodItem) {
        float playerWidth = getPlayerWidth();
        float playerHeight = fitManTexture.getHeight(); // Assume all player textures have the same height

        // Calculate player bounding box
        float playerLeft = playerPosition.x;
        float playerRight = playerPosition.x + playerWidth;
        float playerTop = playerPosition.y + playerHeight;
        float playerBottom = playerPosition.y;

        // Calculate food item bounding box
        float foodLeft = foodItem.getPosition().x;
        float foodRight = foodItem.getPosition().x + foodItem.getTexture().getWidth();
        float foodTop = foodItem.getPosition().y + foodItem.getTexture().getHeight();
        float foodBottom = foodItem.getPosition().y;

        // Check for collision
        return playerRight >= foodLeft && playerLeft <= foodRight &&
                playerBottom <= foodTop && playerTop >= foodBottom;
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
