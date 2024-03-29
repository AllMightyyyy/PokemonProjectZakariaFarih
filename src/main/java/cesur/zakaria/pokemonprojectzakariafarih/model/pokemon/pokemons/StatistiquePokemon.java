package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * The StatistiquePokemon class represents the statistics of a Pokémon,
 * including damage, defense, special damage, special defense, experience level, and hit points.
 */
public class StatistiquePokemon implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private final int dmg;
	private final int def;
	private final int dmgspe;
	private final int defspe;
	private final int xpLevel;
	private int pv;
	private final int pvMax;

    /**
     * Generates random statistics for a Pokémon.
     *
     * @return A StatistiquePokemon object with randomly generated statistics.
     */
    public static StatistiquePokemon RandomStat() {
		Random rand = new Random();
		return new StatistiquePokemon(5 + rand.nextInt(185), 5 + rand.nextInt(245), 10 + rand.nextInt(184),
				20 + rand.nextInt(230), 5 + rand.nextInt(175), 25 + rand.nextInt(200));
	}

    /**
     * Constructs a StatistiquePokemon object with the specified statistics.
     *
     * @param dmg     The damage stat of the Pokémon.
     * @param def     The defense stat of the Pokémon.
     * @param dmgspe  The special damage stat of the Pokémon.
     * @param defspe  The special defense stat of the Pokémon.
     * @param xpLevel The experience level of the Pokémon.
     * @param pv      The hit points (HP) of the Pokémon.
     */
    public StatistiquePokemon(int dmg, int def, int dmgspe, int defspe, int xpLevel, int pv) {
		this.dmg = dmg;
		this.def = def;
		this.dmgspe = dmgspe;
		this.defspe = defspe;
		this.xpLevel = xpLevel;
		this.pv = pv;
		pvMax = pv;
	}

	/**
	 * Returns a string representation of the StatistiquePokemon object.
	 *
	 * @return A string representation of the StatistiquePokemon object.
	 */
	@Override
	public String toString() {
		return "StatistiquePokemon [dmg=" + dmg + ", def=" + def + ", dmgspe=" + dmgspe + ", defspe=" + defspe
				+ ", xpLevel=" + xpLevel + ", pv=" + pv + ", pvMax=" + pvMax + "]";
	}

	/**
	 * Generates a hash code value for the StatistiquePokemon object.
	 *
	 * @return The hash code value for the StatistiquePokemon object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + def;
		result = prime * result + defspe;
		result = prime * result + dmg;
		result = prime * result + dmgspe;
		result = prime * result + pv;
		result = prime * result + pvMax;
		result = prime * result + xpLevel;
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 *
	 * @param obj The reference object with which to compare.
	 * @return true if this StatistiquePokemon object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatistiquePokemon other = (StatistiquePokemon) obj;
		if (def != other.def)
			return false;
		if (defspe != other.defspe)
			return false;
		if (dmg != other.dmg)
			return false;
		if (dmgspe != other.dmgspe)
			return false;
		if (pv != other.pv)
			return false;
		if (pvMax != other.pvMax)
			return false;
        return xpLevel == other.xpLevel;
    }

    /**
     * Checks if the Pokémon has no hit points (HP).
     *
     * @return true if the Pokémon has no hit points; false otherwise.
     */
    public boolean noPv() {
		return pv == 0;
	}

    /**
     * Returns the hit points (HP) of the Pokémon as a string in the format "currentHP/maxHP".
     *
     * @return The hit points (HP) of the Pokémon.
     */
    public String pvOnPvMax() {
		return pv + "/" + pvMax;
	}

    /**
     * Calculates the ratio of current hit points (HP) to maximum hit points (HP).
     *
     * @return The ratio of current HP to max HP.
     */
    public double pvRatio() {
		return (double) pv / pvMax;
	}

    /**
     * Subtracts damage from the Pokémon's hit points (HP).
     *
     * @param i The amount of damage to subtract.
     * @return true if the Pokémon's hit points reach zero or less after subtraction; false otherwise.
     */
    public boolean substractDamage(int i) {
		pv -= i;
		if (pv <= 0) {
			pv = 0;
			return true;
		}
		return false;
	}

    /**
     * Restores the Pokémon's hit points (HP) to its maximum value.
     */
    public void restorePv() {
		pv = pvMax;
	}

    /**
     * Retrieves the experience level of the Pokémon.
     *
     * @return The experience level of the Pokémon.
     */
    public int getXpLevel() {
		return xpLevel;
	}
}
