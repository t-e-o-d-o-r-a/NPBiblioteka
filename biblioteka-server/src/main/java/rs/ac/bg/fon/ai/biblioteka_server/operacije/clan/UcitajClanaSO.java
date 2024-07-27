package rs.ac.bg.fon.ai.biblioteka_server.operacije.clan;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja ucitava odredjenog clana iz baze podataka.
 * 
 * @author Teodora
 *
 */
public class UcitajClanaSO extends ApstraktnaGenerickaOperacija {
   
	/**
	 * Predstavlja ucitanog clana iz baze.
	 * Ukoliko clan nije pronadjen, ima vrednost null.
	 */
    private Clan clan;

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
     * Metoda koja ucitava podatke odredjenog clana iz baze podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Clan c = (Clan) objekat;
        clan = (Clan) broker.pronadji(c, c.getClanID());
    }

    /**
     * Vraca ucitanog clana.
     * 
     * @return objekat klase Clan ili null ukoliko on nije pronadjen
     */
    public Clan getClan() {
        return clan;
    }
    
    
    
}
