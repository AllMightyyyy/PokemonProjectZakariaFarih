package cesur.zakaria.pokemonprojectzakariafarih.model.fight;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * The Trainer class represents a trainer in the Pokemon game, who possesses a team of Pokemon.
 * It provides methods to manage the trainer's team, such as switching Pokemon, checking their status, and restoring them.
 */
public class Trainer implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;
	private final Pokemon[] pokemons;
	private int currentPokemon;
	private final String name;

    /**
     * Constructs a Trainer object with the given name and Pokemon team.
     *
     * @param name     The name of the trainer.
     * @param pokemons The array of Pokemon representing the trainer's team.
     * @throws IllegalArgumentException if the number of Pokémon exceeds 6.
     */
    public Trainer(String name, Pokemon[] pokemons) {
		this.name = name;

		if (pokemons.length > 6) {
			throw new IllegalArgumentException("A trainer can't have more than 6 pokemons");
		}
		this.pokemons = pokemons;
		currentPokemon = 0;
	}

    /**
     * Retrieves the current active Pokemon of the trainer.
     *
     * @return The current active Pokemon.
     */
    public Pokemon getPokemon() {
		return pokemons[currentPokemon];
	}

    /**
     * Retrieves the Pokemon at the specified index in the trainer's team.
     *
     * @param i The index of the Pokemon.
     * @return The Pokemon at the specified index.
     */
    public Pokemon getPokemon(int i) {
		return pokemons[i];
	}

	public Pokemon[] getPokemons() {
		return pokemons;
	}

    /**
     * Gets the size of the trainer's team.
     *
     * @return The size of the team.
     */
    public int teamSize() {
		return pokemons.length;
	}

    /**
     * Checks if the Pokemon at the specified index in the trainer's team is alive.
     *
     * @param i The index of the Pokemon.
     * @return true if the Pokemon is alive, false otherwise.
     */
    public boolean isPokemonAlive(int i) {
		if (pokemons[i] != null) {
			return pokemons[i].isAlive();
		}
		return false;
	}

    /**
     * Switches the current active Pokemon of the trainer to the one at the specified index.
     *
     * @param i The index of the Pokemon to switch to.
     * @return The newly active Pokemon.
     */
    public Pokemon changePokemon(int i) {
		currentPokemon = i;
		return pokemons[currentPokemon];
	}

    /**
     * Restores all Pokemon in the trainer's team to full health.
     */
    public void restoreTrainer() {
		for (Pokemon pokemon : pokemons) {
			pokemon.restore();
		}
	}

	@Override
	public String toString() {
		return "Trainer [pokemons=" + Arrays.toString(pokemons) + ", currentPokemon=" + currentPokemon + "]";
	}

    /**
     * Checks if the trainer has lost the battle (all Pokemon are fainted).
     *
     * @return true if the trainer has lost, false otherwise.
     */
    public boolean loose() {
		for (Pokemon pokemon : pokemons) {
			if (pokemon != null && pokemon.isAlive()) {
				return false;
			}
		}
		return true;
	}

    /**
     * Checks if the trainer has only one Pokemon alive.
     *
     * @return true if only one Pokemon is alive, false otherwise.
     */
    public boolean onlyOnePokemonAlive() {
		int count = 0;
		for (Pokemon pokemon : pokemons) {
			if (pokemon != null && pokemon.isAlive()) {
				count += 1;
			}
		}
		return count == 1;
	}

    /**
     * Gets the name of the trainer.
     *
     * @return The name of the trainer.
     */
    public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPokemon;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(pokemons);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trainer other = (Trainer) obj;
		if (currentPokemon != other.currentPokemon)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
        return Arrays.equals(pokemons, other.pokemons);
    }
}
