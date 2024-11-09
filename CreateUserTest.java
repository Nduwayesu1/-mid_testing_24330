import Dao.LocationDao; // Use your actual implementation
import Dao.UserDao; // Use your actual implementation
import model1.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestLocation {

    private LocationDao locationDao; // Real implementation
    private UserDao userDao; // Real implementation

    @BeforeEach
    public void setUp() {
        // Initialize your DAOs with actual database connections here
        locationDao = new LocationDao(); // or however you instantiate it
        userDao = new UserDao();
    }

    @AfterEach
    public void tearDown() {
        // Clean up any test data from the database if needed
    }

    @Test
    public void testCreateLocation_Success() {
        // Simulate user input
        String input = "CODE124\nNYARUGENGE\n0\ncode1\n"; // Location code, name, type, no parent
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        UI.UserInterface.createLocation(new Scanner(System.in), locationDao);

        // Add assertions to verify the location was actually saved in the database
        Location savedLocation = locationDao.findByLocationCode("CODE124");
        assertNotNull(savedLocation); // Check that the location exists
        assertEquals("NYARUGENGE", savedLocation.getLocationName()); // Check the name
    }
}
