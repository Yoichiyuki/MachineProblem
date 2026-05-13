import java.awt.*;
import javax.swing.*;

/**
 * ============================================
 * MAIN FRAME (VIEW ITEM DETAILS)
 * ============================================
 *
 * Purpose:
 * - Displays selected vault entry
 * - Shows title, username, and password
 * - Opens edit frame with existing data
 */
public class mainFrame extends BaseFrame {

    // =========================
    // UI COMPONENTS
    // =========================
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JPanel infoPanel;

    private JLabel appNameLabel;
    private JLabel usernameTitle;
    private JLabel usernameValue;
    private JLabel passwordTitle;
    private JLabel passwordValue;

    private JButton editButton;

    // =========================
    // STORED VALUES
    // =========================
    private final int id;
    private final String title;
    private final String username;
    private final String password;
    private final Main mainRef;

    // =========================
    // CONSTRUCTOR
    // =========================
    public mainFrame(
            int id,
            String title,
            String username,
            String password,
            Main mainRef) {

        // BaseFrame handles all common frame setup
        super("Password Viewer", 320, 450);

        // Store values for display and editing
        this.id = id;
        this.title = title;
        this.username = username;
        this.password = password;
        this.mainRef = mainRef;

        // Populate labels with actual data
        setData();
    }

    // =========================
    // CREATE COMPONENTS
    // =========================
    @Override
    protected void initializeComponents() {

        mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(255, 102, 102));

        // Title section
        titlePanel = new JPanel(new BorderLayout());

        appNameLabel = new JLabel("TITLE", JLabel.CENTER);
        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Information section
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        usernameTitle = new JLabel("Username:");
        usernameTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        usernameValue = new JLabel();

        passwordTitle = new JLabel("Password:");
        passwordTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        passwordValue = new JLabel();

        // Edit button
        editButton = new JButton("EDIT");
    }

    // =========================
    // ARRANGE COMPONENTS
    // =========================
    @Override
    protected void setupLayout() {

        // Title panel
        titlePanel.setBounds(30, 20, 240, 50);
        titlePanel.add(appNameLabel, BorderLayout.CENTER);

        // Information panel
        infoPanel.setBounds(30, 120, 240, 180);

        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(usernameTitle);
        infoPanel.add(usernameValue);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(passwordTitle);
        infoPanel.add(passwordValue);

        // Edit button
        editButton.setBounds(30, 330, 240, 40);

        // Add everything to main panel
        mainPanel.add(titlePanel);
        mainPanel.add(infoPanel);
        mainPanel.add(editButton);

        // Add panel to frame
        add(mainPanel);
    }

    // =========================
    // REGISTER EVENT LISTENERS
    // =========================
    @Override
    protected void setupEvents() {
        editButton.addActionListener(e -> openEditFrame());
    }

    // =========================
    // DISPLAY ENTRY DATA
    // =========================
    private void setData() {
        appNameLabel.setText(title);
        usernameValue.setText(username);
        passwordValue.setText(password);
    }

    // =========================
    // OPEN EDIT FRAME
    // =========================
    private void openEditFrame() {

        // Pass Main reference so the dashboard can refresh after save
        new editFrame(
                mainRef,
                id,
                title,
                username,
                password
        ).setVisible(true);

        dispose();
    }

    // =========================
    // MAIN (TEST ONLY)
    // =========================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new mainFrame(1, "Sample", "user", "pass", null)
                        .setVisible(true)
        );
    }
}