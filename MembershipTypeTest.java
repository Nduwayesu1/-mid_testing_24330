import Dao.Membership_typeDao;
import UI.UserInterface;
import model1.Membership_type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class MembershipTypeTest {

    private Membership_typeDao membershipTypeDao;
    private Scanner scanner;

    @BeforeEach
    public void setUp() {
        membershipTypeDao = Mockito.mock(Membership_typeDao.class);
        scanner = new Scanner(System.in);
    }

    @Test
    public void testCreateMembershipType() {
        // Set up user input
        String input = "GOLD\n5\n50\n\n"; // Simulate user input
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        scanner = new Scanner(System.in);

        // Call the method to create membership type
        UserInterface.create_membershipType(scanner, membershipTypeDao);

        // Prepare expected values
        String expectedMembershipName = "GOLD";
        Integer expectedMaxBooks = 5;
        Integer expectedPrice = 50;

        // Verify that the DAO's save method was called with correct parameters
        verify(membershipTypeDao, times(1)).saveMembershipType(expectedMembershipName, expectedMaxBooks, expectedPrice);
    }


}
