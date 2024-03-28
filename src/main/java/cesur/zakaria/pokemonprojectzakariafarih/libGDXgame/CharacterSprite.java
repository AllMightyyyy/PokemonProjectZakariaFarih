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

    public CharacterSprite(TiledMap map) {
        this.map = map;
        loadAnimations();
        x = 73.74389f; // Set initial X position
        y = 526.78986f; // Set initial Y position
        stateTime = 0f;
        // Initially set the currentFrame to the down rest frame
    }

    private void loadAnimations() {
        texturesToDispose = new Array<>();

        // Assuming the rest frame is the third image in the sequence
        restLeftFrame = new TextureRegion(new Texture(Gdx.files.internal("characterSprites/emerald_left_3.png")));
        restRightFrame = new TextureRegion(new Texture(Gdx.files.internal("characterSprites/emerald_right_3.png")));
        restUpFrame = new TextureRegion(new Texture(Gdx.files.internal("characterSprites/emerald_up_3.png")));
        restDownFrame = new TextureRegion(new Texture(Gdx.files.internal("characterSprites/emerald_down_3.png")));

        // Add rest frames to disposal list
        texturesToDispose.addAll(restLeftFrame.getTexture(), restRightFrame.getTexture(), restUpFrame.getTexture(), restDownFrame.getTexture());

        leftAnimation = loadAnimation("emerald_left", 3);
        rightAnimation = loadAnimation("emerald_right", 3);
        upAnimation = loadAnimation("emerald_up", 3);
        downAnimation = loadAnimation("emerald_down", 3);
    }

    private Animation<TextureRegion> loadAnimation(String baseName, int count) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i < count; i++) { // Exclude the rest frame for animation
            Texture texture = new Texture(Gdx.files.internal("characterSprites/" + baseName + "_" + i + ".png"));
            texturesToDispose.add(texture);
            frames.add(new TextureRegion(texture));
        }
        return new Animation<>(0.1f, frames, Animation.PlayMode.LOOP_PINGPONG);
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        float moveSpeed = 200 * deltaTime; // Adjust this value as needed

        // Update character position based on pressed movement keys
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
            // Log only when a movement key is processed
            System.out.println("Starting movement - Character position - X: " + x + ", Y: " + y);
        }
        return keyProcessed; // Indicate that the key press was handled
    }
    private boolean isWalkable(float x, float y) {
        int tileX = (int) (x / TILE_WIDTH); // Define or replace TILE_WIDTH with appropriate value
        int tileY = (int) (y / TILE_HEIGHT); // Define or replace TILE_HEIGHT with appropriate value
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("ground"); // Use the layer named "bottomGroundGrass"
        if (layer != null) {
            TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
            if (cell != null) {
                Object walkableProperty = cell.getTile().getProperties().get("walkable");
                if (walkableProperty instanceof Boolean) {
                    return (boolean) walkableProperty;
                }
            }
        }
        return false; // Default to non-walkable if tile, cell, or layer is not found or if walkable property is not set
    }


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
            // Log only when a movement key is released
            System.out.println("Stopping movement - Character position - X: " + x + ", Y: " + y);
        }
        return keyProcessed; // Indicate that the key release was handled
    }

    public void render(SpriteBatch batch) {
        float spriteScale = 16 / (float) currentFrame.getRegionWidth(); //16x16 pixels
        batch.draw(currentFrame, x, y, currentFrame.getRegionWidth() * spriteScale, currentFrame.getRegionHeight() * spriteScale);
    }

    public float getX() {
        return x;
    }

    // Method to get the Y coordinate of the character
    public float getY() {
        return y;
    }

    // Method to get the width of the character's current frame
    public float getWidth() {
        // Assuming the width is consistent across all frames, or you can adjust if not
        return currentFrame.getRegionWidth();
    }

    // Method to get the height of the character's current frame
    public float getHeight() {
        // Assuming the height is consistent across all frames, or you can adjust if not
        return currentFrame.getRegionHeight();
    }

    @Override
    public void dispose() {
        for (Texture texture : texturesToDispose) {
            texture.dispose();
        }
    }
}
