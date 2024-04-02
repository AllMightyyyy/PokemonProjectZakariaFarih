package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.dbUtils.DBUtils;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Bot;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Fight;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.League;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Capacity;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;
import cesur.zakaria.pokemonprojectzakariafarih.session.AppState;
import cesur.zakaria.pokemonprojectzakariafarih.session.Player;
import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The FightController class is responsible for managing the user interface (UI) and game logic during a Pokémon fight.
 * It manages the initialization of fights, updates the UI in response to game events, and handles user inputs
 * during the fight sequence. This class integrates with the game's model to reflect changes in the game state
 * within the UI, such as updating Pokémon health bars, displaying Pokémon and their capacities, and handling
 * the turn-based logic of the game.
 */
public class FightController {
	
	private Fight fight;
	private AudioClip music;
	private Capacity capUsed;
	
    @FXML
    private GridPane mainFooter;

    @FXML
    private Label dialogLeft;

    @FXML
    private GridPane actionFooterRight;

    @FXML
    private Button fightButton;

    @FXML
    private Button pokemonButton;

    @FXML
    private Button runButton;

    @FXML
    private GridPane capacityFooterRight;

    @FXML
    private Button cap1Button;

    @FXML
    private Button cap4Button;

    @FXML
    private Button cap2Button;

    @FXML
    private Button cap3Button;

    @FXML
    private GridPane pokemonFooterRight;

    @FXML
    private Button selectPoke1;

    @FXML
    private Button selectPoke5;

    @FXML
    private Button selectPoke2;

    @FXML
    private Button selectPoke4;

    @FXML
    private Button selectPoke3;

    @FXML
    private Button selectPoke6;
    
    @FXML
    private HBox pokeballs2;

    @FXML
    private Label pokemon2Name;

    @FXML
    private Label pokemon2Lvl;

    @FXML
    private ProgressBar pokemon2Lifebar;

    @FXML
    private Label pokemon2Life;

    @FXML
    private ImageView pokemon2Image;

    @FXML
    private ImageView pokemon1Image;
    
    @FXML
    private HBox pokeballs1;

    @FXML
    private Label pokemon1Name;

    @FXML
    private Label pokemon1Lvl;

    @FXML
    private ProgressBar pokemon1Lifebar;

    @FXML
    private Label pokemon1Life;

    @FXML
    private Label mainDialog;

	/**
	 * Initializes the fight scene with random background, updates UI components, loads trainer and opponent
	 * (bot or another player), and prepares the Pokémon for the fight. This method sets up the initial
	 * state of the UI, including loading Pokémon data and setting background images.
	 *
	 * @throws IOException            if there is an error reading from the file system.
	 * @throws SQLException           if there is an error retrieving data from the database.
	 * @throws ClassNotFoundException if the JDBC Driver class is not found.
	 */
	public void initialize() throws IOException, SQLException, ClassNotFoundException {
		
    	System.out.println("Launching the combat");
		Random random = new Random();
		int randomNumber = random.nextInt(12);
		String filename = "Pictures/fightbg" + randomNumber + ".jpg";
    	BackgroundImage mainBackground= new BackgroundImage(new Image(new FileInputStream(filename)),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,new BackgroundSize(600, 267, false, false, false, false));
    	GridPane root = (GridPane) mainDialog.getParent();
    	root.setBackground(new Background(mainBackground));
    	BackgroundImage fightMenu= new BackgroundImage(new Image(new FileInputStream("Pictures/fightMenu.png")),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,new BackgroundSize(600, 133, false, false, false, false));
    	mainDialog.setBackground(new Background(fightMenu));
    	mainFooter.setBackground(new Background(fightMenu));
		Player currentPlayer = AppState.getCurrentPlayer();
		int playerId = currentPlayer.getId();
		DBUtils.loadTrainer(playerId);
		Pokemon[] pokemons= new Pokemon[6];
		if(!GameControllerStatic.getGameControllerStatic().isMultiPlayer() && !GameControllerStatic.getGameControllerStatic().isLeagueison()) {
			for (int i = 0; i < pokemons.length; i++) {
				pokemons[i]=League.pokemonRandom();
			}
			fight = new Fight(GameControllerStatic.getGameControllerStatic().getTrainer() , new Bot("ZAKARIA",pokemons));
		}else if(GameControllerStatic.getGameControllerStatic().isLeagueison()){
			fight = new Fight(GameControllerStatic.getGameControllerStatic().getTrainer() ,GameControllerStatic.getGameControllerStatic().getBot() );
		}
    	Pokemon poke1=fight.getTrainer1().getPokemon();
    	Pokemon poke2=fight.getTrainer2().getPokemon();
    	printPokemon(poke1, 0);
    	printPokemon(poke2, 1);
    	setAnimations();
    	if(GameControllerStatic.getGameControllerStatic().isLeagueison()) {
    		runButton.setDisable(true);
    	}
		updatePokeballs(fight.getTrainer1());
		updatePokeballs(fight.getTrainer2());
    }

	/**
	 * Updates the display of pokeballs in the UI to reflect the current state of a trainer's Pokémon team.
	 * It visually indicates which Pokémon are still able to fight and which have been defeated.
	 *
	 * @param trainer The trainer whose Pokémon team is being updated in the UI.
	 */
	private void updatePokeballs(Trainer trainer) {
		HBox pokeballsBox;
		if(fight.getFightPlan()==0) {
			pokeballsBox=pokeballs1;
		}
		else {
			pokeballsBox=pokeballs2;
		}
		int i;
    	for (i = 0; i < trainer.teamSize(); i++) {
    		if(trainer.getPokemon(i)!=null) {
    			if(i>=trainer.teamSize()) {
    				pokeballsBox.getChildren().get(i).setVisible(false);
    			}
    			else {
    				if(!trainer.isPokemonAlive(i)) {
    					pokeballsBox.getChildren().get(i).setOpacity(0.5);
                    }
    			}
    		}
    		else{
    			pokeballsBox.getChildren().get(i).setVisible(false);
    		}
		}
    	for (int j = i; j < 6; j++) {
    		pokeballsBox.getChildren().get(j).setVisible(false);
		}
	}

	/**
	 * Manages the bot's turn during the fight. The bot selects a random capacity to use against the player.
	 * This method is responsible for executing the bot's attack and updating the UI accordingly.
	 *
	 * @throws IOException if there is an error loading media files for the attack animation or sounds.
	 */
	private void botPlay() throws IOException {
		Bot pokemonBot=(Bot)fight.getTrainer2();
		Capacity used = pokemonBot.fight();

		if(used!=null) {
			mainFooter.setVisible(false);
			mainDialog.setText("");
			mainDialog.setVisible(true);
			Timeline timeline = new Timeline(new KeyFrame(
	    	        Duration.millis(1500),
	    	        ae ->  {
	    	        	try {
							attack(new ActionEvent());
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
	    	        }));
			timeline.play();
		}
		else {
			//IF IT CAN NOT
			run(new ActionEvent());
		}
		
	}

	/**
	 * Handles the action of attacking during a fight. This method is triggered by user input when selecting
	 * a capacity to use against the opponent. It manages the attack sequence, updates the UI to reflect
	 * the attack's outcome, and progresses the fight to the next turn.
	 *
	 * @param event The action event that triggered the attack.
	 * @throws FileNotFoundException if there is an error loading media files for the attack animation.
	 */
	@FXML
    void attack(ActionEvent event) throws FileNotFoundException {
		if(!GameControllerStatic.getGameControllerStatic().isMultiPlayer() && fight.getCurrentTrainer() instanceof Bot) {
			//IF BOT PLAY
			Bot pokemonBot = (Bot) fight.getTrainer2();
			capUsed=pokemonBot.fight();
		}
		else {
			Button button = (Button) event.getSource();
			capUsed = fight.getCurrentTrainer().getPokemon().getCapacities()[Integer.parseInt(button.getId().subSequence(3, 4).toString()) - 1];
		}	
		
		launchDialog(fight.getCurrentTrainer().getPokemon().getNickName().toUpperCase()+" uses "+capUsed.getName().toUpperCase());
		
		Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.millis(1500),
    	        ae ->  {
    	        	mainDialog.setText("");
    	        	capUsed.subtractPP();
    	        	boolean dead=fight.getNonCurrentTrainer().getPokemon().takeDmgCap(capUsed);
    	        	try {
						printPokemon(fight.getNonCurrentTrainer().getPokemon(),fight.getFightPlan());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

    	        	ImageView imageToAnimate;
	        		int direction;
	        		if(fight.getFightPlan()==0) {
	        			imageToAnimate=pokemon1Image;
	        			direction=-1;
	        		}
	        		else {
	        			imageToAnimate=pokemon2Image;
	        			direction=1;
	        		}
    	        	if(dead) {
    	        		playAnimation(imageToAnimate,0.2, 1, 300*direction, 0);
    	        		updatePokeballs(fight.getNonCurrentTrainer());
    	        		mainDialog.setText(fight.getNonCurrentTrainer().getPokemon().getNickName().toUpperCase()+" is KO.");
    	        		if(fight.getNonCurrentTrainer().loose()) {
    	        			printEndgame(fight.getCurrentTrainer());
    	        		}
    	        		else {
    	        			Timeline timeline1 = new Timeline(new KeyFrame(
    	        	    	        Duration.millis(1500),
    	        	    	        ae1 ->  {
    	        	    	        	mainDialog.setVisible(false);
	    								imageToAnimate.setImage(null);
	    								playAnimation(imageToAnimate, 0.2, 1, 300 * -direction, 0);
	    								if(!GameControllerStatic.getGameControllerStatic().isMultiPlayer() && fight.getNonCurrentTrainer() instanceof Bot) {
	    									try {
												selectPokemon(new ActionEvent());
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
	    								}
	    								else {
	    									//IF PLAYER HAS BEEN ATTACKED
	    									try {
												changePokemon(new ActionEvent());
											} catch (FileNotFoundException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
	    								}
    								}));timeline1.play();
    	        		}
    	        	}
    	        	else {
        	        	playAnimation(imageToAnimate,0.2, 2, 10*direction, 0);
        	        	fight.nextTurn();
        	        	try {
    						nextTurn();
    					} catch (IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    	        	}
    	        }));
		timeline.play();
		
    }

	/**
	 * Prepares and manages the transition to the next turn in the fight. This includes updating the UI
	 * to reflect the current state of the fight, managing bot actions if applicable, and handling
	 * the end of the fight if a winner has been determined.
	 *
	 * @throws IOException if there is an error loading media files for bot actions or updating the UI.
	 */
	private void nextTurn() throws IOException {
		if(!GameControllerStatic.getGameControllerStatic().isMultiPlayer()) {
			if(fight.getCurrentTrainer() instanceof Bot) {
				botPlay();
			}
			else {
				if(fight.getCurrentTrainer().onlyOnePokemonAlive()) {
					pokemonButton.setDisable(true);
				}
                fightButton.setDisable(fight.getCurrentTrainer().getPokemon().noMoreFight());
				capacityFooterRight.setVisible(false);
	        	actionFooterRight.setVisible(true);  
				mainDialog.setVisible(false);
				mainFooter.setVisible(true);
			}
		}
		
	}

	/**
	 * Allows the player to change their active Pokémon. This method is called when the player chooses
	 * to switch Pokémon during the fight. It updates the UI to allow the player to select another Pokémon
	 * from their team.
	 *
	 * @param ignoredEvent The action event that triggered the method call, not used in the method.
	 * @throws FileNotFoundException if there is an error loading media files for displaying the selected Pokémon.
	 */
	@FXML
	void changePokemon(ActionEvent ignoredEvent) throws FileNotFoundException {
		// Hides the action footer and capacity footer, and shows the pokemon footer.
		actionFooterRight.setVisible(false);
		capacityFooterRight.setVisible(false);
		pokemonFooterRight.setVisible(true);
		mainFooter.setVisible(true);

		// Gets the trainer.
		Trainer trainer = fight.getTrainer1();

		// Creates a hash map of buttons and their corresponding pokemon.
		HashMap<Button, Pokemon> map = new HashMap<>();
		map.put(selectPoke1, trainer.getPokemon(0));
		map.put(selectPoke2, trainer.getPokemon(1));
		map.put(selectPoke3, trainer.getPokemon(2));
		map.put(selectPoke4, trainer.getPokemon(3));
		map.put(selectPoke5, trainer.getPokemon(4));
		map.put(selectPoke6, trainer.getPokemon(5));

		// Loops through the map, setting the text of each button to the pokemon's name and disabling the button if the pokemon is not alive or if it is the same as the current pokemon.
		for (Map.Entry<Button, Pokemon> entry : map.entrySet()) {
			if (entry.getValue()!= null) {
				entry.getKey().setText(entry.getValue().getNickName().toUpperCase());
                entry.getKey().setDisable(!entry.getValue().isAlive() || entry.getValue().equals(fight.getCurrentTrainer().getPokemon()));
			}
		}
	}

	/**
	 * Transitions the UI to display the capacity selection interface. This method is called when the
	 * player decides to attack, allowing them to choose one of their active Pokémon's capacities to use.
	 *
	 * @param ignoredEvent The action event that triggered the method call, not used in the method.
	 */
	@FXML
	void fight(ActionEvent ignoredEvent) {
		// Hides the action footer and capacity footer, and shows the pokemon footer.
		actionFooterRight.setVisible(false);
		capacityFooterRight.setVisible(true);

		// Creates a hash map of buttons and their corresponding capacities.
		HashMap<Button, Capacity> map = new HashMap<>();
		map.put(cap1Button, fight.getTrainer1().getPokemon().getCapacities()[0]);
		map.put(cap2Button, fight.getTrainer1().getPokemon().getCapacities()[1]);
		map.put(cap3Button, fight.getTrainer1().getPokemon().getCapacities()[2]);
		map.put(cap4Button, fight.getTrainer1().getPokemon().getCapacities()[3]);

		// Loops through the map, setting the text of each button to the capacity's name and disabling the button if the capacity is not usable.
		for (Map.Entry<Button, Capacity> entry : map.entrySet()) {
			entry.getKey().setText(entry.getValue().getName().toUpperCase() + "\n" + entry.getValue().getPowerPoint() + "/" + entry.getValue().getMaxPowerPoint());
            entry.getKey().setDisable(!entry.getValue().isUsable());
		}
	}

	/**
	 * Manages the action of running from a fight. This method is called when the player chooses to
	 * flee the battle. It updates the UI to indicate the player's retreat and ends the fight.
	 *
	 * @param ignoredEvent The action event that triggered the method call, not used in the method.
	 */
	@FXML
    void run(ActionEvent ignoredEvent) {
    	//SET DISPLAY
    	mainFooter.setVisible(false);
    	mainDialog.setText(fight.getCurrentTrainer().getName()+" runs the fight");
    	mainDialog.setVisible(true);
    	//QUIT
    	quit();
    }

	/**
	 * Handles the quitting of the fight scene, stopping any ongoing music, and transitioning back to
	 * the main menu. This method cleans up the fight scene and ensures a smooth transition out of the fight.
	 */
	private void quit() {
		// Stops the music.
		music.stop();

		// Changes the scene to the main menu.
		String fxml;
		if (GameControllerStatic.getGameControllerStatic().isLeagueison()) {
			fxml = "League.fxml";
		} else {
			fxml = "interface.fxml";
		}
		try {
			MainView.changeScene((Stage) runButton.getScene().getWindow(), fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Plays a transition animation.
		Timeline timeline = new Timeline(new KeyFrame(
				Duration.millis(3000),
				ae1 -> {
					// Do nothing.
				}));
		timeline.play();
	}

	/**
	 * Allows the player or bot to select a Pokémon from their team. This method updates the UI and
	 * game state to reflect the new active Pokémon for the trainer making the selection.
	 *
	 * @param event The action event that triggered the Pokémon selection.
	 * @throws IOException if there is an error loading media files for the new active Pokémon.
	 */
	@FXML
	void selectPokemon(ActionEvent event) throws IOException {
		// Checks if the event source is a button. If it is, the method handles the user selecting a pokemon. If it is not, the method handles the bot selecting a pokemon.
		if (event.getSource() instanceof Button button) {
			// IF USER PLAY
            Trainer trainer = fight.getTrainer1();
			// Changes the currently selected pokemon for the trainer.
			trainer.changePokemon(Integer.parseInt(button.getId().subSequence(button.getId().length() - 1, button.getId().length()).toString()) - 1);
			// Prints the new pokemon to the user interface.
			printPokemon(trainer.getPokemon(), 0);
		} else {
			// IF BOT PLAY
			fight.getNonCurrentTrainer().changePokemon(0);
			// Prints the new pokemon to the user interface.
			printPokemon(fight.getNonCurrentTrainer().getPokemon(), 1);
		}
		// Hides the pokemon footer and shows the action footer.
		pokemonFooterRight.setVisible(false);
		actionFooterRight.setVisible(true);
		// Advances to the next turn in the fight.
		fight.nextTurn();
		// Calls the next turn method, which handles the visibility of the capacity and action footer regions and displays the main dialog and main footer regions as appropriate.
		try {
			nextTurn();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Creates and plays a translation animation for the given ImageView. This method is used to animate
	 * Pokémon sprites during the fight, indicating actions such as attacks or being hit.
	 *
	 * @param image The ImageView to animate.
	 * @param duration The duration of the animation in seconds.
	 * @param cycleCount The number of times the animation should repeat.
	 * @param x The amount to translate along the X axis.
	 * @param y The amount to translate along the Y axis.
	 */
	private void playAnimation(ImageView image, double duration, int cycleCount, int x, int y) {
		TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(duration), image);
		translateAnimation.setCycleCount(cycleCount);
		translateAnimation.setAutoReverse(true);
		translateAnimation.setByX(x);
		translateAnimation.setByY(y);
		translateAnimation.setInterpolator(Interpolator.LINEAR);
		translateAnimation.play();
	}
	/**
	 * Applies idle animations to the Pokémon sprites. This method is called during the initialization
	 * of the fight scene to give life to the Pokémon images.
	 */
	private void setAnimations() {
		// Apply animation to pokemon1Image
		playAnimation(pokemon1Image, 0.3, TranslateTransition.INDEFINITE, 0, 3);

		// Apply animation to pokemon2Image
		playAnimation(pokemon2Image, 0.3, TranslateTransition.INDEFINITE, 0, 3);
	}


	/**
	 * Displays a dialog indicating the end of the game and the winner. This method is called when one
	 * of the trainers wins the fight, handling the display of the winning message and transitioning out
	 * of the fight scene.
	 *
	 * @param winner The trainer who won the fight.
	 */
	private void printEndgame(Trainer winner) {
		// Displays a message indicating the winner of the fight and quits the game.
		Timeline timeline1 = new Timeline(new KeyFrame(
				Duration.millis(3000),
				ae1 -> {
					// Displays the message.
					mainDialog.setText(winner.getName().toUpperCase() + " wins the fight!");
					mainDialog.setVisible(true);
					mainFooter.setVisible(false);
					// Quits the game.
					Timeline timeline2 = new Timeline(new KeyFrame(
							Duration.millis(3000),
							ae2 -> quit()));
					timeline2.play();
				}));
		timeline1.play();
	}
	/**
	 * Updates the UI to display information about a Pokémon, including its name, level, health, and image.
	 * This method is used to reflect changes in the active Pokémon during the fight.
	 *
	 * @param poke The Pokémon to display information for.
	 * @param i The index of the Pokémon (0 for player's Pokémon, 1 for opponent's Pokémon).
	 * @throws FileNotFoundException if there is an error loading the Pokémon's image file.
	 */
	private void printPokemon(Pokemon poke, int i) throws FileNotFoundException {
		// Sets the properties of the given pokemon and updates the user interface.
		if (i == 0) {
			// Sets the properties of the first pokemon.
			pokemon1Name.setText(poke.getNickName().toUpperCase());
			pokemon1Image.setImage(new Image(new FileInputStream("Pictures/" + poke.getSpecie().getImagePath())));
			pokemon1Life.setText(poke.getStat().pvOnPvMax());
			pokemon1Lifebar.setProgress(poke.getStat().pvRatio());
			pokemon1Lvl.setText("Lvl" + poke.getStat().getXpLevel());

			// Sets the properties of the first pokemon's capacities.
			Capacity cap1 = poke.getCapacities()[0];
			Capacity cap2 = poke.getCapacities()[1];
			Capacity cap3 = poke.getCapacities()[2];
			Capacity cap4 = poke.getCapacities()[3];
			cap1Button.setText(cap1.getName().toUpperCase() + "\n" + cap1.getPowerPoint() + "/" + cap1.getMaxPowerPoint());
			cap2Button.setText(cap2.getName().toUpperCase() + "\n" + cap2.getPowerPoint() + "/" + cap2.getMaxPowerPoint());
			cap3Button.setText(cap3.getName().toUpperCase() + "\n" + cap3.getPowerPoint() + "/" + cap3.getMaxPowerPoint());
			cap4Button.setText(cap4.getName().toUpperCase() + "\n" + cap4.getPowerPoint() + "/" + cap4.getMaxPowerPoint());
		} else {
			// Sets the properties of the second pokemon.
			pokemon2Name.setText(poke.getNickName().toUpperCase());
			pokemon2Image.setImage(new Image(new FileInputStream("Pictures/" + poke.getSpecie().getImagePath())));
			pokemon2Life.setText(poke.getStat().pvOnPvMax());
			pokemon2Lifebar.setProgress(poke.getStat().pvRatio());
			pokemon2Lvl.setText("Lvl" + poke.getStat().getXpLevel());
		}
	}
	/**
	 * Displays a dialog with the specified text. This method is used to show messages in the UI,
	 * such as announcing moves or outcomes during the fight.
	 *
	 * @param text The text to display in the dialog.
	 */
	private void launchDialog(String text) {
		mainFooter.setVisible(false);
		mainDialog.setText(text);
		mainDialog.setVisible(true);
	}

	/**
	 * Sets music.
	 *
	 * @param music the music
	 */
	public void setMusic(AudioClip music) {
		this.music = music;
	}

	/**
	 * Gets dialog left.
	 *
	 * @return the dialog left
	 */
	public Label getDialogLeft() {
		return dialogLeft;
	}

	/**
	 * Sets dialog left.
	 *
	 * @param dialogLeft the dialog left
	 */
	public void setDialogLeft(Label dialogLeft) {
		this.dialogLeft = dialogLeft;
	}
}
