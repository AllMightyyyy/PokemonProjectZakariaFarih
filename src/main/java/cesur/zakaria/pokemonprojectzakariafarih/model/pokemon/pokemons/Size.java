package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Size class represents the size of a Pokémon with its height and weight.
 */
public record Size(double height, double weight) implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

    /**
     * Constructs a Size object with the specified height and weight.
     *
     * @param height The height of the Pokémon.
     * @param weight The weight of the Pokémon.
     */
    public Size {
	}

    /**
     * Retrieves the height of the Pokémon.
     *
     * @return The height of the Pokémon.
     */
    @Override
	public double height() {
		return height;
	}

    /**
     * Retrieves the weight of the Pokémon.
     *
     * @return The weight of the Pokémon.
     */
    @Override
	public double weight() {
		return weight;
	}

	/**
	 * Returns a string representation of the Size object.
	 *
	 * @return A string representation of the Size object.
	 */
	@Override
	public String toString() {
		return "Size [weight=" + weight + ", height=" + height + "]";
	}

	/**
	 * Generates a hash code value for the Size object.
	 *
	 * @return The hash code value for the Size object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 *
	 * @param obj The reference object with which to compare.
	 * @return true if this Size object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Size other = (Size) obj;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		return Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
	}
}
