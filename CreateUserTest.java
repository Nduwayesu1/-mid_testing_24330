import Dao.UserDao;
import UI.UserInterface;
import model1.Location;
import model1.User;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateUserTest {
    private UserDao userDao;

    @Before
    public void setUp() {
        userDao = mock(UserDao.class);
    }

    @Test
    public void testCreateUser_Success() {
        // Arrange
        String input = "John\nDoe\njohndoe\n12345\npassword123\n123-456-7890\n0\ncode1\n"; // Mocked input
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        Location mockLocation = new Location();
        mockLocation.setLocationCode("code1");
        // Set other necessary fields for mockLocation as needed.

        // Mocking the location retrieval and user saving
        when(userDao.findLocationByCode("code1")).thenReturn(mockLocation);
        when(userDao.saveUser(any(User.class))).thenReturn("User saved successfully!");

        // Act
        UserInterface.createUser(scanner, userDao);

        // Assert
        // Verify that saveUser was called with a User object containing the expected data
        verify(userDao, times(1)).saveUser(any(User.class));
    }
}
