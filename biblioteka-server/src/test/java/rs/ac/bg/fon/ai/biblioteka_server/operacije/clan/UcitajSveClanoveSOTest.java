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

class UcitajSveClanoveSOTest {
	
	DBRepositoryGeneric brokerMock;
	UcitajSveClanoveSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new UcitajSveClanoveSO(brokerMock);
	}

	@AfterEach
	void tearDown() throws Exception {
		operacija = null;
        brokerMock = null;
	}
	
	@Test
    void testUcitajSveClanoveSO() {
        UcitajSveClanoveSO operacija = new UcitajSveClanoveSO();
        assertNotNull(operacija);
    }

    @Test
    void testUcitajSveClanoveSORepository() {
        UcitajSveClanoveSO operacija = new UcitajSveClanoveSO(brokerMock);
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
        Clan clan = new Clan(); 
        List<ApstraktniDomenskiObjekat> ocekivanaLista = new ArrayList<>();
        ocekivanaLista.add(new Clan(1, "Ime1", "Prezime1", "123123123"));
        ocekivanaLista.add(new Clan(2, "Ime2", "Prezime2", "321321321"));

        expect(brokerMock.vratiSve(clan, null)).andReturn(ocekivanaLista);
        replay(brokerMock);

        operacija.izvrsiOperaciju(clan, null);

        List<Clan> rezultat = operacija.getLista();

        assertEquals(ocekivanaLista, rezultat);

        verify(brokerMock);
    }

    @Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Clan clan = new Clan();

        expect(brokerMock.vratiSve(clan, null)).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(java.lang.Exception.class, () -> operacija.izvrsiOperaciju(clan, null));

        verify(brokerMock);
    }

}
