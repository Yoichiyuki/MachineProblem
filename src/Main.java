import java.awt.*;
import java.util.List;
import javax.swing.*;

public class Main extends BaseFrame {

    // =========================
    // UI COMPONENTS
    // =========================
    public JPanel container;
    public JPanel listPanel;
    public JScrollPane scrollPane;

    public JPanel bottomPanel;
    public JPanel upperPanel;

    public JLabel titleLabel;
    private JButton addButton, LogoutButton;
    private JToggleButton deleteButton;

    // =========================
    // STATE
    // =========================
    private boolean deleteMode = false;

    // =========================
    // CONSTRUCTOR
    // =========================
    public Main() {
        super("Password Vault", 400, 480);
        setResizable(false);
        loadData();
        System.out.println("Session userId = " + MainSession.userId);
    }

    // =========================
    // INIT COMPONENTS
    // =========================
    @Override
    protected void initializeComponents() {

        container = new JPanel(new BorderLayout());

        upperPanel = new JPanel(null);
        upperPanel.setPreferredSize(new Dimension(400, 60));

        titleLabel = new JLabel("PASSWORD VAULT", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // FIX 1: stable scroll behavior
        scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        bottomPanel = new JPanel(new FlowLayout());

        addButton = new JButton("ADD");
        LogoutButton = new JButton("LOGOUT");
        deleteButton = new JToggleButton("DELETE");
    }

    // =========================
    // LAYOUT (UNCHANGED STRUCTURE)
    // =========================
    @Override
    protected void setupLayout() {

        // upper
        titleLabel.setBounds(0, 15, 400, 30);
        upperPanel.add(titleLabel);

        // bottom
        bottomPanel.add(addButton);
        bottomPanel.add(LogoutButton);
        bottomPanel.add(deleteButton);

        // container layout
        container.add(upperPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(container);

        revalidate();
        repaint();
    }

    // =========================
    // EVENTS
    // =========================
    @Override
    protected void setupEvents() {

        addButton.addActionListener(e -> new editFrame(this).setVisible(true));

        deleteButton.addActionListener(e -> {
            deleteMode = deleteButton.isSelected();
            if (!deleteMode) deleteSelectedItems();
        });
        
        LogoutButton.addActionListener(e -> {
            MainSession.userId = -1;
            MainSession.username = null;
            new Login().setVisible(true);
            dispose();
        });
    }

    // =========================
    // LOAD DATA
    // =========================
    private void loadData() {

        if (listPanel == null) return;

        listPanel.removeAll();

        List<String[]> data = DatabaseManager.getDataList(MainSession.userId);

        for (String[] row : data) {

            int id = Integer.parseInt(row[0]);
            String title = row[1];

            JToggleButton item = new JToggleButton(title);

            // FIX 2: stable fixed sizing (prevents shrinking)
            item.setPreferredSize(new Dimension(350, 50));
            item.setMaximumSize(new Dimension(350, 50));
            item.setMinimumSize(new Dimension(350, 50));

            item.setAlignmentX(Component.CENTER_ALIGNMENT);

            item.addActionListener(e -> {

                if (!deleteMode) {

                    mainFrame frame = new mainFrame(
                            id,
                            row[1],
                            row[2],
                            row[3],
                            this
                    );

                    item.setSelected(false);
                    frame.setVisible(true);
                }
            });

            listPanel.add(item);
            listPanel.add(Box.createVerticalStrut(5));
        }

        refreshUI();
    }

    // =========================
    // DELETE MODE
    // =========================
    private void deleteSelectedItems() {

        Component[] comps = listPanel.getComponents();

        for (int i = comps.length - 1; i >= 0; i--) {

            if (comps[i] instanceof JToggleButton btn && btn.isSelected()) {

                String title = btn.getText();

                for (String[] row : DatabaseManager.getDataList(MainSession.userId)) {
                    if (row[1].equals(title)) {
                        DatabaseManager.deleteById(Integer.parseInt(row[0]));
                        break;
                    }
                }

                listPanel.remove(i);
            }
        }

        deleteMode = false;
        deleteButton.setSelected(false);

        loadData();
    }

    // =========================
    // REFRESH UI
    // =========================
    public void refreshData() {
        loadData();
    }

    private void refreshUI() {
        listPanel.revalidate();
        listPanel.repaint();

        container.revalidate();
        container.repaint();
    }
}