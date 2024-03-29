package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import java.util.EventListener;

public interface GameListener extends EventListener {
    void notify(GameEvent event);
}
