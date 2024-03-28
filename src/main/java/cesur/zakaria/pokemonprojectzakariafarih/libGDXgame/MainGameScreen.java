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

public class MainGameScreen implements Screen {
    private Game game; // Reference to our Game class to switch screen
    private CharacterSprite characterSprite;
    private SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public MainGameScreen(Game game) {
        this.game = game;
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        // Initialize resources, set up the game world, etc.
        map = new TmxMapLoader().load("mainMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        // Adjust camera size to match map size
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize CharacterSprite
        this.characterSprite = new CharacterSprite(map);

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

    @Override
    public void render(float delta) {
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

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, 608, 400); // Set the viewport size to 608 x 400
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        characterSprite.dispose();
        batch.dispose();
        map.dispose();
        renderer.dispose();
    }
}
