package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

/**
 * Abstract class representing a playing card.
 */
public abstract class PlayingCard {
    /**
     * Enumeration representing the rarity of a playing card.
     */
    public enum Rarity {
        /**
         * Common rarity.
         */
        COMMON,
        /**
         * Uncommon rarity.
         */
        UNCOMMON,
        /**
         * Rare rarity.
         */
        RARE }

    private String name;
    private Rarity rarity;

    /**
     * Constructor to initialize a playing card with a name and rarity.
     *
     * @param name   The name of the playing card.
     * @param rarity The rarity of the playing card.
     */
    public PlayingCard(String name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    /**
     * Retrieves the name of the playing card.
     *
     * @return The name of the playing card.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the rarity of the playing card.
     *
     * @return The rarity of the playing card.
     */
    public Rarity getRarity() {
        return this.rarity;
    }
}
