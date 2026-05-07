
import java.awt.*;
import javax.swing.*;

/**
 * Simple Edit Frame
 * Purpose:
 * - Lets the user edit:
 *      • App/Title name
 *      • Username
 *      • Password
 * - Has a SAVE button
 */
public class editFrame extends JFrame {

    // =========================
    // COMPONENTS
    // =========================

    // Main container panel
    private JPanel mainPanel;

    // Labels
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    // Text fields
    private JTextField titleField;
    private JTextField usernameField;
    private JTextField passwordField;

    // Button
    private JButton saveButton;

    // =========================
    // CONSTRUCTOR
    // =========================

    public editFrame() {

        // Frame settings
        setTitle("Edit Item");
        setSize(320, 450);
        setLocationRelativeTo(null); // center screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize UI
        initializeComponents();

        // Make frame visible
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
        mainPanel.setBackground(new Color(204, 204, 255));

        // Using absolute positioning
        // (good for fixed-size apps)
        mainPanel.setLayout(null);

        // -------------------------
        // TITLE FIELD
        // -------------------------
        titleField = new JTextField("INPUT APP NAME");
        titleField.setHorizontalAlignment(JTextField.CENTER);
        titleField.setBounds(40, 30, 220, 35);

        // -------------------------
        // USERNAME LABEL
        // -------------------------
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setBounds(40, 120, 100, 25);

        // -------------------------
        // USERNAME FIELD
        // -------------------------
        usernameField = new JTextField();
        usernameField.setBounds(40, 150, 220, 35);

        // -------------------------
        // PASSWORD LABEL
        // -------------------------
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setBounds(40, 220, 100, 25);

        // -------------------------
        // PASSWORD FIELD
        // -------------------------
        passwordField = new JTextField();
        passwordField.setBounds(40, 250, 220, 35);

        // -------------------------
        // SAVE BUTTON
        // -------------------------
        saveButton = new JButton("SAVE");
        saveButton.setBounds(40, 330, 220, 40);

        // Button action
        saveButton.addActionListener(e -> saveData());

        // =========================
        // ADD COMPONENTS TO PANEL
        // =========================

        mainPanel.add(titleField);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(saveButton);

        // Add panel to frame
        add(mainPanel);
    }

    // =========================
    // SAVE BUTTON FUNCTION
    // =========================

    private void saveData() {

        // Get values from fields
        String title = titleField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Example output
        System.out.println("Title: " + title);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Example popup
        JOptionPane.showMessageDialog(
                this,
                "Data Saved!"
        );
    }

    // =========================
    // MAIN METHOD
    // =========================

    public static void main(String[] args) {

        // Run GUI safely
        SwingUtilities.invokeLater(() -> {
            new editFrame();
        });
    }
}