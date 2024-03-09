package com.mygdx.game.game_engine;

import java.util.ArrayList;
import java.util.List;
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

    public void dispose() {
        for (TexturedObject texturedObject : texturedObjects) {
            texturedObject.dispose();
        }
    }
}
