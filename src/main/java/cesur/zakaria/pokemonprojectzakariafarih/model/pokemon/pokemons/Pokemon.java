package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * The Pokemon class represents a Pokemon entity.
 */
public class Pokemon implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private final PokemonSpecie specie;
	private final Capacity[] capacities;
	private final StatistiquePokemon stat;
	private final String nickName;

	/**
	 * Constructs a new Pokemon with the specified species, capacities, statistics, and nickname.
	 *
	 * @param specie     The species of the Pokemon.
	 * @param capacities The capacities (moves) of the Pokemon.
	 * @param stat       The statistics of the Pokemon.
	 * @param nickName   The nickname of the Pokemon.
	 */
	public Pokemon(PokemonSpecie specie, Capacity[] capacities, StatistiquePokemon stat, String nickName) {
		this.nickName = nickName;
		this.specie = specie;
		this.capacities = capacities;
		this.stat = stat;
	}

	/**
	 * Constructs a new Pokemon with the specified species, capacities, and statistics.
	 *
	 * @param specie     The species of the Pokemon.
	 * @param capacities The capacities (moves) of the Pokemon.
	 * @param stat       The statistics of the Pokemon.
	 */
	public Pokemon(PokemonSpecie specie, Capacity[] capacities, StatistiquePokemon stat) {
		this(specie, capacities, stat, specie.getNamePokemon());
	}

	/**
	 * Retrieves the nickname of the Pokemon.
	 *
	 * @return The nickname of the Pokemon.
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * Retrieves the species of the Pokemon.
	 *
	 * @return The species of the Pokemon.
	 */
	public PokemonSpecie getSpecie() {
		return specie;
	}

	/**
	 * Retrieves the capacities (moves) of the Pokemon.
	 *
	 * @return The capacities of the Pokemon.
	 */
	public Capacity[] getCapacities() {
		return capacities;
	}

	/**
	 * Retrieves the statistics of the Pokemon.
	 *
	 * @return The statistics of the Pokemon.
	 */
	public StatistiquePokemon getStat() {
		return stat;
	}

	/**
	 * Checks if the Pokemon is alive.
	 *
	 * @return True if the Pokemon is alive, otherwise false.
	 */
	public boolean isAlive() {
		return !stat.noPv();
	}

	/**
	 * Inflicts damage on the Pokemon.
	 *
	 * @param damage The amount of damage to inflict.
	 * @return True if the Pokemon still has HP after taking damage, otherwise false.
	 */
	public boolean takeDmg(int damage) {
		return stat.substractDamage(damage);
	}

	/**
	 * Restores the HP of the Pokemon.
	 */
	public void restore() {
		stat.restorePv();
	}

	/**
	 * Checks if the Pokemon can no longer fight.
	 *
	 * @return True if the Pokemon can no longer fight (i.e., all capacities are unusable), otherwise false.
	 */
	public boolean noMoreFight() {
		for (Capacity capacity : capacities) {
			if (capacity.isUsable()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Inflicts damage on the Pokemon based on the capacity used.
	 *
	 * @param capUsed The capacity (move) used to inflict damage.
	 * @return True if the Pokemon still has HP after taking damage, otherwise false.
	 */
	public boolean takeDmgCap(Capacity capUsed) {
		return takeDmg((int) (specie.getTypes().getRatio(capUsed.getType()) * capUsed.getPower()));
	}

	/**
	 * Generates a string representation of the Pokemon.
	 *
	 * @return The string representation of the Pokemon.
	 */
	@Override
	public String toString() {
		return "Pokemon [specie=" + specie + ",\n\t capacities=" + Arrays.toString(capacities) + ",\n\t stat=" + stat
				+ ",\n\t nickname=" + getNickName() + "]";
	}

	/**
	 * Computes the hash code for the Pokemon.
	 *
	 * @return The hash code value for the Pokemon.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(capacities);
		result = prime * result + ((specie == null) ? 0 : specie.hashCode());
		result = prime * result + ((stat == null) ? 0 : stat.hashCode());
		return result;
	}

	/**
	 * Checks if the Pokemon is equal to another object.
	 *
	 * @param obj The object to compare.
	 * @return True if the Pokemon is equal to the other object, otherwise false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pokemon other = (Pokemon) obj;
		if (!Arrays.equals(capacities, other.capacities))
			return false;
		if (specie == null) {
			if (other.specie != null)
				return false;
		} else if (!specie.equals(other.specie))
			return false;
		if (stat == null) {
            return other.stat == null;
		} else return stat.equals(other.stat);
    }
}
