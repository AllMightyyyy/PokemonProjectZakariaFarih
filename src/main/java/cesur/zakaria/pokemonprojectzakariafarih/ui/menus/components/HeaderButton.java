package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.components;

import cesur.zakaria.pokemonprojectzakariafarih.ui.menus.utils.RippleEffect;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * A custom JButton implementation for header buttons with a ripple effect.
 */
public class HeaderButton extends JButton {

    private RippleEffect rippleEffect;

    /**
     * Constructs a new HeaderButton with the specified text.
     *
     * @param text the text to be displayed on the button.
     */
    public HeaderButton(String text) {
        super(text);
        init();
    }

    /**
     * Initializes the button properties and appearance.
     */
    private void init() {
        rippleEffect = new RippleEffect(this);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        putClientProperty(FlatClientProperties.STYLE, "font:bold +3"); // Set font style
    }

    /**
     * Overrides the default paint method to include the ripple effect.
     *
     * @param g the Graphics object used for painting.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int arc = UIScale.scale(20); // Scale the corner arc size
        rippleEffect.render(g, new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));
    }
}
