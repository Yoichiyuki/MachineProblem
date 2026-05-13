import java.awt.*;
import javax.swing.*;

public class editFrame extends BaseFrame {

    private JPanel mainPanel;

    private JTextField titleField, userField, passField;
    private JLabel titleLabel, userLabel, passLabel;
    private JButton saveButton;

    private final Main mainRef;

    private int itemId = -1;
    private boolean editMode = false;

    // =========================
    // ADD MODE
    // =========================
    public editFrame(Main mainRef) {
        super("Add Item", 320, 450);
        this.mainRef = mainRef;
    }

    // =========================
    // EDIT MODE
    // =========================
    public editFrame(Main mainRef, int itemId, String title, String username, String password) {
        super("Edit Item", 320, 450);

        this.mainRef = mainRef;
        this.itemId = itemId;
        this.editMode = true;

        SwingUtilities.invokeLater(() -> {
            titleField.setText(title);
            userField.setText(username);
            passField.setText(password);
        });
    }

    // =========================
    // INIT UI
    // =========================
    @Override
    protected void initializeComponents() {

        mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(220, 220, 255));

        titleLabel = new JLabel("App Name:");
        userLabel = new JLabel("Username:");
        passLabel = new JLabel("Password:");

        titleField = new JTextField();
        userField = new JTextField();
        passField = new JTextField();

        saveButton = new JButton("SAVE");
    }

    // =========================
    // LAYOUT
    // =========================
    @Override
    protected void setupLayout() {

        titleLabel.setBounds(40, 10, 200, 25);
        titleField.setBounds(40, 35, 220, 35);

        userLabel.setBounds(40, 100, 200, 25);
        userField.setBounds(40, 125, 220, 35);

        passLabel.setBounds(40, 190, 200, 25);
        passField.setBounds(40, 215, 220, 35);

        saveButton.setBounds(40, 300, 220, 40);

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
    // SAVE LOGIC (FIXED)
    // =========================
    private void saveData() {

        String title = titleField.getText();
        String user = userField.getText();
        String pass = passField.getText();

        if (title.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields!");
            return;
        }

        if (editMode) {
            DatabaseManager.updateData(itemId, title, user, pass);
        } else {
            // IMPORTANT FIX: user-specific storage
            DatabaseManager.insertData(MainSession.userId, title, user, pass);
        }

        if (mainRef != null) {
            mainRef.refreshData();
        }

        dispose();
    }
}