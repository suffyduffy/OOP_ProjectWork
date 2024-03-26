package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game_layer.Objects.*;
import com.mygdx.game_layer.Objects.Collidable;

import static com.badlogic.gdx.math.MathUtils.random;

public class PlayerControlManager {
    private InputOutputManager inputOutputManager;
    private TexturedObject texturedObject; // Aggregation

    public PlayerControlManager(InputOutputManager inputOutputManager) {
        this.inputOutputManager = inputOutputManager;
    }

    void handlePlayerMovement(float deltaTime, GameManager gameManager) {
        float playerSpeed = gameManager.isSkinny ? 300f : (gameManager.isFat ? 100f : 200f); // Adjust speed based on player state
        float playerWidth = gameManager.fitManTexture.getWidth() * gameManager.playerScaleX;

        // Move player left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gameManager.facingRight = false;
            gameManager.playerPosition.x -= playerSpeed * deltaTime;
            // Adjust boundary check when facing left
            float leftBoundary = gameManager.facingRight ? 0 : playerWidth;
            gameManager.playerPosition.x = Math.max(leftBoundary, gameManager.playerPosition.x);
        }
        // Move player right
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gameManager.facingRight = true;
            gameManager.playerPosition.x += playerSpeed * deltaTime;
            // Ensure player does not go beyond right screen border
            float rightBoundary = Gdx.graphics.getWidth() - playerWidth;
            gameManager.playerPosition.x = Math.min(rightBoundary, gameManager.playerPosition.x);
        }
    }

    void checkCollisionsAndHandlePlayerState(EntityManager entityManager, GameManager gameManager) {
        float adjustedPlayerPosX = gameManager.playerPosition.x;
        // Adjust hitbox position when facing left
        if (!gameManager.facingRight) {
            adjustedPlayerPosX -= gameManager.fitManTexture.getWidth() * gameManager.playerScaleX;
        }

        for (Entities entity : entityManager.getEntitiesList()) {
            for (TexturedObject texturedObject : entity.getTexturedObjects()) {
                float foodScaleX = entity.getScaleFactorForType();
                float foodScaleY = entity.getScaleFactorForType();

                // Use adjustedPlayerPosX instead of gameManager.playerPosition.x for collision checks
                if (Collidable.checkCollision(new Vector2(adjustedPlayerPosX, gameManager.playerPosition.y),
                        entityManager.getPlayerWidth(gameManager) * gameManager.playerScaleX,
                        gameManager.fitManTexture.getHeight() * gameManager.playerScaleY,
                        texturedObject, foodScaleX, foodScaleY)) {

                    if (entity instanceof HealthyFoodItem) {
                        gameManager.isSkinny = true;
                        gameManager.isFat = false;
                        gameManager.increaseHealthyFoodEaten(); // This is the new method call
                    } else if (entity instanceof UnhealthyFoodItem) {
                        gameManager.isSkinny = false;
                        gameManager.isFat = true;
                        gameManager.decreaseUnhealthyFoodEaten(); // For decrease of Timer when eat unhealthy
                    }
                    // Reposition the entity to the top
                    float initialX = random.nextFloat() * (Gdx.graphics.getWidth() - texturedObject.getTexture().getWidth());
                    texturedObject.setPosition(initialX, Gdx.graphics.getHeight());
                }
            }
        }
    }
}