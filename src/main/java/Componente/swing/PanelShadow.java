package Componente.swing;

import Componente.shadow.ShadowRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The type Panel shadow.
 */
public class PanelShadow extends JPanel {

    /**
     * Gets shadow type.
     *
     * @return the shadow type
     */
    public ShadowType getShadowType() {
        return shadowType;
    }

    /**
     * Sets shadow type.
     *
     * @param shadowType the shadow type
     */
    public void setShadowType(ShadowType shadowType) {
        this.shadowType = shadowType;
    }

    /**
     * Gets shadow size.
     *
     * @return the shadow size
     */
    public int getShadowSize() {
        return shadowSize;
    }

    /**
     * Sets shadow size.
     *
     * @param shadowSize the shadow size
     */
    public void setShadowSize(int shadowSize) {
        this.shadowSize = shadowSize;
    }

    /**
     * Gets shadow opacity.
     *
     * @return the shadow opacity
     */
    public float getShadowOpacity() {
        return shadowOpacity;
    }

    /**
     * Sets shadow opacity.
     *
     * @param shadowOpacity the shadow opacity
     */
    public void setShadowOpacity(float shadowOpacity) {
        this.shadowOpacity = shadowOpacity;
    }

    /**
     * Gets shadow color.
     *
     * @return the shadow color
     */
    public Color getShadowColor() {
        return shadowColor;
    }

    /**
     * Sets shadow color.
     *
     * @param shadowColor the shadow color
     */
    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
    }

    private ShadowType shadowType = ShadowType.CENTER;
    private int shadowSize = 6;
    private float shadowOpacity = 0.5f;
    private Color shadowColor = Color.BLACK;

    /**
     * Instantiates a new Panel shadow.
     */
    public PanelShadow() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        createShadow(grphcs);
        super.paintComponent(grphcs);
    }

    private void createShadow(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        int size = shadowSize * 2;
        int x = 0;
        int y = 0;
        int width = getWidth() - size;
        int height = getHeight() - size;
        if (null == shadowType) {
            //  Center
            x = shadowSize;
            y = shadowSize;
        } else {
            switch (shadowType) {
                case TOP -> {
                    x = shadowSize;
                    y = size;
                }
                case BOT -> {
                    x = shadowSize;
                    y = 0;
                }
                case TOP_LEFT -> {
                    x = size;
                    y = size;
                }
                case TOP_RIGHT -> {
                    x = 0;
                    y = size;
                }
                case BOT_LEFT -> {
                    x = size;
                    y = 0;
                }
                case BOT_RIGHT -> {
                    x = 0;
                    y = 0;
                }
                default -> {
                    //  Center
                    x = shadowSize;
                    y = shadowSize;
                }
            }
        }
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(getBackground());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.fillRoundRect(0, 0, width, height, 15, 15);

        //  Create Shadow
        ShadowRenderer render = new ShadowRenderer(shadowSize, shadowOpacity, shadowColor);
        g2.drawImage(render.createShadow(img), 0, 0, null);
        g2.drawImage(img, x, y, null);
    }
}
