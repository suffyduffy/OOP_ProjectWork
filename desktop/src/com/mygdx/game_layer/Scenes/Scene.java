package com.mygdx.game_layer.Scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game_engine.Managers.EntityManager;
import com.mygdx.game_layer.Objects.Entities;
import com.mygdx.game_layer.Objects.HealthyFoodItem;
import com.mygdx.game_layer.Objects.TexturedObject;
import com.mygdx.game_layer.Objects.UnhealthyFoodItem;

import java.util.List;

public abstract class Scene {
    private Texture backgroundTexture;
    private EntityManager entityManager;

    public Scene(String backgroundImagePath, EntityManager entityManager) {
        this.backgroundTexture = new Texture(backgroundImagePath);
        this.entityManager = entityManager;
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch) {
        // Render the background first
        batch.draw(backgroundTexture, 0, 0);

        entityManager.renderEntitiesWithScaling(batch);
    }
    public void create(){

    }

    public void dispose() {
        // Dispose of the background texture
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        // Dispose of the entities managed by the EntityManager
        entityManager.dispose();
    }
}