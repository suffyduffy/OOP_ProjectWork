package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game_engine.Managers.EntityManager;

public class GrassScene extends Scene {

    public GrassScene(EntityManager entityManager) {
        super("Scenes/grassCute.jpg", entityManager);
        Gdx.app.log("GrassScene", "Constructor called");
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
