package com.mygdx.game_layer.Objects;

import com.mygdx.game_layer.Objects.TexturedObject;

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
    protected float getScaleFactorForType() {
        return scaleFactor; // Use the scaleFactor specific to this entity
    }
}
