package rs.ac.bg.fon.ai.biblioteka_server.operacije.izdavac;

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
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;

class UcitajSveIzdavaceSOTest {
	
	DBRepositoryGeneric brokerMock;
    UcitajSveIzdavaceSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new UcitajSveIzdavaceSO(brokerMock);
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
        Izdavac izdavac = new Izdavac();
        assertDoesNotThrow(() -> operacija.preduslovi(izdavac));
    }

	@Test
	void testIzvrsiOperaciju() throws Exception {
		Izdavac izdavac = new Izdavac(); 
        List<ApstraktniDomenskiObjekat> ocekivanaLista = new ArrayList<>();
        ocekivanaLista.add(new Izdavac(1, "Naziv1")); 
        ocekivanaLista.add(new Izdavac(2, "Naziv2")); 
        
        expect(brokerMock.vratiSve(izdavac, null)).andReturn(ocekivanaLista);
        replay(brokerMock);

        operacija.izvrsiOperaciju(izdavac, null);

        List<Izdavac> rezultat = operacija.getLista();

        assertEquals(ocekivanaLista, rezultat);

        verify(brokerMock);
	}
	
	@Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Izdavac izdavac = new Izdavac(); 

        expect(brokerMock.vratiSve(izdavac, null)).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(java.lang.Exception.class, () -> operacija.izvrsiOperaciju(izdavac, null));

        verify(brokerMock);
    }

	@Test
	void testUcitajSveIzdavaceSO() {
		UcitajSveIzdavaceSO operacija = new UcitajSveIzdavaceSO();
        assertNotNull(operacija);
	}

	@Test
	void testUcitajSveIzdavaceSORepository() {
		UcitajSveIzdavaceSO operacija = new UcitajSveIzdavaceSO(brokerMock);
        assertNotNull(operacija);
	}

	@Test
	void testGetLista() {
		List<Izdavac> ocekivanaLista = new ArrayList<>();
        operacija.lista = ocekivanaLista;

        assertEquals(ocekivanaLista, operacija.getLista());
	}

}
