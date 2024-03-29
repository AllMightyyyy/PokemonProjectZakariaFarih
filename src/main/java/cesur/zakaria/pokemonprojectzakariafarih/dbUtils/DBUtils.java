package cesur.zakaria.pokemonprojectzakariafarih.dbUtils;

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
     * @param username         The username of the user.
     * @param plaintextPassword The plaintext password entered by the user.
     * @return True if the authentication succeeds, false otherwise.
     */
    public static boolean login(String username, String plaintextPassword) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Establish the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "root", "27122000@ziko");
            // Prepare statement to execute SQL query
            String sql = "SELECT hashed_password FROM pokemonplayeruser WHERE username = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            // Execute query
            rs = ps.executeQuery();
            // Check if there are any results
            if (rs.next()) {
                String storedHashedPassword = rs.getString("hashed_password");
                // Use BCryptPasswordEncoder to check the password
                return encoder.matches(plaintextPassword, storedHashedPassword);
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
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
        PreparedStatement ps = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "root", "27122000@ziko");

            // Check if the user already exists
            psCheckUserExists = connection.prepareStatement("SELECT * FROM pokemonplayeruser WHERE username = ?");
            psCheckUserExists.setString(1, username);
            rs = psCheckUserExists.executeQuery();

            if (rs.next()) {
                // User already exists
                return false;
            } else {
                // Hash the password
                String hashedPassword = encoder.encode(password);

                // Insert the new user
                String sqlInsert = "INSERT INTO pokemonplayeruser (first_name, last_name, username, hashed_password, gender) VALUES (?, ?, ?, ?, ?)";
                ps = connection.prepareStatement(sqlInsert);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, username);
                ps.setString(4, hashedPassword);
                ps.setString(5, gender);

                int result = ps.executeUpdate();
                return result == 1;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (psCheckUserExists != null) psCheckUserExists.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
