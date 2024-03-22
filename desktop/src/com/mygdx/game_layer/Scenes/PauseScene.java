package com.mygdx.game_layer.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_engine.Managers.SceneManager;
import com.mygdx.game_engine.Managers.GameManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game_layer.Objects.HealthyFoodItem;


class PauseScene extends Scene{
    private SceneManager sceneManager;
    private Stage stage;
    private Skin skin;
    private Music music;
    public PauseScene(EntityManager entityManager, SceneManager sceneManager)
    {
        super("Scenes/mainMenu.jpg", entityManager, sceneManager);
        stage = new Stage();
        this.sceneManager = sceneManager;
        // Load and play music
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Music/mainMenu.mp3"));
        music.setLooping(true);
        music.play();




        //Load button skin
        skin = new Skin(Gdx.files.internal("skins/comic-ui.json"));

        //Create buttons for pause scene

        //create resume button
//        TextButton resumeButton = new TextButton("Resume", skin);
//        resumeButton.setPosition(300, 450);
//        resumeButton.setSize(200, 50);
//        resumeButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.log("Start Game", "Clicked"); //Checking msg
//                if (sceneManager != null) {
//                    GameScene gameScene = new GameScene(entityManager, sceneManager);
//                    sceneManager.setCurrentScene(gameScene);
//                    //music.dispose();
//                }
//            }
//        });

//create resume button
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.setPosition(300, 350); // Set button position
        resumeButton.setSize(200, 50); // Set button size
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Pause Scene", "Clicked"); //Checking msg
                entityManager.setRenderEntities(true); // Resume rendering entities
            }
        });


//        //create instruction button
//        TextButton instructionButton = new TextButton("Instruction", skin);
//        instructionButton.setPosition(300, 250); // Set button position
//        instructionButton.setSize(200, 50); // Set button size
//        instructionButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                //testing the button
//                Gdx.app.log("quit", "Clicked");
//                Gdx.app.exit();
//            }
//        });

        //create quit button
        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.setPosition(300, 250); // Set button position
        quitButton.setSize(200, 50); // Set button size
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //testing the button
                Gdx.app.log("quit", "Clicked");
                Gdx.app.exit();
            }
        });
        // Add buttons to the stage
//        stage.addActor(resumeButton);
        stage.addActor(resumeButton);
//        stage.addActor(instructionButton);
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
        // Dispose of the music
        if (music != null) {
            music.stop();
            music.dispose();
        }
    }
}
