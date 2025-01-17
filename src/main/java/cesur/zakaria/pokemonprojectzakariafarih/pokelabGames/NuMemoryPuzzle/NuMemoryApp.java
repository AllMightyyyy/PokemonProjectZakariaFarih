package cesur.zakaria.pokemonprojectzakariafarih.pokelabGames.NuMemoryPuzzle;

import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import cesur.zakaria.pokemonprojectzakariafarih.vue.minigameLab;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static cesur.zakaria.pokemonprojectzakariafarih.dbUtils.DBUtils.updatePlayerPoints;

/**
 * The type Nu memory app.
 */
public class NuMemoryApp extends Application {

    private static final long DURATION_SECONDS = 6;

    private ScheduledExecutorService timerThread
            = Executors.newSingleThreadScheduledExecutor();

    private VBox root;
    private Pane tilePane;
    private List<TileView> tileSequence = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        timerThread.shutdownNow();
    }

    private Parent createContent() {
        root = new VBox();
        root.setPrefSize(1280, 720 + 100);

        var button = new Button("Start");
        button.setOnAction(e -> startGame());

        root.getChildren().addAll(new Pane(), button);

        return root;
    }

    private void startGame() {
        tilePane = populateGrid();

        root.getChildren().set(0, tilePane);

        timerThread.schedule(() -> {
            tilePane.getChildren()
                    .stream()
                    .map(n -> (TileView) n)
                    .forEach(TileView::hide);

        }, DURATION_SECONDS, TimeUnit.SECONDS);
    }

    private Pane populateGrid() {
        var pane = new Pane();
        pane.setPrefSize(1280, 720);

        Random random = new Random();

        List<Point2D> usedPoints = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            int randomX = random.nextInt(1280 / 80);
            int randomY = random.nextInt(720 / 80);

            Point2D p = new Point2D(randomX, randomY);

            while (usedPoints.contains(p)) {
                randomX = random.nextInt(1280 / 80);
                randomY = random.nextInt(720 / 80);

                p = new Point2D(randomX, randomY);
            }

            usedPoints.add(p);

            var tile = new TileView(Integer.toString(i));
            tile.setTranslateX(randomX * 80);
            tile.setTranslateY(randomY * 80);
            tile.setOnMouseClicked(e -> {
                if (tileSequence.isEmpty()) {
                    System.out.println("Game is already over");
                    return;
                }

                var correctTile = tileSequence.remove(0);

                if (tile == correctTile) {
                    tile.show();
                    if (tileSequence.isEmpty()) {
                        System.out.println("Success: game completed");
                        Player currentPlayer = AppState.getCurrentPlayer();
                        if (currentPlayer != null) {
                            int winPoints = 20; // Points to add for winning
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
                    }
                } else {
                    tileSequence.clear();
                    System.out.println("Fail: game over");
                }
            });

            pane.getChildren().add(tile);
            tileSequence.add(tile);
        }

        return pane;
    }

    private static class TileView extends StackPane {

        private Text text;

        /**
         * Instantiates a new Tile view.
         *
         * @param content the content
         */
        TileView(String content) {
            var border = new Rectangle(80, 80, null);
            border.setStroke(Color.BLACK);
            border.setStrokeWidth(4);
            border.setStrokeType(StrokeType.INSIDE);

            text = new Text(content);
            text.setFont(Font.font(64));

            getChildren().addAll(border, text);

            setPickOnBounds(true);
        }

        /**
         * Hide.
         */
        void hide() {
            text.setVisible(false);
        }

        /**
         * Show.
         */
        void show() {
            text.setVisible(true);
        }
    }

    public List<TileView> getTileSequence() {
        return this.tileSequence;
    }

    public void setTileSequence(List<TileView> tileSequence) {
        this.tileSequence = tileSequence;
    }

    public Pane getTilePane() {
        return this.tilePane;
    }

    public void setTilePane(Pane tilePane) {
        this.tilePane = tilePane;
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
