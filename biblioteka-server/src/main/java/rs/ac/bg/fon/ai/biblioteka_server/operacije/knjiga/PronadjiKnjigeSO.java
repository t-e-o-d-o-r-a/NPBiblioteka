package rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja pretrazuje knjige u bazi po odredjenim parametrima pretrage.
 * 
 * @author Teodora
 *
 */
public class PronadjiKnjigeSO extends ApstraktnaGenerickaOperacija {
    
	/**
	 * Lista elemenata klase Knjiga. Bice popunjena pronadjenim knjigama koji se ucitaju iz baze.
	 * Ukoliko nema knjiga koje odgovaraju datim parametrima pretrage, lista ostaje prazna.
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
        lista = broker.filter((Knjiga) objekat);
    }

    /**
     * Vraca listu knjiga koje su pronadjene i ucitane iz baze ili praznu listu ako nema trazenih knjiga.
     * 
     * @return lista elemenata klase Knjiga ili prazna lista
     */
    public List<Knjiga> getLista() {
        return lista;
    }
    
    
}
