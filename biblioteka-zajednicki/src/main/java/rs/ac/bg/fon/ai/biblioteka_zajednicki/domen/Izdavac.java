package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja izdavaca knjige.
 * Nasledjuje apstraktnu klasu ApstraktniDomenskiObjekat i implementira njene metode.
 * 
 * Izdavac ima svoj ID i naziv.
 * 
 * @author Teodora
 *
 */
public class Izdavac extends ApstraktniDomenskiObjekat {
    
	/**
	 * ID izdavaca kao int.
	 */
    private int izdavacID;
    
    /**
     * Naziv izdavaca kao String.
     */
    private String naziv;

    /**
     * Pravi novi objekat klase Izdavac.
     * 
     * Naziv i izdavacID ostaju neinicijalizovani.
     */
    public Izdavac() {
    }

    /**
     * Pravi novi objekat klase Izdavac i postavlja izdavacID i naziv na unete vrednosti.
     * 
     * @param izdavacID ID izdavaca kao int
     * @param naziv naziv izdavaca kao String
     */
    public Izdavac(int izdavacID, String naziv) {
        this.setIzdavacID(izdavacID);
        this.setNaziv(naziv);
    }

    /**
     * Vraca naziv izdavaca.
     *  
     * @return naziv izdavaca kao String
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv izdavaca na unetu vrednost.
     * 
     * Uneti naziv ne sme da bude null niti prazan String.
     * 
     * @param naziv naziv izdavaca kao String
     * 
     * @throws java.lang.NullPointerException ako je uneti naziv null
	 * @throws java.lang.IllegalArgumentException ako je uneti naziv prazan String
     */
    public void setNaziv(String naziv) {
    	if (naziv == null) 
			throw new NullPointerException("Naziv ne sme biti null.");
		if (naziv.isEmpty())
			throw new IllegalArgumentException("Naziv ne sme biti prazan.");
    	
        this.naziv = naziv;
    }

    /**
     * Vraca ID izdavaca.
     *  
     * @return ID izdavaca kao int
     */
    public int getIzdavacID() {
        return izdavacID;
    }

    /**
     * Postavlja ID izdavaca na unetu vrednost.
     * 
     * Uneti ID ne sme biti manji od 0.
     * 
     * @param izdavacID ID izdavaca kao int
     * 
     * @throws java.lang.IllegalArgumentException ako je uneti ID manji od 0
     */
    public void setIzdavacID(int izdavacID) {
    	if (izdavacID < 0)
			throw new IllegalArgumentException("ID ne sme da bude manji od 0.");
    	
        this.izdavacID = izdavacID;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /**
     * Poredi dva izdavaca prema ID-ju i nazivu.
     * 
     * @param obj drugi izdavac sa kojim se poredi
     * 
     * @return 
     * <ul>
	 * 	<li><b>true</b> - ako su oba objekta inicijalizovana, klase su Izdavac, imaju isti naziv i izdavacID</li>
	 * 	<li><b>false</b> - ako nisu klase Izdavac, ako je uneti izdavac null ili ako nije isti naziv ili izdavacID</li>
	 * </ul>
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Izdavac other = (Izdavac) obj;
        if (this.izdavacID != other.izdavacID) {
            return false;
        }
        return Objects.equals(this.naziv, other.naziv);
    }

    /**
     * Vraca naziv izdavaca.
     * 
     * @return String koji predstavlja naziv izdavaca
     */
    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String vratiNazivTabele() {
        return "izdavac";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while (rs.next()) {
            int iid = rs.getInt("izdavac.izdavacID");
            String naziv = rs.getString("izdavac.naziv");
            
            Izdavac i = new Izdavac(iid, naziv);
            lista.add(i);
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "izdavac.izdavacID=" + izdavacID;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        ApstraktniDomenskiObjekat obj = null;
        
        if (rs.next()) {
            int iid = rs.getInt("izdavac.izdavacID");
            String naziv = rs.getString("izdavac.naziv");
            
            obj = new Izdavac(iid, naziv);
        }
        
        return obj;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiUslovZaFiltriranje(ApstraktniDomenskiObjekat obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public void postaviId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
