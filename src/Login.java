import java.awt.*;
import javax.swing.*;

public class Login extends BaseFrame {

    private JPanel logInPanel;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton loginButton;

    private Image background, titleImage, logInbttn, signInbttn;
    private Image logInHover, signInHover;

    public Login() {
        super("Login", 400, 480);

        // Make sure database tables exist
        DatabaseManager.createUsersTable();
        DatabaseManager.createTable();
        setResizable(false);
    }

    @Override
    protected void initializeComponents() {
        background = new ImageIcon("assets/logIn.png").getImage();
        titleImage = new ImageIcon("assets/titleLabel.png").getImage();
        logInbttn = new ImageIcon("assets/logInBttn.png").getImage();
        signInbttn = new ImageIcon("assets/signInBttn.png").getImage();
        logInHover = new ImageIcon("assets/logInHover.png").getImage();
        signInHover = new ImageIcon("assets/signInHover.png").getImage();

        logInPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    };

        titleLabel = new JLabel();
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton();
        signInButton = new JButton();
    }

    @Override
    protected void setupLayout() {
        logInPanel.setBounds(0, 0, 400, 480);
        logInPanel.setLayout(null);

        titleLabel.setIcon(new ImageIcon(titleImage));
        titleLabel.setBounds(44, 45, 300, 60);

        usernameLabel.setBounds(60, 180, 100, 20);
        usernameField.setBounds(60, 200, 260, 25);

        passwordLabel.setBounds(60, 240, 100, 20);
        passwordField.setBounds(60, 260, 260, 25);

        loginButton.setBounds(100, 300, 170, 50);
        loginButton.setIcon(new ImageIcon(logInbttn));


        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            loginButton.setIcon(new ImageIcon(logInHover));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            loginButton.setIcon(new ImageIcon(logInbttn));
        }
    });

        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(false);

        signInButton.setBounds(100, 360, 170, 50);
        signInButton.setIcon(new ImageIcon(signInbttn));



        signInButton.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            signInButton.setIcon(new ImageIcon(signInHover));
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            signInButton.setIcon(new ImageIcon(signInbttn));
        }
    });

        signInButton.setBorderPainted(false);
        signInButton.setContentAreaFilled(false);
        signInButton.setFocusPainted(false);
        signInButton.setOpaque(false);

        logInPanel.add(titleLabel);
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
        boolean success = DatabaseManager.login(username, password);

        if (success) {

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