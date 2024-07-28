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

class ClanTest {

	Clan c;
	
	@BeforeEach
	void setUp() throws Exception {
		c = new Clan();
	}

	@AfterEach
	void tearDown() throws Exception {
		c = null;
	}

	@Test
	void testVratiNazivTabele() {
		assertEquals("clan", c.vratiNazivTabele());
	}

	@Test
	void testVratiListu() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("clan.clanID")).andReturn(1);
        expect(rsMock.getString("clan.ime")).andReturn("Ime");
        expect(rsMock.getString("clan.prezime")).andReturn("Prezime");
        expect(rsMock.getString("clan.brojTelefona")).andReturn("1234567890");

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("clan.clanID")).andReturn(2);
        expect(rsMock.getString("clan.ime")).andReturn("Ime2");
        expect(rsMock.getString("clan.prezime")).andReturn("Prezime2");
        expect(rsMock.getString("clan.brojTelefona")).andReturn("0987654321");

        expect(rsMock.next()).andReturn(false);

        replay(rsMock);

        List<ApstraktniDomenskiObjekat> lista = c.vratiListu(rsMock);

        assertEquals(2, lista.size());
        assertEquals(new Clan(1, "Ime", "Prezime", "1234567890"), lista.get(0));
        assertEquals(new Clan(2, "Ime2", "Prezime2", "0987654321"), lista.get(1));

        verify(rsMock);
	}
	
	@Test
    void testVratiListuGreska() throws Exception {
        ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani SQL izuzetak"));

        replay(rsMock);

        assertThrows(SQLException.class, () -> c.vratiListu(rsMock));

        verify(rsMock);
    }

	@Test
	void testVratiKoloneZaUbacivanje() {
        assertEquals("ime,prezime,brojTelefona", c.vratiKoloneZaUbacivanje());
	}

	@Test
	void testVratiVrednostiZaUbacivanje() {
		c.setIme("Ime");
        c.setPrezime("Prezime");
        c.setBrojTelefona("1234567890");
        
        assertEquals("'Ime','Prezime','1234567890'", c.vratiVrednostiZaUbacivanje());
	}

	@Test
	void testVratiPrimarniKljuc() {
		c.setClanID(1);

        assertEquals("clan.clanID=1", c.vratiPrimarniKljuc());
	}

	@Test
	void testVratiObjekatIzRS() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("clan.clanID")).andReturn(1);
        expect(rsMock.getString("clan.ime")).andReturn("Ime");
        expect(rsMock.getString("clan.prezime")).andReturn("Prezime");
        expect(rsMock.getString("clan.brojTelefona")).andReturn("1234567890");

        replay(rsMock);

        ApstraktniDomenskiObjekat obj = c.vratiObjekatIzRS(rsMock);
        assertNotNull(obj);
        assertTrue(obj instanceof Clan);
        Clan rezultat = (Clan) obj;
        assertEquals(1, rezultat.getClanID());
        assertEquals("Ime", rezultat.getIme());
        assertEquals("Prezime", rezultat.getPrezime());
        assertEquals("1234567890", rezultat.getBrojTelefona());

        verify(rsMock);
	}
	
	@Test
    void testVratiObjekatIzRSGreska() throws Exception {
        ResultSet rsMock = createMock(ResultSet.class);

        expect(rsMock.next()).andThrow(new SQLException("Simulirani SQL izuzetak"));

        replay(rsMock);

        assertThrows(SQLException.class, () -> c.vratiObjekatIzRS(rsMock));

        verify(rsMock);
    }

	@Test
	void testVratiVrednostiZaIzmenu() {
		c.setIme("Ime");
        c.setPrezime("Prezime");
        c.setBrojTelefona("1234567890");
        
        assertEquals("ime='Ime', prezime='Prezime', brojTelefona='1234567890'", c.vratiVrednostiZaIzmenu());
	}

	@Test
	void testVratiUslovZaFiltriranje() {
		c.setIme("Pera");
		
		assertEquals("clan.ime LIKE '%Pera%'", c.vratiUslovZaFiltriranje(c));
	}

	@Test
	void testClan() {
		assertNotNull(c);
        assertEquals(0, c.getClanID());
        assertNull(c.getIme());
        assertNull(c.getPrezime());
        assertNull(c.getBrojTelefona());
	}

	@Test
	void testClanIntStringStringString() {
		c = new Clan(1, "Ime", "Prezime", "1234567890");

        assertNotNull(c);
        assertEquals(1, c.getClanID());
        assertEquals("Ime", c.getIme());
        assertEquals("Prezime", c.getPrezime());
        assertEquals("1234567890", c.getBrojTelefona());
	}

	@Test
	void testSetClanID() {
		c.postaviId(10);
		
        assertEquals(10, c.getClanID());
	}
	
	@Test
    void testSetClanIDNegativan() {
        assertThrows(java.lang.IllegalArgumentException.class, () -> c.setClanID(-1));
    }

	@Test
	void testSetIme() {
		c.setIme("Ime");

        assertEquals("Ime", c.getIme());
	}
	
	@Test
    void testSetImeNull() {
        assertThrows(NullPointerException.class, () -> c.setIme(null));
    }

    @Test
    void testSetImePrazno() {
        assertThrows(IllegalArgumentException.class, () -> c.setIme(""));
    }

	@Test
	void testSetPrezime() {
		c.setPrezime("Prezime");

        assertEquals("Prezime", c.getPrezime());
	}
	
	@Test
    void testSetPrezimeNull() {
        assertThrows(NullPointerException.class, () -> c.setPrezime(null));
    }

    @Test
    void testSetPrezimePrazno() {
        assertThrows(IllegalArgumentException.class, () -> c.setPrezime(""));
    }

	@Test
	void testSetBrojTelefona() {
		c.setBrojTelefona("1234567890");

        assertEquals("1234567890", c.getBrojTelefona());
	}
	
	@Test
    void testSetBrojTelefonaPredug() {
        assertThrows(IllegalArgumentException.class, () -> c.setBrojTelefona("12345678901"));
    }

	@Test
	void testEqualsObject() {
		Clan b = c;

        assertTrue(c.equals(b));
	}
	
	@Test
    void testEqualsObjectNull() {
        assertFalse(c.equals(null));
    }

    @Test
    void testEqualsObjectDrugaKlasa() {
        assertFalse(c.equals(new Izdavac()));
    }
    
    @ParameterizedTest
    @CsvSource({
        "1, Ime, Prezime, 1234567890, 1, Ime, Prezime, 1234567890, true",
        "1, Ime, Prezime, 1234567890, 2, Ime, Prezime, 1234567890, true",
        "1, Ime, Prezime, 1234567890, 1, Ime, Prezime, 0987654321, false",
        "1, Ime, Prezime, 1234567890, 1, Ime1, Prezime, 1234567890, false",
        "1, Ime, Prezime, 1234567890, 1, Ime, Prezime1, 1234567890, false",
        "1, Ime, Prezime, 1234567890, 2, Ime2, Prezime2, 0987654321, false"
    })
    void testEqualsObjectSveOk(int id1, String ime1, String prezime1, String brojTelefona1, int id2, String ime2, String prezime2, String brojTelefona2, boolean eq) {
        c.setClanID(id1);
        c.setIme(ime1);
        c.setPrezime(prezime1);
        c.setBrojTelefona(brojTelefona1);

        Clan b = new Clan(id2, ime2, prezime2, brojTelefona2);

        assertEquals(eq, c.equals(b));
    }

	@Test
	void testToString() {
		c.setIme("Ime");
        c.setPrezime("Prezime");

        String s = c.toString();

        assertTrue(s.contains("Ime"));
        assertTrue(s.contains("Prezime"));
	}

}
