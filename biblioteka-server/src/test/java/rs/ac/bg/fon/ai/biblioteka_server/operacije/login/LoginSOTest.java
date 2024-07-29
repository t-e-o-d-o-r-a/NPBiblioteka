package rs.ac.bg.fon.ai.biblioteka_server.operacije.login;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl.DBRepositoryGeneric;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.ApstraktniDomenskiObjekat;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;

class LoginSOTest {
	
	DBRepositoryGeneric brokerMock;
	LoginSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new LoginSO(brokerMock);
	}

	@AfterEach
	void tearDown() throws Exception {
		operacija = null;
        brokerMock = null;
	}

	@Test
    void testPredusloviNevalidanObjekat() {
        assertThrows(java.lang.Exception.class, () -> operacija.preduslovi(new Autor()));
    }

    @Test
    void testPredusloviNull() {
        assertThrows(java.lang.Exception.class, () -> operacija.preduslovi(null));
    }

    @Test
    void testPredusloviValidanObjekat() {
        Bibliotekar bibliotekar = new Bibliotekar();
        assertDoesNotThrow(() -> operacija.preduslovi(bibliotekar));
    }

    @Test
    void testIzvrsiOperacijuBibliotekarPostoji() throws Exception {
    	Bibliotekar bibliotekar = new Bibliotekar(1, "Pera", "Peric", "pera", "pera123");

    	List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        lista.add(new Bibliotekar(1, "Pera", "Peric", "pera", "pera123")); 
        lista.add(new Bibliotekar(2, "Mika", "Mikic", "mika", "mika123"));

        expect(brokerMock.vratiSve(bibliotekar, null)).andReturn(lista);
        replay(brokerMock);

        operacija.izvrsiOperaciju(bibliotekar, null);

        Bibliotekar rezultat = operacija.getBibliotekar();

        assertEquals(bibliotekar, rezultat);

        verify(brokerMock);
    }

    @Test
    void testIzvrsiOperacijuBibliotekarNePostoji() throws Exception {
    	Bibliotekar bibliotekar = new Bibliotekar(1, "Pera", "Peric", "pera", "pera123");

        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        lista.add(new Bibliotekar(2, "Mika", "Mikic", "mika", "mika123"));

        expect(brokerMock.vratiSve(bibliotekar, null)).andReturn(lista);
        replay(brokerMock);

        operacija.izvrsiOperaciju(bibliotekar, null);

        Bibliotekar rezultat = operacija.getBibliotekar();

        assertNull(rezultat);

        verify(brokerMock);
    }
    
    @Test
    void testIzvrsiOperacijuGreska() throws Exception {
    	Bibliotekar bibliotekar = new Bibliotekar(1, "Pera", "Peric", "pera", "pera123");

        expect(brokerMock.vratiSve(bibliotekar, null)).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(java.lang.Exception.class, () -> operacija.izvrsiOperaciju(bibliotekar, null));

        verify(brokerMock);
    }

	@Test
	void testLoginSO() {
		LoginSO operacija = new LoginSO();
        assertNotNull(operacija);
	}

	@Test
	void testLoginSORepository() {
		LoginSO operacija = new LoginSO(brokerMock);
        assertNotNull(operacija);
	}


}
