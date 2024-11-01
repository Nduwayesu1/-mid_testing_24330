package Dao;

import model1.HibernateUtil;
import model1.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;

public class RoomDao {
    public Room findRoomById(UUID roomId) {
        Transaction transaction = null;
        Room room = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); // Start a transaction
            room = session.get(Room.class, roomId); // Retrieve the Room by its ID
            transaction.commit(); // Commit the transaction
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback(); // Rollback if there's an error
            }
            ex.printStackTrace(); // Print the stack trace for debugging
        }

        return room; // Return the found Room or null if not found
    }
}
