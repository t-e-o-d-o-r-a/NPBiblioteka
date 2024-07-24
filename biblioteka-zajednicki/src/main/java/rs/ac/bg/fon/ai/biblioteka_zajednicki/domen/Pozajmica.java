package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Pozajmica implements ApstraktniDomenskiObjekat {
    
    private int pozajmicaID;
    private Date datum;
    private Clan clan;
    private Bibliotekar bibliotekar;
    private List<StavkaPozajmice> stavke;
    private String napomena;

    public Pozajmica() {
    }

    public Pozajmica(int pozajmicaID, Date datum, Clan clan, Bibliotekar bibliotekar, List<StavkaPozajmice> stavke, String napomena) {
        this.pozajmicaID = pozajmicaID;
        this.datum = datum;
        this.clan = clan;
        this.bibliotekar = bibliotekar;
        this.stavke = stavke;
        this.napomena = napomena;
    }

    public List<StavkaPozajmice> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaPozajmice> stavke) {
        this.stavke = stavke;
    }

    public int getPozajmicaID() {
        return pozajmicaID;
    }

    public void setPozajmicaID(int pozajmicaID) {
        this.pozajmicaID = pozajmicaID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }

    public void setBibliotekar(Bibliotekar bibliotekar) {
        this.bibliotekar = bibliotekar;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Pozajmica other = (Pozajmica) obj;
        if (this.pozajmicaID != other.pozajmicaID) {
            return false;
        }
        if (!Objects.equals(this.clan, other.clan)) {
            return false;
        }
        return Objects.equals(this.bibliotekar, other.bibliotekar);
    }

    @Override
    public String toString() {
        return "Pozajmica{" + "datum=" + datum + ", clan=" + clan + ", bibliotekar=" + bibliotekar + ", stavke=" + stavke + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "pozajmica";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while (rs.next()) {
            // za clana:
            int clanId = rs.getInt("clan.clanID");
            String imeClana = rs.getString("clan.ime");
            String prezimeClana = rs.getString("clan.prezime");
            String brTel = rs.getString("clan.brojTelefona");
            Clan c = new Clan(clanId, imeClana, prezimeClana, brTel);
            
            // za bibliotekara:
            int bibId = rs.getInt("bibliotekar.bibliotekarID");
            String imeBib = rs.getString("bibliotekar.ime");
            String prezimeBib = rs.getString("bibliotekar.prezime");
            String username = rs.getString("bibliotekar.korisnickoIme");
            Bibliotekar b = new Bibliotekar(bibId, imeBib, prezimeBib, username, null);
            
            // za pozajmicu:
            int pozId = rs.getInt("pozajmica.pozajmicaID");
            Date datum = rs.getDate("pozajmica.datum");
            String napomena = rs.getString("pozajmica.napomena");
            Pozajmica p = new Pozajmica(pozId, datum, c, b, new ArrayList<>(), napomena);
            
            lista.add(p);
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "datum,clan,bibliotekar,napomena";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatiranDatum = sdf.format(datum);
        return "'" + formatiranDatum + "'," + clan.getClanID() + "," + bibliotekar.getBibliotekarID() + ",'" + napomena + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "pozajmica.pozajmicaID=" + pozajmicaID;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        ApstraktniDomenskiObjekat obj = null;
        
        if (rs.next()) {
            // za clana:
            int clanId = rs.getInt("clan.clanID");
            String imeClana = rs.getString("clan.ime");
            String prezimeClana = rs.getString("clan.prezime");
            String brTel = rs.getString("clan.brojTelefona");
            Clan c = new Clan(clanId, imeClana, prezimeClana, brTel);
            
            // za bibliotekara:
            int bibId = rs.getInt("bibliotekar.bibliotekarID");
            String imeBib = rs.getString("bibliotekar.ime");
            String prezimeBib = rs.getString("bibliotekar.prezime");
            String username = rs.getString("bibliotekar.korisnickoIme");
            Bibliotekar b = new Bibliotekar(bibId, imeBib, prezimeBib, username, null);
            
            // za pozajmicu:
            int pozId = rs.getInt("pozajmica.pozajmicaID");
            Date datum = rs.getDate("pozajmica.datum");
            String napomena = rs.getString("pozajmica.napomena");
            obj = new Pozajmica(pozId, datum, c, b, new ArrayList<>(), napomena);
        }
        
        return obj;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "datum='" + datum + "', clan=" + clan.getClanID() + ", bibliotekar=" + bibliotekar.getBibliotekarID() + ", napomena='" + napomena + "'";
    }

    @Override
    public String vratiUslovZaFiltriranje(ApstraktniDomenskiObjekat obj) {
        Pozajmica p = (Pozajmica) obj;
        Clan c = p.getClan();
        return "pozajmica.clan=" + c.getClanID();
    }

    @Override
    public String join() {
        return " JOIN clan ON (clan.clanID = pozajmica.clan) JOIN bibliotekar ON (bibliotekar.bibliotekarID = pozajmica.bibliotekar) ";
    }

    @Override
    public void postaviId(int id) {
        pozajmicaID = id;
    }
    
    
    
}
