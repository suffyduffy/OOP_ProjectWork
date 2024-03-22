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

public class AIControlManager {
    // Implement ControlledEntity methods here
    public float timeSinceLastEntity = 0f;
    public float timeBetweenEntities = 2f;
    public int nextEntityIndex = 0;

    private EntityManager entityManager;

    public AIControlManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        // Rest of the constructor...
    }

    public void updateEntityManagement(float deltaTime) {
        timeSinceLastEntity += deltaTime;

        // Check if enough time has passed to activate the next entity
        if (timeSinceLastEntity >= timeBetweenEntities) {
            if (nextEntityIndex < entityManager.getInactiveEntitiesList().size()) {
                Entities entityToSpawn = entityManager.getInactiveEntitiesList().get(nextEntityIndex);

                // Activate the entity, which should also remove it from the inactive list
                entityManager.activateEntity(entityToSpawn);

                // Increment the index to point to the next entity in the list
                nextEntityIndex++;

                // Reset the timer after an entity is activated to ensure one activation per interval
                timeSinceLastEntity = 0f;
            } else {
                // If there are no more entities to activate, reset the index to loop back
                // if looping is desired behavior, or handle the end of activation as needed
                nextEntityIndex = 0; // or handle the end of the list appropriately
            }
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
                    float initialX = random.nextFloat() * (Gdx.graphics.getWidth() );
                    texturedObject.setPosition(initialX, Gdx.graphics.getHeight());
                }
            }
        }
    }
}
