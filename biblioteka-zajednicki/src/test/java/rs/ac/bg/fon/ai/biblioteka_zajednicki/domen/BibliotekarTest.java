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

class BibliotekarTest {
	
	private Bibliotekar b;

	@BeforeEach
	void setUp() throws Exception {
		b = new Bibliotekar();
	}

	@AfterEach
	void tearDown() throws Exception {
		b = null;
	}

	@Test
	void testVratiNazivTabele() {
		assertEquals("bibliotekar", b.vratiNazivTabele());
	}

	@Test
	void testVratiListu() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("bibliotekar.bibliotekarID")).andReturn(1);
        expect(rsMock.getString("bibliotekar.ime")).andReturn("Ime");
        expect(rsMock.getString("bibliotekar.prezime")).andReturn("Prezime");
        expect(rsMock.getString("bibliotekar.korisnickoIme")).andReturn("korisnik");
        expect(rsMock.getString("bibliotekar.lozinka")).andReturn("lozinka");

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("bibliotekar.bibliotekarID")).andReturn(2);
        expect(rsMock.getString("bibliotekar.ime")).andReturn("Ime2");
        expect(rsMock.getString("bibliotekar.prezime")).andReturn("Prezime2");
        expect(rsMock.getString("bibliotekar.korisnickoIme")).andReturn("korisnik2");
        expect(rsMock.getString("bibliotekar.lozinka")).andReturn("lozinka2");

        expect(rsMock.next()).andReturn(false);

        replay(rsMock);

        List<ApstraktniDomenskiObjekat> lista = b.vratiListu(rsMock);

        assertEquals(2, lista.size());
        assertEquals(new Bibliotekar(1, "Ime", "Prezime", "korisnik", "lozinka"), lista.get(0));
        assertEquals(new Bibliotekar(2, "Ime2", "Prezime2", "korisnik2", "lozinka2"), lista.get(1));

        verify(rsMock);
	}
	
	@Test
    void testVratiListuGreska() throws Exception {
        ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani SQL izuzetak"));

        replay(rsMock);

        assertThrows(SQLException.class, () -> b.vratiListu(rsMock));

        verify(rsMock);
    }

	@Test
	void testVratiKoloneZaUbacivanje() {
		assertEquals("ime,prezime,korisnickoIme,lozinka", b.vratiKoloneZaUbacivanje());
	}

	@Test
	void testVratiVrednostiZaUbacivanje() {
		b.setIme("Ime");
        b.setPrezime("Prezime");
        b.setKorisnickoIme("korisnik");
        b.setLozinka("lozinka");

        assertEquals("'Ime','Prezime','korisnik','lozinka'", b.vratiVrednostiZaUbacivanje());
	}

	@Test
	void testVratiPrimarniKljuc() {
		b.setBibliotekarID(1);

        assertEquals("bibliotekar.bibliotekarID=1", b.vratiPrimarniKljuc());
	}

	@Test
	void testVratiObjekatIzRS() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("bibliotekar.bibliotekarID")).andReturn(1);
        expect(rsMock.getString("bibliotekar.ime")).andReturn("Ime");
        expect(rsMock.getString("bibliotekar.prezime")).andReturn("Prezime");
        expect(rsMock.getString("bibliotekar.korisnickoIme")).andReturn("korisnik");
        expect(rsMock.getString("bibliotekar.lozinka")).andReturn("lozinka");

        replay(rsMock);

        ApstraktniDomenskiObjekat obj = b.vratiObjekatIzRS(rsMock);
        assertNotNull(obj);
        assertTrue(obj instanceof Bibliotekar);
        Bibliotekar rezultat = (Bibliotekar) obj;
        assertEquals(1, rezultat.getBibliotekarID());
        assertEquals("Ime", rezultat.getIme());
        assertEquals("Prezime", rezultat.getPrezime());
        assertEquals("korisnik", rezultat.getKorisnickoIme());
        assertEquals("lozinka", rezultat.getLozinka());

        verify(rsMock);
	}
	
	@Test
    void testVratiObjekatIzRSGreska() throws Exception {
        ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani SQL izuzetak"));

        replay(rsMock);

        assertThrows(SQLException.class, () -> b.vratiObjekatIzRS(rsMock));

        verify(rsMock);
    }

	@Test
	void testVratiVrednostiZaIzmenu() {
		b.setIme("Ime");
        b.setPrezime("Prezime");
        b.setKorisnickoIme("korisnik");
        b.setLozinka("lozinka");

        assertEquals("ime='Ime', prezime='Prezime', korisnickoIme='korisnik', lozinka='lozinka'", b.vratiVrednostiZaIzmenu());
	}

	@Test
	void testBibliotekar() {
		assertNotNull(b);
        assertEquals(0, b.getBibliotekarID());
        assertNull(b.getIme());
        assertNull(b.getPrezime());
        assertNull(b.getKorisnickoIme());
        assertNull(b.getLozinka());
	}

	@Test
	void testBibliotekarIntStringStringStringString() {
		b = new Bibliotekar(1, "Ime", "Prezime", "korisnik", "lozinka");

        assertNotNull(b);
        assertEquals(1, b.getBibliotekarID());
        assertEquals("Ime", b.getIme());
        assertEquals("Prezime", b.getPrezime());
        assertEquals("korisnik", b.getKorisnickoIme());
        assertEquals("lozinka", b.getLozinka());
	}

	@Test
	void testSetBibliotekarID() {
		b.setBibliotekarID(10);

        assertEquals(10, b.getBibliotekarID());
	}
	
	@Test
    void testSetBibliotekarIDNegativan() {
        assertThrows(IllegalArgumentException.class, () -> b.setBibliotekarID(-1));
    }

	@Test
	void testSetIme() {
		b.setIme("Ime");

        assertEquals("Ime", b.getIme());
	}

	@Test
	void testSetPrezime() {
		b.setPrezime("Prezime");

        assertEquals("Prezime", b.getPrezime());
	}

	@Test
	void testSetKorisnickoIme() {
		b.setKorisnickoIme("korisnik");

        assertEquals("korisnik", b.getKorisnickoIme());
	}
	
	@Test
    void testSetKorisnickoImeNull() {
        assertThrows(NullPointerException.class, () -> b.setKorisnickoIme(null));
    }

    @Test
    void testSetKorisnickoImePrazno() {
        assertThrows(IllegalArgumentException.class, () -> b.setKorisnickoIme(""));
    }

	@Test
	void testSetLozinka() {
		b.setLozinka("lozinka");

        assertEquals("lozinka", b.getLozinka());
	}

	@Test
	void testToString() {
		b.setIme("Pera");
        b.setPrezime("Peric");
        
        String s = b.toString();
        
        assertTrue(s.contains("Pera"));
        assertTrue(s.contains("Peric"));
	}

	@Test
	void testEqualsObject() {
		Bibliotekar b2 = b;

        assertTrue(b.equals(b2));
	}
	
	@Test
    void testEqualsObjectNull() {
        assertFalse(b.equals(null));
    }

    @Test
    void testEqualsObjectDrugaKlasa() {
        assertFalse(b.equals(new Izdavac()));
    }
    
    @ParameterizedTest
    @CsvSource({
        "korisnik, lozinka, korisnik, lozinka, true",
        "korisnik1, lozinka, korisnik, lozinka, false",
        "korisnik, lozinka1, korisnik, lozinka, false",
        "korisnik1, lozinka1, korisnik, lozinka, false"
    })
    void testEqualsObjectParametrized(String korisnickoIme1, String lozinka1, String korisnickoIme2, String lozinka2, boolean expected) {
        b.setKorisnickoIme(korisnickoIme1);
        b.setLozinka(lozinka1);
        
        Bibliotekar b2 = new Bibliotekar();
        b2.setKorisnickoIme(korisnickoIme2);
        b2.setLozinka(lozinka2);

        assertEquals(expected, b.equals(b2));
    }

}
