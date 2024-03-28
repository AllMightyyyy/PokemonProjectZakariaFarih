package cesur.zakaria.pokemonprojectzakariafarih.controler;

import javafx.scene.control.Button;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Bot;

public class BotButton extends Button {
	
	private final Bot trainer;

	public BotButton(Bot trainer) {
		super();
		this.trainer = trainer;
		setText(trainer.getName());
	}
	
	public Bot getTrainer() {
		return trainer;
	}
}
