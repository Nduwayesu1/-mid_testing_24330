import Dao.RoomDao;
import UI.UserInterface;
import model1.Room;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RoomCreationTest {

    private RoomDao roomDao;
    private Scanner scanner;

    @Before
    public void setUp() {
        roomDao = mock(RoomDao.class);
        scanner = mock(Scanner.class);
    }

    @Test
    public void testCreateRoom_Success() {
        // Simulate user input for room code
        when(scanner.nextLine()).thenReturn("Room101");

        // Simulate the DAO saveRoom method
        when(roomDao.saveRoom(any(Room.class))).thenReturn("Room saved successfully.");

        // Call the method
        UserInterface.createRoom(scanner, roomDao);

        // Capture the Room object that was saved
        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomDao).saveRoom(roomCaptor.capture());

        // Validate the captured Room object
        Room savedRoom = roomCaptor.getValue();
        assertEquals("Room101", savedRoom.getRoomCode());
        // You can also check that a UUID is generated for roomId
        assertNotNull(savedRoom.getRoomId());
    }
}
