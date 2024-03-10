package com.mygdx.game.Objects;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Objects.TexturedObject;

public abstract class Entities {
    private List<TexturedObject> texturedObjects;

    public Entities() {
        texturedObjects = new ArrayList<>();
    }

    public void addTexturedObject(TexturedObject texturedObject) {
        texturedObjects.add(texturedObject);
    }

    public List<TexturedObject> getTexturedObjects() {
        return texturedObjects;
    }

    public Vector2 getPosition() {
        if (!texturedObjects.isEmpty()) {
            return texturedObjects.get(0).getPosition();
        }
        return null; // or some default value
    }

    public void setPosition(float x, float y) {
        for (TexturedObject texturedObject : texturedObjects) {
            texturedObject.setPosition(x, y);
        }
    }

    public Texture getTexture() {
        if (!texturedObjects.isEmpty()) {
            return texturedObjects.get(0).getTexture();
        }
        return null; // or some default value
    }

    // This method includes scaling logic during rendering
    public void renderWithScaling(SpriteBatch batch) {
        for (TexturedObject texturedObject : texturedObjects) {
            float scaleFactor = getScaleFactorForType(); // Implement this method based on entity type
            Texture texture = texturedObject.getTexture();
            Vector2 position = texturedObject.getPosition();
            float width = texture.getWidth() * scaleFactor;
            float height = texture.getHeight() * scaleFactor;

            if (texture != null) {
                batch.draw(texture, position.x, position.y, width, height);
            }
        }
    }

    // Abstract method to be implemented by each subclass
    protected abstract float getScaleFactorForType();
    public void dispose() {
        for (TexturedObject texturedObject : texturedObjects) {
            texturedObject.dispose();
        }
    }
}
