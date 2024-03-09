package com.mygdx.game.game_engine;

import com.mygdx.game.game_layer.TexturedObject;
import java.util.ArrayList;
import java.util.List;

public abstract class Entities {
    private List<TexturedObject> texturedObjects;

    public Entities() {
        texturedObjects = new ArrayList<>();
    }

    public void addTexturedObject(TexturedObject texturedObject) {
        texturedObjects.add(texturedObject);
    }

    public void removeTexturedObject(TexturedObject texturedObject) {
        texturedObjects.remove(texturedObject);
    }

    // Other methods related to Entities

    // Example method to get all textured objects
    public List<TexturedObject> getTexturedObjects() {
        return texturedObjects;
    }

    // Any other methods you might have in this class would go here
}
