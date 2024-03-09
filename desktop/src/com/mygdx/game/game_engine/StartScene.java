package com.mygdx.game.game_engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScene extends Scene {

    public StartScene(EntityManager entityManager) {
        super("Scenes/mainMenu.jpg", entityManager);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        // Add more rendering logic specific to the start scene here
    }

    // Add other methods specific to the start scene if necessary
}
