package cesur.zakaria.pokemonprojectzakariafarih.cardGui;

import cesur.zakaria.pokemonprojectzakariafarih.cardModel.GameEvent;
import javafx.event.EventHandler;

public interface CardViewListener extends EventHandler<CardViewEvent> {
    void notify(GameEvent event);
}
