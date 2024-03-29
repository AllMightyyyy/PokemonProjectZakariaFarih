package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.mainMenu;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Represents the title displayed in the main menu.
 */
public class MenuTitle extends Pane {
    private Text text;

    /**
     * Constructs a MenuTitle with the specified name.
     * @param name The name to be displayed as the title.
     */
    public MenuTitle(String name) {
        String spread = "";
        for (char c : name.toCharArray()) {
            spread += c + " ";
        }

        text = new Text(spread);
        text.setFont(Font.loadFont(MenuApp.class.getResource("res/Penumbra-HalfSerif-Std_35114.ttf").toExternalForm(), 48));
        text.setFill(Color.WHITE);
        text.setEffect(new DropShadow(30, Color.BLACK));

        getChildren().addAll(text);
    }

    /**
     * Gets the width of the title.
     * @return The width of the title.
     */
    public double getTitleWidth() {
        return text.getLayoutBounds().getWidth();
    }

    /**
     * Gets the height of the title.
     * @return The height of the title.
     */
    public double getTitleHeight() {
        return text.getLayoutBounds().getHeight();
    }
}
