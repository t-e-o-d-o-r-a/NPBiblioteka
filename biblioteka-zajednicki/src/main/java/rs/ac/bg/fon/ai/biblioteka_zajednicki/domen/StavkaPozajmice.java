package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class StavkaPozajmice implements ApstraktniDomenskiObjekat {
    
    private Pozajmica pozajmica;
    private int stavkaID;
    private Date datumDo;
    private Knjiga knjiga;

    public StavkaPozajmice() {
    }

    public StavkaPozajmice(Pozajmica pozajmica, int stavkaID, Date datumDo, Knjiga knjiga) {
        this.pozajmica = pozajmica;
        this.stavkaID = stavkaID;
        this.datumDo = datumDo;
        this.knjiga = knjiga;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    public Pozajmica getPozajmica() {
        return pozajmica;
    }

    public void setPozajmica(Pozajmica pozajmica) {
        this.pozajmica = pozajmica;
    }

    public int getStavkaID() {
        return stavkaID;
    }

    public void setStavkaID(int stavkaID) {
        this.stavkaID = stavkaID;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
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
        final StavkaPozajmice other = (StavkaPozajmice) obj;

        return Objects.equals(this.knjiga, other.knjiga);
    }

    @Override
    public String toString() {
        return "StavkaPozajmice{" + "pozajmica=" + pozajmica + ", stavkaID=" + stavkaID + ", datumDo=" + datumDo + ", knjiga=" + knjiga + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "stavkaPozajmice";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while (rs.next()) {
            // za stavku:
            int stavkaId = rs.getInt("stavkaPozajmice.stavkaID");
            Date datumDo = rs.getDate("stavkaPozajmice.datumDo");

            int pozId = rs.getInt("stavkaPozajmice.pozajmica");
            Pozajmica p = new Pozajmica();
            p.setPozajmicaID(pozId);
            
            // za knjigu:
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
            
            StavkaPozajmice stavka = new StavkaPozajmice(p, stavkaId, datumDo, k);
            lista.add(stavka);
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "pozajmica,stavkaID,datumDo,knjiga";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatiranDatum = sdf.format(datumDo);
        return pozajmica.getPozajmicaID() + "," + stavkaID + ",'" + formatiranDatum + "'," + knjiga.getKnjigaID();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "stavkaPozajmice.pozajmica=" + pozajmica.getPozajmicaID() + " AND stavkaPozajmice.stavkaID=" + stavkaID; 
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatiranDatum = sdf.format(datumDo);
        return "datumDo='" + formatiranDatum + "'";
    }

    @Override
    public String vratiUslovZaFiltriranje(ApstraktniDomenskiObjekat obj) {
        StavkaPozajmice sp = (StavkaPozajmice) obj;
        Pozajmica p = sp.getPozajmica();
        return " stavkaPozajmice.pozajmica=" + p.getPozajmicaID();
    }

    @Override
    public String join() {
        return " JOIN knjiga ON (stavkaPozajmice.knjiga = knjiga.knjigaID) JOIN pozajmica ON (stavkaPozajmice.pozajmica = pozajmica.pozajmicaID) JOIN autor ON (knjiga.autor = autor.autorID) JOIN izdavac ON (knjiga.izdavac = izdavac.izdavacID) ";
    }

    @Override
    public void postaviId(int id) {
        stavkaID = id;
    }

    
    
    
    
}
