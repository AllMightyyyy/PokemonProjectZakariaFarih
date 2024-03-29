/**
 * Controller class for the Team Planner application in a Pokémon project.
 * Responsible for managing user interactions with the team planning interface,
 * loading and displaying Pokémon data, handling filters, adding/removing Pokémon
 * from the team, analyzing team defense coverage, and providing functionality
 * to randomize the team.
 */
package cesur.zakaria.pokemonprojectzakariafarih.teamCenter.controller;

import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.entity.Pokemon;
import cesur.zakaria.pokemonprojectzakariafarih.teamCenter.utility.DataLoader;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeamPlannerController {

    // FXML elements
    @FXML private TilePane pokemonTilePane;
    @FXML private HBox teamRow1, teamRow2;
    @FXML private ChoiceBox<String> typeFilterChoiceBox;
    @FXML private ChoiceBox<String> generationFilterChoiceBox;
    @FXML private TextField searchTextField;
    @FXML private Button reset;

    // Other class fields
    private final List<AnchorPane> teamSlots = new ArrayList<>();
    private static final String TYPES_COVERAGE_PATH = "/cesur/zakaria/pokemonprojectzakariafarih/images/typesCoverage/";
    private List<Pokemon> selectedPokemons = new ArrayList<>();
    private JSONObject typeData;

    /**
     * Initializes the controller.
     * Loads Pokémon data, initializes filters, and sets up event handlers.
     */
    public void initialize() {
        generateTeamPlaceholders();
        try {
            loadTypeData();
            loadPokemonsIntoTilePane();
            initializeFilters();
            reset.setOnAction(event -> handleResetAction());
        } catch (Exception e) {
            e.printStackTrace();
            //Handle the exception appropriately
        }
    }


    /**
     * Loads Pokémon images and adds them to the TilePane.
     * @throws IOException if an I/O error occurs while loading images.
     */
    private void loadPokemonsIntoTilePane() throws IOException {
        Map<String, Pokemon> pokemons = DataLoader.loadPokemonData();

        pokemons.values().forEach(pokemon -> {
            String imageName = pokemon.generateImageName();
            URL imageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/" + imageName);
            if (imageUrl != null) {
                // Load image
                Image pokemonImage = new Image(imageUrl.toExternalForm());
                ImageView imageView = new ImageView(pokemonImage);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);

                // Wrapping ImageView in a Pane for the context menu
                Pane imageWrapper = new Pane(imageView);
                imageWrapper.setPrefSize(100, 100);

                // Context Menu setup
                ContextMenu contextMenu = new ContextMenu();
                MenuItem addItem = new MenuItem("Add");
                addItem.setOnAction(e -> handleAddPokemon(pokemon));
                MenuItem deleteItem = new MenuItem("Delete");
                deleteItem.setOnAction(e -> handleDeletePokemon(pokemon));
                contextMenu.getItems().addAll(addItem, deleteItem);

                // Assign context menu to the wrapper pane
                imageWrapper.setOnContextMenuRequested(e ->
                        contextMenu.show(imageWrapper, e.getScreenX(), e.getScreenY()));

                pokemonTilePane.getChildren().add(imageWrapper);
            } else {
                System.err.println("Resource not found: " + imageName);
            }
        });
    }


    /**
     * Generates placeholders for team slots in the UI.
     */
    private void generateTeamPlaceholders() {
        // Default image URLs
        URL defaultPokemonImageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/0000_000_uk_n.png");
        URL defaultTypeImageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/types/normal.png");

        for (int i = 0; i < 6; i++) {
            // Create ImageView for Pokémon image
            assert defaultPokemonImageUrl != null;
            ImageView pokemonImageView = new ImageView(new Image(defaultPokemonImageUrl.toExternalForm()));
            pokemonImageView.setFitHeight(150);
            pokemonImageView.setFitWidth(150);
            pokemonImageView.setLayoutX(25);
            pokemonImageView.setLayoutY(20);

            // Create HBox for type images
            HBox typeImagesBox = new HBox(2);
            typeImagesBox.setId("typeImagesBox" + i);
            typeImagesBox.setLayoutX(pokemonImageView.getLayoutX());
            typeImagesBox.setLayoutY(pokemonImageView.getLayoutY() - 30);

            // Add default type image to typeImagesBox
            assert defaultTypeImageUrl != null;
            ImageView defaultTypeImageView = new ImageView(new Image(defaultTypeImageUrl.toExternalForm()));
            defaultTypeImageView.setFitHeight(15);
            defaultTypeImageView.setFitWidth(15);
            typeImagesBox.getChildren().add(defaultTypeImageView);

            // Create label for Pokémon name
            Label pokemonLabel = new Label("EMPTY SLOT");
            pokemonLabel.setLayoutX(25);
            pokemonLabel.setPrefWidth(150);
            pokemonLabel.setAlignment(Pos.CENTER);
            pokemonLabel.setLayoutY(175);

            // Create AnchorPane to hold all elements
            AnchorPane slot = new AnchorPane();
            slot.getChildren().addAll(pokemonImageView, typeImagesBox, pokemonLabel);
            slot.setPrefSize(200, 200);

            // Add slot to teamSlots list
            teamSlots.add(slot);

            // Add slot to appropriate row in UI
            if (i < 3) {
                teamRow1.getChildren().add(slot);
            } else {
                teamRow2.getChildren().add(slot);
            }
        }
    }

    /**
     * Resets the content of team slots to their default state.
     */
    private void resetTeamSlots() {
        for (AnchorPane slot : teamSlots) {
            ImageView pokemonImage = (ImageView) slot.getChildren().get(0);
            Label pokemonLabel = (Label) slot.getChildren().get(2);
            HBox typeImagesBox = (HBox) slot.getChildren().get(1);

            // Reset image, label, and type images
            pokemonImage.setImage(null);
            pokemonLabel.setText("EMPTY SLOT");
            typeImagesBox.getChildren().clear();
        }
    }



    /**
     * Handles adding a Pokémon to the team slots.
     * @param selectedPokemon The Pokémon to add to the team.
     */
    private void handleAddPokemon(Pokemon selectedPokemon) {
        if (selectedPokemons.size() >= 6) {
            showAlert();
            return;
        }

        boolean slotFound = false;

        for (AnchorPane slot : teamSlots) {
            Label pokemonLabel = (Label) slot.getChildren().get(2);

            // Check if slot is empty
            if ("EMPTY SLOT".equals(pokemonLabel.getText())) {
                updateSlotWithPokemon(slot, selectedPokemon);
                selectedPokemons.add(selectedPokemon);
                slotFound = true;
                break;
            }
        }

        if (!slotFound) {
            // If no empty slot found, shift existing Pokémon to make room
            for (int i = 0; i < teamSlots.size() - 1; i++) {
                copyPokemonData(teamSlots.get(i + 1), teamSlots.get(i));
            }
            // Add new Pokémon to last slot
            updateSlotWithPokemon(teamSlots.getLast(), selectedPokemon);
            selectedPokemons.add(selectedPokemon);
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     */
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Team Full");
        alert.setHeaderText(null);
        alert.setContentText("Your team is full. Cannot add more Pokémon.");
        alert.showAndWait();
    }

    /**
     * Copies Pokémon data from one slot to another.
     * @param sourceSlot The source slot to copy from.
     * @param targetSlot The target slot to copy to.
     */
    private void copyPokemonData(AnchorPane sourceSlot, AnchorPane targetSlot) {
        ImageView sourceImage = (ImageView) sourceSlot.getChildren().get(0);
        ImageView targetImage = (ImageView) targetSlot.getChildren().get(0);
        Label sourceLabel = (Label) sourceSlot.getChildren().get(2);
        Label targetLabel = (Label) targetSlot.getChildren().get(2);
        HBox sourceTypesBox = (HBox) sourceSlot.getChildren().get(1);
        HBox targetTypesBox = (HBox) targetSlot.getChildren().get(1);

        // Copy image, label, and type images
        targetImage.setImage(sourceImage.getImage());
        targetLabel.setText(sourceLabel.getText());
        targetTypesBox.getChildren().setAll(sourceTypesBox.getChildren());
    }


    /**
     * Updates a team slot with the specified Pokémon.
     * @param slot The team slot to update.
     * @param pokemon The Pokémon to update the slot with.
     */
    private void updateSlotWithPokemon(AnchorPane slot, Pokemon pokemon) {
        ImageView pokemonImageView = (ImageView) slot.getChildren().get(0);
        Label pokemonLabel = (Label) slot.getChildren().get(2);
        HBox typeImagesBox = (HBox) slot.getChildren().get(1);

        // Set image, label, and type images
        String imageName = pokemon.generateImageName();
        URL imageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/" + imageName);

        if (imageUrl != null) {
            Image pokemonImage = new Image(imageUrl.toExternalForm());
            pokemonImageView.setImage(pokemonImage);
        } else {
            System.err.println("Resource not found for Pokemon image: " + imageName);
        }

        pokemonLabel.setText(pokemon.getName());
        typeImagesBox.getChildren().clear();

        for (String type : pokemon.getPokemonType()) {
            String typeImagePath = "/cesur/zakaria/pokemonprojectzakariafarih/images/types/" + type.toLowerCase() + ".png";
            URL typeImageUrl = getClass().getResource(typeImagePath);
            if (typeImageUrl != null) {
                ImageView typeImageView = new ImageView(new Image(typeImageUrl.toExternalForm()));
                typeImageView.setFitHeight(20);
                typeImageView.setFitWidth(20);
                typeImagesBox.getChildren().add(typeImageView);
            } else {
                System.err.println("Resource not found for type image: " + typeImagePath);
            }
        }
    }

    /**
     * Handles deleting a Pokémon from the team slots.
     * @param pokemonToDelete The Pokémon to delete from the team.
     */
    private void handleDeletePokemon(Pokemon pokemonToDelete) {
        teamSlots.forEach(slot -> {
            Label label = (Label) slot.getChildren().get(2);
            if (pokemonToDelete.getName().equals(label.getText())) {
                ImageView imageView = (ImageView) slot.getChildren().getFirst();
                imageView.setImage(null); // Clear image
                label.setText("EMPTY SLOT"); // Reset label

                // Remove the deleted Pokémon from the selectedPokemons list
                selectedPokemons = selectedPokemons.stream()
                        .filter(pokemon -> !pokemon.getName().equals(pokemonToDelete.getName()))
                        .collect(Collectors.toList());
            }
        });
    }

    /**
     * Handles the action of resetting the team slots.
     */
    @FXML
    private void handleResetAction() {
        selectedPokemons.clear(); // Clear selectedPokemons list
        resetTeamSlots(); // Reset team slots
    }

    /**
     * Handles the action of randomizing the team.
     */
    @FXML
    private void handleRandomizeTeam() {
        selectedPokemons.clear(); // Clear selectedPokemons list
        resetTeamSlots(); // Reset team slots

        try {
            Map<String, Pokemon> allPokemons = DataLoader.loadPokemonData(); // Load all Pokémon
            List<Pokemon> pokemonList = new ArrayList<>(allPokemons.values());
            Collections.shuffle(pokemonList); // Randomize the order

            if (pokemonList.size() < 6) {
                System.err.println("Not enough Pokémon to randomize.");
                return;
            }

            // Select first 6 Pokémon from shuffled list
            List<Pokemon> selectedPokemons = pokemonList.subList(0, 6);

            // Update team slots with selected Pokémon
            for (int i = 0; i < selectedPokemons.size(); i++) {
                selectedPokemons.get(i).setPokemonTypes(selectedPokemons.get(i).getPokemonType());
                updateSlotWithPokemon(teamSlots.get(i), selectedPokemons.get(i));
                this.selectedPokemons.add(selectedPokemons.get(i)); // Add to selectedPokemons list
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }


    /**
     * Handles the action of showing the team analysis.
     */
    @FXML
    private void showTeamAnalysis() {
        try {
            // Create a new window (Stage)
            Stage stage = new Stage();
            stage.setTitle("Team Defense Analysis");

            // Create a new GridPane for the table
            GridPane gridPane = createTable();

            // Populate the table with data
            populateTable(gridPane);

            // Create a scene with the table and set it on the stage
            Scene scene = new Scene(gridPane);
            stage.setScene(scene);

            // Show the new window
            stage.showAndWait();
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON exception appropriately
        }
    }

    /**
     * Creates a GridPane table for displaying team defense analysis.
     * @return The GridPane table.
     */
    private GridPane createTable() {
        // Create and configure the table-like GridPane here
        GridPane table = new GridPane();
        table.setHgap(10); // Horizontal gap between cells
        table.setVgap(10); // Vertical gap between cells

        // ... Other configurations for the GridPane

        return table;
    }

    /**
     * Populates the given GridPane table with data for team defense analysis.
     * @param table The GridPane table to populate.
     * @throws JSONException If there is an error parsing JSON data.
     */
    private void populateTable(GridPane table) throws JSONException {
        table.getChildren().clear(); // Clear previous content
        // Ensure the typeData has been loaded properly
        if (typeData == null) {
            try {
                loadTypeData(); // Make sure this method is correctly loading your JSON data
            } catch (IOException e) {
                e.printStackTrace();
                return; // If there's an error loading the data, handle it appropriately
            }
        }

        // Array of type names should match your JSON keys
        String[] typeNames = {"normal", "fire", "water", "electric", "grass", "ice", "fighting", "poison", "ground", "flying", "psychic", "bug", "rock", "ghost", "dragon", "dark", "steel", "fairy"};

        // Assuming getSelectedPokemons() returns the correctly selected Pokémon list
        List<Pokemon> selectedPokemons = getSelectedPokemons();

        // Add type images as row headers
        for (int typeIndex = 0; typeIndex < typeNames.length; typeIndex++) {
            ImageView typeImage = createTypeImageView(typeNames[typeIndex]);
            table.add(typeImage, 0, typeIndex + 1); // Adding to column 0, the correct row for the type
        }

        for (int pokemonIndex = 0; pokemonIndex < selectedPokemons.size(); pokemonIndex++) {
            Pokemon pokemon = selectedPokemons.get(pokemonIndex);
            String imageName = pokemon.generateImageName();
            URL imageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/" + imageName);
            if (imageUrl != null) {
                Image pokemonImage = new Image(imageUrl.toExternalForm());
                ImageView pokemonImageView = new ImageView(pokemonImage);
                pokemonImageView.setFitWidth(60); // Adjust size as needed
                pokemonImageView.setFitHeight(60);
                table.add(pokemonImageView, pokemonIndex + 1, 0); // Adjust position as needed
            }
        }

        // Add Pokémon images as column headers and effectiveness images to the cells
        for (int pokemonIndex = 0; pokemonIndex < selectedPokemons.size(); pokemonIndex++) {
            Pokemon pokemon = selectedPokemons.get(pokemonIndex);

            for (int typeIndex = 0; typeIndex < typeNames.length; typeIndex++) {
                String typeName = typeNames[typeIndex];
                String effectiveness = calculateEffectiveness(pokemon, typeName);

                ImageView effectivenessImage = createEffectivenessImageView(effectiveness);

                // Debugging: Check if the effectivenessImage is null
                if (effectivenessImage.getImage() == null) {
                    System.out.println("Image not found for effectiveness: " + effectiveness);
                } else {
                    // If not null, add the image to the grid
                    table.add(effectivenessImage, pokemonIndex + 1, typeIndex + 1);
                }
            }
        }

        // Adding "Total Weak" and "Total Resist" labels to the grid
        Label totalWeakHeader = new Label("Total Weak");
        Label totalResistHeader = new Label("Total Resist");
        totalWeakHeader.setStyle("-fx-font-weight: bold; -fx-text-fill: #000000;");
        totalResistHeader.setStyle("-fx-font-weight: bold; -fx-text-fill: #000000;");
        table.add(totalWeakHeader, teamSlots.size() + 1, 0);
        table.add(totalResistHeader, teamSlots.size() + 2, 0);

        // Calculate and add "Total Weak" and "Total Resist" for each type
        for (int typeIndex = 0; typeIndex < typeNames.length; typeIndex++) {
            String typeName = typeNames[typeIndex];
            int totalWeak = calculateTotalWeakForType(typeName);
            int totalResist = calculateTotalResistForType(typeName);
            Label totalWeakValue = new Label(String.valueOf(totalWeak));
            Label totalResistValue = new Label(String.valueOf(totalResist));
            totalWeakValue.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF; -fx-padding: 5;");
            totalResistValue.setStyle("-fx-background-color: #00FF00; -fx-text-fill: #FFFFFF; -fx-padding: 5;");
            table.add(totalWeakValue, teamSlots.size() + 1, typeIndex + 1);
            table.add(totalResistValue, teamSlots.size() + 2, typeIndex + 1);
        }
    }


    private String calculateEffectiveness(Pokemon pokemon, String typeName) throws JSONException {
        List<String> pokemonTypes = pokemon.getPokemonType();
        String effectiveness = "";

        for (String type : pokemonTypes) {
            JSONObject typeDataObject = typeData.getJSONObject("type_data").getJSONObject(type);

            if (typeDataObject != null) {
                JSONArray immuneToArray = typeDataObject.optJSONArray("immune2");
                JSONArray weakToArray = typeDataObject.optJSONArray("weak2");
                JSONArray resistsArray = typeDataObject.optJSONArray("resists");

                if (isInArray(immuneToArray, typeName)) {
                    return "0X"; // Immune
                } else if (isInArray(weakToArray, typeName)) {
                    effectiveness = "1/2X"; // Weak to
                } else if (isInArray(resistsArray, typeName)) {
                    if (effectiveness.isEmpty()) {
                        effectiveness = "X2"; // Resists
                    }
                }
            }
        }

        // If no specific effectiveness was found, assume normal effectiveness
        return effectiveness.isEmpty() ? "" : effectiveness;
    }




    private boolean isInArray(JSONArray jsonArray, String value) {
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                String element = jsonArray.optString(i);
                if (element != null && element.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }
    private ImageView createTypeImageView(String type) {
        String imagePath = TYPES_COVERAGE_PATH + type + ".png";
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        return new ImageView(image);
    }

    /**
     * Calculates the total number of Pokémon weak to a specific type.
     * @param typeName The name of the type to calculate weakness for.
     * @return The total number of Pokémon weak to the specified type.
     * @throws JSONException If there is an error parsing JSON data.
     */
    private int calculateTotalWeakForType(String typeName) throws JSONException {
        int totalWeak = 0;

        for (Pokemon pokemon : selectedPokemons) {
            List<String> pokemonTypes = pokemon.getPokemonType();

            for (String type : pokemonTypes) {
                JSONObject typeDataObject = typeData.getJSONObject("type_data").getJSONObject(type);

                if (typeDataObject != null) {
                    JSONArray weakToArray = typeDataObject.optJSONArray("weak2");

                    if (isInArray(weakToArray, typeName)) {
                        totalWeak++;
                    }
                }
            }
        }

        return totalWeak;
    }

    /**
     * Calculates the total number of Pokémon resistant to a specific type.
     * @param typeName The name of the type to calculate resistance for.
     * @return The total number of Pokémon resistant to the specified type.
     * @throws JSONException If there is an error parsing JSON data.
     */
    private int calculateTotalResistForType(String typeName) throws JSONException {
        int totalResist = 0;

        for (Pokemon pokemon : selectedPokemons) {
            List<String> pokemonTypes = pokemon.getPokemonType();

            for (String type : pokemonTypes) {
                JSONObject typeDataObject = typeData.getJSONObject("type_data").getJSONObject(type);

                if (typeDataObject != null) {
                    JSONArray resistsArray = typeDataObject.optJSONArray("resists");

                    if (isInArray(resistsArray, typeName)) {
                        totalResist++;
                    }
                }
            }
        }

        return totalResist;
    }


    /**
     * Loads type data from the JSON file.
     * @throws IOException If an I/O error occurs.
     * @throws JSONException If there is an error parsing JSON data.
     */
    private void loadTypeData() throws IOException, JSONException {
        String jsonData = new String(Files.readAllBytes(Paths.get("src/main/resources/cesur/zakaria/pokemonprojectzakariafarih/teamCenter/utility/jsonData/types.json")));
        typeData = new JSONObject(jsonData);
    }

    private List<Pokemon> getSelectedPokemons() {
        return selectedPokemons;
    }

    private ImageView createEffectivenessImageView(String effectiveness) {
        String imagePath = switch (effectiveness) {
            case "0X" -> "/cesur/zakaria/pokemonprojectzakariafarih/images/efficacy/X0.png";
            case "1/2X" -> "/cesur/zakaria/pokemonprojectzakariafarih/images/efficacy/1-2.png";
            case "X2" -> "/cesur/zakaria/pokemonprojectzakariafarih/images/efficacy/X2.png";
            default ->
                // Default to an empty image or a placeholder image
                    "/cesur/zakaria/pokemonprojectzakariafarih/images/efficacy/Empty.png"; // Or provide a path to a placeholder image
        };
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        return new ImageView(image);
    }

    /**
     * Initializes the filters for type and generation.
     */
    private void initializeFilters() {
        // Example: Populate the type filter choice box
        typeFilterChoiceBox.getItems().addAll("All", "Fire", "Water", "Grass", "Bug", "dark", "dragon", "electric", "fairy", "fighting", "flying", "ghost", "ground", "ice", "normal", "poison", "psychic", "rock", "steel"); // Add all types
        typeFilterChoiceBox.setValue("All"); // Set default value

        // Populate the generation filter choice box with generations
        generationFilterChoiceBox.getItems().addAll("All", "Generation 1", "Generation 2", "Generation 3", "Generation 4", "Generation 5", "Generation 6", "Generation 7");
        generationFilterChoiceBox.setValue("All"); // Set default value

        // Add listener to searchTextField to filter on text input
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterPokemons();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Add listeners to ChoiceBoxes to filter when selection changes
        typeFilterChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterPokemons();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        generationFilterChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterPokemons();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Filters the displayed Pokémon based on type, generation, and search text.
     * @throws IOException If an error occurs while filtering the Pokémon.
     */
    private void filterPokemons() throws IOException {
        String selectedType = typeFilterChoiceBox.getValue();
        String selectedGenerationString = generationFilterChoiceBox.getValue();
        String searchText = searchTextField.getText().toLowerCase();

        // Determine the selected generation as an int
        final int selectedGenerationInt;
        if ("Generation 1".equals(selectedGenerationString)) {
            selectedGenerationInt = 0; // Treat "Generation 1" as "All" in terms of filtering
        } else if (!"All".equals(selectedGenerationString)) {
            try {
                // Extract the integer value if a specific generation other than "All" or "Generation 1" is selected
                selectedGenerationInt = Integer.parseInt(selectedGenerationString.split(" ")[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return; // Exit the method if there's an error parsing the integer
            }
        } else {
            selectedGenerationInt = 0; // Use 0 to signify 'All' generations
        }


        // Assuming DataLoader.loadPokemonData() is handled appropriately for exceptions
        Map<String, Pokemon> allPokemons = DataLoader.loadPokemonData();

        // Apply filters
        Stream<Pokemon> filteredStream = allPokemons.values().stream()
                .filter(pokemon -> "All".equals(selectedType) || pokemon.getPokemonType().stream().anyMatch(type -> type.equalsIgnoreCase(selectedType)))
                .filter(pokemon -> selectedGenerationInt == 0 || pokemon.getGeneration() == selectedGenerationInt)
                .filter(pokemon -> pokemon.getName().toLowerCase().contains(searchText));


        List<Pokemon> filteredPokemons = filteredStream.collect(Collectors.toList());

        // Update TilePane with filtered Pokémon
        updatePokemonTilePane(filteredPokemons);
    }


    /**
     * Updates the displayed Pokémon in the TilePane based on the filtered list.
     * @param pokemons The list of Pokémon to display.
     */
    private void updatePokemonTilePane(List<Pokemon> pokemons) {
        pokemonTilePane.getChildren().clear(); // Clear existing tiles

        pokemons.forEach(pokemon -> {
            String imageName = pokemon.generateImageName();
            URL imageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/" + imageName);
            if (imageUrl != null) {
                Image pokemonImage = new Image(imageUrl.toExternalForm());
                ImageView imageView = new ImageView(pokemonImage);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);

                // Wrapping ImageView in a Pane for the context menu
                Pane imageWrapper = new Pane(imageView);
                imageWrapper.setPrefSize(100, 100);

                // Context Menu setup
                ContextMenu contextMenu = new ContextMenu();
                MenuItem addItem = new MenuItem("Add");
                addItem.setOnAction(e -> handleAddPokemon(pokemon));
                MenuItem deleteItem = new MenuItem("Delete");
                deleteItem.setOnAction(e -> handleDeletePokemon(pokemon));
                contextMenu.getItems().addAll(addItem, deleteItem);

                // Assign context menu to the wrapper pane
                imageWrapper.setOnContextMenuRequested(e ->
                        contextMenu.show(imageWrapper, e.getScreenX(), e.getScreenY()));

                pokemonTilePane.getChildren().add(imageWrapper);
            } else {
                System.err.println("Resource not found: " + imageName);
            }
        });
    }
}