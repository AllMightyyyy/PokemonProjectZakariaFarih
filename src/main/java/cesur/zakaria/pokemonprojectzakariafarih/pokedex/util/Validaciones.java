package cesur.zakaria.pokemonprojectzakariafarih.pokedex.util;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import java.awt.*;

/**
 * The Validaciones class provides utility methods for performing validations and applying styles to components.
 */
public class Validaciones {

    /**
     * Applies a custom style to the scroll bar of a JScrollPane component.
     *
     * @param component The component whose scroll bar's style is to be modified.
     */
    public static void barraEstiloCategoria(Component component) {
        JScrollPane scroll = (JScrollPane) component.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "background:lighten(@background,2%);"
                + "track:lighten(@background,2%);"
                + "trackArc:999");
    }

}
