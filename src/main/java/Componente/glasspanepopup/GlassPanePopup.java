package Componente.glasspanepopup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The type Glass pane popup.
 *
 * @author Raven
 */
public class GlassPanePopup {

    /**
     * Gets layer pane.
     *
     * @return the layer pane
     */
    protected JLayeredPane getLayerPane() {
        return layerPane;
    }

    private static GlassPanePopup instance;
    private JLayeredPane layerPane;
    /**
     * The Window snapshots.
     */
    protected WindowSnapshots windowSnapshots;
    /**
     * The Content pane.
     */
    protected Container contentPane;

    private GlassPanePopup() {
        init();
    }

    private void init() {
        layerPane = new JLayeredPane();
        layerPane.setLayout(new CardLayout());
    }

    /**
     * Add and show popup.
     *
     * @param component the component
     * @param option    the option
     * @param name      the name
     */
    public void addAndShowPopup(Component component, Option option, String name) {
        Popup popup = new Popup(this, component, option);
        if (name != null) {
            popup.setName(name);
        }
        layerPane.add(popup, 0);
        popup.setVisible(true);
        popup.setShowPopup(true);
        if (!layerPane.isVisible()) {
            layerPane.setVisible(true);
        }
        layerPane.grabFocus();
    }

    private void updateLayout() {
        for (Component com : layerPane.getComponents()) {
            com.revalidate();
        }
    }

    /**
     * Install.
     *
     * @param frame the frame
     */
    public static void install(JFrame frame) {
        instance = new GlassPanePopup();
        instance.windowSnapshots=new WindowSnapshots(frame);
        instance.contentPane = frame.getContentPane();
        frame.setGlassPane(instance.layerPane);
        frame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                SwingUtilities.invokeLater(() -> {
                    instance.updateLayout();
                });
            }
        });
    }

    /**
     * Show popup.
     *
     * @param component the component
     * @param option    the option
     * @param name      the name
     */
    public static void showPopup(Component component, Option option, String name) {
        if (component.getMouseListeners().length == 0) {
            component.addMouseListener(new MouseAdapter() {
            });
        }
        instance.addAndShowPopup(component, option, name);
    }

    /**
     * Show popup.
     *
     * @param component the component
     * @param option    the option
     */
    public static void showPopup(Component component, Option option) {
        showPopup(component, option, null);
    }

    /**
     * Show popup.
     *
     * @param component the component
     * @param name      the name
     */
    public static void showPopup(Component component, String name) {
        showPopup(component, new DefaultOption(), name);
    }

    /**
     * Show popup.
     *
     * @param component the component
     */
    public static void showPopup(Component component) {
        showPopup(component, new DefaultOption(), null);
    }

    /**
     * Close popup.
     *
     * @param index the index
     */
    public static void closePopup(int index) {
        index = instance.getLayerPane().getComponentCount() - 1 - index;
        if (index >= 0 && index < instance.getLayerPane().getComponentCount()) {
            if (instance.getLayerPane().getComponent(index) instanceof Popup popup) {
                popup.setShowPopup(false);
            }
        }
    }

    /**
     * Close popup last.
     */
    public static void closePopupLast() {
        closePopup(getPopupCount() - 1);
    }

    /**
     * Close popup.
     *
     * @param name the name
     */
    public static void closePopup(String name) {
        for (Component com : instance.layerPane.getComponents()) {
            if (com.getName() != null && com.getName().equals(name)) {
                if (com instanceof Popup popup) {
                    popup.setShowPopup(false);
                }
            }
        }
    }

    /**
     * Close popup all.
     */
    public static void closePopupAll() {
        for (Component com : instance.layerPane.getComponents()) {
            if (com instanceof Popup popup) {
                popup.setShowPopup(false);
            }
        }
    }

    /**
     * Gets popup count.
     *
     * @return the popup count
     */
    public static int getPopupCount() {
        return instance.layerPane.getComponentCount();
    }

    /**
     * Remove popup.
     *
     * @param popup the popup
     */
    protected synchronized void removePopup(Component popup) {
        layerPane.remove(popup);
        if (layerPane.getComponentCount() == 0) {
            layerPane.setVisible(false);
        }
    }
}
