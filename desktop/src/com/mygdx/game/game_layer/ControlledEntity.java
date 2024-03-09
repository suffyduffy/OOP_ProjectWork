package com.mygdx.game.game_layer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ControlledEntity {
    void update(float deltaTime);
    void render(SpriteBatch batch);
    void handleInput();
}