// MainFrame.java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainFrame extends JFrame {

    private final LibrarySystem library;

    //To manage multiple pages
    private CardLayout cardLayout;
    private JPanel cards;

    //Login Page UI Components
    private JTextField loginUserField;
    private JPasswordField loginPassField;
    private JRadioButton employeeRadio;
    private JRadioButton memberRadio;

    //save current user after login
    private Employee currentEmployee;
    private Member currentMember;

    //define colors in pages
    private final Color BACK = new Color(240, 248, 255); // alice blue-ish
    private final Color ACCENT = new Color(100, 149, 237); // cornflower blue
    private final Color BTN = new Color(72, 118, 255);
    private final Font FONT = new Font("SansSerif", Font.PLAIN, 14);

    //constructor for Initializing fields
    public MainFrame() {
        library = new LibrarySystem();
        initUI();
        setTitle("Library Management System");
        setSize(900, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //--> Method For Overall UI structure
    private void initUI() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBackground(BACK);
        cards.add(createLoginPanel(), "login");
        cards.add(createEmployeePanel(), "employee");
        cards.add(createMemberPanel(), "member");
        add(cards);
        cardLayout.show(cards, "login");
    }

    //-->method for first page
    private JPanel createLoginPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BACK);

        JLabel title = new JLabel("Library Management System", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        title.setForeground(ACCENT);

        JPanel center = new JPanel();
        center.setBackground(BACK);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(20, 200, 20, 200));

        //--> role selection
        JPanel roleP = new JPanel(new FlowLayout(FlowLayout.CENTER));
        roleP.setBackground(BACK);
        employeeRadio = new JRadioButton("Employee");
        memberRadio = new JRadioButton("Member");
        ButtonGroup g = new ButtonGroup();
        employeeRadio.setSelected(true);
        g.add(employeeRadio);
        g.add(memberRadio);
        roleP.add(employeeRadio);
        roleP.add(memberRadio);

        loginUserField = new JTextField();
        loginUserField.setMaximumSize(new Dimension(300, 30));
        loginUserField.setPreferredSize(new Dimension(300, 30));
        loginPassField = new JPasswordField();
        loginPassField.setMaximumSize(new Dimension(300, 30));
        loginPassField.setPreferredSize(new Dimension(300, 30));

        center.add(new JLabel("Username:")); center.add(loginUserField);
        center.add(Box.createRigidArea(new Dimension(0,8)));
        center.add(new JLabel("Password:")); center.add(loginPassField);
        center.add(Box.createRigidArea(new Dimension(0,12)));
        center.add(roleP);

        JButton btnLogin = styledButton("Login");
        btnLogin.addActionListener(this::onLogin);
        center.add(Box.createRigidArea(new Dimension(0,12)));
        center.add(btnLogin);

        p.add(title, BorderLayout.NORTH);
        p.add(center, BorderLayout.CENTER);

        return p;
    }

    //--> Methods For Determining The Next Page From Inputs
    private void onLogin(ActionEvent e) {
        String user = loginUserField.getText();
        String pass = new String(loginPassField.getPassword());
        if (employeeRadio.isSelected()) {
            Employee emp = library.loginEmployee(user, pass);
            if (emp == null) {
                JOptionPane.showMessageDialog(this, "Invalid employee credentials.");
                return;
            }
            currentEmployee = emp;
            showEmployeeDashboard();
        } else {
            Member m = library.loginMember(user, pass);
            if (m == null) {
                int ans = JOptionPane.showConfirmDialog(this, "Member not found. Register as new member?");
                if (ans == JOptionPane.YES_OPTION) {
                    showRegisterMemberDialog();
                }
                return;
            }
            currentMember = m;
            showMemberDashboard();
        }
    }

    private void showEmployeeDashboard() {
        cardLayout.show(cards, "employee");
        setTitle("Employee - " + currentEmployee.getName());
    }

    private JPanel createEmployeePanel() {
        //--> EMPLOYEE DASHBOARD
        JPanel employeePanel = new JPanel(new BorderLayout());
        employeePanel.setBackground(BACK);

        JLabel lbl = new JLabel("Employee Dashboard", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        lbl.setForeground(ACCENT);
        employeePanel.add(lbl, BorderLayout.NORTH);

        //--> left menu
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(BACK);
        menu.setBorder(new EmptyBorder(10,10,10,10));

        JButton btnAddBook = styledButton("Add Book");
        btnAddBook.addActionListener(ev -> showAddBookDialog());

        JButton btnAddMember = styledButton("Add Member");
        btnAddMember.addActionListener(ev -> showRegisterMemberDialog());

        JButton btnBorrow = styledButton("Borrow Book");
        btnBorrow.addActionListener(ev -> showBorrowDialogAsEmployee());

        JButton btnReturn = styledButton("Return Book");
        btnReturn.addActionListener(ev -> showReturnDialog());

        JButton btnView = styledButton("View All (Books & Members)");
        btnView.addActionListener(ev -> showAllData());

        JButton btnLogout = styledButton("Logout");
        btnLogout.addActionListener(ev -> logout());

        menu.add(btnAddBook); menu.add(Box.createRigidArea(new Dimension(0,8)));
        menu.add(btnAddMember); menu.add(Box.createRigidArea(new Dimension(0,8)));
        menu.add(btnBorrow); menu.add(Box.createRigidArea(new Dimension(0,8)));
        menu.add(btnReturn); menu.add(Box.createRigidArea(new Dimension(0,8)));
        menu.add(btnView); menu.add(Box.createVerticalGlue()); menu.add(btnLogout);

        employeePanel.add(menu, BorderLayout.WEST);

        //--> center area for search & lists
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(BACK);

        //--> search bar
        JPanel searchP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchP.setBackground(BACK);
        JTextField searchField = new JTextField(30);
        JButton searchBtn = styledButton("Search Book");
        searchBtn.addActionListener(ev -> {
            String q = searchField.getText();
            if (q.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter title or ID to search.");
                return;
            }
            try {
                int id = Integer.parseInt(q);
                Book b = library.findBookById(id);
                JOptionPane.showMessageDialog(this, b == null ? "Not found" : b.toString());
            } catch (NumberFormatException ex) {
                List<Book> found = library.searchBooksByPartialTitle(q);
                if (found.isEmpty()) JOptionPane.showMessageDialog(this, "No match found.");
                else {
                    StringBuilder sb = new StringBuilder();
                    for (Book b : found) sb.append(b).append("\n");
                    JOptionPane.showMessageDialog(this, sb.toString());
                }
            }
        });
        searchP.add(new JLabel("Search by title or ID: "));
        searchP.add(searchField);
        searchP.add(searchBtn);

        center.add(searchP, BorderLayout.NORTH);

        //--> list area
        JTextArea listArea = new JTextArea();
        listArea.setEditable(false);
        listArea.setFont(FONT);
        JScrollPane sp = new JScrollPane(listArea);
        center.add(sp, BorderLayout.CENTER);

        //--> refresh button
        JButton refresh = styledButton("Refresh List");
        refresh.addActionListener(ev -> {
            StringBuilder sb = new StringBuilder("Books:\n");
            for (Book b : library.listBooks()) sb.append(b).append("\n");
            sb.append("\nMembers:\n");
            for (Member m : library.listMembers()) sb.append(m).append("\n");
            listArea.setText(sb.toString());
        });
        center.add(refresh, BorderLayout.SOUTH);

        employeePanel.add(center, BorderLayout.CENTER);
        return employeePanel;
    }

    //--> MEMBER DASHBOARD
    private JPanel memberPanel;
    private void showMemberDashboard() {
        cardLayout.show(cards, "member");
        setTitle("Member - " + currentMember.getName());
    }

    private JPanel createMemberPanel() {
        memberPanel = new JPanel(new BorderLayout());
        memberPanel.setBackground(BACK);

        JLabel lbl = new JLabel("Member Dashboard", SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        lbl.setForeground(ACCENT);
        memberPanel.add(lbl, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setBackground(BACK);
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(new EmptyBorder(10,10,10,10));

        JButton viewBooks = styledButton("View Books");
        viewBooks.addActionListener(ev -> {
            StringBuilder sb = new StringBuilder("Books:\n");
            for (Book b : library.listBooks()) sb.append(b).append("\n");
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        JButton borrow = styledButton("Borrow Book");
        borrow.addActionListener(ev -> {
            showBorrowDialogAsMember();
        });

        JButton logout = styledButton("Logout");
        logout.addActionListener(ev -> {
            currentMember = null;
            logout();
        });

        menu.add(viewBooks); menu.add(Box.createRigidArea(new Dimension(0,8)));
        menu.add(borrow); menu.add(Box.createVerticalGlue()); menu.add(logout);

        memberPanel.add(menu, BorderLayout.WEST);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(FONT);
        memberPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        // show member info when panel shown
        memberPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                if (currentMember != null) infoArea.setText(currentMember.toString());
            }
        });

        return memberPanel;
    }

    //--> HELPERS
    private JButton styledButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(BTN);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(FONT);
        return b;
    }

    private void showAddBookDialog() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Book ID:"));
            String title = JOptionPane.showInputDialog(this, "Title:");
            String author = JOptionPane.showInputDialog(this, "Author:");
            library.addBook(id, title, author);
            JOptionPane.showMessageDialog(this, "Book added.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    private void showRegisterMemberDialog() {
        int id = library.listMembers().size() + 1;
        String name = JOptionPane.showInputDialog(this, "Name:");
        String memId = "M" + id;
        String phone = JOptionPane.showInputDialog(this, "Phone:");
        String address = JOptionPane.showInputDialog(this, "Address:");
        String username = JOptionPane.showInputDialog(this, "Username:");
        String password = JOptionPane.showInputDialog(this, "Password:");
        library.addMember(id, name, memId, phone, address, username, password);
        JOptionPane.showMessageDialog(this, "Member registered. username: " + username);
    }

    private void showBorrowDialogAsEmployee() {
        try {
            int memberId = Integer.parseInt(JOptionPane.showInputDialog(this, "Member ID:"));
            Member m = library.findMemberById(memberId);
            if (m == null) { JOptionPane.showMessageDialog(this, "Member not found."); return; }
            int bookId = Integer.parseInt(JOptionPane.showInputDialog(this, "Book ID:"));
            String res = library.borrowBook(m, bookId);
            JOptionPane.showMessageDialog(this, res);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid numeric input.");
        }
    }

    private void showBorrowDialogAsMember() {
        try {
            int bookId = Integer.parseInt(JOptionPane.showInputDialog(this, "Book ID:"));
            if (currentMember == null) {
                JOptionPane.showMessageDialog(this, "No active member.");
                return;
            }
            String res = library.borrowBook(currentMember, bookId);
            JOptionPane.showMessageDialog(this, res);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    private void showReturnDialog() {
        try {
            int memberId = Integer.parseInt(JOptionPane.showInputDialog(this, "Member ID:"));
            int bookId = Integer.parseInt(JOptionPane.showInputDialog(this, "Book ID:"));
            String res = library.returnBook(memberId, bookId);
            JOptionPane.showMessageDialog(this, res);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    private void showAllData() {
        StringBuilder sb = new StringBuilder("Books:\n");
        for (Book b : library.listBooks()) sb.append(b).append("\n");
        sb.append("\nMembers:\n");
        for (Member m : library.listMembers()) sb.append(m).append("\n");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void logout() {
        currentMember = null;
        currentEmployee = null;
        library.saveAll();
        cardLayout.show(cards, "login");
        setTitle("Library Management System");
    }

    //--> entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
        });
    }
}
