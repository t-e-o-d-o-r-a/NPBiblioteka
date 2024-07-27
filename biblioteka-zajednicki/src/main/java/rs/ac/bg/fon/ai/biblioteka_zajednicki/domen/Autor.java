package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja autora knjige.
 * Nasledjuje apstraktnu klasu ApstraktniDomenskiObjekat i implementira njene metode.
 * 
 * Autor ima ime, prezime i svoj ID.
 * 
 * @author Teodora
 *
 */
public class Autor extends ApstraktniDomenskiObjekat {
    
	/**
	 * ID autora kao int.
	 */
    private int autorID;
    
    /**
     * Ime autora kao String.
     */
    private String ime;
    
    /**
     * Prezime autora kao String.
     */
    private String prezime;

    /**
     * Pravi novi objekat klase Autor.
     * 
     * Ime, prezime i autorID ostaju neinicijalzovani.
     */
    public Autor() {
    }

    /**
     * Pravi novi objekat klase Autor i postavlja autorID, ime i prezime na unete vrednosti.
     * 
     * @param autorID ID autora kao int
     * @param ime ime autora kao String
     * @param prezime prezime autora kao String
     */
    public Autor(int autorID, String ime, String prezime) {
        this.setAutorID(autorID);
        this.setIme(ime);
        this.setPrezime(prezime);
    }

    /**
     * Vraca prezime autora.
     * 
     * @return prezime autora kao String
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime autora na unetu vrednost.
     * 
     * Uneto prezime ne sme da bude null niti prazan String.
     * 
     * @param prezime prezime autora kao String
     * 
     * @throws java.lang.NullPointerException ako je uneto prezime null
	 * @throws java.lang.IllegalArgumentException ako je uneto prezime prazan String
     */
    public void setPrezime(String prezime) {
    	if (prezime == null) 
			throw new NullPointerException("Prezime ne sme biti null.");
		if (prezime.isEmpty())
			throw new IllegalArgumentException("Prezime ne sme biti prazno.");
    	
        this.prezime = prezime;
    }

    /**
     * Vraca ID autora.
     * 
     * @return ID autora kao int
     */
    public int getAutorID() {
        return autorID;
    }

    /**
     * Postavlja ID autora na unetu vrednost.
     * 
     * Uneti ID ne sme biti manji od 0.
     *  
     * @param autorID ID autora kao int
     * 
     * @throws java.lang.IllegalArgumentException ako je uneti ID manji od 0
     */
    public void setAutorID(int autorID) {
    	if (autorID < 0)
			throw new IllegalArgumentException("ID ne sme da bude manji od 0.");
    	
        this.autorID = autorID;
    }

    /**
     * Vraca ime autora.
     * 
     * @return ime autora kao String
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime autora na unetu vrednost.
     * 
     * Uneto ime ne sme da bude null niti prazan String.
     * 
     * @param ime ime autora kao String
     * 
     * @throws java.lang.NullPointerException ako je uneto ime null
	 * @throws java.lang.IllegalArgumentException ako je uneto ime prazan String
     */
    public void setIme(String ime) {
    	if (ime == null) 
			throw new NullPointerException("Ime ne sme biti null.");
		if (ime.isEmpty())
			throw new IllegalArgumentException("Ime ne sme biti prazno.");
    	
        this.ime = ime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /**
     * Poredi dva autora prema ID-ju, imenu i prezimenu.
     * 
     * @param obj drugi autor sa kojim se poredi
     * 
     * @return 
     * <ul>
	 * 	<li><b>true</b> - ako su oba objekta inicijalizovana, klase su Autor imaju isto ime, prezime i autorID</li>
	 * 	<li><b>false</b> - ako nisu klase Autor, ako je uneti autor null ili ako nije isto ime ili prezime ili autorID</li>
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
        final Autor other = (Autor) obj;
        if (this.autorID != other.autorID) {
            return false;
        }
        if (!Objects.equals(this.ime, other.ime)) {
            return false;
        }
        return Objects.equals(this.prezime, other.prezime);
    }

    /**
     * Vraca ime i prezime autora kao String.
     * 
     * @return String koji sadrzi ime i prezime autora
     */
    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String vratiNazivTabele() {
        return "autor";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while (rs.next()) {
            int aid = rs.getInt("autor.autorID");
            String ime = rs.getString("autor.ime");
            String prezime = rs.getString("autor.prezime");
            
            Autor a = new Autor(aid, ime, prezime);
            lista.add(a);
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
        return "autor.autorID=" + autorID;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        ApstraktniDomenskiObjekat obj = null;
        if (rs.next()) {
            int id = rs.getInt("autor.autorID");
            String ime = rs.getString("autor.ime");
            String prezime = rs.getString("autor.prezime");
            
            obj = new Autor(id, ime, prezime);
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