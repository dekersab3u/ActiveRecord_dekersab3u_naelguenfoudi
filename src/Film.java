import java.sql.*;
import java.util.ArrayList;

import Exception.RealisateurAbsentException;

public class Film {
    private String titre;
    private int id;
    private int id_real;
    private static Connection connect=DBConnection.getConnection();


    public Film(String titre, Personne real) throws SQLException {
        this.titre = titre;
        this.id_real = real.getId();
        this.id = -1;
    }

    private Film(String titre, int id) {
        this.titre = titre;
        this.id = id;
    }

    public static Film findById(int id) throws SQLException {
        Film filmReturn = null;
        Connection connectid;
        connectid = DBConnection.getConnection();
        PreparedStatement prep1 = connectid.prepareStatement("SELECT  titre, id_rea FROM film WHERE id = ?");
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();

        if (rs.next()) {
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_rea");
            filmReturn = new Film(titre, id);
            filmReturn.id_real = id_real;
        }
        return filmReturn;
    }

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(id_real);
    }

    public static void createTable() throws SQLException {
        String createString = "CREATE TABLE `Film` (\n" +
                "  `id` int(11) NOT NULL,\n" +
                "  `titre` varchar(40) NOT NULL,\n" +
                "  `id_rea` int(11) DEFAULT NULL\n" +
                ")";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        String drop = "DROP TABLE Film";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if (id_real == -1) {
            throw new RealisateurAbsentException("Il n'y a pas de réalisateur ");
        }
        if (id == -1) {
            saveNew();
        } else {
            updateF();
        }
    }

    private void updateF() throws SQLException {
        String SQLprep = "update Film set titre=?, id_rea=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, titre);
        prep.setInt(2, id_real);
        prep.setInt(3, id);
        prep.execute();
    }

    private void saveNew() throws SQLException {
        String insertString = "INSERT INTO Film(titre,id_rea) VALUES(?,?)";
        PreparedStatement prep1 = connect.prepareStatement(insertString);
        prep1.setString(1, titre);
        prep1.setInt(2, id_real);
        prep1.executeUpdate();
        id = Personne.getIdIncrement(prep1);
    }

    public static ArrayList<Film> findByRealisateur(Personne realisateur) throws SQLException, RealisateurAbsentException {
        int id_realisateurAChercher = realisateur.getId();
        ArrayList<Film> listFilmReturn = new ArrayList<>();
        if (id_realisateurAChercher != 1) {
            String queryString = "SELECT id,titre from FILM where id_rea=?";
            PreparedStatement prep = connect.prepareStatement(queryString);
            prep.setInt(1, id_realisateurAChercher);
            prep.execute();
            ResultSet rs = prep.getResultSet();
            while (rs.next()) {
                int id_film = rs.getInt("id");
                String titre = rs.getString("titre");
                Film filmCurrent = new Film(titre, id_film);
                listFilmReturn.add(filmCurrent);
            }
        } else {
            throw new RealisateurAbsentException("pas de realisateur");
        }
        return listFilmReturn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_real() {
        return id_real;
    }

    public void setId_real(int id_real) {
        this.id_real = id_real;
    }
}
