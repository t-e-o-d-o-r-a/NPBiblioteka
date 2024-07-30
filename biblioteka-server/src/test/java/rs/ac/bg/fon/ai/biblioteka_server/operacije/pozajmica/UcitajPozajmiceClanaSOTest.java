package rs.ac.bg.fon.ai.biblioteka_server.operacije.pozajmica;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl.DBRepositoryGeneric;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.ApstraktniDomenskiObjekat;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.StavkaPozajmice;

class UcitajPozajmiceClanaSOTest {
	
	DBRepositoryGeneric brokerMock;
    UcitajPozajmiceClanaSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new UcitajPozajmiceClanaSO(brokerMock);
	}

	@AfterEach
	void tearDown() throws Exception {
		operacija = null;
        brokerMock = null;
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
        Pozajmica pozajmica = new Pozajmica();
        assertDoesNotThrow(() -> operacija.preduslovi(pozajmica));
    }

    @Test
    void testIzvrsiOperacijuUspesno() throws Exception {
    	Clan clan = new Clan(1, "Pera", "Peric", "1234567890");
        Pozajmica p1 = new Pozajmica(1, new Date(), clan, new Bibliotekar(1, "Mika", "Mikic", "mika", "mika123"), null, null);
        Pozajmica p2 = new Pozajmica(2, new Date(), clan, new Bibliotekar(2, "Zika", "Zikic", "zika", "zika123"), null, null);

        List<ApstraktniDomenskiObjekat> ocekivanePozajmice = new ArrayList<>();
        ocekivanePozajmice.add(p1);
        ocekivanePozajmice.add(p2);

        expect(brokerMock.filter(p1)).andReturn(ocekivanePozajmice);

        StavkaPozajmice sp1 = new StavkaPozajmice(p1, 1, new Date(), new Knjiga());
        StavkaPozajmice sp2 = new StavkaPozajmice(p1, 2, new Date(), new Knjiga());
        List<ApstraktniDomenskiObjekat> stavkeP1 = new ArrayList<>();
        stavkeP1.add(sp1);
        stavkeP1.add(sp2);

        StavkaPozajmice sp3 = new StavkaPozajmice(p2, 1, new Date(), new Knjiga());
        StavkaPozajmice sp4 = new StavkaPozajmice(p2, 1, new Date(), new Knjiga());
        List<ApstraktniDomenskiObjekat> stavkeP2 = new ArrayList<>();
        stavkeP2.add(sp3);
        stavkeP2.add(sp4);

        expect(brokerMock.filter(isA(StavkaPozajmice.class))).andAnswer(() -> {
            StavkaPozajmice arg = (StavkaPozajmice) getCurrentArguments()[0];
            if (arg.getPozajmica().equals(p1)) {
                return stavkeP1;
            } else if (arg.getPozajmica().equals(p2)) {
                return stavkeP2;
            } else {
                return new ArrayList<>();
            }
        }).times(2);
        
        replay(brokerMock);

        operacija.izvrsiOperaciju(p1, null);

        List<Pozajmica> rezultat = operacija.getPozajmice();

        assertEquals(ocekivanePozajmice, rezultat);
        assertEquals(stavkeP1, rezultat.get(0).getStavke());
        assertEquals(stavkeP2, rezultat.get(1).getStavke());

        verify(brokerMock);
    }
	
	@Test
    void testIzvrsiOperacijuGreska() throws Exception {
        Pozajmica p = new Pozajmica(1, new Date(), new Clan(1, "Pera", "Peric", "1234567890"), new Bibliotekar(1, "Mika", "Mikic", "mika", "mika123"), null, null);
        
        expect(brokerMock.filter(p)).andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(java.lang.Exception.class, () -> operacija.izvrsiOperaciju(p, null));

        verify(brokerMock);
    }

	@Test
	void testUcitajPozajmiceClanaSORepository() {
		UcitajPozajmiceClanaSO operacija = new UcitajPozajmiceClanaSO(brokerMock);
        assertNotNull(operacija);
	}

	@Test
	void testGetPozajmice() {
		UcitajPozajmiceClanaSO operacija = new UcitajPozajmiceClanaSO();
        assertNotNull(operacija);
	}

}
