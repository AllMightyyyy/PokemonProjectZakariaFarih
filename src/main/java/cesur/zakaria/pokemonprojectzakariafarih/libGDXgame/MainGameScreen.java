package cesur.zakaria.pokemonprojectzakariafarih.libGDXgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Represents the main game screen where the gameplay occurs.
 * Implements LibGDX's Screen interface to manage screen transitions and rendering.
 */
public class MainGameScreen implements Screen {
    private CharacterSprite characterSprite; // Represents the main character sprite
    private final SpriteBatch batch; // SpriteBatch for rendering sprites
    private TiledMap map; // TiledMap representing the game world
    private OrthogonalTiledMapRenderer renderer; // Renderer for the TiledMap
    private OrthographicCamera camera; // Orthographic camera for viewing the game world

    /**
     * Constructs a new MainGameScreen with a reference to the Game class.
     * Initializes the SpriteBatch.
     *
     * @param ignoredGame The Game instance to manage screen transitions.
     */
    public MainGameScreen(Game ignoredGame) {
        // Reference to our Game class to switch screens
        this.batch = new SpriteBatch();
    }

    /**
     * Overrides the method from the Screen interface to initialize resources,
     * set up the game world, and handle input processing when the screen is shown.
     */
    @Override
    public void show() {
        // Load the TiledMap from file
        map = new TmxMapLoader().load("mainMap.tmx");

        // Create a renderer for the TiledMap
        renderer = new OrthogonalTiledMapRenderer(map);

        // Adjust the camera size to match the map size
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize the CharacterSprite with the loaded TiledMap
        this.characterSprite = new CharacterSprite(map);

        // Set up input processing to handle keyboard events for character movement
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                return characterSprite.handleKeyDown(keyCode);
            }

            @Override
            public boolean keyUp(int keyCode) {
                return characterSprite.handleKeyUp(keyCode);
            }
        });
    }

    /**
     * Overrides the method from the Screen interface to render the game world.
     * Updates the character position, adjusts the camera position, and renders
     * the map and character sprite accordingly.
     *
     * @param delta The time elapsed since the last frame.
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        characterSprite.update(delta); // Update the character position first

        // Center the camera on the character
        float characterCenterX = characterSprite.getX() + characterSprite.getWidth() / 2;
        float characterCenterY = characterSprite.getY() + characterSprite.getHeight() / 2;

        // Clamp the camera position to ensure it doesn't go outside the map bounds
        float cameraHalfWidth = camera.viewportWidth * 0.5f;
        float cameraHalfHeight = camera.viewportHeight * 0.5f;

        float cameraLeft = Math.max(characterCenterX - cameraHalfWidth, 0);
        float cameraBottom = Math.max(characterCenterY - cameraHalfHeight, 0);

        float mapPixelWidth = 200 * 16; // 200 tiles * 16 pixels/tile
        float mapPixelHeight = 200 * 16;

        float cameraRight = Math.min(cameraLeft + camera.viewportWidth, mapPixelWidth);
        float cameraTop = Math.min(cameraBottom + camera.viewportHeight, mapPixelHeight);

        camera.position.set((cameraRight + cameraLeft) / 2, (cameraTop + cameraBottom) / 2, 0);

        camera.update();
        renderer.setView(camera);
        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        characterSprite.render(batch); // Draw the character
        batch.end();
    }

    /**
     * Overrides the method from the Screen interface to resize the screen.
     * Adjusts the camera viewport size to maintain aspect ratio.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        // Set the camera viewport size to maintain aspect ratio
        camera.setToOrtho(false, 608, 400); // Set the viewport size to 608 x 400
    }

    /**
     * Overrides the method from the Screen interface.
     * No action is performed when the application is paused.
     */
    @Override
    public void pause() {}

    /**
     * Overrides the method from the Screen interface.
     * No action is performed when the application is resumed.
     */
    @Override
    public void resume() {}

    /**
     * Overrides the method from the Screen interface.
     * No action is performed when the screen is hidden.
     */
    @Override
    public void hide() {}

    /**
     * Overrides the method from the Screen interface to dispose of resources
     * when the screen is no longer needed.
     */
    @Override
    public void dispose() {
        // Dispose of resources used by the CharacterSprite, SpriteBatch, TiledMap, and renderer
        characterSprite.dispose();
        batch.dispose();
        map.dispose();
        renderer.dispose();
    }
}
