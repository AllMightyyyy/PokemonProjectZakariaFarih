package cesur.zakaria.pokemonprojectzakariafarih.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Bot;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.League;
import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This class is the controller for the Personnal League scene.
 * It allows the user to create and save a personal league.
 */
public class PersonnalLeagueController {

    @FXML
    private BorderPane root;

    @FXML
    private TextField label;


    private static ArrayList<Bot> trainers=new ArrayList<Bot>();

    /**
     * This method initializes the controller.
     * If the create league mode is on, it adds the current bot to the list of trainers.
     * Then, it creates a VBox containing the labels of the trainers, and sets it as the center of the root.
     * @throws FileNotFoundException if the file containing the trainers cannot be found.
     */
    public void initialize() throws FileNotFoundException {

        if(!GameControllerStatic.getGameControllerStatic().isCreateLeagueOn()){

            System.out.println("Patate");
            GameControllerStatic.getGameControllerStatic().setCreateLeagueOn(true);

        }else {
            PersonnalLeagueController.trainers.add(GameControllerStatic.getGameControllerStatic().getBot());

        }
        VBox vBox=new VBox();
        for (Bot bot : trainers) {
            Label label =new Label(bot.getName());
            label.setFont(new Font(20));
            vBox.getChildren().add(label);
        }
        vBox.setAlignment(Pos.CENTER);

        vBox.setPrefSize(243, 285);
        root.setCenter(vBox);
        BorderPane.setAlignment(vBox, Pos.CENTER);

    }

    /**
     * This method saves the list of trainers in a file with the given name.
     * The file is saved in the "League" directory.
     * @param event the action event
     * @throws FileNotFoundException if the file cannot be found.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    void Save(ActionEvent event) throws FileNotFoundException, IOException {
        ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream("League/"+label.getText()+".league")) ;
        oos.writeObject(new League(PersonnalLeagueController.trainers));
        oos.close();
    }

    /**
     * This method switches to the Pokedex scene.
     * @param event the action event
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    void Add(ActionEvent event) throws IOException {
        MainView.changeScene((Stage)root.getScene().getWindow(), "Pokedex.fxml");
    }

    /**
     * This method switches to the League scene.
     * @param event the action event
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    void Menu(ActionEvent event) throws IOException {
        GameControllerStatic.getGameControllerStatic().setCreateLeagueOn(false);
        MainView.changeScene((Stage)root.getScene().getWindow(), "League.fxml");
    }
}