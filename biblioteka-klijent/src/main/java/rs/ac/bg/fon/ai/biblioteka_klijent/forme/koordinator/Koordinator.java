package rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.ClanForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.GlavnaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.KnjigaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.LoginForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.PozajmicaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.PregledClanovaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.PregledKnjigaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri.ClanKontroler;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri.GlavnaKontroler;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri.KnjigaKontroler;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri.LoginKontroler;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri.PozajmicaKontroler;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri.PregledClanovaKontroler;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri.PregledKnjigaKontroler;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri.PregledPozajmicaKontroler;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.util.FormaMod;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.PregledPozajmicaForma;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author teodo
 */
public class Koordinator {
    
    private static Koordinator instanca;
    private Bibliotekar ulogovaniBibliotekar;
    private GlavnaKontroler glavnaKontroler;
    private PregledClanovaKontroler pregledClanovaKontroler;
    private PregledKnjigaKontroler pregledKnjigaKontroler;
    private final Map<String, Object> parametri;

    private Koordinator() {
        parametri = new HashMap<>();
    }

    public static Koordinator getInstance() {
        if (instanca == null) {
            instanca = new Koordinator();
        }
        return instanca;
    }

    public Bibliotekar getUlogovaniBibliotekar() {
        return ulogovaniBibliotekar;
    }

    public void setUlogovaniBibliotekar(Bibliotekar ulogovaniBibliotekar) {
        this.ulogovaniBibliotekar = ulogovaniBibliotekar;
    }
    
    public void dodajParametar(String s, Object o) {
        parametri.put(s, o);
    }
    
    public Object vratiParametar(String s) {
        return parametri.get(s);
    }

    public void otvoriLoginFormu() {
        LoginKontroler loginKontroler = new LoginKontroler(new LoginForma());
        loginKontroler.otvoriFormu();
    }

    public void otvoriGlavnuFormu() {
        glavnaKontroler = new GlavnaKontroler(new GlavnaForma());
        glavnaKontroler.otvoriFormu();
    }

    public void otvoriClanFormu() {
        ClanKontroler clanKontroler = new ClanKontroler(new ClanForma(glavnaKontroler.getGf(), true));
        clanKontroler.otvoriFormu(FormaMod.DODAVANJE);
    }

    public void otvoriPregledClanovaFormu() {
        pregledClanovaKontroler = new PregledClanovaKontroler(new PregledClanovaForma(glavnaKontroler.getGf(), true));
        pregledClanovaKontroler.otvoriFormu();
    }
    
    public void osveziPregledClanovaFormu() {
        pregledClanovaKontroler.pripremiFormu();
    }

    public void otvoriDetaljeClana() {
        ClanKontroler clanKontroler = new ClanKontroler(new ClanForma(glavnaKontroler.getGf(), true));
        clanKontroler.otvoriFormu(FormaMod.PRIKAZ);
    }
    
    public void otvoriIzmenuClana() {
        ClanKontroler clanKontroler = new ClanKontroler(new ClanForma(glavnaKontroler.getGf(), true));
        clanKontroler.otvoriFormu(FormaMod.IZMENA);
    }

    public void otvoriKnjigaFormu() {
        KnjigaKontroler knjigaKontroler = new KnjigaKontroler(new KnjigaForma(glavnaKontroler.getGf(), true));
        knjigaKontroler.otvoriFormu(FormaMod.DODAVANJE);
    }

    public void otvoriPregledKnjigaFormu() {
        pregledKnjigaKontroler = new PregledKnjigaKontroler(new PregledKnjigaForma(glavnaKontroler.getGf(), true));
        pregledKnjigaKontroler.otvoriFormu();
    }

    public void otvoriDetaljeKnjige() {
        KnjigaKontroler knjigaKontroler = new KnjigaKontroler(new KnjigaForma(glavnaKontroler.getGf(), true));
        knjigaKontroler.otvoriFormu(FormaMod.PRIKAZ);
    }

    public void osveziPregledKnjigaFormu() {
        pregledKnjigaKontroler.pripremiFormu();
    }

    public void otvotiPozajmicaFormu() {
        PozajmicaKontroler pozajmicaKontroler = new PozajmicaKontroler(new PozajmicaForma(glavnaKontroler.getGf(), true));
        pozajmicaKontroler.otvoriFormu();
    }

    public void otvoriPregledPozajmicaFormu() {
        PregledPozajmicaKontroler pregledPozajmicaKontroler = new PregledPozajmicaKontroler(new PregledPozajmicaForma(glavnaKontroler.getGf(), true));
        pregledPozajmicaKontroler.otvoriFormu();
    }
    
}
