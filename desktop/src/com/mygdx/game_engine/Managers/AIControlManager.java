package com.mygdx.game_engine.Managers;

import com.mygdx.game_engine.Interface.ControlledEntity;
import com.mygdx.game_layer.Objects.Entities;
import com.mygdx.game_layer.Objects.HealthyFoodItem;
import com.mygdx.game_layer.Objects.UnhealthyFoodItem;

import java.util.ArrayList;
import java.util.List;

public class AIControlManager implements ControlledEntity {
    // Implement ControlledEntity methods here
    public List<Entities> aiControlledEntities = new ArrayList<>();
    public float timeSinceLastEntity = 0f;
    public float timeBetweenEntities = 2f;
    public int nextEntityIndex = 0;

    public void prepareEntities() {
        // Create and add all entities to the allEntities list with their respective scale factors
        UnhealthyFoodItem burger = new UnhealthyFoodItem("Food/Burger.png");
        burger.setScaleFactor(0.25f);
        aiControlledEntities.add(burger);

        UnhealthyFoodItem frenchFries = new UnhealthyFoodItem("Food/FrenchFries.png");
        frenchFries.setScaleFactor(0.25f);
        aiControlledEntities.add(frenchFries);

        UnhealthyFoodItem pizza = new UnhealthyFoodItem("Food/Pizza.png");
        pizza.setScaleFactor(0.25f);
        aiControlledEntities.add(pizza);

        UnhealthyFoodItem hotDog = new UnhealthyFoodItem("Food/Hotdog.png");
        hotDog.setScaleFactor(0.25f);
        aiControlledEntities.add(hotDog);

        HealthyFoodItem cookedChicken = new HealthyFoodItem("Food/CookedChicken.png");
        cookedChicken.setScaleFactor(0.125f);
        aiControlledEntities.add(cookedChicken);

        HealthyFoodItem salad = new HealthyFoodItem("Food/Salad.png");
        salad.setScaleFactor(0.25f);
        aiControlledEntities.add(salad);

        // Add more entities as needed
    }

    public List<Entities> getAIControlledEntities() {
        return aiControlledEntities;
    }
}