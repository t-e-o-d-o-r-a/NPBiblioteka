package rs.ac.bg.fon.ai.biblioteka_server.operacije.pozajmica;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.StavkaPozajmice;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

/**
 * Sistemska operacija koja vrsi izmenu odredjene pozajmice i njenih stavki u bazi.
 * Za svaku vracenu knjigu se povecava broj raspolozivih primeraka u bazi.
 * Nasledjuje apstraktnu klasu ApstraktnaGenerickaOperacija i implementira njene apstraktne metode.
 * 
 * @author Teodora
 *
 */
public class IzmeniPozajmicuSO extends ApstraktnaGenerickaOperacija {

	/**
     * Metoda koja proverava da li je prosledjeni objekat instanca klase Pozajmica.
     * 
     * @param objekat objekat koji se validira
     * @throws java.lang.Exception ukoliko prosledjeni objekat nije instanca klase Pozajmica  
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Pozajmica)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    /**
     * Metoda koja menja podatke odredjene pozajmice i njenih stavki u bazi podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Pozajmica p = (Pozajmica) objekat;
        broker.promeni((Pozajmica) p);
        List<StavkaPozajmice> zaBrisanje = p.getStavke();
        for (StavkaPozajmice sp : zaBrisanje) {
            broker.obrisi((StavkaPozajmice) sp);
            Knjiga k = sp.getKnjiga();
            k.setBrojPrimeraka(k.getBrojPrimeraka() + 1);
            broker.promeni((Knjiga) k);
        }
    }
    
}