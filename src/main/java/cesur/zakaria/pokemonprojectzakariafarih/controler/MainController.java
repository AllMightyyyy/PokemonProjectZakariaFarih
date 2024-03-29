package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;

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
     *
     * @param ignoredEvent the action event
     * @throws IOException if there is an error while changing the scene
     */
    @FXML
    void launchFight(ActionEvent ignoredEvent) throws IOException {
        MainView.changeScene((Stage)launchFightButton.getScene().getWindow(), "Fight.fxml");
    }

    /**
     * This method is called when the launchPokedexButton is clicked.
     * It changes the scene to the pokedex scene.
     *
     * @param ignoredEvent the action event
     * @throws IOException if there is an error while changing the scene
     */
    @FXML
    void launchPokedex(ActionEvent ignoredEvent) throws IOException {
        MainView.changeScene((Stage)launchFightButton.getScene().getWindow(), "Pokedex.fxml");
    }

    /**
     * Gets music.
     *
     * @return the music
     */
    public AudioClip getMusic() {
        return music;
    }

    /**
     * Sets music.
     *
     * @param music the music
     */
    public void setMusic(AudioClip music) {
        this.music = music;
    }
}