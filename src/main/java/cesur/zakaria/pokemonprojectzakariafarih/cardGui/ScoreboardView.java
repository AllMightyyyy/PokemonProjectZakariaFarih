package cesur.zakaria.pokemonprojectzakariafarih.cardGui;

import cesur.zakaria.pokemonprojectzakariafarih.cardModel.Game;
import cesur.zakaria.pokemonprojectzakariafarih.cardModel.GameEvent;
import cesur.zakaria.pokemonprojectzakariafarih.cardModel.GameListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Represents the scoreboard view in the Pokemon game.
 * This view displays the number of pokemons for each player and the current phase of the game.
 */
public class ScoreboardView extends GridPane implements GameListener {
    private static ScoreboardView scoreboardView = new ScoreboardView();

    private TextField ptsJ1, ptsJ2, actualPhase;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private ScoreboardView() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        Game.getInstance().addGameListener(this);

        ptsJ1 = new TextField();
        ptsJ2 = new TextField();

        ptsJ1.setText("" + Game.getInstance().getPokemonsJ1());
        ptsJ2.setText("" + Game.getInstance().getPokemonsJ2());

        ptsJ1.setEditable(false);
        ptsJ2.setEditable(false);

        this.add(new Label(String.format("Pokémons de %s: ", GameWindow.getNameJ1())), 0, 0);
        this.add(ptsJ1, 1, 0);
        this.add(new Label(String.format("Pokémons de %s: ", GameWindow.getNameJ2())), 0, 1);
        this.add(ptsJ2, 1, 1);

        this.add(new Label("Actual Phase:"), 0, 2);
        actualPhase = new TextField(GameWindow.getNameJ1() + " download cards");
        actualPhase.setEditable(false);
        this.add(actualPhase, 1, 2);

        Button butAttack = new Button("ATTACK");
        this.add(butAttack, 0, 3);
        butAttack.setOnAction(e -> Game.getInstance().play());
    }

    /**
     * Gets the singleton instance of the ScoreboardView.
     *
     * @return The singleton instance of ScoreboardView.
     */
    public static ScoreboardView getInstance() {
        return scoreboardView;
    }

    /**
     * Sets the text of the actual phase field.
     *
     * @param text The text to be set.
     */
    public void setFieldActualPhase(String text) {
        actualPhase.setText(text);
    }

    /**
     * Notifies the scoreboard view of a game event.
     *
     * @param event The game event.
     */
    @Override
    public void notify(GameEvent event) {
        ptsJ1.setText("" + Game.getInstance().getPokemonsJ1());
        ptsJ2.setText("" + Game.getInstance().getPokemonsJ2());
    }
}
