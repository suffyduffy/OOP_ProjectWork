package com.mygdx.game_layer.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_layer.Scenes.Scene;

public class GameScene extends Scene {

    public GameScene(EntityManager entityManager) {
        super("Scenes/grassCute.jpg", entityManager);
        Gdx.app.log("GameScene", "Constructor called");
        // Load and play music
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Music/Hey Kids Remake.WAV"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        // Add more rendering logic specific to the grass scene here
    }

    // Add other methods specific to the grass scene if necessary
}
