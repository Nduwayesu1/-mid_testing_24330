package Dao;

import model1.Book;
import model1.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class BookDao {


    public String saveBook(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(book);
            transaction.commit();
            return "Data Saved Successfully";
        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return null;
    }


    public Book findBookById(UUID bookId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, bookId);
        }
    }

    public int getTotalNumberOfBooks() {
        int count = 0;
        String hql = "SELECT COUNT(b) FROM Book b"; // Adjust 'Book' to match your entity class name

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(hql, Long.class);
            count = query.uniqueResult().intValue();
        } catch (Exception e) {
            System.out.println("Error retrieving total number of books: " + e.getMessage());
        }

        return count;
    }
}
