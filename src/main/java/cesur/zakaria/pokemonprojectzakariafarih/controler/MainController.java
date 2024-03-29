package cesur.zakaria.pokemonprojectzakariafarih.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * MainController class is the controller class for MainView.fxml.
 * It controls the main view of the application.
 */
public class MainController {

    /**
     * The music variable is used to play the background music.
     */
    private AudioClip music;

    /**
     * The launchFightButton is a button that launches the fight scene.
     */
    @FXML
    private Button launchFightButton;

    /**
     * This method is used to initialize the controller class.
     */
    public void initialize() {
    }

    /**
     * This method is called when the launchFightButton is clicked.
     * It changes the scene to the fight scene.
     * @param event the action event
     * @throws IOException if there is an error while changing the scene
     */
    @FXML
    void launchFight(ActionEvent event) throws IOException {
        MainView.changeScene((Stage)launchFightButton.getScene().getWindow(), "Fight.fxml");
    }

    /**
     * This method is called when the launchPokedexButton is clicked.
     * It changes the scene to the pokedex scene.
     * @param event the action event
     * @throws IOException if there is an error while changing the scene
     */
    @FXML
    void launchPokedex(ActionEvent event) throws IOException {
        MainView.changeScene((Stage)launchFightButton.getScene().getWindow(), "Pokedex.fxml");
    }
}