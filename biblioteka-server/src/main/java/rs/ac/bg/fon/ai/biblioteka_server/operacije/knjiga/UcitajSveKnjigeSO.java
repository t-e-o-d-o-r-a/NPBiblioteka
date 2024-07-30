package rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;
import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;

/**
 * Sistemska operacija koja vraca sve knjige koji postoje u bazi podataka.
 * Nasledjuje apstraktnu klasu ApstraktnaGenerickaOperacija i implementira njene apstraktne metode.
 * 
 * @author Teodora
 *
 */
public class UcitajSveKnjigeSO extends ApstraktnaGenerickaOperacija {

	/**
	 * Lista elemenata klase Knjiga. Bice popunjena svim knjigama koje se ucitaju iz baze.
	 * Ukoliko nema knjiga, lista ostaje prazna.
	 */
    List<Knjiga> lista;
    
    /**
     * Konstruktor koji inicijalizuje instancu operacije bez postavljanja specificne implementacije
     * brokera. Koristi default implementaciju iz klase {@link ApstraktnaGenerickaOperacija}.
     */
    public UcitajSveKnjigeSO() {
        super();
    }
    
    /**
     * Konstruktor koji omogucava postavljanje specificne implementacije brokera.
     * 
     * @param broker Instanca repozitorijuma koji se koristi za pristup bazi podataka.
     */
    public UcitajSveKnjigeSO(Repository broker) {
        super(broker);
    }
    
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
     * Metoda koja ucitava sve knjige iz baze podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        lista = broker.vratiSve((Knjiga) objekat, kljuc);
    }

    /**
     * Vraca listu knjiga koje su ucitane iz baze ili praznu listu ako nema knjiga.
     * 
     * @return lista elemenata klase Knjiga ili prazna lista
     */
    public List<Knjiga> getLista() {
        return lista;
    }
    
    
}
