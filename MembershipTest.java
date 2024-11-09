import Dao.MembershipDao;
import Dao.UserDao;
import UI.UserInterface;
import model1.Enum.EStatus;
import model1.User;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class CreateMembershipTest {

    private MembershipDao membershipDao;
    private UserDao userDao;

    @Before
    public void setUp() {
        membershipDao = mock(MembershipDao.class); // Mock MembershipDao
        userDao = mock(UserDao.class); // Mock UserDao
    }

    @Test
    public void testCreateMembership() {
        // Arrange

        String membershipCode = "M12345";
        String expiringDate = "2025-12-31";
        String pricePerDay = "10";
        EStatus status= EStatus.PENDING;
        String userId = "eb779ce1-7b37-4b6d-bfe3-ce8cb1d1e736";

        // Simulate user input
        String input = membershipCode + "\n" + expiringDate + "\n" + pricePerDay + "\n" + status + "\n" + userId + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);

        // Set up a mock user with the given user ID
        UUID userUUID = UUID.fromString(userId);
        User user = new User(userUUID, "student");
        when(userDao.getUserId(userUUID)).thenReturn(user);

        // Act
        UserInterface.createMembership(scanner, membershipDao);

        // Assert
        // Verify that registerMembership was called once with any Membership object
        verify(membershipDao, times(1)).registerMembership(any());
    }
}
