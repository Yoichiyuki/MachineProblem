import java.awt.*;
import javax.swing.*;

/**
 * ============================================
 * MAIN FRAME (VIEW ITEM DETAILS)
 * ============================================
 *
 * Purpose:
 * - Displays selected vault entry:
 *      • Title
 *      • Username
 *      • Password
 *
 * - Opens edit frame with proper reference
 */

public class mainFrame extends JFrame {

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
    // STORED VALUES (for edit use)
    // =========================
    private int id;
    private String title;
    private String username;
    private String password;
    private Main mainRef;

    // =========================
    // CONSTRUCTOR (UPDATED)
    // =========================
    public mainFrame(int id, String title, String username, String password, Main mainRef){
        // store values
        this.id = id;
        this.title = title;
        this.username = username;
        this.password = password;
        this.mainRef = mainRef;

        setTitle("Password Viewer");
        setSize(320, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
        initializeComponents();
        setData();
        setVisible(true);
    }

    // =========================
    // SET DATA INTO LABELS
    // =========================
    private void setData() {
        appNameLabel.setText(title);
        usernameValue.setText(username);
        passwordValue.setText(password);
    }

    // =========================
    // INIT UI
    // =========================
    private void initializeComponents() {

        mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(255, 102, 102));

        // =========================
        // TITLE
        // =========================
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBounds(30, 20, 240, 50);

        appNameLabel = new JLabel("TITLE", JLabel.CENTER);
        appNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        titlePanel.add(appNameLabel, BorderLayout.CENTER);

        // =========================
        // INFO
        // =========================
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBounds(30, 120, 240, 180);

        usernameTitle = new JLabel("Username:");
        usernameTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        usernameValue = new JLabel();

        passwordTitle = new JLabel("Password:");
        passwordTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        passwordValue = new JLabel();

        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(usernameTitle);
        infoPanel.add(usernameValue);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(passwordTitle);
        infoPanel.add(passwordValue);

        // =========================
        // EDIT BUTTON
        // =========================
        editButton = new JButton("EDIT");
        editButton.setBounds(30, 330, 240, 40);

        editButton.addActionListener(e -> openEditFrame());

        // =========================
        // ADD TO PANEL
        // =========================
        mainPanel.add(titlePanel);
        mainPanel.add(infoPanel);
        mainPanel.add(editButton);

        add(mainPanel);
    }

    // =========================
    // OPEN EDIT FRAME (FIXED)
    // =========================
    private void openEditFrame() {

        // Pass Main reference so UI can refresh after save
        new editFrame(mainRef,id,title,username,password).setVisible(true);
        this.dispose();
    }

    // =========================
    // MAIN (TEST ONLY)
    // =========================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new mainFrame(1 ,"Sample", "user", "pass"   ,null);
        });
    }
}