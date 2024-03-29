package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * The Capacity class represents a Pokemon's capacity.
 */
public class Capacity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private static final HashMap<String, Capacity> nameMap = new HashMap<>();
	private final String name;
	private final int power;
	private int powerPoint;
	private final int maxPowerPoint;
	private final int accuracy;
	private final CategoryCapacity categoryCapacity;
	private final EnumPokemonType type;

	/**
	 * Constructs a new Capacity with the specified attributes.
	 *
	 * @param name             The name of the capacity.
	 * @param power            The power of the capacity.
	 * @param powerPoint       The current power points of the capacity.
	 * @param accuracy         The accuracy of the capacity.
	 * @param categoryCapacity The category of the capacity.
	 * @param type             The type of the capacity.
	 */
	private Capacity(String name, int power, int powerPoint, int accuracy, CategoryCapacity categoryCapacity, EnumPokemonType type) {
		this.name = Objects.requireNonNull(name);
		if ((power < 15 || power > 300 || power % 5 != 0) && power != 0) {
			throw new IllegalArgumentException("The power must be between 15 and 300, and it should be a multiple of 5. Current power: " + power);
		}
		this.power = power;
		this.powerPoint = powerPoint;
		if (accuracy < 0 || accuracy > 100) {
			throw new IllegalArgumentException("The accuracy must be between 0 and 100.");
		}
		this.accuracy = accuracy;
		this.categoryCapacity = Objects.requireNonNull(categoryCapacity);
		this.type = type;
		maxPowerPoint = powerPoint;
	}

    /**
     * Retrieves the type of the capacity.
     *
     * @return The type of the capacity.
     */
    public EnumPokemonType getType() {
		return type;
	}

    /**
     * Retrieves the power of the capacity.
     *
     * @return The power of the capacity.
     */
    public int getPower() {
		return power;
	}

    /**
     * Retrieves the name of the capacity.
     *
     * @return The name of the capacity.
     */
    public String getName() {
		return name;
	}

    /**
     * Creates a new instance of Capacity with the specified attributes and adds it to the name map.
     *
     * @param name             The name of the capacity.
     * @param power            The power of the capacity.
     * @param powerPoint       The current power points of the capacity.
     * @param accuracy         The accuracy of the capacity.
     * @param categoryCapacity The category of the capacity.
     * @param type             The type of the capacity.
     * @return The created Capacity instance.
     */
    public static Capacity instance(String name, int power, int powerPoint, int accuracy, CategoryCapacity categoryCapacity, EnumPokemonType type) {
		if (nameMap.containsKey(name)) {
			throw new IllegalArgumentException("The capacity name is already taken.");
		}
		Capacity capacity = new Capacity(name, power, powerPoint, accuracy, categoryCapacity, type);
		nameMap.put(name, capacity);
		return capacity;
	}

    /**
     * Indicates whether the capacity is usable (has remaining power points).
     *
     * @return true if the capacity is usable; false otherwise.
     */
    public boolean isUsable() {
		return powerPoint > 0;
	}

    /**
     * Retrieves the current power points of the capacity.
     *
     * @return The current power points of the capacity.
     */
    public int getPowerPoint() {
		return powerPoint;
	}

    /**
     * Retrieves the maximum power points of the capacity.
     *
     * @return The maximum power points of the capacity.
     */
    public int getMaxPowerPoint() {
		return maxPowerPoint;
	}

    /**
     * Subtracts one PowerPoint from the capacity.
     */
    public void subtractPP() {
		if (powerPoint > 0) {
			powerPoint--;
		}
	}

	@Override
	public String toString() {
		return "Capacity [name=" + name + ", power=" + power + ", accuracy=" + accuracy + ", categoryCapacity="
				+ categoryCapacity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryCapacity.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + power;
		result = prime * result + accuracy;
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
		Capacity other = (Capacity) obj;
		if (categoryCapacity != other.categoryCapacity)
			return false;
        if (!name.equals(other.name))
            return false;
		if (power != other.power)
			return false;
        return accuracy == other.accuracy;
    }
}
