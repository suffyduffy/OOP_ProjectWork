package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game_layer.Objects.*;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private List<Entities> entitiesList;

    // Texture variables
    private Texture fitManTexture;
    private Texture skinnyManTexture;
    private Texture fatManTexture;

    public EntityManager() {
        entitiesList = new ArrayList<>();
        loadTextures(); // Load textures upon EntityManager creation
    }

    // Method to load textures
    private void loadTextures() {
        fitManTexture = new Texture("Sprites/fitman.png");
        skinnyManTexture = new Texture("Sprites/skinnyman.png");
        fatManTexture = new Texture("Sprites/fatman.png");
    }

    public void addEntity(Entities entity) {
        entitiesList.add(entity);
    }

    public void removeEntity(Entities entity) {
        entitiesList.remove(entity);
    }

    public List<Entities> getEntitiesList() {
        return entitiesList; // This method was missing and needs to be added
    }

    public Texture getFitManTexture() {
        return fitManTexture;
    }

    public Texture getSkinnyManTexture() {
        return skinnyManTexture;
    }

    public Texture getFatManTexture() {
        return fatManTexture;
    }

    public void renderEntitiesWithScaling(SpriteBatch batch) {
        for (Entities entity : entitiesList) {
            entity.render(batch); // Assuming each entity knows how to render itself with scaling
        }
    }

    public void dispose() {
        for (Entities entity : entitiesList) {
            entity.dispose();
        }
        // Dispose additional image textures
        fitManTexture.dispose();
        skinnyManTexture.dispose();
        fatManTexture.dispose();
    }

    float getPlayerWidth(GameManager gameManager) {
        Texture playerTexture = gameManager.fitManTexture;
        if (gameManager.isSkinny) {
            playerTexture = gameManager.skinnyManTexture;
        } else if (gameManager.isFat) {
            playerTexture = gameManager.fatManTexture;
        }
        return playerTexture.getWidth();
    }

    // ... any additional methods ...
}
