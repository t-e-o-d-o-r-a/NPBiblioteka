package rs.ac.bg.fon.ai.biblioteka_server.operacije.izdavac;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja vraca sve izdavace koji postoje u bazi podataka.
 * 
 * @author Teodora
 *
 */
public class UcitajSveIzdavaceSO extends ApstraktnaGenerickaOperacija {
    
	/**
	 * Lista elemenata klase Izdavac. Bice popunjena svim izdavacima koji se ucitaju iz baze.
	 * Ukoliko nema izdavaca, lista ostaje prazna.
	 */
    List<Izdavac> lista;

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Izdavac)) {
            throw new Exception("Neispravni parametar.");
        }
    }

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
