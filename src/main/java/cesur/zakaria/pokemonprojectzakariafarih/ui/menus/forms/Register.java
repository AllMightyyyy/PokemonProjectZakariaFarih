package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.forms;

import cesur.zakaria.pokemonprojectzakariafarih.dbUtils.DBUtils;
import cesur.zakaria.pokemonprojectzakariafarih.ui.menus.components.PasswordStrengthStatus;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a registration form for new users.
 */
public class Register extends JPanel {
    private Window parentWindow; // Store reference to parent window

    /**
     * Constructs a new Register panel.
     */
    public Register() {
        init();
        setPreferredSize(new Dimension(600, 600));
    }

    /**
     * Sets the parent window.
     *
     * @param parentWindow The parent window to set.
     */
    public void setParentWindow(Window parentWindow) {
        this.parentWindow = parentWindow;
        centerParentWindow();
    }

    private void centerParentWindow() {
        if (parentWindow != null) {
            // This assumes the parent window's size has been set
            parentWindow.setLocationRelativeTo(null);
            parentWindow.setVisible(true);
        }
    }

    private void init() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        cmdRegister = new JButton("Sign Up");

        cmdRegister.addActionListener(e -> {
            if (isMatchPassword()) {
                String firstName = txtFirstName.getText().trim();
                String lastName = txtLastName.getText().trim();
                String username = txtUsername.getText().trim();
                char[] passwordCharArray = txtPassword.getPassword();
                String plainPassword = new String(passwordCharArray); // This should be the actual user-entered password

                // Determine the gender
                String gender = jrMale.isSelected() ? "Male" : "Female";

                boolean registrationSuccess = DBUtils.registerUser(username, plainPassword, firstName, lastName, gender);

                if (registrationSuccess) {
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, "Registration successful!");
                    if (parentWindow != null) {
                        parentWindow.dispose(); // Close the sign-up window
                    }
                } else {
                    Notifications.getInstance().show(Notifications.Type.ERROR, "Registration failed. User might already exist.");
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Passwords don't match. Try again!");
            }
        });
        passwordStrengthStatus = new PasswordStrengthStatus();

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "arc:20;[light]background:darken(@background,3%);[dark]background:lighten(@background,3%)");

        txtFirstName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "First name");
        txtLastName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Last name");
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Re-enter your password");

        JLabel lbTitle = new JLabel("Welcome to our Pokemon Application");
        JLabel description = new JLabel("Join us to play a wonderful pokemon game, made by a newb student, hope you like it!");

        passwordStrengthStatus.initPasswordField(txtPassword);

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Full Name"), "gapy 10");
        panel.add(txtFirstName, "split 2");
        panel.add(txtLastName);
        panel.add(new JLabel("Gender"), "gapy 8");
        panel.add(createGenderPanel());
        panel.add(new JSeparator(), "gapy 5 5");
        panel.add(new JLabel("Username or Email"));
        panel.add(txtUsername);
        panel.add(new JLabel("Password"), "gapy 8");
        panel.add(txtPassword);
        panel.add(passwordStrengthStatus, "gapy 0");
        panel.add(new JLabel("Confirm Password"), "gapy 0");
        panel.add(txtConfirmPassword);
        panel.add(cmdRegister, "gapy 20");
        add(panel);
    }

    private Component createGenderPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        jrMale = new JRadioButton("Male");
        jrFemale = new JRadioButton("Female");
        groupGender = new ButtonGroup();
        groupGender.add(jrMale);
        groupGender.add(jrFemale);
        jrMale.setSelected(true);
        panel.add(jrMale);
        panel.add(jrFemale);
        return panel;
    }

    /**
     * Checks if the passwords match.
     *
     * @return True if the passwords match, false otherwise.
     */
    public boolean isMatchPassword() {
        String password = String.valueOf(txtPassword.getPassword());
        String confirmPassword = String.valueOf(txtConfirmPassword.getPassword());
        return password.equals(confirmPassword);
    }

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JRadioButton jrMale;
    private JRadioButton jrFemale;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private ButtonGroup groupGender;
    private JButton cmdRegister;
    private PasswordStrengthStatus passwordStrengthStatus;
}
