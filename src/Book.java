//A class for holds books attributes

public class Book {

    //define attributes for library Books

    private int bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    //constructor for Initializing fields
    public Book(int bookId, String title, String author, int copies) {
        this.bookId = bookId;
        this.title = title == null ? "" : title.replace(",", "؛");
        this.author = author == null ? "" : author.replace(",", "؛");
        this.isAvailable = true;
    }

    //setter & getter for return and set attributes

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    //override toString method for showing information
    @Override
    public String toString() {
        return bookId + " - " + title + " by " + author + " [" + (isAvailable ? "Available" : "Borrowed") + "]";
    }


}
