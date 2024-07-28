package rs.ac.bg.fon.ai.biblioteka_server.operacije.clan;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja vraca sve clanove koji postoje u bazi podataka.
 * Nasledjuje apstraktnu klasu ApstraktnaGenerickaOperacija i implementira njene apstraktne metode.
 * 
 * @author Teodora
 *
 */
public class UcitajSveClanoveSO extends ApstraktnaGenerickaOperacija {
    
	/**
	 * Lista elemenata klase Clan. Bice popunjena svim clanovima koji se ucitaju iz baze.
	 * Ukoliko nema clanova, lista ostaje prazna.
	 */
    List<Clan> lista;

    /**
     * Vraca listu clanova koji su ucitani iz baze ili praznu listu ako nema clanova.
     * 
     * @return lista elemenata klase Clan ili prazna lista
     */
    public List<Clan> getLista() {
        return lista;
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
     * Metoda koja ucitava sve clanove iz baze podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        lista = broker.vratiSve(objekat, kljuc);
    }
    
}
