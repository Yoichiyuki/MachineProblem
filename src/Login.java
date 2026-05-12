import java.awt.*;
import javax.swing.*;

public class Login extends JFrame {

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
        // Make sure database tables exist
        DatabaseManager.createUsersTable();
        DatabaseManager.createTable();

        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 480));

        // MAIN PANEL
        logInPanel = new JPanel();
        logInPanel.setBackground(new Color(255, 204, 204));
        logInPanel.setLayout(null);

        // TITLE
        titleLabel = new JLabel("PASSWORD VAULT");
        titleLabel.setFont(
            new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 36)
        );
        titleLabel.setBounds(60, 30, 300, 60);
        logInPanel.add(titleLabel);

        // LOGIN LABEL
        loginLabel = new JLabel("Log-In");
        loginLabel.setBounds(60, 150, 80, 20);
        logInPanel.add(loginLabel);

        // USERNAME
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(60, 180, 100, 20);
        logInPanel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(60, 200, 260, 25);
        logInPanel.add(usernameField);

        // PASSWORD
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(60, 240, 100, 20);
        logInPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(60, 260, 260, 25);
        logInPanel.add(passwordField);

        // LOGIN BUTTON
        loginButton = new JButton("Log-in");
        loginButton.setBounds(100, 300, 170, 50);
        loginButton.addActionListener(e -> onLogin());
        logInPanel.add(loginButton);

        // GO TO SIGN-UP BUTTON
        signInButton = new JButton("Sign-In");
        signInButton.setBounds(100, 380, 170, 50);
        signInButton.addActionListener(e -> openSignIn());
        logInPanel.add(signInButton);

        // FRAME
        add(logInPanel);
        pack();
    }

    // Open Sign-Up Window
    private void openSignIn() {
        new SignIn().setVisible(true);
        dispose();
    }

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
            JOptionPane.showMessageDialog(
                this,
                "Login successful!"
            );

            new Main().setVisible(true);
            dispose();

        } else {
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