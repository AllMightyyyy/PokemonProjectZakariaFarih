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

public class MainController {
	
	private AudioClip music;
	
    @FXML
    private Button launchFightButton;

    public void initialize() {
    }
    @FXML
    void launchFight(ActionEvent event) throws IOException {
    	MainView.changeScene((Stage)launchFightButton.getScene().getWindow(), "Fight.fxml");
    }
    
    @FXML
    void launchPokedex(ActionEvent event) throws IOException {
    	MainView.changeScene((Stage)launchFightButton.getScene().getWindow(), "Pokedex.fxml");
    }
}
