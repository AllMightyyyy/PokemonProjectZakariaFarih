package cesur.zakaria.pokemonprojectzakariafarih.controler;

import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Bot;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.League;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Trainer;

import java.io.*;

public class GameControllerStatic {
	
	private static GameControllerStatic gameControllerStatic;
	
	private boolean multiPlayer;
	private Trainer trainer;
	private Bot bot;
	private League league;
	private boolean leagueison;
	private boolean createLeagueOn;
	
	public GameControllerStatic() {
		super();
		multiPlayer=false;
		trainer=null;
		bot=null;
		league=null;
		leagueison=false;
		createLeagueOn=false;
	}

	public static GameControllerStatic getGameControllerStatic(){
		if (gameControllerStatic == null)
			gameControllerStatic= new GameControllerStatic();
		return gameControllerStatic;
	}
	
	public boolean isMultiPlayer() {
		return multiPlayer;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public Bot getBot() {
		return bot;
	}

	public League getLeague() {
		return league;
	}

	public boolean isLeagueison() {
		return leagueison;
	}

	public boolean isCreateLeagueOn() {
		return createLeagueOn;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public void setLeagueison(boolean leagueison) {
		this.leagueison = leagueison;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
	
	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public void setCreateLeagueOn(boolean createLeagueOn) {
		this.createLeagueOn = createLeagueOn;
	}

	public static void Save() throws FileNotFoundException, IOException {
		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream("SaveTrainer1")) ;
		oos.writeObject(gameControllerStatic.getTrainer());
		oos.close(); 
	}

	public static void UnSave() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("SaveTrainer1"));
			getGameControllerStatic().setTrainer((Trainer)ois.readObject());
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException  e2) {
			e2.printStackTrace();
		}
		 
		
	}
}
