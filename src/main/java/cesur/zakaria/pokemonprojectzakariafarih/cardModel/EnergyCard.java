package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

/**
 * Represents an Energy card in the Pokemon game.
 * Energy cards provide energy for attacks.
 */
public class EnergyCard extends PlayingCard {
    /** The quantity of energy provided by the card. */
    public static final int QTY_ENERGY = 1;

    /**
     * Constructs an EnergyCard object.
     */
    public EnergyCard() {
        super("Energy Card", Rarity.COMMON);
    }
}
