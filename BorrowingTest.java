


import static UI.UserInterface.initiateBorrowing;
import static org.mockito.Mockito.*;

import Dao.BookDao;
import Dao.BorrowerDao;
import Dao.UserDao;
import UI.UserInterface;
import model1.Book;
import model1.Borrower;

import model1.BorrowerId;
import model1.User;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class BorrowingTest {

    private BorrowerDao borrowerDao;
    private BookDao bookDao;
    private UserDao userDao;
    private Scanner scanner;

    @Before
    public void setUp() {
        borrowerDao = mock(BorrowerDao.class);
        bookDao = mock(BookDao.class);
        userDao = mock(UserDao.class);
        scanner = new Scanner(System.in);
    }

    @Test
    public void testInitiateBorrowing_Success() {
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        User mockUser = new User(); // Mock User object
        Book mockBook = new Book(); // Mock Book object

        when(userDao.personId(userId)).thenReturn(mockUser);
        when(bookDao.findBookById(bookId)).thenReturn(mockBook);
        when(userDao.canUserBorrowMoreBooks(userId)).thenReturn(true);
        when(borrowerDao.borrowBook(any(Borrower.class))).thenReturn("Book borrowed successfully.");

        // Simulate user input
        String input = userId.toString() + "\n" + bookId.toString() + "\n" +
                "2024-11-01\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        // Call the method
        initiateBorrowing(scanner, borrowerDao, bookDao, userDao);

        // Verify interactions
        verify(userDao).personId(userId);
        verify(bookDao).findBookById(bookId);
        verify(userDao).canUserBorrowMoreBooks(userId);
        verify(borrowerDao).borrowBook(any(Borrower.class));
    }

}
