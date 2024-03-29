package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.forms;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import cesur.zakaria.pokemonprojectzakariafarih.dbUtils.DBUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D;

import static cesur.zakaria.pokemonprojectzakariafarih.ui.menus.transparentSplash.pokemonSplash.switchToPokSplash;

/**
 * Represents the login panel.
 */
public class Login extends JPanel {
    private JFrame mainFrame;

    /**
     * Constructs a new Login panel with the specified main frame.
     * @param mainFrame The main frame of the application.
     */
    public Login(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        init();
    }

    /**
     * Initializes the components of the login panel.
     */
    private void init() {
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
        });
        setLayout(new MigLayout("wrap,fillx,insets 45 45 50 45", "[fill]"));
        JLabel title = new JLabel("Login to your account", SwingConstants.CENTER);
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JCheckBox chRememberMe = new JCheckBox("Remember me");
        JButton cmdLogin = new JButton("Login");
        // Set styles and properties for components...
        add(title);
        add(new JLabel("Username"), "gapy 20");
        add(txtUsername);
        add(new JLabel("Password"), "gapy 10");
        add(txtPassword);
        add(chRememberMe);
        add(cmdLogin, "gapy 30");

        cmdLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            boolean loginSuccess = DBUtils.login(username, password);
            if(loginSuccess) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "LOGGIN PASSED");
                new Timer(2000, ev -> {
                    disposeMainFrame();
                    launchJavaFXApplication();
                }) {{
                    setRepeats(false);
                    start();
                }};
            } else {
                System.out.println("LOGIN FAILED");
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "LOGIN FAILED");
            }

        });
    }

    /**
     * Paints the component.
     * @param g The Graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int arc = UIScale.scale(20);
        g2.setColor(getBackground());
        g2.setComposite(AlphaComposite.SrcOver.derive(0.6f));
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));
        g2.dispose();
        super.paintComponent(g);
    }

    /**
     * Disposes the main frame of the application.
     */
    private void disposeMainFrame() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
    }

    /**
     * Launches the JavaFX application.
     */
    private void launchJavaFXApplication() {
        Platform.startup(() -> {
            // Setup and show your JavaFX stage
            Stage stage = new Stage();
            switchToPokSplash(stage);
        });
    }

}
