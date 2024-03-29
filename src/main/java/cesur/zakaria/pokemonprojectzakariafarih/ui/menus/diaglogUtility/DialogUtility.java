package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.diaglogUtility;

import javax.swing.*;
import java.awt.*;

/**
 * A utility class providing methods for dialog-related operations.
 */
public class DialogUtility {

    /**
     * Finds and returns the parent JFrame of a given component.
     *
     * @param component The component for which to find the JFrame.
     * @return The parent JFrame of the component, or null if none is found.
     */
    public static JFrame getParentFrame(Component component) {
        if (component == null) return null;
        while (component != null && !(component instanceof JFrame)) {
            component = component.getParent();
        }
        return (JFrame) component;
    }
}
