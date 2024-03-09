package com.mygdx.game.game_engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.game_layer.TexturedObject;

public class HealthyFoodItem extends Entities {
    private float scaleFactor = 1.0f;
    public HealthyFoodItem(String texturePath) {
        super();
        addTexturedObject(new TexturedObject(texturePath));
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public void render(SpriteBatch batch) {
        // Implement rendering logic specific to HealthyFoodItem
    }

    @Override
    public void update(float deltaTime) {
        // Implement update logic specific to HealthyFoodItem
    }
}

