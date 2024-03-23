package com.mygdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game_engine.Managers.GameManager;

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		// Set up LWJGL3 configurations
		config.setTitle("HealthyEating");
		config.setForegroundFPS(60);
		config.setWindowIcon("Icon.png");
		config.setWindowedMode(800, 600); // Set your desired width and height
		new Lwjgl3Application(new GameManager(), config);
	}
}