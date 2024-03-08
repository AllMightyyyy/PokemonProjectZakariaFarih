package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.controller;

import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.entity.Pokemon;
import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.utility.DataLoader;
import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.utility.PokemonSelectionListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.Map;

public class TeamPlannerController implements PokemonSelectionListener {
    @FXML
    private TilePane pokemonTilePane;

    private PokemonTeamPlannerController pokemonTeamPlannerController;

    public void initialize() {
        // Load all Pokémon data using DataLoader
        try {
            Map<String, Pokemon> pokemonMap = DataLoader.loadPokemonData("src/main/resources/cesur/zakaria/pokemonprojectzakariafarih/teamCenter/utility/jsonData/pokemon.json");

            for (Pokemon pokemon : pokemonMap.values()) {
                // Load the PokedexItem.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("pokedexItem.fxml")); // Correct the path if needed
                TilePane pane = loader.load(); // Load the FXML which returns the root node
                PokedexItemController controller = loader.getController();

                // Set the Pokemon object and listener in the controller
                controller.setPokemon(pokemon);
                controller.setPokemonSelectionListener(this); // 'this' refers to the TeamPlannerController

                // Add the loaded item to the TilePane
                pokemonTilePane.getChildren().add(pane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPokemonTeamPlanner(PokemonTeamPlannerController controller) {
        this.pokemonTeamPlannerController = controller;
    }

    @Override
    public void onPokemonAdded(Pokemon pokemon) {
        pokemonTeamPlannerController.setPokemonName(pokemon.getName());
        pokemonTeamPlannerController.setPokemonImage(pokemon.getImagePath());
    }

    @Override
    public void onPokemonRemoved(Pokemon pokemon) {

    }
}
