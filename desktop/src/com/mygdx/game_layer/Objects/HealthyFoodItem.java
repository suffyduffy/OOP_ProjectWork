package com.mygdx.game_layer.Objects;

import com.mygdx.game_layer.Objects.TexturedObject;
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

