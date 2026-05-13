import java.awt.*;
import javax.swing.*;

public class Login extends BaseFrame {

    private JPanel logInPanel;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel loginLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton loginButton;

    public Login() {
        super("Login", 400, 480);

        // Make sure database tables exist
        DatabaseManager.createUsersTable();
        DatabaseManager.createTable();
    }

    @Override
    protected void initializeComponents() {
        logInPanel = new JPanel();
        titleLabel = new JLabel("PASSWORD VAULT");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        loginLabel = new JLabel("Log-In");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Log-in");
        signInButton = new JButton("Sign-In");
    }

    @Override
    protected void setupLayout() {
        logInPanel.setBackground(new Color(255, 204, 204));
        logInPanel.setLayout(null);

        titleLabel.setFont(
            new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 36)
        );
        titleLabel.setBounds(60, 30, 300, 60);

        loginLabel.setBounds(60, 150, 80, 20);

        usernameLabel.setBounds(60, 180, 100, 20);
        usernameField.setBounds(60, 200, 260, 25);

        passwordLabel.setBounds(60, 240, 100, 20);
        passwordField.setBounds(60, 260, 260, 25);

        loginButton.setBounds(100, 300, 170, 50);
        signInButton.setBounds(100, 380, 170, 50);

        logInPanel.add(titleLabel);
        logInPanel.add(loginLabel);
        logInPanel.add(usernameLabel);
        logInPanel.add(usernameField);
        logInPanel.add(passwordLabel);
        logInPanel.add(passwordField);
        logInPanel.add(loginButton);
        logInPanel.add(signInButton);

        add(logInPanel);
    }

    @Override
    protected void setupEvents() {
        loginButton.addActionListener(e -> onLogin());
        signInButton.addActionListener(e -> openSignIn());
    }

    // Open Sign-Up Window
    private void openSignIn() {
        new SignIn().setVisible(true);
        dispose();
    }

    // =====================
    // METHODS
    // =====================

    // Login Logic
    private void onLogin() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter both username and password.",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // NOTE:
        // For now we store password directly.
        // Later you should hash it before login.
        boolean success = DatabaseManager.login(username, password);

        if (success) {

            // ✅ SET SESSION HERE
            int userId = DatabaseManager.getUserId(username);
            MainSession.userId = userId;
            MainSession.username = username;

            if (username.equals("admin")) {
                new AdminMain().setVisible(true);
            } else {
                new Main().setVisible(true);
            }

            dispose();
        }else {
            JOptionPane.showMessageDialog(
                this,
                "Invalid username or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}