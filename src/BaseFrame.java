import javax.swing.*;

// Main, MainFrame, SignIn, and Login all extend this base class to avoid code duplication
// This is the way I apply abstraction <3

public abstract class BaseFrame extends JFrame {

    public BaseFrame(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeComponents();
        setupLayout();
        setupEvents();
    }

    protected abstract void initializeComponents();
    protected abstract void setupLayout();
    protected abstract void setupEvents();
}