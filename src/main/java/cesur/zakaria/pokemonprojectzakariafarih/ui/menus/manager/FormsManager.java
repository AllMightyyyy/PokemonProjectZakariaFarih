package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.manager;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import javax.swing.*;
import java.awt.*;

/**
 * Manages the display of forms in the application window.
 */
public class FormsManager {
    private static FormsManager instance;
    private JFrame mainFrame; // Use JFrame to generalize the application window

    /**
     * Private constructor to prevent instantiation from outside.
     */
    private FormsManager() {
    }

    /**
     * Retrieves the singleton instance of FormsManager.
     * @return The singleton instance of FormsManager.
     */
    public static FormsManager getInstance() {
        if (instance == null) {
            instance = new FormsManager();
        }
        return instance;
    }

    /**
     * Sets the main frame of the application.
     * @param frame The main frame of the application.
     */
    public void setMainFrame(JFrame frame) {
        this.mainFrame = frame;
    }

    /**
     * Shows the specified form in the main frame.
     * @param form The form to be displayed.
     */
    public void showForm(JComponent form) {
        if (mainFrame != null) {
            EventQueue.invokeLater(() -> {
                FlatAnimatedLafChange.showSnapshot();
                mainFrame.setContentPane(form);
                mainFrame.revalidate();
                mainFrame.repaint();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            });
        } else {
            System.err.println("Main frame is not set in FormsManager.");
        }
    }

    /**
     * Shows the specified form in a new window.
     * @param form The form to be displayed.
     * @param title The title of the new window.
     * @param size The size of the new window.
     */
    public void showFormInNewWindow(JComponent form, String title, Dimension size) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(size);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(form);
            frame.setVisible(true);
        });
    }
}
