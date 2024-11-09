package Dao;

import jakarta.persistence.*;
import model1.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.UUID;

import static model1.HibernateUtil.sessionFactory;

public class UserDao {

    private UserDao userDao;

    private SessionFactory sessionFactory;

    public UserDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public String saveUser(User user) {

        try {

            Session session= HibernateUtil.getSessionFactory().openSession();
            Transaction  tr= session.beginTransaction();
            session.save(user);
            tr.commit();
            return  "user Created Succesfull";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Location findLocationByCode(String locationCode) {
        Session session = sessionFactory.openSession();
        Location location = null;

        try {
            location = session.createQuery("FROM Location WHERE locationCode = :code", Location.class)
                    .setParameter("code", locationCode)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Error finding location by code: " + e.getMessage());
        } finally {
            session.close();
        }

        return location;
    }

    // this methood is not giving the data
    // I need to implement it
    public String getLocationByPersonId(String personId) {
        String locationName = null; // Initialize the location name to null
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // Create a query to fetch the location name based on the personId
                String hql = "SELECT u.location.locationName FROM User u WHERE u.personId = :personId";
                locationName = session.createQuery(hql, String.class)
                        .setParameter("personId", personId)
                        .uniqueResult();
                transaction.commit(); // Commit the transaction
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback(); // Rollback in case of an error
                }
                e.printStackTrace(); // Handle exceptions appropriately
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exceptions for session opening
        }
        return locationName; // Return the location name or null if not found
    }

    public String authenticateUser(UUID userId, String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Modify the query to only retrieve the password field
            String hql = "SELECT u.userName FROM User u WHERE u.userId = :userId";
            org.hibernate.query.Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("userId", userId);

            // Retrieve the password field only
            List<String> results = query.getResultList();
            transaction.commit();

            if (!results.isEmpty()) {
                String retrievedUsername = results.get(0); // Get the hashed password

                // Validate the password against the hashed password using BCrypt
                if (retrievedUsername.equals(username)) {
                    return "Success"; // Authentication successful
                } else {
                    return "Failed"; // Password mismatch
                }
            } else {
                return "Not Found"; // No user found
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exceptions appropriately
            return "Internal server Error"; // Handle exceptions gracefully
        }
    }





    public boolean canUserBorrowMoreBooks(UUID userId) {
        // Define the maximum number of books a user can borrow
        int maxBooksAllowed = 5;

        // Initialize the number of books borrowed by the user
        int borrowedCount = 0;

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Start a transaction
            transaction = session.beginTransaction();

            // Query to count the number of books borrowed by the user
            Query query = (Query) session.createQuery("SELECT COUNT(b) FROM Borrower b WHERE b.reader_id = :userId", Borrower.class);
            query.setParameter("userId", userId);
            borrowedCount = query.getMaxResults(); // Get the count of borrowed books

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback if there was an error
            }
            e.printStackTrace(); // Handle the exception (logging, etc.)
        }

        // Check if the user can borrow more books
        return borrowedCount < maxBooksAllowed;
    }

    public User getUserId(UUID userId) {
        User user = null; // Initialize user to null

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin a transaction
            Transaction transaction = session.beginTransaction();

            // Fetch the user using the provided userId
            user = session.get(User.class, userId);

            // Commit the transaction
            transaction.commit();

            if (user == null) {
                System.out.println("No user found with the provided user ID: " + userId);
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exceptions appropriately
        }

        return user; // Return the user or null if not found
    }


    // Method to get the province name based on person ID
    public String getProvinceByPersonId(String personId) {
        Transaction transaction = null;
        String provinceName = null;

        // Try-with-resources block to ensure session is closed properly
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Query to find the user's location
            String hql = "SELECT u.location.parentLocation.locationName FROM User u WHERE u.personId = :personId";
            Query query = (Query) session.createQuery(hql, String.class);
            query.setParameter("personId", personId);

            provinceName = query.toString();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return provinceName;
    }

}
