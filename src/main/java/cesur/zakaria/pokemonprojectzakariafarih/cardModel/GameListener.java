package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import java.util.EventListener;

/**
 * Represents a listener for game events.
 * Extends EventListener interface to provide the functionality of a listener for game events.
 */
public interface GameListener extends EventListener {

    /**
     * Notifies the listener of a game event.
     * @param event The game event to be notified.
     */
    void notify(GameEvent event);
}
