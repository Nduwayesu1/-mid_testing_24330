import Dao.ConnectionDao;
import org.junit.Test;

public class DbConnectionTesting {

    @Test
    public void testingConnection() {
        ConnectionDao connection=new ConnectionDao();
        System.out.println(connection.establishCon());
    }

}
