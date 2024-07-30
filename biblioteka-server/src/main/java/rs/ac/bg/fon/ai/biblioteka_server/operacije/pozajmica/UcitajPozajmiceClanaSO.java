package rs.ac.bg.fon.ai.biblioteka_server.operacije.pozajmica;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.StavkaPozajmice;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;
import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;

/**
 * Sistemska operacija koja ucitava sve pozajmice i njihove stavke za odredjenog clana iz baze podataka.
 * 
 * @author Teodora
 *
 */
public class UcitajPozajmiceClanaSO extends ApstraktnaGenerickaOperacija {
    
	/**
	 * Lista elemenata klase Pozajmica. Bice popunjena svim pozajmicama datog clana koje se ucitaju iz baze.
	 * Ukoliko nema pozajmica, lista ostaje prazna.
	 */
    List<Pozajmica> pozajmice;
    
    /**
     * Konstruktor koji inicijalizuje instancu operacije bez postavljanja specificne implementacije
     * brokera. Koristi default implementaciju iz klase {@link ApstraktnaGenerickaOperacija}.
     */
    public UcitajPozajmiceClanaSO() {
        super();
    }
    
    /**
     * Konstruktor koji omogucava postavljanje specificne implementacije brokera.
     * 
     * @param broker Instanca repozitorijuma koji se koristi za pristup bazi podataka.
     */
    public UcitajPozajmiceClanaSO(Repository broker) {
        super(broker);
    }

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
     * Metoda koja ucitava sve pozajmice odredjenog clana iz baze podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Pozajmica p = (Pozajmica) objekat;
        pozajmice = broker.filter(p);
        
        for (Pozajmica pozajmica : pozajmice) {
            StavkaPozajmice sp = new StavkaPozajmice();
            sp.setPozajmica(pozajmica);
            List<StavkaPozajmice> stavke = broker.filter((StavkaPozajmice) sp);
            pozajmica.setStavke(stavke);
        }
    }

    /**
     * Vraca listu pozajmica datog clana koje su ucitane iz baze ili praznu listu ako nema pozajmica.
     * 
     * @return lista elemenata klase Pozajmica ili prazna lista
     */
    public List<Pozajmica> getPozajmice() {
        return pozajmice;
    }
    
    
}
