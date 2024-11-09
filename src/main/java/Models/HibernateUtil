package model1;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    public static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the Configuration instance
            Configuration configuration = new Configuration();

            // Set Hibernate properties for PostgreSQL directly
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5433/auca_library_db");
            configuration.setProperty("hibernate.connection.username", "postgres");
            configuration.setProperty("hibernate.connection.password", "1234");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            // Register all annotated entity classes
            configuration.addAnnotatedClass(Book.class);
            configuration.addAnnotatedClass(Borrower.class);
            configuration.addAnnotatedClass(Location.class);
            configuration.addAnnotatedClass(Membership.class);
            configuration.addAnnotatedClass(Membership_type.class);
            configuration.addAnnotatedClass(Room.class);
            configuration.addAnnotatedClass(Shelf.class);
            configuration.addAnnotatedClass(User.class);


            // Build the SessionFactory
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
