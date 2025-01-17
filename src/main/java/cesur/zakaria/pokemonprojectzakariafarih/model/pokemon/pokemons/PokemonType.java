package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.sql.*;
import java.util.*;

/**
 * The PokemonType class represents the types of a Pokemon.
 */
public class PokemonType implements Iterator<EnumPokemonType>, Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private final EnumSetPokemonType enumPokemonTypes;
	private final Map<EnumPokemonType, Double> typeRatio = new EnumMap<>(EnumPokemonType.class);
	private static final Map<EnumSetPokemonType, PokemonType> arrayToType = new HashMap<>();
	private static final String DB_URL = "jdbc:mysql://localhost:3306/pokemondb";
	private static final String USER = "root";
	private static final String PASS = "27122000@ziko";

	/**
	 * Constructs a new PokemonType with the specified EnumSetPokemonType.
	 *
	 * @param enumPokemonTypes The EnumSetPokemonType representing the types of the Pokemon.
	 */
	private PokemonType(EnumSetPokemonType enumPokemonTypes) {
		this.enumPokemonTypes = enumPokemonTypes;
	}

    /**
     * Retrieves the PokemonType associated with the given EnumSetPokemonType.
     *
     * @param enumPokemonTypes The EnumSetPokemonType representing the types of the Pokemon.
     * @return The PokemonType associated with the given EnumSetPokemonType.
     */
    public static PokemonType getPokemonType(EnumSetPokemonType enumPokemonTypes) {
		return PokemonType.arrayToType.get(enumPokemonTypes);
	}

    /**
     * Generates the Pokemon types from the data in the "CSV/grid_types.csv" file.
     * now from Database instead
     *
     * @throws IOException if an I/O error occurs while reading the file.
     */
    public static void generatePokemonType() {
		// First, load the JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		ArrayList<EnumPokemonType> enumPokemonTypesList = new ArrayList<>();
		try (
				// Establish a connection to the database
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				// Create a statement to execute SQL queries
				Statement stmt = conn.createStatement();
				// Execute a SELECT query to get all the data from your types table
				ResultSet rs = stmt.executeQuery("SELECT * FROM grid_types");
		) {
			int nbline = 0;
			while (rs.next()) { // Iterate over each row in the result set
				if (nbline == 0) {
					// Assume first row contains column names for types
					for (int j = 2; j <= rs.getMetaData().getColumnCount(); j++) {
						enumPokemonTypesList.add(EnumPokemonType.fromString(rs.getMetaData().getColumnName(j)));
					}
				} else {
					EnumSetPokemonType enumPokemonTypesSet;
					if (rs.getString(2).isBlank()) {
						enumPokemonTypesSet = new EnumSetPokemonType(EnumPokemonType.fromString(rs.getString(1)));
					} else {
						enumPokemonTypesSet = new EnumSetPokemonType(EnumPokemonType.fromString(rs.getString(1)), EnumPokemonType.fromString(rs.getString(2)));
					}
					PokemonType pokemonType = new PokemonType(enumPokemonTypesSet);
					for (int j = 2; j <= rs.getMetaData().getColumnCount(); j++) {
						if (enumPokemonTypesList.get(j - 2) != null) {
							pokemonType.typeRatio.put(enumPokemonTypesList.get(j - 2), rs.getDouble(j));
						}
					}
					PokemonType.arrayToType.put(enumPokemonTypesSet, pokemonType);
				}
				nbline++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves a string representation of the PokemonType.
	 *
	 * @return A string representation of the PokemonType.
	 */
	@Override
	public String toString() {
		return "PokemonType [enumPokemonTypes=" + enumPokemonTypes + ", typeRatio=" + typeRatio + "]";
	}

	/**
	 * Computes the hash code for the PokemonType.
	 *
	 * @return The hash code value for the PokemonType.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enumPokemonTypes == null) ? 0 : enumPokemonTypes.hashCode());
		result = prime * result + typeRatio.hashCode();
		return result;
	}

	/**
	 * Checks if the PokemonType is equal to another object.
	 *
	 * @param obj The object to compare.
	 * @return True if the PokemonType is equal to the other object, otherwise false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokemonType other = (PokemonType) obj;
		if (enumPokemonTypes == null) {
			if (other.enumPokemonTypes != null)
				return false;
		} else if (!enumPokemonTypes.equals(other.enumPokemonTypes))
			return false;
        return typeRatio.equals(other.typeRatio);
    }

	/**
	 * Checks if there is a next EnumPokemonType.
	 *
	 * @return True if there is a next EnumPokemonType, otherwise false.
	 */
	@Override
	public boolean hasNext() {
		return enumPokemonTypes.hasNext();
	}

	/**
	 * Retrieves the next EnumPokemonType.
	 *
	 * @return The next EnumPokemonType.
	 */
	@Override
	public EnumPokemonType next() {
		return enumPokemonTypes.next();
	}

    /**
     * Resets the iterator over the EnumPokemonType.
     */
    public void resetIterator() {
		enumPokemonTypes.resetIterator();
	}

    /**
     * Retrieves the ratio associated with the specified EnumPokemonType.
     *
     * @param e The EnumPokemonType.
     * @return The ratio associated with the specified EnumPokemonType.
     */
    public Double getRatio(EnumPokemonType e) {
		return typeRatio.get(e);
	}

    /**
     * Retrieves the number of Pokemon types.
     *
     * @return The number of Pokemon types.
     */
    public int size() {
		return EnumPokemonType.values().length;
	}

    /**
     * Retrieves the EnumPokemonType at the specified index.
     *
     * @param index The index of the EnumPokemonType.
     * @return The EnumPokemonType at the specified index.
     */
    public EnumPokemonType get(int index) {
		return enumPokemonTypes.get(index);
	}
}
