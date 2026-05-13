import java.awt.*;
import javax.swing.*;

/**
 * ============================================
 * EDIT / ADD FRAME
 * ============================================
 *
 * Purpose:
 * - Add new vault entry OR edit existing one
 * - Saves data to database
 */
public class editFrame extends BaseFrame {

    // =========================
    // UI COMPONENTS
    // =========================
    private JPanel mainPanel;

    private JTextField titleField, userField, passField;
    private JLabel userLabel, passLabel, titleLabel;
    private JButton saveButton;

    // =========================
    // MAIN REFERENCE
    // =========================
    private Main mainRef;

    // =========================
    // EDIT MODE DATA
    // =========================
    private int itemId = -1;
    private boolean editMode = false;

    // =========================
    // ADD MODE CONSTRUCTOR
    // =========================
    public editFrame(Main mainRef) {

        super("Add Item", 320, 450);

        this.mainRef = mainRef;
    }

    // =========================
    // EDIT MODE CONSTRUCTOR
    // =========================
    public editFrame(Main mainRef,
                     int itemId,
                     String title,
                     String username,
                     String password) {

        super("Edit Item", 320, 450);

        this.mainRef = mainRef;
        this.itemId = itemId;
        this.editMode = true;

        // set values AFTER UI creation
        SwingUtilities.invokeLater(() -> {
            titleField.setText(title);
            userField.setText(username);
            passField.setText(password);
        });
    }

    // =========================
    // CREATE COMPONENTS
    // =========================
    @Override
    protected void initializeComponents() {

        mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(204, 204, 255));

        titleLabel = new JLabel("App name:");

        titleField = new JTextField();
        userLabel = new JLabel("Username :");
        userField = new JTextField();

        passLabel = new JLabel("Password :");
        passField = new JTextField();

        saveButton = new JButton("SAVE");
    }

    // =========================
    // ARRANGE COMPONENTS
    // =========================
    @Override
    protected void setupLayout() {

        titleLabel.setBounds(40, 5, 120, 30);
        titleField.setBounds(40, 35, 220, 40);

        userLabel.setBounds(40, 120, 120, 30);
        userField.setBounds(40, 155, 220, 30);

        passLabel.setBounds(40, 220, 120, 30);
        passField.setBounds(40, 255, 220, 30);

        saveButton.setBounds(30, 340, 240, 40);

        mainPanel.add(titleLabel);
        mainPanel.add(titleField);
        mainPanel.add(userLabel);
        mainPanel.add(userField);
        mainPanel.add(passLabel);
        mainPanel.add(passField);
        mainPanel.add(saveButton);

        add(mainPanel);
    }

    // =========================
    // EVENTS
    // =========================
    @Override
    protected void setupEvents() {

        saveButton.addActionListener(e -> saveData());
    }

    // =========================
    // SAVE LOGIC
    // =========================
    private void saveData() {

        String title = titleField.getText();
        String user = userField.getText();
        String pass = passField.getText();

        // validation
        if (title.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return;
        }

        // database operation
        if (editMode) {
            DatabaseManager.updateData(itemId, title, user, pass);
        } else {
            DatabaseManager.insertData(title, user, pass);
        }

        // refresh main UI
        if (mainRef != null) {
            mainRef.refreshData();
        }

        dispose();
    }
}