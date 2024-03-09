package com.mygdx.game.game_engine;

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
}

