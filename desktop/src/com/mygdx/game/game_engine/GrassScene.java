package com.mygdx.game.game_engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GrassScene extends Scene {

    public GrassScene(EntityManager entityManager) {
        super("Scenes/grassCute.jpg", entityManager);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        // Add more rendering logic specific to the grass scene here
    }

    // Add other methods specific to the grass scene if necessary
}
