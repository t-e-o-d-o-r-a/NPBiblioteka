package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clan implements ApstraktniDomenskiObjekat {
    
    private int clanID;
    private String ime;
    private String prezime;
    private String brojTelefona;

    public Clan() {
    }

    public Clan(int clanID, String ime, String prezime, String brojTelefona) {
        this.clanID = clanID;
        this.ime = ime;
        this.prezime = prezime;
        this.brojTelefona = brojTelefona;
    }

    public int getClanID() {
        return clanID;
    }

    public void setClanID(int clanID) {
        this.clanID = clanID;
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

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
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
        final Clan other = (Clan) obj;
        if (!Objects.equals(this.ime, other.ime)) {
            return false;
        }
        if (!Objects.equals(this.prezime, other.prezime)) {
            return false;
        }
        return Objects.equals(this.brojTelefona, other.brojTelefona);
    }

    @Override
    public String toString() {
        return "Clan{" + "ime=" + ime + ", prezime=" + prezime + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "clan";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while (rs.next()) {
            int cid = rs.getInt("clan.clanID");
            String ime = rs.getString("clan.ime");
            String prezime = rs.getString("clan.prezime");
            String brTel = rs.getString("clan.brojTelefona");
            
            Clan c = new Clan(cid, ime, prezime, brTel);
            lista.add(c);
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime,prezime,brojTelefona";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + ime + "','" + prezime + "','" + brojTelefona + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "clan.clanID=" + clanID;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        ApstraktniDomenskiObjekat obj = null;
        if (rs.next()) {
            int cid = rs.getInt("clan.clanID");
            String ime = rs.getString("clan.ime");
            String prezime = rs.getString("clan.prezime");
            String brTel = rs.getString("clan.brojTelefona");
            
            obj = new Clan(cid, ime, prezime, brTel);
        }
        
        return obj;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ime='" + ime + "', prezime='" + prezime + "', brojTelefona='" + brojTelefona + "'";
    }

    @Override
    public String vratiUslovZaFiltriranje(ApstraktniDomenskiObjekat obj) {
        Clan clan = (Clan) obj;
        return "clan.ime LIKE '%" + clan.getIme() + "%'";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public void postaviId(int id) {
        clanID = id;
    }
   
}
