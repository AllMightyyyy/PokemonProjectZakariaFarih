package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.model.fight.League;
import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

/**
 * The type League controller.
 */
public class LeagueController {

    /**
     * The root layout of the scene
     */
    @FXML
    private BorderPane root;

    /**
     * The league object that is being managed by this controller
     */
    private League league;

    /**
     * The index of the last bot that was selected in the league
     */
    private static int lastFightIndex = 0;

    /**
     * A boolean indicating whether the main menu is being shown or not
     */
    private boolean mainMenu = true;

    /**
     * Initializes the controller by checking if the league is already loaded and showing the appropriate view
     */
    public void initialize() {
        if (GameControllerStatic.getGameControllerStatic().isLeagueison()) {
            mainMenu = false;
            lauchLeague(null);
        }
    }

    /**
     * Launches the league view, creating a new league if it does not already exist
     *
     * @param ignoredEvent the event that triggered this method
     */
    @FXML
    void lauchLeague(ActionEvent ignoredEvent) {
        if (!GameControllerStatic.getGameControllerStatic().isLeagueison()) {
            try {
                league = League.createDefaultLeague();
            } catch (IOException e) {
                e.printStackTrace();
            }

            GameControllerStatic.getGameControllerStatic().setLeagueison(true);
            GameControllerStatic.getGameControllerStatic().setLeague(league);
        } else {
            league = GameControllerStatic.getGameControllerStatic().getLeague();
        }

        HBox hBox = new HBox();
        int i = 0;
        int j = league.getActualBot();
        if (lastFightIndex == j) {
            league.getBot(j).restoreTrainer();
        }
        lastFightIndex = j;
        for (var trainer : league.getBots()) {

            BotButton button = new BotButton(trainer);
            if (i!= j) {
                button.setDisable(true);
            }
            button.setOnAction(this.event);
            hBox.getChildren().add(button);
            i++;
        }

        root.setCenter(hBox);
    }

    /**
     * The event handler for when a bot button is clicked
     */
    private final EventHandler<ActionEvent> event = new EventHandler<>() {

        @Override
        public void handle(ActionEvent e) {
            GameControllerStatic.getGameControllerStatic().setBot(((BotButton) e.getSource()).getTrainer());

            try {
                MainView.changeScene((Stage) root.getScene().getWindow(), "Fight.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    };

    /**
     * Creates a new personal league
     *
     * @param ignoredEvent the event that triggered this method
     */
    @FXML
    void createPersonnalLeague(ActionEvent ignoredEvent) {
        try {
            MainView.changeScene((Stage) root.getScene().getWindow(), "PersonalLeague.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Launches the personal league view, showing a list of available leagues
     *
     * @param ignoredEvent the event that triggered this method
     */
    @FXML
    void lauchPersonnalLeague(ActionEvent ignoredEvent) {

        HBox hBox = new HBox();
        String repertoire = "League";
        File dir = new File("League");
        if (dir.isDirectory()) {
            String[] s = dir.list();
            for (int i = 0; i < Objects.requireNonNull(s).length; i++) {

                File f = new File(repertoire + s[i]);
                String filename = f.getName();
                int j = filename.lastIndexOf('.');
                if (i > 0 && i < filename.length() - 1) {
                    if (filename.substring(j + 1).equalsIgnoreCase("league")) {
                        LeagueButton button = new LeagueButton(s[i]);
                        button.setOnAction(eventUnsave);
                        hBox.getChildren().add(button);
                    }
                }

            }
        }
        root.setCenter(hBox);
    }

    /**
     * The event handler for when a league button is clicked
     */
    private final EventHandler<ActionEvent> eventUnsave = new EventHandler<>() {

        @Override
        public void handle(ActionEvent event) {
            String fileString = ((LeagueButton) event.getSource()).getLeagueNameString();
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(new FileInputStream("League/" + fileString));
                System.out.println(ois);
                GameControllerStatic.getGameControllerStatic().setLeague((League) ois.readObject());
                System.out.println(league);
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            GameControllerStatic.getGameControllerStatic().setLeagueison(true);
            initialize();
        }
    };

    /**
     * Shows the main menu view
     *
     * @param ignoredEvent the event that triggered this method
     */
    @FXML
    void Menu(ActionEvent ignoredEvent) {
        GameControllerStatic.getGameControllerStatic().setLeagueison(false);
        try {
            if (mainMenu) {
                MainView.changeScene((Stage) root.getScene().getWindow(), "interface.fxml");
            } else {
                MainView.changeScene((Stage) root.getScene().getWindow(), "League.fxml");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
