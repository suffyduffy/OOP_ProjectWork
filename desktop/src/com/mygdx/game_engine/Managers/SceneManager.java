package com.mygdx.game_engine.Managers;

import com.mygdx.game_layer.Scenes.GameScene;
import com.mygdx.game_layer.Scenes.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    private List<Scene> scenes; // SceneManager aggregates Scene(s)
    private Scene currentScene; // Keep track of the current scene

    public SceneManager() {
        scenes = new ArrayList<>();
    }
    // In SceneManager.java
    public void setCurrentScene(Scene scene) {
        if (scene != null) {
            currentScene = scene;
        } else {
            System.out.println("SceneManager: Attempted to set current scene to null.");
        }
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

    // Method to set the current scene
    public void setCurrentScene(GameScene scene) {
        if (scene != null) {
            currentScene = scene;
        } else {
            System.out.println("SceneManager: Attempted to set current scene to null.");
        }
    }

    // Method to get the current scene
    public Scene getCurrentScene() {
        return currentScene;
    }

    // Other methods related to scene management can be added here
}
