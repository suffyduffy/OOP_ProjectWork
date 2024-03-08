package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.GameManager;

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		// Set up LWJGL3 configurations
		config.setTitle("FatFuck");
		config.setWindowedMode(800, 600); // Set your desired width and height
		new Lwjgl3Application(new GameManager(), config);
	}
}