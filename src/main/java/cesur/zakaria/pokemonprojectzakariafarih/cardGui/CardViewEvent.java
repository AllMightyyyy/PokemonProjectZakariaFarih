package cesur.zakaria.pokemonprojectzakariafarih.cardGui;

import javafx.event.Event;

import java.io.Serial;

public class CardViewEvent extends Event {
    @Serial
    private static final long serialVersionUID = 1L;
    public CardViewEvent(CardView source) {
        super(source, null, ANY);
    }

    public CardView getCardView() {
        return (CardView) getSource();
    }
}
