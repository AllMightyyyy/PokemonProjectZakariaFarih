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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeamPlannerController {

    @FXML
    private TilePane pokemonTilePane;

    @FXML
    private HBox teamRow1, teamRow2;

    @FXML
    private Button randomizeTeamButton;

    @FXML
    private Button showTeamAnalysis;

    @FXML
    private ChoiceBox<String> typeFilterChoiceBox;

    @FXML
    private ChoiceBox<String> generationFilterChoiceBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button reset;

    private List<AnchorPane> teamSlots = new ArrayList<>();

    private static final String TYPES_COVERAGE_PATH = "/cesur/zakaria/pokemonprojectzakariafarih/images/typesCoverage/";

    private GridPane defenseCoverageTable;

    private List<Pokemon> selectedPokemons = new ArrayList<>();

    private List<String> pokemonTypes;

    private JSONObject typeData;

    public void initialize() {
        generateTeamPlaceholders(6);
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


    private void loadPokemonsIntoTilePane() throws IOException {
        Map<String, Pokemon> pokemons = DataLoader.loadPokemonData();

        pokemons.values().forEach(pokemon -> {
            String imageName = pokemon.generateImageName(); // Ensure this method returns a valid resource name
            URL imageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/" + imageName);
            if (imageUrl != null) {
                Image pokemonImage = new Image(imageUrl.toExternalForm());
                ImageView imageView = new ImageView(pokemonImage);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);

                // Wrapping ImageView in a Pane for the context menu
                Pane imageWrapper = new Pane(imageView);
                imageWrapper.setPrefSize(100, 100); // Match the ImageView size or adjust as needed

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

    private void generateTeamPlaceholders(int numberOfSlots) {
        URL defaultPokemonImageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/0000_000_uk_n.png");
        URL defaultTypeImageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/types/normal.png");

        for (int i = 0; i < numberOfSlots; i++) {
            ImageView pokemonImageView = new ImageView(new Image(defaultPokemonImageUrl.toExternalForm()));
            pokemonImageView.setFitHeight(150);
            pokemonImageView.setFitWidth(150);
            pokemonImageView.setLayoutX(25);
            pokemonImageView.setLayoutY(20);

            HBox typeImagesBox = new HBox(2); // Spacing between children
            typeImagesBox.setId("typeImagesBox" + i); // Assign unique ID based on slot index
            // Position it correctly in relation to the pokemonImageView
            typeImagesBox.setLayoutX(pokemonImageView.getLayoutX());
            typeImagesBox.setLayoutY(pokemonImageView.getLayoutY() - 30); // Place above the pokemonImageView

            // Add the default type image to the typeImagesBox
            ImageView defaultTypeImageView = new ImageView(new Image(defaultTypeImageUrl.toExternalForm()));
            defaultTypeImageView.setFitHeight(15); // Set smaller size for the default type icon
            defaultTypeImageView.setFitWidth(15);
            typeImagesBox.getChildren().add(defaultTypeImageView); // Add to the HBox


            Label pokemonLabel = new Label("EMPTY SLOT");
            pokemonLabel.setLayoutX(25);
            pokemonLabel.setPrefWidth(150);
            pokemonLabel.setAlignment(Pos.CENTER);
            pokemonLabel.setLayoutY(175);

            AnchorPane slot = new AnchorPane();
            slot.getChildren().addAll(pokemonImageView, typeImagesBox, pokemonLabel);
            slot.setPrefSize(200, 200);

            teamSlots.add(slot);

            if (i < 3) {
                teamRow1.getChildren().add(slot);
            } else {
                teamRow2.getChildren().add(slot);
            }
        }
    }

    private void resetTeamSlots() {
        for (AnchorPane slot : teamSlots) {
            ImageView pokemonImage = (ImageView) slot.getChildren().get(0);
            Label pokemonLabel = (Label) slot.getChildren().get(2);
            HBox typeImagesBox = (HBox) slot.getChildren().get(1);

            pokemonImage.setImage(null); // Remove the image
            pokemonLabel.setText("EMPTY SLOT"); // Reset the label
            typeImagesBox.getChildren().clear(); // Clear type images
        }
    }


    private void handleAddPokemon(Pokemon selectedPokemon) {
        if (selectedPokemons.size() >= 6) {
            showAlert("Team Full", "Your team is full. Cannot add more Pokémon.");
            return; // Early return to prevent adding
        }

        boolean slotFound = false;

        for (int i = 0; i < teamSlots.size(); i++) {
            AnchorPane slot = teamSlots.get(i);
            ImageView pokemonImage = (ImageView) slot.getChildren().get(0);
            Label pokemonLabel = (Label) slot.getChildren().get(2); // Assuming Label is at index 2
            HBox typeImagesBox = (HBox) slot.getChildren().get(1); // Assuming HBox is at index 1

            selectedPokemon.setPokemonTypes(selectedPokemon.getPokemonType());

            // Check if the slot is empty based on the label text
            if ("EMPTY SLOT".equals(pokemonLabel.getText())) {
                updateSlotWithPokemon(slot, selectedPokemon);
                selectedPokemons.add(selectedPokemon); // Add the selected Pokemon to the list
                slotFound = true;
                break;
            }
        }

        // If no empty slot is found, shift the existing Pokémon to make room
        if (!slotFound) {
            // Shift all existing Pokémon to the left
            for (int i = 0; i < teamSlots.size() - 1; i++) {
                copyPokemonData(teamSlots.get(i + 1), teamSlots.get(i));
            }
            // Add the new Pokémon to the last slot
            updateSlotWithPokemon(teamSlots.get(teamSlots.size() - 1), selectedPokemon);
            selectedPokemons.add(selectedPokemon); // Add the selected Pokemon to the list
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void copyPokemonData(AnchorPane sourceSlot, AnchorPane targetSlot) {
        ImageView sourceImage = (ImageView) sourceSlot.getChildren().get(0);
        ImageView targetImage = (ImageView) targetSlot.getChildren().get(0);
        Label sourceLabel = (Label) sourceSlot.getChildren().get(2);
        Label targetLabel = (Label) targetSlot.getChildren().get(2);
        HBox sourceTypesBox = (HBox) sourceSlot.getChildren().get(1);
        HBox targetTypesBox = (HBox) targetSlot.getChildren().get(1);

        targetImage.setImage(sourceImage.getImage());
        targetLabel.setText(sourceLabel.getText());
        targetTypesBox.getChildren().setAll(sourceTypesBox.getChildren());
    }

    private void updateSlotWithPokemon(AnchorPane slot, Pokemon pokemon) {
        ImageView pokemonImageView = (ImageView) slot.getChildren().get(0);
        Label pokemonLabel = (Label) slot.getChildren().get(2);
        HBox typeImagesBox = (HBox) slot.getChildren().get(1);

        String imageName = pokemon.generateImageName();
        URL imageUrl = getClass().getResource("/cesur/zakaria/pokemonprojectzakariafarih/images/pokemon/" + imageName);

        if (imageUrl != null) {
            Image pokemonImage = new Image(imageUrl.toExternalForm());
            pokemonImageView.setImage(pokemonImage);
        } else {
            System.err.println("Resource not found for Pokemon image: " + imageName);
        }

        pokemonLabel.setText(pokemon.getName());

        // Clear the HBox of any previous type images
        typeImagesBox.getChildren().clear();

        // Add new type images for this Pokemon
        for (String type : pokemon.getPokemonType()) {
            String typeImagePath = "/cesur/zakaria/pokemonprojectzakariafarih/images/types/" + type.toLowerCase() + ".png";
            URL typeImageUrl = getClass().getResource(typeImagePath);
            if (typeImageUrl != null) {
                ImageView typeImageView = new ImageView(new Image(typeImageUrl.toExternalForm()));
                typeImageView.setFitHeight(20); // Set a smaller size
                typeImageView.setFitWidth(20);
                typeImagesBox.getChildren().add(typeImageView);
            } else {
                System.err.println("Resource not found for type image: " + typeImagePath);
            }
        }
    }

    private void handleDeletePokemon(Pokemon pokemonToDelete) {
        teamSlots.forEach(slot -> {
            ImageView imageView = (ImageView) slot.getChildren().get(0);
            Label label = (Label) slot.getChildren().get(2);
            if (pokemonToDelete.getName().equals(label.getText())) {
                imageView.setImage(null); // Clear the image
                label.setText("EMPTY SLOT"); // Reset the label

                // Find and remove the exact Pokemon object from the selectedPokemons list
                selectedPokemons = selectedPokemons.stream()
                        .filter(pokemon -> !pokemon.getName().equals(pokemonToDelete.getName()))
                        .collect(Collectors.toList());

                return; // Exit after finding and handling the selected Pokémon
            }
        });
    }

    @FXML
    private void handleResetAction() {
        // Clear the selectedPokemons list
        selectedPokemons.clear();

        // Reset each team slot to its default state
        resetTeamSlots();
    }
    @FXML
    private void handleRandomizeTeam() {
        selectedPokemons.clear();
        resetTeamSlots(); // Reset the UI for team slots
        try {
            Map<String, Pokemon> allPokemons = DataLoader.loadPokemonData(); // Assuming this is your method to load all Pokémon
            List<Pokemon> pokemonList = new ArrayList<>(allPokemons.values());
            Collections.shuffle(pokemonList); // Randomize the order of Pokémon

            // Make sure you have at least 6 Pokémon to choose from
            if (pokemonList.size() < 6) {
                System.err.println("Not enough Pokémon to randomize.");
                return; // Or handle the case as you see fit
            }

            // Select the first 6 Pokémon from the shuffled list
            List<Pokemon> selectedPokemons = pokemonList.subList(0, 6);

            // Clear existing team slots or handle them according to your logic
            // Assuming you have a method to update a slot with a Pokemon:
            for (int i = 0; i < selectedPokemons.size(); i++) {
                selectedPokemons.get(i).setPokemonTypes(selectedPokemons.get(i).getPokemonType());
                updateSlotWithPokemon(teamSlots.get(i), selectedPokemons.get(i));
                this.selectedPokemons.add(selectedPokemons.get(i)); // Add the selected Pokemon to the list
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions appropriately
        }
    }

    @FXML
    private void showTeamAnalysis() throws JSONException {
        // Create a new window (Stage)
        Stage stage = new Stage();
        stage.setTitle("Team Defense Analysis");

        // Create a new GridPane that will hold our table
        GridPane gridPane = createTable();

        // Populate the GridPane with images for types and selected Pokemons
        populateTable(gridPane);

        // Create a scene with the GridPane and set it on the stage
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);

        // Show the new window
        stage.showAndWait();
    }

    private GridPane createTable() {
        // Create and configure the table-like GridPane here
        GridPane table = new GridPane();
        table.setHgap(10); // Horizontal gap between cells
        table.setVgap(10); // Vertical gap between cells

        // ... Other configurations for the GridPane

        return table;
    }

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

                if (immuneToArray != null && isInArray(immuneToArray, typeName)) {
                    return "0X"; // Immune
                } else if (weakToArray != null && isInArray(weakToArray, typeName)) {
                    effectiveness = "1/2X"; // Weak to
                } else if (resistsArray != null && isInArray(resistsArray, typeName)) {
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
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        return imageView;
    }

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


    private void loadTypeData() throws IOException, JSONException {
        String jsonData = new String(Files.readAllBytes(Paths.get("src/main/resources/cesur/zakaria/pokemonprojectzakariafarih/teamCenter/utility/jsonData/types.json")));
        typeData = new JSONObject(jsonData);
    }

    private List<Pokemon> getSelectedPokemons() {
        return selectedPokemons;
    }

    private JSONObject loadTypeDataFromFile(String filePath) throws IOException, JSONException {
        String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));
        return new JSONObject(jsonData);
    }

    private ImageView createEffectivenessImageView(String effectiveness) {
        String imagePath;
        switch (effectiveness) {
            case "0X":
                imagePath = "/cesur/zakaria/pokemonprojectzakariafarih/images/efficacy/X0.png";
                break;
            case "1/2X":
                imagePath = "/cesur/zakaria/pokemonprojectzakariafarih/images/efficacy/1-2.png";
                break;
            case "X2":
                imagePath = "/cesur/zakaria/pokemonprojectzakariafarih/images/efficacy/X2.png";
                break;
            default:
                // Default to an empty image or a placeholder image
                imagePath = "/cesur/zakaria/pokemonprojectzakariafarih/images/efficacy/Empty.png"; // Or provide a path to a placeholder image
                break;
        }
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        return imageView;
    }

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

        // Update TilePane with filtered Pokemons
        updatePokemonTilePane(filteredPokemons);
    }


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