package rs.ac.bg.fon.ai.biblioteka_server.operacije.clan;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;
import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;

/**
 * Sistemska operacija koja brise odredjenog clana iz baze.
 * Nasledjuje apstraktnu klasu ApstraktnaGenerickaOperacija i implementira njene apstraktne metode.
 * 
 * @author Teodora
 *
 */
public class ObrisiClanaSO extends ApstraktnaGenerickaOperacija{
	
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
    public ObrisiClanaSO() {
        super();
    }
    
    /**
     * Konstruktor koji omogucava postavljanje specificne implementacije brokera.
     * 
     * @param broker Instanca repozitorijuma koji se koristi za pristup bazi podataka.
     */
    public ObrisiClanaSO(Repository broker) {
        super(broker);
    }

	/**
     * Metoda koja proverava da li je prosledjeni objekat instanca klase Clan.
     * 
     * @param objekat objekat koji se validira
     * @throws java.lang.Exception ukoliko prosledjeni objekat nije instanca klase Clan  
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Clan)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    /**
     * Metoda koja brise odredjenog clana iz baze podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.obrisi((Clan) objekat);
        uspesno = true;
    }
    
}
