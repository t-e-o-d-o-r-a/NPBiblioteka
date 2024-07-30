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

class AutorTest {
	
	Autor a;

	@BeforeEach
	void setUp() throws Exception {
		a = new Autor();
	}

	@AfterEach
	void tearDown() throws Exception {
		a = null;
	}

	@Test
	void testVratiNazivTabele() {
		assertEquals("autor", a.vratiNazivTabele());
	}

	@Test
	void testVratiListu() throws Exception {
		// mockujemo ResultSet klasu
		ResultSet rsMock = createMock(ResultSet.class);
		
		// definisemo ocekivano ponasanje
		expect(rsMock.next()).andReturn(true);
		expect(rsMock.getInt("autor.autorID")).andReturn(1);
	    expect(rsMock.getString("autor.ime")).andReturn("Ime1");
	    expect(rsMock.getString("autor.prezime")).andReturn("Prezime1");

	    expect(rsMock.next()).andReturn(true);
	    expect(rsMock.getInt("autor.autorID")).andReturn(2);
	    expect(rsMock.getString("autor.ime")).andReturn("Ime2");
	    expect(rsMock.getString("autor.prezime")).andReturn("Prezime2");

	    expect(rsMock.next()).andReturn(false);
	    
	    // aktiviramo mock objekat da bi poceo da reaguje na ocekivane pozive
	    replay(rsMock);
	
	    // poziv metode koja se testira
	    List<ApstraktniDomenskiObjekat> lista = a.vratiListu(rsMock);
	    
	    // provera rezultata
	    assertEquals(2, lista.size());
	    assertEquals(new Autor(1, "Ime1", "Prezime1"), lista.get(0));
	    assertEquals(new Autor(2, "Ime2", "Prezime2"), lista.get(1));
	    
	    // verifikacija mock objekta - ako neko ocekivanje nije ispunjeno, test ce pasti
	    verify(rsMock);
	}
	
	@Test
	void testVratiListuGreska() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);
		
		expect(rsMock.next()).andThrow(new SQLException("Simulirani izuzetak"));
		
		replay(rsMock);
		
		assertThrows(java.sql.SQLException.class, () -> a.vratiListu(rsMock));
		
		verify(rsMock);
	}

	@Test
	void testVratiPrimarniKljuc() {
		a.setAutorID(1);
		
		assertEquals("autor.autorID=1", a.vratiPrimarniKljuc());
	}

	@Test
	void testVratiObjekatIzRS() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);
		
		expect(rsMock.next()).andReturn(true);
        expect(rsMock.getInt("autor.autorID")).andReturn(1);
        expect(rsMock.getString("autor.ime")).andReturn("Ime1");
        expect(rsMock.getString("autor.prezime")).andReturn("Prezime1");
        
        replay(rsMock);
        
        ApstraktniDomenskiObjekat obj = a.vratiObjekatIzRS(rsMock);
        assertNotNull(obj);
        assertTrue(obj instanceof Autor);
        Autor rezultat = (Autor) obj;
        assertEquals(1, rezultat.getAutorID());
        assertEquals("Ime1", rezultat.getIme());
        assertEquals("Prezime1", rezultat.getPrezime());
        
        verify(rsMock);
	}
	
	@Test
	void testVratiObjekatIzRSGreska() throws Exception {
		ResultSet rsMock = createMock(ResultSet.class);
		
		expect(rsMock.next()).andThrow(new SQLException("Simulirani SQL izuzetak"));
		
		replay(rsMock);
		
		assertThrows(java.sql.SQLException.class, () -> a.vratiObjekatIzRS(rsMock));
		
		verify(rsMock);
	}

	@Test
	void testAutor() {
		assertNotNull(a);
		assertEquals(0, a.getAutorID());
		assertNull(a.getIme());
		assertNull(a.getPrezime());
	}

	@Test
	void testAutorIntStringString() {
		a = new Autor(1, "Pera", "Peric");
		
		assertNotNull(a);
		assertEquals(1, a.getAutorID());
		assertEquals("Pera", a.getIme());
		assertEquals("Peric", a.getPrezime());
	}

	@Test
	void testSetPrezime() {
		a.setPrezime("Peric");
		
		assertEquals("Peric", a.getPrezime());
	}
	
	@Test
	void testSetPrezimeNull() {
		assertThrows(java.lang.NullPointerException.class, () -> a.setPrezime(null));
	}
	
	@Test
	void testSetPrezimePrazno() {
		assertThrows(java.lang.IllegalArgumentException.class, () -> a.setPrezime(""));
	}

	@Test
	void testSetAutorID() {
		a.setAutorID(10);
		
		assertEquals(10, a.getAutorID());
	}
	
	@Test
	void testSetAutorIDNegativan() {
		assertThrows(java.lang.IllegalArgumentException.class, () -> a.setAutorID(-1));
	}

	@Test
	void testSetIme() {
		a.setIme("Pera");
		
		assertEquals("Pera", a.getIme());
	}

	@Test
	void testSetImePrazno() {
		assertThrows(java.lang.IllegalArgumentException.class, () -> a.setIme(""));
	}

	@Test
	void testSetImeNull() {
		assertThrows(java.lang.NullPointerException.class, () -> a.setIme(null));
	}

	
	@Test
	void testEqualsObject() {
		Autor b = a;
		
		assertTrue(a.equals(b));
	}
	
	@Test
	void testEqualsObjectNull() {
		assertFalse(a.equals(null));
	}
	
	@Test
	void testEqualsObjectDrugaKlasa() {
		assertFalse(a.equals(new Izdavac()));
	}
	
	@ParameterizedTest
	@CsvSource({
		"1, 1, Pera, Peric, Pera, Peric, true",
		"1, 1, Pera, Peric, Mika, Peric, false",
		"1, 1, Pera, Peric, Pera, Mikic, false",
		"1, 2, Pera, Peric, Mika, Mikic, false",
		"1, 2, Pera, Peric, Mika, Mikic, false"
	})
	void testEqualsObjectSveOk(int id1, int id2, String ime1, String prezime1, String ime2, String prezime2, boolean eq) {
		a.setAutorID(id1);
		a.setIme(ime1);
		a.setPrezime(prezime1);
		
		Autor b = new Autor(id2, ime2, prezime2);
		
		assertEquals(eq, a.equals(b));
	}

	@Test
	void testToString() {
		a.setIme("Mika");
		a.setPrezime("Mikic");
		
		String s = a.toString();
		
		assertTrue(s.contains("Mika"));
		assertTrue(s.contains("Mikic"));
	}

}
