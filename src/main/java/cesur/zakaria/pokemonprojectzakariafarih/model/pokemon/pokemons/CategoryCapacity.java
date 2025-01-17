package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

/**
 * The CategoryCapacity enum represents the category of a Pokemon capacity.
 */
public enum CategoryCapacity {
    /**
     * Physical category capacity.
     */
    PHYSICAL,
    /**
     * Special category capacity.
     */
    SPECIAL,
    /**
     * Statut category capacity.
     */
    STATUT;

    /**
     * Converts a string to a CategoryCapacity enum value.
     *
     * @param s The string to convert.
     * @return The corresponding CategoryCapacity enum value, or null if no match is found.
     */
    public static CategoryCapacity fromString(String s) {
        return switch (s.trim().toLowerCase()) {
            case "physical" -> PHYSICAL;
            case "statut" -> STATUT;
            case "special" -> SPECIAL;
            default -> null;
        };
	}
}

