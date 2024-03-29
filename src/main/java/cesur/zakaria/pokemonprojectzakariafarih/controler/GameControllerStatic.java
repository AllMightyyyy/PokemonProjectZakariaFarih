package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Bot;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.League;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;

import java.io.*;

/**
 * This class is responsible for managing the game logic and data.
 * It provides static methods that can be called from anywhere in the application.
 */
public class GameControllerStatic {

    private static GameControllerStatic gameControllerStatic;

    private final boolean multiPlayer;
    private Trainer trainer;
    private Bot bot;
    private League league;
    private boolean leagueison;
    private boolean createLeagueOn;

    /**
     * Creates a new instance of the GameControllerStatic class.
     */
    public GameControllerStatic() {
        super();
        multiPlayer=false;
        trainer=null;
        bot=null;
        league=null;
        leagueison=false;
        createLeagueOn=false;
    }

    /**
     * Returns the single instance of the GameControllerStatic class.
     * If the instance does not exist, it is created.
     * @return the single instance of the GameControllerStatic class
     */
    public static GameControllerStatic getGameControllerStatic(){
        if (gameControllerStatic == null)
            gameControllerStatic= new GameControllerStatic();
        return gameControllerStatic;
    }

    /**
     * Returns true if the game is being played in multiplayer mode, false otherwise.
     * @return true if the game is being played in multiplayer mode, false otherwise
     */
    public boolean isMultiPlayer() {
        return multiPlayer;
    }

    /**
     * Returns the player's Trainer object.
     * @return the player's Trainer object
     */
    public Trainer getTrainer() {
        return trainer;
    }

    /**
     * Returns the computer's Bot object.
     * @return the computer's Bot object
     */
    public Bot getBot() {
        return bot;
    }

    /**
     * Returns the League object.
     * @return the League object
     */
    public League getLeague() {
        return league;
    }

    /**
     * Returns true if the League is active, false otherwise.
     * @return true if the League is active, false otherwise
     */
    public boolean isLeagueison() {
        return leagueison;
    }

    /**
     * Returns true if a new League is to be created when the game starts, false otherwise.
     * @return true if a new League is to be created when the game starts, false otherwise
     */
    public boolean isCreateLeagueOn() {
        return createLeagueOn;
    }

    /**
     * Sets the League object.
     * @param league the League object
     */
    public void setLeague(League league) {
        this.league = league;
    }

    /**
     * Sets whether the League is active or not.
     * @param leagueison true if the League is active, false otherwise
     */
    public void setLeagueison(boolean leagueison) {
        this.leagueison = leagueison;
    }

    /**
     * Sets the player's Trainer object.
     * @param trainer the player's Trainer object
     */
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    /**
     * Sets the computer's Bot object.
     * @param bot the computer's Bot object
     */
    public void setBot(Bot bot) {
        this.bot = bot;
    }

    /**
     * Sets whether a new League is to be created when the game starts or not.
     * @param createLeagueOn true if a new League is to be created, false otherwise
     */
    public void setCreateLeagueOn(boolean createLeagueOn) {
        this.createLeagueOn = createLeagueOn;
    }

    /**
     * Saves the player's Trainer object to a file.
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException if there is an error while saving the file
     */
    public static void Save() throws FileNotFoundException, IOException {
        ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream("SaveTrainer1")) ;
        oos.writeObject(gameControllerStatic.getTrainer());
        oos.close();
    }

    /**
     * Loads the player's Trainer object from a file.
     */
    public static void UnSave() {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("SaveTrainer1"));
            getGameControllerStatic().setTrainer((Trainer)ois.readObject());
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}