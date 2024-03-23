package com.mygdx.game_layer.Objects;

public class Score {
    private int healthyFoodEaten;

    public Score() {
        healthyFoodEaten = 0;
    }

    public void increaseHealthyFoodEaten() {
        healthyFoodEaten++;
    }

// if want to minus food point after touching unhealthy food
//    public void decreaseUnhealthyFoodEaten() {
//        if (healthyFoodEaten > 0) {
//            healthyFoodEaten--;
//        }
//    }

    public int getHealthyFoodEaten() {
        return healthyFoodEaten;
    }
}