package rs.ac.bg.fon.ai.biblioteka_server.operacije;

import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;
import rs.ac.bg.fon.ai.biblioteka_server.repository.db.DBRepository;
import rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl.DBRepositoryGeneric;

/**
 * Predstavlja apstraktnu klasu koju nasledjuju sve konkretne sistemske operacije. 
 * 
 * @author Teodora
 *
 */
public abstract class ApstraktnaGenerickaOperacija {
    
	/**
	 * Referenca na interfejs Repository koji sadrzi genericke metode za rad sa bazom podataka. 
	 */
    protected final Repository broker;

    /**
     * Konstruktor koji inicijalizuje broker na instancu klase DBRepositoryGeneric koja sadrzi
     * konkretne metode za rad sa bazom podataka.
     */
    public ApstraktnaGenerickaOperacija() {
        this.broker = new DBRepositoryGeneric();
    }
    
    /**
     * Metoda koja izvrsava celokupnu sistemsku operaciju i pravi promene u bazi ukoliko je operacija uspesno izvrsena.
     * Najpre vrsi validacija, zatim se uspostavlja konekcija, nako cega sledi izvrsenje konkretne operacije. 
     * Na kraju se potvrdjuje transakcija ukoliko nije doslo do greske, a ako jeste doslo do greske, transakcija se ponistava.
     * Nakon ovih koraka, gasi se konekcija sa bazom.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi pri izvrsavanju operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    public final void izvrsi(Object objekat, String kljuc) throws Exception {
        try {
            preduslovi(objekat);
            zapocniTransakciju();
            izvrsiOperaciju(objekat, kljuc);
            potvrdiTransakciju();            
        } catch(Exception e) {
            e.printStackTrace();
            ponistiTransakciju();
            throw e;
        } finally {
            ugasiKonekciju();
        }
    }

    /**
     * Metoda koja vrsi validaciju prosledjenih parametara neophodnu kako bi se nastavilo sa daljim izvrsavanjem operacije. 
     * Ukoliko baci gresku jer uslovi nisu ispunjeni, operacija se nece dalje izvrsavati.
     * 
     * @param objekat objekat koji se validira
     * @throws java.lang.Exception ukoliko nisu ispunjeni preduslovi  
     */
    protected abstract void preduslovi(Object objekat) throws Exception;

    /**
     * Metoda koja vrsi odgovarajucu sistemsku operaciju. U okviru nje se pozivaju operacije nad bazom podataka.
     * 
     * @param objekat objekat koji se koristi prilikom izvrsenja operacije
     * @param kljuc dodatni uslov koji se koristi za izvrsavanje operacije
     * @throws java.lang.Exception ukoliko dodje do greske prilikom izvrsenja operacije
     */
    protected abstract void izvrsiOperaciju(Object objekat, String kljuc) throws Exception;

    /**
     * Metoda koja uspostavlja konekciju sa bazom.
     * 
     * @throws java.lang.Exception ukoliko dodje do greske sa konekcijom
     */
    private void zapocniTransakciju() throws Exception {
        ((DBRepository) broker).connect();
    }

    /**
     * Metoda koja potvrdjuje promene nastale u bazi u okviru date operacije.
     * 
     * @throws java.lang.Exception ukoliko dodje do greske sa konekcijom
     */
    private void potvrdiTransakciju() throws Exception {
        ((DBRepository) broker).commit();
    }

    /**
     * Metoda koja ponistava promene u bazi koje su nastale u okviru date operacije.
     * 
     * @throws java.lang.Exception ukoliko dodje do greske sa konekcijom
     */
    private void ponistiTransakciju() throws Exception {
        ((DBRepository) broker).rollback();
    }

    /**
     * Metoda koja prekida konekciju sa bazom.
     * 
     * @throws java.lang.Exception ukoliko dodje do greske sa konekcijom
     */
    private void ugasiKonekciju() throws Exception {
        ((DBRepository) broker).disconnect();
    }
    
}
