import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


import java.sql.Connection;
import java.sql.SQLException;


public class Tests {

    public Connection testconnect;


    @BeforeEach
    public void beforeEach() throws SQLException {
        testconnect=DBConnection.getConnection();
    }

    @Test
    public void testDBConnect() throws SQLException {
        Connection t2 = DBConnection.getConnection();
        assertEquals(testconnect, t2);
    }

    @Test
    public void TestDBName() throws SQLException {
        DBConnection.setNomDB("test");
        Connection t2 = DBConnection.getConnection();
        boolean testname=t2 == testconnect;
        assertEquals(false,testname);
    }


}
