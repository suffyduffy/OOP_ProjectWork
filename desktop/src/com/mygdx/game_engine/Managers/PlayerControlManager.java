package com.mygdx.game_engine.Managers;

import com.mygdx.game_layer.Objects.TexturedObject;

public class PlayerControlManager {
    private InputOutputManager inputOutputManager;
    private TexturedObject texturedObject; // Aggregation

    public PlayerControlManager(InputOutputManager inputOutputManager) {
        this.inputOutputManager = inputOutputManager;
    }

    public InputOutputManager getInputOutputManager() {
        return inputOutputManager;
    }

    public void setInputOutputManager(InputOutputManager inputOutputManager) {
        this.inputOutputManager = inputOutputManager;
    }

    public TexturedObject getTexturedObject() {
        return texturedObject;
    }

    public void setTexturedObject(TexturedObject texturedObject) {
        this.texturedObject = texturedObject;
    }
}