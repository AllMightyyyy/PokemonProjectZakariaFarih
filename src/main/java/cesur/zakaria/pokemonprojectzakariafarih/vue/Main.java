package cesur.zakaria.pokemonprojectzakariafarih.vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.PokemonType;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            BorderPane root = FXMLLoader.load(getClass().getResource("interface.fxml"));
            Scene scene = new Scene(root);
            primaryStage.getIcons().add(new Image(new FileInputStream("Pictures/pokeball.png")));
            primaryStage.setScene(scene);
            primaryStage.setTitle("Pokemon - Zakaria Farih");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        PokemonType.generatePokemonType();
        launch(args);
    }
}