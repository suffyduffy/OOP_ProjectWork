package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game_engine.Interface.ControlledEntity;
import com.mygdx.game_layer.Objects.Entities;
import com.mygdx.game_layer.Objects.HealthyFoodItem;
import com.mygdx.game_layer.Objects.TexturedObject;
import com.mygdx.game_layer.Objects.UnhealthyFoodItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.random;

public class AIControlManager implements ControlledEntity {
    // Implement ControlledEntity methods here
    public List<Entities> aiControlledEntities = new ArrayList<>();
    public float timeSinceLastEntity = 0f;
    public float timeBetweenEntities = 2f;
    public int nextEntityIndex = 0;

    private EntityManager entityManager;

    public AIControlManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        // Rest of the constructor...
    }


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

    public void updateEntityManagement(float deltaTime) {
        timeSinceLastEntity += deltaTime;

        if (timeSinceLastEntity >= timeBetweenEntities && nextEntityIndex < aiControlledEntities.size()) {
            Entities entity = aiControlledEntities.get(nextEntityIndex++);
            // Note: EntityManager should be accessible here, possibly passed in constructor or through a setter
            entityManager.addEntity(entity);
            timeSinceLastEntity = 0f;
        }
    }

    public void updateEntities(float deltaTime, Vector2 playerPosition, float playerScaleX, float playerScaleY) {
        Iterator<Entities> iterator = entityManager.getEntitiesList().iterator();
        while (iterator.hasNext()) {
            Entities entity = iterator.next();
            for (TexturedObject texturedObject : entity.getTexturedObjects()) {
                // Make the food fall down by decreasing the y position
                texturedObject.getPosition().y -= 60 * deltaTime;

                // Reset the position to the top once it reaches the bottom
                if (texturedObject.getPosition().y < 0) {
                    float initialX = random.nextFloat() * (Gdx.graphics.getWidth() - texturedObject.getTexture().getWidth());
                    texturedObject.setPosition(initialX, Gdx.graphics.getHeight());
                }
            }
        }
    }
}
