package cesur.zakaria.pokemonprojectzakariafarih.main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.UIScale;

import raven.popup.GlassPanePopup;
import raven.toast.Notifications;
import cesur.zakaria.pokemonprojectzakariafarih.ui.menus.forms.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main class of the Pokemon Project application.
 * This class initializes the application's GUI and starts the main window.
 */
public class Main extends JFrame {

    private Home home;

    /**
     * Constructs a new Main instance.
     */
    public Main() {
        init();
    }

    /**
     * Initializes the main window of the application.
     */
    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIScale.scale(new Dimension(1365, 768)));
        setLocationRelativeTo(null);

        // Create the home screen
        home = new Home();
        setContentPane(home);

        // Add window listener to handle events when the window is opened or closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // Initialize the home screen overlay and start animations
                home.initOverlay(Main.this);
                home.play(0);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                // Stop animations when the window is closing
                home.stop();
            }
        });

        // Set up notifications and glass pane popup
        Notifications.getInstance().setJFrame(this);
        GlassPanePopup.install(this);
    }

    /**
     * The main entry point of the application.
     * Initializes the FlatLaf theme and starts the main window.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        // Install custom fonts and set up FlatLaf theme
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.themes");
        FlatMacDarkLaf.setup();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));

        // Start the main window on the event dispatch thread
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}
