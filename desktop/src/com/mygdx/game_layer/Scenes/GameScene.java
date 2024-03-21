package com.mygdx.game_layer.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_engine.Managers.SceneManager;
import com.mygdx.game_layer.Objects.Timer;

public class GameScene extends Scene {
    private Stage stage;
    private Timer gameTimer;
    private Skin skin;
    private boolean paused;
    private SceneManager sceneManager;
    private EntityManager entityManager;
    private Music music;
    private BitmapFont font; // Font for text rendering

    public GameScene(EntityManager entityManager, SceneManager sceneManager) {
        super("Scenes/grassCute.jpg", entityManager);
        Gdx.app.log("GameScene", "Constructor called");
        this.sceneManager = sceneManager;
        this.entityManager = entityManager;
        skin = new Skin(Gdx.files.internal("skins/comic-ui.json"));
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        // Load and play music
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/Hey Kids Remake.WAV"));
        music.setLooping(true);
        music.play();

        gameTimer = new Timer(10); // Adjust the initial time as needed
        gameTimer.start();

        // Initialize font
        font = new BitmapFont();
        font.setColor(Color.BLACK);

    }

    private void createPauseMenu()
    {

        // Create buttons for resume, restart, options, and quit
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton restartButton = new TextButton("Restart", skin);
        TextButton quitButton = new TextButton("Quit", skin);

        resumeButton.setPosition(300, 450); // Set button position
        resumeButton.setSize(200, 50); // Set button size

        restartButton.setPosition(300, 350); // Set button position
        restartButton.setSize(200, 50); // Set button size

        quitButton.setPosition(300, 250); // Set button position
        quitButton.setSize(200, 50); // Set button size

        // Add click listeners to the buttons
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Button Pressed", "Clicked");
                resumeGame();
            }
        });
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Button Pressed", "Clicked");
                Gdx.app.log("MenuScene", "Constructor called");
                if (sceneManager != null) {
                    MenuScene menuScene = new MenuScene(entityManager, sceneManager);
                    sceneManager.setCurrentScene(menuScene);
                    dispose();

                }
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Button Pressed", "Clicked");
                Gdx.app.exit();
            }
        });

        // Add buttons to the stage
        stage.addActor(resumeButton);
        stage.addActor(restartButton);
        stage.addActor(quitButton);
    }

    @Override
    public void update(float delta) {
        if (!paused) {
            gameTimer.update(delta); // Update the timer if not paused
        }
        if (gameTimer.getTimeRemaining() <= 0) {
            pauseGame(); // Pause the game when time is up
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.input.setInputProcessor(stage);
        update(Gdx.graphics.getDeltaTime());
        if (!paused) {
            super.render(batch);
            // Render the timer at the top right corner
            font.draw(batch, "Time: " + (int) gameTimer.getTimeRemaining(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);

            // Check if time is up
            if (gameTimer.getTimeRemaining() <= 0) {
                // Render "You Lose" text
                font.draw(batch, "You Lose", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2);
                gameTimer.stop();

            }
        } else {
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            if (paused) {
                resumeGame();
            } else {
                pauseGame();
            }
        }
    }
    private void pauseGame() {
        paused = true;
        createPauseMenu();
        gameTimer.stop();
        Gdx.input.setInputProcessor(stage);
    }
    private void resumeGame() {
        paused = false;
        stage.clear();
        gameTimer.start();
        Gdx.input.setInputProcessor(null);
    }
    public void dispose() {
        // Dispose of resources when the scene is disposed
        skin.dispose();
        stage.dispose();
        font.dispose();
        //entityManager.dispose();

        // Dispose of the music
        if (music != null) {
            music.stop();
            music.dispose();
        }

        // Dispose of the background texture and other resources managed by the superclass
        super.dispose();
    }

}