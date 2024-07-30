package rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Format;

class UcitajSveKnjigeSOTest {
	
	DBRepositoryGeneric brokerMock;
	UcitajSveKnjigeSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new UcitajSveKnjigeSO(brokerMock);
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
        List<ApstraktniDomenskiObjekat> ocekivanaLista = new ArrayList<>();
        ocekivanaLista.add(new Knjiga(1, "Naslov1", 2, 2020, new Autor(1, "AutorIme1", "AutorPrezime1"), new Izdavac(1, "Izdavac1"), Format.MEK_POVEZ));
        ocekivanaLista.add(new Knjiga(2, "Naslov2", 12, 2021, new Autor(2, "AutorIme2", "AutorPrezime2"), new Izdavac(2, "Izdavac2"), Format.MEK_POVEZ));

        expect(brokerMock.vratiSve(knjiga, null)).andReturn(ocekivanaLista);
        replay(brokerMock);

        operacija.izvrsiOperaciju(knjiga, null);

        List<Knjiga> rezultat = operacija.getLista();

        assertEquals(ocekivanaLista, rezultat);

        verify(brokerMock);
    }

    @Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Knjiga knjiga = new Knjiga();

        expect(brokerMock.vratiSve(knjiga, null)).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(java.lang.Exception.class, () -> operacija.izvrsiOperaciju(knjiga, null));

        verify(brokerMock);
    }

	@Test
	void testUcitajSveKnjigeSO() {
		UcitajSveKnjigeSO operacija = new UcitajSveKnjigeSO();
        assertNotNull(operacija);
	}

	@Test
	void testUcitajSveKnjigeSORepository() {
		UcitajSveKnjigeSO operacija = new UcitajSveKnjigeSO(brokerMock);
        assertNotNull(operacija);
	}

}
