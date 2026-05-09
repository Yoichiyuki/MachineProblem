import java.awt.*;
import javax.swing.*;

/**
 * ============================================
 * EDIT FRAME
 * ============================================
 *
 * Custom-coded version of your NetBeans UI
 * while keeping your current project style.
 */

public class editFrame extends JFrame {

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
    // FOR DATABASE_UPDATE 
    // =========================
    private int itemId = -1;
    private boolean editMode = false;

    // =========================
    // CONSTRUCTOR
    // =========================
    public editFrame(Main mainRef) {

        this.mainRef = mainRef;

        setTitle("Add Item");
        setSize(320, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();

        setVisible(true);
    }

    // =========================
    // CONSTRUCTOR for DATA EDIT (with pre-filled values)
    // =========================

    public editFrame(
            Main mainRef,
            int itemId,
            String title,
            String username,
            String password
        ) {

        this.mainRef = mainRef;
        this.itemId = itemId;
        this.editMode = true;

        setTitle("Edit Item");

        setSize(320, 450);

        setResizable(false);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        // Create UI FIRST
        initUI();

        // THEN set values
        titleField.setText(title);
        userField.setText(username);
        passField.setText(password);

        setVisible(true);
    }

    // =========================
    // UI SETUP
    // =========================
    private void initUI() {

        // =========================
        // MAIN PANEL
        // =========================
        mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(204, 204, 255));

        // =========================
        // TITLE LABEL
        // =========================
        titleLabel = new JLabel("App name:");
        titleLabel.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                14
        ));
        titleLabel.setBounds(40, 5, 120, 30);
        // =========================
        // TITLE FIELD
        // =========================
        titleField = new JTextField();
        titleField.setHorizontalAlignment(JTextField.CENTER);

        titleField.setBounds(40, 35, 220, 40);

        // =========================
        // USER LABEL
        // =========================
        userLabel = new JLabel("Username :");

        userLabel.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                14
        ));

        userLabel.setBounds(40, 120, 120, 30);

        // =========================
        // USER FIELD
        // =========================
        userField = new JTextField();

        userField.setBounds(40, 155, 220, 30);

        // =========================
        // PASSWORD LABEL
        // =========================
        passLabel = new JLabel("Password :");

        passLabel.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                14
        ));

        passLabel.setBounds(40, 220, 120, 30);

        // =========================
        // PASSWORD FIELD
        // =========================
        passField = new JTextField();

        passField.setBounds(40, 255, 220, 30);

        // =========================
        // SAVE BUTTON
        // =========================
        saveButton = new JButton("SAVE");

        saveButton.setBounds(30, 340, 240, 40);

        saveButton.addActionListener(e -> saveData());

        // =========================
        // ADD COMPONENTS
        // =========================
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
    // SAVE DATA
    // =========================
    private void saveData() {

        String title = titleField.getText();
        String user = userField.getText();
        String pass = passField.getText();

        // validation
        if (title.isEmpty()
                || user.isEmpty()
                || pass.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Fill all fields!"
            );

            return;
        }

        // save to database
        if (editMode) {
        DatabaseManager.updateData(itemId, title, user, pass);
            } else {
                DatabaseManager.insertData(title, user, pass);
            }
        // refresh Main UI
        if (mainRef != null) {
            mainRef.refreshData();
        }

        dispose();
    }

    // =========================
    // TEST MAIN
    // =========================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new editFrame(null);
        });
    }
}