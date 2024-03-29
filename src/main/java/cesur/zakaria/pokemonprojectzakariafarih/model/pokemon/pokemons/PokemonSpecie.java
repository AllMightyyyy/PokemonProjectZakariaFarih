package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.Serializable;
import java.util.Objects;

/**
 * The PokemonSpecie class represents a species of Pokemon.
 */
public class PokemonSpecie implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int nbPokemon;
	private final String namePokemon;
	private final PokemonType types;
	private final Size size;
	private final String imagePath;

	/**
	 * Constructs a new PokemonSpecie with the specified attributes.
	 *
	 * @param nbPokemon    The number of the Pokemon species.
	 * @param namePokemon  The name of the Pokemon species.
	 * @param enumPokemonTypes The types of the Pokemon species.
	 * @param height       The height of the Pokemon species.
	 * @param weight       The weight of the Pokemon species.
	 * @param imagePath    The path to the image of the Pokemon species.
	 */
	public PokemonSpecie(int nbPokemon, String namePokemon, PokemonType enumPokemonTypes, double height, double weight,
						 String imagePath) {
		this.nbPokemon = nbPokemon;
		this.namePokemon = Objects.requireNonNull(namePokemon);
		this.size = new Size(height, weight);
		this.types = Objects.requireNonNull(enumPokemonTypes);
		this.imagePath = Objects.requireNonNull(imagePath);
	}

	/**
	 * Retrieves the number of the Pokemon species.
	 *
	 * @return The number of the Pokemon species.
	 */
	public int getNbPokemon() {
		return nbPokemon;
	}

	/**
	 * Retrieves the name of the Pokemon species.
	 *
	 * @return The name of the Pokemon species.
	 */
	public String getNamePokemon() {
		return namePokemon;
	}

	/**
	 * Retrieves the types of the Pokemon species.
	 *
	 * @return The types of the Pokemon species.
	 */
	public PokemonType getTypes() {
		return types;
	}

	/**
	 * Retrieves the path to the image of the Pokemon species.
	 *
	 * @return The path to the image of the Pokemon species.
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Retrieves the size of the Pokemon species.
	 *
	 * @return The size of the Pokemon species.
	 */
	public Size getSize() {
		return size;
	}

	/**
	 * Generates a string representation of the PokemonSpecie.
	 *
	 * @return The string representation of the PokemonSpecie.
	 */
	@Override
	public String toString() {
		return "PokemonSpecie [nbPokemon=" + nbPokemon + ", namePokemon=" + namePokemon + ", types=" + types + ", size="
				+ size + ", imagePath=" + imagePath + "]";
	}

	/**
	 * Computes the hash code for the PokemonSpecie.
	 *
	 * @return The hash code value for the PokemonSpecie.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((namePokemon == null) ? 0 : namePokemon.hashCode());
		result = prime * result + nbPokemon;
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((types == null) ? 0 : types.hashCode());
		return result;
	}

	/**
	 * Checks if the PokemonSpecie is equal to another object.
	 *
	 * @param obj The object to compare.
	 * @return True if the PokemonSpecie is equal to the other object, otherwise false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokemonSpecie other = (PokemonSpecie) obj;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (namePokemon == null) {
			if (other.namePokemon != null)
				return false;
		} else if (!namePokemon.equals(other.namePokemon))
			return false;
		if (nbPokemon != other.nbPokemon)
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (types == null) {
			if (other.types != null)
				return false;
		} else if (!types.equals(other.types))
			return false;
		return true;
	}
}
