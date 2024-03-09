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

        // Now render each entity with scaling if needed
        for (Entities entity : entityManager.getEntitiesList()) {
            for (TexturedObject texturedObject : entity.getTexturedObjects()) {
                Texture texture = texturedObject.getTexture();
                Vector2 position = texturedObject.getPosition();

                float scaleFactor = 1.0f; // Default scale factor
                float width = texture.getWidth();
                float height = texture.getHeight();

                // Check if this entity is a HealthyFoodItem and specifically if it's the CookedChicken
                if (entity instanceof HealthyFoodItem) {
                    if ("CookedChicken.png".equals(texturedObject.getTextureName()) ||
                            "Salad.png".equals(texturedObject.getTextureName())) {
                        scaleFactor = ((HealthyFoodItem) entity).getScaleFactor();
                    }
                }
                // Check if this entity is an UnhealthyFoodItem and if it's the FrenchFries
                else if (entity instanceof UnhealthyFoodItem) {
                    if ("FrenchFries.png".equals(texturedObject.getTextureName()) || "Hotdog.png".equals(texturedObject.getTextureName()) || "Pizza.png".equals(texturedObject.getTextureName()) || "Burger.png".equals(texturedObject.getTextureName())) {
                        scaleFactor = ((UnhealthyFoodItem) entity).getScaleFactor();
                    }
                }

                // Apply the scale factor
                width *= scaleFactor;
                height *= scaleFactor;

                if (texture != null) {
                    batch.draw(texture, position.x, position.y, width, height);
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
