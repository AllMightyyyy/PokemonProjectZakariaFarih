package cesur.zakaria.pokemonprojectzakariafarih.libGDXgame;

import com.badlogic.gdx.Game;

/**
 * Main game class extending LibGDX's Game class.
 * Responsible for initializing the game and setting the initial screen.
 */
public class LibGdxGame extends Game {

    /**
     * Called when the application is first created.
     * Initializes the game and sets the initial screen.
     */
    @Override
    public void create() {
        // Initialize game here
        this.setScreen(new MainGameScreen(this));
    }

    /**
     * Renders the current screen.
     */
    public void render() {
        super.render();
    }
}
