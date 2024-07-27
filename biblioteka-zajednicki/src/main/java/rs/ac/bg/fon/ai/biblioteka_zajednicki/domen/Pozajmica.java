package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja pozajmicu u okviru koje clan u nekom trenutku pozajmljuje jednu ili vise knjiga iz biblioteke. Pozajmicu belezi bibliotekar.
 * Nasledjuje apstraktnu klasu ApstraktniDomenskiObjekat i implementira njene metode.
 * 
 * Pozajmica ima svoj ID, datum kada je kreirana, clana koji pozajmljuje, bibliotekara koji je belezi, listu stavki gde svaka stavka odgovara jednoj
 * pozajmljenoj knjizi i napomenu.
 * 
 * @author Teodora
 *
 */
public class Pozajmica extends ApstraktniDomenskiObjekat {
    
	/**
	 * ID pozajmice kao int.
	 */
    private int pozajmicaID;
    
    /**
     * Datum kada je pozajmica kreirana kao objekat klase Date.
     */
    private Date datum;
    
    /**
     * Clan koji pozajmljuje knjige kao objekat klase Clan.
     */
    private Clan clan;
    
    /**
     * Bibliotekar koji evidentira pozajmicu kao objekat klase Bibliotekar.
     */
    private Bibliotekar bibliotekar;
    
    /**
     * Lista stavki pozajmice kod koje svaka stavka odgovara po jednoj pozajmljenoj knjizi.
     * Lista moze biti prazna ili null u slucajevima da nema stavki pozajmice.
     */
    private List<StavkaPozajmice> stavke;
    
    /**
     * Napomena vezana za pozajmicu kao String.
     */
    private String napomena;

    /**
     * Pravi novi objekat klase Pozajmica.
     * 
     * PozajmicaID, datum, clan, bibliotekar, stavke i napomena ostaju neinicijalizovani.
     */
    public Pozajmica() {
    }

    /**
     * Pravi novi objekat klase Pozajmica i postavlja datum, clana, bibliotekara, stavke, napomenu i pozajmicaID na unete vrednosti.
     * 
     * @param pozajmicaID ID pozajmice kao int
     * @param datum datum kreiranja pozajmice kao objekat klase Date
     * @param clan clan koji pozajmljuje knjige kao objekat klase Clan
     * @param bibliotekar bibliotekar koji evidentira pozajmicu kao objekat klase Bibliotekar
     * @param stavke lista stavki pozajmice
     * @param napomena napomena koja ide uz pozajmicu kao String
     */
    public Pozajmica(int pozajmicaID, Date datum, Clan clan, Bibliotekar bibliotekar, List<StavkaPozajmice> stavke, String napomena) {
        this.setPozajmicaID(pozajmicaID);
        this.setDatum(datum);
        this.setClan(clan);
        this.setBibliotekar(bibliotekar);
        this.setStavke(stavke);
        this.setNapomena(napomena);
    }

    /**
     * Vraca listu stavki pozajmice.
     * 
     * @return lista sa stavkama pozajmice ili null ukoliko nema stavki
     */
    public List<StavkaPozajmice> getStavke() {
        return stavke;
    }

    /**
     * Postavlja listu stavki na unetu vrednost.
     * 
     * @param stavke lista sa stavkama pozajmice ili null ukoliko nema stavki
     */
    public void setStavke(List<StavkaPozajmice> stavke) {
        this.stavke = stavke;
    }

    /**
     * Vraca ID pozajmice.
     * 
     * @return ID pozajmice kao int
     */
    public int getPozajmicaID() {
        return pozajmicaID;
    }

    /**
     * Postavlja ID pozajmice na unetu vrednost.
     * 
     * Uneti ID ne sme biti manji od 0.
     * 
     * @param pozajmicaID ID pozajmice kao int
     * 
     * @throws java.lang.IllegalArgumentException ako je uneti ID manji od 0
     */
    public void setPozajmicaID(int pozajmicaID) {
    	if (pozajmicaID < 0)
			throw new IllegalArgumentException("ID ne sme da bude manji od 0.");
    	
        this.pozajmicaID = pozajmicaID;
    }

    /**
     * Vraca datum kreiranja pozajmice.
     * 
     * @return datum kreiranja pozajmice kao objekat klase Date
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * Postavlja datum kreiranja pozajmice na unetu vrednost.
     * 
     * @param datum datum kreiranja pozajmice kao objekat klase Date
     */
    public void setDatum(Date datum) {
        this.datum = datum;
    }

    /**
     * Vraca clana koji je pozajmio knjige.
     * 
     * @return clan kao objekat klase Clan
     */
    public Clan getClan() {
        return clan;
    }

    /**
     * Postavlja clana koji pozajmljuje knjige na unetu vrednost.
     * 
     * @param clan clan kao objekat klase Clan
     */
    public void setClan(Clan clan) {
        this.clan = clan;
    }

    /**
     * Vraca napomenu pozajmice.
     * 
     * @return napomena kao String
     */
    public String getNapomena() {
        return napomena;
    }

    /**
     * Postavlja napomenu pozajmice na unetu vrednost.
     * 
     * @param napomena napomena kao String
     */
    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    /**
     * Vraca bibliotekara koji je evidentirao pozajmicu.
     * 
     * @return bibliotekar kao objekat klase Bibliotekar
     */
    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }

    /**
     * Postavlja bibliotekara koji evidentira pozajmicu na unetu vrednost
     * 
     * @param bibliotekar bibliotekar kao objekat klase Bibliotekar
     */
    public void setBibliotekar(Bibliotekar bibliotekar) {
        this.bibliotekar = bibliotekar;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    /**
     * Poredi dve pozajmice prema ID-ju, clanu i bibliotekaru.
     * 
     * @param obj druga pozajmica sa kojom se poredi
     * 
     * @return 
     * <ul>
	 * 	<li><b>true</b> - ako su oba objekta inicijalizovana, klase su Pozajmica, imaju istog clana, bibliotekara i pozajmicaID</li>
	 * 	<li><b>false</b> - ako nisu klase Pozajmica, ako je uneta pozajmica null ili ako nije isti clan ili bibliotekar ili pozajmicaID</li>
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
