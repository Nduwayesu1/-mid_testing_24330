package Dao;

import jakarta.persistence.*;
import model1.HibernateUtil;
import model1.Location;
import model1.Person;
import model1.User;
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
            session.close();
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

    public User authenticateUser(String username, String password) {
        User user = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // HQL to fetch the user by username
            String hql = "FROM User u WHERE u.userName = :username";
            user = session.createQuery(hql, User.class)
                    .setParameter("username", username)
                    .uniqueResult();

            transaction.commit(); // Commit the transaction

            // Check if the user was found and validate the password
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                return user; // Return the authenticated user
            } else {
                return null; // Return null if authentication fails
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exceptions appropriately
            return null; // Return null in case of an error
        }
    }

    public User personId(UUID person_id) {
        User user = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin a transaction
            Transaction transaction = session.beginTransaction();

            // Fetch the user using the provided ID
            user = session.get(User.class, person_id);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exceptions appropriately
        }

        return user; // Return the found user or null if not found
    }



}
