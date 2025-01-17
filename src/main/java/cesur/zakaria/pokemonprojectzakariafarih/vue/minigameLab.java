package cesur.zakaria.pokemonprojectzakariafarih.vue;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.PokemonType;
import cesur.zakaria.pokemonprojectzakariafarih.pokelabGames.NuMemoryPuzzle.NuMemoryApp;
import cesur.zakaria.pokemonprojectzakariafarih.pokelabGames.puzzle.Main;
import cesur.zakaria.pokemonprojectzakariafarih.pokelabGames.tictactoe.TicTacToeApp;
import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import cesur.zakaria.pokemonprojectzakariafarih.ui.menus.mainMenu.MenuApp;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Manages the mini-game laboratory interface of the Pokémon application, providing a hub for accessing
 * various mini-games such as puzzles, memory games, and Tic Tac Toe. This class extends JavaFX's Application
 * and leverages FXML for dynamic UI generation and interaction.
 */
public class minigameLab extends Application {

    private static Text pointsText = new Text("Points: 0");
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Generates the main content for the mini-game laboratory, arranging the mini-games and utilities
     * in a visually appealing layout against a themed background.
     *
     * @return The root Parent node containing the laboratory's UI components.
     */
    Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(1366, 768);
        Image bgImage = new Image("/cesur/zakaria/pokemonprojectzakariafarih/vue/minigameLab/pokemonlabbg.jpg",
                1366, 768,
                false, true
        );

        Node pointsDisplay = createPointsDisplay();


        VBox box = new VBox(
                5,
                new MenuItem("Evolve My Pokemons", () -> {
                    try {
                        launchEvolvePokemons();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }),
                new MenuItem("Puzzle", () -> {
                    try {
                        // Create a new Stage for the puzzle game
                        Stage puzzleStage = new Stage();
                        // Start the puzzle game in the new Stage
                        new Main().start(puzzleStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }),
                new MenuItem("Number Memory Puzzle", minigameLab::launchNuMemoryPuzzle),
                new MenuItem("Tictactoe", minigameLab::launchTicTacToeGame),
                new MenuItem("QUIT", minigameLab::launchFightScreen)
        );
        box.setBackground(new Background(
                new BackgroundFill(Color.web("black", 0.6), null, null)
        ));
        box.setTranslateX(1280 - 300);
        box.setTranslateY(720 - 300);

        root.getChildren().addAll(
                new ImageView(bgImage),
                box
        );
        root.getChildren().add(pointsDisplay);

        return root;
    }

    /**
     * Represents an interactive menu item for the mini-game laboratory interface.
     * Each menu item is associated with an action, typically launching a mini-game or utility.
     */
    private static class MenuItem extends StackPane {
        /**
         * Instantiates a new Menu item.
         *
         * @param name   the name
         * @param action the action
         */
        MenuItem(String name, Runnable action) {
            LinearGradient gradient = new LinearGradient(
                    0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0.1, Color.web("black", 0.75)),
                    new Stop(1.0, Color.web("black", 0.15))
            );
            Rectangle bg0 = new Rectangle(250, 30, gradient);
            Rectangle bg1 = new Rectangle(250, 30, Color.web("black", 0.2));

            FillTransition ft = new FillTransition(Duration.seconds(0.6),
                    bg1, Color.web("black", 0.2), Color.web("white", 0.3));

            ft.setAutoReverse(true);
            ft.setCycleCount(Integer.MAX_VALUE);

            hoverProperty().addListener((o, oldValue, isHovering) -> {
                if (isHovering) {
                    ft.playFromStart();
                } else {
                    ft.stop();
                    bg1.setFill(Color.web("black", 0.2));
                }
            });

            Rectangle line = new Rectangle(5, 30);
            line.widthProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(8).otherwise(5)
            );
            line.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.RED).otherwise(Color.GRAY)
            );

            Text text = new Text(name);
            text.setFont(Font.font(22.0));
            text.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.WHITE).otherwise(Color.GRAY)
            );

            setOnMouseClicked(e -> action.run());

            setOnMousePressed(e -> bg0.setFill(Color.LIGHTBLUE));

            setOnMouseReleased(e -> bg0.setFill(gradient));

            setAlignment(Pos.CENTER_LEFT);

            HBox box = new HBox(15, line, text);
            box.setAlignment(Pos.CENTER_LEFT);

            getChildren().addAll(bg0, bg1, box);
        }
    }
    /**
     * Creates a display for showing the current player's points. This display includes a semi-transparent
     * background with a frosted glass effect and updates dynamically to reflect the player's points total.
     *
     * @return A Node that can be added to the scene graph, representing the points display.
     */
    private Node createPointsDisplay() {
        // Semi-transparent background with rounded corners
        Rectangle bg = new Rectangle(200, 100);
        bg.setArcWidth(15.0);
        bg.setArcHeight(15.0);
        bg.setFill(Color.web("white", 0.5)); // Adjust opacity for glass effect

        // Apply a Gaussian blur to the background for the frosted glass effect
        GaussianBlur blur = new GaussianBlur(10); // Adjust the radius to your liking
        bg.setEffect(blur);

        Player currentPlayer = AppState.getCurrentPlayer();
        // Use the current player's points to initialize the pointsText, if available
        pointsText = new Text("Points: " + (currentPlayer != null ? currentPlayer.getPoints() : "0"));
        pointsText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        pointsText.setFill(Color.BLACK);

        // Adding a drop shadow to the text for better readability
        DropShadow textShadow = new DropShadow();
        textShadow.setOffsetX(2.0);
        textShadow.setOffsetY(2.0);
        textShadow.setColor(Color.color(0.1, 0.1, 0.1, 0.7));
        pointsText.setEffect(textShadow);

        // StackPane to center the text on the rectangle
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(bg, pointsText);
        stackPane.setLayoutX(20); // Margin from the top left corner
        stackPane.setLayoutY(20);

        // Optional: Apply an additional drop shadow to the whole stack pane for more depth
        DropShadow paneShadow = new DropShadow();
        paneShadow.setOffsetX(5.0);
        paneShadow.setOffsetY(5.0);
        paneShadow.setColor(Color.color(0, 0, 0, 0.5));
        stackPane.setEffect(paneShadow);

        return stackPane;
    }

    /**
     * Launches the Number Memory Puzzle game in a new Stage.
     */
    private static void launchNuMemoryPuzzle() {
        Platform.runLater(() -> {
            try {
                // Create a new Stage for the Snake game
                Stage NuMemoryPuzzleStage = new Stage();
                // Start the memory game in the new Stage
                NuMemoryApp memoryGame = new NuMemoryApp();
                memoryGame.start(NuMemoryPuzzleStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * Launches the Tic Tac Toe game in a new Stage.
     */
    private static void launchTicTacToeGame() {
        Platform.runLater(() -> {
            try {
                // Create a new Stage for the Snake game
                Stage TicTacToeStage = new Stage();
                // Start the memory game in the new Stage
                TicTacToeApp ticTacToeGame = new TicTacToeApp();
                ticTacToeGame.start(TicTacToeStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Closes the current stage and launches the main game interface.
     */
    private static void launchFightScreen() {
        Stage currentStage = (Stage) pointsText.getScene().getWindow();
        currentStage.hide();

        // Launch the JavaFX game
        Platform.runLater(() -> {
            PokemonType.generatePokemonType();
            Stage gameStage = new Stage();
            interfaceMenu mainApp = new interfaceMenu();
            try {
                mainApp.start(gameStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Launches the Pokémon evolution screen.
     *
     * @throws IOException If the FXML file for the evolution screen cannot be loaded.
     */
    public static void launchEvolvePokemons() throws IOException {
        FXMLLoader loader = new FXMLLoader(minigameLab.class.getResource("evolvePokemon.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pokémon Center");
        stage.show();
    }

    // Method to update points somewhere in your class

    /**
     * Updates the points display with a new total.
     *
     * @param newPoints The new points total to display.
     */
    public static void updatePoints(int newPoints) {
        Platform.runLater(() -> pointsText.setText("Points: " + newPoints));
    }

}
