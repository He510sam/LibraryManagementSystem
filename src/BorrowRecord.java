//class for save the records
public class BorrowRecord {

    //define attributes for class
    private int memberId;
    private int bookId;
    private String borrowDate;
    private String dueDate;

    //constructor for Initializing fields
    public BorrowRecord(int memberId, int bookId, String borrowDate, String dueDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    //setter & getter for return and set attributes
    public int getMemberId() {return memberId;}

    public void setMemberId(int memberId) {this.memberId = memberId;}

    public int getBookId() {return bookId;}

    public void setBookId(int bookId) {this.bookId = bookId;}

    public String getBorrowDate() {return borrowDate;}

    public void setBorrowDate(String borrowDate) {this.borrowDate = borrowDate;}

    public String getDueDate() {return dueDate;}

    public void setDueDate(String dueDate) {this.dueDate = dueDate;}

    //methods for read and write info from files
    public String toCSV() {
        return memberId + "," + bookId + "," + borrowDate + "," + dueDate;
    }

    public static BorrowRecord fromCSV(String line) {
        String[] p = line.split(",", -1);
        int mid = Integer.parseInt(p[0]);
        int bid = Integer.parseInt(p[1]);
        String borrow = p[2];
        String due = p[3];
        return new BorrowRecord(mid, bid, borrow, due);
    }

    //override toString method for showing information
    @Override
    public String toString() {
        return "Member " + memberId + " | Book " + bookId + " | Borrow: " + borrowDate + " | Due: " + dueDate;
    }
}
