package Componente.glasspanepopup;

import java.awt.*;

/**
 * The interface Option.
 *
 * @author Raven
 */
public interface Option {

    /**
     * Gets layout.
     *
     * @param parent  the parent
     * @param animate the animate
     * @return the layout
     */
    String getLayout(Component parent, float animate);

    /**
     * Use snapshot boolean.
     *
     * @return the boolean
     */
    boolean useSnapshot();

    /**
     * Close when pressed esc boolean.
     *
     * @return the boolean
     */
    boolean closeWhenPressedEsc();

    /**
     * Close when click outside boolean.
     *
     * @return the boolean
     */
    boolean closeWhenClickOutside();

    /**
     * Block background boolean.
     *
     * @return the boolean
     */
    boolean blockBackground();

    /**
     * Background color.
     *
     * @return the color
     */
    Color background();

    /**
     * Opacity float.
     *
     * @return the float
     */
    float opacity();

    /**
     * Duration int.
     *
     * @return the int
     */
    int duration();

    /**
     * Gets animate.
     *
     * @return the animate
     */
    float getAnimate();

    /**
     * Sets animate.
     *
     * @param animate the animate
     */
    void setAnimate(float animate);
}
