package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.controller;

import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.entity.Pokemon;
import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.utility.DataLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.Map;

public class TeamPlannerController {
    @FXML
    private TilePane pokemonTilePane;

    public void initialize() {
        // Load all Pokémon data using DataLoader
        try {
            Map<String, Pokemon> pokemonMap = DataLoader.loadPokemonData("src/main/resources/cesur/zakaria/pokemonprojectzakariafarih/teamCenter/utility/jsonData/pokemon.json");

            // Iterate over each Pokémon and inject them into the TilePane
            for (Pokemon pokemon : pokemonMap.values()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("pokedexItem.fxml"));
                cesur.zakaria.demo1.teamCenter.controller.PokedexItemController controller = new cesur.zakaria.demo1.teamCenter.controller.PokedexItemController();
                loader.setController(controller);
                pokemonTilePane.getChildren().add(loader.load());
                controller.setPokemonImage(pokemon.getImagePath()); // Set the image for the Pokémon
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
