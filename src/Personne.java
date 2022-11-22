import java.sql.*;
import java.util.ArrayList;

public class Personne {
    private int id;
    private String prenom;
    private String nom;
    private static Connection connect;

    public Personne(String prenom, String nom) throws SQLException {
        this.prenom = prenom;
        this.nom = nom;
        this.id=-1;
        connect=DBConnection.getConnection();
    }

    public int getId() {
        return id;
    }

    public ArrayList<Personne> findAll() throws SQLException {
        ArrayList<Personne> listPersonne=new ArrayList<>();
        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");

            Personne personne=new Personne(prenom,nom);

            personne.id=rs.getInt("id");
            listPersonne.add(personne);

        }
        return listPersonne;
    }
    public Personne findById(int id) throws SQLException {
        Personne personne=null;
        String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            id = rs.getInt("id");
            personne=new Personne(prenom,nom);
            personne.id=id;
        }
        return personne;

    }
    public ArrayList<Personne> findByName(String name) throws SQLException {
        ArrayList<Personne> listPersonne=new ArrayList<>();
        String SQLPrep = "SELECT * FROM Personne WHERE nom=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setString(1,nom);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");

            Personne personne=new Personne(prenom,nom);

            personne.id=rs.getInt("id");
            listPersonne.add(personne);

        }
        return listPersonne;
    }

    public void createTable() throws SQLException {
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }
    public void deleteTable() throws SQLException {
        String drop = "DROP TABLE Personne";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }
}
