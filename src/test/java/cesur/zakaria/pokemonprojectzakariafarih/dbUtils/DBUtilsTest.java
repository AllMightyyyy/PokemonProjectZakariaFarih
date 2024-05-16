package cesur.zakaria.pokemonprojectzakariafarih.dbUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBUtilsTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccessful() throws Exception {
        // Arrange
        String username = "validUser";
        String hashedPassword = new BCryptPasswordEncoder().encode("correctPassword");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("hashed_password")).thenReturn(hashedPassword);
        when(mockResultSet.getInt("id")).thenReturn(1);

        try (MockedStatic<DriverManager> mocked = mockStatic(DriverManager.class)) {
            mocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            boolean result = DBUtils.login(username, "correctPassword");

            assertTrue(result, "Login should be successful");

            if (result) {
                System.out.println("User registration simulated successfully for: " + username);
            }
        }
    }

    @Test
    public void testLoginIncorrectPassword() throws Exception {
        // Arrange
        String username = "validUser";
        String hashedPassword = new BCryptPasswordEncoder().encode("correctPassword");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("hashed_password")).thenReturn(hashedPassword);

        try (MockedStatic<DriverManager> mocked = mockStatic(DriverManager.class)) {
            mocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act
            boolean result = DBUtils.login(username, "wrongPassword");

            // Assert
            assertFalse(result, "Login should fail with incorrect password");

            if(!result) {
                System.out.println("Login failed with incorrect password for: " + username);
            }

            if(result) {
                System.out.println("login success");
            }
        }
    }

    @Test
    public void testLoginNonexistentUsername() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        try (MockedStatic<DriverManager> mocked = mockStatic(DriverManager.class)) {
            mocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act
            boolean result = DBUtils.login("nonexistentUser", "somePassword");

            // Assert
            assertFalse(result, "Login should fail with nonexistent username");

            if(!result) {
                System.out.println("Login failed with nonexistent username");
            }
        }
    }

    @Test
    public void testLoginEmptyUsername() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        try (MockedStatic<DriverManager> mocked = mockStatic(DriverManager.class)) {
            mocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act
            boolean result = DBUtils.login("", "somePassword");

            // Assert
            assertFalse(result, "Login should fail with an empty username");

            System.out.println("Login failed with an empty username");
        }
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        // Arrange
        String username = "newUser";
        String hashedPassword = new BCryptPasswordEncoder().encode("securePassword");

        // Prepare statements for checking user existence and insertion
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(false);

        try (MockedStatic<DriverManager> mocked = mockStatic(DriverManager.class)) {
            mocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act
            boolean result = DBUtils.registerUser(username, "securePassword", "zakaria", "Doe", "Male");

            // Assert
            assertTrue(result, "User registration should be successful");

            if(result) {
                System.out.println("User registration successful");
            }
        }
    }

    @Test
    public void testRegisterUserDuplicateUsername() throws Exception {
        // Arrange
        String username = "existingUser";
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        try (MockedStatic<DriverManager> mocked = mockStatic(DriverManager.class)) {
            mocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Act
            boolean result = DBUtils.registerUser(username, "anotherPassword", "Jane", "Smith", "Female");

            // Assert
            assertFalse(result, "Registration should fail for a duplicate username");
        }
    }

    @Test
    public void testRegisterUserMissingRequiredFields() {
        // Arrange
        String username = "missingFieldsUser";

        // Act
        boolean result = DBUtils.registerUser(username, "", "", "Jones", "Male");

        // Assert
        assertFalse(result, "Registration should fail with missing required fields");
    }
}