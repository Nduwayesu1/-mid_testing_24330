import Dao.MembershipDao;
import Dao.UserDao;
import UI.UserInterface;
import model1.Enum.EStatus;
import model1.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MembershipTest {
    private MembershipDao membershipDao;
    private UserDao userDao;

    @Before
    public void setUp() {
        membershipDao = mock(MembershipDao.class);
        userDao = mock(UserDao.class);
    }

    @Test
    public void testCreateMembership_ValidInput_ShouldCreateMembership() {
        // Arrange
        String input = "MEM123\n2024-12-31\n100\nPENDING\n" + UUID.randomUUID().toString() + "\n"; // Mocked input
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Generate a mock user ID for the test
        UUID userId = UUID.fromString(input.split("\n")[4]);

        // Mocking UserDao to return a valid user
        User mockUser = new User(); // Assuming a User class with a default constructor
        when(userDao.personId(UUID.fromString(String.valueOf(userId)))).thenReturn(mockUser);

        // Act
        UserInterface.createMembership(scanner, membershipDao); // Use scanner directly

        // Capture the arguments for verification
        ArgumentCaptor<String> membershipCodeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<LocalDate> expiringTimeCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<Integer> pricePerDayCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<EStatus> statusCaptor = ArgumentCaptor.forClass(EStatus.class);
        ArgumentCaptor<UUID> userIdCaptor = ArgumentCaptor.forClass(UUID.class);

        // Verify the call to registerMembership
        verify(membershipDao).registerMembership(
                membershipCodeCaptor.capture(),
                expiringTimeCaptor.capture(),
                pricePerDayCaptor.capture(),
                statusCaptor.capture(),
                UUID.fromString(String.valueOf(userIdCaptor.capture()))
        );

        // Assert the captured values
        assertEquals("MEM123", membershipCodeCaptor.getValue());
        assertEquals(LocalDate.parse("2024-12-31"), expiringTimeCaptor.getValue());
        assertEquals(EStatus.PENDING, statusCaptor.getValue());
        assertEquals(userId, userIdCaptor.getValue());
    }
}
