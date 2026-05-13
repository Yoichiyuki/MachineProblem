import java.awt.*;
import javax.swing.*;

public class AdminMain extends Main {

    private JLabel userCountLabel, displayLabel;
    private JButton refreshButton;
    private JPanel adminPanel;

    public AdminMain() {
        super();
        setTitle("Admin Dashboard");
    }

    // =========================
    // UI COMPONENTS
    // =========================
    @Override
    protected void initializeComponents() {

        super.initializeComponents();

        displayLabel = new JLabel("Total Users:");
        displayLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        userCountLabel = new JLabel("0");
        userCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        refreshButton = new JButton("Refresh Count");

        adminPanel = new JPanel(null);
        adminPanel.setPreferredSize(new Dimension(400, 100));
    }

    // =========================
    // LAYOUT
    // =========================
    @Override
    protected void setupLayout() {

        super.setupLayout();

        displayLabel.setBounds(20, 10, 150, 30);
        userCountLabel.setBounds(140, 10, 100, 30);
        refreshButton.setBounds(20, 50, 150, 30);

        adminPanel.add(displayLabel);
        adminPanel.add(userCountLabel);
        adminPanel.add(refreshButton);

        // SAFE: do NOT remove Main UI components
        container.add(adminPanel, BorderLayout.NORTH);
    }

    // =========================
    // EVENTS
    // =========================
    @Override
    protected void setupEvents() {

        super.setupEvents();

        refreshButton.addActionListener(e -> loadUserCount());
    }

    // =========================
    // LOAD USER COUNT
    // =========================
    private void loadUserCount() {
        int count = DatabaseManager.getUserCount();
        userCountLabel.setText(String.valueOf(count));
    }

    public static void main(String[] args) {
        new AdminMain().setVisible(true);
    }
}