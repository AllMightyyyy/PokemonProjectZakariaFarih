package cesur.zakaria.pokemonprojectzakariafarih.libGDXgame;

import com.badlogic.gdx.Game;

public class LibGdxGame extends Game {
    @Override
    public void create() {
        //initialize game here
        this.setScreen(new MainGameScreen(this));
    }

    public void render() {
        super.render();
    }
}
