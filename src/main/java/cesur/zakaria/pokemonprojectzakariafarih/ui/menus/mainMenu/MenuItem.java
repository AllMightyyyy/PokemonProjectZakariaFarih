package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.mainMenu;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

/**
 * Represents a menu item in the main menu.
 */
public class MenuItem extends Pane {
    private final Text text;

    private final Effect shadow = new DropShadow(5, Color.BLACK);
    private final Effect blur = new BoxBlur(1, 1, 3);

    /**
     * Constructs a MenuItem with the specified name.
     *
     * @param name The name of the menu item.
     */
    public MenuItem(String name) {
        Polygon bg = new Polygon(
                0, 0,
                200, 0,
                215, 15,
                200, 30,
                0, 30
        );
        bg.setStroke(Color.color(1, 1, 1, 0.75));
        bg.setEffect(new GaussianBlur());

        bg.fillProperty().bind(
                Bindings.when(pressedProperty())
                        .then(Color.color(0, 0, 0, 0.75))
                        .otherwise(Color.color(0, 0, 0, 0.25))
        );

        text = new Text(name);
        text.setTranslateX(5);
        text.setTranslateY(20);
        text.setFont(Font.loadFont(Objects.requireNonNull(MenuApp.class.getResource("res/Penumbra-HalfSerif-Std_35114.ttf")).toExternalForm(), 20));
        text.setFill(Color.WHITE);

        text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(shadow)
                        .otherwise(blur)
        );

        getChildren().addAll(bg, text);
    }

    /**
     * Sets the action to be performed when the menu item is clicked.
     *
     * @param action The action to be performed.
     */
    public void setOnAction(Runnable action) {
        setOnMouseClicked(e -> action.run());
    }
}
