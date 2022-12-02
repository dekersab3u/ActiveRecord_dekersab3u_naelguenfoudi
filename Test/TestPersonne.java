import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPersonne {
    Personne nael;
    Personne louis;
    Personne leo;
    Personne valentin;
    Personne edouard;
    Personne damien;

    @BeforeEach
    public void initialisation() throws SQLException {
        nael = new Personne("nael", "Guenfoudi");
        nael.save();
        louis = new Personne("louis", "talfournier");
        louis.save();
        leo = new Personne("leo", "marque");
        leo.save();
        edouard = new Personne("edouard", "saulnier");
        edouard.save();
        valentin = new Personne("valentin", "toussaint");
        valentin.save();
        damien = new Personne("damien", "bluem");
        damien.save();
    }

    @AfterEach
    public void renitialiser() throws SQLException {
        Personne.deleteTable();
    }

    @Test
    public void test_constructeur() {
        Personne kamelMonPere = new Personne("kamel", "guenfoudi");
        assertEquals("kamel", kamelMonPere.getNom());
        assertEquals("guenfoudi", kamelMonPere.getNom());
        assertEquals(-1, kamelMonPere.getId());
    }

    @Test
    public void test_findById() throws SQLException {
        Personne pForId = Personne.findById(2);
        assertEquals("louis", pForId.getPrenom());
    }

    @Test
    public void test_findById_mauvaiseId() throws SQLException {
        Personne pForId = Personne.findById(10);
        assertEquals(null, pForId);
    }

    @Test
    public void test_findById_Id_1() throws SQLException {
        Personne pForId = Personne.findById(-1);
        assertEquals(null, pForId);
    }

    @Test
    public void test_findAll() throws SQLException {
        ArrayList<Personne> listPersonne = Personne.findAll();
        Personne personne2 = listPersonne.get(listPersonne.size());
        assertEquals("damien", personne2.getPrenom());
        assertEquals(6, listPersonne.size());
    }

    @Test
    public void test_findName() throws SQLException {
        ArrayList<Personne> listPersonneName = Personne.findByName("guenfoudi");
        Personne nael = listPersonneName.get(0);
        assertEquals("nael", nael.getPrenom());
        assertEquals(1, listPersonneName.size());
    }

    @Test
    public void test_delete() throws SQLException {
        louis.delete();
        ArrayList<Personne> listVideNormalement = Personne.findByName("talfournier");
        assertEquals(0, listVideNormalement.size());
        assertEquals(-1, louis.getId());
    }

    @Test
    public void test_savePasPresent() throws SQLException {
        Personne samyMonFrere = new Personne("samy", "guenfoudi");
        samyMonFrere.save();
        Personne samyNormalement = Personne.findById(samyMonFrere.getId());
        assertEquals(samyMonFrere.getPrenom(), samyNormalement.getPrenom());

    }

    @Test
    public void test_savePresent() throws SQLException {
        louis.setNom("renesson");
        ArrayList<Personne> listLouisRenesson = Personne.findByName("renesson");
        assertEquals(1, listLouisRenesson.size());
    }
}
