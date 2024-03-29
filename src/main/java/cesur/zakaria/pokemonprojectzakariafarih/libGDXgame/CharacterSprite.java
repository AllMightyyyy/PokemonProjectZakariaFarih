package cesur.zakaria.pokemonprojectzakariafarih.libGDXgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Represents a character sprite in a LibGDX game.
 * Handles character animation, movement, and rendering.
 */
public class CharacterSprite implements Disposable {
    private static final float TILE_WIDTH = 16;
    private static final float TILE_HEIGHT = 16;
    private TextureRegion currentFrame;
    private TextureRegion restUpFrame, restDownFrame, restLeftFrame, restRightFrame;
    private Animation<TextureRegion> upAnimation, downAnimation, leftAnimation, rightAnimation;
    private float stateTime; // Tracks animation state time
    private float x, y; // Character position
    private String lastDirection = "DOWN"; // Initially set Default Rest to Down
    private Array<Texture> texturesToDispose;
    private boolean movingUp = false;
    private boolean movingDown = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private TiledMap map;

    /**
     * Creates a new CharacterSprite with the specified TiledMap.
     *
     * @param map The TiledMap to use for collision detection.
     */
    public CharacterSprite(TiledMap map) {
        this.map = map;
        loadAnimations();
        x = 73.74389f; // Set initial X position
        y = 526.78986f; // Set initial Y position
        stateTime = 0f;
    }

    /**
     * Loads the character animations from texture files.
     */
    private void loadAnimations() {
        texturesToDispose = new Array<>();

        // Load rest frames
        restLeftFrame = new TextureRegion(new Texture(Gdx.files.internal("characterSprites/emerald_left_3.png")));
        restRightFrame = new TextureRegion(new Texture(Gdx.files.internal("characterSprites/emerald_right_3.png")));
        restUpFrame = new TextureRegion(new Texture(Gdx.files.internal("characterSprites/emerald_up_3.png")));
        restDownFrame = new TextureRegion(new Texture(Gdx.files.internal("characterSprites/emerald_down_3.png")));

        // Add rest frames to disposal list
        texturesToDispose.addAll(restLeftFrame.getTexture(), restRightFrame.getTexture(), restUpFrame.getTexture(), restDownFrame.getTexture());

        // Load animations
        leftAnimation = loadAnimation("emerald_left", 3);
        rightAnimation = loadAnimation("emerald_right", 3);
        upAnimation = loadAnimation("emerald_up", 3);
        downAnimation = loadAnimation("emerald_down", 3);
    }

    /**
     * Loads an animation from texture files.
     *
     * @param baseName The base name of the animation texture files.
     * @param count    The number of frames in the animation.
     * @return The loaded animation.
     */
    private Animation<TextureRegion> loadAnimation(String baseName, int count) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i < count; i++) { // Exclude the rest frame for animation
            Texture texture = new Texture(Gdx.files.internal("characterSprites/" + baseName + "_" + i + ".png"));
            texturesToDispose.add(texture);
            frames.add(new TextureRegion(texture));
        }
        return new Animation<>(0.1f, frames, Animation.PlayMode.LOOP_PINGPONG);
    }

    /**
     * Updates the character's state.
     *
     * @param deltaTime The time since the last update.
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        float moveSpeed = 200 * deltaTime; // Adjust this value as needed

        // Update character position based on pressed movement keys and collision detection
        if (movingUp && isWalkable(x, y + moveSpeed)) {
            y += moveSpeed;
            lastDirection = "UP";
        }
        if (movingDown && isWalkable(x, y - moveSpeed)) {
            y -= moveSpeed;
            lastDirection = "DOWN";
        }
        if (movingLeft && isWalkable(x - moveSpeed, y)) {
            x -= moveSpeed;
            lastDirection = "LEFT";
        }
        if (movingRight && isWalkable(x + moveSpeed, y)) {
            x += moveSpeed;
            lastDirection = "RIGHT";
        }

        // Update current animation frame based on movement
        if (movingUp || movingDown || movingLeft || movingRight) {
            switch (lastDirection) {
                case "UP":
                    currentFrame = upAnimation.getKeyFrame(stateTime, true);
                    break;
                case "DOWN":
                    currentFrame = downAnimation.getKeyFrame(stateTime, true);
                    break;
                case "LEFT":
                    currentFrame = leftAnimation.getKeyFrame(stateTime, true);
                    break;
                case "RIGHT":
                    currentFrame = rightAnimation.getKeyFrame(stateTime, true);
                    break;
            }
        } else {
            // When not moving, revert to the rest frame based on the last direction
            updateRestFrame();
        }
    }

    /**
     * Updates the current frame to the rest frame based on the last direction.
     */
    private void updateRestFrame() {
        switch (lastDirection) {
            case "UP":
                currentFrame = restUpFrame;
                break;
            case "DOWN":
                currentFrame = restDownFrame;
                break;
            case "LEFT":
                currentFrame = restLeftFrame;
                break;
            case "RIGHT":
                currentFrame = restRightFrame;
                break;
        }
    }

    /**
     * Handles key press events for character movement.
     *
     * @param keyCode The key code of the pressed key.
     * @return True if the key press was handled, false otherwise.
     */
    public boolean handleKeyDown(int keyCode) {
        boolean keyProcessed = false;
        switch (keyCode) {
            case Input.Keys.UP:
                movingUp = true;
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                movingDown = true;
                keyProcessed = true;
                break;
            case Input.Keys.LEFT:
                movingLeft = true;
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                movingRight = true;
                keyProcessed = true;
                break;
        }
        if (keyProcessed) {
            System.out.println("Starting movement - Character position - X: " + x + ", Y: " + y);
        }
        return keyProcessed;
    }

    /**
     * Handles key release events for character movement.
     *
     * @param keyCode The key code of the released key.
     * @return True if the key release was handled, false otherwise.
     */
    public boolean handleKeyUp(int keyCode) {
        boolean keyProcessed = false;
        switch (keyCode) {
            case Input.Keys.UP:
                movingUp = false;
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                movingDown = false;
                keyProcessed = true;
                break;
            case Input.Keys.LEFT:
                movingLeft = false;
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                movingRight = false;
                keyProcessed = true;
                break;
        }
        if (keyProcessed) {
            System.out.println("Stopping movement - Character position - X: " + x + ", Y: " + y);
        }
        return keyProcessed;
    }

    /**
     * Renders the character sprite.
     *
     * @param batch The SpriteBatch used for rendering.
     */
    public void render(SpriteBatch batch) {
        float spriteScale = 16 / (float) currentFrame.getRegionWidth(); // Assuming 16x16 pixels
        batch.draw(currentFrame, x, y, currentFrame.getRegionWidth() * spriteScale, currentFrame.getRegionHeight() * spriteScale);
    }

    /**
     * Gets the X coordinate of the character.
     *
     * @return The X coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the Y coordinate of the character.
     *
     * @return The Y coordinate.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the width of the character's current frame.
     *
     * @return The width of the frame.
     */
    public float getWidth() {
        return currentFrame.getRegionWidth();
    }

    /**
     * Gets the height of the character's current frame.
     *
     * @return The height of the frame.
     */
    public float getHeight() {
        return currentFrame.getRegionHeight();
    }

    /**
     * Disposes of resources used by the character sprite.
     */
    @Override
    public void dispose() {
        for (Texture texture : texturesToDispose) {
            texture.dispose();
        }
    }

    /**
     * Checks if the character can walk to the specified position.
     *
     * @param x The X coordinate to check.
     * @param y The Y coordinate to check.
     * @return True if the character can walk to the position, false otherwise.
     */
    private boolean isWalkable(float x, float y) {
        int tileX = (int) (x / TILE_WIDTH);
        int tileY = (int) (y / TILE_HEIGHT);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("ground");
        if (layer != null) {
            TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
            if (cell != null) {
                Object walkableProperty = cell.getTile().getProperties().get("walkable");
                if (walkableProperty instanceof Boolean) {
                    return (boolean) walkableProperty;
                }
            }
        }
        return false;
    }
}
