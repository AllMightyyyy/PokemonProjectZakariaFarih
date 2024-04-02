package cesur.zakaria.pokemonprojectzakariafarih.session;

/**
 * Manages the application state by storing and providing access to the current player's session.
 * The AppState class serves as a global access point to the currently active player object, allowing
 * various components of the application to query or modify the player's state as needed throughout the user's session.
 */
public class AppState {
    /**
     * Holds a reference to the current player session. This static field ensures that the player's
     * state is accessible application-wide, maintaining a consistent state across different components.
     */
    private static Player currentPlayer;

    /**
     * Sets the current player session to the specified Player object. This method updates the
     * application's state to reflect the active player, making it available for other parts of the
     * application to access and interact with the player's data.
     *
     * @param player The Player object representing the current player. This object contains
     *               all relevant information about the player's session, such as their identity,
     *               game progress, and other relevant state information.
     */
    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    /**
     * Returns the currently active player session. This method provides access to the Player object
     * representing the user currently interacting with the application, allowing various components
     * of the application to query and use the player's data.
     *
     * @return The current Player object, containing all necessary data about the player's session.
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
}
