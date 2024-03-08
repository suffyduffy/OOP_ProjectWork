package com.mygdx.game;

import com.mygdx.game.InputOutputManager;

public class PlayerControlManager {
    private InputOutputManager inputOutputManager;
    private TexturedObject texturedObject; // Aggregation

    public PlayerControlManager(InputOutputManager inputOutputManager) {
        this.inputOutputManager = inputOutputManager;
        this.texturedObject = texturedObject;
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