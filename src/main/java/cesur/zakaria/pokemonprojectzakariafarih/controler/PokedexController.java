package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.dbUtils.DBUtils;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Bot;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokedex.Pokedex;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.*;
import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;
import cesur.zakaria.pokemonprojectzakariafarih.vue.interfaceMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The PokedexController class serves as the controller for the Pokedex view within the Pokemon Project application.
 * It handles the initialization and interaction within the Pokedex, allowing users to browse and select Pokemon,
 * as well as view detailed information about each Pokemon. This class interacts with the Pokedex model to retrieve
 * Pokemon data and manages the UI components to display that data.
 */
public class PokedexController {

	@FXML
	private BorderPane root;

	@FXML
	private GridPane root2;

	private final ObservableList<Node> chidreNodes = FXCollections.observableArrayList();

	private final ObservableList<RowConstraints> rowContraintsList = FXCollections.observableArrayList();

	private Pokedex pokedex = null;

	private TextField nameFiled;

	private final int column = 6;

	private int choosenPokemon = 0;

	private final Pokemon[] pokemons = new Pokemon[column];

	private CapacityDeck deck = null;

	private static final Logger logger = Logger.getLogger(PokedexController.class.getName());

	static {
		try {
			FileHandler fh = new FileHandler("Pokedex.log", true);
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the Pokedex view with existing Pokemon data. This method sets up the Pokedex grid
	 * with Pokemon entries and configures event handlers for user interactions.
	 *
	 * @throws FileNotFoundException If a required file is not found.
	 */
	public void initialize() throws FileNotFoundException {
		logger.info("Launching Pokedex");
		try {
			pokedex = Pokedex.getPokedex();
			deck = CapacitiesHelper.getCapacityDeck();
		} catch (IOException e) {
			// Handle the initial IOException
			logger.severe("Error initializing Pokedex: " + e.getMessage());

			// Retry initializing the Pokedex after a short delay
			retryPokedexInitialization();
		}

		int total = pokedex.size();
		int row = total / column;
		int pokemonNumber = 1;
		for (int i = 0; i < row; i++) {//for All the row that we need to create
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setPrefHeight(100.0);
			rowConstraints.setMinHeight(100.0);
			root2.getRowConstraints().add(rowConstraints);
			rowContraintsList.add(rowConstraints);//Create the row on the grid
			HBox vBox = new HBox();
			nameFiled = new TextField("Player");
			vBox.getChildren().add(new Label("Trainer's name :  "));
			vBox.getChildren().add(nameFiled);
			root.setTop(vBox);
			for (int j = 0; j < column; j++) {//for All the columns of the grid
				//Add the PokemonButton with the image and importante information
				PokemonButton rectangle = new PokemonButton(pokedex.get(pokemonNumber));
				rectangle.setPrefWidth(100.0);

				rectangle.setPrefHeight(100.0);
				rectangle.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
						BorderWidths.DEFAULT)));
				rectangle.setTop(new Label(pokemonNumber + " " + pokedex.get(pokemonNumber).getNamePokemon()));
				BorderPane.setAlignment(rectangle.getTop(), Pos.CENTER);
				ImageView view = new ImageView();
				view.prefHeight(75.0);
				view.prefWidth(75.0);
				view.setFitHeight(75.0);
				view.setFitWidth(75.0);
				view.setImage(

						new Image(new FileInputStream("Pictures/" + pokedex.get(pokemonNumber).getImagePath()),

								75.0, 75.0, false, false));
				rectangle.setCenter(view);

				BorderPane.setAlignment(rectangle.getCenter(), Pos.CENTER);
				rectangle.setOnMouseClicked(event);
				root2.add(rectangle, j, i);
				chidreNodes.add(rectangle);
				pokemonNumber++;

			}
		}


	}
	/**
	 * Attempts to retry the initialization of the Pokedex and capacity deck in case of initial failure,
	 * such as due to IO issues. Schedules the retry task to run after a short delay.
	 */
	private void retryPokedexInitialization() {
		// Implement retry logic here
		// For example, you can use a Timer or ScheduledExecutorService to retry after a delay
		// Here's a simple example using a ScheduledExecutorService:
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.schedule(this::retryInitializationTask, 2, TimeUnit.SECONDS);
	}

	/**
	 * The task to be executed by retryPokedexInitialization. Attempts to reload the Pokedex and capacity
	 * deck and handle success or further failures accordingly.
	 */
	private void retryInitializationTask() {
		try {
			pokedex = Pokedex.getPokedex();
			deck = CapacitiesHelper.getCapacityDeck();

			// If initialization succeeds after retry, update the UI or proceed with further logic
			// For example:
			// Platform.runLater(() -> {
			//     updateUI();
			// });
		} catch (IOException e) {
			// If retry fails, log the error and retry again
			logger.severe("Retry failed: " + e.getMessage());
			retryPokedexInitialization();
		}
	}

	/**
	 * Handles mouse click events on PokemonButtons within the Pokedex grid. This event handler is
	 * responsible for displaying detailed information about the clicked Pokemon, including its
	 * name, image, type, size, and available capacities. It enables users to select Pokemon and
	 * view their details in a dedicated interface area.
	 */
	private final EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
		private int capacitychoosen = 0;
		private final int capacityMax = 4;
		private Capacity[] capacities = new Capacity[capacityMax];
		private PokemonSpecie pokemonSpecie;
		private TextField namePokemonField;
		private PokemonButton pokemonButton;

		/**
		 * This method handles the mouse event when a PokemonButton is clicked. It gets the PokemonSpecie of the clicked PokemonButton and sets the PokemonInterface with the information of the clicked Pokemon.
		 *
		 * @param e the MouseEvent that triggered the method
		 */
		public void handle(MouseEvent e) {
			var x = e.getSource();
			if (x instanceof PokemonButton pokemonButton) {
				this.pokemonButton = pokemonButton;
				pokemonSpecie = pokemonButton.getPokemonSpecie();

				/*
				  This method sets the PokemonInterface with the information of the clicked Pokemon. It creates a HBox with the name of the Pokemon and an ImageView of the Pokemon's image. It also adds the types of the Pokemon and its height and weight to the PokemonInterface.

				  @param pokemonSpecie the PokemonSpecie of the clicked Pokemon
				 * @throws FileNotFoundException if the image of the clicked Pokemon is not found
				 */
				try {
					setPokemonInterface(pokemonSpecie);
				} catch (FileNotFoundException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		/**
		 * Sets the interface with detailed information about a clicked Pokemon. This method updates the
		 * UI to show the Pokemon's name, image, types, size, and allows the user to select capacities for
		 * the Pokemon. It is triggered when a PokemonButton in the Pokedex grid is clicked.
		 *
		 * @param pokemonSpecie The species of the clicked Pokemon.
		 * @throws FileNotFoundException If the image file for the Pokemon or its capacities is not found.
		 */
		public void setPokemonInterface(PokemonSpecie pokemonSpecie) throws FileNotFoundException {
			logger.info("Pokemon selected: " + pokemonSpecie.getNamePokemon());
			HBox hBox1 = new HBox();
			namePokemonField = new TextField(pokemonSpecie.getNamePokemon());
			hBox1.getChildren().add(new Label("Pokemon's name"));
			hBox1.getChildren().add(namePokemonField);
			root.setTop(hBox1);
			AnchorPane anchorPane = new AnchorPane();
			anchorPane.setPrefWidth(600);
			anchorPane.setPrefHeight(200);
			anchorPane.setMaxHeight(100);
			ImageView view = new ImageView();
			view.setImage(new Image(new FileInputStream("Pictures/" + pokemonSpecie.getImagePath()), 200.0, 150.0, false, false));
			AnchorPane.setLeftAnchor(view, 0.0);
			AnchorPane.setTopAnchor(view, 65.0);

			AnchorPane anchorPane3 = new AnchorPane();
			anchorPane3.setPrefWidth(274);
			anchorPane3.setPrefHeight(76);
			AnchorPane.setLeftAnchor(anchorPane3, 286.0);
			AnchorPane.setTopAnchor(anchorPane3, 43.0);
			anchorPane.getChildren().addAll(view, anchorPane3);

			ImageView view2 = new ImageView();
			view2.setImage(new Image(new FileInputStream("Pictures/pokeball.png"), 34.0, 28.0, false, false));
			Label label = new Label(pokemonSpecie.getNbPokemon() + " " + pokemonSpecie.getNamePokemon());
			label.setFont(new Font(20));
			AnchorPane.setLeftAnchor(view2, 10.0);
			AnchorPane.setTopAnchor(view2, 10.0);
			AnchorPane.setLeftAnchor(label, 81.0);
			AnchorPane.setTopAnchor(label, 9.0);

			BackgroundImage pokedexBackground = new BackgroundImage(new Image(new FileInputStream("Pictures/pokedexBackground.png")),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					new BackgroundSize(250, 75, false, false, false, false));

			anchorPane3.setBackground(new Background(pokedexBackground));
			anchorPane3.getChildren().addAll(view2, label);

			ImageView view3 = new ImageView();
			view3.setImage(new Image(
					new FileInputStream("Pictures/types/" + pokemonSpecie.getTypes().get(0).toString() + ".png"), 30.0,
					30.0, false, false));
			ImageView view4 = new ImageView();
			if (pokemonSpecie.getTypes().size() == 2) {
				view4.setImage(new Image(
						new FileInputStream("Pictures/types/" + pokemonSpecie.getTypes().get(1).toString() + ".png"),
						30.0, 30.0, false, false));

			}

			AnchorPane.setLeftAnchor(view3, 347.0);
			AnchorPane.setTopAnchor(view3, 81.0);
			AnchorPane.setLeftAnchor(view4, 407.0);
			AnchorPane.setTopAnchor(view4, 81.0);
			anchorPane.getChildren().addAll(view3, view4);
			VBox vBox = new VBox();
			vBox.setPrefHeight(69);
			vBox.setPrefWidth(250);
			Label label2 = new Label("Height : " + pokemonSpecie.getSize().height() + "Inch");
			Label label3 = new Label("Weight : " + pokemonSpecie.getSize().weight() + ".lbs.");
			label2.setFont(new Font(20));
			label3.setFont(new Font(20));
			vBox.getChildren().addAll(label2, label3);
			vBox.setAlignment(Pos.CENTER);
			BackgroundImage pokedexBackground2 = new BackgroundImage(new Image(new FileInputStream("Pictures/pokedexBackground2.png")),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					new BackgroundSize(250, 69, false, false, false, false));
			vBox.setBackground(new Background(pokedexBackground2));
			AnchorPane.setLeftAnchor(vBox, 311.0);
			AnchorPane.setTopAnchor(vBox, 188.0);

			anchorPane.getChildren().addAll(vBox);

			root.setCenter(anchorPane);
			BackgroundImage fightMenu = new BackgroundImage(new Image(new FileInputStream("Pictures/fightMenu.png")),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
					new BackgroundSize(600, 100, false, false, false, false));
			HBox hBox = new HBox();
			Button returnButton = new Button("Return");
			Button selectButton = new Button("Select");
			selectButton.setOnMouseClicked(e -> {
				pokemonButton.setBorder(new Border(
						new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
				logger.info("Selected Pokemon: " + pokemonSpecie.getNamePokemon() + ", Types: " + pokemonSpecie.getTypes() + ", Height: " + pokemonSpecie.getSize().height() + ", Weight: " + pokemonSpecie.getSize().weight());
				try {
					updateRoot(pokemonSpecie.getTypes());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			returnButton.setOnMouseClicked(event -> root.setCenter(root2));
			returnButton.setPrefHeight(75);
			selectButton.setPrefHeight(75);
			returnButton.setPrefWidth(75);
			selectButton.setPrefWidth(75);
			hBox.setBackground(new Background(fightMenu));
			hBox.getChildren().addAll(returnButton, selectButton);
			hBox.setPrefHeight(100);
			hBox.setPrefWidth(600);
			hBox.setAlignment(Pos.CENTER);
			hBox.setSpacing(10);
			//anchorPane.getChildren().add(hBox);
			root.setBottom(hBox);
			AnchorPane.setLeftAnchor(hBox, 0.0);
			AnchorPane.setTopAnchor(hBox, 300.0);

		}

		/**
		 * Updates the root layout to display capacity buttons for the selected Pokemon type. This method
		 * clears existing content and populates the interface with buttons for each capacity available
		 * to the selected Pokemon type, facilitating capacity selection.
		 *
		 * @param type The Pokemon type for which to display capacity buttons.
		 * @throws FileNotFoundException If an image file for a capacity type is not found.
		 */
		public void updateRoot(PokemonType type) throws FileNotFoundException {
			// Get the capacities associated with the provided Pokemon type
			var set = deck.get(type);

			// Clear the existing content in the root GridPane
			root2.getChildren().clear();
			root2.getRowConstraints().clear();

			// Set the root BorderPane to display the GridPane in the center
			root.setTop(null);
			root.setBottom(null);
			root.setCenter(root2);

			// Initialize variables for iterating through the capacities and populating the GridPane
			int k = 0; // Counter for capacities
			int i, j; // Indices for rows and columns in the GridPane

			// Iterate through the capacities and add corresponding CapacityButtons to the GridPane
			for (Capacity capacity : set) {
				i = k / column; // Calculate the row index
				j = k % column; // Calculate the column index

				// Add a new row constraint if starting a new row
				if (j == 0) {
					RowConstraints rowConstraints = new RowConstraints();
					rowConstraints.setPrefHeight(100.0);
					rowConstraints.setMinHeight(100.0);
					root2.getRowConstraints().add(rowConstraints);
				}

				// Create a new CapacityButton with capacity information
				CapacityButton rectangle = new CapacityButton(capacity);
				rectangle.setPrefWidth(100.0 - ((BorderWidths.DEFAULT.getLeft() + BorderWidths.DEFAULT.getRight()) * column));
				rectangle.setPrefHeight(100.0);
				rectangle.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
						BorderWidths.DEFAULT)));
				rectangle.setTop(new Label(capacity.getName()));

				// Create a VBox for displaying Power and PP information
				BorderPane vBox2 = new BorderPane();
				vBox2.setRight(new Label("Pow:" + capacity.getPower()));
				vBox2.setLeft(new Label("PP:" + capacity.getMaxPowerPoint()));
				rectangle.setBottom(vBox2);

				// Create an ImageView for displaying the capacity type icon
				ImageView view = new ImageView();
				view.prefHeight(50);
				view.prefWidth(50.0);
				view.setFitHeight(50);
				view.setFitWidth(50.0);
				view.setImage(new Image(new FileInputStream("Pictures/types/" + capacity.getType().toString() + ".png"),
						50.0, 50.0, false, false));
				rectangle.setCenter(view);

				// Set event handler for the CapacityButton
				rectangle.setOnMouseClicked(event2);

				// Add the CapacityButton to the GridPane at the appropriate row and column
				root2.add(rectangle, j, i);
				k++;
			}
		}

		/**
		 * Handles mouse click events on CapacityButtons. This event handler is responsible for selecting
		 * capacities for the currently chosen Pokemon, allowing users to customize their Pokemon's capacities
		 * before proceeding further in the application, such as entering a fight or saving to the trainer's roster.
		 */
		private final EventHandler<MouseEvent> event2 = new EventHandler<>() {
			/**
			 * Handles mouse click events.
			 * Checks if the source of the event is a CapacityButton and if so, selects the capacity.
			 *
			 * @param e The MouseEvent triggering the event.
			 */
			public void handle(MouseEvent e) {
				var x = e.getSource();
				if (x instanceof CapacityButton capacityButton) {
					boolean newCapacity = true;

					// Check if the selected capacity is new
					for (Capacity capacity : capacities) {
						if (capacity == capacityButton.getCapacity()) {
							newCapacity = false;
							break;
						}
					}

					// If the capacity is new, select it and update the interface
					if (newCapacity) {
						capacityButton.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID,
								CornerRadii.EMPTY, new BorderWidths(4))));
						capacities[capacitychoosen] = capacityButton.getCapacity();
						capacitychoosen++;

						// If all capacities are selected, proceed to the next step
						if (capacitychoosen == capacityMax) {
							root2.getChildren().clear();
							root2.getChildren().addAll(chidreNodes);
							root2.getRowConstraints().clear();
							root2.getRowConstraints().addAll(rowContraintsList);
							pokemons[choosenPokemon] = new Pokemon(pokemonSpecie, capacities,
									StatistiquePokemon.RandomStat(), namePokemonField.getText());
							choosenPokemon++;
							capacities = new Capacity[capacityMax];
							capacitychoosen = 0;

							// Log the chosen Pokemon and its attributes
							logger.info("Chosen Pokemon: " + pokemons[choosenPokemon - 1].toString());

							// If all Pokemon are chosen, proceed to the next screen
							if (choosenPokemon == column) {
								try {
									// Proceed to PersonalLeague screen if league creation is enabled
									if (GameControllerStatic.getGameControllerStatic().isCreateLeagueOn()) {
										GameControllerStatic.getGameControllerStatic().setBot(new Bot(nameFiled.getText(), pokemons));
										MainView.changeScene((Stage) root2.getScene().getWindow(),
												"PersonalLeague.fxml");
									} else {
										// Proceed to main interface screen
										GameControllerStatic.getGameControllerStatic().setTrainer(new Trainer(nameFiled.getText(), pokemons));
										interfaceMenu mainApp = new interfaceMenu();
										try {
											mainApp.start((Stage) root2.getScene().getWindow());
										} catch (Exception exc) {
											throw new RuntimeException(exc);
										}
										Player currentPlayer = AppState.getCurrentPlayer();
										int playerId = currentPlayer.getId();
										Trainer trainer = GameControllerStatic.getGameControllerStatic().getTrainer();
										DBUtils.saveTrainer(playerId, trainer);
									}
								} catch (IOException e1) {
									e1.printStackTrace();
								} catch (SQLException ex) {
									throw new RuntimeException(ex);
								}
							}
						}
					}
				}
			}
		};
	};
}
