package Dao;

import model1.HibernateUtil;
import model1.Shelf;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;

public class ShelfDao {


    public String saveShelf(Shelf shelf) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); // Start a transaction
            session.save(shelf); // Save the shelf object
            transaction.commit(); // Commit the transaction
            return  "Data saved ";
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback(); // Rollback if there's an error
            }
            ex.printStackTrace(); // Print the stack trace for debugging
            System.out.println("Error saving shelf: " + ex.getMessage());
        }
       return  null;
    }
    public Shelf findShelfById(UUID shelfId) {
        Transaction transaction = null;
        Shelf shelf = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); // Start a transaction
            shelf = session.get(Shelf.class, shelfId); // Retrieve the Shelf by its ID
            transaction.commit(); // Commit the transaction
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback(); // Rollback if there's an error
            }
            ex.printStackTrace(); // Print the stack trace for debugging
        }

        return shelf; // Return the found Shelf or null if not found
    }
}
