package cesur.zakaria.pokemonprojectzakariafarih.model.pokedex;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.EnumPokemonType;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.EnumSetPokemonType;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.PokemonSpecie;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.PokemonType;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

/**
 * The Pokedex class represents a collection of Pokemon species.
 * It provides methods for accessing and managing the Pokemon species data.
 */
public class Pokedex {

	private static Pokedex pokedex;
	private static final HashMap<Integer, PokemonSpecie> pokedexMap= new HashMap<>();

	/**
	 * Retrieves the singleton instance of the Pokedex.
	 *
	 * @return The singleton instance of the Pokedex.
	 * @throws IOException if an I/O error occurs while reading the data file.
	 */
	public static Pokedex getPokedex() throws IOException {
		if (pokedex == null)
			pokedex = generate();
		return pokedex;
	}

	/**
	 * Generates a new instance of the Pokedex.
	 *
	 * @return a new instance of the Pokedex
	 * @throws IOException if an I/O error occurs while reading the data file
	 */
	private static Pokedex generate() {
		if (pokedex != null) {
			return pokedex;
		}
		pokedex = new Pokedex();

		final String DB_URL = "jdbc:mysql://localhost:3306/pokemondb";
		final String USER = "root";
		final String PASS = "27122000@ziko";
		final String QUERY = "SELECT * FROM pokedex";

		try {
			// Load the JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establish a connection to the database
			try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				 Statement stmt = conn.createStatement();
				 ResultSet rs = stmt.executeQuery(QUERY)) {

				// Process the result set
				while (rs.next()) {
					int id = rs.getInt("id");
					String identifier = rs.getString("identifier");
					String picture = rs.getString("picture");
					double height = rs.getDouble("height") / 10; // Assuming height is stored in tenths of units
					double weight = rs.getDouble("weight") / 10; // Assuming weight is stored in tenths of units
					EnumPokemonType type1 = EnumPokemonType.fromString(rs.getString("type1"));
					EnumPokemonType type2 = EnumPokemonType.fromString(rs.getString("type2"));
					EnumSetPokemonType typesSet = type2 == null ?
							new EnumSetPokemonType(type1) :
							new EnumSetPokemonType(type1, type2);
					PokemonType pokemonType = PokemonType.getPokemonType(typesSet);

					PokemonSpecie specie = new PokemonSpecie(id, identifier, pokemonType, height, weight, picture);
					// Populate the pokedex map
					pokedexMap.put(id, specie);
				}
			} catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return pokedex;
	}

	/**
	 * Retrieves the Pokemon species associated with the specified index.
	 *
	 * @param index The index of the Pokemon species.
	 * @return The Pokemon species.
	 */
	public PokemonSpecie get(Integer index) {
		return pokedexMap.get(index);
	}

	@Override
	public String toString() {
		StringBuilder builder=new StringBuilder();
		builder.append("Pokedex:\n");
		for (PokemonSpecie pokemonSpecie : pokedexMap.values()) {
			builder.append(pokemonSpecie).append("\n");
		}
		return builder.toString();
	}

	/**
	 * Gets the size of the Pokedex.
	 *
	 * @return The size of the Pokedex.
	 */
	public int size() {
		return pokedexMap.size();
	}

}
