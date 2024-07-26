package rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja vraca sve knjige koji postoje u bazi podataka.
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
    
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Knjiga)) {
            throw new Exception("Neispravni parametar.");
        }
    }

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
