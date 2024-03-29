package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The CapacityDeck class represents a deck of capacities categorized by Pokemon types.
 */
public class CapacityDeck implements Serializable {
	private static final long serialVersionUID = 1L;
	private final HashMap<EnumPokemonType, Set<Capacity>> hashMap = new HashMap<>();

	/**
	 * Adds a capacity to the deck associated with the given Pokemon type.
	 *
	 * @param type The Pokemon type associated with the capacity.
	 * @param cap  The capacity to add.
	 */
	public void add(EnumPokemonType type, Capacity cap) {
		Set<Capacity> x = hashMap.get(type);
		x.add(cap);
	}

	/**
	 * Checks if the deck contains capacities for the given Pokemon type.
	 *
	 * @param e The Pokemon type to check.
	 * @return true if the deck contains capacities for the given type; false otherwise.
	 */
	public boolean containsKey(EnumPokemonType e) {
		return hashMap.containsKey(e);
	}

	/**
	 * Adds a new entry to the deck with the specified key and value.
	 *
	 * @param key   The Pokemon type key.
	 * @param value The capacity value.
	 * @return The set of capacities associated with the key.
	 */
	public Set<Capacity> put(EnumPokemonType key, Capacity value) {
		var x = new HashSet<Capacity>();
		x.add(value);
		return hashMap.put(key, x);
	}

	/**
	 * Retrieves a set of capacities associated with the given Pokemon types.
	 *
	 * @param types The Pokemon types to retrieve capacities for.
	 * @return A set of capacities associated with the given Pokemon types.
	 */
	public Set<Capacity> get(PokemonType types) {
		Set<Capacity> capacities = new HashSet<>();
		while (types.hasNext()) {
			EnumPokemonType type = types.next();
			capacities.addAll(hashMap.get(type));
		}
		capacities.addAll(hashMap.get(EnumPokemonType.NORMAL));
		types.resetIterator();
		return capacities;
	}
}
