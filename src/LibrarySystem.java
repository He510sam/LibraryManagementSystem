import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Class for implementation logic of program
public class LibrarySystem {
    //lists for save data
    private final List<Book> books = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();
    private final List<BorrowRecord> records = new ArrayList<>();
    //variables for files
    private final String BOOK_FILE = "books.txt";
    private final String MEMBER_FILE = "members.txt";
    private final String EMPLOYEE_FILE = "employee.txt";
    private final String RECORD_FILE = "borrow_records.txt";

    //constructor for Initializing fields
    public LibrarySystem() {
        loadAll();
        ensureDefaultAdmin();
    }

    //method for set default employees
    private void ensureDefaultAdmin() {
        if (employees.isEmpty()) {
            Employee e = new Employee(1, "Admin", "admin", "admin");
            employees.add(e);
            saveEmployees();
        }
    }

    //methods for load & save
    public void loadAll() {
        loadBooks();
        loadMembers();
        loadEmployees();
        loadRecords();
    }

    //methods for load and save
    private void loadBooks() {
        for (String line : FileManager.readAllLines(BOOK_FILE)) {
            if (!line.trim().isEmpty()) books.add(Book.fromCSV(line));
        }
    }

    private void saveBooks() {
        List<String> out = new ArrayList<>();
        for (Book b : books) out.add(b.toCSV());
        FileManager.writeAllLines(BOOK_FILE, out);
    }

    private void loadMembers() {
        for (String line : FileManager.readAllLines(MEMBER_FILE)) {
            if (!line.trim().isEmpty()) members.add(Member.fromCSV(line));
        }
    }

    private void saveMembers() {
        List<String> out = new ArrayList<>();
        for (Member m : members) out.add(m.toCSV());
        FileManager.writeAllLines(MEMBER_FILE, out);
    }

    private void loadEmployees() {
        for (String line : FileManager.readAllLines(EMPLOYEE_FILE)) {
            if (!line.trim().isEmpty()) employees.add(Employee.fromCSV(line));
        }
    }

    private void saveEmployees() {
        List<String> out = new ArrayList<>();
        for (Employee e : employees) out.add(e.toCSV());
        FileManager.writeAllLines(EMPLOYEE_FILE, out);
    }

    private void loadRecords() {
        for (String line : FileManager.readAllLines(RECORD_FILE)) {
            if (!line.trim().isEmpty()) records.add(BorrowRecord.fromCSV(line));
        }
    }

    private void saveRecords() {
        List<String> out = new ArrayList<>();
        for (BorrowRecord r : records) out.add(r.toCSV());
        FileManager.writeAllLines(RECORD_FILE, out);
    }

    //methods for add lists
    public Book addBook(int id, String title, String author) {
        Book b = new Book(id, title, author, true);
        books.add(b);
        saveBooks();
        return b;
    }

    public Member addMember(int id, String name, String memId, String phone, String address,
                            String username, String password) {
        Member m = new Member(id, name, memId, phone, address, username, password);
        members.add(m);
        saveMembers();
        return m;
    }

    public Employee addEmployee(int id, String name, String username, String password) {
        Employee e = new Employee(id, name, username, password);
        employees.add(e);
        saveEmployees();
        return e;
    }

    //return unmodifiable version of files
    public List<Book> listBooks() {
        return Collections.unmodifiableList(books);
    }

    public List<Member> listMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<BorrowRecord> listRecords() {
        return Collections.unmodifiableList(records);
    }

    //--> methods for searching
    public Book findBookById(int id) {
        for (Book b : books) if (b.getBookId() == id) return b;
        return null;
    }

    public Book findBookByTitle(String title) {
        for (Book b : books) if (b.getTitle().equalsIgnoreCase(title)) return b;
        return null;
    }

    //--> methods for login
    public Member loginMember(String username, String password) {
        for (Member m : members) if (m.getUsername().equals(username) && m.getPassword().equals(password)) return m;
        return null;
    }

    public Employee loginStaff(String username, String password) {
        for (Employee e : employees) if (e.getUsername().equals(username) && e.getPassword().equals(password)) return e;
        return null;
    }

    //--> method for borrow
    public String borrowBook(Member m, int bookId) {
        if (m.hasPenalty()) return "The member has a debt: " + m.getPenalty();
        Book b = findBookById(bookId);
        if (b == null) return "Book not found!";
        if (!b.isAvailable()) return "Book is already borrowed!";

        LocalDate now = LocalDate.now();
        LocalDate due = now.plusDays(44);

        records.add(new BorrowRecord(m.getId(), bookId, now.toString(), due.toString()));
        b.setAvailable(false);
        saveBooks();
        saveRecords();

        return "Book borrowed successfully.\nReturn date: " + due;
    }

    // --> method for return & fine calculation
    public String returnBook(int memberId, int bookId) {
        BorrowRecord target = null;
        for (BorrowRecord r : records) {
            if (r.getMemberId() == memberId && r.getBookId() == bookId) {
                target = r;
                break;
            }
        }
        Book b = findBookById(bookId);
        if (b == null) return "The Book Not Found!!";
        if (target == null) {
            b.setAvailable(true);
            saveBooks();
            return "No loan record found!\n book marked!!";
        }
        LocalDate due = LocalDate.parse(target.getDueDate());
        long daysLate = ChronoUnit.DAYS.between(due, LocalDate.now());
        double fine = 0.0;
        if (daysLate > 0) {
            //attribute for base price
            double FINE_PER_DAY = 500.0;
            fine = daysLate * FINE_PER_DAY;
            Member mem = findMemberById(memberId);
            if (mem != null) {
                mem.setPenalty(mem.getPenalty() + fine);
                saveMembers();
            }
        }
        records.remove(target);
        b.setAvailable(true);
        saveBooks();
        saveRecords();
        if (fine > 0) return "The book was returned. Fine imposed:" + fine + " (Lateness " + daysLate + " days).";
        return "The book was returned without penalty.";
    }

    //--> method for find member
    public Member findMemberById(int id) {
        for (Member m : members) if (m.getId() == id) return m;
        return null;
    }

    //--> method for search by partial title (case-insensitive)
    public List<Book> searchBooksByPartialTitle(String q) {
        List<Book> res = new ArrayList<>();
        for (Book b : books) if (b.getTitle().toLowerCase().contains(q.toLowerCase())) res.add(b);
        return res;


    }
}
