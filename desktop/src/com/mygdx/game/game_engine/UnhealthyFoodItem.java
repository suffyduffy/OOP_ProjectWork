package com.mygdx.game.game_engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.game_layer.TexturedObject;

public class UnhealthyFoodItem extends Entities {
    private float scaleFactor = 1.0f; // default scale is 1.0 (no scaling)

    public UnhealthyFoodItem(String texturePath) {
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
        // Implement rendering logic specific to UnhealthyFoodItem
    }

    @Override
    public void update(float deltaTime) {
        // Implement update logic specific to UnhealthyFoodItem
    }
}
