package cesur.zakaria.pokemonprojectzakariafarih.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import cesur.zakaria.pokemonprojectzakariafarih.dbUtils.DBUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField passWord;

    @FXML
    protected void onSignUpButtonClick() {
        String username = userName.getText();
        String password = passWord.getText(); // In real applications, consider hashing the password before sending it to the database.

        // Validate input (optional, since you have validation in DBUtils)
        if (username.isEmpty() || password.isEmpty()) {
            // Show error message to user
            System.out.println("Username and password are required!");
            return;
        }

        // Attempt to register the user
        boolean success = DBUtils.registerUser(username, password);
        if (success) {
            System.out.println("Registration successful!");
            // Navigate to login page or show success message
        } else {
            // Show error message (e.g., user already exists or validation failed)
            System.out.println("Registration failed!");
        }
    }

    @FXML
    protected void onExitButtonClick(ActionEvent event) {
        // Get the current stage using the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }
    /*
    @FXML
    private ImageView logo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Image image = new Image("/images/zf_logo.png"); // Adjust the path as needed
        //logo.setImage(image);
    }

     */
}
