package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.forms;

import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwingToJavaFXBridge {

    // This static block ensures that JavaFX runtime is initialized.
    static {
        // This line is crucial. It initializes the JavaFX environment.
        new JFXPanel();
    }

    public static void showSignUpWindow() {
        // Ensure that the following JavaFX code runs on the JavaFX Application Thread
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(SwingToJavaFXBridge.class.getResource("fxml/sign-up.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Sign Up");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
