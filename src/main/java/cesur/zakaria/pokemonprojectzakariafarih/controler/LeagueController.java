package cesur.zakaria.pokemonprojectzakariafarih.controler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.League;
import cesur.zakaria.pokemonprojectzakariafarih.vue.MainView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LeagueController {

	@FXML
	private BorderPane root;

	private League league;

	private static int lastFightIndex = 0;
	

	private boolean mainMenu=true;

	public void initialize() {
		if (GameControllerStatic.getGameControllerStatic().isLeagueison()) {

			mainMenu=false;
			lauchLeague(null);
		}
	}

	@FXML
	void lauchLeague(ActionEvent event) {
		if (!GameControllerStatic.getGameControllerStatic().isLeagueison()) {
			try {
				league = League.createDefaultLeague();
			} catch (IOException e) {
				e.printStackTrace();
			}

			GameControllerStatic.getGameControllerStatic().setLeagueison(true);
			GameControllerStatic.getGameControllerStatic().setLeague(league);
		} else {
			league = GameControllerStatic.getGameControllerStatic().getLeague();
		}

		HBox hBox = new HBox();
		int i = 0;
		int j = league.getActualBot();
		if (lastFightIndex == j) {
			league.getBot(j).restoreTrainer();
		}
		lastFightIndex = j;
		for (var trainer : league.getBots()) {

			BotButton button = new BotButton(trainer);
			if (i != j) {
				button.setDisable(true);
			}
			button.setOnAction(this.event);
			hBox.getChildren().add(button);
			i++;
		}

		root.setCenter(hBox);
	}

	private EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

		public void handle(ActionEvent e) {

			GameControllerStatic.getGameControllerStatic().setBot(((BotButton) e.getSource()).getTrainer());

			try {
				MainView.changeScene((Stage) root.getScene().getWindow(), "Fight.fxml");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};

	@FXML
	void createPersonnalLeague(ActionEvent event) {
		try {
			MainView.changeScene((Stage) root.getScene().getWindow(), "PersonalLeague.fxml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void lauchPersonnalLeague(ActionEvent event) {

		HBox hBox = new HBox();
		String repertoire = "League";
		File dir = new File("League");
		if (dir.isDirectory()) {
			String s[] = dir.list();
			for (int i = 0; i < s.length; i++) {

				File f = new File(repertoire + s[i]);
				if (f != null) {
					String filename = f.getName();
					int j = filename.lastIndexOf('.');
					if (i > 0 && i < filename.length() - 1) {
						if (filename.substring(j + 1).toLowerCase().equals("league")) {
							LeagueButton button = new LeagueButton(s[i]);
							button.setOnAction(eventUnsave);
							hBox.getChildren().add(button);
						}
					}

				}

			}
		}
		root.setCenter(hBox);
	}
	private EventHandler<ActionEvent> eventUnsave = new EventHandler<ActionEvent>() {

		public void handle(ActionEvent event) {
			String fileString=((LeagueButton)event.getSource()).getLeagueNameString();
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream("League/"+fileString));
				System.out.println(ois);
				GameControllerStatic.getGameControllerStatic().setLeague((League) ois.readObject());
				System.out.println(league);
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}

			GameControllerStatic.getGameControllerStatic().setLeagueison(true);
			initialize();
		}
	};

	@FXML
	void Menu(ActionEvent event) {
		GameControllerStatic.getGameControllerStatic().setLeagueison(false);
		try {
			if(mainMenu) {

				MainView.changeScene((Stage) root.getScene().getWindow(), "interface.fxml");
			}else
				MainView.changeScene((Stage) root.getScene().getWindow(), "League.fxml");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
