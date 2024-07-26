package rs.ac.bg.fon.ai.biblioteka_server.operacije.autor;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja vraca sve autore koji postoje u bazi podataka.
 * 
 * @author Teodora
 *
 */
public class UcitajSveAutoreSO extends ApstraktnaGenerickaOperacija {
    
	/**
	 * Lista elemenata klase Autor. Bice popunjena svim autorima koji se ucitaju iz baze.
	 * Ukoliko nema autora, lista ostaje prazna.
	 */
    List<Autor> lista;

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Autor)) {
            throw new Exception("Neispravni parametar.");
        }
    }

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
