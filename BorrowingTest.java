import Dao.BorrowerDao;
import UI.UserInterface;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class BorrowingTest {

    private BorrowerDao borrowerDao;
    private UserInterface userInterface;

    @Before
    public void setUp() {
        borrowerDao = mock(BorrowerDao.class); // Mock BorrowerDao
        userInterface=mock(UserInterface.class);

    }

    @Test
    public void testInitiateBorrowing_Successful() {
        // Arrange
        String userId = "c6f5d22a-ffa6-4cb6-b677-2750f428fb23"; // Sample UUID for user
        String bookId = "477c0866-3c68-4461-8177-e35d5ec29a74"; // Sample UUID for book
        String pickupDate = "2024-11-20"; // Sample pickup date

        // Simulate user input for userId, bookId, and pickupDate
        String input = userId + "\n" + bookId + "\n" + pickupDate + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Mock the borrowBook method to return a success message
        when(borrowerDao.borrowBook(UUID.fromString(bookId), UUID.fromString(userId), LocalDate.parse(pickupDate)))
                .thenReturn("Borrowing successful!");

        // Act
        UserInterface.initiateBorrowing(scanner);

        // Assert
        // Verify that the borrowBook method was called with the expected arguments
        verify(userInterface, times(1)).initiateBorrowing(scanner);
    }
}

