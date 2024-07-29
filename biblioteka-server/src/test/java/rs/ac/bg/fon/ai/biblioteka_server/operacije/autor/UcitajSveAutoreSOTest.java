package rs.ac.bg.fon.ai.biblioteka_server.operacije.autor;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;
import rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl.DBRepositoryGeneric;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.ApstraktniDomenskiObjekat;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;

class UcitajSveAutoreSOTest {
	
	DBRepositoryGeneric brokerMock;
	UcitajSveAutoreSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
		operacija = new UcitajSveAutoreSO(brokerMock);
	}

	@AfterEach
	void tearDown() throws Exception {
		operacija = null;
		brokerMock = null;
	}
	
	@Test
	void testUcitajSveAutoreSO() {
	    UcitajSveAutoreSO operacija = new UcitajSveAutoreSO();
	    assertNotNull(operacija);
	}

	@Test
	void testUcitajSveAutoreSORepository() {
	    UcitajSveAutoreSO operacija = new UcitajSveAutoreSO(brokerMock);
	    assertNotNull(operacija);
	}

	@Test
	void testPredusloviNevalidanObjekat() {
		assertThrows(java.lang.Exception.class, () -> operacija.preduslovi(new Knjiga()));
	}
	
	@Test
	void testPredusloviNull() {
		assertThrows(java.lang.Exception.class, () -> operacija.preduslovi(null));
	}
	
	@Test
	void testPredusloviValidanObjekat() {
		Autor autor = new Autor();
		assertDoesNotThrow(() -> operacija.preduslovi(autor));
	}

	@Test
	void testIzvrsiOperaciju() throws Exception {
		Autor autor = new Autor();
		List<ApstraktniDomenskiObjekat> ocekivanaLista = new ArrayList<>();
		ocekivanaLista.add(new Autor(1, "Ime1", "Prezime1"));
		ocekivanaLista.add(new Autor(2, "Ime2", "Prezime2"));
        
        expect(brokerMock.vratiSve(autor, null)).andReturn(ocekivanaLista);
        replay(brokerMock);

        operacija.izvrsiOperaciju(autor, null);
        
        List<Autor> rezultat = operacija.getLista();
        
        assertEquals(ocekivanaLista, rezultat);
        
        verify(brokerMock);
	}
	
	@Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Autor autor = new Autor();
        
        expect(brokerMock.vratiSve(autor, null)).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);
        
        assertThrows(java.lang.Exception.class, () -> operacija.izvrsiOperaciju(autor, null));
        
        verify(brokerMock);
    }

	@Test
	void testGetLista() {
		List<Autor> ocekivanaLista = new ArrayList<>();
        operacija.lista = ocekivanaLista;
        
        assertEquals(ocekivanaLista, operacija.getLista());
	}

}
