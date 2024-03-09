package com.mygdx.game.game_engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.game_layer.PlayerControlManager;

public class GameManager extends Game {

    private PlayerControlManager playerControlManager;
    private AIControlManager aiControlManager;
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private InputOutputManager inputOutputManager;
    private SpriteBatch batch;
    private Music music;

    @Override
    public void create() {
        batch = new SpriteBatch();
        inputOutputManager = new InputOutputManager();
        playerControlManager = new PlayerControlManager(inputOutputManager);
        aiControlManager = new AIControlManager();
        entityManager = new EntityManager();
        sceneManager = new SceneManager();

        // Initialize the start scene and add it to the scene manager
        StartScene startScene = new StartScene(entityManager);
        sceneManager.addScene(startScene);
        sceneManager.setCurrentScene(startScene);

        // Load and play music
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/Hey Kids Remake.WAV"));
        music.setLooping(true);
        music.play();

        // Set up input processor to listen for touch input
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                // Switch to the MountainScene on any touch/click release
                sceneManager.setCurrentScene(new GrassScene(entityManager));
                return true; // Return true to indicate the event was handled
            }
        });

        // Create food entities with the path to your images
        UnhealthyFoodItem burger = new UnhealthyFoodItem("Food/Burger.png");
        UnhealthyFoodItem frenchFries = new UnhealthyFoodItem("Food/FrenchFries.png");
        UnhealthyFoodItem pizza = new UnhealthyFoodItem("Food/Pizza.png");
        UnhealthyFoodItem hotDog = new UnhealthyFoodItem("Food/Hotdog.png");
        HealthyFoodItem cookedChicken = new HealthyFoodItem("Food/CookedChicken.png");
        HealthyFoodItem salad = new HealthyFoodItem("Food/Salad.png");

        // ... and so on for the rest of your food items

        // Add them to the EntityManager
        entityManager.addEntity(burger);
        entityManager.addEntity(frenchFries);
        entityManager.addEntity(pizza);
        entityManager.addEntity(hotDog);
        entityManager.addEntity(cookedChicken);
        entityManager.addEntity(salad);
        // ... and so on for the rest of your food items

        // ... scaling down food sizes...
        cookedChicken.setScaleFactor(0.25f); // scale down to half size
        salad.setScaleFactor(0.5f);
        frenchFries.setScaleFactor(0.5f);
        pizza.setScaleFactor(0.25f);
        burger.setScaleFactor(0.25f);
        hotDog.setScaleFactor(0.5f);
    }

    @Override
    public void render() {
        super.render();

        batch.begin();

        Scene currentScene = sceneManager.getCurrentScene();
        if (currentScene != null) {
            currentScene.render(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        // Dispose of the current scene
        if (sceneManager.getCurrentScene() != null) {
            sceneManager.getCurrentScene().dispose();
        }

        // Dispose of the entities
        if (entityManager != null) {
            entityManager.dispose();
        }

        // Dispose of batch and music
        if (batch != null) {
            batch.dispose();
        }
        if (music != null) {
            music.dispose();
        }
    }

    // Getter for SceneManager for potential external use
    public SceneManager getSceneManager() {
        return sceneManager;
    }
}