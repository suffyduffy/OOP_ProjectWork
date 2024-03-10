package com.mygdx.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import java.io.File; // Add this import for File


public class TexturedObject {
    private String texturePath;
    private Texture texture;
    private Vector2 position; // Position attribute

    public TexturedObject(String texturePath) {
        this.texturePath = texturePath;
        this.texture = new Texture(texturePath);
        this.position = new Vector2(0, 0); // Default position
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    // Method to get the texture file name
    public String getTextureName() {
        // Assuming texturePath is the full path, extract the file name
        return new File(texturePath).getName();
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
