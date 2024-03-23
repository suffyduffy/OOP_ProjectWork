package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game_layer.Objects.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityManager {
    private List<Entities> entitiesList;
    private List<Entities> inactiveEntitiesList; // Entities waiting to be activated

    private boolean renderEntities = true;

    // Texture variables
    private Texture fitManTexture;
    private Texture skinnyManTexture;
    private Texture fatManTexture;

    public EntityManager() {
        entitiesList = new ArrayList<>();
        inactiveEntitiesList = new ArrayList<>(); // Initialize the list
        loadTextures(); // Load textures upon EntityManager creation
    }

    public void setRenderEntities(boolean render) {
        this.renderEntities = render;
    }

    // Method to load textures
    private void loadTextures() {
        // Load textures for the player
        fitManTexture = new Texture("Sprites/fitman.png");
        skinnyManTexture = new Texture("Sprites/skinnyman.png");
        fatManTexture = new Texture("Sprites/fatman.png");
    }

    public void initializeFoodItems() {
        // Instantiate and categorize food items
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/Burger.png", 0.125f)); // Assuming constructor takes texture path and scale factor
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/FrenchFries.png", 0.125f));
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/Hotdog.png", 0.125f));
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/Pizza.png", 0.125f));
        inactiveEntitiesList.add(new HealthyFoodItem("Food/CookedChicken.png", 0.125f));
        inactiveEntitiesList.add(new HealthyFoodItem("Food/Fruit.png", 0.125f));
        inactiveEntitiesList.add(new HealthyFoodItem("Food/Salad.png", 0.125f));
        inactiveEntitiesList.add(new HealthyFoodItem("Food/Water.png", 0.125f));
        Collections.shuffle(inactiveEntitiesList);
    }

    public void activateEntity(Entities entity) {
        if (inactiveEntitiesList.remove(entity)) { // Remove the entity from the inactive list if present
            entitiesList.add(entity); // Add the entity to the active list
         }
    }

    public List<Entities> getInactiveEntitiesList() {
        return this.inactiveEntitiesList; // Return the list of inactive entities
    }

    public List<Entities> getEntitiesList() {
        return entitiesList;
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
        if(renderEntities)
        {
            for (Entities entity : entitiesList) {
                entity.render(batch); // Assuming each entity knows how to render itself with scaling
            }
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

    public void update(float delta) {
    }

    // ... any additional methods ...
}
