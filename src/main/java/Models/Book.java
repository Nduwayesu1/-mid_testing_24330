package model1;

import model1.Enum.EBook_status;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EBook_status status;

    @Column(name = "edition", nullable = false)
    private Integer edition;

    @Column(name = "isbn_code", nullable = false, unique = true)
    private String isbnCode;

    @Column(name = "publication_year", nullable = false)
    private LocalDate publicationYear;

    @Column(name = "publisher_name", nullable = false)
    private String publisherName;

    @ManyToOne
    @JoinColumn(name = "shelf_id", referencedColumnName = "shelf_id", nullable = false)
    private Shelf shelf;

    @Column(name = "title", nullable = false)
    private String title;

    public Book() {
    }

    public Book(UUID bookId, EBook_status status, Integer edition, String isbnCode, LocalDate publicationYear, String publisherName, Shelf shelf, String title) {
        this.bookId = bookId;
        this.status = status;
        this.edition = edition;
        this.isbnCode = isbnCode;
        this.publicationYear = publicationYear;
        this.publisherName = publisherName;
        this.shelf = shelf;
        this.title = title;
    }

    public Book(UUID bookId) {
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public EBook_status getStatus() {
        return status;
    }

    public void setStatus(EBook_status status) {
        this.status = status;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public String getIsbnCode() {
        return isbnCode;
    }

    public void setIsbnCode(String isbnCode) {
        this.isbnCode = isbnCode;
    }

    public LocalDate getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(LocalDate publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
