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

class UcitajKnjiguSOTest {

	DBRepositoryGeneric brokerMock;
    UcitajKnjiguSO operacija;
	
	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new UcitajKnjiguSO(brokerMock);
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
        knjiga.setKnjigaID(1);

        Knjiga ocekivanaKnjiga = new Knjiga(1, "Naslov1", 2, 2020, new Autor(1, "AutorIme1", "AutorPrezime1"), new Izdavac(1, "Izdavac1"), Format.MEK_POVEZ);

        expect(brokerMock.pronadji(knjiga, 1)).andReturn(ocekivanaKnjiga);
        replay(brokerMock);

        operacija.izvrsiOperaciju(knjiga, null);

        Knjiga rezultat = operacija.getKnjiga();

        assertEquals(ocekivanaKnjiga, rezultat);

        verify(brokerMock);
    }

    @Test
    void testIzvrsiOperacijuKnjigaNijePronadjena() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setKnjigaID(1);

        expect(brokerMock.pronadji(knjiga, 1)).andReturn(null);
        replay(brokerMock);

        operacija.izvrsiOperaciju(knjiga, null);

        Knjiga rezultat = operacija.getKnjiga();

        assertNull(rezultat);

        verify(brokerMock);
    }

    @Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setKnjigaID(1);

        expect(brokerMock.pronadji(knjiga, 1)).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(java.lang.Exception.class, () -> operacija.izvrsiOperaciju(knjiga, null));

        verify(brokerMock);
    }

	@Test
	void testUcitajKnjiguSO() {
		UcitajKnjiguSO operacija = new UcitajKnjiguSO();
        assertNotNull(operacija);
	}

	@Test
	void testUcitajKnjiguSORepository() {
		UcitajKnjiguSO operacija = new UcitajKnjiguSO(brokerMock);
        assertNotNull(operacija);
	}

}
