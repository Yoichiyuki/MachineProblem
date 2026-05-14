import java.awt.*;
import javax.swing.*;

public class editFrame extends BaseFrame {

    private JPanel mainPanel;

    private JTextField titleField, userField, passField;
    private JLabel titleLabel, userLabel, passLabel;
    private JButton saveButton;

    private Image editFrameBG, saveBttn, saveHover;

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
        saveBttn = new ImageIcon("assets/SaveBttn.png").getImage();
        saveHover = new ImageIcon("assets/SaveHover.png").getImage();
        editFrameBG = new ImageIcon("assets/editFrameBG.png").getImage();
        mainPanel = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(editFrameBG, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setBackground(new Color(220, 220, 255));

        titleLabel = new JLabel("App Name:");
        userLabel = new JLabel("Username:");
        passLabel = new JLabel("Password:");

        titleField = new JTextField();
        userField = new JTextField();
        passField = new JTextField();

        saveButton = new JButton();
        
    }

    // =========================
    // LAYOUT
    // =========================
    @Override
    protected void setupLayout() {

        titleLabel.setBounds(40, 45, 200, 25);
        titleField.setBounds(40, 65, 220, 35);

        userLabel.setBounds(40, 125, 200, 25);
        userField.setBounds(40, 145, 220, 35);

        passLabel.setBounds(40, 205, 200, 25);
        passField.setBounds(40, 235, 220, 35);

        saveButton.setBounds(40, 300, 220, 40);
        saveButton.setIcon(new ImageIcon(saveBttn));

        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                saveButton.setIcon(new ImageIcon(saveHover));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                saveButton.setIcon(new ImageIcon(saveBttn));
            }
        });

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