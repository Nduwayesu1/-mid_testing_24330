import Dao.BorrowerDao;
import model1.*;
import model1.Enum.EStatus;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MaxBorrowerTest {

    @InjectMocks
    private BorrowerDao borrowerDao;  // The DAO class we're testing

    @Mock
    private Session session;  // Mock Hibernate session

    @Mock
    private User user;  // Mock the User entity

    @Mock
    private Membership membership;  // Mock the Membership entity

    @Mock
    private Membership_type membershipType;  // Mock the MembershipType entity

    @Mock
    private Query<Long> query;  // Mock Hibernate query for counting borrowed books

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks before each test
    }

    @Test
    public void testCanUserBorrowMoreBooks_Success() {
        // Create a random UUID for the user
        UUID userId = UUID.fromString("eb779ce1-7b37-4b6d-bfe3-ce8cb1d1e736");
        int maxBooks = 3;  // Set max books allowed for the user
        long borrowedBooksCount = 2;  // User has already borrowed 2 books
        // Mock the User entity
        when(session.get(User.class, userId)).thenReturn(user);

        // Mock the Membership entity
        when(user.getMembershipList()).thenReturn(Arrays.asList(membership));
        when(membership.getMembershipTypes()).thenReturn(Arrays.asList(membershipType));
        when(membershipType.getMembership()).thenReturn(membership);
        when(membership.getStatus()).thenReturn(EStatus.APPROVED);  // Ensure the membership is approved
        when(membershipType.getMaxBooks()).thenReturn(maxBooks);  // Set maxBooks to 5

        // Mock the query to count the number of borrowed books
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(eq("userId"), eq(userId))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(borrowedBooksCount);  // User has borrowed 3 books

        // Act
        boolean result = borrowerDao.canUserBorrowMoreBooks(userId);  // Call the method under test

        // Assert
        // User can borrow more books (2 < 3)
        assertTrue(result);
    }

    @Test
    public void testCanUserBorrowMoreBooks_ExceededLimit() {
        // Arrange
        // Create a random UUID for the user
        UUID userId = UUID.fromString("eb779ce1-7b37-4b6d-bfe3-ce8cb1d1e736");
        // Set max books allowed for the user
        int maxBooks = 3;
        // User has already borrowed 2 books,
        long borrowedBooksCount = 2;
        // Mock the User entity
        when(session.get(User.class, userId)).thenReturn(user);

        // Mock the Membership entity
        when(user.getMembershipList()).thenReturn(Arrays.asList(membership));
        when(membership.getMembershipTypes()).thenReturn(Arrays.asList(membershipType));
        when(membershipType.getMembership()).thenReturn(membership);
        // Ensure the membership is approved
        when(membership.getStatus()).thenReturn(EStatus.APPROVED);
        // Set maxBooks to 3
        when(membershipType.getMaxBooks()).thenReturn(maxBooks);

        // Mock the query to count the number of borrowed books
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(eq("userId"), eq(userId))).thenReturn(query);
        // User has borrowed 2 books
        when(query.getSingleResult()).thenReturn(borrowedBooksCount);

        // Act
        // Call the method under test
        boolean result = borrowerDao.canUserBorrowMoreBooks(userId);

        // Assert
        // User cannot borrow more books (2 <  3)
        assertFalse(result);
    }



}
