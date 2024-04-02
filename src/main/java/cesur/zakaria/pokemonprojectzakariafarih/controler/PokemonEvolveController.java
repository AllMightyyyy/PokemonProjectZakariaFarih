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

public class PokemonEvolveController {
    private static final int MAX_HP = 225;
    private static final int MAX_ATTACK = 190;
    private static final int MAX_DEFENSE = 250;

    // FXML Bindings
    @FXML private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    @FXML private Spinner<Integer> HPSpinner1, AttSpinner1, DefSpinner1, HPSpinner2, AttSpinner2, DefSpinner2, HPSpinner3, AttSpinner3, DefSpinner3, HPSpinner4, AttSpinner4, DefSpinner4, HPSpinner5, AttSpinner5, DefSpinner5, HPSpinner6, AttSpinner6, DefSpinner6;
    @FXML private Label availablePointsLabel, pokemon1Label, pokemon2Label, pokemon3Label, pokemon4Label, pokemon5Label, pokemon6Label;

    private Trainer trainer;
    private int availablePoints;
    private Pokemon[] pokemons;

    @FXML
    public void initialize() {
        Platform.runLater(this::loadData);
    }

    private void loadData() {
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
    private void disableIncrementButton(Spinner<Integer> spinner) {
        // Get the increment button and disable it
        for (Node node : spinner.getChildrenUnmodifiable()) {
            if (node instanceof Button && ((Button) node).getText().equals("▲")) {
                node.setDisable(true);
                break;
            }
        }
    }

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
    private void updateStat(Spinner<Integer> spinner, int pointChange) {
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
    private void updateAvailablePointsDisplay() {
        availablePointsLabel.setText("Available Points: " + availablePoints);
    }

    @FXML
    private void onSaveButtonClicked() {
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

    @FXML
    private void onResetButtonClicked() {
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

    private void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}
