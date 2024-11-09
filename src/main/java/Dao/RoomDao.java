package Dao;

import model1.HibernateUtil;
import model1.Room;
import model1.Shelf;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public String saveRoom(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(room);
            transaction.commit();
            return "Room saved successfully.";
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "Error saving room: " + e.getMessage();
        }
    }

    public String getRoomWithFewestBooks() {
        String roomDetails = "No room found or an error occurred.";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Room> query = session.createQuery(
                    "SELECT r FROM Room r " +
                            "LEFT JOIN r.shelfList s " +
                            "LEFT JOIN s.bookList b " +
                            "GROUP BY r " +
                            "ORDER BY COUNT(b) ASC",
                    Room.class
            );
            query.setMaxResults(1); // Limit to get only the room with the fewest books

            Room room = query.uniqueResult();
            if (room != null) {
                roomDetails = "Room Code: " + room.getRoomCode() +
                        ", Room ID: " + room.getRoomId() +
                        ", Book Count: " + room.countBooksInRoom();
            }
        } catch (Exception e) {
            System.out.println("Error retrieving the room with the fewest books: " + e.getMessage());
        }

        return roomDetails;
    }

    public long findNumberOfBooksInRoom(UUID roomId) {
        long bookCount = 0;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Corrected HQL query to count books in a room
            String hql = "SELECT COUNT(b) " +
                    "FROM Room r " +
                    "JOIN r.shelfList s " +    // Join on shelves of the room
                    "JOIN s.bookList b " +     // Join on books in each shelf
                    "WHERE r.roomId = :roomId"; // Only for the specific room

            // Create the query and set the parameter
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("roomId", roomId);

            // Get the result and handle null values
            bookCount = query.uniqueResult() != null ? query.uniqueResult() : 0; // Return the total number of books
        } catch (Exception e) {
            System.out.println("Error retrieving the number of books in the room: " + e.getMessage());
        }

        return bookCount;  // Return the number of books
    }


}
