import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import Dao.UserDao;
import model1.HibernateUtil;
import model1.User;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class AuthenticationTest {

    private UserDao userDao;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userDao = new UserDao();
        // Mocking the HibernateUtil to return our mocked session
        HibernateUtil.getSessionFactory();
        when(HibernateUtil.getSessionFactory().openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void testAuthenticateUser_Success() {
        String username = "testUser";
        String password = "correctPassword";
        User mockUser = new User();
        mockUser.setUserName(username);
        mockUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt())); // Hash the password

        // Setting up the mocked behavior for the session
        when(session.createQuery(anyString(), eq(User.class))).thenReturn(mock(Query.class));
        when(session.createQuery(anyString(), eq(User.class)).setParameter("username", "Olivier")).thenReturn(mock(Query.class));
        when(session.createQuery(anyString(), eq(User.class)).setParameter("username", "Olivier").uniqueResult()).thenReturn(mockUser);

        User result = userDao.authenticateUser(username, password);
        assertNotNull(result);
        assertEquals(username, result.getUserName());
    }


}
