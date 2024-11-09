package Dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import model1.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import model1.Borrower;
import model1.BorrowerId;
import org.hibernate.query.Query;

public class FeeService {

    public String calculateAndUpdateLateFee(UUID userId, UUID bookId, LocalDate returnDate, double dailyLateFee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Corrected HQL query to use the BorrowerId properties and returnDate
            String hql = "SELECT borrower.id.bookId, borrower.id.readerId, borrower.returnDate, borrower.dueDate, borrower.fine " +
                    "FROM Borrower borrower " +
                    "WHERE borrower.id.bookId = :bookId AND borrower.id.readerId = :userId";

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("bookId", bookId);
            query.setParameter("userId", userId);

            Object[] result = query.uniqueResult();

            if (result != null) {
                UUID fetchedBookId = (UUID) result[0];
                UUID fetchedUserId = (UUID) result[1];
                LocalDate fetchedReturnDate = (LocalDate) result[2];
                LocalDate fetchedDueDate = (LocalDate) result[3];
                int currentFine = (int) result[4]; // Current fine from the database

                System.out.println("Fetched Book ID: " + fetchedBookId);
                System.out.println("Fetched User ID: " + fetchedUserId);
                System.out.println("Fetched Return Date: " + fetchedReturnDate);
                System.out.println("Fetched Due Date: " + fetchedDueDate);

                // Compare the return date and due date
                if (fetchedReturnDate != null && returnDate.isEqual(fetchedReturnDate)) {
                    return "Return date matches with the database!";
                } else if (returnDate.isAfter(fetchedDueDate)) {
                    // If the book is returned late, calculate the late fee
                    double lateFee = calculateLateFee(fetchedDueDate, returnDate, dailyLateFee);

                    // Fetch the Borrower entity to update the fine
                    Borrower borrower = session.get(Borrower.class, new BorrowerId(bookId, userId));
                    if (borrower != null) {
                        // Add the calculated late fee to the current fine
                        borrower.setFine(currentFine + (int) lateFee);
                        session.merge(borrower);  // Update the borrower record
                    }

                    // Commit the transaction to save the updated fine to the database
                    session.flush();  // Ensure that changes are immediately written to the database
                    transaction.commit();  // Commit the transaction

                    return "Book is returned late. Late fee applied: " + lateFee;
                } else {
                    return "Book returned on time. No late fee applied.";
                }
            } else {
                return "No Borrower found with the given user and book IDs.";
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback in case of any error
            }
            e.printStackTrace();
            return "An error occurred while processing the data.";
        } finally {
            session.close();  // Close session after operation
        }
    }

    // Method to calculate the late fee
    public static double calculateLateFee(LocalDate dueDate, LocalDate returnDate, double dailyLateFee) {
        if (returnDate.isAfter(dueDate)) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, returnDate);
            return daysOverdue * dailyLateFee;
        }
        return 0.0;
    }
}
