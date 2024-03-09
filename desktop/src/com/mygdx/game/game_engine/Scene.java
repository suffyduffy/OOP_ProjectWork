package com.mygdx.game.game_engine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.game_layer.TexturedObject;
import java.util.List;

public abstract class Scene {
    private Texture backgroundTexture;
    private EntityManager entityManager;

    public Scene(String backgroundImagePath, EntityManager entityManager) {
        this.backgroundTexture = new Texture(backgroundImagePath);
        this.entityManager = entityManager;
    }

    public void render(SpriteBatch batch) {
        // Render the background first
        batch.draw(backgroundTexture, 0, 0);

        // Now render each entity
        for (Entities entity : entityManager.getEntitiesList()) {
            for (TexturedObject texturedObject : entity.getTexturedObjects()) {
                Texture texture = texturedObject.getTexture();
                Vector2 position = texturedObject.getPosition(); // This needs to be a defined method in TexturedObject
                if (texture != null) {
                    batch.draw(texture, position.x, position.y, texture.getWidth(), texture.getHeight());
                }
            }
        }
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
