import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import Exception.RealisateurAbsentException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFilm {

    Personne testreal;
    Film testfilm1;
    Film testfilm2;

    @BeforeEach
    public void init() throws SQLException, RealisateurAbsentException {
        Personne.createTable();
        Film.createTable();
        testreal = new Personne("per", "sonne");
        testreal.save();
        testfilm1 = new Film("titfilm", testreal);
        testfilm1.save();
    }

    @Test
    public void testfind() throws SQLException
    {
        testfilm2 = Film.findById(2);
        assertEquals("Alien", testfilm2.getTitre());
    }

    @Test
    public void testsave() throws SQLException, RealisateurAbsentException {
        testfilm2 = new Film("titfilm", testreal);
        testfilm2.save();
        Film resultfilm = Film.findById(2);
        assertEquals(testfilm2, resultfilm);
    }

    @Test
    public void testupdate() throws SQLException, RealisateurAbsentException {
        testfilm1 = new Film("titfilm", Personne.findById(1));
        testfilm1.save();
        Film resultfilm = Film.findById(2);
        assertEquals(testfilm1, resultfilm);
    }


    @AfterEach
    public void enddelete() throws SQLException
    {
        Film.deleteTable();
        Personne.deleteTable();
    }





}
