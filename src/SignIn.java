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

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton signInButton;

    private Image signInBG, titleImage;
    private Image signinbttn, signinhover;


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
        signInBG = new ImageIcon("assets/signInBG.png").getImage();
        titleImage = new ImageIcon("assets/titleLabel.png").getImage();
        signinbttn = new ImageIcon("assets/signinbttn2.png").getImage();
        signinhover = new ImageIcon("assets/signinhover2.png").getImage();

        // Main panel
        mainPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(signInBG, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null);

        // Labels
        titleLabel = new JLabel();

        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        confirmLabel = new JLabel("Confirm password");

        // Input fields
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        // Button
        signInButton = new JButton();
    }

    // =========================
    // ARRANGE COMPONENTS
    // =========================
    @Override
    protected void setupLayout() {

        // Title
        titleLabel.setIcon(new ImageIcon(titleImage));
        titleLabel.setBounds(44, 45, 300, 60);

        // Labels
        usernameLabel.setBounds(60, 235, 100, 20);
        usernameField.setBounds(60, 255, 260, 25);

        passwordLabel.setBounds(60, 280, 100, 20);
        passwordField.setBounds(60, 300, 260, 25);

        confirmLabel.setBounds(60, 323, 150, 20);
        confirmPasswordField.setBounds(60, 343, 260, 25);

        // Button
        signInButton.setBounds(143, 378, 90, 30);
        signInButton.setIcon(new ImageIcon(signinbttn));

        signInButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                signInButton.setIcon(new ImageIcon(signinhover));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                signInButton.setIcon(new ImageIcon(signinbttn));
            }
        });

        signInButton.setBorderPainted(false);
        signInButton.setContentAreaFilled(false);
        signInButton.setFocusPainted(false);
        signInButton.setOpaque(false);       

        // Add components to panel
        mainPanel.add(titleLabel);
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