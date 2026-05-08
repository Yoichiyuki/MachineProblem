import java.awt.*;
import javax.swing.*;

/**
 * Main Display Frame
 *
 * Purpose:
 * - Displays:
 *      • App name
 *      • Username
 *      • Password
 * - Has an EDIT button
 */
public class mainFrame extends JFrame {

    // =========================
    // COMPONENTS
    // =========================

    // Main panel
    private JPanel mainPanel;

    // Top title panel
    private JPanel titlePanel;

    // Info panel
    private JPanel infoPanel;

    // Labels
    private JLabel appNameLabel;
    private JLabel usernameTitle;
    private JLabel usernameValue;
    private JLabel passwordTitle;
    private JLabel passwordValue;

    // Button
    private JButton editButton;

    // =========================
    // CONSTRUCTOR
    // =========================

    public mainFrame() {
        this(null);
    }
    
    public mainFrame(JToggleButton sourceButton) {

        // Frame settings
        setTitle("Password Viewer");
        setSize(320, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

         // Add window listener to untoggle button on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (sourceButton != null) {
                    sourceButton.setSelected(false);
                }
            }
        });
        // Initialize UI
        initializeComponents();

        // Make visible
        setVisible(true);
    }

    // =========================
    // INITIALIZE COMPONENTS
    // =========================

    private void initializeComponents() {

        // -------------------------
        // MAIN PANEL
        // -------------------------
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 102, 102));

        // Absolute layout
        mainPanel.setLayout(null);

        // =========================
        // TITLE PANEL
        // =========================

        titlePanel = new JPanel();
        titlePanel.setBounds(30, 20, 240, 50);

        // App name label
        appNameLabel = new JLabel("PLACEHOLDER");
        appNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(appNameLabel, BorderLayout.CENTER);

        // =========================
        // INFO PANEL
        // =========================

        infoPanel = new JPanel();
        infoPanel.setBounds(30, 120, 240, 180);

        // Use vertical layout
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        // Username title
        usernameTitle = new JLabel("Username:");
        usernameTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Username value
        usernameValue = new JLabel("PLACEHOLDER");

        // Password title
        passwordTitle = new JLabel("Password:");
        passwordTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Password value
        passwordValue = new JLabel("PLACEHOLDER");

        // Add spacing and components
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(usernameTitle);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(usernameValue);

        infoPanel.add(Box.createVerticalStrut(30));

        infoPanel.add(passwordTitle);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(passwordValue);

        // =========================
        // EDIT BUTTON
        // =========================

        editButton = new JButton("EDIT");
        editButton.setBounds(30, 330, 240, 40);

        // Button action
        editButton.addActionListener(e -> openEditFrame());

        // =========================
        // ADD COMPONENTS
        // =========================

        mainPanel.add(titlePanel);
        mainPanel.add(infoPanel);
        mainPanel.add(editButton);

        // Add main panel to frame
        add(mainPanel);
    }

    // =========================
    // OPEN EDIT WINDOW
    // =========================

    private void openEditFrame() {

        // Open another frame
        new editFrame().setVisible(true);
        this.dispose(); // Close current frame
    }

    // =========================
    // SETTERS
    // =========================
    // Use these to update displayed data

    public void setAppName(String text) {
        appNameLabel.setText(text);
    }

    public void setUsername(String text) {
        usernameValue.setText(text);
    }

    public void setPassword(String text) {
        passwordValue.setText(text);
    }

    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            mainFrame frame = new mainFrame();

            // Sample data
            frame.setAppName("Facebook");
            frame.setUsername("john_doe");
            frame.setPassword("12345678");
        });
    }
}