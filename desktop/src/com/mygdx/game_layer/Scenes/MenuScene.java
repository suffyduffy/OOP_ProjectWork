package com.mygdx.game_layer.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_engine.Managers.SceneManager;

public class MenuScene extends Scene {
    private Stage stage;
    private Skin skin;
    private AssetManager assetManager;
    private SceneManager sceneManager;
    private Music music;
    public MenuScene(EntityManager entityManager, SceneManager sceneManager) {
        super("Scenes/mainMenu.jpg", entityManager, sceneManager);
        stage = new Stage();
        this.sceneManager = sceneManager;
        //sceneManager = new SceneManager();
        Gdx.input.setInputProcessor(stage); // Set the stage as the input processor
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Music/mainMenu.mp3"));
        music.setLooping(true);
        music.play();
        // Load skin
        skin = new Skin(Gdx.files.internal("skins/comic-ui.json"));

        // Create first button
        TextButton button1 = new TextButton("Start Game", skin);
        button1.setPosition(300, 350); // Set button position
        button1.setSize(200, 50); // Set button size

        // Add click listener to button 1
        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle button 1 click event
                Gdx.app.log("Start Game", "Clicked");
                if (sceneManager != null) {
                    GameScene gameScene = new GameScene(entityManager, sceneManager);
                    sceneManager.setCurrentScene(gameScene);
                    music.dispose();
                }
            }
        });

        // Create second button
        TextButton button2 = new TextButton("Exit", skin);
        button2.setPosition(300, 250); // Set button position
        button2.setSize(200, 50); // Set button size

        // Add click listener to button 2
        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle button 2 click event
                Gdx.app.log("Exit Game", "Clicked");
                Gdx.app.exit();
            }
        });

        // Add buttons to the stage
        stage.addActor(button1);
        stage.addActor(button2);
    }

    @Override
    public void update(float delta) {
        stage.act(delta); // Update the stage
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch); // Render the background image
        batch.end(); // End the sprite batch before drawing the stage

        // Draw the buttons on top
        stage.getViewport().apply(); // Apply the stage's viewport
        stage.draw();

        batch.begin(); // Begin the sprite batch again after drawing the stage
    }

    @Override
    public void dispose() {
        super.dispose(); // Dispose of the background texture
        stage.dispose();
        skin.dispose();
        if (music != null) {
            music.stop();
            music.dispose();
        }

    }

    // Add other methods specific to the start scene if necessary
}