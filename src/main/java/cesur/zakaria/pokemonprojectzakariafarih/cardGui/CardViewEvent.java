package cesur.zakaria.pokemonprojectzakariafarih.cardGui;

import javafx.event.Event;

import java.io.Serial;

/**
 * Represents an event related to a CardView.
 */
public class CardViewEvent extends Event {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a CardViewEvent with the specified CardView source.
     *
     * @param source The source CardView of the event.
     */
    public CardViewEvent(CardView source) {
        super(source, null, ANY);
    }

    /**
     * Gets the CardView associated with this event.
     *
     * @return The CardView associated with this event.
     */
    public CardView getCardView() {
        return (CardView) getSource();
    }
}
