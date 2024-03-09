package com.mygdx.game.game_engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.game_layer.PlayerControlManager;
import com.mygdx.game.game_layer.TexturedObject;
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
                // Switch to the GrassScene on any touch/click release
                sceneManager.setCurrentScene(new GrassScene(entityManager));
                return true;
            }
        });

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
        }
        batch.end();
    }

    private void update(float deltaTime) {
        Scene currentScene = sceneManager.getCurrentScene();

        // Check if the current scene is not StartScene before adding entities
        if (!(currentScene instanceof StartScene)) {
            timeSinceLastEntity += deltaTime;

            // Check if it's time to add the next entity
            if (timeSinceLastEntity >= timeBetweenEntities && nextEntityIndex < allEntities.size()) {
                Entities entity = allEntities.get(nextEntityIndex++);
                entityManager.addEntity(entity);
                timeSinceLastEntity = 0f;
            }
        }

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
            }
        }
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
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
