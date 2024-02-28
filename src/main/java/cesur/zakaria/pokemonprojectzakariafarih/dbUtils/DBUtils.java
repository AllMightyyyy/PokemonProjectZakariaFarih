package cesur.zakaria.pokemonprojectzakariafarih.dbUtils;

import java.sql.*;

public class DBUtils {
    public static boolean login (String username, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //establish the connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "root", "27122000@ziko");
            //prepare statement to execute sql query
            String sql = "SELECT * FROM pokemonplayer WHERE username = ? AND hashed_password = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            //execute query
            rs = ps.executeQuery();
            //check if we got any results
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    public static boolean registerUser(String username, String password) {
        //validation rules
        if (!validateUsername(username) || !validatePassword(password)) {
            return false;
        }
        Connection connection = null;
        PreparedStatement ps = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemondb", "zakaria", "27122000@ziko");
            //check if user exists
            psCheckUserExists = connection.prepareStatement("SELECT * FROM pokemonplayer WHERE username = ?");
            psCheckUserExists.setString(1, username);
            rs = psCheckUserExists.executeQuery();
            if(rs.next()) {
                //user already exists
                return false;
            } else {
                String sqlInsert = "INSERT INTO pokemonplayer (username, hashed_password) VALUES (?, ?)";
                ps = connection .prepareStatement(sqlInsert);
                ps.setString(1, username);
                ps.setString(2, password);
                int result = ps.executeUpdate();
                //if insert is success, result should be 1
                return result == 1;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
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
