package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja clana koji se uclanio u biblioteku.
 * Nasledjuje apstraktnu klasu ApstraktniDomenskiObjekat i implementira njene metode.
 * 
 * Clan ima svoj ID, ime, prezime i broj telefona.
 * 
 * @author Teodora
 *
 */
public class Clan extends ApstraktniDomenskiObjekat {
    
	/**
	 * ID clana kao int.
	 */
    private int clanID;
    
    /**
     * Ime clana kao String.
     */
    private String ime;
    
    /**
     * Prezime clana kao String.
     */
    private String prezime;
    
    /**
     * Broj telefona clana kao String.
     */
    private String brojTelefona;

    /**
     * Pravi novi objekat klase Clan.
     * 
     * Ime, prezime, clanID i brojTelefona ostaju neinicijalizovani.
     */
    public Clan() {
    }

    /**
     * Pravi novi objekat klase Clan i postavlja clanID, ime, prezime i brojTelefona na unete vrednosti.
     * 
     * @param clanID ID clana kao int
     * @param ime ime clana kao String
     * @param prezime prezime clana kao String
     * @param brojTelefona broj telefona clana kao String
     */
    public Clan(int clanID, String ime, String prezime, String brojTelefona) {
        this.setClanID(clanID);
        this.setIme(ime);
        this.setPrezime(prezime);
        this.setBrojTelefona(brojTelefona);
    }

    /**
     * Vraca ID clana.
     * 
     * @return ID clana kao int
     */
    public int getClanID() {
        return clanID;
    }

    /**
     * Postavlja ID clana na unetu vrednost.
     * 
     * Uneti ID ne sme biti manji od 0.
     * 
     * @param clanID ID clana kao int
     * 
     * @throws java.lang.IllegalArgumentException ako je uneti ID manji od 0
     */
    public void setClanID(int clanID) {
    	if (clanID < 0)
			throw new IllegalArgumentException("ID ne sme da bude manji od 0.");
    	
        this.clanID = clanID;
    }

    /**
     * Vraca ime clana.
     * 
     * @return ime clana kao String
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime clana na unetu vrednost.
     * 
     * Uneto ime ne sme da bude null niti prazan String.
     * 
     * @param ime ime clana kao String
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

    /**
     * Vraca prezime clana.
     * 
     * @return prezime clana kao String
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime clana na unetu vrednost.
     * 
     * Uneto prezime ne sme da bude null niti prazan String.
     * 
     * @param prezime prezime clana kao String
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
     * Vraca broj telefona clana.
     * 
     * @return broj telefona clana kao String
     */
    public String getBrojTelefona() {
        return brojTelefona;
    }

    /**
     * Postavlja broj telefona clana na unetu vrednost.
     * 
     * Broj telefona ne sme da ima vise od 10 karaktera.
     * 
     * @param brojTelefona broj telefona clana kao String
     * 
     * @throws java.lang.IllegalArgumentException ako je uneti broj telefona duzi od 10 karaktera
     */
    public void setBrojTelefona(String brojTelefona) {
    	if (brojTelefona.length() > 10)
			throw new IllegalArgumentException("Broj telefona ne sme da bude duzi od 10 cifara.");
    	
        this.brojTelefona = brojTelefona;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Poredi dva clana prema imenu, prezimenu i broju telefona.
     * 
     * @param obj drugi clan sa kojim se poredi
     * 
     * @return 
     * <ul>
	 * 	<li><b>true</b> - ako su oba objekta inicijalizovana, klase su Clan, imaju isto ime, prezime i broj telefona</li>
	 * 	<li><b>false</b> - ako nisu klase Clan, ako je uneti clan null ili ako nije isto ime ili prezime ili broj telefona</li>
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
        final Clan other = (Clan) obj;
        if (!Objects.equals(this.ime, other.ime)) {
            return false;
        }
        if (!Objects.equals(this.prezime, other.prezime)) {
            return false;
        }
        return Objects.equals(this.brojTelefona, other.brojTelefona);
    }

    /**
     * Vraca podatke o clanu koji obuhvataju njegovo ime i prezime.
     * 
     * @return String koji sadrzi ime i prezime clana
     */
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
