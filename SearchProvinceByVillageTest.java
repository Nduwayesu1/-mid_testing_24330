import Dao.LocationDao;
import UI.UserInterface;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SearchProvinceByVillageTest {

    private LocationDao locationDao;
    private final PrintStream originalOut = System.out; // Store original System.out
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream(); // Capture output

    @Before
    public void setUp() {
        locationDao = mock(LocationDao.class); // Mocking the LocationDao for testing
        System.setOut(new PrintStream(outputStreamCaptor)); // Redirect System.out
    }

    @Test
    public void testSearchByVillage_Found() {
        // Arrange
        // Specify the name of the village
        String villageName = "Kinyinya"; // Manual input for the village name
        String input = villageName + "\n"; // Format as if it were console input
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Mocking the searchByVillages method to return a province when the village is found
        when(locationDao.searchByVillages(villageName)).thenReturn("KIGALI");

        // Act
        UserInterface.searchByVillage(scanner, locationDao);

        // Assert
        // Verify that searchByVillages was called with the correct village name
        verify(locationDao, times(1)).searchByVillages(villageName);
        // Check the output
        assertTrue(outputStreamCaptor.toString().contains("The province for the village 'Kinyinya' is: KIGALI"));
    }


}
