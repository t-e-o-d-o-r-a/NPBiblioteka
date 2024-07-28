package rs.ac.bg.fon.ai.biblioteka_server.operacije.login;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja prijavljuje bibliotekara na sistem.
 * Proverava da li dati bibliotekar postoji registrovan u bazi podataka.
 * Nasledjuje apstraktnu klasu ApstraktnaGenerickaOperacija i implementira njene apstraktne metode.
 * 
 * @author Teodora
 *
 */
public class LoginSO extends ApstraktnaGenerickaOperacija{
    
	/**
	 * Bibliotekar koji se prijavio na sistem.
	 * Ukoliko trazeni bibliotekar ne postoji, bice jednak null.
	 */
    private Bibliotekar bibliotekar;

    /**
     * Metoda koja proverava da li je prosledjeni objekat instanca klase Bibliotekar.
     * 
     * @param objekat objekat koji se validira
     * @throws java.lang.Exception ukoliko prosledjeni objekat nije instanca klase Bibliotekar  
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Bibliotekar)) {
            throw new Exception("Neispravan parametar.");
        }
    }

    /**
     * Metoda koja proverava da li dati bibliotekar postoji u bazi podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        List<Bibliotekar> lista = broker.vratiSve((Bibliotekar) objekat, kljuc);
        System.out.println("Klasa LoginSO, svi bibliotekari: " + lista);
        
        for (Bibliotekar b : lista) {
            if (b.equals((Bibliotekar) objekat)) {
                bibliotekar = b;
                return;
            }
        }
        
        bibliotekar = null;
    }

    /**
     * Vraca prijavljenog bibliotekara ukoliko je prijava uspesna, a ukoliko nije, vraca null.
     * 
     * @return prijavljeni bibliotekar kao instanca klase Bibliotekar ili null ako dati bibliotekar ne postoji u bazi
     */
    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }
    
}
