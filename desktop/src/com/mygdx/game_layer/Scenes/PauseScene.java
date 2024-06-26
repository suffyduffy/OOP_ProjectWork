package com.mygdx.game_layer.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_engine.Managers.SceneManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class PauseScene extends Scene{
    private SceneManager sceneManager;
    private Stage stage;
    private Skin skin;

    public PauseScene(EntityManager entityManager, SceneManager sceneManager)
    {
        super("Scenes/pausebg.jpg", entityManager, sceneManager);
        stage = new Stage();
        this.sceneManager = sceneManager;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);


        // Load button skin
        this.skin = new Skin(Gdx.files.internal("skins/comic-ui.json"));

        //create resume button
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.setPosition(300, 300); // Set button position
        resumeButton.setSize(200, 50); // Set button size
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Game Scene", "Clicked and resume"); //Checking msg
                entityManager.setRenderEntities(true); // Resume rendering entities

                // Get the current game scene from your SceneManager and set it as the current scene
                GameScene gameScene = sceneManager.getCurrentGameScene();
                if (gameScene != null) {
                    gameScene.resume(); // Make sure to call the resume method if needed
                    sceneManager.setCurrentScene(gameScene);
                } else {
                    // Handle the situation where the gameScene is null
                    Gdx.app.log("PauseScene", "No current game scene to resume.");
                }
            }
        });

        //create quit button
        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.setPosition(300, 200); // Set button position
        quitButton.setSize(200, 50); // Set button size
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //testing the button
                Gdx.app.log("quit", "Quit button clicked");
                Gdx.app.exit();
            }
        });
        // Add buttons to the stage
        stage.addActor(resumeButton);
        stage.addActor(quitButton);

    }

    public void update(float delta) {
        stage.act(delta); // Update the stage
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.end(); // End the sprite batch before drawing the stage

        // Draw the buttons on top
        stage.getViewport().apply(); // Apply the stage's viewport
        stage.draw();

        batch.begin();
    }

    @Override
    public void dispose() {
        // Dispose of the background texture and other resources managed by the superclass
        super.dispose();
        stage.dispose();
        skin.dispose();

    }
}
