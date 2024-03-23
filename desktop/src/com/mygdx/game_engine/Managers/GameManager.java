package com.mygdx.game_engine.Managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game_layer.Scenes.GameOverScene;
import com.mygdx.game_layer.Scenes.MenuScene;
import com.mygdx.game_layer.Scenes.Scene;
import com.mygdx.game_layer.Scenes.GameScene;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game_layer.Utils.Score;
import com.mygdx.game_layer.Utils.Timer;


public class GameManager extends Game {

    private PlayerControlManager playerControlManager;
    private AIControlManager aiControlManager;
    EntityManager entityManager;
    private SceneManager sceneManager;
    private InputOutputManager inputOutputManager;
    private SpriteBatch batch;
    private Timer gameTimer; // New timer field
    private Score score;
    Texture fitManTexture;
    Texture skinnyManTexture;
    Texture fatManTexture;
    Vector2 playerPosition;
    boolean isSkinny = false;
    boolean isFat = false;
    boolean facingRight = true;
    float playerScaleX = 1.0f; // Default scale factor for width
    float playerScaleY = 1.0f; // Default scale factor for height
    float timeCount = 30; // Default game time
    private BitmapFont font; // Font for text rendering

    @Override
    public void create() {
        batch = new SpriteBatch();
        inputOutputManager = new InputOutputManager();
        playerControlManager = new PlayerControlManager(inputOutputManager);
        entityManager = new EntityManager();
        aiControlManager = new AIControlManager(entityManager);
        sceneManager = new SceneManager();

        // Initialize the start scene and add it to the scene manager
        MenuScene menuScene = new MenuScene(entityManager, sceneManager);
        sceneManager.addScene(menuScene);
        sceneManager.setCurrentScene(menuScene);


        // Load additional images from EntityManager
        fitManTexture = entityManager.getFitManTexture();
        skinnyManTexture = entityManager.getSkinnyManTexture();
        fatManTexture = entityManager.getFatManTexture();
        playerPosition = new Vector2(
                (Gdx.graphics.getWidth() - fitManTexture.getWidth()) / 2,
                0
        );

        entityManager.initializeFoodItems(); // This line initializes the food items
        gameTimer = new Timer(timeCount); // Adjust the initial time as needed

        // Initialize font
        font = new BitmapFont(Gdx.files.internal("Skins/custom.fnt"),
                Gdx.files.internal("Skins/custom.png"), false);
        font.setColor(Color.BLACK);

        // Initialize scoreboard
        score = new Score();

    }

    @Override
    public void render() {
        super.render();
        update(Gdx.graphics.getDeltaTime());
        batch.begin();
        Scene currentScene = sceneManager.getCurrentScene();
        if (currentScene != null) {
            currentScene.render(batch);

            // Draw player only if in the game scene
            if (currentScene instanceof GameScene) {
                Texture playerTexture = fitManTexture;
                float scaleX = 0.4f; // Default scale factor for width
                float scaleY = 0.4f; // Default scale factor for height

                if (isSkinny) {
                    playerTexture = skinnyManTexture;
                    // Define scale factors for the skinny state, for example:
                    scaleX = 0.4f;
                    scaleY = 0.4f;
                } else if (isFat) {
                    playerTexture = fatManTexture;
                    // Define scale factors for the fat state, for example:
                    scaleX = 0.4f;
                    scaleY = 0.4f;
                }

                // Render the timer
                font.draw(batch, "Time: " + (int) gameTimer.getTimeRemaining(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);

                //Render the scoreboard
                font.draw(batch, "Healthy Foods: " + score.getHealthyFoodEaten(), 20, Gdx.graphics.getHeight() - 20);

                // Check if time is up
                if (gameTimer.getTimeRemaining() <= 0) {
                    // Change scene
                    GameOverScene gameOverScene = new GameOverScene(entityManager, sceneManager);
                    sceneManager.setCurrentScene(gameOverScene);
                    resetGame();
                }

                // Apply scale factors when drawing the texture
                batch.draw(playerTexture,
                        playerPosition.x,
                        playerPosition.y,
                        playerTexture.getWidth() * scaleX * (facingRight ? 1 : -1), // This will flip the texture if facingLeft is false
                        playerTexture.getHeight() * scaleY);
            }
        }
        batch.end();
    }

    private void update(float deltaTime) {
        Scene currentScene = sceneManager.getCurrentScene();
        gameTimer.update(deltaTime);

        if (gameTimer.isTimeUp()) {
            stopGame();
        }
        // Update the scale factors based on the player's state
        if (isSkinny) {
            playerScaleX = 0.4f;
            playerScaleY = 0.4f;
        } else if (isFat) {
            playerScaleX = 0.4f; // Assuming you want the fat state to be slightly larger
            playerScaleY = 0.4f;
        } else {
            // Reset to default scale if neither skinny nor fat
            playerScaleX = 0.4f;
            playerScaleY = 0.4f;
        }

        // Check if the current scene is not StartScene before adding entities
        if ((currentScene instanceof GameScene)) {
            aiControlManager.updateEntityManagement(deltaTime);
            aiControlManager.updateEntities(deltaTime, playerPosition, playerScaleX, playerScaleY);
        }

        playerControlManager.handlePlayerMovement(deltaTime, this);

        // Player collision logic and other player-specific updates
        playerControlManager.checkCollisionsAndHandlePlayerState(entityManager, this);

        // When the game scene starts, start the timer once
        if (currentScene instanceof GameScene && !gameTimer.isRunning()) {
            gameTimer.start();
        }
    }

    private void stopGame() {
        // Stop the timer
        gameTimer.stop();
        entityManager.setRenderEntities(false);
    }
    private void resetGame() {
        // Reset the game timer to its initial value
        gameTimer.reset(timeCount);
        entityManager.setRenderEntities(false);
        Texture playerTexture = fitManTexture;
        isSkinny = false;
        isFat = false;

    }
    public void increaseHealthyFoodEaten() {
        //healthy food + 1 when eaten
        score.increaseHealthyFoodEaten();
        this.gameTimer.addTime(3); // Add 3 seconds to the timer
    }

    public void decreaseUnhealthyFoodEaten() {
        this.gameTimer.minusTime(10); // Subtract 10 seconds to the timer
    }

    @Override
    public void dispose() {
        if (sceneManager.getCurrentScene() != null) {
            sceneManager.getCurrentScene().dispose();
        }
        if (entityManager != null) {
            entityManager.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
    }

}
