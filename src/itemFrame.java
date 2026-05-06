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
        setPreferredSize(new Dimension(300, 200));

        itemLabel = new JLabel(itemName, JLabel.CENTER);
        itemLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 24));

        add(itemLabel);
    }
}