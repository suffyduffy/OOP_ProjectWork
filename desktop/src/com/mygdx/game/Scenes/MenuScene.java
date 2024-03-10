package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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

    public MenuScene(EntityManager entityManager) {
        super("Scenes/mainMenu.jpg", entityManager);
        stage = new Stage();
        sceneManager = new SceneManager();
        Gdx.input.setInputProcessor(stage); // Set the stage as the input processor

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
                    // Replace "GrassScene" with the name of the scene class you want to switch to
                    sceneManager.setCurrentScene(new GrassScene(entityManager));
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
    }

    // Add other methods specific to the start scene if necessary
}
