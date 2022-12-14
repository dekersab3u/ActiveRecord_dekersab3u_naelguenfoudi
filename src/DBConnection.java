import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private String userName = "root";
    private String password = MotDePasse.MDP;
    private String serverName = "localhost";
    //Attention, sous MAMP, le port est 8889
    private String portNumber = "3306";
    private String tableName = "testpersonne";

    // iL faut une base nommee testpersonne !
    private static String dbName = "testpersonne";
    private static Connection instance;


    private DBConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);

        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        instance = DriverManager.getConnection(urlDB, connectionProps);
    }
    public static synchronized Connection getConnection()  {
        if(instance==null) {
            try {
                new DBConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            return instance;
    }

    public static synchronized void setNomDB(String nomDB) throws SQLException {
        if(nomDB!=null){
            dbName=nomDB;
            new DBConnection();
        }
            }
}
