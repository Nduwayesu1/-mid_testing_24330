import Dao.BookDao;
import Dao.ShelfDao;
import UI.UserInterface;
import model1.Book;
import model1.Enum.EBook_status;
import model1.Shelf;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AssignBookToShelfTest {

    private BookDao bookDao;
    private ShelfDao shelfDao;
    private Scanner scanner;

    @Before
    public void setUp() {
        bookDao = mock(BookDao.class);
        shelfDao = mock(ShelfDao.class);
        scanner = mock(Scanner.class);
    }

    @Test
    public void testCreateBook_Success() {
        // Prepare mock data
        UUID shelfId = UUID.randomUUID();
        Shelf shelf = new Shelf();
        shelf.setShelfId(shelfId);
        shelf.setBookCategory("Fiction");

        // Simulate user input
        when(scanner.nextLine()).thenReturn("Effective Java", "9780134686097", "3",
                "2021-01-01", "Addison-Wesley", "AVAILABLE", shelfId.toString());
        when(shelfDao.findShelfById(shelfId)).thenReturn(shelf);

        // Call the method
        UserInterface.createBook(scanner, bookDao, shelfDao);

        // Capture the Book object that was saved
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookDao).saveBook(bookCaptor.capture());

        // Validate the captured Book object
        Book savedBook = bookCaptor.getValue();
        assertEquals("Effective Java", savedBook.getTitle());
        assertEquals("9780134686097", savedBook.getIsbnCode());
        assertEquals(Integer.valueOf(3), savedBook.getEdition());
        assertEquals(LocalDate.of(2021, 1, 1), savedBook.getPublicationYear());
        assertEquals("Addison-Wesley", savedBook.getPublisherName());
        assertEquals(EBook_status.AVAILABLE, savedBook.getStatus());
        assertEquals(shelf, savedBook.getShelf());
    }
}
