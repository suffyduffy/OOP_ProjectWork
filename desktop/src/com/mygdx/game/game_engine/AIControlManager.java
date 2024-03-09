// AIControlManager.java
package com.mygdx.game.game_engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.game_layer.TexturedObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIControlManager {
    private List<Entities> controlledEntities = new ArrayList<>();
    private float timeSinceLastEntity = 0f;
    private float timeBetweenEntities = 2f;
    private int nextEntityIndex = 0;

    public void addEntity(Entities entity) {
        // Add entities to the controlled list
        controlledEntities.add(entity);
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
