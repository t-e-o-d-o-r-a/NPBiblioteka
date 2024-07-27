package rs.ac.bg.fon.ai.biblioteka_server.operacije.clan;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja pretrazuje clanove u bazi po odredjenim parametrima pretrage.
 * 
 * @author Teodora
 *
 */
public class PronadjiClanoveSO extends ApstraktnaGenerickaOperacija {
    
	/**
	 * Lista elemenata klase Clan. Bice popunjena pronadjenim clanovima koji se ucitaju iz baze.
	 * Ukoliko nema clanova koji odgovaraju datim parametrima pretrage, lista ostaje prazna.
	 */
    private List<Clan> lista;

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
     * Metoda koja pretrazuje clanove u bazi podataka prema odredjenim parametrima pretrage.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        lista = broker.filter((Clan) objekat);
    }

    /**
     * Vraca listu clanova koji su pronadjeni i ucitani iz baze ili praznu listu ako nema trazenih clanova.
     * 
     * @return lista elemenata klase Clan ili prazna lista
     */
    public List<Clan> getLista() {
        return lista;
    }
    
    
}
