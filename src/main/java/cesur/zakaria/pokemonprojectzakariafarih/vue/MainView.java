package cesur.zakaria.pokemonprojectzakariafarih.vue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utility class for changing scenes in the JavaFX application.
 */
public class MainView {
	private static final MainView instance = new MainView();

	private MainView() {
	}

    /**
     * Changes the scene of the current stage to the scene loaded from the specified FXML file.
     *
     * @param currentStage The current stage whose scene will be changed.
     * @param fxml         The path to the FXML file for the new scene.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public static void changeScene(Stage currentStage, String fxml) throws IOException {
		currentStage.setScene(new Scene(FXMLLoader.load(instance.getClass().getResource(fxml))));
	}
}
