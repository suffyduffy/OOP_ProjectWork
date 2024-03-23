package com.mygdx.game_engine.Managers;

import com.mygdx.game_layer.Scenes.GameScene;
import com.mygdx.game_layer.Scenes.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    private List<Scene> scenes; // SceneManager aggregates Scene(s)
    private Scene currentScene; // Keep track of the current scene

    private GameScene gameScene; // This would be set when you switch to the game scene.

    public GameScene getCurrentGameScene() {
        return gameScene; // Return the current instance of GameScene.
    }

    public SceneManager() {
        scenes = new ArrayList<>();
    }
    public void setCurrentScene(Scene scene) {
        if (scene instanceof GameScene) {
            this.gameScene = (GameScene) scene; // Keep track of the game scene.
        }
        currentScene = scene; // Set the current scene as usual.
    }

    // Method to add scene to SceneManager
    public void addScene(Scene scene) {
        if (scene != null) {
            scenes.add(scene);
            if (currentScene == null) { // If there is no current scene, set the added scene as the current scene
                currentScene = scene;
            }
        } else {
            System.out.println("SceneManager: Attempted to add null scene.");
        }
    }

    // Method to get the current scene
    public Scene getCurrentScene() {
        return currentScene;
    }

    // Method to resume the game from a paused state
    public void resumeGame() {
        if (gameScene != null) {
            gameScene.resume(); // Resume the game logic.
            setCurrentScene(gameScene); // Make sure gameScene is the current scene.
        } else {
            // Log an error or handle the case where gameScene is null
        }
    }


}
