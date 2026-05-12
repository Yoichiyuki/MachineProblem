import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * ============================================
 * MAIN DASHBOARD (FINAL CLEAN VERSION)
 * ============================================
 *
 * Purpose:
 * - Displays all saved vault items from database
 * - Supports Add, Delete, Open item
 * - Refreshes UI after database changes
 */

public class Main extends JFrame {

    // =========================
    // UI COMPONENTS
    // =========================
    private JPanel container;
    private JScrollPane scrollPane;
    private JPanel listPanel, bottomPanel, upperPanel;
    private JLabel titleLabel;
    private JButton addButton;
    private JToggleButton deleteButton;

    // =========================
    // STATE
    // =========================
    private boolean deleteMode = false;

    // =========================
    // CONSTRUCTOR
    // =========================
    public Main() {

        initComponents();
        loadData();

        setLocationRelativeTo(null);
    }

    // =========================
    // INIT UI
    // =========================
    private void initComponents() {

        setTitle("Password Vault");
        setSize(400, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        container = new JPanel(new BorderLayout());

        // =========================
        // TOP BAR
        // =========================
        upperPanel = new JPanel(null);
        upperPanel.setPreferredSize(new Dimension(400, 60));

        titleLabel = new JLabel("PASSWORD VAULT", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBounds(0, 15, 400, 40);

        upperPanel.add(titleLabel);

        // =========================
        // LIST PANEL
        // =========================
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(listPanel);
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        // =========================
        // BOTTOM PANEL
        // =========================
        bottomPanel = new JPanel(new FlowLayout());

        addButton = new JButton("ADD");
        addButton.addActionListener(e -> onAdd());

        deleteButton = new JToggleButton("DELETE");
        deleteButton.addActionListener(e -> toggleDelete());

        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);

        // =========================
        // ADD TO FRAME
        // =========================
        container.add(upperPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);

        add(container);
    }

    // =========================
    // ADD BUTTON
    // =========================
    private void onAdd() {

        new editFrame(this).setVisible(true);
    }

    // =========================
// DELETE MODE TOGGLE
// =========================
private void toggleDelete() {

    // If the toggle button is ON, enter delete mode.
    // If the toggle button is OFF, perform deletion.
    deleteMode = deleteButton.isSelected();

    if (!deleteMode) {
        deleteSelectedItems();
    }
}

// =========================
// DELETE ITEMS (UI + DATABASE)
// =========================
private void deleteSelectedItems() {

    // Get all components in the list panel
    Component[] comps = listPanel.getComponents();

    // Loop backwards so removing components is safe
    for (int i = comps.length - 1; i >= 0; i--) {

        // Only process toggle buttons that are selected
        if (comps[i] instanceof JToggleButton btn && btn.isSelected()) {

            // Get the title from the button text
            String title = btn.getText();

            // Delete from database
            DatabaseManager.deleteByTitle(title);

            // Remove the vertical spacer right after the button (if it exists)
            if (i + 1 < comps.length && comps[i + 1] instanceof Box.Filler) {
                listPanel.remove(i + 1);
            }

            // Remove the button itself
            listPanel.remove(i);
        }
    }

    // Exit delete mode
    deleteMode = false;
    deleteButton.setSelected(false);

    // Reload everything from the database
    refreshData();
}

    // =========================
    // LOAD DATA FROM DATABASE
    // =========================
    private void loadData() {

        List<String[]> data = DatabaseManager.getDataList();

        for (String[] row : data) {

            int id = Integer.parseInt(row[0]);
            String title = row[1];
            String username = row[2];
            String password = row[3];

            JToggleButton item = new JToggleButton(title);

            Dimension buttonSize = new Dimension(350, 50);

            item.setPreferredSize(buttonSize);
            item.setMinimumSize(buttonSize);
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            item.setAlignmentX(Component.CENTER_ALIGNMENT);

            item.addActionListener(e -> {

            if (!deleteMode) {

                // Open details frame
                mainFrame frame = new mainFrame(id, title, username, password, this);

                // Untoggle button immediately
                item.setSelected(false);
                frame.setVisible(true);
            }
        });

            listPanel.add(item);
            listPanel.add(Box.createVerticalStrut(8));
        }
    }

    // =========================
    // REFRESH UI AFTER DB CHANGE
    // =========================
    public void refreshData() {

        listPanel.removeAll();
        loadData();

        listPanel.revalidate();
        listPanel.repaint();
    }

    // =========================
    // MAIN METHOD
    // =========================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}