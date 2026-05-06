import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Main.class.getName());

                // =========================
                //  UI COMPONENTS (FIELDS)
                // =========================
                private JPanel container;
                private JScrollPane jScrollPane2;
                private JPanel listPanel, bottomPanel, upperPanel;
                private JLabel titleLabel;
                private JButton addButton, deleteButton, backButton;

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
        //  CONTROLS (BOTTOM AREA)
        //  - ADD button
        //  - DELETE placeholder button ("O")
        // =========================
        addButton = new JButton("ADD");
        deleteButton = new JButton("O");


        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(addButton);
        bottomPanel.add(deleteButton);

        container.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("BACK");
        backButton.setPreferredSize(new Dimension(30, 60));

        upperPanel = new JPanel();
        upperPanel.setLayout(null);
        upperPanel.setPreferredSize(new Dimension(400, 60));
        backButton = new JButton("<");
        backButton.setFocusPainted(false);
        backButton.setBounds(10, 15, 30, 20);
        titleLabel = new JLabel("PASSWORD VAULT", JLabel.CENTER);
        titleLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 24));
        titleLabel.setBounds(0, 15, 400, 40);
        upperPanel.add(titleLabel);
        upperPanel.add(backButton);

        container.add(upperPanel, BorderLayout.NORTH);

        //gokgokgok
        // pussssssssyyyyyyyy
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
        //  FRAME LAYOUT WRAPPER
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
        //  ACTION: ADD ITEM BUTTON / BACK BUTTON
    // =========================

        private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        logger.log(java.util.logging.Level.INFO,
                "Back button clicked ({0})", evt.getActionCommand());
                
        }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {

        logger.log(java.util.logging.Level.INFO,
                "Add button clicked ({0})", evt.getActionCommand());

        // Create new list item button
        JButton item = new JButton(
                "Item " + (listPanel.getComponentCount() + 1)
        );

        // =========================
        //  ITEM STYLE SETTINGS
        // =========================
        item.setFocusPainted(false);
        item.setHorizontalAlignment(SwingConstants.CENTER);
        // IMPORTANT: controls width behavior inside BoxLayout + JScrollPane
        item.setPreferredSize(new Dimension(290, 50));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        item.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        // Click behavior
        item.addActionListener(e ->
                System.out.println("Clicked: " + item.getText())
        );

        // =========================
        //  ADD ITEM TO LIST
        // =========================
        listPanel.add(Box.createVerticalStrut(10));
        listPanel.add(item);

        // Refresh UI
        listPanel.revalidate();
        listPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}