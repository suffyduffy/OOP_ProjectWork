package com.mygdx.game_layer.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game_layer.Objects.TexturedObject;

public class UnhealthyFoodItem extends Entities {
    private Texture texture; // Texture for this food item

    private float scaleFactor; // default scale is 1.0 (no scaling)

    public UnhealthyFoodItem(String texturePath, float scaleFactor) {
        super();
        addTexturedObject(new TexturedObject(texturePath));
        setScaleFactor(scaleFactor); // Set the scale factor for the food item
        this.texture = new Texture(texturePath); // Initialize your texture

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
    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose(); // Dispose of the texture
            texture = null; // Clear the reference to allow garbage collection
        }
    }
}
