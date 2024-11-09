import Dao.UserDao;
import UI.UserInterface;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.UUID;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class AuthenticationTest {

    private UserDao userDao;
    private final PrintStream originalOut = System.out; // Store original System.out
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream(); // Capture output

    @Before
    public void setUp() {
        userDao = mock(UserDao.class); // Mocking the UserDao for testing
        System.setOut(new PrintStream(outputStreamCaptor)); // Redirect System.out
    }

    @Test
    public void testUserLogin_SuccessfulLogin() {
        // Arrange
        // Simulate user ID and username input
        String userId = "eb779ce1-7b37-4b6d-bfe3-ce8cb1d1e736";
        String username = "student";
        String input = userId + "\n" + username + "\n"; // Simulate user entering ID and username
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Mocking the authenticateUser method to return a successful login message
        when(userDao.authenticateUser(UUID.fromString(userId), username)).thenReturn("User authenticated successfully.");

        // Act
        UserInterface.userLogin(scanner, userDao);

        // Assert
        // Verify that authenticateUser was called with the correct parameters
        verify(userDao, times(1)).authenticateUser(UUID.fromString(userId), username);

        // Check if the output contains the expected success message
        assertTrue(outputStreamCaptor.toString().contains("User authenticated successfully."));
    }

    @Test
    public void testUserLogin_InvalidUserIdFormat() {
        // Arrange
        // Simulate an invalid UUID input
        String invalidUserId = "eb779ce17b374b6d-bfe3ce8cb1d1e736";
        String input = invalidUserId + "\n"; // Simulate user entering invalid user ID
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Act
        UserInterface.userLogin(scanner, userDao);

        // Assert
        // Check if the output contains the expected error message
        assertTrue(outputStreamCaptor.toString().contains("Invalid User ID format. Please enter a valid UUID."));
    }

    @Test
    public void testUserLogin_EmptyUserId() {
        // Arrange
        String input = "\n"; // Simulate user entering an empty user ID
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Act
        UserInterface.userLogin(scanner, userDao);

        // Assert
        // Check if the output contains the expected error message
        assertTrue(outputStreamCaptor.toString().contains("User ID cannot be empty."));
    }

}
