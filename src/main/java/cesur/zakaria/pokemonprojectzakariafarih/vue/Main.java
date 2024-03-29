package cesur.zakaria.pokemonprojectzakariafarih.vue;

import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.PokemonType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        // Generate Pokemon types before launching the application
        PokemonType.generatePokemonType();

        // Launch the application
        launch(args);
    }
}
