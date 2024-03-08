package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.controller;

import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.entity.Pokemon;
import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.utility.PokemonSelectionListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PokedexItemController implements Initializable {

    @FXML
    private ImageView pokemonImageView;

    private PokemonSelectionListener listener;

    private Pokemon pokemon;

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        setPokemonImage(pokemon.getImagePath());
    }

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

    public void setPokemonSelectionListener(PokemonSelectionListener listener) {
        this.listener = listener;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu contextMenu = new ContextMenu();

        // Create the menu items
        MenuItem addItem = new MenuItem("Add");
        addItem.setOnAction(event -> {
            // Your add logic here
            if(listener != null && pokemon != null) {
                listener.onPokemonAdded(pokemon);
            }
            System.out.println("Add clicked");
        });

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> {
            // Your delete logic here
            System.out.println("Delete clicked");
        });

        // Add menu items to context menu
        contextMenu.getItems().addAll(addItem, deleteItem);

        // Set the context menu on the ImageView
        pokemonImageView.setOnContextMenuRequested(event ->
                contextMenu.show(pokemonImageView, event.getScreenX(), event.getScreenY()));

        // To only show the context menu on right-click
        pokemonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(pokemonImageView, event.getScreenX(), event.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }
}
