package com.mygdx.game_layer.Objects;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game_layer.Objects.TexturedObject;
import com.mygdx.game_engine.Interface.ControlledEntity;

public class Collidable implements ControlledEntity {

    // Method to check collision between player and food item
    public static boolean checkCollision(Vector2 playerPosition, float playerWidth, float playerHeight,
                                         TexturedObject foodItem, float foodScaleX, float foodScaleY) {
        // Calculate player bounding box based on scaled dimensions
        float playerLeft = playerPosition.x;
        float playerRight = playerPosition.x + playerWidth;
        float playerTop = playerPosition.y + playerHeight;
        float playerBottom = playerPosition.y;

        // Get the scaled dimensions of the food item
        float foodWidth = foodItem.getTexture().getWidth() * foodScaleX; // foodScaleX is the scale factor for the food's width
        float foodHeight = foodItem.getTexture().getHeight() * foodScaleY; // foodScaleY is the scale factor for the food's height

        // Calculate food item bounding box based on scaled dimensions
        float foodLeft = foodItem.getPosition().x;
        float foodRight = foodItem.getPosition().x + foodWidth;
        float foodTop = foodItem.getPosition().y + foodHeight;
        float foodBottom = foodItem.getPosition().y;

        // Check for collision
        return playerRight >= foodLeft && playerLeft <= foodRight &&
                playerTop >= foodBottom && playerBottom <= foodTop;
    }
}
