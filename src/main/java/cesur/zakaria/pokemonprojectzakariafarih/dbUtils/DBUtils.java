package cesur.zakaria.pokemonprojectzakariafarih.dbUtils;

import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;
import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.*;
import java.sql.*;

/**
 * Provides utility methods for database interactions within the Pokemon Project application. This class
 * handles operations such as user authentication, registration, saving and loading trainer data, and managing
 * player points. It employs the BCryptPasswordEncoder for secure password handling.
 */
public class DBUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final String URL = "jdbc:mysql://localhost:3306/pokemondb";
    private static final String USERNAME = "root";
    private static final String PS = "27122000@ziko";
    public static final String ERROR_CLOSING_RESOURCES = "Error closing resources: ";

    /**
     * Attempts to authenticate a user using the provided credentials. It checks the username and password
     * against the database entries and, upon successful authentication, sets the current player session in AppState.
     *
     * @param username          The username of the user attempting to log in.
     * @param plaintextPassword The plaintext password provided during login.
     * @return True if authentication is successful, false otherwise.
     */
    public static boolean login(String username, String plaintextPassword) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement psLogin = prepareLoginStatement(connection, username);
            ResultSet rsLogin = executeQuery(psLogin);
            if (rsLogin.next()) {
                if (checkPassword(rsLogin, plaintextPassword)) {
                    int userId = rsLogin.getInt("id");
                    int points = retrievePlayerPoints(connection, userId);
                    Player currentPlayer = createPlayer(userId, username, points);
                    AppState.setCurrentPlayer(currentPlayer);
                    return true; // Login successful
                }
            }
            return false; // Login failed
        } catch (SQLException e) {
            handleLoginError(e);
            return false;
        } finally {
            closeConnection(connection);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, "root", PS);
    }

    private static PreparedStatement prepareLoginStatement(Connection connection, String username) throws SQLException {
        String sqlLogin = "SELECT id, hashed_password FROM pokemonplayeruser WHERE username = ?";
        PreparedStatement psLogin = connection.prepareStatement(sqlLogin);
        psLogin.setString(1, username);
        return psLogin;
    }

    private static ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    private static boolean checkPassword(ResultSet rsLogin, String plaintextPassword) throws SQLException {
        String storedHashedPassword = rsLogin.getString("hashed_password");
        return encoder.matches(plaintextPassword, storedHashedPassword);
    }

    private static int retrievePlayerPoints(Connection connection, int userId) throws SQLException {
        String sqlGameData = "SELECT points FROM pokemonplayeruser_data WHERE user_id = ?";
        try (PreparedStatement psGameData = connection.prepareStatement(sqlGameData)) {
            psGameData.setInt(1, userId);
            try (ResultSet rsGameData = psGameData.executeQuery()) {
                return rsGameData.next() ? rsGameData.getInt("points") : 0;
            }
        }
    }

    private static Player createPlayer(int userId, String username, int points) {
        return new Player(userId, username, points);
    }

    private static void handleLoginError(SQLException e) {
        System.out.println("Error during login: " + e.getMessage());
    }

    private static void closeConnection(Connection connection) {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.err.println(ERROR_CLOSING_RESOURCES + e.getMessage());
        }
    }


    /**
     * Registers a new user in the database with the specified details. The password is securely hashed
     * before storage. The method checks for the existence of the username to avoid duplicates.
     *
     * @param username  The username of the new user.
     * @param password  The plaintext password of the new user, which will be hashed for storage.
     * @param firstName The first name of the new user.
     * @param lastName  The last name of the new user.
     * @param gender    The gender of the new user.
     * @return True if registration is successful and the user does not already exist, false otherwise.
     */
    public static boolean registerUser(String username, String password, String firstName, String lastName, String gender) {
        try (Connection connection = DriverManager.getConnection(URL, "root", PS)) {
            connection.setAutoCommit(false); // Start transaction

            if (userExists(connection, username)) {
                return false; // User already exists
            }

            long userId = insertUser(connection, username, password, firstName, lastName, gender);
            if (userId != -1) {
                insertPlayerData(connection, userId);
                connection.commit(); // Commit transaction
                return true; // Registration successful
            }
            return false; // Registration failed
        } catch (SQLException e) {
            handleRegistrationError(e);
            return false;
        }
    }

    private static boolean userExists(Connection connection, String username) throws SQLException {
        String sqlCheckUserExists = "SELECT id FROM pokemonplayeruser WHERE username = ?";
        try (PreparedStatement psCheckUserExists = connection.prepareStatement(sqlCheckUserExists)) {
            psCheckUserExists.setString(1, username);
            try (ResultSet rs = psCheckUserExists.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static long insertUser(Connection connection, String username, String password, String firstName, String lastName, String gender) throws SQLException {
        String sqlInsertUser = "INSERT INTO pokemonplayeruser (username, hashed_password, first_name, last_name, gender) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement psInsertUser = connection.prepareStatement(sqlInsertUser, Statement.RETURN_GENERATED_KEYS)) {
            psInsertUser.setString(1, username);
            psInsertUser.setString(2, encoder.encode(password)); // Hash the password
            psInsertUser.setString(3, firstName);
            psInsertUser.setString(4, lastName);
            psInsertUser.setString(5, gender);
            int userResult = psInsertUser.executeUpdate();
            if (userResult == 1) {
                try (ResultSet rs = psInsertUser.getGeneratedKeys()) {
                    return rs.next() ? rs.getLong(1) : -1;
                }
            }
            return -1;
        }
    }

    private static void insertPlayerData(Connection connection, long userId) throws SQLException {
        String sqlInsertPlayerData = "INSERT INTO pokemonplayeruser_data (user_id, points) VALUES (?, 0)";
        try (PreparedStatement psInsertPlayerData = connection.prepareStatement(sqlInsertPlayerData)) {
            psInsertPlayerData.setLong(1, userId);
            psInsertPlayerData.executeUpdate();
        }
    }

    private static void handleRegistrationError(SQLException e) {
        System.out.println("Registration error: " + e.getMessage());
    }



    /**
     * Updates the points of a player in the database, based on the player's ID and the new points value.
     * This method is typically called after a player has spent points, for example, to evolve Pokemon.
     *
     * @param userId    The ID of the player whose points are being updated.
     * @param newPoints The new points value to set for the player.
     * @return True if the update is successful, false otherwise.
     */
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
                System.err.println(ERROR_CLOSING_RESOURCES + e.getMessage());
            }
        }
    }

    /**
     * Updates the player's points in the database after saving the game state. This method sets the player's
     * points to a specific value, replacing the current value in the database.
     *
     * @param userId    The ID of the player whose points are being updated.
     * @param newPoints The new total points value for the player.
     * @return True if the update is successful, false otherwise.
     */
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
                System.err.println(ERROR_CLOSING_RESOURCES + e.getMessage());
            }
        }
    }

    /**
     * Saves a Trainer object to the database associated with a specific player ID. This method serializes
     * the Trainer object for storage in a binary format within the database.
     *
     * @param playerId The ID of the player associated with the Trainer object.
     * @param trainer  The Trainer object to be saved.
     * @throws SQLException if a database access error occurs.
     * @throws IOException  if an error occurs during object serialization.
     */
    public static void saveTrainer(int playerId, Trainer trainer) throws SQLException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PS);

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
     * Loads a Trainer object from the database for a specific player ID. The Trainer object is deserialized
     * from its stored binary format in the database.
     *
     * @param playerId The ID of the player whose Trainer object is to be loaded.
     * @return The loaded Trainer object, or null if not found.
     * @throws SQLException           if a database access error occurs.
     * @throws IOException            if an error occurs during object deserialization.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     */
    public static Trainer loadTrainer(int playerId) throws SQLException, IOException, ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PS);
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

    /**
     * Retrieves the file path for a Pokemon's image based on a given identifier. This method is used
     * to fetch the visual representation of Pokemon from the database.
     *
     * @param identifier The identifier of the Pokemon whose image path is requested.
     * @return The file path of the Pokemon's image if found, null otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public static String getPokemonImagePath(String identifier) throws SQLException {
        String imagePath = null;
        String query = "SELECT picture FROM pokedex WHERE identifier = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PS);
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

    /**
     * Retrieves the current points of a player from the database.
     *
     * @param userId The ID of the player whose points are to be retrieved.
     * @return The current points of the player.
     * @throws SQLException if a database access error occurs.
     */
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
