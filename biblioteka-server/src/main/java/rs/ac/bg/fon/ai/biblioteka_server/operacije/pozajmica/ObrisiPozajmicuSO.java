package rs.ac.bg.fon.ai.biblioteka_server.operacije.pozajmica;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.StavkaPozajmice;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;
import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;

/**
 * Sistemska operacija koja brise odredjenu pozajmicu i njene stavke iz baze.
 * Za svaku vracenu knjigu se povecava broj raspolozivih primeraka u bazi.
 * Nasledjuje apstraktnu klasu ApstraktnaGenerickaOperacija i implementira njene apstraktne metode.
 * 
 * @author Teodora
 *
 */
public class ObrisiPozajmicuSO extends ApstraktnaGenerickaOperacija {
	
	/**
	 * Pokazatelj uspesnosti operacije. Ukoliko je operacija uspesno izvrsena, ima vrednost true, a ukoliko je doslo do greske, ima vrednost false.
	 * Pocetna vrednost je false.
	 */
	private boolean uspesno = false;
	
	/**
	 * Vraca vrednost boolean promenljive upesno.
	 * Sluzi kao indikator uspesnosti izvrsene operacije.
	 * 
	 * @return true ako je operacija uspesno izvrsena, false ukoliko nije
	 */
	public boolean isUspesno() {
		return uspesno;
	}
	
	/**
     * Konstruktor koji inicijalizuje instancu operacije bez postavljanja specificne implementacije
     * brokera. Koristi default implementaciju iz klase {@link ApstraktnaGenerickaOperacija}.
     */
    public ObrisiPozajmicuSO() {
        super();
    }
    
    /**
     * Konstruktor koji omogucava postavljanje specificne implementacije brokera.
     * 
     * @param broker Instanca repozitorijuma koji se koristi za pristup bazi podataka.
     */
    public ObrisiPozajmicuSO(Repository broker) {
        super(broker);
    }

	/**
     * Metoda koja proverava da li je prosledjeni objekat instanca klase Pozajmica.
     * 
     * @param objekat objekat koji se validira
     * @throws java.lang.Exception ukoliko prosledjeni objekat nije instanca klase Pozajmica  
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Pozajmica)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    /**
     * Metoda koja brise odredjenu pozajmicu i njene stavke iz baze podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Pozajmica p = (Pozajmica) objekat;
        List<StavkaPozajmice> zaBrisanje = p.getStavke();
        for (StavkaPozajmice sp : zaBrisanje) {
            broker.obrisi((StavkaPozajmice) sp);
            Knjiga k = sp.getKnjiga();
            k.setBrojPrimeraka(k.getBrojPrimeraka() + 1);
            broker.promeni((Knjiga) k);
        }
        broker.obrisi((Pozajmica) p);
        
        uspesno = true;
    }
    
}