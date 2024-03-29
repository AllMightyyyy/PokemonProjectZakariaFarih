package cesur.zakaria.pokemonprojectzakariafarih.controler;

import javafx.scene.control.Button;

/**
 * A custom button that displays a league name.
 */
public class LeagueButton extends Button {

    private final String leagueNameString;

    /**
     * Creates a new LeagueButton with the given league name.
     *
     * @param leagueNameString the name of the league to display
     */
    public LeagueButton(String leagueNameString) {
        super();
        this.leagueNameString = leagueNameString;
        setText(leagueNameString);
    }

    /**
     * Returns the league name displayed by this button.
     *
     * @return the league name
     */
    public String getLeagueNameString() {
        return leagueNameString;
    }

}
