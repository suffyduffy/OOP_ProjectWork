package com.mygdx.game.game_engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GrassScene extends Scene {

    public GrassScene() {
        super("Scenes/grassCute.jpg");
    }

    @Override
    public void renderBackground(SpriteBatch batch) {
        super.renderBackground(batch);
        // You can add more rendering logic specific to the mountain scene here
    }

    // Add other methods specific to the mountain scene if necessary
}