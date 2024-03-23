package com.mygdx.game_layer.Objects;

import java.io.*;

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
    public int getHighScore() {
        return highScore;
    }
}