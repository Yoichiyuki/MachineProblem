import java.awt.*;
import javax.swing.*;

public class itemFrame extends JFrame {

    private JLabel itemLabel;

    public itemFrame(String itemName) {
        initComponents(itemName);
        setLocationRelativeTo(null); // center window
    }

    private void initComponents(String itemName) {

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 400));
        setResizable(false);
        setSize(300, 400);
        

        itemLabel = new JLabel(itemName, JLabel.CENTER);
        itemLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 24));
        add(itemLabel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new itemFrame("Example Item").setVisible(true);
        });
    }
}