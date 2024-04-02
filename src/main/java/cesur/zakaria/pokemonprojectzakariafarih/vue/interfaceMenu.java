package cesur.zakaria.pokemonprojectzakariafarih.vue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokedex.Pokedex;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

import static cesur.zakaria.pokemonprojectzakariafarih.vue.MainView.changeScene;

public class interfaceMenu extends Application implements Initializable {
    private boolean pokedexGenerated = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!pokedexGenerated) {
            generatePokedex();
            pokedexGenerated = true;
        }
    }
    private void generatePokedex() {
        try {
            Pokedex.getPokedex(); // Generate the Pokedex
        } catch (IOException e) {
            // Handle the exception if necessary
            e.printStackTrace();
            showAlert("Error", "Failed to generate Pokedex");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(1366, 768);

        Image bgImage = new Image("/cesur/zakaria/pokemonprojectzakariafarih/vue/interfaceMenu/interfacebg.jpg");
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitHeight(768);
        bgImageView.setFitWidth(1366);
        root.getChildren().add(bgImageView);

        Title title = new Title("P O K E M O N");
        title.setTranslateX(75);
        title.setTranslateY(200);

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnMouseClicked(event -> System.exit(0));

        MenuItem fight = new MenuItem("FIGHT");
        fight.setOnMouseClicked(event -> {
            try {
                changeScene((Stage) root.getScene().getWindow(), "Fight.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem pokedex = new MenuItem("POKEDEX");
        pokedex.setOnMouseClicked(event -> {
            try {
                changeScene((Stage) root.getScene().getWindow(), "Pokedex.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem pokemonLab = new MenuItem("POKEMON LAB");
        pokemonLab.setOnMouseClicked(event -> {
            mainLoading loadingScreen = new mainLoading();
            Stage stage = (Stage) root.getScene().getWindow(); // Reuses the existing window.
            try {
                loadingScreen.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        MenuBox menu = new MenuBox(
                fight,
                pokedex,
                pokemonLab,
                itemExit);
        menu.setTranslateX(100);
        menu.setTranslateY(300);

        root.getChildren().addAll(title, menu);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Pokemon - Zakaria Farih");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private static class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(250, 60);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 50));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

    private static class MenuBox extends VBox {
        public MenuBox(MenuItem... items) {
            getChildren().add(createSeparator());

            for (MenuItem item : items) {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(200);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }
    }

    private static class MenuItem extends StackPane {
        public MenuItem(String name) {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.DARKVIOLET),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.DARKVIOLET)
            });

            Rectangle bg = new Rectangle(200, 30);
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 22));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });


            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
            });

            setOnMousePressed(event -> {
                bg.setFill(Color.DARKVIOLET);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });
        }
    }
}
