package cesur.zakaria.pokemonprojectzakariafarih.dbUtils;

import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;
import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.*;
import java.sql.*;

/**
 * Utility class for interacting with the database.
 * Provides methods for user authentication and registration.
 */
public class DBUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final String URL = "jdbc:mysql://localhost:3306/pokemondb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "27122000@ziko";

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
    public static boolean updatePlayerPointsAfterSave(int userId, int newPoints) {
        Connection connection = null;
        PreparedStatement psUpdatePoints = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "root", "27122000@ziko");
            String sqlUpdatePoints = "UPDATE pokemonplayeruser_data SET points = ? WHERE user_id = ?";
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
    /**
     * Saves the player's Trainer object to a MySQL database.
     *
     * @param playerId the ID of the player associated with the trainer
     * @param trainer  the Trainer object to save
     * @throws SQLException if a database access error occurs
     * @throws IOException  if there is an error while saving the object
     */
    public static void saveTrainer(int playerId, Trainer trainer) throws SQLException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Check if a trainer entry already exists for the given playerId
            pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM trainers WHERE user_id = ?");
            pstmt.setInt(1, playerId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    // Update existing trainer entry
                    pstmt = conn.prepareStatement("UPDATE trainers SET trainer_data = ? WHERE user_id = ?");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(trainer);
                    byte[] trainerBytes = baos.toByteArray();
                    ByteArrayInputStream bais = new ByteArrayInputStream(trainerBytes);
                    pstmt.setBinaryStream(1, bais, trainerBytes.length);
                    pstmt.setInt(2, playerId);
                    pstmt.executeUpdate();
                    return;
                }
            }

            // Insert new trainer entry
            pstmt = conn.prepareStatement("INSERT INTO trainers (user_id, trainer_data) VALUES (?, ?)");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(trainer);
            byte[] trainerBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(trainerBytes);
            pstmt.setInt(1, playerId);
            pstmt.setBinaryStream(2, bais, trainerBytes.length);
            pstmt.executeUpdate();
        } finally {
            // Close resources
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Loads the player's Trainer object from a MySQL database.
     *
     * @param playerId the ID of the player whose Trainer object to load
     * @return the loaded Trainer object
     * @throws SQLException if a database access error occurs
     * @throws IOException  if there is an error while loading the object
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public static Trainer loadTrainer(int playerId) throws SQLException, IOException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("SELECT trainer_data FROM trainers WHERE user_id = ? ORDER BY id DESC LIMIT 1")) {
            pstmt.setInt(1, playerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    byte[] trainerBytes = rs.getBytes("trainer_data");
                    ByteArrayInputStream bais = new ByteArrayInputStream(trainerBytes);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    return (Trainer) ois.readObject();
                }
            }
        }
        return null;
    }

    public static String getPokemonImagePath(String identifier) throws SQLException {
        String imagePath = null;
        String query = "SELECT picture FROM pokedex WHERE identifier = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, identifier.toLowerCase());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    imagePath = rs.getString("picture");
                }
            }
        }
        return imagePath;
    }
    public static int getUserPoints(int userId) throws SQLException {
        int points = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "root", "27122000@ziko");
            String sql = "SELECT points FROM pokemonplayeruser_data WHERE user_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                points = rs.getInt("points");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return points;
    }
}
