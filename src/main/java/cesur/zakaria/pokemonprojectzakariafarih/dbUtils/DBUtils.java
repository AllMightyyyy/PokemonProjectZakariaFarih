package cesur.zakaria.pokemonprojectzakariafarih.dbUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;

public class DBUtils {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static boolean login(String username, String plaintextPassword) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Establish the connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "root", "27122000@ziko");
            // Prepare statement to execute SQL query
            String sql = "SELECT hashed_password FROM pokemonplayeruser WHERE username = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            // Execute query
            rs = ps.executeQuery();
            // Check if we got any results
            if (rs.next()) {
                String storedHashedPassword = rs.getString("hashed_password");
                // Use BCryptPasswordEncoder to check the password
                return encoder.matches(plaintextPassword, storedHashedPassword);
            }
            return false;
            //f
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
    public static boolean registerUser(String username, String password, String firstName, String lastName, String gender) {
        // Validation (Make sure to validate input according to your requirements)
        /*
        if (!validateUsername(username) || !validatePassword(password)) {
            return false;
        }

         */

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
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
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
    private static boolean validateUsername(String username) {
        // rule: username must be between 5 and 20 characters and can only contain letters, numbers, and underscores
        return username.matches("^[a-zA-Z0-9_]{5,20}$");
    }
    private static boolean validatePassword(String password) {
        // rules: password must be at least 8 characters, must contain at least one letter, one number, and one special character
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }
}
