package Componente.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

/**
 * The type Image box radial.
 */
public class ImageBoxRadial extends JLayeredPane {

    private Image imagen;
    private Color colorRadial;

    /**
     * Sets imagen.
     *
     * @param imagen the imagen
     */
    public void setImagen(Image imagen) {
        this.imagen = imagen;
        repaint();
    }

    /**
     * Gets color radial.
     *
     * @return the color radial
     */
    public Color getColorRadial() {
        return colorRadial;
    }

    /**
     * Sets color radial.
     *
     * @param colorRadial the color radial
     */
    public void setColorRadial(Color colorRadial) {
        this.colorRadial = colorRadial;
        repaint();
    }

    /**
     * Is selected boolean.
     *
     * @return the boolean
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets selected.
     *
     * @param selected the selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    private boolean selected;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            drawImageCentered(g2);
            g2.dispose();
        }
        drawRadialGradients(g);
    }

    private void drawImageCentered(Graphics2D g2) {
        Dimension size = getSize();
        int imageWidth = imagen.getWidth(this);
        int imageHeight = imagen.getHeight(this);
        double scaleX = (double) size.width / imageWidth;
        double scaleY = (double) size.height / imageHeight;
        double scale = Math.min(scaleX, scaleY);
        int newWidth = (int) (imageWidth * scale);
        int newHeight = (int) (imageHeight * scale);
        int x = (size.width - newWidth) / 2;
        int y = (size.height - newHeight) / 2;
        g2.drawImage(imagen, x, y, newWidth, newHeight, this);
    }

    private void drawRadialGradients(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        drawRadialGradient(g2, getFirstRadialGradient());
        drawRadialGradient(g2, getSecondRadialGradient());
        g2.dispose();
    }

    private void drawRadialGradient(Graphics2D g2, RadialGradient radialGradient) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(radialGradient.getGradientPaint());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        if (selected) {
            g2.setColor(new Color(94, 156, 255));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
    }

    private RadialGradient getFirstRadialGradient() {
        Color[] colors = {new Color(255, 200, 230, 40), new Color(0, 0, 0, 0)};
        float[] dist = {0.0f, 0.70f};
        Point2D center = new Point2D.Float(getWidth() * 0.4f, 0);
        float radius = getWidth() * 1.0f;
        return new RadialGradient(colors, dist, center, radius);
    }

    private RadialGradient getSecondRadialGradient() {
        Color backgroundColor = getColorRadial() != null ? getColorRadial() : new Color(37, 99, 235, 30);
        Color[] colors = {backgroundColor, new Color(0, 0, 0, 0)};
        float[] dist = {0.0f, 0.85f};
        Point2D center = new Point2D.Float(getWidth() * 0.4f, 0);
        float radius = getWidth() * 1.0f;
        return new RadialGradient(colors, dist, center, radius);
    }

    /**
     * Sets custom cursor.
     */
// Método para establecer un cursor personalizado
    public void setCustomCursor() {
        Cursor customCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); // Ejemplo de cursor personalizado
        setCursor(customCursor);
    }

    private static class RadialGradient {

        private final Color[] colors;
        private final float[] dist;
        private final Point2D center;
        private final float radius;

        /**
         * Instantiates a new Radial gradient.
         *
         * @param colors the colors
         * @param dist   the dist
         * @param center the center
         * @param radius the radius
         */
        public RadialGradient(Color[] colors, float[] dist, Point2D center, float radius) {
            this.colors = colors;
            this.dist = dist;
            this.center = center;
            this.radius = radius;
        }

        /**
         * Gets gradient paint.
         *
         * @return the gradient paint
         */
        public RadialGradientPaint getGradientPaint() {
            return new RadialGradientPaint(center, radius, dist, colors);
        }
    }

}
