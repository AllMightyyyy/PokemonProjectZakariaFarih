package cesur.zakaria.pokemonprojectzakariafarih.pokelabGames.tictactoe;

import java.util.ArrayList;
import java.util.List;

import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import cesur.zakaria.pokemonprojectzakariafarih.vue.minigameLab;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import static cesur.zakaria.pokemonprojectzakariafarih.dbUtils.DBUtils.updatePlayerPoints;

/**
 * The type Tic tac toe app.
 */
public class TicTacToeApp extends Application {

    private boolean playable = true;
    private boolean turnX = true;
    Tile[][] board = new Tile[3][3];
    private List<Combo> combos = new ArrayList<>();

    private Pane root = new Pane();

    private Parent createContent() {
        root.setPrefSize(600, 600);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile();
                tile.setTranslateX(j * 200);
                tile.setTranslateY(i * 200);

                root.getChildren().add(tile);

                board[j][i] = tile;
            }
        }

        // horizontal
        for (int y = 0; y < 3; y++) {
            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));
        }

        // vertical
        for (int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));
        }

        // diagonals
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));

        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(combo);
                Player currentPlayer = AppState.getCurrentPlayer();
                if (currentPlayer != null) {
                    int winPoints = 5; // Points to add for winning
                    boolean updateSuccess = updatePlayerPoints(currentPlayer.getId(), winPoints);
                    if (updateSuccess) {
                        // Update AppState if needed
                        currentPlayer.setPoints(currentPlayer.getPoints() + winPoints);
                        AppState.setCurrentPlayer(currentPlayer);
                        // Now update the UI to reflect the new points total
                        minigameLab.updatePoints(currentPlayer.getPoints());

                        System.out.println("Points updated successfully!");
                    } else {
                        System.out.println("Failed to update points.");
                    }
                }
                break;
            }
        }
    }

    private void playWinAnimation(Combo combo) {
        Line line = new Line();
        line.setStartX(combo.tiles[0].getCenterX());
        line.setStartY(combo.tiles[0].getCenterY());
        line.setEndX(combo.tiles[0].getCenterX());
        line.setEndY(combo.tiles[0].getCenterY());

        root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), combo.tiles[2].getCenterX()),
                new KeyValue(line.endYProperty(), combo.tiles[2].getCenterY())));
        timeline.play();
    }

    class Combo {
        private Tile[] tiles;

        /**
         * Instantiates a new Combo.
         *
         * @param tiles the tiles
         */
        public Combo(Tile... tiles) {
            this.tiles = tiles;
        }

        /**
         * Is complete boolean.
         *
         * @return the boolean
         */
        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;

            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    class Tile extends StackPane {
        private Text text = new Text();

        /**
         * Instantiates a new Tile.
         */
        public Tile() {
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
                if (!playable)
                    return;

                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX)
                        return;

                    drawX();
                    turnX = false;
                    checkState();
                }
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX)
                        return;

                    drawO();
                    turnX = true;
                    checkState();
                }
            });
        }

        /**
         * Gets center x.
         *
         * @return the center x
         */
        public double getCenterX() {
            return getTranslateX() + 100;
        }

        /**
         * Gets center y.
         *
         * @return the center y
         */
        public double getCenterY() {
            return getTranslateY() + 100;
        }

        /**
         * Gets value.
         *
         * @return the value
         */
        public String getValue() {
            return text.getText();
        }

        void drawX() {
            text.setText("X");
        }

        void drawO() {
            text.setText("O");
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
