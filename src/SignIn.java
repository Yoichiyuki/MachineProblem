import java.awt.*;
import javax.swing.*;

/**
 * ============================================
 * SIGN-IN FRAME
 * ============================================
 *
 * Purpose:
 * - Creates a new user account
 * - Validates user input
 * - Returns to login after successful registration
 */
public class SignIn extends BaseFrame {

    // =========================
    // UI COMPONENTS
    // =========================
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmLabel;
    private JLabel signInLabel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton signInButton;

    // =========================
    // CONSTRUCTOR
    // =========================
    public SignIn() {
        // BaseFrame handles title, size, centering, and setup
        super("Sign In", 400, 480);
    }

    // =========================
    // CREATE COMPONENTS
    // =========================
    @Override
    protected void initializeComponents() {

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(204, 204, 255));
        mainPanel.setLayout(null);

        // Labels
        titleLabel = new JLabel("PASSWORD VAULT");
        titleLabel.setFont(
                new Font(
                        "Tw Cen MT Condensed Extra Bold",
                        Font.BOLD,
                        36
                )
        );

        signInLabel = new JLabel("Sign-In");

        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        confirmLabel = new JLabel("Confirm password");

        // Input fields
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        // Button
        signInButton = new JButton("Sign-In");
    }

    // =========================
    // ARRANGE COMPONENTS
    // =========================
    @Override
    protected void setupLayout() {

        // Title
        titleLabel.setBounds(60, 30, 300, 60);

        // Labels
        signInLabel.setBounds(60, 120, 80, 20);

        usernameLabel.setBounds(60, 150, 100, 20);
        usernameField.setBounds(60, 170, 260, 25);

        passwordLabel.setBounds(60, 200, 100, 20);
        passwordField.setBounds(60, 220, 260, 25);

        confirmLabel.setBounds(60, 250, 150, 20);
        confirmPasswordField.setBounds(60, 270, 260, 25);

        // Button
        signInButton.setBounds(100, 350, 170, 50);

        // Add components to panel
        mainPanel.add(titleLabel);
        mainPanel.add(signInLabel);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(confirmLabel);
        mainPanel.add(confirmPasswordField);
        mainPanel.add(signInButton);

        // Add panel to frame
        add(mainPanel);
    }

    // =========================
    // REGISTER EVENT LISTENERS
    // =========================
    @Override
    protected void setupEvents() {
        signInButton.addActionListener(e -> onSignIn());
    }

    // =========================
    // SIGN-UP LOGIC
    // =========================
    private void onSignIn() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        // Validate input
        if (username.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Save new account
        boolean success = DatabaseManager.signUp(
                username,
                password
        );

        if (success) {
            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!"
            );

            new Login().setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Username already exists.",
                    "Sign-In Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}