package com.mygdx.game_layer.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_engine.Managers.SceneManager;
import com.badlogic.gdx.Input.Keys;

public class GameScene extends Scene {
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private Music music;
    private boolean isPaused;

    public GameScene(EntityManager entityManager, SceneManager sceneManager) {
        super("Scenes/grassCute.jpg", entityManager, sceneManager);
        this.entityManager = entityManager;
        this.sceneManager = sceneManager;
        Gdx.app.log("GameScene", "Constructor called");

        /*if(music != null)
        {
            music.dispose();
            music = null;
        }
        else
        {
            // Load and play music
            music = Gdx.audio.newMusic(Gdx.files.internal("Music/Mamak.mp3"));
            music.setLooping(true);
            music.play();
        }*/

    }

    @Override
    public void update(float delta) {
        if (!isPaused) {
            entityManager.update(delta); // Update entities if not paused
        }
    }

    private void switchToPauseScene() {
        //isPaused = true;  // Set the game to paused

        if (isPaused = true)
        {
            entityManager.setRenderEntities(false); // Stop rendering entities
            PauseScene pauseScene = new PauseScene(entityManager, sceneManager);
            sceneManager.setCurrentScene(pauseScene);
            //music.dispose(); // Stop music when switching to pause scene
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);  // This draws the background texture
        if (!isPaused) {
            // Place rendering logic for entities here
            entityManager.renderEntitiesWithScaling(batch);
            entityManager.setRenderEntities(true);

        }

        // The conditional check for entering the pause scene
        if (Gdx.input.isKeyPressed(Keys.P) && !isPaused) {
            Gdx.app.log("GameScene", "Attempting to switch to PauseScene");
            isPaused = true;
            switchToPauseScene();
        }
    }


    public void resume() {
        isPaused = false;
        entityManager.setRenderEntities(true); // Start rendering entities again.
    }
    @Override
    public void dispose() {
        // Dispose of the background texture and other resources managed by the superclass
        super.dispose();
        // Dispose of the music
        if (music != null) {
            music.stop();
            music.dispose();
        }
    }
}
