package com.mygdx.game_layer.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_engine.Managers.SceneManager;

public class GameOverScene extends Scene{
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private Stage stage;
    private Skin skin;

    private BitmapFont font;
    public GameOverScene(EntityManager entityManager, SceneManager sceneManager) {
        super("Scenes/gameOver.jpg", entityManager, sceneManager);

        this.sceneManager = sceneManager;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.skin = new Skin(Gdx.files.internal("skins/comic-ui.json"));

        font = new BitmapFont();
        font.setColor(Color.BLACK);

        TextButton menu = new TextButton("Restart", skin);
        menu.setPosition(125, 350); // Set button position
        menu.setSize(200, 50); // Set button size
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle button 1 click event
                Gdx.app.log("Menu Scene", "Clicked");
                if (sceneManager != null) {
                    InstructionScene1 instructionScene1 = new InstructionScene1(entityManager, sceneManager);
                    sceneManager.setCurrentScene(instructionScene1);

                }
            }
        });

        TextButton quit = new TextButton("Quit", skin);
        quit.setPosition(125, 200); // Set button position
        quit.setSize(200, 50); // Set button size
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle button 1 click event
                Gdx.app.log("GameOver", "Clicked");
                Gdx.app.exit();
            }
        });
        stage.addActor(menu);
        stage.addActor(quit);

    }

    @Override
    public void update(float delta) {
        stage.act(delta);

    }
    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.end(); // End the sprite batch before drawing the stage

        // Draw the buttons on top
        stage.getViewport().apply(); // Apply the stage's viewport
        stage.draw();

        batch.begin();
//        font.draw(batch, "Highest Score: " + score.getHighScore(), 125, 450);
//        batch.end();
    }
    public void dispose() {
        // Dispose of the background texture and other resources managed by the superclass
        super.dispose();
        stage.dispose();
        skin.dispose();
        font.dispose();

    }

}

