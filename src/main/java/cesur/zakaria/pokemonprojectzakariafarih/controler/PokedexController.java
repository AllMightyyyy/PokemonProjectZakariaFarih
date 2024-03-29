/**
 * This class serves as the controller for the Pokedex view in the Pokemon Project application.
 * It manages the display of Pokemon information and interactions related to the Pokedex.
 */
package cesur.zakaria.pokemonprojectzakariafarih.controler;

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
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Bot;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokedex.Pokedex;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.*;
import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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


	/**
	 * Initializes the Pokedex view with existing Pokemon data.
	 * @throws FileNotFoundException If a required file is not found.
	 */
	public void initialize() throws FileNotFoundException {
		System.out.println("Lancement du Pok�dex");
		try {
			pokedex = Pokedex.getPokedex();
			deck = CapacitiesHelper.getCapacityDeck();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Ta mere");
			e.printStackTrace();
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
			if (x instanceof PokemonButton) {
				PokemonButton pokemonButton = (PokemonButton) x;
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
		 * This method sets the PokemonInterface with the information of the clicked Pokemon. It creates a HBox with the name of the Pokemon and an ImageView of the Pokemon's image. It also adds the types of the Pokemon and its height and weight to the PokemonInterface.
		 *
		 * @param pokemonSpecie the PokemonSpecie of the clicked Pokemon
		 * @throws FileNotFoundException if the image of the clicked Pokemon is not found
		 */
		public void setPokemonInterface(PokemonSpecie pokemonSpecie) throws FileNotFoundException {
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
			Label label2 = new Label("Height : " + pokemonSpecie.getSize().getHeight() + "Inch");
			Label label3 = new Label("Weight : " + pokemonSpecie.getSize().getWeight() + ".lbs.");
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
		 * Updates the root layout with capacity buttons corresponding to the provided Pokemon type.
		 * Clears the existing content in the root GridPane and adds new CapacityButtons for each capacity
		 * associated with the provided Pokemon type.
		 *
		 * @param type The Pokemon type for which capacity buttons need to be displayed.
		 * @throws FileNotFoundException If an image file is not found.
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
		 * Event handler for handling mouse clicks on CapacityButtons.
		 * Responsible for selecting capacities for the currently chosen Pokemon.
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
                                        MainView.changeScene((Stage) root2.getScene().getWindow(), "interface.fxml");
                                        GameControllerStatic.Save();
                                    }
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        };
	};
}
