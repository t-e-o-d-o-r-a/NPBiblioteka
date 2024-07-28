package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KnjigaTest {
	
	Knjiga k;

	@BeforeEach
	void setUp() throws Exception {
		k = new Knjiga();
	}

	@AfterEach
	void tearDown() throws Exception {
		k = null;
	}

	@Test
	void testVratiNazivTabele() {
		assertEquals("knjiga", k.vratiNazivTabele());
	}

	@Test
	void testVratiListu() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("knjiga.knjigaID")).andReturn(1);
        expect(rsMock.getString("knjiga.naslov")).andReturn("Naslov1");
        expect(rsMock.getInt("knjiga.brojPrimeraka")).andReturn(3);
        expect(rsMock.getInt("knjiga.godinaIzdanja")).andReturn(2020);
        expect(rsMock.getString("knjiga.format")).andReturn("TVRD_POVEZ");
        expect(rsMock.getInt("izdavac.izdavacID")).andReturn(1);
        expect(rsMock.getString("izdavac.naziv")).andReturn("Izdavac1");
        expect(rsMock.getInt("knjiga.autor")).andReturn(1);
        expect(rsMock.getString("autor.ime")).andReturn("Ime1");
        expect(rsMock.getString("autor.prezime")).andReturn("Prezime1");

        expect(rsMock.next()).andReturn(false);

        replay(rsMock);

        List<ApstraktniDomenskiObjekat> lista = k.vratiListu(rsMock);

        assertEquals(1, lista.size());
        Autor autor = new Autor(1, "Ime1", "Prezime1");
        Izdavac izdavac = new Izdavac(1, "Izdavac1");
        Knjiga ocekivanaKnjiga = new Knjiga(1, "Naslov1", 3, 2020, autor, izdavac, Format.TVRD_POVEZ);

        assertEquals(ocekivanaKnjiga, lista.get(0));

        verify(rsMock);
	}
	
	@Test
    void testVratiListuGreska() throws Exception {
        ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani izuzetak"));

        replay(rsMock);

        assertThrows(java.sql.SQLException.class, () -> k.vratiListu(rsMock));

        verify(rsMock);
    }

	@Test
	void testVratiKoloneZaUbacivanje() {
		assertEquals("naslov,brojPrimeraka,godinaIzdanja,izdavac,format,autor", k.vratiKoloneZaUbacivanje());
	}

	@Test
	void testVratiVrednostiZaUbacivanje() {
		Autor autor = new Autor(1, "Pera", "Peric");
	    Izdavac izdavac = new Izdavac(2, "Izdavac");
	    k.setNaslov("Naslov");
	    k.setBrojPrimeraka(3);
	    k.setGodinaIzdanja(2020);
	    k.setAutor(autor);
	    k.setIzdavac(izdavac);
	    k.setFormat(Format.TVRD_POVEZ);
	    
	    assertEquals("'Naslov',3,2020,2,'TVRD_POVEZ',1", k.vratiVrednostiZaUbacivanje());
	}

	@Test
	void testVratiPrimarniKljuc() {
		k.setKnjigaID(1);

        assertEquals("knjiga.knjigaID=1", k.vratiPrimarniKljuc());
	}

	@Test
	void testVratiObjekatIzRS() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("knjiga.knjigaID")).andReturn(1);
        expect(rsMock.getString("knjiga.naslov")).andReturn("Naslov1");
        expect(rsMock.getInt("knjiga.brojPrimeraka")).andReturn(3);
        expect(rsMock.getInt("knjiga.godinaIzdanja")).andReturn(2020);
        expect(rsMock.getString("knjiga.format")).andReturn("TVRD_POVEZ");
        expect(rsMock.getInt("izdavac.izdavacID")).andReturn(1);
        expect(rsMock.getString("izdavac.naziv")).andReturn("Izdavac1");
        expect(rsMock.getInt("knjiga.autor")).andReturn(1);
        expect(rsMock.getString("autor.ime")).andReturn("Ime1");
        expect(rsMock.getString("autor.prezime")).andReturn("Prezime1");

        replay(rsMock);

        ApstraktniDomenskiObjekat obj = k.vratiObjekatIzRS(rsMock);

        assertNotNull(obj);
        assertTrue(obj instanceof Knjiga);
        Knjiga rezultat = (Knjiga) obj;

        assertEquals(1, rezultat.getKnjigaID());
        assertEquals("Naslov1", rezultat.getNaslov());
        assertEquals(3, rezultat.getBrojPrimeraka());
        assertEquals(2020, rezultat.getGodinaIzdanja());
        assertEquals(Format.TVRD_POVEZ, rezultat.getFormat());
        assertEquals(new Autor(1, "Ime1", "Prezime1"), rezultat.getAutor());
        assertEquals(new Izdavac(1, "Izdavac1"), rezultat.getIzdavac());

        verify(rsMock);
	}
	
	@Test
    void testVratiObjekatIzRSGreska() throws Exception {
        ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani SQL izuzetak"));

        replay(rsMock);

        assertThrows(java.sql.SQLException.class, () -> k.vratiObjekatIzRS(rsMock));

        verify(rsMock);
    }

	@Test
	void testVratiVrednostiZaIzmenu() {
		Autor autor = new Autor(1, "Pera", "Peric");
	    Izdavac izdavac = new Izdavac(2, "Izdavac");
	    k.setNaslov("Naslov");
	    k.setBrojPrimeraka(3);
	    k.setGodinaIzdanja(2020);
	    k.setAutor(autor);
	    k.setIzdavac(izdavac);
	    k.setFormat(Format.TVRD_POVEZ);

	    assertEquals("naslov='Naslov', brojPrimeraka=3, godinaIzdanja=2020, izdavac=2, format='TVRD_POVEZ', autor=1", k.vratiVrednostiZaIzmenu());
	}

	@Test
	void testVratiUslovZaFiltriranje() {
		Knjiga knjigaZaFiltriranje = new Knjiga();
	    Autor autor = new Autor(1, "Pera", "Peric");
	    knjigaZaFiltriranje.setNaslov("Naslov");
	    knjigaZaFiltriranje.setAutor(autor);

	    assertEquals("(knjiga.naslov LIKE '%Naslov%' OR knjiga.autor=1)", k.vratiUslovZaFiltriranje(knjigaZaFiltriranje));
	}

	@Test
	void testPostaviId() {
		k.postaviId(10);
		
	    assertEquals(10, k.getKnjigaID());
	}
	
	@Test
	void testJoin() {
	    assertEquals(" JOIN autor ON (knjiga.autor = autor.autorID) JOIN izdavac ON (knjiga.izdavac = izdavac.izdavacID) ", k.join());
	}

	@Test
	void testKnjiga() {
		assertNotNull(k);
        assertEquals(0, k.getKnjigaID());
        assertNull(k.getNaslov());
        assertEquals(0, k.getBrojPrimeraka());
        assertEquals(0, k.getGodinaIzdanja());
        assertNull(k.getAutor());
        assertNull(k.getIzdavac());
        assertNull(k.getFormat());
	}

	@Test
	void testKnjigaIntStringIntIntAutorIzdavacFormat() {
		Autor autor = new Autor(1, "Pera", "Peric");
        Izdavac izdavac = new Izdavac(1, "Izdavac1");
        k = new Knjiga(1, "Naslov1", 3, 2020, autor, izdavac, Format.TVRD_POVEZ);

        assertNotNull(k);
        assertEquals(1, k.getKnjigaID());
        assertEquals("Naslov1", k.getNaslov());
        assertEquals(3, k.getBrojPrimeraka());
        assertEquals(2020, k.getGodinaIzdanja());
        assertEquals(autor, k.getAutor());
        assertEquals(izdavac, k.getIzdavac());
        assertEquals(Format.TVRD_POVEZ, k.getFormat());
	}

	@Test
	void testSetFormat() {
		k.setFormat(Format.TVRD_POVEZ);

        assertEquals(Format.TVRD_POVEZ, k.getFormat());
	}

	@Test
	void testSetKnjigaID() {
		k.setKnjigaID(10);

        assertEquals(10, k.getKnjigaID());
	}
	
	@Test
    void testSetKnjigaIDNegativan() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> k.setKnjigaID(-1));
    }

	@Test
	void testSetNaslov() {
		k.setNaslov("Naslov1");

        assertEquals("Naslov1", k.getNaslov());
	}
	
	@Test
    void testSetNaslovNull() {
        assertThrows(java.lang.NullPointerException.class, () -> k.setNaslov(null));
    }

	@Test
	void testSetBrojPrimeraka() {
		k.setBrojPrimeraka(10);

        assertEquals(10, k.getBrojPrimeraka());
	}
	
	@Test
    void testSetBrojPrimerakaNegativan() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> k.setBrojPrimeraka(-1));
    }

	@Test
	void testSetGodinaIzdanja() {
		int trenutnaGodina = LocalDate.now().getYear();
        k.setGodinaIzdanja(trenutnaGodina);

        assertEquals(trenutnaGodina, k.getGodinaIzdanja());
	}
	
    @Test
    void testSetGodinaIzdanjaNegativna() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> k.setGodinaIzdanja(-1));
    }

    @Test
    void testSetGodinaIzdanjaVecaOdTrenutne() {
        int sledecaGodina = LocalDate.now().getYear() + 1;
        assertThrows(java.lang.IllegalArgumentException.class, () -> k.setGodinaIzdanja(sledecaGodina));
    }

	@Test
	void testSetAutor() {
		Autor autor = new Autor(1, "Pera", "Peric");
        k.setAutor(autor);

        assertEquals(autor, k.getAutor());
	}

	@Test
	void testSetIzdavac() {
		Izdavac izdavac = new Izdavac(1, "Izdavac1");
        k.setIzdavac(izdavac);

        assertEquals(izdavac, k.getIzdavac());
	}

	@Test
	void testEqualsObject() {
		Knjiga k1 = k;
		
		assertTrue(k.equals(k1));
	}
	
	@Test
	void testEqualsObjectNull() {
	    assertFalse(k.equals(null));
	}

	@Test
	void testEqualsObjectDrugaKlasa() {
	    assertFalse(k.equals(new Autor()));
	}
	
	@ParameterizedTest
	@CsvSource({
	    "1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, 1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, true",
	    "1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, 1, Naslov1, 3, 2021, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, false",
	    "1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, 1, Naslov2, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, false",
	    "1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, 1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Mika, Peric, false",
	    "1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, 1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac2, 1, Pera, Peric, false",
	    "1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, 1, Naslov1, 3, 2020, MEK_POVEZ, 1, Izdavac2, 1, Pera, Peric, false",
	    "1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, 1, Naslov2, 3, 2021, MEK_POVEZ, 1, Izdavac2, 1, Mika, Peric, false",
	})
	void testEqualsObjectSveOk(
	        int id1, String naslov1, int brojPrimeraka1, int godinaIzdanja1, String format1, int izdavacID1, String izdavacNaziv1, int autorID1, String autorIme1, String autorPrezime1,
	        int id2, String naslov2, int brojPrimeraka2, int godinaIzdanja2, String format2, int izdavacID2, String izdavacNaziv2, int autorID2, String autorIme2, String autorPrezime2,
	        boolean eq) {
	    
	    Autor autor1 = new Autor(autorID1, autorIme1, autorPrezime1);
	    Izdavac izdavac1 = new Izdavac(izdavacID1, izdavacNaziv1);
	    Knjiga knjiga1 = new Knjiga(id1, naslov1, brojPrimeraka1, godinaIzdanja1, autor1, izdavac1, Format.valueOf(format1));
	    
	    Autor autor2 = new Autor(autorID2, autorIme2, autorPrezime2);
	    Izdavac izdavac2 = new Izdavac(izdavacID2, izdavacNaziv2);
	    Knjiga knjiga2 = new Knjiga(id2, naslov2, brojPrimeraka2, godinaIzdanja2, autor2, izdavac2, Format.valueOf(format2));

	    assertEquals(eq, knjiga1.equals(knjiga2));
	}

	@Test
	void testToString() {
		 Autor autor = new Autor(1, "Pera", "Peric");
	     Izdavac izdavac = new Izdavac(1, "Izdavac1");
	     Knjiga knjiga = new Knjiga(1, "Naslov1", 3, 2020, autor, izdavac, Format.TVRD_POVEZ);

	     String s = knjiga.toString();

	     assertTrue(s.contains("Naslov"));
	     assertTrue(s.contains("2020"));
	     assertTrue(s.contains("Pera"));
	     assertTrue(s.contains("Peric"));
	     assertTrue(s.contains("Izdavac1"));
	}

}
