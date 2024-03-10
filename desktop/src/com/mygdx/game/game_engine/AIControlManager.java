// AIControlManager.java
package com.mygdx.game.game_engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.game_layer.TexturedObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIControlManager {
    public List<Entities> controlledEntities = new ArrayList<>();
    public float timeSinceLastEntity = 0f;
    public float timeBetweenEntities = 2f;
    public int nextEntityIndex = 0;

    public void prepareAIEntities() {
        // Add entities to the controlled list
        // Create and add all entities to the allEntities list with their respective scale factors
        UnhealthyFoodItem burger = new UnhealthyFoodItem("Food/Burger.png");
        burger.setScaleFactor(0.25f);
        controlledEntities.add(burger);

        UnhealthyFoodItem frenchFries = new UnhealthyFoodItem("Food/FrenchFries.png");
        frenchFries.setScaleFactor(0.5f);
        controlledEntities.add(frenchFries);

        UnhealthyFoodItem pizza = new UnhealthyFoodItem("Food/Pizza.png");
        pizza.setScaleFactor(0.25f);
        controlledEntities.add(pizza);

        UnhealthyFoodItem hotDog = new UnhealthyFoodItem("Food/Hotdog.png");
        hotDog.setScaleFactor(0.5f);
        controlledEntities.add(hotDog);

        HealthyFoodItem cookedChicken = new HealthyFoodItem("Food/CookedChicken.png");
        cookedChicken.setScaleFactor(0.25f);
        controlledEntities.add(cookedChicken);

        HealthyFoodItem salad = new HealthyFoodItem("Food/Salad.png");
        salad.setScaleFactor(0.5f);
        controlledEntities.add(salad);
    }

    public void update(float deltaTime) {
        // Only AI-specific updates
        for (Entities entity : controlledEntities) {
            entity.update(deltaTime);
        }
    }

    public void render(SpriteBatch batch) {
        for (Entities entity : controlledEntities) {
            // Render each AI-controlled entity
            entity.render(batch);
        }
    }
}
