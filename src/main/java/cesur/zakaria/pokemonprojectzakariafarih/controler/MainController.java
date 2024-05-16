package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;
import cesur.zakaria.pokemonprojectzakariafarih.vue.mainLoading;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls the main application view defined in MainView.fxml. This controller handles user interactions
 * from the main menu, including navigation to different parts of the application such as the fight scene,
 * the Pokedex, and the Pokemon Lab. It also manages background music playback.
 */
public class MainController {

    /**
     * Background music player for the application's main view. It allows playing, pausing, or stopping
     * background music to enhance user experience.
     */
    private AudioClip music;

    /**
     * Button to navigate to the fight scene. When clicked, this button triggers a scene change to
     * display the fight interface where users can engage in battles.
     */
    @FXML
    private Button launchFightButton;

    /**
     * Button to navigate to the Pokemon Lab. When clicked, this button triggers a scene change or
     * another form of display (e.g., loading screen) to present the Pokemon Lab interface where users
     * can interact with various aspects of their Pokemon collection.
     */
    @FXML
    private Button launchPokemonLab;

    /**
     * Initializes the controller after its root element has been completely processed. This method
     * is called automatically during the application startup and is used to set up initial states or
     * perform any necessary configuration for the controller.
     */
    public void initialize() {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles the action of launching the fight scene. This method is triggered when the user clicks
     * the "Launch Fight" button. It switches the current view to the fight scene, allowing the user to
     * start a new battle.
     *
     * @param ignoredEvent The action event associated with the button click. Not used in this method.
     * @throws IOException if there is an error changing the scene, such as a missing FXML file.
     */
    @FXML
    void launchFight(ActionEvent ignoredEvent) throws IOException {
        MainView.changeScene((Stage)launchFightButton.getScene().getWindow(), "Fight.fxml");
    }

    /**
     * Handles the action of launching the Pokedex scene. This method is triggered when the user clicks
     * the button intended for Pokedex navigation (button not shown in the provided code but presumably
     * existing in the FXML and/or the controller). It switches the current view to the Pokedex scene,
     * allowing the user to view their Pokemon collection.
     *
     * @param ignoredEvent The action event associated with the button click. Not used in this method.
     * @throws IOException if there is an error changing the scene, such as a missing FXML file.
     */
    @FXML
    void launchPokedex(ActionEvent ignoredEvent) throws IOException {
        MainView.changeScene((Stage) launchFightButton.getScene().getWindow(), "Pokedex.fxml");
    }

    /**
     * Handles the action of launching the Pokemon Lab. This method is triggered when the user clicks
     * the "Launch Pokemon Lab" button. It displays the Pokemon Lab interface, potentially through a
     * loading screen, where users can access more detailed interactions with their Pokemon.
     *
     * @param ignoredEvent The action event associated with the button click. Not used in this method.
     * @throws IOException if there is an error initiating the Pokemon Lab view.
     */
    @FXML
    void launchPokemonLab(ActionEvent ignoredEvent) throws IOException {
        mainLoading loadingScreen = new mainLoading();
        Stage stage = (Stage) launchPokemonLab.getScene().getWindow(); // Reuses the existing window.
        try {
            loadingScreen.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the current AudioClip instance being used for background music.
     *
     * @return The AudioClip instance representing the current background music.
     */
    public AudioClip getMusic() {
        return music;
    }

    /**
     * Sets the AudioClip instance to be used for background music in the application's main view.
     *
     * @param music The new AudioClip instance to use for background music.
     */
    public void setMusic(AudioClip music) {
        this.music = music;
    }
}