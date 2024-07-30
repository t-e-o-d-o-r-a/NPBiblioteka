package rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl.DBRepositoryGeneric;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Format;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;

class ObrisiKnjiguSOTest {
	
	DBRepositoryGeneric brokerMock;
    ObrisiKnjiguSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new ObrisiKnjiguSO(brokerMock);
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
        Knjiga  knjiga = new Knjiga();
        assertDoesNotThrow(() -> operacija.preduslovi(knjiga));
    }

    @Test
    void testIzvrsiOperacijuUspesno() throws Exception {
        Knjiga knjiga = new Knjiga(1, "Naslov", 3, 2000, new Autor(), new Izdavac(), Format.MEK_POVEZ);

        brokerMock.obrisi(knjiga);
        expectLastCall().once();
        replay(brokerMock);

        operacija.izvrsiOperaciju(knjiga, null);

        assertTrue(operacija.isUspesno());

        verify(brokerMock);
    }

    @Test
    void testIzvrsiOperacijuGreska() throws Exception {
    	Knjiga knjiga = new Knjiga(1, "Naslov", 3, 2000, new Autor(), new Izdavac(), Format.MEK_POVEZ);

        brokerMock.obrisi(knjiga);
        expectLastCall().andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(Exception.class, () -> operacija.izvrsiOperaciju(knjiga, null));

        assertFalse(operacija.isUspesno());

        verify(brokerMock);
    }

	@Test
	void testObrisiKnjiguSO() {
		ObrisiKnjiguSO operacija = new ObrisiKnjiguSO();
        assertNotNull(operacija);
	}

	@Test
	void testObrisiKnjiguSORepository() {
		ObrisiKnjiguSO operacija = new ObrisiKnjiguSO(brokerMock);
        assertNotNull(operacija);
	}

}
