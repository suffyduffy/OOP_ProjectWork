package com.mygdx.game_engine.Managers;

import com.mygdx.game_layer.Objects.Entities;

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

    public void dispose() {
        for (Entities entity : entitiesList) {
            entity.dispose();
        }
    }

    // ... any additional methods ...
}