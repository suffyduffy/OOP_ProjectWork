package com.mygdx.game_layer.Objects;

public class Timer {
    private float timeRemaining;
    private boolean isRunning;

    public Timer(float initialTime) {
        this.timeRemaining = initialTime;
        this.isRunning = false;
    }

    public void start() {
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    public void reset(float initialTime) {
        timeRemaining = initialTime;
        isRunning = false;
    }

    public void update(float deltaTime) {
        if (isRunning) {
            timeRemaining -= deltaTime;
            if (timeRemaining <= 0) {
                timeRemaining = 0;
                isRunning = false;
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public float getTimeRemaining() {
        return timeRemaining;
    }
}