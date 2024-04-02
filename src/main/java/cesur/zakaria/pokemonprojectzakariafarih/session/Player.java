package cesur.zakaria.pokemonprojectzakariafarih.session;

/**
 * Represents a player in the application. This class holds information about the player, including their unique
 * identifier, username, and points accumulated throughout the game. It provides a foundation for tracking player
 * progress and attributes, facilitating various game functionalities such as scoring, identification, and session management.
 */
public class Player {
    /**
     * Unique identifier for the player. This is typically used within the database and application logic
     * to uniquely identify and manage player data.
     */
    private int id;

    /**
     * The username chosen by the player. This is used for player identification within the game's user interface
     * and potentially in multiplayer or community features.
     */
    private String username;

    /**
     * The current point total for the player. Points can be earned through gameplay and are used to track the
     * player's progress, achievements, or for in-game transactions and upgrades.
     */
    private int points;

    /**
     * Constructs a new Player instance with the specified id, username, and points.
     *
     * @param id       The unique identifier for the player.
     * @param username The username of the player.
     * @param points   The initial point total for the player.
     */
    public Player(int id, String username, int points) {
        this.id = id;
        this.username = username;
        this.points = points;
    }

    // Getters and Setters

    /**
     * Gets the player's unique identifier.
     *
     * @return The unique identifier for the player.
     */
    public int getId() { return id; }

    /**
     * Gets the player's username.
     *
     * @return The username of the player.
     */
    public String getUsername() { return username; }

    /**
     * Gets the current point total for the player.
     *
     * @return The player's current points.
     */
    public int getPoints() { return points; }

    /**
     * Sets the player's point total to the specified value.
     * This method can be used to update the player's points based on game events or transactions.
     *
     * @param points The new point total for the player.
     */
    public void setPoints(int points) { this.points = points; }
}
