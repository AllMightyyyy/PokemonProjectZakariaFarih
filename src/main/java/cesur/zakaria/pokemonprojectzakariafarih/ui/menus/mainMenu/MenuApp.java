package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.mainMenu;

import cesur.zakaria.pokemonprojectzakariafarih.cardGui.GameWindow;
import cesur.zakaria.pokemonprojectzakariafarih.libGDXgame.LibGdxGame;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.PokemonType;
import cesur.zakaria.pokemonprojectzakariafarih.vue.interfaceMenu;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the main menu of the Pokemon application.
 */
public class MenuApp {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static Stage primaryStage;

    /**
     * Constructs a new MenuApp with the specified primary stage.
     *
     * @param primaryStage The primary stage of the application.
     */
    public MenuApp(Stage primaryStage) {
        MenuApp.primaryStage = primaryStage;
    }

    // Menu data
    private static final List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Enter World", MenuApp::launchEnterWorld),
            new Pair<String, Runnable>("Fight", MenuApp::launchEnterFight),
            new Pair<String, Runnable>("Card Game", MenuApp::launchCardGame),
            new Pair<String, Runnable>("Pokemon Center", MenuApp::launchPokemonCenter),
            new Pair<String, Runnable>("Pokédex", MenuApp::launchPokedex),
            new Pair<String, Runnable>("Credits", MenuApp::launchCreditsScreen),
            new Pair<String, Runnable>("Exit to Desktop", Platform::exit)
    );

    private static final Pane root = new Pane();
    private static final VBox menuBox = new VBox(-5);
    private static Line line;

    /**
     * Creates the content of the main menu.
     * @return The parent node containing the main menu content.
     */
    private static Parent createContent() {
        addBackground();
        addTitle();

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;

        addLine(lineX, lineY);
        addMenu(lineX + 5, lineY + 5);

        startAnimation();

        return root;
    }

    private static void addBackground() {
        ImageView imageView = new ImageView(new Image(MenuApp.class.getResource("res/pokMap.jpg").toExternalForm()));
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        root.getChildren().add(imageView);
    }

    private static void addTitle() {
        MenuTitle title = new MenuTitle("POKEMON TGC");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }

    private static void addLine(double x, double y) {
        line = new Line(x, y, x, y + 300);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

    private static void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    private static void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        root.getChildren().add(menuBox);
    }

    /**
     * Switches to the main menu.
     *
     * @param stage The primary stage of the application.
     */
    public static void switchToMainmenu(Stage stage) {
        MenuApp menuApp = new MenuApp(stage); // Create an instance passing the stage
        Scene scene = new Scene(createContent(), WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Pokemon App - Made by Zakaria Farih");
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - WIDTH) / 2);
        stage.setY((screenBounds.getHeight() - HEIGHT) / 2);
        stage.show();
    }

    private static void launchCreditsScreen() {
        SwingUtilities.invokeLater(cesur.zakaria.pokemonprojectzakariafarih.ui.menus.credits.Screen::new);
    }

    private static void launchCardGame() {
        // Close the current menu window
        primaryStage.hide();

        // Launch the card game using the primary stage
        Platform.runLater(() -> {
            GameWindow gameWindow = new GameWindow();
            gameWindow.start(primaryStage);
        });
    }

    private static void launchEnterFight() {
        // Close the current menu window
        primaryStage.hide();

        // Launch the JavaFX game
        Platform.runLater(() -> {
            try {
                PokemonType.generatePokemonType();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage gameStage = new Stage();
            interfaceMenu mainApp = new interfaceMenu();
            try {
                mainApp.start(gameStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void launchPokedex() {
        SwingUtilities.invokeLater(() -> {
            cesur.zakaria.pokemonprojectzakariafarih.pokedex.view.Dashboard dashboard = new cesur.zakaria.pokemonprojectzakariafarih.pokedex.view.Dashboard();
            dashboard.setVisible(true);
        });
    }

    private static void launchPokemonCenter() {
        try {
            // Load the FXML file for the Pokémon Center
            FXMLLoader loader = new FXMLLoader(MenuApp.class.getResource("TeamPlanner.fxml"));
            Parent root = loader.load();

            // Create a new stage for the Pokémon Center
            Stage stage = new Stage();
            stage.setWidth(1100);
            stage.setHeight(1000);
            stage.initModality(Modality.APPLICATION_MODAL); // Makes the window modal if desired

            // Add custom drag handling here if you want to make the window draggable

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Pokémon Center");

            // Handle closing the window
            stage.setOnCloseRequest(event -> {
                // Optionally switch back to the main menu or do other cleanup
                primaryStage.show(); // Show the main menu again if it was hidden
            });

            // Show the Pokémon Center window
            stage.show();

            // Optionally hide the main menu
            primaryStage.hide();
        } catch (IOException e) {
            e.printStackTrace(); // Properly handle the exception
        }
    }

    private static void launchEnterWorld() {
        new Thread(() -> {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            //Set your configurations options
            config.width = 608;
            config.height = 480;
            config.title = "Zakaria Farih's Pokemon Game";
            config.resizable = false;

            new LwjglApplication(new LibGdxGame(), config);
        }).start();

        //Optionally hide or close the JavaFX window
        Platform.runLater(() -> primaryStage.hide());
    }
}
