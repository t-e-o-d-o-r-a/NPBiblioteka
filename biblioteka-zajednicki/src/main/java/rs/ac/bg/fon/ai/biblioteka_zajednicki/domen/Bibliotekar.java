package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bibliotekar implements ApstraktniDomenskiObjekat {
    
    private int bibliotekarID;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;

    public Bibliotekar() {
    }

    public Bibliotekar(int bibliotekarID, String ime, String prezime, String korisnickoIme, String lozinka) {
        this.bibliotekarID = bibliotekarID;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public int getBibliotekarID() {
        return bibliotekarID;
    }

    public void setBibliotekarID(int bibliotekarID) {
        this.bibliotekarID = bibliotekarID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    @Override
    public String toString() {
        return "Bibliotekar{" + "ime=" + ime + ", prezime=" + prezime + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

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
