package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.manager;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import javax.swing.*;
import java.awt.*;

public class FormsManager {
    private static FormsManager instance;
    private JFrame mainFrame; // Use JFrame to generalize the application window

    public static FormsManager getInstance() {
        if (instance == null) {
            instance = new FormsManager();
        }
        return instance;
    }

    private FormsManager() {
    }

    public void setMainFrame(JFrame frame) {
        this.mainFrame = frame;
    }

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

    // Method to show form in a new window or dialog
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
