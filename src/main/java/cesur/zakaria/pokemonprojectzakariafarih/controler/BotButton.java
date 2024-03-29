package cesur.zakaria.pokemonprojectzakariafarih.controler;

import javafx.scene.control.Button;
import cesur.zakaria.pokemonprojectzakariafarih.model.fight.Bot;

/**
 * A custom button that represents a bot in the game.
 *
 * @author Zakaria
 *
 */
public class BotButton extends Button {

    private final Bot trainer;

    /**
     * Creates a new BotButton with the given trainer.
     *
     * @param trainer the bot that this button represents
     */
    public BotButton(Bot trainer) {
        super();
        this.trainer = trainer;
        setText(trainer.getName());
    }

    /**
     * Returns the bot that this button represents.
     *
     * @return the bot
     */
    public Bot getTrainer() {
        return trainer;
    }
}
