import Dao.RoomDao;
import Dao.ShelfDao;
import UI.UserInterface;
import model1.Room;
import model1.Shelf;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssugnShelfToRoomTest {


    private ShelfDao shelfDao;
    private RoomDao roomDao;
    private Scanner scanner;

    @Before
    public void setUp() {
        shelfDao = mock(ShelfDao.class);
        roomDao = mock(RoomDao.class);
        scanner = mock(Scanner.class);
    }

    @Test
    public void testCreateShelf_Success() {
        // Prepare mock data
        UUID roomId = UUID.randomUUID();
        Room room = new Room();
        room.setRoomId(roomId);
        room.setRoomCode("Room1");

        // Simulate user input
        when(scanner.nextLine()).thenReturn("Fiction", "10", "5", "2", roomId.toString());
        when(roomDao.findRoomById(roomId)).thenReturn(room);

        // Call the method
        UserInterface.createShelf(scanner, shelfDao, roomDao);

        // Capture the Shelf object that was saved
        ArgumentCaptor<Shelf> shelfCaptor = ArgumentCaptor.forClass(Shelf.class);


        // Validate the captured Shelf object
        Shelf savedShelf = shelfCaptor.getValue();
        assertEquals("Fiction", savedShelf.getBookCategory());
        assertEquals(Optional.of(10), savedShelf.getInitialStock());
        assertEquals(Optional.of(5), savedShelf.getAvailableStock());
        assertEquals(Optional.of(2), savedShelf.getBorrowedNumber());
        assertEquals(room, savedShelf.getRoom());
    }

}
