import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFilm {

    Personne testreal;
    Film testfilm1;
    Film testfilm2;

    @BeforeEach
    public void init() throws SQLException, throws RealisateurAbsentException {
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
    public void testsave() throws SQLException
    {
        testfilm2 = new Film("titfilm", )
        testfilm2.save();
    }
    
    @AfterEach
    public void enddelete() throws SQLException
    {
        Film.deleteTable();
        Personne.deleteTable();
    }





}
