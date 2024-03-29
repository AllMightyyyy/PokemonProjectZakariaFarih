package cesur.zakaria.pokemonprojectzakariafarih.vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.PokemonType;

import java.io.IOException;

/**
 * Main class for launching the application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file for the interface
            BorderPane root = FXMLLoader.load(getClass().getResource("interface.fxml"));

            // Create a scene with the loaded interface
            Scene scene = new Scene(root);

            // Set the icon for the primary stage
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/pokeball.png")));

            // Set the scene for the primary stage
            primaryStage.setScene(scene);

            // Set the title for the primary stage
            primaryStage.setTitle("Pokemon - Zakaria Farih");

            // Make the primary stage not resizable
            primaryStage.setResizable(false);

            // Show the primary stage
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // Generate Pokemon types before launching the application
        PokemonType.generatePokemonType();

        // Launch the application
        launch(args);
    }
}
