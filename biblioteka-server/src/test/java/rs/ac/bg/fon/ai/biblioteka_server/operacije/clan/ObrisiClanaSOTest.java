package rs.ac.bg.fon.ai.biblioteka_server.operacije.clan;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl.DBRepositoryGeneric;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;

class ObrisiClanaSOTest {
	
	DBRepositoryGeneric brokerMock;
	ObrisiClanaSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new ObrisiClanaSO(brokerMock);
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
        Clan clan = new Clan();
        assertDoesNotThrow(() -> operacija.preduslovi(clan));
    }

    @Test
    void testIzvrsiOperaciju() throws Exception {
        Clan clan = new Clan(1, "Pera", "Peric", "123123123");

        brokerMock.obrisi(clan);
        expectLastCall().once();
        replay(brokerMock);

        operacija.izvrsiOperaciju(clan, null);

        assertTrue(operacija.isUspesno());
        verify(brokerMock);
    }

    @Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Clan clan = new Clan(1, "Pera", "Peric", "123123123");

        brokerMock.obrisi(clan);
        expectLastCall().andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(Exception.class, () -> operacija.izvrsiOperaciju(clan, null));

        assertFalse(operacija.isUspesno());
        verify(brokerMock);
    }

	@Test
	void testObrisiClanaSO() {
		ObrisiClanaSO operacija = new ObrisiClanaSO();
		assertNotNull(operacija);
	}

	@Test
	void testObrisiClanaSORepository() {
		ObrisiClanaSO operacija = new ObrisiClanaSO(brokerMock);
		assertNotNull(operacija);
	}

}
