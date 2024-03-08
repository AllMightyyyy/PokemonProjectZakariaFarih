package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PokemonTeamPlannerController {
    @FXML
    private Label pokemonLabel;

    @FXML
    private ImageView pokemonImage;

    public void setPokemonName(String name)  {
        pokemonLabel.setText(name);
    }

    public void setPokemonImage(String imagePath) {
        Image image = new Image(imagePath);
        pokemonImage.setImage(image);
    }
}
