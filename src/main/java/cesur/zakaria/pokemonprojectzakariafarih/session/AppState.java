package cesur.zakaria.pokemonprojectzakariafarih.session;

public class AppState {
    private static Player currentPlayer;

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
}
