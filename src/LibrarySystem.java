import java.util.ArrayList;
import java.util.List;

//Class for implementation logic of program
public class LibrarySystem {
    //lists for
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<BorrowRecord> records = new ArrayList<>();
    //variables for files
    private final String BOOK_FILE = "books.txt";
    private final String MEMBER_FILE = "members.txt";
    private final String STAFF_FILE = "staff.txt";
    private final String RECORD_FILE = "borrow_records.txt";
    //attribute for base price
    private final double FINE_PER_DAY = 500.0;

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
        loadBooks(); loadMembers(); loadEmployees(); loadRecords();
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
        for (String line : FileManager.readAllLines(STAFF_FILE)) {
            if (!line.trim().isEmpty()) employees.add(Employee.fromCSV(line));
        }
    }
    private void saveEmployees() {
        List<String> out = new ArrayList<>();
        for (Employee e : employees) out.add(e.toCSV());
        FileManager.writeAllLines(STAFF_FILE, out);
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

}
