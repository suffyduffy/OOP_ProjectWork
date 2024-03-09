package com.mygdx.game.game_engine;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.game_layer.TexturedObject;

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

    public void dispose() {
        for (TexturedObject texturedObject : texturedObjects) {
            texturedObject.dispose();
        }
    }
}
