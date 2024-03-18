package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game_layer.Objects.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityManager {
    private List<Entities> entitiesList;
    private List<Entities> inactiveEntitiesList; // Entities waiting to be activated


    // Texture variables
    private Texture fitManTexture;
    private Texture skinnyManTexture;
    private Texture fatManTexture;
    private Texture burgerTexture;
    private Texture cookedchickenTexture;
    private Texture frenchfriesTexture;
    private Texture fruitTexture;
    private Texture hotdogTexture;
    private Texture pizzaTexture;
    private Texture saladTexture;


    public EntityManager() {
        entitiesList = new ArrayList<>();
        inactiveEntitiesList = new ArrayList<>(); // Initialize the list
        loadTextures(); // Load textures upon EntityManager creation
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
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/Burger.png", 0.25f)); // Assuming constructor takes texture path and scale factor
        // ... add other food items similarly
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/FrenchFries.png", 0.25f));
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/Hotdog.png", 0.25f));
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/Pizza.png", 0.25f));
        inactiveEntitiesList.add(new HealthyFoodItem("Food/CookedChicken.png", 0.125f));
        inactiveEntitiesList.add(new HealthyFoodItem("Food/Fruit.png", 0.125f));
        inactiveEntitiesList.add(new HealthyFoodItem("Food/Salad.png", 0.25f));
        Collections.shuffle(inactiveEntitiesList);

    }

    public void addEntity(Entities entity) {
        entitiesList.add(entity);
    }

    public void activateEntity(Entities entity) {
        if (inactiveEntitiesList.remove(entity)) { // Remove the entity from the inactive list if present
            entitiesList.add(entity); // Add the entity to the active list
         }
    }

    public List<Entities> getInactiveEntitiesList() {
        return this.inactiveEntitiesList; // Return the list of inactive entities
    }
//    public void removeEntity(Entities entity) {
//        entitiesList.remove(entity);
//    }

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
    public Texture getBurgerTexture(){
        return burgerTexture;
    }

    public Texture getFrenchfriesTexture() {
        return frenchfriesTexture;
    }

    public Texture getFruitTexture() {
        return fruitTexture;
    }

    public Texture getHotdogTexture() {
        return hotdogTexture;
    }

    public Texture getSaladTexture() {
        return saladTexture;
    }

    public Texture getPizzaTexture() {
        return pizzaTexture;
    }

    public Texture getCookedchickenTexture() {
        return cookedchickenTexture;
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
