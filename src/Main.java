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

    private JButton addButton, LogoutButton;
    private JToggleButton deleteButton;

    private Image upperPanelImg;
    private Image itemBttn, itemHover, itemSelected;

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

        upperPanelImg = new ImageIcon("assets/upperPanelImg.png").getImage();
        itemBttn = new ImageIcon("assets/ItemBttn.png").getImage();
        itemHover = new ImageIcon("assets/ItemHover.png").getImage();
        itemSelected = new ImageIcon("assets/ItemHover.png").getImage();

        container = new JPanel(new BorderLayout());

        upperPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(upperPanelImg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        upperPanel.setPreferredSize(new Dimension(400, 60));
 

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        // FIX 1: stable scroll behavior
        scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        bottomPanel = new JPanel(new FlowLayout());

        addButton = new JButton("ADD");
        LogoutButton = new JButton("CLOSE");
        deleteButton = new JToggleButton("DELETE");
    }

    // =========================
    // LAYOUT (UNCHANGED STRUCTURE)
    // =========================
    @Override
    protected void setupLayout() {

        // bottom

        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setBackground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        addButton.setForeground(Color.BLACK);

        LogoutButton.setPreferredSize(new Dimension(100, 30));
        LogoutButton.setBackground(Color.WHITE);
        LogoutButton.setFocusPainted(false);
        LogoutButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        LogoutButton.setForeground(Color.BLACK);

        deleteButton.setPreferredSize(new Dimension(100, 30));
        // Save the original color first
        deleteButton.setBackground(Color.LIGHT_GRAY);
        deleteButton.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                deleteButton.setForeground(Color.RED); // Make text readable on red
            } else {
                deleteButton.setForeground(Color.BLACK);
            }
        });
       // IMPORTANT: Disable default L&F painting
        deleteButton.setContentAreaFilled(true);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Optional: adds a border back

        bottomPanel.setBackground(Color.DARK_GRAY);
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
            item.setIcon(new ImageIcon(itemBttn));
            item.setSelectedIcon(new ImageIcon(itemSelected));

            item.setHorizontalTextPosition(SwingConstants.CENTER);
            item.setVerticalTextPosition(SwingConstants.CENTER);

        item.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

                if (deleteMode) return; // don't hover if in delete mode

                item.setIcon(new ImageIcon(itemHover));
                item.setFont(item.getFont().deriveFont(Font.BOLD));
                item.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

                if (deleteMode) return;

                item.setIcon(new ImageIcon(itemBttn));
                

                item.setFont(item.getFont().deriveFont(Font.PLAIN));
                item.setForeground(Color.BLACK);
            }
        });

            item.setBorderPainted(false);
            item.setContentAreaFilled(false);
            item.setFocusPainted(false);
            item.setOpaque(false);


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