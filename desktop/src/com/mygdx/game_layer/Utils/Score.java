package com.mygdx.game_layer.Utils;


public class Score {
    private int healthyFoodEaten;
    private int highScore;

    public Score() {
        healthyFoodEaten = 0;
        highScore = 0;
    }

    public void increaseHealthyFoodEaten() {
        healthyFoodEaten++;
        if (healthyFoodEaten > highScore) {
            highScore = healthyFoodEaten;
        }
    }
    public int getHealthyFoodEaten() {
        return healthyFoodEaten;
    }

}