package rs.ac.bg.fon.ai.biblioteka_server.operacije.clan;


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
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;

class PronadjiClanoveSOTest {
	
	DBRepositoryGeneric brokerMock;
	PronadjiClanoveSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new PronadjiClanoveSO(brokerMock);
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
        Clan clan = new Clan(12, "Pera", "Zikic", "123123222");
        List<ApstraktniDomenskiObjekat> ocekivanaLista = new ArrayList<>();
        ocekivanaLista.add(new Clan(1, "Pera", "Peric", "123123123"));
        ocekivanaLista.add(new Clan(2, "Pera", "Mikic", "456456456"));

        expect(brokerMock.filter(clan)).andReturn(ocekivanaLista);
        replay(brokerMock);

        operacija.izvrsiOperaciju(clan, null);
        
        List<Clan> rezultat = operacija.getLista();
        
        assertEquals(ocekivanaLista, rezultat);
        
        verify(brokerMock);
    }

    @Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Clan clan = new Clan();
        
        expect(brokerMock.filter(clan)).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);
        
        assertThrows(java.lang.Exception.class, () -> operacija.izvrsiOperaciju(clan, null));
        
        verify(brokerMock);
    }

	@Test
	void testPronadjiClanoveSO() {
		operacija = new PronadjiClanoveSO();
		assertNotNull(operacija);
	}

	@Test
	void testPronadjiClanoveSORepository() {
		operacija = new PronadjiClanoveSO(brokerMock);
		assertNotNull(operacija);
	}

}
