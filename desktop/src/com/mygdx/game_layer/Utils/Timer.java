package com.mygdx.game_layer.Utils;

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

    // Adds time to the timer
    public void addTime(float amount) {
        this.timeRemaining += amount;
    }

    public void minusTime(float amount) {
        this.timeRemaining -= amount;
    }


    public boolean isRunning() {
        return isRunning;
    }

    public float getTimeRemaining() {
        return timeRemaining;
    }

    public void pause() {
    }

    public boolean isTimeUp() {
        return !isRunning && timeRemaining <= 0;
    }

}