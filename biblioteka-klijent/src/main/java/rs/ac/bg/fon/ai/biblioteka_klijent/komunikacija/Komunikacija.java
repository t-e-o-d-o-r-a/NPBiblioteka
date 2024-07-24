package rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija.Odgovor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija.Operacija;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija.Posiljalac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija.Primalac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija.Zahtev;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator.Koordinator;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Komunikacija {
    
    private static Komunikacija instanca;
    Socket socket;
    Posiljalac posiljalac;
    Primalac primalac;

    private Komunikacija() throws IOException {
        socket = new Socket("localhost", 9000);
        posiljalac = new Posiljalac(socket);
        primalac = new Primalac(socket);
    }

    public static Komunikacija getInstance() throws IOException {
        if (instanca == null) {
            instanca = new Komunikacija();
        }
        return instanca;
    }
    
    public Bibliotekar login(String korisnickoIme, String lozinka) throws Exception {
        Bibliotekar b = new Bibliotekar();
        b.setKorisnickoIme(korisnickoIme);
        b.setLozinka(lozinka);
        
        Zahtev zahtev = new Zahtev(Operacija.LOGIN, b);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() == null) {
            return (Bibliotekar) odgovor.getOdgovor();
        } else {
            throw odgovor.getGreska();
        }
    }

    public void krajRadaLogin() {
        Zahtev zahtev = new Zahtev(Operacija.KRAJ_RADA_LOGIN, null);
        posiljalac.posalji(zahtev);
    }

    public void krajRada() {
        Zahtev zahtev = new Zahtev(Operacija.KRAJ_RADA, Koordinator.getInstance().getUlogovaniBibliotekar());
        posiljalac.posalji(zahtev);
    }

    public void dodajClana(Clan c) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.SACUVAJ_CLANA, c);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
    }

    public List<Clan> ucitajSveClanove() throws Exception {
        List<Clan> clanovi = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_SVE_CLANOVE, null);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
        
        clanovi = (List<Clan>) odgovor.getOdgovor();
        return clanovi;
    }

    public void obrisiClana(Clan izabrani) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_CLANA, izabrani);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
    }

    public void izmeniClana(Clan c) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_CLANA, c);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
    }

    public List<Clan> vratiClanoveIme(String imeClana) throws Exception {
        List<Clan> clanovi = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.NADJI_CLANOVE, imeClana);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
        
        clanovi = (List<Clan>) odgovor.getOdgovor();
        return clanovi;
    }

    public List<Autor> vratiSveAutore() throws Exception {
        List<Autor> autori = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_SVE_AUTORE, null);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
        
        autori = (List<Autor>) odgovor.getOdgovor();
        return autori;
    }

    public void sacuvajKnjigu(Knjiga knjiga) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.SACUVAJ_KNJIGU, knjiga);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
    }

    public List<Knjiga> vratiSveKnjige() throws Exception {
        List<Knjiga> knjige = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_SVE_KNJIGE, null);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
        
        knjige = (List<Knjiga>) odgovor.getOdgovor();
        return knjige;
    }

    public void obrisiKnjigu(Knjiga k) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_KNJIGU, k);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
    }

    public void izmeniKnjigu(Knjiga knjiga) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_KNJIGU, knjiga);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
    }

    public List<Knjiga> vratiKnjigeNaslovAutor(Knjiga k) throws Exception {
        List<Knjiga> knjige = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.NADJI_KNJIGE, k);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
        
        knjige = (List<Knjiga>) odgovor.getOdgovor();
        return knjige;
    }

    public Knjiga ucitajKnjigu(int knjigaIdIzmena) throws Exception {
        Knjiga knjiga = new Knjiga();
        knjiga.setKnjigaID(knjigaIdIzmena);
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_KNJIGU, knjiga);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
        
        knjiga = (Knjiga) odgovor.getOdgovor();
        return knjiga;
    }

    public Clan ucitajClana(int clanIdIzmena) throws Exception {
        Clan clan = new Clan();
        clan.setClanID(clanIdIzmena);
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_CLANA, clan);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
        
        clan = (Clan) odgovor.getOdgovor();
        return clan;
    }

    public List<Izdavac> vratiSveIzdavace() throws Exception {
        List<Izdavac> izdavaci = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_SVE_IZDAVACE, null);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
        
        izdavaci = (List<Izdavac>) odgovor.getOdgovor();
        return izdavaci;
    }

    public void sacuvajPozajmicu(Pozajmica p) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.SACUVAJ_POZAJMICU, p);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
    }

    public List<Pozajmica> vratiPozajmiceClana(Clan clan) throws Exception {
        List<Pozajmica> pozajmice = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_POZAJMICE_CLANA, clan);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        } 
        
        pozajmice = (List<Pozajmica>) odgovor.getOdgovor();
        return pozajmice;
    }

    public void izmeniPozajmicu(Pozajmica p) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_POZAJMICU, p);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
    }

    public void obrisiPozajmicu(Pozajmica p) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_POZAJMICU, p);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
    }
    
    
    
    
}

