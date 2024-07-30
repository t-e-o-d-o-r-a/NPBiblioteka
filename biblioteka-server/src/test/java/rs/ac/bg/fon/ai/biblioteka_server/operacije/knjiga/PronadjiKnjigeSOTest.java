package rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga;

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
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Format;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;

class PronadjiKnjigeSOTest {
	
	DBRepositoryGeneric brokerMock;
    PronadjiKnjigeSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new PronadjiKnjigeSO(brokerMock);
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
    void testIzvrsiOperaciju() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setNaslov("Naslov1");
        List<ApstraktniDomenskiObjekat> ocekivanaLista = new ArrayList<>();
        ocekivanaLista.add(new Knjiga(1, "Naslov1", 2, 2020, new Autor(1, "AutorIme1", "AutorPrezime1"), new Izdavac(1, "Izdavac1"), Format.MEK_POVEZ));
        ocekivanaLista.add(new Knjiga(2, "Naslov1", 12, 2021, new Autor(2, "AutorIme2", "AutorPrezime2"), new Izdavac(2, "Izdavac2"), Format.MEK_POVEZ));

        expect(brokerMock.filter(knjiga)).andReturn(ocekivanaLista);
        replay(brokerMock);

        operacija.izvrsiOperaciju(knjiga, null);

        List<Knjiga> rezultat = operacija.getLista();

        assertEquals(ocekivanaLista, rezultat);

        verify(brokerMock);
    }
    
    @Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Knjiga knjiga = new Knjiga();

        expect(brokerMock.filter(knjiga)).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(Exception.class, () -> operacija.izvrsiOperaciju(knjiga, null));

        verify(brokerMock);
    }

	@Test
	void testPronadjiKnjigeSO() {
		PronadjiKnjigeSO operacija = new PronadjiKnjigeSO();
        assertNotNull(operacija);
	}

	@Test
	void testPronadjiKnjigeSORepository() {
		PronadjiKnjigeSO operacija = new PronadjiKnjigeSO(brokerMock);
        assertNotNull(operacija);
	}

}
