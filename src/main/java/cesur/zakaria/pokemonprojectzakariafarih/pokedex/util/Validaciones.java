package cesur.zakaria.pokemonprojectzakariafarih.pokedex.util;

import com.formdev.flatlaf.FlatClientProperties;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

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
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "background:lighten(@background,2%);"
                + "track:lighten(@background,2%);"
                + "trackArc:999");
    }

    /**
     * Displays a message in a JPanel if it is empty.
     *
     * @param crazyPanel The panel to be checked for emptiness and to display the message.
     */
    public static void panelVacio(JPanel crazyPanel) {
        JLabel label = new JLabel("No hay elementos agregados");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +3;"
                + "[light]foreground:tint(@foreground,30%);"
                + "[dark]foreground:shade(@foreground,30%);"
        );

        int componentCount = crazyPanel.getComponentCount();
        if (componentCount == 0) {
            crazyPanel.add(label);
        } else {
            Component[] components = crazyPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JLabel && ((JLabel) component).getText().equals("No hay elementos agregados")) {
                    crazyPanel.remove(component);
                    break;
                }
            }
        }

        crazyPanel.revalidate();
        crazyPanel.repaint();
    }

    /**
     * Checks if a string field is empty.
     *
     * @param campo The string to be checked.
     * @return True if the string is not empty, false otherwise.
     */
    public static boolean campoVacio(String campo) {
        String textoSinEspacios = campo.trim();
        return !"".equals(textoSinEspacios);
    }
}
