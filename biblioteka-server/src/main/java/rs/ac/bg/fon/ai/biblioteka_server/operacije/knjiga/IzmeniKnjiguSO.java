package rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja vrsi izmenu odredjene knjige u bazi.
 * 
 * @author Teodora
 *
 */
public class IzmeniKnjiguSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Knjiga)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.promeni((Knjiga) objekat);
    }
    
}
