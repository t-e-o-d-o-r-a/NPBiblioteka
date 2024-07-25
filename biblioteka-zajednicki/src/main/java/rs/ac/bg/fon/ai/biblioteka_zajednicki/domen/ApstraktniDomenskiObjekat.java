package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 * Interfejs kojeg implementiraju sve domenske klase.
 * 
 * @author Teodora
 */
public interface ApstraktniDomenskiObjekat extends Serializable{
    
	/**
	 * Vraca naziv tabele u bazi kojoj odgovara klasa. 
	 * 
	 * @return naziv tabele kao String
	 */
    public String vratiNazivTabele();
    
    /**
     * Vraca listu objekata odgovarajuce klase na osnovu prosledjenog ResultSet-a.
     * 
     * @param rs ResultSet dobijen iz baze iz kojeg se ucitavaju objekti date klase
     * @return lista objekata odgovarajuce klase ili prazna lista ukoliko objekti nisu pronadjeni
     * @throws java.lang.Exception ako dodje do greske prilikom ucitavanja objekata iz ResultSet-a 
     */
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception;
    
    /**
     * Vraca nazive kolona kao jedan String koji ce se koristiti prilikom kreiranja upita za ubacivanje odgovarajuceg objekta u bazu.
     *  
     * @return naziv kolona za ubacivanje kao String
     */
    public String vratiKoloneZaUbacivanje();
    
    /**
     * Vraca vrednosti atributa objekta kao jedan String koji ce se koristiti prilikom kreiranja upita za ubacivanje tog objekta u bazu.
     *  
     * @return vrednosti atributa za ubacivanje kao String
     */
    public String vratiVrednostiZaUbacivanje();
    
    /**
     * Vraca naziv kolone primarnog kljuca i vrednost primarnog kljuca odvojene znakom jednakosti kao jedan String koji ce se koristiti prilikom 
     * kreiranja SQL upita.
     * 
     * @return naziv kolone i vrednost primarnog kljuca kao String
     */
    public String vratiPrimarniKljuc();
    
    /**
     * Vraca objekat odgovarajuce klase na osnovu prosledjenog ResultSet-a.
     * 
     * @param rs ResultSet dobijen iz baze iz kojeg se ucitava objekat date klase
     * @return objekat odgovarajuce klase ili null ukoliko objekat nije pronadjen
     * @throws java.lang.Exception ako dodje do greske prilikom ucitavanja objekta iz ResultSet-a  
     */
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception;
    
    /**
     * Vraca jedan String koji predstavlja nazive kolona u bazi i vrednosti koje se u njih upisuju radi izmene odvojene znakom jednakosti.
     * Ovakav String se koristi prilikom kreiranja odgovarajuceg SQL upita za izmenu odgovarajuceg objekta u bazi.
     * 
     * @return String koji predstavlja nazive kolona i vrednosti za izmenu
     */
    public String vratiVrednostiZaIzmenu();
    
    /**
     * Za odgovarajucu domensku klasu vraca uslov po kojem se pretrazuju njeni objekti u bazi.
     * 
     * @param obj objekat odgovarajuce domenske klase koji kao atribute ima vrednosti koje se koriste kao parametri pretrage
     * @return String koji predstavlja uslov za filtriranje
     */
    public String vratiUslovZaFiltriranje(ApstraktniDomenskiObjekat obj);
    
    /**
     * Vraca String koji se koristi u SQL upitima za join-ovanje tabela. Koristi se ukoliko je tabela objeta odgovarajuce domenske klase
     * povezana sa drugim tabelama preko spoljnog kljuca.
     * 
     * @return JOIN deo SQL upita kao String
     */
    public String join();
    
    /**
     * Postavlja id objekta odgovarajuce domenske klase na prosledjenu vrednost. 
     * 
     * @param id nova vrednost id-ja objekta koja se postavlja
     */
    public void postaviId(int id);
    
}