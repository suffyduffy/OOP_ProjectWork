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
        aiControlManager.prepareAIEntities();
//        prepareEntities();

        AIControlManager aiControlManager = new AIControlManager();
        aiControlManager.prepareAIEntities();

        if (aiControlManager.controlledEntities.size() == 6) {
            System.out.println("All food entities are in the controlled list.");
        } else {
            System.out.println("Some entities are missing from the controlled list.");
        }
    }

    @Override
    public void render() {
        super.render();

        float deltaTime = Gdx.graphics.getDeltaTime();
        update(deltaTime);

        batch.begin();
        Scene currentScene = sceneManager.getCurrentScene();
        if (currentScene != null) {
            currentScene.render(batch);
        }
        aiControlManager.update(deltaTime);  // Now AIControlManager has its own update logic
        aiControlManager.render(batch);      // Delegate rendering of AI entities to AIControlManager
        batch.end();
    }


    private void update(float deltaTime) {
        Scene currentScene = sceneManager.getCurrentScene();

        // Check if the current scene is not StartScene before adding entities
        if (!(currentScene instanceof StartScene)) {
            aiControlManager.timeSinceLastEntity += deltaTime;

            // Check if it's time to add the next entity
            if (aiControlManager.timeSinceLastEntity >= aiControlManager.timeBetweenEntities && aiControlManager.nextEntityIndex < aiControlManager.controlledEntities.size()) {
                Entities entity = aiControlManager.controlledEntities.get(aiControlManager.nextEntityIndex++);
                entityManager.addEntity(entity);
                aiControlManager.timeSinceLastEntity = 0f;
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
        aiControlManager.update(deltaTime);
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
