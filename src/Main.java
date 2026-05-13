import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * ============================================
 * MAIN DASHBOARD
 * ============================================
 *
 * Purpose:
 * - Displays all saved vault items
 * - Supports Add, Delete, and Open item
 * - Refreshes UI after database changes
 */
public class Main extends BaseFrame {

    // =========================
    // UI COMPONENTS
    // =========================
    public JPanel container;
    public JScrollPane scrollPane;
    public JPanel listPanel, bottomPanel, upperPanel;
    public  JLabel titleLabel;
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
        // BaseFrame handles:
        // - setTitle()
        // - setSize()
        // - setLocationRelativeTo()
        // - setDefaultCloseOperation()
        // - initializeComponents()
        // - setupLayout()
        // - setupEvents()
        super("Password Vault", 400, 480);

        // Additional frame settings specific to Main
        setResizable(false);

        // Load vault entries from the database
        loadData();
    }

    // =========================
    // CREATE COMPONENTS
    // =========================
    @Override
    protected void initializeComponents() {

        // Main container
        container = new JPanel(new BorderLayout());

        // Top panel
        upperPanel = new JPanel(null);
        upperPanel.setPreferredSize(new Dimension(400, 60));

        titleLabel = new JLabel("PASSWORD VAULT", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));

        // List panel
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(listPanel);
        scrollPane.setHorizontalScrollBarPolicy(
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        // Bottom panel
        bottomPanel = new JPanel(new FlowLayout());

        addButton = new JButton("ADD");
        deleteButton = new JToggleButton("DELETE");
    }

    // =========================
    // ARRANGE COMPONENTS
    // =========================
    @Override
    protected void setupLayout() {

        // Position title label
        titleLabel.setBounds(0, 15, 400, 40);

        // Add title to top panel
        upperPanel.add(titleLabel);

        // Add buttons to bottom panel
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);

        // Assemble main container
        container.add(upperPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);

        // Add container to frame
        add(container);
    }

    // =========================
    // REGISTER EVENT LISTENERS
    // =========================
    @Override
    protected void setupEvents() {

        addButton.addActionListener(e -> onAdd());
        deleteButton.addActionListener(e -> toggleDelete());
    }

    // =========================
    // OPEN ADD ENTRY WINDOW
    // =========================
    private void onAdd() {
        new editFrame(this).setVisible(true);
    }

    // =========================
    // TOGGLE DELETE MODE
    // =========================
    private void toggleDelete() {

        // When toggle is ON, enable delete mode.
        // When toggle is turned OFF, delete selected items.
        deleteMode = deleteButton.isSelected();

        if (!deleteMode) {
            deleteSelectedItems();
        }
    }

    // =========================
    // DELETE SELECTED ITEMS
    // =========================
    private void deleteSelectedItems() {

        Component[] comps = listPanel.getComponents();

        // Traverse backwards so removal is safe
        for (int i = comps.length - 1; i >= 0; i--) {

            // Only delete selected toggle buttons
            if (comps[i] instanceof JToggleButton btn && btn.isSelected()) {

                String title = btn.getText();

                // Remove from database
                DatabaseManager.deleteByTitle(title);

                // Remove spacing after the button
                if (i + 1 < comps.length &&
                    comps[i + 1] instanceof Box.Filler) {
                    listPanel.remove(i + 1);
                }

                // Remove the button itself
                listPanel.remove(i);
            }
        }

        // Exit delete mode
        deleteMode = false;
        deleteButton.setSelected(false);

        // Reload updated data
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

            // Each vault entry is represented as a toggle button
            JToggleButton item = new JToggleButton(title);

            Dimension buttonSize = new Dimension(350, 50);

            item.setPreferredSize(buttonSize);
            item.setMinimumSize(buttonSize);
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            item.setAlignmentX(Component.CENTER_ALIGNMENT);

            item.addActionListener(e -> {

                // In normal mode, open the selected entry
                if (!deleteMode) {

                    mainFrame frame = new mainFrame(
                        id, title, username, password, this
                    );

                    // Untoggle immediately
                    item.setSelected(false);

                    frame.setVisible(true);
                }
            });

            // Add item and spacing
            listPanel.add(item);
            listPanel.add(Box.createVerticalStrut(8));
        }
    }

    // =========================
    // REFRESH LIST DATA
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
        SwingUtilities.invokeLater(() ->
            new Main().setVisible(true)
        );
    }
}