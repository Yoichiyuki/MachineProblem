import java.awt.*;
import javax.swing.*;

public class SignIn extends JFrame {

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

    public SignIn() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 480));

        // PANEL
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(204, 204, 255));
        mainPanel.setLayout(null);

        // TITLE
        titleLabel = new JLabel("PASSWORD VAULT");
        titleLabel.setFont(
            new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 36)
        );
        titleLabel.setBounds(60, 30, 300, 60);
        mainPanel.add(titleLabel);

        // SIGN-IN LABEL
        signInLabel = new JLabel("Sign-In");
        signInLabel.setBounds(60, 120, 80, 20);
        mainPanel.add(signInLabel);

        // USERNAME
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(60, 150, 100, 20);
        mainPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(60, 170, 260, 25);
        mainPanel.add(usernameField);

        // PASSWORD
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(60, 200, 100, 20);
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(60, 220, 260, 25);
        mainPanel.add(passwordField);

        // CONFIRM PASSWORD
        confirmLabel = new JLabel("Confirm password");
        confirmLabel.setBounds(60, 250, 150, 20);
        mainPanel.add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(60, 270, 260, 25);
        mainPanel.add(confirmPasswordField);

        // SIGN-IN BUTTON
        signInButton = new JButton("Sign-In");
        signInButton.setBounds(100, 350, 170, 50);
        signInButton.addActionListener(e -> onSignIn());
        mainPanel.add(signInButton);

        // FRAME
        add(mainPanel);
        pack();
    }

    // Sign-Up Logic
    private void onSignIn() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword =
            new String(confirmPasswordField.getPassword());

        // Validation
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

        // NOTE:
        // For now we store password directly.
        // Later you should hash it before storing.
        boolean success = DatabaseManager.signUp(username, password);

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