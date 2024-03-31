package cesur.zakaria.pokemonprojectzakariafarih.dbUtils;

import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;

/**
 * Utility class for interacting with the database.
 * Provides methods for user authentication and registration.
 */
public class DBUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Attempts to authenticate a user based on the provided username and password.
     *
     * @param username          The username of the user.
     * @param plaintextPassword The plaintext password entered by the user.
     * @return True if the authentication succeeds, false otherwise.
     */
    public static boolean login(String username, String plaintextPassword) {
        Connection connection = null;
        PreparedStatement psLogin = null;
        PreparedStatement psGameData = null;
        ResultSet rsLogin = null;
        ResultSet rsGameData = null;
        try {
            // Establish the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "root", "27122000@ziko");
            // Prepare statement to execute SQL query for user authentication
            String sqlLogin = "SELECT id, hashed_password FROM pokemonplayeruser WHERE username = ?";
            psLogin = connection.prepareStatement(sqlLogin);
            psLogin.setString(1, username);
            // Execute query
            rsLogin = psLogin.executeQuery();
            // Check if there are any results for user authentication
            if (rsLogin.next()) {
                String storedHashedPassword = rsLogin.getString("hashed_password");
                int userId = rsLogin.getInt("id");
                // Use BCryptPasswordEncoder to check the password
                if (encoder.matches(plaintextPassword, storedHashedPassword)) {
                    // If password matches, retrieve additional player data
                    String sqlGameData = "SELECT points FROM pokemonplayeruser_data WHERE user_id = ?";
                    psGameData = connection.prepareStatement(sqlGameData);
                    psGameData.setInt(1, userId);
                    rsGameData = psGameData.executeQuery();
                    if (rsGameData.next()) {
                        int points = rsGameData.getInt("points");
                        // Create a Player object and set the current player session in AppState
                        Player currentPlayer = new Player(userId, username, points);
                        AppState.setCurrentPlayer(currentPlayer);
                        return true; // Login successful
                    }
                }
            }
            return false; // Login failed
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        } finally {
            // Close resources
            try {
                if (rsGameData != null) rsGameData.close();
                if (psGameData != null) psGameData.close();
                if (rsLogin != null) rsLogin.close();
                if (psLogin != null) psLogin.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    /**
     * Registers a new user with the provided details.
     *
     * @param username  The username of the new user.
     * @param password  The password of the new user.
     * @param firstName The first name of the new user.
     * @param lastName  The last name of the new user.
     * @param gender    The gender of the new user.
     * @return True if the registration succeeds, false if the user already exists or an error occurs.
     */
    public static boolean registerUser(String username, String password, String firstName, String lastName, String gender) {
        Connection connection = null;
        PreparedStatement psInsertUser = null;
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsertPlayerData = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "root", "27122000@ziko");
            connection.setAutoCommit(false); // Start transaction

            // Check if the user already exists
            psCheckUserExists = connection.prepareStatement("SELECT id FROM pokemonplayeruser WHERE username = ?");
            psCheckUserExists.setString(1, username);
            rs = psCheckUserExists.executeQuery();
            if (rs.next()) {
                // User already exists
                return false;
            }

            // Insert the new user into 'users' table
            String sqlInsertUser = "INSERT INTO pokemonplayeruser (username, hashed_password, first_name, last_name, gender) VALUES (?, ?, ?, ?, ?)";
            psInsertUser = connection.prepareStatement(sqlInsertUser, Statement.RETURN_GENERATED_KEYS);
            psInsertUser.setString(1, username);
            psInsertUser.setString(2, encoder.encode(password)); // Hash the password
            psInsertUser.setString(3, firstName);
            psInsertUser.setString(4, lastName);
            psInsertUser.setString(5, gender);

            int userResult = psInsertUser.executeUpdate();
            if (userResult == 1) {
                // Get the generated user ID
                rs = psInsertUser.getGeneratedKeys();
                if (rs.next()) {
                    long userId = rs.getLong(1);

                    // Insert default player data into 'player_data' table
                    String sqlInsertPlayerData = "INSERT INTO pokemonplayeruser_data (user_id, points) VALUES (?, 0)";
                    psInsertPlayerData = connection.prepareStatement(sqlInsertPlayerData);
                    psInsertPlayerData.setLong(1, userId);
                    psInsertPlayerData.executeUpdate();

                    // Commit transaction
                    connection.commit();
                    return true; // Registration successful
                }
            }
            return false; // Registration failed
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    System.err.println("Transaction rollback error: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (psCheckUserExists != null) psCheckUserExists.close();
                if (psInsertUser != null) psInsertUser.close();
                if (psInsertPlayerData != null) psInsertPlayerData.close();
                if (connection != null) {
                    connection.setAutoCommit(true); // Restore auto-commit mode
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public static boolean updatePlayerPoints(int userId, int newPoints) {
        Connection connection = null;
        PreparedStatement psUpdatePoints = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "root", "27122000@ziko");
            String sqlUpdatePoints = "UPDATE pokemonplayeruser_data SET points = points + ? WHERE user_id = ?";
            psUpdatePoints = connection.prepareStatement(sqlUpdatePoints);
            psUpdatePoints.setInt(1, newPoints);
            psUpdatePoints.setInt(2, userId);

            int rowsAffected = psUpdatePoints.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.out.println("Error updating player points: " + e.getMessage());
            return false;
        } finally {
            // Close resources
            try {
                if (psUpdatePoints != null) psUpdatePoints.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
