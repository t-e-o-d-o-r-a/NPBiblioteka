package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja knjigu koja je evidentirana u biblioteci.
 * Implementira interfejs ApstraktniDomenskiObjekat.
 * 
 * Knjiga ima svoj ID, naslov, broj primeraka, godinu izdanja, autora, izdavaca i format.
 * 
 * @author Teodora
 *
 */
public class Knjiga implements ApstraktniDomenskiObjekat {
    
	/**
	 * ID knjige kao int.
	 */
    private int knjigaID;
    
    /**
     * Naslov knjige kao String.
     */
    private String naslov;
    
    /**
     * Broj primeraka knjige koji su raspolozivi kao int.
     */
    private int brojPrimeraka;
    
    /**
     * Godina izdanja knjige kao int.
     */
    private int godinaIzdanja;
    
    /**
     * Autor knjige kao objekat klase Autor.
     */
    private Autor autor;
    
    /**
     * Izdavac knjige kao objekat klase Izdavac.
     */
    private Izdavac izdavac;
    
    /**
     * Format knjige kao enum vrednost Format.
     */
    private Format format;

    /**
     * Pravi novi objekat klase Knjiga.
     * 
     * Naslov, knjigaID, brojPrimeraka, godinaIzdanja, autor, izdavac i format ostaju neinicijalizovani.
     */
    public Knjiga() {
    }

    /**
     * Pravi novi objekat klase Knjiga i postavlja knjigaID, naslov, brojPrimeraka, godinuIzdanja, autora, izdavaca i format na unete vrednosti.
     * 
     * @param knjigaID ID knjige kao int
     * @param naslov naslov knjige kao String
     * @param brojPrimeraka raspoloziv broj primeraka knjige kao int 
     * @param godinaIzdanja godina izdanja knjige kao int
     * @param autor autor knjige kao objekat klase Autor
     * @param izdavac izdavac knjige kao objekat klase Izdavac
     * @param format format knjige kao enum vrednost Format
     */
    public Knjiga(int knjigaID, String naslov, int brojPrimeraka, int godinaIzdanja, Autor autor, Izdavac izdavac, Format format) {
        this.knjigaID = knjigaID;
        this.naslov = naslov;
        this.brojPrimeraka = brojPrimeraka;
        this.godinaIzdanja = godinaIzdanja;
        this.autor = autor;
        this.izdavac = izdavac;
        this.format = format;
    }

    /**
     * Vraca format knjige.
     *  
     * @return format knjige kao enum vrednost Format
     */
    public Format getFormat() {
        return format;
    }

    /**
     * Postavlja format knjige na unetu vrednost.
     * 
     * @param format format knjige kao enum vrednost Format
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * Vraca ID knjige.
     * 
     * @return ID knjige kao int
     */
    public int getKnjigaID() {
        return knjigaID;
    }

    /**
     * Postavlja ID knjige na unetu vrednost.
     * 
     * @param knjigaID ID knjige kao int
     */
    public void setKnjigaID(int knjigaID) {
        this.knjigaID = knjigaID;
    }

    /**
     * Vraca naslov knjige.
     * 
     * @return naslov knjige kao String.
     */
    public String getNaslov() {
        return naslov;
    }

    /**
     * Postavlja naslov knjige na unetu vrednost.
     * 
     * @param naslov naslov knjige kao String
     */
    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    /**
     * Vraca raspoloziv broj primeraka knjige.
     * 
     * @return raspoloziv broj primeraka knjige kao int
     */
    public int getBrojPrimeraka() {
        return brojPrimeraka;
    }
    
	/**
	 * Postavlja raspoloziv broj primeraka knjige na unetu vrednost.
	 * 
	 * @param brojPrimeraka raspoloziv broj primeraka knjige kao int
	 */
    public void setBrojPrimeraka(int brojPrimeraka) {
        this.brojPrimeraka = brojPrimeraka;
    }

    /**
     * Vraca godinu izdanja knjige.
     * 
     * @return godina izdanja knjige kao int
     */
    public int getGodinaIzdanja() {
        return godinaIzdanja;
    }

    /**
     * Postavlja godinu izdanja knjige na unetu vrednost.
     * 
     * @param godinaIzdanja godina izdanja knjige kao int
     */
    public void setGodinaIzdanja(int godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
    }

    /**
     * Vraca autora knjige.
     * 
     * @return autor knjige kao objekat klase Autor
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * Postavlja autora knjige na unetu vrednost.
     * 
     * @param autor autor knjige kao objekat klase Autor
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * Vraca izdavaca knjige.
     * 
     * @return izdavac knjige kao objekat klase Izdavac
     */
    public Izdavac getIzdavac() {
        return izdavac;
    }

    /**
     * Postavlja izdavaca knjige na unetu vrednost.
     * 
     * @param izdavac izdavac knjige kao objekat klase Izdavac
     */
    public void setIzdavac(Izdavac izdavac) {
        this.izdavac = izdavac;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Poredi dve knjige prema godini izdanja, naslovu, autoru, izdavacu i formatu.
     * 
     * @param obj druga knjiga sa kojim se poredi
     * 
     * @return 
     * <ul>
	 * 	<li><b>true</b> - ako su oba objekta inicijalizovana, klase su Knjiga, imaju istu godinu izdanja, naslov, autora, izdavaca i format</li>
	 * 	<li><b>false</b> - ako nisu klase Knjiga, ako je uneta knjiga null ili ako nije ista godina izdanja ili naslov ili autor ili izdavac ili format</li>
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

