package model1;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class BorrowerId implements Serializable {
    private UUID bookId;
    private UUID readerId;
    private LocalDate pickupDate;
    private LocalDate returnDate;


    public BorrowerId() {
    }

    public BorrowerId(UUID bookId, UUID readerId, LocalDate pickupDate, LocalDate returnDate) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
    }

    public BorrowerId(UUID bookId, UUID userId) {
        this.bookId=bookId;
        this.readerId=userId;
    }


    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public UUID getReaderId() {
        return readerId;
    }

    public void setReaderId(UUID readerId) {
        this.readerId = readerId;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowerId that = (BorrowerId) o;
        return Objects.equals(bookId, that.bookId) &&
                Objects.equals(readerId, that.readerId) &&
                Objects.equals(pickupDate, that.pickupDate) &&
                Objects.equals(returnDate, that.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, readerId, pickupDate, returnDate);
    }

}
