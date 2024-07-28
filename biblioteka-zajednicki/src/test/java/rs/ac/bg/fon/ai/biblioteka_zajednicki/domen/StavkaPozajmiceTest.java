package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StavkaPozajmiceTest {

	StavkaPozajmice sp;
    Pozajmica p;
    Knjiga k;
    Date d;
	
	@BeforeEach
	void setUp() throws Exception {
        k = new Knjiga(1, "Naslov", 1, 2021, new Autor(1, "ImeAutora", "PrezimeAutora"), new Izdavac(1, "NazivIzdavaca"), Format.TVRD_POVEZ);
        d = new Date();
        p = new Pozajmica(1, d, new Clan(11, "Pera", "Peric", "123123132"), new Bibliotekar(2, "Mika", "Mikic", "mika", "mika123"), new ArrayList<>(), "Napomena");
	
        sp = new StavkaPozajmice();
	}

	@AfterEach
	void tearDown() throws Exception {
		sp = null;
        p = null;
        k = null;
        d = null;
	}

	@Test
	void testVratiNazivTabele() {
		assertEquals("stavkaPozajmice", sp.vratiNazivTabele());
	}

	@Test
	void testVratiListu() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("stavkaPozajmice.stavkaID")).andReturn(1);
        expect(rsMock.getDate("stavkaPozajmice.datumDo")).andReturn(new java.sql.Date(d.getTime()));
        expect(rsMock.getInt("stavkaPozajmice.pozajmica")).andReturn(1);

        expect(rsMock.getInt("knjiga.knjigaID")).andReturn(1);
        expect(rsMock.getString("knjiga.naslov")).andReturn("Naslov");
        expect(rsMock.getInt("knjiga.brojPrimeraka")).andReturn(1);
        expect(rsMock.getInt("knjiga.godinaIzdanja")).andReturn(2021);
        expect(rsMock.getString("knjiga.format")).andReturn("TVRD_POVEZ");

        expect(rsMock.getInt("izdavac.izdavacID")).andReturn(1);
        expect(rsMock.getString("izdavac.naziv")).andReturn("Izdavac");

        expect(rsMock.getInt("knjiga.autor")).andReturn(1);
        expect(rsMock.getString("autor.ime")).andReturn("Ime");
        expect(rsMock.getString("autor.prezime")).andReturn("Prezime");

        expect(rsMock.next()).andReturn(false);

        replay(rsMock);

        List<ApstraktniDomenskiObjekat> lista = sp.vratiListu(rsMock);

        assertEquals(1, lista.size());
        StavkaPozajmice dobijenaStavka = (StavkaPozajmice) lista.get(0);
        assertEquals(1, dobijenaStavka.getStavkaID());
        assertEquals(d, dobijenaStavka.getDatumDo());
        assertEquals(1, dobijenaStavka.getPozajmica().getPozajmicaID());
        assertEquals(1, dobijenaStavka.getKnjiga().getKnjigaID());

        verify(rsMock);
	}
	
	@Test
    void testVratiListuGreska() throws Exception {
        ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani izuzetak"));

        replay(rsMock);

        assertThrows(SQLException.class, () -> sp.vratiListu(rsMock));

        verify(rsMock);
    }

	@Test
	void testVratiKoloneZaUbacivanje() {
		assertEquals("pozajmica,stavkaID,datumDo,knjiga", sp.vratiKoloneZaUbacivanje());
	}

	@Test
	void testVratiVrednostiZaUbacivanje() {
		sp.setDatumDo(d);
		sp.setKnjiga(k);
		sp.setPozajmica(p);
		sp.setStavkaID(1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatiranDatum = sdf.format(d);
        assertEquals(p.getPozajmicaID() + ",1,'" + formatiranDatum + "',1", sp.vratiVrednostiZaUbacivanje());
	}

	@Test
	void testVratiPrimarniKljuc() {
		sp.setPozajmica(p);
		sp.setStavkaID(1);
		assertEquals("stavkaPozajmice.pozajmica=1 AND stavkaPozajmice.stavkaID=1", sp.vratiPrimarniKljuc());
	}

	@Test
	void testVratiVrednostiZaIzmenu() {
		sp.setDatumDo(d);
		sp.setKnjiga(k);
		sp.setPozajmica(p);
		sp.setStavkaID(1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatiranDatum = sdf.format(d);
        assertEquals("datumDo='" + formatiranDatum + "'", sp.vratiVrednostiZaIzmenu());
	}

	@Test
	void testVratiUslovZaFiltriranje() {
		sp.setPozajmica(p);
		assertEquals(" stavkaPozajmice.pozajmica=1", sp.vratiUslovZaFiltriranje(sp));
	}

	@Test
	void testJoin() {
		assertEquals(" JOIN knjiga ON (stavkaPozajmice.knjiga = knjiga.knjigaID) JOIN pozajmica ON (stavkaPozajmice.pozajmica = pozajmica.pozajmicaID) JOIN autor ON (knjiga.autor = autor.autorID) JOIN izdavac ON (knjiga.izdavac = izdavac.izdavacID) ", sp.join());
	}

	@Test
	void testPostaviId() {
		sp.postaviId(10);
		
        assertEquals(10, sp.getStavkaID());
	}

	@Test
	void testStavkaPozajmice() {
        assertNotNull(sp);
        assertNull(sp.getPozajmica());
        assertEquals(0, sp.getStavkaID());
        assertNull(sp.getDatumDo());
        assertNull(sp.getKnjiga());
	}

	@Test
	void testStavkaPozajmicePozajmicaIntDateKnjiga() {
		sp = new StavkaPozajmice(p, 3, d, k);
		
		assertNotNull(sp);
        assertEquals(p, sp.getPozajmica());
        assertEquals(3, sp.getStavkaID());
        assertEquals(d, sp.getDatumDo());
        assertEquals(k, sp.getKnjiga());
	}

	@Test
	void testSetKnjiga() {
		sp.setKnjiga(k);
		
        assertEquals(k, sp.getKnjiga());
	}

	@Test
	void testSetPozajmica() {
		sp.setPozajmica(p);
		
        assertEquals(p, sp.getPozajmica());
	}

	@Test
	void testSetStavkaID() {
		sp.setStavkaID(10);
		
        assertEquals(10, sp.getStavkaID());
	}

	@Test
	void testSetDatumDo() {
		sp.setDatumDo(d);
		
        assertEquals(d, sp.getDatumDo());
	}

	@Test
	void testEqualsObject() {
		StavkaPozajmice sp1 = sp;
		
        assertTrue(sp.equals(sp1));
	}
	
	@Test
    void testEqualsObjectNull() {
        assertFalse(sp.equals(null));
    }

    @Test
    void testEqualsObjectDrugaKlasa() {
        assertFalse(sp.equals(new Autor()));
    }
    
    @ParameterizedTest
    @CsvSource({
    	"1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, 1, Naslov1, 3, 2020, TVRD_POVEZ, 1, Izdavac1, 1, Pera, Peric, true",
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

        StavkaPozajmice sp1 = new StavkaPozajmice(null, 1, d, knjiga1);
        StavkaPozajmice sp2 = new StavkaPozajmice(null, 2, d, knjiga2);

        assertEquals(eq, sp1.equals(sp2));
    }

	@Test
	void testToString() {
		sp.setDatumDo(d);
		sp.setKnjiga(k);
		sp.setPozajmica(p);
		sp.setStavkaID(1);
		
		String s = sp.toString();
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String formatiranDatum = sdf.format(d);
	    
	    assertTrue(s.contains(formatiranDatum));
	    assertTrue(s.contains(p.toString()));
	    assertTrue(s.contains(k.getNaslov()));
	    assertTrue(s.contains(k.getAutor().getIme()));
	    assertTrue(s.contains(k.getAutor().getPrezime()));
	    assertTrue(s.contains(k.getIzdavac().getNaziv()));
	    assertTrue(s.contains("1")); 
	}

}
