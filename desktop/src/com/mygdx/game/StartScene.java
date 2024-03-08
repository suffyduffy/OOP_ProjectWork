package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScene extends Scene {

    public StartScene() {
        super("Scenes/mainMenu.jpg");
    }

    @Override
    public void renderBackground(SpriteBatch batch) {
        super.renderBackground(batch);
        // You can add more rendering logic specific to the start scene here
    }

    // Add other methods specific to the start scene if necessary
}