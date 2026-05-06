
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {

    // =========================
    //  UI COMPONENTS (FIELDS)
    // =========================
    
    private JPanel container;
    private JScrollPane jScrollPane2;
    private JPanel listPanel, bottomPanel, upperPanel;
    private JLabel titleLabel;
    private JButton addButton,backButton;
    private JToggleButton deleteButton;

    // =========================
    // 
    // =========================
    private boolean deleteMode = false;

    public Main() {
        initComponents();
        setLocationRelativeTo(null); // center window
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        // =========================
        //  FRAME SETTINGS
        // =========================
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 480));

        // =========================
        //  MAIN CONTAINER PANEL
        // =========================
        container = new JPanel();
        container.setLayout(new BorderLayout());

        // =========================
        //  UPPER PANEL
        // =========================
        upperPanel = new JPanel();
        upperPanel.setLayout(null);
        upperPanel.setPreferredSize(new Dimension(400, 60));

        backButton = new JButton("<");
        backButton.setFocusPainted(false);
        backButton.setBounds(10, 15, 30, 20);
        backButton.addActionListener(e -> onBackButton());

        titleLabel = new JLabel("PASSWORD VAULT", JLabel.CENTER);
        titleLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 24));
        titleLabel.setBounds(0, 15, 400, 40);

        upperPanel.add(titleLabel);
        upperPanel.add(backButton);

        container.add(upperPanel, BorderLayout.NORTH);

        // =========================
        //  BOTTOM PANEL
        // =========================
        addButton = new JButton("ADD");
        addButton.setPreferredSize(new Dimension(300, 60));
        addButton.addActionListener(e -> onAddButton());

        deleteButton = new JToggleButton("DELETE");
        deleteButton.setPreferredSize(new Dimension(20, 60));
        deleteButton.addActionListener(e -> onDeleteButton());

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);

        container.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("BACK");
        backButton.setPreferredSize(new Dimension(30, 60));

        // =========================
        //  LIST AREA (SCROLLABLE)
        //  - where generated items go
        // =========================
        jScrollPane2 = new JScrollPane();
        jScrollPane2.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        listPanel = new JPanel(); // This will hold the list of items

        // Stack items vertically
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        listPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        jScrollPane2.setViewportView(listPanel);

        // Add scroll list to center area
        container.add(jScrollPane2, BorderLayout.CENTER);

        // =========================
        //  FRAME LAYOUT WRAPPER // dont touch HAHAHAHHA
        // =========================
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(container, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(container, GroupLayout.DEFAULT_SIZE,
                                480, Short.MAX_VALUE)
        );

        pack();
    }

    // =========================
    //  ACTION: ADD ITEM BUTTON / BACK BUTTON / DELETE BUTTON
    // =========================
        private void onBackButton(){ 
                new Login().setVisible(true);
                dispose();
        }

        private void onDeleteButton(){
                deleteMode = !deleteMode;

                if (!deleteMode) {
                        deleteMode = true;
                        deleteButton.setText("Confirm Delete");
                } else {
                        deleteSelectedItems();
                        deleteMode = false;
                        deleteButton.setText("Delete");
                }
                
        }

        private void deleteSelectedItems() {
                
                Component[] components = listPanel.getComponents();
                
                for (Component comp : components) {
                        if (comp instanceof JToggleButton btn) {
                                if (btn.isSelected()) {
                                listPanel.remove(btn);
                        }
                }
        }

        listPanel.revalidate();
        listPanel.repaint();
        }

        private void  onAddButton(){ 
                addItem();
        }

        private void addItem(){
                JToggleButton item = new JToggleButton(
                        "Item " + (listPanel.getComponentCount() + 1)
                );

                item.setFocusPainted(false);
                item.setHorizontalAlignment(SwingConstants.CENTER);
                item.setPreferredSize(new Dimension(290, 50));
                item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                item.setAlignmentX(Component.CENTER_ALIGNMENT);

                item.addActionListener(e -> {
                if (!deleteMode) {
                // NORMAL MODE → open new frame
                new itemFrame(item.getText()).setVisible(true);
                } else {
                // DELETE MODE → just toggle selection (no action needed)
                item.setSelected(!item.isSelected());
                }
        });

                listPanel.add(Box.createVerticalStrut(10));
                listPanel.add(item);

                listPanel.revalidate();
                listPanel.repaint();
        }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
