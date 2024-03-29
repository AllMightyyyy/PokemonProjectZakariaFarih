package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;

/**
 * The EnumSetPokemonType class represents a set of Pokemon types.
 */
public class EnumSetPokemonType implements Iterator<EnumPokemonType>, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private EnumPokemonType enumPokemonType1;
	private EnumPokemonType enumPokemonType2;
	private int actualPosition = 0;

	/**
	 * Constructs a new EnumSetPokemonType object with two specified Pokemon types.
	 *
	 * @param enumPokemonType1 The first Pokemon type.
	 * @param enumPokemonType2 The second Pokemon type.
	 */
	public EnumSetPokemonType(EnumPokemonType enumPokemonType1, EnumPokemonType enumPokemonType2) {
		this.enumPokemonType1 = enumPokemonType1;
		this.enumPokemonType2 = enumPokemonType2;
	}

	/**
	 * Constructs a new EnumSetPokemonType object with a single specified Pokemon type.
	 *
	 * @param enumPokemonType1 The Pokemon type.
	 */
	public EnumSetPokemonType(EnumPokemonType enumPokemonType1) {
		this(enumPokemonType1, null);
	}

	/**
	 * Adds a new Pokemon type to the set.
	 *
	 * @param enumPokemonType The Pokemon type to add.
	 */
	public void add(EnumPokemonType enumPokemonType) {
		if (enumPokemonType1 == null) {
			enumPokemonType1 = enumPokemonType;
		} else if (enumPokemonType2 == null) {
			enumPokemonType2 = enumPokemonType;
		}
	}

	/**
	 * Checks if there is another Pokemon type in the set.
	 *
	 * @return true if there is another Pokemon type, false otherwise.
	 */
	@Override
	public boolean hasNext() {
		return actualPosition == 0 ? enumPokemonType1 != null : actualPosition == 1 && enumPokemonType2 != null;
	}

	/**
	 * Retrieves the next Pokemon type in the set.
	 *
	 * @return The next Pokemon type.
	 */
	@Override
	public EnumPokemonType next() {
		if (actualPosition == 0 && enumPokemonType1 != null) {
			actualPosition++;
			return enumPokemonType1;
		} else if (actualPosition == 1 && enumPokemonType2 != null) {
			actualPosition++;
			return enumPokemonType2;
		}
		return null;
	}

	/**
	 * Gets the size of the set.
	 *
	 * @return The size of the set.
	 */
	public int size() {
		return enumPokemonType1 == null ? 0 : enumPokemonType2 == null ? 1 : 2;
	}

	/**
	 * Gets the Pokemon type at the specified index in the set.
	 *
	 * @param index The index of the Pokemon type.
	 * @return The Pokemon type at the specified index, or null if the index is invalid.
	 */
	public EnumPokemonType get(int index) {
		return index == 0 ? enumPokemonType1 : index == 1 ? enumPokemonType2 : null;
	}

	/**
	 * Resets the iterator position to the beginning of the set.
	 */
	public void resetIterator() {
		actualPosition = 0;
	}

	/**
	 * Computes the hash code for the set.
	 *
	 * @return The hash code.
	 */
	@Override
	public int hashCode() {
		int result = 1;
		result += enumPokemonType1 == null ? 0 : enumPokemonType1.hashCode();
		result += enumPokemonType2 == null ? 0 : enumPokemonType2.hashCode();
		return result;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 *
	 * @param obj The reference object with which to compare.
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		EnumSetPokemonType other = (EnumSetPokemonType) obj;
		return (enumPokemonType1 == other.enumPokemonType1 || enumPokemonType2 == other.enumPokemonType1) &&
				(enumPokemonType2 == other.enumPokemonType2 || enumPokemonType1 == other.enumPokemonType2);
	}

	/**
	 * Returns a string representation of the EnumSetPokemonType object.
	 *
	 * @return A string representation of the object.
	 */
	@Override
	public String toString() {
		return "EnumSetPokemonType [enumPokemonType1=" + enumPokemonType1 +
				", enumPokemonType2=" + enumPokemonType2 +
				", actualPosition=" + actualPosition + "]";
	}
}
