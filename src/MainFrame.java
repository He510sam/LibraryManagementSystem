import javax.swing.*;
import java.awt.*;
//Execute Class
public class MainFrame extends JFrame {
    private LibrarySystem library;
    private CardLayout cardLayout;
    private JPanel cards;

    // UI components that reference
    private JTextField loginUserField;
    private JPasswordField loginPassField;
    private JRadioButton staffRadio;
    private JRadioButton memberRadio;

    private Employee currentStaff;
    private Member currentMember;

    //The Color of window
    private final Color BACK = new Color(240, 248, 255); // alice blue-ish
    private final Color ACCENT = new Color(100, 149, 237); // cornflower blue
    private final Color BTN = new Color(72, 118, 255);
    private final Font FONT = new Font("SansSerif", Font.PLAIN, 14);

    public MainFrame() {
        library = new LibrarySystem();
        initUI();
        setTitle("Library Management System");
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initUI() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBackground(BACK);

        cards.add(createLoginPanel(), "login");
        cards.add(createEmployeefPanel(), "employee");
        cards.add(createMemberPanel(), "member");

        add(cards);
        cardLayout.show(cards, "login");
    }
}