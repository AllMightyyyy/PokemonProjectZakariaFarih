package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.dbUtils.DBUtils;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;
import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import cesur.zakaria.pokemonprojectzakariafarih.vue.minigameLab;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Controls the Pokemon evolution interface within the Pokemon Project application.
 * This class is responsible for managing the user interactions for evolving Pokemon,
 * including stat allocation using available points and updating Pokemon stats.
 */
public class PokemonEvolveController {
    private static final int MAX_HP = 225;
    private static final int MAX_ATTACK = 190;
    private static final int MAX_DEFENSE = 250;

    // FXML Bindings
    @FXML
    ImageView imageView1;
    @FXML
    ImageView imageView2;
    @FXML
    ImageView imageView3;
    @FXML
    ImageView imageView4;
    @FXML
    ImageView imageView5;
    @FXML
    ImageView imageView6;
    @FXML
    Spinner<Integer> HPSpinner1;
    @FXML
    Spinner<Integer> AttSpinner1;
    @FXML
    Spinner<Integer> DefSpinner1;
    @FXML
    Spinner<Integer> HPSpinner2;
    @FXML
    Spinner<Integer> AttSpinner2;
    @FXML
    Spinner<Integer> DefSpinner2;
    @FXML
    Spinner<Integer> HPSpinner3;
    @FXML
    Spinner<Integer> AttSpinner3;
    @FXML
    Spinner<Integer> DefSpinner3;
    @FXML
    Spinner<Integer> HPSpinner4;
    @FXML
    Spinner<Integer> AttSpinner4;
    @FXML
    Spinner<Integer> DefSpinner4;
    @FXML
    Spinner<Integer> HPSpinner5;
    @FXML
    Spinner<Integer> AttSpinner5;
    @FXML
    Spinner<Integer> DefSpinner5;
    @FXML
    Spinner<Integer> HPSpinner6;
    @FXML
    Spinner<Integer> AttSpinner6;
    @FXML
    Spinner<Integer> DefSpinner6;
    @FXML
    Label availablePointsLabel;
    @FXML
    Label pokemon1Label;
    @FXML
    Label pokemon2Label;
    @FXML
    Label pokemon3Label;
    @FXML
    Label pokemon4Label;
    @FXML
    Label pokemon5Label;
    @FXML
    Label pokemon6Label;

    Trainer trainer;
    int availablePoints;
    Pokemon[] pokemons;

    /**
     * Initializes the controller and schedules the data loading to occur after the UI has been rendered.
     */
    @FXML
    public void initialize() {
        Platform.runLater(this::loadData);
    }

    /**
     * Loads the trainer data from the database, including Pokemon information and available points for evolution.
     * Then updates the UI with this data.
     */
    void loadData() {
        try {
            trainer = DBUtils.loadTrainer(AppState.getCurrentPlayer().getId());
            assert trainer != null;
            pokemons = trainer.getPokemons();
            availablePoints = AppState.getCurrentPlayer().getPoints();
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load data.");
        }
    }

    /**
     * Updates the UI components with the loaded Pokemon data and initializes the spinners for stat allocation.
     * @throws SQLException if an SQL error occurs during data retrieval.
     * @throws FileNotFoundException if an image file for a Pokemon is not found.
     */
    private void updateUI() throws SQLException, FileNotFoundException {
        ImageView[] imageViews = {imageView1, imageView2, imageView3, imageView4, imageView5, imageView6};
        Label[] labels = {pokemon1Label, pokemon2Label, pokemon3Label, pokemon4Label, pokemon5Label, pokemon6Label};
        List<Spinner<Integer>> hpSpinners = Arrays.asList(HPSpinner1, HPSpinner2, HPSpinner3, HPSpinner4, HPSpinner5, HPSpinner6);
        List<Spinner<Integer>> attSpinners = Arrays.asList(AttSpinner1, AttSpinner2, AttSpinner3, AttSpinner4, AttSpinner5, AttSpinner6);
        List<Spinner<Integer>> defSpinners = Arrays.asList(DefSpinner1, DefSpinner2, DefSpinner3, DefSpinner4, DefSpinner5, DefSpinner6);

        for (int i = 0; i < pokemons.length; i++) {
            Pokemon pokemon = pokemons[i];
            labels[i].setText(pokemon.getNickName());

            String imagePath = "Pictures/" + DBUtils.getPokemonImagePath(pokemon.getNickName().toLowerCase());
            Image image = new Image(new FileInputStream(imagePath), 75, 75, true, true);
            imageViews[i].setImage(image);

            setupSpinner(hpSpinners.get(i), pokemon.getStat().getPv(), MAX_HP);
            setupSpinner(attSpinners.get(i), pokemon.getStat().getDmg(), MAX_ATTACK);
            setupSpinner(defSpinners.get(i), pokemon.getStat().getDef(), MAX_DEFENSE);
        }

        updateAvailablePointsDisplay();
    }

    /**
     * Configures a given spinner for stat allocation, including setting its initial and maximum values.
     * Adds a listener to handle user interactions with the spinner.
     * @param spinner The spinner to be configured.
     * @param initialValue The initial value for the spinner, based on the Pokemon's current stat.
     * @param maxValue The maximum value the spinner can reach.
     */
    private void setupSpinner(Spinner<Integer> spinner, int initialValue, int maxValue) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(initialValue, maxValue, initialValue));

        // Add listener for changes and update stats/points accordingly
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            // Keep track of the original value before the change
            int originalValue = oldValue;

            // Ensure the new value is within the acceptable range
            if (newValue < initialValue) {
                // Reset the spinner value to the initial value
                spinner.getValueFactory().setValue(initialValue);
            } else {
                // Calculate the change in points
                int pointChange = newValue - originalValue;
                // Check if there are enough available points for the change
                if (pointChange <= availablePoints || pointChange < 0) {
                    // Update available points based on the change in spinner value
                    availablePoints -= pointChange;
                    // Update stats accordingly
                    updateStat(spinner, pointChange);
                    // Update the UI to reflect changes in available points and stats
                    updateAvailablePointsDisplay();

                    // Disable the increment button if available points reach zero
                    if (availablePoints == 0) {
                        disableIncrementButton(spinner);
                    } else {
                        enableIncrementButton(spinner);
                    }
                } else {
                    // If there are not enough available points, reset the spinner value
                    spinner.getValueFactory().setValue(originalValue);
                    // Show an alert to inform the user
                    showAlert("Error", "Insufficient points to make this change.");
                }
            }
        });
    }
    /**
     * Disables the increment button of a spinner to prevent users from exceeding the available points limit.
     * @param spinner The spinner whose increment button should be disabled.
     */
    private void disableIncrementButton(Spinner<Integer> spinner) {
        // Get the increment button and disable it
        for (Node node : spinner.getChildrenUnmodifiable()) {
            if (node instanceof Button && ((Button) node).getText().equals("▲")) {
                node.setDisable(true);
                break;
            }
        }
    }

    /**
     * Enables the increment button of a spinner, allowing users to allocate more points to a Pokemon's stats.
     * @param spinner The spinner whose increment button should be enabled.
     */
    private void enableIncrementButton(Spinner<Integer> spinner) {
        // Get the increment button and enable it
        for (Node node : spinner.getChildrenUnmodifiable()) {
            if (node instanceof Button && ((Button) node).getText().equals("▲")) {
                node.setDisable(false);
                break;
            }
        }
    }
    // Method to update the stats based on spinner value change
    /**
     * Updates the specified stat for a Pokemon based on the spinner's value change.
     * @param spinner The spinner corresponding to the stat being updated.
     * @param pointChange The amount by which the stat is to be updated.
     */
    void updateStat(Spinner<Integer> spinner, int pointChange) {
        // Get the index of the spinner in the list of spinners
        int spinnerIndex = getSpinnerIndex(spinner);

        // Get the corresponding Pokemon
        Pokemon pokemon = pokemons[spinnerIndex];

        // Determine which spinner is being changed based on its ID or position
        if (spinner.getId().contains("HPSpinner")) {
            // If the spinner is an HP spinner, update the HP stat
            int newHP = pokemon.getStat().getPv() + pointChange;
            pokemon.getStat().setPv(newHP);
        } else if (spinner.getId().contains("AttSpinner")) {
            // If the spinner is an Attack spinner, update the Attack stat
            int newAttack = pokemon.getStat().getDmg() + pointChange;
            pokemon.getStat().setDmg(newAttack);
        } else if (spinner.getId().contains("DefSpinner")) {
            // If the spinner is a Defense spinner, update the Defense stat
            int newDefense = pokemon.getStat().getDef() + pointChange;
            pokemon.getStat().setDef(newDefense);
        }
    }
    /**
     * Determines the index of a given spinner within the array of spinners, identifying the Pokemon it corresponds to.
     * @param spinner The spinner for which the index is sought.
     * @return The index of the spinner, indicating the corresponding Pokemon.
     */
    private int getSpinnerIndex(Spinner<Integer> spinner) {
        List<Spinner<Integer>> hpSpinners = Arrays.asList(HPSpinner1, HPSpinner2, HPSpinner3, HPSpinner4, HPSpinner5, HPSpinner6);
        List<Spinner<Integer>> attSpinners = Arrays.asList(AttSpinner1, AttSpinner2, AttSpinner3, AttSpinner4, AttSpinner5, AttSpinner6);
        List<Spinner<Integer>> defSpinners = Arrays.asList(DefSpinner1, DefSpinner2, DefSpinner3, DefSpinner4, DefSpinner5, DefSpinner6);
        // Iterate through the list of spinners
        for (int i = 0; i < hpSpinners.size(); i++) {
            // Check if the current spinner matches the given spinner
            if (spinner.equals(hpSpinners.get(i)) ||
                    spinner.equals(attSpinners.get(i)) ||
                    spinner.equals(defSpinners.get(i))) {
                // If a match is found, return the index
                return i;
            }
        }
        // If the spinner is not found, return -1 or throw an exception
        return -1; // Or throw an exception indicating spinner not found
    }
    /**
     * Updates the display of available points for stat allocation.
     */
    private void updateAvailablePointsDisplay() {
        availablePointsLabel.setText("Available Points: " + availablePoints);
    }

    /**
     * Handles the "Save" button click event, saving the evolved Pokemon stats to the database and updating the available points.
     */
    @FXML
    void onSaveButtonClicked() {
        try {
            GameControllerStatic.getGameControllerStatic().setTrainer(new Trainer("SaveTrainer1", pokemons));
            Trainer trainer = GameControllerStatic.getGameControllerStatic().getTrainer();
            Player currentPlayer = AppState.getCurrentPlayer();
            int playerId = currentPlayer.getId();
            DBUtils.saveTrainer(playerId, trainer);
            DBUtils.updatePlayerPointsAfterSave(currentPlayer.getId(), availablePoints);
            Stage stage = (Stage) HPSpinner1.getScene().getWindow();
            int updatedPoints = availablePoints;
            minigameLab.updatePoints(updatedPoints);
            // Show confirmation alert
            showAlert("Success", "Changes saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save changes.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the "Reset" button click event, resetting the Pokemon stats and available points to their original values.
     */
    @FXML
    void onResetButtonClicked() {
        try {
            Player currentPlayer = AppState.getCurrentPlayer();
            assert currentPlayer != null;

            // Reset the current user's points using the points retrieved from the database
            int userId = currentPlayer.getId();
            int resetPoints = DBUtils.getUserPoints(userId);
            currentPlayer.setPoints(resetPoints);
            AppState.setCurrentPlayer(currentPlayer);

            // Reload trainer data from the database
            trainer = DBUtils.loadTrainer(userId);
            assert trainer != null;
            pokemons = trainer.getPokemons();

            // Update UI with the initial data
            updateUI();

            // Show confirmation alert
            showAlert("Success", "Reset successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to reset.");
        }
    }

    /**
     * Displays an alert dialog to the user.
     * @param title The title of the alert dialog.
     * @param content The content message of the alert dialog.
     */
    private void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    /**
     * Updates the specified stat for a Pokemon.
     * @param pokemon The Pokemon whose stat is to be updated.
     * @param statType The type of stat to update ("HP", "Attack", "Defense").
     * @param pointChange The amount by which the stat is to be updated.
     */
    void updateStat(Pokemon pokemon, String statType, int pointChange) {
        switch (statType) {
            case "HP":
                pokemon.getStat().setPv(pokemon.getStat().getPv() + pointChange);
                break;
            case "Attack":
                pokemon.getStat().setDmg(pokemon.getStat().getDmg() + pointChange);
                break;
            case "Defense":
                pokemon.getStat().setDef(pokemon.getStat().getDef() + pointChange);
                break;
        }
    }
}
