package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * The CapacitiesHelper class provides methods for managing the capacities of Pokemon.
 * It loads capacities from a CSV file and creates a deck of capacities.
 */
public class CapacitiesHelper {

    private static CapacityDeck capacityDeck;

    /**
     * Retrieves the deck of capacities.
     *
     * @return The deck of capacities.
     * @throws IOException if an I/O error occurs while reading the data file.
     */
    public static CapacityDeck getCapacityDeck() throws IOException {
        if (capacityDeck == null)
            capacityDeck = generate();
        return capacityDeck;
    }


    private static CapacityDeck generate() {
        if (capacityDeck != null) {
            // If the deck is already set, we return it
            return capacityDeck;
        }
        capacityDeck = new CapacityDeck();

        // Database connection setup
        final String DB_URL = "jdbc:mysql://localhost:3306/pokemondb";
        final String USER = "root";
        final String PASS = "27122000@ziko";

        // SQL query to fetch all moves
        final String QUERY = "SELECT * FROM moves";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(QUERY)) {

                // Process the result set
                while (rs.next()) {
                    try {
                        EnumPokemonType type = EnumPokemonType.fromString(rs.getString("type"));
                        // Instantiate a new Capacity object with the retrieved data
                        Capacity cap = Capacity.instance(
                                rs.getString("identifier"),
                                rs.getInt("power"),
                                rs.getInt("pp"),
                                rs.getInt("accuracy"),
                                CategoryCapacity.fromString(rs.getString("damage_class")),
                                type
                        );
                        // Add the new Capacity object to the deck
                        if (capacityDeck.containsKey(type)) {
                            capacityDeck.add(type, cap);
                        } else {
                            capacityDeck.put(type, cap);
                        }
                    } catch (Exception ignored) {
                        // Handle any exceptions, e.g., parsing errors
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Capacity Helper" + capacityDeck);
        return capacityDeck;
    }
}
