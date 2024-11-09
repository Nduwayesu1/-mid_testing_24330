import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.UUID;

import Dao.LocationDao;
import UI.UserInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetUserProvinceNameTest {

    @Mock
    private LocationDao locationDao;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outContent));  // Redirect output to capture printed statements
    }

    @Test
    public void testGetUserProvinceName() {
        // Arrange
        UUID userId = UUID.fromString("72938177-67a8-4b97-8112-66603b5f00d0");
        String userIdInput = userId.toString() + "\n";  // Simulate input followed by newline
        String expectedProvinceName = "Kigali";

        // Set up input and mock behavior
        System.setIn(new ByteArrayInputStream(userIdInput.getBytes()));  // Mock user input
        when(locationDao.getUserProvinceName(userId)).thenReturn(expectedProvinceName);

        // Act
        UserInterface.getUserProvinceNameFromInput();  // Call method to test

        // Assert
        assertTrue(outContent.toString().contains("Province: " + expectedProvinceName));

    }
}
