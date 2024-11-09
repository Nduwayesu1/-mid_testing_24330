import Dao.ShelfDao;
import Dao.RoomDao;
import UI.UserInterface;
import model1.Room;
import model1.Shelf;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class BookToShelfTest {

    private ShelfDao shelfDao;
    private RoomDao roomDao;
    private UserInterface userInterface;

    @Before
    public void setUp() {
        shelfDao = mock(ShelfDao.class); // Mock ShelfDao
        roomDao = mock(RoomDao.class);   // Mock RoomDao
        userInterface = new UserInterface();
    }

    @Test
    public void testCreateShelf_Success() {
        // Arrange
        String bookCategory = "Science Fiction";
        int initialStock = 50;
        int availableStock = 40;
        int borrowedNumber = 10;
        String roomIdInput = "d7a7ff72-960e-4e6f-b9c2-b94f5a604aa1"; // Sample room ID
        UUID roomId = UUID.fromString(roomIdInput);

        // Mock roomDao to return a room when findRoomById is called
        Room mockRoom = new Room(); // Create a mock Room instance
        mockRoom.setRoomId(roomId);
        when(roomDao.findRoomById(roomId)).thenReturn(mockRoom);

        // Mock shelfDao to return a success message when saveShelf is called
        when(shelfDao.saveShelf(any(Shelf.class))).thenReturn("Shelf created successfully!");

        // Simulate user input for creating a shelf
        String input = bookCategory + "\n" + initialStock + "\n" + availableStock + "\n" + borrowedNumber + "\n" + roomIdInput + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        UserInterface.createShelf(scanner, shelfDao, roomDao);

        // Assert
        // Verify the saveShelf method was called once with any Shelf object
        verify(shelfDao, times(1)).saveShelf(any(Shelf.class));
        // Assert that the output contains the success message
        assertTrue(outContent.toString().contains("Shelf created successfully!"));
    }

    @Test
    public void testCreateShelf_RoomNotFound() {
        // Arrange
        String bookCategory = "Mystery";
        int initialStock = 30;
        int availableStock = 25;
        int borrowedNumber = 5;
        String roomIdInput = "d7a7ff72-960e-4e6f-b9c2-b94f5a604aa1"; // Sample room ID
        UUID roomId = UUID.fromString(roomIdInput);

        // Mock roomDao to return null when findRoomById is called, simulating room not found
        when(roomDao.findRoomById(roomId)).thenReturn(null);

        // Simulate user input for creating a shelf
        String input = bookCategory + "\n" + initialStock + "\n" + availableStock + "\n" + borrowedNumber + "\n" + roomIdInput + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        UserInterface.createShelf(scanner, shelfDao, roomDao);

        // Assert
        // Verify that the findRoomById method was called once with the expected roomId
        verify(roomDao, times(1)).findRoomById(roomId);
        // Assert that the output contains the error message for room not found
        assertTrue(outContent.toString().contains("No room found with the given ID."));
    }

    @Test
    public void testCreateShelf_InvalidInput() {
        // Arrange
        String bookCategory = "Life";
        String invalidInitialStock = ""; // Invalid stock value
        String invalidAvailableStock = ""; // Invalid stock value
        String invalidBorrowedNumber = ""; // Invalid stock value
        String roomIdInput = "d7a7ff72-960e-4e6f-b9c2-b94f5a604aa1"; // Sample room ID

        // Simulate user input with invalid stock values
        String input = bookCategory + "\n" + invalidInitialStock + "\n" + invalidAvailableStock + "\n" + invalidBorrowedNumber + "\n" + roomIdInput + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        UserInterface.createShelf(scanner, shelfDao, roomDao);

        // Assert
        // Assert that the output contains the error message for invalid input
        assertTrue(outContent.toString().contains("Error during shelf creation"));
    }
}
