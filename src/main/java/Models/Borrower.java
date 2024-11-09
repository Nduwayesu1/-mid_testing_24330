package model1;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrower")
public class Borrower {
    @EmbeddedId
    private BorrowerId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("bookId")
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("readerId")
    @JoinColumn(name = "reader_id", referencedColumnName = "user_id")
    private User reader;
    @Column(name = "return_date")  // Add this field if not present
    private LocalDate returnDate;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "fine", nullable = false)
    private Integer fine;

    // Full constructor
    public Borrower(BorrowerId id, Book book, User reader, LocalDate dueDate, Integer fine) {
        this.id = id;
        this.book = book;
        this.reader = reader;
        this.dueDate = dueDate;
        this.fine = fine;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // Simplified constructor
    public Borrower(Book book, User reader, LocalDate dueDate) {
        this.book = book;
        this.reader = reader;
        this.dueDate = dueDate;
        this.fine = 0; // Default fine value
        this.id = new BorrowerId(book.getBookId(), reader.getUserId()); // Set IDs
    }


    public Borrower() {}

    public BorrowerId getId() {
        return id;
    }

    public void setId(BorrowerId id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getReader() { // Changed to return User
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getFine() {
        return fine;
    }

    public void setFine(Integer fine) {
        this.fine = fine;
    }
}
