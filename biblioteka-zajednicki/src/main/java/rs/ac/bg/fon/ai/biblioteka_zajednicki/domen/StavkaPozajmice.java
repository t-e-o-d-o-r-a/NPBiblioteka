package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja jednu stavku pozajmice. Jedna stavka obuhvata jednu knjigu koja je pozajmljena.
 * Implementira interfejs ApstraktniDomenskiObjekat.
 * 
 * Stavka pozajmice ime svoj ID, pozajmicu kojoj pripada, datum do kojeg treba vratiti knjigu i knjigu koja se pozajmljuje.
 * 
 * @author Teodora
 *
 */
public class StavkaPozajmice implements ApstraktniDomenskiObjekat {
    
	/**
	 * Pozajmica kojoj pripada stavka kao objekat klase Pozajmica.
	 */
    private Pozajmica pozajmica;
    
    /**
     * ID stavke pozajmice kao int.
     */
    private int stavkaID;
    
    /**
     * Datum do kojeg treba vratiti knjigu kao objekat klase Date.
     */
    private Date datumDo;
    
    /**
     * Knjiga koja se pozajmljuje u okviru pozajmice kao objekat klase Knjiga.
     */
    private Knjiga knjiga;

    /**
     * Pravi novi objekat klase StavkaPozajmice.
     * 
     * Pozajmica, stavkaID, datumDo i knjiga ostaju neinicijalizovani.
     */
    public StavkaPozajmice() {
    }

    /**
     * Pravi novi objekat klase StavkaPozajmice i postavlja pozajmicu, stavkaID, datumDo i knjigu na unete vrednosti.
     * 
     * @param pozajmica pozajmica kojoj stavka pripada kao objekat klase Pozajmica
     * @param stavkaID ID stavke kao int
     * @param datumDo datum do kojeg treba vratiti knjigu kao objekat klase Date
     * @param knjiga knjiga koja se pozajmljuje kao objekat klase Knjiga
     */
    public StavkaPozajmice(Pozajmica pozajmica, int stavkaID, Date datumDo, Knjiga knjiga) {
        this.pozajmica = pozajmica;
        this.stavkaID = stavkaID;
        this.datumDo = datumDo;
        this.knjiga = knjiga;
    }

    /**
     * Vraca knjigu koja je pozajmljena.
     * 
     * @return pozajmljena knjiga kao objekat klase Knjiga
     */
    public Knjiga getKnjiga() {
        return knjiga;
    }

    /**
     * Postavlja knjigu koja se pozajmljuje na unetu vrednost.
     * 
     * @param knjiga pozajmljena knjiga kao objekat klase Knjiga
     */
    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    /**
     * Vraca pozajmicu kojoj pripada stavka.
     * 
     * @return pozajmica kao objekat klase Pozajmica
     */
    public Pozajmica getPozajmica() {
        return pozajmica;
    }

    /**
     * Postavlja pozajmicu kojoj stavka pripada na unetu vrednost.
     * 
     * @param pozajmica pozajmica kao objekat klase Pozajmica
     */
    public void setPozajmica(Pozajmica pozajmica) {
        this.pozajmica = pozajmica;
    }

    /**
     * Vraca ID stavke.
     * 
     * @return ID stavke kao int
     */
    public int getStavkaID() {
        return stavkaID;
    }

    /**
     * Postavlja ID stavke na unetu vrednost.
     * 
     * @param stavkaID ID stavke kao int
     */
    public void setStavkaID(int stavkaID) {
        this.stavkaID = stavkaID;
    }

    /**
     * Vraca datum do kojeg treba vratiti knjigu.
     * 
     * @return datum kao objekat klase Date
     */
    public Date getDatumDo() {
        return datumDo;
    }

    /**
     * Postavlja datum do kojeg treba vratiti knjigu na unetu vrednost.
     * 
     * @param datumDo datum kao objekat klase Date
     */
    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    /**
     * Poredi dve stavke pozajmice prema knjizi.
     * 
     * @param obj druga stavka sa kojom se poredi
     * 
     * @return 
     * <ul>
	 * 	<li><b>true</b> - ako su oba objekta inicijalizovana, klase su StavkaPozajmice, imaju istu knjigu</li>
	 * 	<li><b>false</b> - ako nisu klase StavkaPozajmice, ako je uneta stavka null ili ako nije ista knjiga</li>
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
