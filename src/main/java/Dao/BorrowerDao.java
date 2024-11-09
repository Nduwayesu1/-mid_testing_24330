package Dao;

import model1.*;
import model1.Enum.EBook_status;
import model1.Enum.EStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.UUID;

public class BorrowerDao {

    public String borrowBook(UUID bookId, UUID userId, LocalDate pickupDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Fetch Book entity and check if it exists and is available
            Book book = session.get(Book.class, bookId);
            if (book == null) {
                return "Book not found";
            }
            if (book.getStatus() ==EBook_status.BORROWED){
                return  "Book Has been Borrowed";
            }
            if(book.getStatus() == EBook_status.RESERVED){
                return  "The Book has been reserved";
            }
            // Fetch User (Reader) entity and check he  is in the database
            User reader = session.get(User.class, userId);
            if (reader == null) {
                return "Reader not found";
            }
            // Verify if the user is allowed to borrow more books
            if (!canUserBorrowMoreBooks(userId)) {
                return "User cannot borrow more books than allowed by their membership";
            }

            // Calculate the due date (2 weeks)
            LocalDate dueDate = pickupDate.plusWeeks(2);
            // Create the BorrowerId with all necessary fields
            BorrowerId borrowerId = new BorrowerId(bookId, userId, pickupDate, dueDate);

            // Create the Borrower entity with the initialized BorrowerId
            Borrower borrower = new Borrower(borrowerId, book, reader, dueDate, 0);  // Fine initialized to 0
            // Update the book's status to "BORROWED"
            book.setStatus(EBook_status.BORROWED);
            // Update book status
            session.update(book);
            // Persist the Borrower entity
            session.persist(borrower);
            transaction.commit();

            return "Borrowing process successful";
        } catch (Exception e) {
            e.printStackTrace();
            return "Borrowing process failed: " + e.getMessage();
        }
    }


    // Method to check if the user can borrow more books based on their membership type
    public boolean canUserBorrowMoreBooks(UUID userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Fetch the User entity
            User user = session.get(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }

            // Fetch the Membership_type directly from the user's memberships
            Membership_type membershipType = user.getMembershipList().stream()
                    .flatMap(m -> m.getMembershipTypes().stream()) // Flatten to get all membership types
                    .filter(mt -> mt.getMembership().getStatus() == EStatus.APPROVED) // Only approved statuses
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No approved membership type found for user"));

            // Get the maxBooks value from the Membership_type entity
            int borrowingLimit = membershipType.getMaxBooks();

            // Count how many books the user has already borrowed
            long borrowedBooksCount = session.createQuery(
                            "SELECT COUNT(b) FROM Borrower b WHERE b.reader.userId = :userId", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();

            // Check if the user has reached the borrowing limit
            return borrowedBooksCount < borrowingLimit;
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Default to false if an error occurs
        }
    }


}
