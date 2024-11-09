import Dao.RoomDao;
import UI.UserInterface;
import model1.Room;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ShelfToRoomTest {

    private RoomDao roomDao;
    private Scanner scanner;

    @Before
    public void setUp() {
        roomDao = mock(RoomDao.class);
        ByteArrayInputStream inputStream = new ByteArrayInputStream("Room3".getBytes());
        scanner = new Scanner(inputStream);
    }

    @Test
    public void testCreateRoom_Success() {
        // Simulate the DAO saveRoom method
        when(roomDao.saveRoom(any(Room.class))).thenReturn("Room saved successfully.");

        // Call the method
        UserInterface.createRoom(scanner, roomDao);

        // Capture the Room object that was saved
        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        verify(roomDao).saveRoom(roomCaptor.capture());

        // Validate the captured Room object
        Room savedRoom = roomCaptor.getValue();
        assertEquals("Room3", savedRoom.getRoomCode());
        // You can also check that a UUID is generated for roomId
        assertNull(savedRoom.getRoomId());
    }
}
