package cesur.zakaria.pokemonprojectzakariafarih.controler;

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
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.util.Duration;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Bot;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Fight;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.League;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Capacity;
import cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons.Pokemon;
import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
	 * Initialize the fight and its display. It is called automatically at the launch of the controller.
	 * @param 
	 * @return void
	 */
	public void initialize() throws IOException{
		
    	System.out.println("Launching the combat");
    	BackgroundImage mainBackground= new BackgroundImage(new Image(new FileInputStream("Pictures/fightBackground.png")),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,new BackgroundSize(600, 267, false, false, false, false));
    	GridPane root = (GridPane) mainDialog.getParent();
    	root.setBackground(new Background(mainBackground));
    	BackgroundImage fightMenu= new BackgroundImage(new Image(new FileInputStream("Pictures/fightMenu.png")),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,new BackgroundSize(600, 133, false, false, false, false));
    	mainDialog.setBackground(new Background(fightMenu));
    	mainFooter.setBackground(new Background(fightMenu));
		GameControllerStatic.UnSave();
		Pokemon[] pokemons= new Pokemon[6];
		if(GameControllerStatic.getGameControllerStatic().isMultiPlayer()==false && !GameControllerStatic.getGameControllerStatic().isLeagueison()) {
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

	private void updatePokeballs(Trainer trainer) {
		HBox pokeballsBox;
		if(fight.getFightPlan()==0) {
			pokeballsBox=pokeballs1;
		}
		else {
			pokeballsBox=pokeballs2;
		}
		int i=0;
    	for (i = 0; i < trainer.teamSize(); i++) {
    		if(trainer.getPokemon(i)!=null) {
    			if(i>=trainer.teamSize()) {
    				pokeballsBox.getChildren().get(i).setVisible(false);
    			}
    			else {
    				if(!trainer.isPokemonAlive(i)) {
    					pokeballsBox.getChildren().get(i).setOpacity(0.5);;
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

	@FXML
    void attack(ActionEvent event) throws FileNotFoundException {
		if(GameControllerStatic.getGameControllerStatic().isMultiPlayer()==false && fight.getCurrentTrainer() instanceof Bot) {
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
    	        	capUsed.substractPP();
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
	    								if(GameControllerStatic.getGameControllerStatic().isMultiPlayer()==false && fight.getNonCurrentTrainer() instanceof Bot) {
	    									try {
												selectPokemon(new ActionEvent());
											} catch (FileNotFoundException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
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

	private void nextTurn() throws IOException {
		if(GameControllerStatic.getGameControllerStatic().isMultiPlayer()==false) {
			if(fight.getCurrentTrainer() instanceof Bot) {
				botPlay();
			}
			else {
				if(fight.getCurrentTrainer().onlyOnePokemonAlive()) {
					pokemonButton.setDisable(true);
				}
				if(fight.getCurrentTrainer().getPokemon().noMoreFight()) {
					fightButton.setDisable(true);
				}else {
					fightButton.setDisable(false);
				}
				capacityFooterRight.setVisible(false);
	        	actionFooterRight.setVisible(true);  
				mainDialog.setVisible(false);
				mainFooter.setVisible(true);
			}
		}
		
	}

    @FXML
    void changePokemon(ActionEvent event) throws FileNotFoundException {
    	actionFooterRight.setVisible(false);
    	capacityFooterRight.setVisible(false);
    	pokemonFooterRight.setVisible(true);
    	mainFooter.setVisible(true);
    	Trainer trainer = fight.getTrainer1();
    	HashMap<Button, Pokemon> map = new HashMap<Button, Pokemon>()
    	{{
    		put(selectPoke1,trainer.getPokemon(0));
    		put(selectPoke2,trainer.getPokemon(1));
    		put(selectPoke3,trainer.getPokemon(2));
    		put(selectPoke4,trainer.getPokemon(3));
    		put(selectPoke5,trainer.getPokemon(4));
    		put(selectPoke6,trainer.getPokemon(5));
    	}};
    	for(Map.Entry<Button, Pokemon> entry : map.entrySet()) {
    		if(entry.getValue()!=null) {
    			entry.getKey().setText(entry.getValue().getNickName().toUpperCase());
        		if(!entry.getValue().isAlive() || entry.getValue().equals(fight.getCurrentTrainer().getPokemon())) {
        			entry.getKey().setDisable(true);
        		}
        		else {
        			entry.getKey().setDisable(false);
        		}
    		}
    	}
    }

    @FXML
    void fight(ActionEvent event) {
    	//SET DISPLAY
    	actionFooterRight.setVisible(false);
    	capacityFooterRight.setVisible(true);

    	HashMap<Button, Capacity> map = new HashMap<Button, Capacity>()
    	{{
    		put(cap1Button,fight.getTrainer1().getPokemon().getCapacities()[0]);
    		put(cap2Button,fight.getTrainer1().getPokemon().getCapacities()[1]);
    		put(cap3Button,fight.getTrainer1().getPokemon().getCapacities()[2]);
    		put(cap4Button,fight.getTrainer1().getPokemon().getCapacities()[3]);
    	}};
    	for(Map.Entry<Button, Capacity> entry : map.entrySet()) {
    		entry.getKey().setText(entry.getValue().getName().toUpperCase()+"\n"+entry.getValue().getPowerPoint()+"/"+entry.getValue().getMaxPowerPoint());
    		if(!entry.getValue().isUsable()) {
    			entry.getKey().setDisable(true);
    		}
    		else {
    			entry.getKey().setDisable(false);
    		}
    	}
    }

    @FXML
    void run(ActionEvent event) throws IOException {
    	//SET DISPLAY
    	mainFooter.setVisible(false);
    	mainDialog.setText(fight.getCurrentTrainer().getName()+" runs the fight");
    	mainDialog.setVisible(true);
    	//QUIT
    	quit();
    }

    private void quit() {
    	String fxml;
    	if(GameControllerStatic.getGameControllerStatic().isLeagueison()) {
    		fxml="League.fxml";
    	}
    	else {
    		fxml="interface.fxml";
    	}
    	Timeline timeline = new Timeline(new KeyFrame(
    	        Duration.millis(3000),
    	        ae1 ->  {
    	        	try {
    	        		music.stop();
						MainView.changeScene((Stage)runButton.getScene().getWindow(), fxml);
					} catch (IOException e) {
						e.printStackTrace();
					}
    	        }));timeline.play();
    }

    @FXML
    void selectPokemon(ActionEvent event) throws IOException {
    	if(event.getSource() instanceof Button) {
    		//IF USER PLAY
    		Button button = (Button)event.getSource();
        	Trainer trainer=(Trainer)fight.getTrainer1();
        	trainer.changePokemon(Integer.parseInt(button.getId().subSequence(button.getId().length()-1, button.getId().length()).toString())-1);
        	printPokemon(trainer.getPokemon(), 0);
    	}
    	else {
    		fight.getNonCurrentTrainer().changePokemon(0);
    		printPokemon(fight.getNonCurrentTrainer().getPokemon(), 1);
    	}
    	pokemonFooterRight.setVisible(false);
    	actionFooterRight.setVisible(true);
    	fight.nextTurn();
    	try {
			nextTurn();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    private void playAnimation(ImageView image,double duration, int cycleCout, int x,int y) {
    	TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(duration), image); 
        translateAnimation.setCycleCount(cycleCout); 
        translateAnimation.setAutoReverse(true); 
        translateAnimation.setByX(x); 
        translateAnimation.setByY(y); 
        translateAnimation.setInterpolator(Interpolator.LINEAR); 
        translateAnimation.play();
    }
	private void setAnimations() {
		playAnimation(pokemon1Image, 0.3, TranslateTransition.INDEFINITE, 0, 3);
		playAnimation(pokemon2Image, 0.3, TranslateTransition.INDEFINITE, 0, 3);
	}


	private void printEndgame(Trainer winner) {
		Timeline timeline1 = new Timeline(new KeyFrame(
    	        Duration.millis(3000),
    	        ae1 ->  {
    	        	//DISPLAY
    	        	mainDialog.setText(winner.getName().toUpperCase()+" wins the fight!");
    	        	mainDialog.setVisible(true);
    	        	mainFooter.setVisible(false);
    	        	Timeline timeline2 = new Timeline(new KeyFrame(
        	    	        Duration.millis(3000),
        	    	        ae2 ->  {
        	    	        	//QUIT
        	    	        	quit();
        	    	        }));
        			timeline2.play();
    	        }));
		timeline1.play();
	}
	private void printPokemon(Pokemon poke,int i) throws FileNotFoundException {
		if(i==0) {
			pokemon1Name.setText(poke.getNickName().toUpperCase());
	    	pokemon1Image.setImage(new Image(new FileInputStream("Pictures/"+poke.getSpecie().getImagePath())));
	    	pokemon1Life.setText(poke.getStat().pvOnPvMax());
	    	pokemon1Lifebar.setProgress(poke.getStat().pvRatio());
	    	pokemon1Lvl.setText("Lvl"+poke.getStat().getXpLevel());
	    	Capacity cap1=poke.getCapacities()[0];
	    	Capacity cap2=poke.getCapacities()[1];
	    	Capacity cap3=poke.getCapacities()[2];
	    	Capacity cap4=poke.getCapacities()[3];
	    	cap1Button.setText(cap1.getName().toUpperCase()+"\n"+cap1.getPowerPoint()+"/"+cap1.getMaxPowerPoint());
	    	cap2Button.setText(cap2.getName().toUpperCase()+"\n"+cap2.getPowerPoint()+"/"+cap2.getMaxPowerPoint());
	    	cap3Button.setText(cap3.getName().toUpperCase()+"\n"+cap3.getPowerPoint()+"/"+cap3.getMaxPowerPoint());
	    	cap4Button.setText(cap4.getName().toUpperCase()+"\n"+cap4.getPowerPoint()+"/"+cap4.getMaxPowerPoint());
		}
		else {
			pokemon2Name.setText(poke.getNickName().toUpperCase());
	    	pokemon2Image.setImage(new Image(new FileInputStream("Pictures/"+poke.getSpecie().getImagePath())));
	    	pokemon2Life.setText(poke.getStat().pvOnPvMax());
	    	pokemon2Lifebar.setProgress(poke.getStat().pvRatio());
	    	pokemon2Lvl.setText("Lvl"+poke.getStat().getXpLevel());
		}
	}
	private void launchDialog(String text) {
		mainFooter.setVisible(false);
		mainDialog.setText(text);
		mainDialog.setVisible(true);
	}

}
