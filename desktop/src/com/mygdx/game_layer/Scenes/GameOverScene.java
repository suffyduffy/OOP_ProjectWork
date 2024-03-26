package com.mygdx.game_layer.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_engine.Managers.SceneManager;
import com.mygdx.game_layer.Utils.Score;


public class GameOverScene extends Scene{
    private SceneManager sceneManager;
    private Stage stage;
    private Skin skin;
    private BitmapFont font;
    private Music music;
    private Score score;
    public GameOverScene(EntityManager entityManager, SceneManager sceneManager, Score score ) {
        super("Scenes/gameOver.jpg", entityManager, sceneManager);
        this.score = score;
        this.sceneManager = sceneManager;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.music = Gdx.audio.newMusic(Gdx.files.internal("Music/gameOver.mp3"));
        this.music.setLooping(false);
        this.music.play();

        this.skin = new Skin(Gdx.files.internal("skins/comic-ui.json"));

        TextButton menu = new TextButton("Restart", skin);
        menu.setPosition(125, 350); // Set button position
        menu.setSize(200, 50); // Set button size
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle button 1 click event
                Gdx.app.log("Menu Scene", "Clicked");
                InstructionScene1 instructionScene1 = new InstructionScene1(entityManager, sceneManager);
                sceneManager.setCurrentScene(instructionScene1);

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
        // Draw the score
        batch.begin(); // Begin batch to draw text
        if (font == null) {
            font = new BitmapFont(); // Or use your custom font as needed
            font.setColor(Color.WHITE); // Set the font color
        }
        font.draw(batch, "Final Score: " + score.getHealthyFoodEaten(), 100, 200); // Position as needed

        batch.end(); // End the sprite batch after drawing text
        stage.getViewport().apply(); // Apply the stage's viewport
        stage.draw();

        batch.begin();
    }
    public void dispose() {
        // Dispose of the background texture and other resources managed by the superclass
        super.dispose();
        stage.dispose();
        skin.dispose();
        font.dispose();

    }

}

