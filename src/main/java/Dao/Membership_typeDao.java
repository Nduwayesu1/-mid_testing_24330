package Dao;

import model1.HibernateUtil;
import model1.Membership;
import model1.Membership_type;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.UUID;

public class Membership_typeDao {

    public String saveMembershipType(String membershipName, Integer maxBooks, Integer price, Membership membership) {
        if (membershipName == null || maxBooks == null || price == null || membership == null) {
            return "Invalid input.";
        }

        Membership_type membershipType = new Membership_type();
        membershipType.setMembershipTypeId(UUID.randomUUID());
        membershipType.setMembershipName(membershipName);
        membershipType.setMaxBooks(maxBooks);
        membershipType.setPrice(price);
        membershipType.setMembership(membership);  // Set the association

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // Check if Membership is unsaved (new)
                if (membership.getMembershipId() == null) {
                    session.save(membership);  // Save Membership first if it is unsaved
                }

                // Now save Membership_type with the saved Membership entity
                session.save(membershipType);
                transaction.commit();
                return "Membership type created successfully";
            } catch (Exception e) {
                transaction.rollback(); // Rollback if there's an error
                return "Error during saving membership type: " + e.getMessage();
            }
        } catch (Exception e) {
            return "Error during session management: " + e.getMessage();
        }
    }



    public Membership findMembershipById(UUID membershipId) {
        Membership membership = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Fetch the membership using the provided ID
            membership = session.get(Membership.class, membershipId);

            // Optionally log if the membership is not found
            if (membership == null) {
                System.out.println("No membership found with ID: " + membershipId);
            }else{
                return membership;
            }
        } catch (Exception ex) {
            // Log the exception for further analysis
            System.err.println("Error fetching membership by ID: " + ex.getMessage());
            ex.printStackTrace(); // Optional: Log the stack trace if needed
        }

        // Return the found membership or null if not found
        return null;
    }

}
