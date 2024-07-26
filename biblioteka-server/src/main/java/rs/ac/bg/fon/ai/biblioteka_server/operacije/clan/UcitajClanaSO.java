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

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Clan)) {
            throw new Exception("Neispravni parametar.");
        }
    }

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
