package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.util.HashMap;
import java.util.Map;

/**
 * The EnumPokemonType enum represents the types of Pokemon.
 */
public enum EnumPokemonType {
	/**
	 * None enum pokemon type.
	 */
	None,
	/**
	 * Acier enum pokemon type.
	 */
	ACIER,
	/**
	 * Fighthing enum pokemon type.
	 */
	FIGHTHING,
	/**
	 * Dragon enum pokemon type.
	 */
	DRAGON,
	/**
	 * Water enum pokemon type.
	 */
	WATER,
	/**
	 * Electric enum pokemon type.
	 */
	ELECTRIC,
	/**
	 * Fairy enum pokemon type.
	 */
	FAIRY,
	/**
	 * Fire enum pokemon type.
	 */
	FIRE,
	/**
	 * Ice enum pokemon type.
	 */
	ICE,
	/**
	 * Bug enum pokemon type.
	 */
	BUG,
	/**
	 * Normal enum pokemon type.
	 */
	NORMAL,
	/**
	 * Grass enum pokemon type.
	 */
	GRASS,
	/**
	 * Poison enum pokemon type.
	 */
	POISON,
	/**
	 * Psychic enum pokemon type.
	 */
	PSYCHIC,
	/**
	 * Rock enum pokemon type.
	 */
	ROCK,
	/**
	 * Ground enum pokemon type.
	 */
	GROUND,
	/**
	 * Ghost enum pokemon type.
	 */
	GHOST,
	/**
	 * Dark enum pokemon type.
	 */
	DARK,
	/**
	 * Flying enum pokemon type.
	 */
	FLYING;

    static {
        new HashMap<EnumPokemonType, String>() {{
            put(EnumPokemonType.POISON, "poison");
            put(EnumPokemonType.FLYING, "flying");
            put(EnumPokemonType.GROUND, "ground");
            put(EnumPokemonType.FAIRY, "fairy");
            put(EnumPokemonType.GRASS, "grass");
            put(EnumPokemonType.FIGHTHING, "fighting");
            put(EnumPokemonType.PSYCHIC, "psychic");
            put(EnumPokemonType.ACIER, "steel");
            put(EnumPokemonType.ICE, "ice");
            put(EnumPokemonType.ROCK, "rock");
            put(EnumPokemonType.WATER, "water");
            put(EnumPokemonType.ELECTRIC, "electric");
            put(EnumPokemonType.DRAGON, "dragon");
            put(EnumPokemonType.DARK, "dark");
            put(EnumPokemonType.GHOST, "ghost");
            put(EnumPokemonType.BUG, "bug");
            put(EnumPokemonType.FIRE, "fire");
            put(EnumPokemonType.NORMAL, "Normal");
        }};
    }

    private static final HashMap<String, EnumPokemonType> stringToType = new HashMap<>() {{
        put("poison", EnumPokemonType.POISON);
        put("flying", EnumPokemonType.FLYING);
        put("ground", EnumPokemonType.GROUND);
        put("fairy", EnumPokemonType.FAIRY);
        put("grass", EnumPokemonType.GRASS);
        put("fighting", EnumPokemonType.FIGHTHING);
        put("psychic", EnumPokemonType.PSYCHIC);
        put("steel", EnumPokemonType.ACIER);
        put("ice", EnumPokemonType.ICE);
        put("rock", EnumPokemonType.ROCK);
        put("water", EnumPokemonType.WATER);
        put("electric", EnumPokemonType.ELECTRIC);
        put("dragon", EnumPokemonType.DRAGON);
        put("dark", EnumPokemonType.DARK);
        put("ghost", EnumPokemonType.GHOST);
        put("bug", EnumPokemonType.BUG);
        put("fire", EnumPokemonType.FIRE);
        put("normal", EnumPokemonType.NORMAL);
    }};

	/**
	 * Converts a string to the corresponding EnumPokemonType enum value.
	 *
	 * @param s The string to convert.
	 * @return The corresponding EnumPokemonType enum value, or null if no match is found.
	 */
	public static EnumPokemonType fromString(String s) {
		String test = s.replaceAll("\\s", ""); // Remove whitespace
		return EnumPokemonType.stringToType.get(test);
	}

	/**
	 * Converts the EnumPokemonType enum value to its string representation.
	 *
	 * @return The string representation of the EnumPokemonType.
	 */
	@Override
	public String toString() {
		return EnumPokemonType.stringToType.entrySet()
				.stream()
				.filter(entry -> EnumPokemonType.this.equals(entry.getValue()))
				.map(Map.Entry::getKey)
				.iterator()
				.next();
	}
}
