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
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Format;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.StavkaPozajmice;

class KreirajPozajmicuSOTest {
	
	DBRepositoryGeneric brokerMock;
    KreirajPozajmicuSO operacija;

	@BeforeEach
	void setUp() throws Exception {
		brokerMock = EasyMock.createMock(DBRepositoryGeneric.class);
        operacija = new KreirajPozajmicuSO(brokerMock);
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
		Pozajmica p = new Pozajmica(10, new Date(), new Clan(1, "Pera", "Peric", "1234567890"), new Bibliotekar(2, "Mika", "Mikic", "mika", "mika123"), null, null);
		
		Autor autor = new Autor(1, "Ime1", "Prezime1");
        Izdavac izdavac = new Izdavac(1, "Izdavac1");
        Knjiga k1 = new Knjiga(1, "Naslov1", 3, 2020, autor, izdavac, Format.TVRD_POVEZ);
        StavkaPozajmice sp = new StavkaPozajmice(p, 1, new Date(), k1);
        
        Autor autor2 = new Autor(2, "Ime2", "Prezime2");
        Izdavac izdavac2 = new Izdavac(2, "Izdavac2");
        Knjiga k2 = new Knjiga(2, "Naslov2", 3, 2022, autor2, izdavac2, Format.MEK_POVEZ);
        StavkaPozajmice sp2 = new StavkaPozajmice(p, 2, new Date(), k2);

        List<StavkaPozajmice> stavke = new ArrayList<>();
        stavke.add(sp);
        stavke.add(sp2);
        p.setStavke(stavke);
        
        brokerMock.dodaj(p);
        expectLastCall().once();

        brokerMock.dodaj(sp);
        expectLastCall().once();
        
        brokerMock.dodaj(sp2);
        expectLastCall().once();

        k1.setBrojPrimeraka(2);
        brokerMock.promeni(k1);
        expectLastCall().once();

        k2.setBrojPrimeraka(2);
        brokerMock.promeni(k2);
        expectLastCall().once();

        replay(brokerMock);

        operacija.izvrsiOperaciju(p, null);

        assertTrue(operacija.isUspesno());
        verify(brokerMock);
        
	}
	
	@Test
    void testIzvrsiOperacijuGreska() throws Exception {
		Pozajmica p = new Pozajmica(10, new Date(), new Clan(1, "Pera", "Peric", "1234567890"), new Bibliotekar(2, "Mika", "Mikic", "mika", "mika123"), null, null);
		
		Autor autor = new Autor(1, "Ime1", "Prezime1");
        Izdavac izdavac = new Izdavac(1, "Izdavac1");
        Knjiga k1 = new Knjiga(1, "Naslov1", 3, 2020, autor, izdavac, Format.TVRD_POVEZ);
        StavkaPozajmice sp = new StavkaPozajmice(p, 1, new Date(), k1);

        List<StavkaPozajmice> stavke = new ArrayList<>();
        stavke.add(sp);
        p.setStavke(stavke);
        
        brokerMock.dodaj(p);
        expectLastCall().once();

        brokerMock.dodaj(sp);
        expectLastCall().andThrow(new Exception("Simulirana greska."));
        replay(brokerMock);

        assertThrows(Exception.class, () -> operacija.izvrsiOperaciju(p, null));

        assertFalse(operacija.isUspesno());
        verify(brokerMock);
    }

	@Test
	void testKreirajPozajmicuSO() {
		KreirajPozajmicuSO operacija = new KreirajPozajmicuSO();
        assertNotNull(operacija);
	}

	@Test
	void testKreirajPozajmicuSORepository() {
		KreirajPozajmicuSO operacija = new KreirajPozajmicuSO(brokerMock);
        assertNotNull(operacija);
	}

}
