import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Film {
    private String titre;
    private int id;
    private int id_real;


    public Film(String titre, Personne real) {
        this.titre = titre;
        this.id_real = real.getId();
    }

    private Film(String titre, int id) {
        this.titre = titre;
        this.id = id;
    }

    public Film FindById(int id) throws SQLException {
        Connection connectid;
        connectid = DBConnection.getConnection();
        PreparedStatement prep1=connectid.prepareStatement("SELECT id, titre, id_rea FROM film WHERE id = ?");
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();

        while(rs.next()) {
            
        }


    }
}
