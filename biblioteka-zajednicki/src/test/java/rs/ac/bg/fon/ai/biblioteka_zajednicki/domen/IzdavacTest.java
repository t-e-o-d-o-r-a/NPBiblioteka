package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IzdavacTest {
	
	Izdavac i;

	@BeforeEach
	void setUp() throws Exception {
		i = new Izdavac();
	}

	@AfterEach
	void tearDown() throws Exception {
		i = null;
	}

	@Test
	void testVratiNazivTabele() {
		assertEquals("izdavac", i.vratiNazivTabele());
	}

	@Test
	void testVratiListu() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("izdavac.izdavacID")).andReturn(1);
        expect(rsMock.getString("izdavac.naziv")).andReturn("Naziv1");

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("izdavac.izdavacID")).andReturn(2);
        expect(rsMock.getString("izdavac.naziv")).andReturn("Naziv2");

        expect(rsMock.next()).andReturn(false);

        replay(rsMock);

        List<ApstraktniDomenskiObjekat> lista = i.vratiListu(rsMock);

        assertEquals(2, lista.size());
        assertEquals(new Izdavac(1, "Naziv1"), lista.get(0));
        assertEquals(new Izdavac(2, "Naziv2"), lista.get(1));

        verify(rsMock);
	}
	
	@Test
	void testVratiListuGreska() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani izuzetak"));

        replay(rsMock);

        assertThrows(SQLException.class, () -> i.vratiListu(rsMock));

        verify(rsMock);
	}

	@Test
	void testVratiPrimarniKljuc() {
		i.setIzdavacID(1);

        assertEquals("izdavac.izdavacID=1", i.vratiPrimarniKljuc());
	}

	@Test
	void testVratiObjekatIzRS() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);
		
		expect(rsMock.next()).andReturn(true);
		expect(rsMock.getInt("izdavac.izdavacID")).andReturn(1);
        expect(rsMock.getString("izdavac.naziv")).andReturn("Naziv1");
        
        replay(rsMock);
        
        ApstraktniDomenskiObjekat obj = i.vratiObjekatIzRS(rsMock);
        assertNotNull(obj);
        assertTrue(obj instanceof Izdavac);
        Izdavac rezultat = (Izdavac) obj;
        assertEquals(1, rezultat.getIzdavacID());
        assertEquals("Naziv1", rezultat.getNaziv());
        
        verify(rsMock);
	}
	
	@Test
	void testVratiObjekatIzRSGreska() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani SQL izuzetak"));

        replay(rsMock);

        assertThrows(SQLException.class, () -> i.vratiObjekatIzRS(rsMock));

        verify(rsMock);
	}

	@Test
	void testIzdavac() {
		assertNotNull(i);
        assertEquals(0, i.getIzdavacID());
        assertNull(i.getNaziv());
	}

	@Test
	void testIzdavacIntString() {
		 i = new Izdavac(1, "Naziv");

        assertNotNull(i);
        assertEquals(1, i.getIzdavacID());
        assertEquals("Naziv", i.getNaziv());
	}

	@Test
	void testSetNaziv() {
		i.setNaziv("Naziv");

        assertEquals("Naziv", i.getNaziv());
	}
	
	@Test
    void testSetNazivNull() {
        assertThrows(NullPointerException.class, () -> i.setNaziv(null));
    }

    @Test
    void testSetNazivPrazno() {
        assertThrows(IllegalArgumentException.class, () -> i.setNaziv(""));
    }

	@Test
	void testSetIzdavacID() {
		i.setIzdavacID(10);

        assertEquals(10, i.getIzdavacID());
	}
	
	@Test
    void testSetIzdavacIDNegativan() {
        assertThrows(IllegalArgumentException.class, () -> i.setIzdavacID(-1));
    }

	@Test
	void testEqualsObject() {
		Izdavac b = i;

        assertTrue(i.equals(b));
	}
	
	@Test
    void testEqualsObjectNull() {
        assertFalse(i.equals(null));
    }

    @Test
    void testEqualsObjectDrugaKlasa() {
        assertFalse(i.equals(new Autor()));
    }

    @ParameterizedTest
    @CsvSource({
        "1, Naziv1, 1, Naziv1, true",
        "1, Naziv1, 2, Naziv1, false",
        "1, Naziv1, 1, Naziv2, false",
        "1, Naziv1, 2, Naziv2, false"
    })
    void testEqualsObjectSveOk(int id1, String naziv1, int id2, String naziv2, boolean eq) {
        i.setIzdavacID(id1);
        i.setNaziv(naziv1);

        Izdavac b = new Izdavac(id2, naziv2);

        assertEquals(eq, i.equals(b));
    }

	@Test
	void testToString() {
		i.setNaziv("Naziv");

        String s = i.toString();

        assertTrue(s.contains("Naziv"));
	}

}
