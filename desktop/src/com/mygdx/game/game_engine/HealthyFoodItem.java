package com.mygdx.game.game_engine;

import com.mygdx.game.game_layer.TexturedObject;

public class HealthyFoodItem extends Entities {
    public HealthyFoodItem(String texturePath) {
        super();
        addTexturedObject(new TexturedObject(texturePath));
    }
}

