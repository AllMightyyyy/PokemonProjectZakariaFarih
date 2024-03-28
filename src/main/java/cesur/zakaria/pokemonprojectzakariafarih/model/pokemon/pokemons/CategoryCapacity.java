package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

public enum CategoryCapacity  {
	PHYSICAL,
	SPECIAL,
	STATUT;

	public static CategoryCapacity fromString(String s) {
		switch (s.trim()) {
		case "physical":
			return CategoryCapacity.PHYSICAL;
		case "statut":
			return CategoryCapacity.STATUT;
		case "special":
			return CategoryCapacity.SPECIAL;
		default:
			return null;
		}
	}
}	
