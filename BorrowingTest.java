


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

    @Before
    public void setUp() {
        borrowerDao = mock(BorrowerDao.class);
        bookDao = mock(BookDao.class);
        userDao = mock(UserDao.class);
    }

    @Test
    public void testInitiateBorrowing() {
        // Mock user input
        String userInput = "123e4567-e89b-12d3-a456-426614174000\n" + // user ID
                "456e4567-e89b-12d3-a456-426614174001\n" + // book ID
                "2023-11-01\n"; // pickup date
        InputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Mock Book and User
        UUID userId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UUID bookId = UUID.fromString("456e4567-e89b-12d3-a456-426614174001");

        Book mockBook = new Book(); // Assuming you have a default constructor
        User mockUser = new User(); // Assuming you have a default constructor

        when(bookDao.findBookById(bookId)).thenReturn(mockBook);
        when(userDao.personId(userId)).thenReturn(mockUser);

        // Call the method under test
        UserInterface.initiateBorrowing(scanner, borrowerDao, bookDao, userDao);

        // Verify the interactions and state
        BorrowerId expectedBorrowerId = new BorrowerId(bookId, userId, LocalDate.parse("2023-11-01"), LocalDate.parse("2023-11-15")); // Adjust according to your logic
        Borrower expectedBorrower = new Borrower(expectedBorrowerId, mockBook, mockUser, LocalDate.parse("2023-11-15"), 0);

        verify(borrowerDao).borrowBook(expectedBorrower); // Verify that borrowBook was called
    }
}
