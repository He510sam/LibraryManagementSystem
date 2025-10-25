//use for add date
import java.time.LocalDate;

//class for save the records
public class BorrowRecord {

    //define attributes for class
    private int memberId;
    private int bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    //constructor for Initializing fields
    public BorrowRecord(int memberId, int bookId, LocalDate borrowDate, LocalDate dueDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }
}
