package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game_layer.Objects.*;

import java.util.ArrayList;
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

        // Load textures for the food items
//        burgerTexture = new Texture("Food/Burger.png");
//        cookedchickenTexture = new Texture("Food/CookedChicken.png");
//        frenchfriesTexture = new Texture("Food/FrenchFries.png");
//        fruitTexture = new Texture("Food/Fruit.png");
//        hotdogTexture = new Texture("Food/Hotdog.png");
//        pizzaTexture = new Texture("Food/Pizza.png");
//        saladTexture = new Texture("Food/Salad.png");
    }

    public void initializeFoodItems() {
        // Instantiate and categorize food items
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/Burger.png", 0.25f)); // Assuming constructor takes texture path and scale factor
        Gdx.app.log("EntityManager", "Added Burger. Inactive list size: " + inactiveEntitiesList.size());

        // ... add other food items similarly
        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/FrenchFries.png", 0.25f));
        Gdx.app.log("EntityManager", "Added frenchfries. Inactive list size: " + inactiveEntitiesList.size());

        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/Hotdog.png", 0.25f));
        Gdx.app.log("EntityManager", "Added hotdog. Inactive list size: " + inactiveEntitiesList.size());

        inactiveEntitiesList.add(new UnhealthyFoodItem("Food/Pizza.png", 0.25f));
        Gdx.app.log("EntityManager", "Added Pizza. Inactive list size: " + inactiveEntitiesList.size());

        inactiveEntitiesList.add(new HealthyFoodItem("Food/CookedChicken.png", 0.125f));
        Gdx.app.log("EntityManager", "Added cookedChicken. Inactive list size: " + inactiveEntitiesList.size());
        inactiveEntitiesList.add(new HealthyFoodItem("Food/Fruit.png", 0.125f));
        Gdx.app.log("EntityManager", "Added Fruit. Inactive list size: " + inactiveEntitiesList.size());
        inactiveEntitiesList.add(new HealthyFoodItem("Food/Salad.png", 0.25f));
        Gdx.app.log("EntityManager", "Added Salad. Inactive list size: " + inactiveEntitiesList.size());

    }

    public void addEntity(Entities entity) {
        entitiesList.add(entity);
    }

    public void activateEntity(Entities entity) {
        if (inactiveEntitiesList.remove(entity)) { // Remove the entity from the inactive list if present
            entitiesList.add(entity); // Add the entity to the active list
            Gdx.app.log("EntityManager", "Activated entity. Inactive list size: " + inactiveEntitiesList.size());
            Gdx.app.log("EntityManager", "Activated entity. Active list size: " + entitiesList.size());
        } else {
            Gdx.app.log("EntityManager", "Failed to activate entity. Entity not found in inactive list.");
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
        burgerTexture.dispose();
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
