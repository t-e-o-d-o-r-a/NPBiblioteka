package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Knjiga implements ApstraktniDomenskiObjekat {
    
    private int knjigaID;
    private String naslov;
    private int brojPrimeraka;
    private int godinaIzdanja;
    private Autor autor;
    private Izdavac izdavac;
    private Format format;

    public Knjiga() {
    }

    public Knjiga(int knjigaID, String naslov, int brojPrimeraka, int godinaIzdanja, Autor autor, Izdavac izdavac, Format format) {
        this.knjigaID = knjigaID;
        this.naslov = naslov;
        this.brojPrimeraka = brojPrimeraka;
        this.godinaIzdanja = godinaIzdanja;
        this.autor = autor;
        this.izdavac = izdavac;
        this.format = format;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public int getKnjigaID() {
        return knjigaID;
    }

    public void setKnjigaID(int knjigaID) {
        this.knjigaID = knjigaID;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getBrojPrimeraka() {
        return brojPrimeraka;
    }

    public void setBrojPrimeraka(int brojPrimeraka) {
        this.brojPrimeraka = brojPrimeraka;
    }

    public int getGodinaIzdanja() {
        return godinaIzdanja;
    }

    public void setGodinaIzdanja(int godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Izdavac getIzdavac() {
        return izdavac;
    }

    public void setIzdavac(Izdavac izdavac) {
        this.izdavac = izdavac;
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
        final Knjiga other = (Knjiga) obj;
        if (this.godinaIzdanja != other.godinaIzdanja) {
            return false;
        }
        if (!Objects.equals(this.naslov, other.naslov)) {
            return false;
        }
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        if (!Objects.equals(this.izdavac, other.izdavac)) {
            return false;
        }
        return this.format == other.format;
    }

    

    

    @Override
    public String toString() {
        return "Knjiga{" + "naslov=" + naslov + ", godinaIzdanja=" + godinaIzdanja + ", autor=" + autor + ", izdavac=" + izdavac + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "knjiga";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while (rs.next()) {
            int kid = rs.getInt("knjiga.knjigaID");
            String naslov = rs.getString("knjiga.naslov");
            int brPrimeraka = rs.getInt("knjiga.brojPrimeraka");
            int god = rs.getInt("knjiga.godinaIzdanja");
            Format f = Format.valueOf(rs.getString("knjiga.format"));
            
            int iid = rs.getInt("izdavac.izdavacID");
            String nazivIzdavaca = rs.getString("izdavac.naziv");
            Izdavac i = new Izdavac(iid, nazivIzdavaca);
            
            int aid = rs.getInt("knjiga.autor");
            String ime = rs.getString("autor.ime");
            String prezime = rs.getString("autor.prezime");
            Autor a = new Autor(aid, ime, prezime);
            
            Knjiga k = new Knjiga(kid, naslov, brPrimeraka, god, a, i, f);
            lista.add(k);
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naslov,brojPrimeraka,godinaIzdanja,izdavac,format,autor";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + naslov + "'," + brojPrimeraka + "," + godinaIzdanja + "," + izdavac.getIzdavacID() + 
                ",'" + format.name() + "'," + autor.getAutorID();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "knjiga.knjigaID=" + knjigaID;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        ApstraktniDomenskiObjekat obj = null;
        
        if (rs.next()) {
            int kid = rs.getInt("knjiga.knjigaID");
            String naslov = rs.getString("knjiga.naslov");
            int brPrimeraka = rs.getInt("knjiga.brojPrimeraka");
            int god = rs.getInt("knjiga.godinaIzdanja");
            Format f = Format.valueOf(rs.getString("knjiga.format"));
            
            int iid = rs.getInt("izdavac.izdavacID");
            String nazivIzdavaca = rs.getString("izdavac.naziv");
            Izdavac i = new Izdavac(iid, nazivIzdavaca);
            
            int aid = rs.getInt("knjiga.autor");
            String ime = rs.getString("autor.ime");
            String prezime = rs.getString("autor.prezime");
            Autor a = new Autor(aid, ime, prezime);
            
            obj = new Knjiga(kid, naslov, brPrimeraka, god, a, i, f);
        }
        
        return obj;
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "naslov='" + naslov + "', brojPrimeraka=" + brojPrimeraka + ", godinaIzdanja=" + godinaIzdanja + ", izdavac=" + izdavac.getIzdavacID() + 
                    ", format='" + format.name() + "', autor=" + autor.getAutorID();
    }

    @Override
    public String vratiUslovZaFiltriranje(ApstraktniDomenskiObjekat obj) {
        Knjiga knjiga = (Knjiga) obj;
        return "(knjiga.naslov LIKE '%" + knjiga.getNaslov() + "%' OR knjiga.autor=" + knjiga.getAutor().getAutorID() + ")";
    }

    @Override
    public String join() {
        return " JOIN autor ON (knjiga.autor = autor.autorID) JOIN izdavac ON (knjiga.izdavac = izdavac.izdavacID) ";
    }

    @Override
    public void postaviId(int id) {
        knjigaID = id;
    }
    
    
    
}

