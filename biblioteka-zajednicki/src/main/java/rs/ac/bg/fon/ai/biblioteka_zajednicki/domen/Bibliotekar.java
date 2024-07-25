package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja bibliotekara koji se prijavljuje na sistem i koristi softver za evidenciju rada biblioteke.
 * Implementira interfejs ApstraktniDomenskiObjekat.
 * 
 * Bibliotekar ima ID, ime, prezime, kao i korisnicko ime i lozinku pomocu kojih se prijavljuje na sistem.
 * 
 * @author Teodora
 *
 */
public class Bibliotekar implements ApstraktniDomenskiObjekat {
    
	/**
	 * ID bibliotekara kao int.
	 */
    private int bibliotekarID;
    
    /**
     * Ime bibliotekara kao String.
     */
    private String ime;
    
    /**
     * Prezime bibliotekara kao String.
     */
    private String prezime;
    
    /**
     * Korisnicko ime bibliotekara kao String.
     */
    private String korisnickoIme;
    
    /**
     * Lozinka bibliotekara kao String.
     */
    private String lozinka;

    /**
     * Pravi novi objekat klase Bibliotekar.
     * 
     * Ime, prezime, korisnickoIme, lozinka i bibliotekarID ostaju neinicijalizovani.
     */
    public Bibliotekar() {
    }

    /**
     * Pravi novi objekat klase Bibliotekar i postavlja bibliotekarID, ime, prezime, korisnickoIme i lozinku na unete vrednosti.
     * 
     * @param bibliotekarID ID bibliotekara kao int
     * @param ime ime bibliotekara kao String
     * @param prezime prezime bibliotekara kao String
     * @param korisnickoIme korisnicko ime bibliotekara kao String
     * @param lozinka lozinka bibliotekara kao String
     */
    public Bibliotekar(int bibliotekarID, String ime, String prezime, String korisnickoIme, String lozinka) {
        this.bibliotekarID = bibliotekarID;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    /**
     * Vraca ID bibliotekara.
     * 
     * @return ID bibliotekara kao int
     */
    public int getBibliotekarID() {
        return bibliotekarID;
    }

    /**
     * Postavlja ID bibliotekara na unetu vrednost.
     * 
     * @param bibliotekarID ID bibliotekara kao int
     */
    public void setBibliotekarID(int bibliotekarID) {
        this.bibliotekarID = bibliotekarID;
    }

    /**
     * Vraca ime bibliotekara.
     * 
     * @return ime bibliotekara kao String
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime bibliotekara na unetu vrednost.
     * 
     * @param ime ime bibliotekara kao String
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * Vraca prezime bibliotekara.
     * 
     * @return prezime bibliotekara kao String
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime bibliotekara na unetu vrednost.
     * 
     * @param prezime prezime bibliotekara kao String
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * Vraca korisnicko ime bibliotekara.
     * 
     * @return korisnicko ime bibliotekara kao String
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Postavlja korisnicko ime bibliotekara na unetu vrednost.
     * 
     * @param korisnickoIme korisnicko ime bibliotekara kao String
     */
    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    /**
     * Vraca lozinku bibliotekara.
     * 
     * @return lozinka bibliotekara kao String
     */
    public String getLozinka() {
        return lozinka;
    }

    /**
     * Postavlja lozinku bibliotekara na unetu vrednost.
     * 
     * @param lozinka lozinka bibliotekara kao String
     */
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    /**
     * Vraca podatke o bibliotekaru koji sadrze njegovo ime i prezime.
     * 
     * @return String koji sadrzi ime i prezime bibliotekara
     */
    @Override
    public String toString() {
        return "Bibliotekar{" + "ime=" + ime + ", prezime=" + prezime + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Poredi dva bibliotekara prema korisnickom imenu i lozinki.
     * 
     * @param obj drugi bibliotekar sa kojim se poredi
     * 
     * @return 
     * <ul>
	 * 	<li><b>true</b> - ako su oba objekta inicijalizovana, klase su Bibliotekar, imaju isto korisnicko ime i lozinku</li>
	 * 	<li><b>false</b> - ako nisu klase Bibliotekar, ako je uneti bibliotekar null ili ako nije isto korisnicko ime ili lozinka</li>
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
        final Bibliotekar other = (Bibliotekar) obj;
        if (!Objects.equals(this.korisnickoIme, other.korisnickoIme)) {
            return false;
        }
        return Objects.equals(this.lozinka, other.lozinka);
    }

    @Override
    public String vratiNazivTabele() {
        return "bibliotekar";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while (rs.next()) {
            int bid = rs.getInt("bibliotekar.bibliotekarID");
            String ime = rs.getString("bibliotekar.ime");
            String prezime = rs.getString("bibliotekar.prezime");
            String korisnickoIme = rs.getString("bibliotekar.korisnickoIme");
            String lozinka = rs.getString("bibliotekar.lozinka");
            
            Bibliotekar b = new Bibliotekar(bid, ime, prezime, korisnickoIme, lozinka);
            lista.add(b);
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime,prezime,korisnickoIme,lozinka";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + ime + "','" + prezime + "','" + korisnickoIme + "','" + lozinka + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "bibliotekar.bibliotekarID=" + bibliotekarID;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        ApstraktniDomenskiObjekat obj = null;
        if (rs.next()) {
            int bid = rs.getInt("bibliotekar.bibliotekarID");
            String ime = rs.getString("bibliotekar.ime");
            String prezime = rs.getString("bibliotekar.prezime");
            String korisnickoIme = rs.getString("bibliotekar.korisnickoIme");
            String lozinka = rs.getString("bibliotekar.lozinka");
            
            obj = new Bibliotekar(bid, ime, prezime, korisnickoIme, lozinka);
        }
        
        return obj;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ime='" + ime + "', prezime='" + prezime + "', korisnickoIme='" + korisnickoIme + "', lozinka='" + lozinka + "'";
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
