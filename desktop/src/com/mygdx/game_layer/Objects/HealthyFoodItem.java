package com.mygdx.game_layer.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game_layer.Objects.TexturedObject;
public class HealthyFoodItem extends Entities {
    private float scaleFactor = 1.0f;
    public HealthyFoodItem(String texturePath) {
        super();
        addTexturedObject(new TexturedObject(texturePath));
    }

    @Override
    public void render(SpriteBatch batch) {
        // Include your rendering logic here, applying any scaling as necessary
        // Example:
        for (TexturedObject texturedObject : this.getTexturedObjects()) {
            batch.draw(
                    texturedObject.getTexture(),
                    texturedObject.getPosition().x,
                    texturedObject.getPosition().y,
                    texturedObject.getTexture().getWidth() * this.getScaleFactor(),
                    texturedObject.getTexture().getHeight() * this.getScaleFactor()
            );
        }
    }
    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public float getScaleFactorForType() {
        return scaleFactor; // Use the scaleFactor specific to this entity
    }
}

