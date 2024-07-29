package rs.ac.bg.fon.ai.biblioteka_server.operacije.clan;

import static org.junit.jupiter.api.Assertions.*;

import org.easymock.EasyMock;

import static org.easymock.EasyMock.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl.DBRepositoryGeneric;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;

class UcitajClanaSOTest {
	
	DBRepositoryGeneric brokerMock;
	UcitajClanaSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new UcitajClanaSO(brokerMock);
	}

	@AfterEach
	void tearDown() throws Exception {
		operacija = null;
        brokerMock = null;
	}
	
	@Test
    void testUcitajClanaSO() {
        UcitajClanaSO operacija = new UcitajClanaSO();
        assertNotNull(operacija);
    }

    @Test
    void testUcitajClanaSORepository() {
        UcitajClanaSO operacija = new UcitajClanaSO(brokerMock);
        assertNotNull(operacija);
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
        Clan ocekivaniClan = new Clan(1, "Pera", "Peric", "123123123");

        expect(brokerMock.pronadji(clan, clan.getClanID())).andReturn(ocekivaniClan);
        replay(brokerMock);

        operacija.izvrsiOperaciju(clan, null);

        Clan rezultat = operacija.getClan();

        assertEquals(ocekivaniClan, rezultat);

        verify(brokerMock);
    }

    @Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Clan clan = new Clan(1, "Pera", "Peric", "123123123");

        expect(brokerMock.pronadji(clan, clan.getClanID())).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(java.lang.Exception.class, () -> operacija.izvrsiOperaciju(clan, null));

        verify(brokerMock);
    }

}
