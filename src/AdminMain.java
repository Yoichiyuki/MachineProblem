import java.awt.*;
import javax.swing.*;

//This is my grandchild class that extends Main. It adds admin-specific features like user count display and refresh button. It reuses all the vault display logic from Main, so I don't have to duplicate code.

public class AdminMain extends Main {

    private JLabel userCountLabel, DisplayLabel;
    private JButton refreshButton;
    private JPanel adminPanel;

    public AdminMain() {
        super();

        setTitle("Admin Dashboard");
    }

    @Override
    protected void initializeComponents() {

        super.initializeComponents();

        userCountLabel = new JLabel();
        userCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        userCountLabel.setBounds(190, 80, 100, 30);
        
        DisplayLabel = new JLabel("Total Users:");
        DisplayLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        DisplayLabel.setBounds(123, 20, 150, 30);

        refreshButton = new JButton("Refresh Count");
        refreshButton.setBounds(132, 170, 120, 30);
    }

    @Override
    protected void setupLayout() {

        super.setupLayout();

        // Add admin panel on top or bottom
        adminPanel = new JPanel();
        adminPanel.setLayout(null);

        adminPanel.add(DisplayLabel);
        adminPanel.add(userCountLabel);
        adminPanel.add(refreshButton);

        container.remove(scrollPane); // Remove existing upper panel
        container.remove(bottomPanel); // Remove existing bottom panel
        container.add(adminPanel, BorderLayout.CENTER);
    }

    @Override
    protected void setupEvents() {

        super.setupEvents();

        refreshButton.addActionListener(e -> loadUserCount());
    }

    private void loadUserCount() {
        int count = DatabaseManager.getUserCount();
        userCountLabel.setText(String.valueOf(count));
    }

    public static void main(String[] args) {
        new AdminMain().setVisible(true);
    }

}