package Dao;

import model1.Borrower;
import model1.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class BorrowerDao {

    public String borrowBook(Borrower borrower) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(borrower);
            transaction.commit();
            return "Book borrowed successfully.";
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "Error borrowing book: " + e.getMessage();
        }
    }
}
