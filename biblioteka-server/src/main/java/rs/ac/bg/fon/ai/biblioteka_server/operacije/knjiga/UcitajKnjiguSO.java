package rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja ucitava odredjenu knjigu iz baze podataka.
 * 
 * @author Teodora
 *
 */
public class UcitajKnjiguSO extends ApstraktnaGenerickaOperacija {

	/**
	 * Predstavlja ucitanu knjigu iz baze.
	 * Ukoliko knjiga nije pronadjena, ima vrednost null.
	 */
    private Knjiga knjiga;
    
    /**
     * Metoda koja proverava da li je prosledjeni objekat instanca klase Knjiga.
     * 
     * @param objekat objekat koji se validira
     * @throws java.lang.Exception ukoliko prosledjeni objekat nije instanca klase Knjiga  
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Knjiga)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    /**
     * Metoda koja ucitava podatke odredjene knjige iz baze podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Knjiga k = (Knjiga) objekat;
        knjiga = (Knjiga) broker.pronadji(k, k.getKnjigaID());
    }
    
    /**
     * Vraca ucitanu knjigu.
     * 
     * @return objekat klase Knjiga ili null ukoliko ona nije pronadjena
     */
    public Knjiga getKnjiga() {
        return knjiga;
    }
    
    
    
}
