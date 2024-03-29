package cesur.zakaria.pokemonprojectzakariafarih.pokedex.util;

import javax.swing.*;
import java.awt.*;

/**
 * The WrapLayout class is a layout manager that lays out components in a flow, wrapping to the next row or column
 * if necessary.
 */
public class WrapLayout extends FlowLayout {

    /**
     * Constructs a new WrapLayout with the specified alignment and gap between components.
     *
     * @param align the alignment value
     * @param hgap  the horizontal gap between components
     * @param vgap  the vertical gap between components
     */
    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    /**
     * Calculates the preferred size dimensions for the specified target container.
     *
     * @param target the target container
     * @return the preferred size dimensions
     */
    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    /**
     * Calculates the minimum size dimensions for the specified target container.
     *
     * @param target the target container
     * @return the minimum size dimensions
     */
    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension minimum = layoutSize(target, false);
        minimum.width -= (getHgap() + 1);
        return minimum;
    }

    /**
     * Calculates the size dimensions for the specified target container.
     *
     * @param target    the target container
     * @param preferred true to calculate preferred size, false to calculate minimum size
     * @return the size dimensions
     */
    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getSize().width;

            if (targetWidth == 0) {
                targetWidth = Integer.MAX_VALUE;
            }

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
            int maxWidth = targetWidth - horizontalInsetsAndGap;

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);

                if (m.isVisible()) {
                    Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                    if (rowWidth + d.width > maxWidth) {
                        addRow(dim, rowWidth, rowHeight);
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    if (rowWidth != 0) {
                        rowWidth += hgap;
                    }

                    rowWidth += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                }
            }

            addRow(dim, rowWidth, rowHeight);

            dim.width += horizontalInsetsAndGap;
            dim.height += insets.top + insets.bottom + vgap * 2;

            Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
            if (scrollPane != null) {
                dim.width -= (hgap + 1);
            }

            return dim;
        }
    }

    /**
     * Adds a row with the specified dimensions to the layout.
     *
     * @param dim       the dimensions of the layout
     * @param rowWidth  the width of the row
     * @param rowHeight the height of the row
     */
    private void addRow(Dimension dim, int rowWidth, int rowHeight) {
        dim.width = Math.max(dim.width, rowWidth);

        if (dim.height > 0) {
            dim.height += getVgap();
        }

        dim.height += rowHeight;
    }
}
