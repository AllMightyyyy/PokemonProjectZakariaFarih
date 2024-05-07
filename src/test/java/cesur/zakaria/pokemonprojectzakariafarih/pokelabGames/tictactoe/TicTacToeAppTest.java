package cesur.zakaria.pokemonprojectzakariafarih.pokelabGames.tictactoe;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for TicTacToeApp.
 */
public class TicTacToeAppTest {
    private TicTacToeApp app;

    @BeforeEach
    public void setUp() {
        app = new TicTacToeApp();
    }

    /**
     * Tests Tile behavior.
     */
    @Test
    public void testTile() {
        TicTacToeApp.Tile tile = app.new Tile();
        assertTrue(tile.getValue().isEmpty(), "New tile should be empty initially.");

        tile.setOnMouseClicked(event -> tile.drawX());
        tile.drawX();
        assertEquals("X", tile.getValue(), "Tile should show X after drawX().");

        tile.drawO();
        assertEquals("O", tile.getValue(), "Tile should show O after drawO().");
    }

    /**
     * Tests Combo behavior.
     */
    @Test
    public void testCombo() {
        TicTacToeApp.Tile tile1 = app.new Tile();
        TicTacToeApp.Tile tile2 = app.new Tile();
        TicTacToeApp.Tile tile3 = app.new Tile();
        TicTacToeApp.Combo combo = app.new Combo(tile1, tile2, tile3);

        assertFalse(combo.isComplete(), "Initial combo should not be complete.");

        tile1.drawX();
        tile2.drawX();
        tile3.drawX();
        assertTrue(combo.isComplete(), "Combo with three Xs should be complete.");

        tile3.drawO();
        assertFalse(combo.isComplete(), "Combo with mixed values should not be complete.");
    }

    /**
     * Tests if state checking correctly identifies the winning state.
     */
    @Test
    public void testCheckState() throws Exception {

        Platform.runLater(() -> {
            try {
                app.start(null); // Or pass a valid Stage mock if required
                // Your testing logic here
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Simulate filling a winning combo
        TicTacToeApp.Tile[][] board = {
                { app.new Tile(), app.new Tile(), app.new Tile() },
                { app.new Tile(), app.new Tile(), app.new Tile() },
                { app.new Tile(), app.new Tile(), app.new Tile() }
        };

        app.board = board;
        TicTacToeApp.Combo winningCombo = app.new Combo(board[0][0], board[0][1], board[0][2]);
        board[0][0].drawX();
        board[0][1].drawX();
        board[0][2].drawX();
        assertTrue(winningCombo.isComplete(), "A filled combo should be complete.");
    }
}
