package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

/**
 * The CategoryCapacity enum represents the category of a Pokemon capacity.
 */
public enum CategoryCapacity {
	PHYSICAL,
	SPECIAL,
	STATUT;

	/**
	 * Converts a string to a CategoryCapacity enum value.
	 *
	 * @param s The string to convert.
	 * @return The corresponding CategoryCapacity enum value, or null if no match is found.
	 */
	public static CategoryCapacity fromString(String s) {
		switch (s.trim().toLowerCase()) {
			case "physical":
				return PHYSICAL;
			case "statut":
				return STATUT;
			case "special":
				return SPECIAL;
			default:
				return null;
		}
	}
}

