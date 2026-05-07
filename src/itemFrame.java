import java.awt.*;
import javax.swing.*;

public class itemFrame extends JFrame {

    private JLabel itemLabel;
    private JPanel container;

    public itemFrame(String itemName) {
        initComponents(itemName);
        setLocationRelativeTo(null); // center window
    }

    private void initComponents(String itemName) {

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 400));
        setResizable(false);
        setSize(300, 400);

        container = new JPanel();
        container.setLayout(null);
        container.setPreferredSize(new Dimension(300, 400));
        container.setBackground(Color.GRAY);



        add(container);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new itemFrame("").setVisible(true);
        });
    }
}