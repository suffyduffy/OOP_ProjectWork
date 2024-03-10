package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Objects.Entities;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private List<Entities> entitiesList;

    public EntityManager() {
        entitiesList = new ArrayList<>();
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
    public void renderEntitiesWithScaling(SpriteBatch batch) {
        for (Entities entity : entitiesList) {
            entity.renderWithScaling(batch);
        }
    }
    public void dispose() {
        for (Entities entity : entitiesList) {
            entity.dispose();
        }
    }

    // ... any additional methods ...
}