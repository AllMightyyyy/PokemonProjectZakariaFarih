package cesur.zakaria.pokemonprojectzakariafarih.cardGui;

import cesur.zakaria.pokemonprojectzakariafarih.cardModel.GameEvent;
import javafx.event.EventHandler;

/**
 * Represents a listener for CardView events.
 */
public interface CardViewListener extends EventHandler<CardViewEvent> {

    /**
     * Notifies the listener of a game event.
     *
     * @param event The game event to be notified.
     */
    void notify(GameEvent event);
}
