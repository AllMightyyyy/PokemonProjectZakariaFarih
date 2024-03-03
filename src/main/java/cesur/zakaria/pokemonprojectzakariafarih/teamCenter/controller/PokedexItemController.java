package cesur.zakaria.demo1.teamCenter.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class PokedexItemController {

    @FXML
    private ImageView pokemonImageView;

    public void setPokemonImage(String imagePath) {
        // Construct the correct path to the image file
        String fullPath = "src/main/resources/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/" + imagePath;
        System.out.println("Attempting to load image from path: " + fullPath);

        // Attempt to load the image using the absolute path
        File file = new File(fullPath);
        Image image = new Image(file.toURI().toString());

        // Check if the image loading was successful
        if (image.isError()) {
            System.out.println("Could not load image from path: " + fullPath);
            throw new IllegalArgumentException("Cannot load image: " + fullPath);
        }

        // Set the image to the image view
        pokemonImageView.setImage(image);
    }




}
