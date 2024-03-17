package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game_layer.Objects.*;

import static com.badlogic.gdx.math.MathUtils.random;

public class PlayerControlManager {
    private InputOutputManager inputOutputManager;
    private TexturedObject texturedObject; // Aggregation

    public PlayerControlManager(InputOutputManager inputOutputManager) {
        this.inputOutputManager = inputOutputManager;
    }

    public InputOutputManager getInputOutputManager() {
        return inputOutputManager;
    }

    public void setInputOutputManager(InputOutputManager inputOutputManager) {
        this.inputOutputManager = inputOutputManager;
    }

    public TexturedObject getTexturedObject() {
        return texturedObject;
    }

    public void setTexturedObject(TexturedObject texturedObject) {
        this.texturedObject = texturedObject;
    }

    void handlePlayerMovement(float deltaTime, GameManager gameManager) {
        float playerSpeed = gameManager.isSkinny ? 300f : (gameManager.isFat ? 100f : 200f); // Adjust speed based on player state

        // Move player left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gameManager.playerPosition.x -= playerSpeed * deltaTime;
            // Ensure player does not go beyond left screen border
            gameManager.playerPosition.x = Math.max(0, gameManager.playerPosition.x);
        }
        // Move player right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            float playerWidth = gameManager.entityManager.getPlayerWidth(gameManager);
            gameManager.playerPosition.x += playerSpeed * deltaTime;
            // Ensure player does not go beyond right screen border
            gameManager.playerPosition.x = Math.min(700, gameManager.playerPosition.x);
        }
    }

    void checkCollisionsAndHandlePlayerState(EntityManager entityManager, GameManager gameManager) {
        for (Entities entity : entityManager.getEntitiesList()) {
            for (TexturedObject texturedObject : entity.getTexturedObjects()) {
                float foodScaleX = entity.getScaleFactorForType();
                float foodScaleY = entity.getScaleFactorForType();

                if (Collidable.checkCollision(gameManager.playerPosition, entityManager.getPlayerWidth(gameManager) * gameManager.playerScaleX, gameManager.fitManTexture.getHeight() * gameManager.playerScaleY,
                        texturedObject, foodScaleX, foodScaleY)) {
                    if (entity instanceof HealthyFoodItem) {
                        gameManager.isSkinny = true;
                        gameManager.isFat = false;
                    } else if (entity instanceof UnhealthyFoodItem) {
                        gameManager.isSkinny = false;
                        gameManager.isFat = true;
                    }
                    // Reposition the entity to the top
                    float initialX = random.nextFloat() * (Gdx.graphics.getWidth() - texturedObject.getTexture().getWidth());
                    texturedObject.setPosition(initialX, Gdx.graphics.getHeight());
                }
            }
        }
    }
}