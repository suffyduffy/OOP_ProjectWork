package com.mygdx.game.game_layer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

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

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
