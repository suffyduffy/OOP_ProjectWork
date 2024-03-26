package com.mygdx.game_layer.Scenes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_engine.Managers.SceneManager;

public class InstructionScene1 extends Scene{

    private Stage stage;
    private Skin skin;
    private SceneManager sceneManager;
    private Music music;
    private TextButton nextBtn;
    public InstructionScene1(EntityManager entityManager,SceneManager sceneManager)
    {
        super("Scenes/IS1.jpg",entityManager, sceneManager);
        this.stage = new Stage();
        this.sceneManager = sceneManager;


        Gdx.input.setInputProcessor(this.stage);

        this.skin = new Skin(Gdx.files.internal("skins/comic-ui.json"));

        //Create next btn
        nextBtn = new TextButton("Next", skin);
        nextBtn.setPosition(640, 65); // Set button position
        nextBtn.setSize(100, 50); // Set button size
        nextBtn.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                InstructionScene2 instructionScene2 = new InstructionScene2(entityManager, sceneManager);
                sceneManager.setCurrentScene(instructionScene2); // This is the likely correct method name

            }
        });

        this.stage.addActor(nextBtn);

    }
    @Override
    public void update(float delta) {
        this.stage.act(delta); // Update the stage

    }
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.end(); // End the sprite batch before drawing the stage

        // Draw the buttons on top
        stage.getViewport().apply(); // Apply the stage's viewport
        stage.draw();

        batch.begin();
    }
}
