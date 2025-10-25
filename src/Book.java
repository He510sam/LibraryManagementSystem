//A class for holds books attributes

public class Book {

    //define attributes for library Books

    private int bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    //constructor for Initializing fields
    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }
}
