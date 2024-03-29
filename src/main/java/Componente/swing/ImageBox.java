package Componente.swing;

import javax.swing.*;
import java.awt.*;

/**
 * The type Image box.
 */
public class ImageBox extends JLayeredPane {

    private Image imagen;

    /**
     * Sets imagen.
     *
     * @param imagen the imagen
     */
    public void setImagen(Image imagen) {
        this.imagen = imagen;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (imagen != null) {
            // Obtenemos el tamaño del componente
            Dimension size = getSize();
            // Obtenemos el tamaño de la imagen
            int imageWidth = imagen.getWidth(this);
            int imageHeight = imagen.getHeight(this);
            double scaleX = (double) size.width / imageWidth;
            double scaleY = (double) size.height / imageHeight;
            double scale = Math.min(scaleX, scaleY); // Tomamos el menor de los dos escalados para mantener la proporción
            // Calculamos el nuevo tamaño de la imagen
            int newWidth = (int) (imageWidth * scale);
            int newHeight = (int) (imageHeight * scale);
            // Calculamos la posición para centrar la imagen en el contenedor
            int x = (size.width - newWidth) / 2;
            int y = (size.height - newHeight) / 2;
            g2.drawImage(imagen, x, y, newWidth, newHeight, this);
        }
        g2.dispose();

    }

    /**
     * Sets custom cursor.
     */
// Método para establecer un cursor personalizado
    public void setCustomCursor() {
        Cursor customCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); // Ejemplo de cursor personalizado
        setCursor(customCursor);
    }

}
