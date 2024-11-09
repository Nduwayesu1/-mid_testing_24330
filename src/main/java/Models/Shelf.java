package model1;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shelf")
public class Shelf {

    @Id
    @Column(name = "shelf_id", nullable = false, unique = true)
    private UUID shelfId;

    @Column(name = "available_stock", nullable = false)
    private Integer availableStock;

    @Column(name = "book_category", nullable = false)
    private String bookCategory;

    @Column(name = "borrowed_number", nullable = false)
    private Integer borrowedNumber;

    @Column(name = "initial_stock", nullable = false)
    private Integer initialStock;

    // Many-to-One relationship with Room
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private Room room;

    // One-to-Many relationship with Book
    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> bookList;

    // Getters and Setters
    public UUID getShelfId() {
        return shelfId;
    }

    public void setShelfId(UUID shelfId) {
        this.shelfId = shelfId;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public Integer getBorrowedNumber() {
        return borrowedNumber;
    }

    public void setBorrowedNumber(Integer borrowedNumber) {
        this.borrowedNumber = borrowedNumber;
    }

    public Integer getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(Integer initialStock) {
        this.initialStock = initialStock;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
