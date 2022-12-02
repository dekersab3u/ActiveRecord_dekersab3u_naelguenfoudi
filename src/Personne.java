import java.sql.*;
import java.util.ArrayList;

public class Personne {

    /**
     * id de la personne , id unique par personne
     */
    private int id;
    private String prenom;
    private String nom;
    /**
     * connection pour la BDD
     */
    private static Connection connect=DBConnection.getConnection();

    public Personne(String prenom, String nom) throws SQLException {
        this.prenom = prenom;
        this.nom = nom;
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    /**
     * permet de recuperer une liste d'objet Personne en fonction des données de la table personne (DB)
     *
     * @return une liste de Personne
     * @throws SQLException
     */
    public static ArrayList<Personne> findAll() throws SQLException {
        ArrayList<Personne> listPersonne = new ArrayList<Personne>();
        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        return getPersonnes(listPersonne, rs);
    }

    /**
     * permet de  trouver une personne dans la DB grâce à l'ID et creer un objet  Personne
     *
     * @param id de la personne à trouver
     * @return un objet Personne
     * @throws SQLException
     */
    public static Personne findById(int id) throws SQLException {
        Personne personne = null;
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
            personne = new Personne(prenom, nom);
            personne.id = id;
        }
        return personne;

    }

    /**
     * permet de trouver toutes les personnes en fonction d'un meme nom et de retourner une liste de Personne
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public static ArrayList<Personne> findByName(String name) throws SQLException {
        ArrayList<Personne> listPersonne = new ArrayList<Personne>();
        String SQLPrep = "SELECT * FROM Personne WHERE nom=?;";
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setString(1, name);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        return getPersonnes(listPersonne, rs);
    }

    /**
     * creer une table Personne
     *
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        String drop = "DROP TABLE Personne";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    /**
     * permet de supprimer une Personne dans la table si elle s'y trouve
     *
     * @throws SQLException
     */
    public void delete() throws SQLException {
        if (id != -1) {
            String deleteString = "DELETE FROM Personne where ?";
            PreparedStatement prep1 = connect.prepareStatement(deleteString);
            prep1.setInt(1, id);
            prep1.executeUpdate();
            id = -1;
        }

    }

    /**
     * permet de sauvegarder ou de mettre à jour , si la personne est déjà dans la table, une Personne dans la table
     *
     * @throws SQLException
     */
    public void save() throws SQLException {
        if (id == -1) {
            saveNew();
        } else {
            updateP();
        }
    }

    /**
     * permet de recuperer l'id d'une personne dans al table
     *
     * @param prep1
     * @return
     * @throws SQLException
     */
    public static int getIdIncrement(PreparedStatement prep1) throws SQLException {
        ResultSet rs = prep1.getGeneratedKeys();
        int idReturn = -1;
        if (rs.next()) {
            idReturn = rs.getInt(1);
        }
        return idReturn;
    }

    /**
     * permet de sauvegarder une nouvelle personne dans la table
     *
     * @throws SQLException
     */
    private void saveNew() throws SQLException {
        String insertString = "INSERT INTO Personne(nom,prenom) VALUES(?,?)";
        PreparedStatement prep1 = connect.prepareStatement(insertString);
        prep1.setString(1, nom);
        prep1.setString(2, prenom);
        prep1.executeUpdate();
        id = getIdIncrement(prep1);
    }

    /**
     * permet de mettre à jour les infos d'une personne
     *
     * @throws SQLException
     */
    private void updateP() throws SQLException {
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, nom);
        prep.setString(2, prenom);
        prep.setInt(3, id);
        prep.execute();
    }

    private static ArrayList<Personne> getPersonnes(ArrayList<Personne> listPersonne, ResultSet rs) throws SQLException {
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");

            Personne personne = new Personne(prenom, nom);

            personne.id = rs.getInt("id");
            listPersonne.add(personne);

        }
        return listPersonne;
    }

    public void setNom(String nom) {
        this.nom=nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setPrenom(String prenom) {
        this.prenom=prenom;
    }
}
