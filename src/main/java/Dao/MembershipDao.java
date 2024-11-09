package Dao;

import model1.Enum.EStatus;
import model1.HibernateUtil;
import model1.Membership;
import model1.Membership_type;
import model1.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MembershipDao {


    public void registerMembership(Membership membership) {
        // Ensure that required fields are set before saving
        if (membership.getMembershipCode() == null || membership.getMembershipCode().isEmpty()) {
            throw new IllegalArgumentException("Membership code cannot be null or empty.");
        }

        // Fetch the user based on the user's ID stored in the membership
        User user = membership.getUser(); // Assuming user is already set in Membership

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        // Save the new membership to the database
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(membership); // Save the membership
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            ex.printStackTrace();
            throw new RuntimeException("Failed to register membership: " + ex.getMessage());
        }
    }
    public void registerMembership(String membershipCode, LocalDate expiringTime, Integer pricePerDay, EStatus status, UUID userId) {
        // Create a new Membership instance
        Membership newMembership = new Membership(
                UUID.randomUUID(), // Generate a new UUID for membershipId
                membershipCode,
                expiringTime,
                pricePerDay,
                null, // Initially no membership types
                status,
                findUserById(userId), // Retrieve the User using userId
                LocalDate.now() // Set the registration date as today
        );

        // Save the new membership to the database
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(newMembership); // Save the Membership object
            transaction.commit(); // Commit the transaction
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            ex.printStackTrace(); // Log the exception
        }
    }

    private User findUserById(UUID userId) {
        // Logic to retrieve user from the database using userId
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, userId); // Fetch user by UUID
        }
    }

}
