package com.mygdx.game.game_engine;

import com.mygdx.game.game_layer.TexturedObject;

public class UnhealthyFoodItem extends Entities {
    public UnhealthyFoodItem(String texturePath) {
        super();
        addTexturedObject(new TexturedObject(texturePath));
    }
}
