package rs.ac.bg.fon.ai.biblioteka_server.operacije.izdavac;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;
import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;

/**
 * Sistemska operacija koja vraca sve izdavace koji postoje u bazi podataka.
 * Nasledjuje apstraktnu klasu ApstraktnaGenerickaOperacija i implementira njene apstraktne metode.
 * 
 * @author Teodora
 *
 */
public class UcitajSveIzdavaceSO extends ApstraktnaGenerickaOperacija {
    
	/**
	 * Lista elemenata klase Izdavac. Bice popunjena svim izdavacima koji se ucitaju iz baze.
	 * Ukoliko nema izdavaca, lista ostaje prazna.
	 */
    private List<Izdavac> lista;
    
    /**
     * Konstruktor koji inicijalizuje instancu operacije bez postavljanja specificne implementacije
     * brokera. Koristi default implementaciju iz klase {@link ApstraktnaGenerickaOperacija}.
     */
    public UcitajSveIzdavaceSO() {
        super();
    }
    
    /**
     * Konstruktor koji omogucava postavljanje specificne implementacije brokera.
     * 
     * @param broker Instanca repozitorijuma koji se koristi za pristup bazi podataka.
     */
    public UcitajSveIzdavaceSO(Repository broker) {
        super(broker);
    }

    /**
     * Metoda koja proverava da li je prosledjeni objekat instanca klase Izdavac.
     * 
     * @param objekat objekat koji se validira
     * @throws java.lang.Exception ukoliko prosledjeni objekat nije instanca klase Izdavac  
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Izdavac)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    /**
     * Metoda koja ucitava sve izdavace iz baze podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        lista = broker.vratiSve((Izdavac) objekat, kljuc);
    }

    /**
     * Vraca listu izdavaca koji su ucitani iz baze.
     * 
     * @return lista elemenata klase Izdavac
     */
    public List<Izdavac> getLista() {
        return lista;
    }
    
    
}
