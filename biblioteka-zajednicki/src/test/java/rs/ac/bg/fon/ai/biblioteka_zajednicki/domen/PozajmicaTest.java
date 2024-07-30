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

class PozajmicaTest {
	
	Pozajmica p;
	Clan c;
	Bibliotekar b;
	Date d;
	StavkaPozajmice s1;
	StavkaPozajmice s2;

	@BeforeEach
	void setUp() throws Exception {
		p = new Pozajmica();
		c = new Clan(1, "Pera", "Peric", "1234567890");
		b = new Bibliotekar(2, "Mika", "Mikic", "mika", "mika123");
		d = new Date();
		
		Autor autor = new Autor(1, "Ime1", "Prezime1");
        Izdavac izdavac = new Izdavac(1, "Izdavac1");
        Knjiga k1 = new Knjiga(1, "Naslov1", 3, 2020, autor, izdavac, Format.TVRD_POVEZ);
        s1 = new StavkaPozajmice(p, 1, d, k1);
        
        Autor autor2 = new Autor(2, "Ime2", "Prezime2");
        Izdavac izdavac2 = new Izdavac(2, "Izdavac2");
        Knjiga k2 = new Knjiga(2, "Naslov2", 3, 2022, autor2, izdavac2, Format.MEK_POVEZ);
        s1 = new StavkaPozajmice(p, 2, d, k2);       
	}

	@AfterEach
	void tearDown() throws Exception {
		p = null;
		c = null;
		b = null;
		d = null;
		s1 = null;
		s2 = null;
	}

	@Test
	void testVratiNazivTabele() {
		assertEquals("pozajmica", p.vratiNazivTabele());
	}

	@Test
	void testVratiListu() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("clan.clanID")).andReturn(1);
        expect(rsMock.getString("clan.ime")).andReturn("ImeClana");
        expect(rsMock.getString("clan.prezime")).andReturn("PrezimeClana");
        expect(rsMock.getString("clan.brojTelefona")).andReturn("12345");
        expect(rsMock.getInt("bibliotekar.bibliotekarID")).andReturn(1);
        expect(rsMock.getString("bibliotekar.ime")).andReturn("ImeBib");
        expect(rsMock.getString("bibliotekar.prezime")).andReturn("PrezimeBib");
        expect(rsMock.getString("bibliotekar.korisnickoIme")).andReturn("username");
        expect(rsMock.getInt("pozajmica.pozajmicaID")).andReturn(1);
        expect(rsMock.getDate("pozajmica.datum")).andReturn(new java.sql.Date(new Date().getTime()));
        expect(rsMock.getString("pozajmica.napomena")).andReturn("Napomena");
        expect(rsMock.next()).andReturn(false);

        replay(rsMock);

        List<ApstraktniDomenskiObjekat> lista = p.vratiListu(rsMock);

        assertEquals(1, lista.size());
        Clan clan = new Clan(1, "ImeClana", "PrezimeClana", "12345");
        Bibliotekar bibliotekar = new Bibliotekar(1, "ImeBib", "PrezimeBib", "username", null);
        Pozajmica ocekivanaPozajmica = new Pozajmica(1, new Date(), clan, bibliotekar, new ArrayList<>(), "Napomena");

        Pozajmica dobijenaPozajmica = (Pozajmica) lista.get(0);
        assertEquals(ocekivanaPozajmica.getPozajmicaID(), dobijenaPozajmica.getPozajmicaID());
        assertEquals(ocekivanaPozajmica.getClan(), dobijenaPozajmica.getClan());
        assertEquals(ocekivanaPozajmica.getBibliotekar(), dobijenaPozajmica.getBibliotekar());
        assertEquals(ocekivanaPozajmica.getNapomena(), dobijenaPozajmica.getNapomena());
        assertEquals(ocekivanaPozajmica.getStavke(), dobijenaPozajmica.getStavke());
        assertEquals(ocekivanaPozajmica.getDatum().getTime(), dobijenaPozajmica.getDatum().getTime());

        verify(rsMock);
	}
	
	@Test
    void testVratiListuGreska() throws Exception {
        ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani izuzetak"));

        replay(rsMock);

        assertThrows(SQLException.class, () -> p.vratiListu(rsMock));

        verify(rsMock);
    }

	@Test
	void testVratiKoloneZaUbacivanje() {
		assertEquals("datum,clan,bibliotekar,napomena", p.vratiKoloneZaUbacivanje());
	}

	@Test
	void testVratiVrednostiZaUbacivanje() {
		p.setBibliotekar(b);
		p.setClan(c);
		p.setDatum(d);
		p.setNapomena("Napomena");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatiranDatum = sdf.format(p.getDatum());
        
        assertEquals("'" + formatiranDatum + "',1,2,'Napomena'", p.vratiVrednostiZaUbacivanje());
	}

	@Test
	void testVratiPrimarniKljuc() {
		p.setPozajmicaID(1);

        assertEquals("pozajmica.pozajmicaID=1", p.vratiPrimarniKljuc());
	}

	@Test
	void testVratiObjekatIzRS() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("clan.clanID")).andReturn(1);
        expect(rsMock.getString("clan.ime")).andReturn("ImeClana");
        expect(rsMock.getString("clan.prezime")).andReturn("PrezimeClana");
        expect(rsMock.getString("clan.brojTelefona")).andReturn("12345");
        expect(rsMock.getInt("bibliotekar.bibliotekarID")).andReturn(1);
        expect(rsMock.getString("bibliotekar.ime")).andReturn("ImeBib");
        expect(rsMock.getString("bibliotekar.prezime")).andReturn("PrezimeBib");
        expect(rsMock.getString("bibliotekar.korisnickoIme")).andReturn("username");
        expect(rsMock.getInt("pozajmica.pozajmicaID")).andReturn(1);
        expect(rsMock.getDate("pozajmica.datum")).andReturn(new java.sql.Date(new Date().getTime()));
        expect(rsMock.getString("pozajmica.napomena")).andReturn("Napomena");

        replay(rsMock);

        ApstraktniDomenskiObjekat obj = p.vratiObjekatIzRS(rsMock);

        assertNotNull(obj);
        assertTrue(obj instanceof Pozajmica);
        Pozajmica rezultat = (Pozajmica) obj;

        assertEquals(1, rezultat.getPozajmicaID());
        assertEquals("ImeClana", rezultat.getClan().getIme());
        assertEquals("PrezimeClana", rezultat.getClan().getPrezime());
        assertEquals("12345", rezultat.getClan().getBrojTelefona());
        assertEquals("ImeBib", rezultat.getBibliotekar().getIme());
        assertEquals("PrezimeBib", rezultat.getBibliotekar().getPrezime());
        assertEquals("username", rezultat.getBibliotekar().getKorisnickoIme());
        assertEquals("Napomena", rezultat.getNapomena());

        verify(rsMock);
	}
	
	@Test
    void testVratiObjekatIzRSGreska() throws Exception {
        ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani SQL izuzetak"));

        replay(rsMock);

        assertThrows(SQLException.class, () -> p.vratiObjekatIzRS(rsMock));

        verify(rsMock);
    }

	@Test
	void testVratiVrednostiZaIzmenu() {
		p.setBibliotekar(b);
		p.setClan(c);
		p.setDatum(d);
		p.setNapomena("Napomena");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatiranDatum = sdf.format(p.getDatum());
        
        assertEquals("datum='" + formatiranDatum + "', clan=1, bibliotekar=2, napomena='Napomena'", p.vratiVrednostiZaIzmenu());
	}

	@Test
	void testVratiUslovZaFiltriranje() {
		p.setClan(c);
		
		assertEquals("pozajmica.clan=1", p.vratiUslovZaFiltriranje(p));
	}

	@Test
	void testJoin() {
		assertEquals(" JOIN clan ON (clan.clanID = pozajmica.clan) JOIN bibliotekar ON (bibliotekar.bibliotekarID = pozajmica.bibliotekar) ", p.join());
	}

	@Test
	void testPostaviId() {
		p.postaviId(10);

        assertEquals(10, p.getPozajmicaID());
	}

	@Test
	void testPozajmica() {
		assertNotNull(p);
        assertEquals(0, p.getPozajmicaID());
        assertNull(p.getDatum());
        assertNull(p.getClan());
        assertNull(p.getBibliotekar());
        assertNull(p.getStavke());
        assertNull(p.getNapomena());
	}

	@Test
	void testPozajmicaIntDateClanBibliotekarListOfStavkaPozajmiceString() {
		List<StavkaPozajmice> stavke = new ArrayList<>();
		stavke.add(s1);
		stavke.add(s2);
		
		p = new Pozajmica(1, d, c, b, stavke, "Napomena");
		
		assertNotNull(p);
		assertEquals(1, p.getPozajmicaID());
        assertEquals(d, p.getDatum());
        assertEquals(c, p.getClan());
        assertEquals(b, p.getBibliotekar());
        assertEquals(2, p.getStavke().size());
        assertEquals(s1, p.getStavke().get(0));
        assertEquals(s2, p.getStavke().get(1));
        assertEquals("Napomena", p.getNapomena());
	}

	@Test
	void testSetStavke() {
		List<StavkaPozajmice> stavke = new ArrayList<>();
		stavke.add(s1);
		stavke.add(s2);
		p.setStavke(stavke);
		
		assertEquals(2, p.getStavke().size());
        assertEquals(s1, p.getStavke().get(0));
        assertEquals(s2, p.getStavke().get(1));
	}

	@Test
	void testSetPozajmicaID() {
		p.setPozajmicaID(10);
		
        assertEquals(10, p.getPozajmicaID());
	}
	
	@Test
	void testSetPozajmicaIDNegativan() {
		assertThrows(java.lang.IllegalArgumentException.class, () -> p.setPozajmicaID(-10));
	}

	@Test
	void testSetDatum() {
		p.setDatum(d);
		
        assertEquals(d, p.getDatum());
	}

	@Test
	void testSetClan() {
		p.setClan(c);
		
        assertEquals(c, p.getClan());
	}

	@Test
	void testSetNapomena() {
		p.setNapomena("Nova napomena");
		
        assertEquals("Nova napomena", p.getNapomena());
	}

	@Test
	void testSetBibliotekar() {
		p.setBibliotekar(b);
		
        assertEquals(b, p.getBibliotekar());
	}

	@Test
	void testEqualsObject() {
		Pozajmica p1 = p;
		
		assertTrue(p.equals(p1));
	}
	
	@Test
	void testEqualsObjectNull() {
	    assertFalse(p.equals(null));
	}

	@Test
	void testEqualsObjectDrugaKlasa() {
	    assertFalse(p.equals(new Autor()));
	}
	
	@ParameterizedTest
    @CsvSource({
        "1, 1, ImeClana1, PrezimeClana1, 12345, 1, ImeBib1, PrezimeBib1, username1, 1, 1, ImeClana1, PrezimeClana1, 12345, 1, ImeBib1, PrezimeBib1, username1, true",
        "1, 1, ImeClana1, PrezimeClana1, 12345, 1, ImeBib1, PrezimeBib1, username1, 2, 1, ImeClana1, PrezimeClana1, 12345, 1, ImeBib1, PrezimeBib1, username1, false",
        "1, 1, ImeClana1, PrezimeClana1, 12345, 1, ImeBib1, PrezimeBib1, username1, 1, 2, ImeClana2, PrezimeClana2, 12344, 1, ImeBib1, PrezimeBib1, username1, false",
        "1, 1, ImeClana1, PrezimeClana1, 12345, 1, ImeBib1, PrezimeBib1, username1, 1, 1, ImeClana1, PrezimeClana1, 12345, 2, ImeBib2, PrezimeBib2, username2, false",
        "1, 1, ImeClana1, PrezimeClana1, 12345, 1, ImeBib1, PrezimeBib1, username1, 2, 2, ImeClana2, PrezimeClana2, 12344, 2, ImeBib2, PrezimeBib2, username2, false",
    })
    void testEqualsObjectSveOk(
            int pozajmicaID1, int clanID1, String imeClana1, String prezimeClana1, String brojTelefona1, int bibID1, String imeBib1, String prezimeBib1, String username1, 
            int pozajmicaID2, int clanID2, String imeClana2, String prezimeClana2, String brojTelefona2, int bibID2, String imeBib2, String prezimeBib2, String username2, boolean expected) {

        Clan clan1 = new Clan(clanID1, imeClana1, prezimeClana1, brojTelefona1);
        Bibliotekar bibliotekar1 = new Bibliotekar(bibID1, imeBib1, prezimeBib1, username1, null);
        
        Clan clan2 = new Clan(clanID2, imeClana2, prezimeClana2, brojTelefona2);
        Bibliotekar bibliotekar2 = new Bibliotekar(bibID2, imeBib2, prezimeBib2, username2, null);
        
        Pozajmica pozajmica1 = new Pozajmica(pozajmicaID1, null, clan1, bibliotekar1, new ArrayList<>(), null);
        Pozajmica pozajmica2 = new Pozajmica(pozajmicaID2, null, clan2, bibliotekar2, new ArrayList<>(), null);
        
        assertEquals(expected, pozajmica1.equals(pozajmica2));
    }
	    
	@Test
	void testToString() {
		p.setBibliotekar(b);
		p.setClan(c);
		p.setDatum(d);
		p.setNapomena("Napomena");
		
		List<StavkaPozajmice> stavke = new ArrayList<>();
		stavke.add(s1);
		stavke.add(s2);
		p.setStavke(stavke);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatiranDatum = sdf.format(d);
        
        String s = p.toString();
        
        assertTrue(s.contains(formatiranDatum));
        assertTrue(s.contains(c.getIme()));
        assertTrue(s.contains(c.getPrezime()));
        assertTrue(s.contains(b.getIme()));
        assertTrue(s.contains(b.getPrezime()));
        assertTrue(s.contains(stavke.size()+ ""));
	}

}
