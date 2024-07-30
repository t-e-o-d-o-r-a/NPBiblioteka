package rs.ac.bg.fon.ai.biblioteka_server.operacije.autor;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;
import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;

/**
 * Sistemska operacija koja vraca sve autore koji postoje u bazi podataka.
 * Nasledjuje apstraktnu klasu ApstraktnaGenerickaOperacija i implementira njene apstraktne metode.
 * 
 * @author Teodora
 *
 */
public class UcitajSveAutoreSO extends ApstraktnaGenerickaOperacija {
    
	/**
	 * Lista elemenata klase Autor. Bice popunjena svim autorima koji se ucitaju iz baze.
	 * Ukoliko nema autora, lista ostaje prazna.
	 */
    private List<Autor> lista;
    
    /**
     * Konstruktor koji inicijalizuje instancu operacije bez postavljanja specificne implementacije
     * brokera. Koristi default implementaciju iz klase {@link ApstraktnaGenerickaOperacija}.
     */
    public UcitajSveAutoreSO() {
        super();
    }
    
    /**
     * Konstruktor koji omogucava postavljanje specificne implementacije brokera.
     * 
     * @param broker Instanca repozitorijuma koji se koristi za pristup bazi podataka.
     */
    public UcitajSveAutoreSO(Repository broker) {
        super(broker);
    }

    /**
     * Metoda koja proverava da li je prosledjeni objekat instanca klase Autor.
     * 
     * @param objekat objekat koji se validira
     * @throws java.lang.Exception ukoliko prosledjeni objekat nije instanca klase Autor  
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Autor)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    /**
     * Metoda koja ucitava sve autore iz baze podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        lista = broker.vratiSve((Autor) objekat, kljuc);
    }

    /**
     * Vraca listu autora koji su ucitani iz baze.
     * 
     * @return lista elemenata klase Autor
     */
    public List<Autor> getLista() {
        return lista;
    }
    
    
}
