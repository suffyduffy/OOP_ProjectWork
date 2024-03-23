package com.mygdx.game_engine.Interface;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.awt.*;

public interface ControlledEntity {
    void update(float deltaTime);

    // Method to handle input events for the entity
    void handleInput(InputEvent inputEvent);

    // Method to render the entity on the screen
    void render(Graphics2D g);

    boolean collidesWith(ControlledEntity otherEntity);
}
