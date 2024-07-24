package rs.ac.bg.fon.ai.biblioteka_server.kontroler;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.autor.UcitajSveAutoreSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.clan.IzmeniClanaSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.clan.KreirajClanaSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.clan.ObrisiClanaSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.clan.PronadjiClanoveSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.clan.UcitajClanaSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.clan.UcitajSveClanoveSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.izdavac.UcitajSveIzdavaceSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga.IzmeniKnjiguSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga.KreirajKnjiguSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga.ObrisiKnjiguSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga.PronadjiKnjigeSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga.UcitajKnjiguSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga.UcitajSveKnjigeSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.login.LoginSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.pozajmica.IzmeniPozajmicuSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.pozajmica.KreirajPozajmicuSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.pozajmica.ObrisiPozajmicuSO;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.pozajmica.UcitajPozajmiceClanaSO;
import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;
import rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl.DBRepositoryGeneric;
import rs.ac.bg.fon.ai.biblioteka_server.server.Server;

public class Kontroler {
    
    private static Kontroler instanca;
    private final Repository repository;
    private Server server;
    private List<Bibliotekar> prijavljeniBibliotekari;

    private Kontroler() {
        this.repository = new DBRepositoryGeneric();
        prijavljeniBibliotekari = new ArrayList<>();
    }
    
    public static Kontroler getInstance() {
        if (instanca == null) instanca = new Kontroler();
        return instanca;
    }

    public void pokreniServer() {
        if (server == null || !server.isAlive()) {
            server = new Server();
            server.start();
            System.out.println("Server je pokrenut.");
        }
    }

    public void zaustaviServer() {
        System.out.println("Server je zaustavljen.");
        server.zaustaviServer();
    }

    public List<Bibliotekar> getPrijavljeniBibliotekari() {
        return prijavljeniBibliotekari;
    }

    public void setPrijavljeniBibliotekari(List<Bibliotekar> prijavljeniBibliotekari) {
        this.prijavljeniBibliotekari = prijavljeniBibliotekari;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    public Bibliotekar login(String korisnickoIme, String lozinka) throws Exception {
        Bibliotekar b = new Bibliotekar();
        b.setKorisnickoIme(korisnickoIme);
        b.setLozinka(lozinka);
        
        LoginSO operacija = new LoginSO();
        operacija.izvrsi(b, null);
        System.out.println("Klasa Kontroler: " + operacija.getBibliotekar());
        return operacija.getBibliotekar();
    }

    public void sacuvajClana(Clan c) throws Exception {
        KreirajClanaSO operacija = new KreirajClanaSO();
        operacija.izvrsi(c, null);
    }

    public List<Clan> vratiSveClanove() throws Exception {
        UcitajSveClanoveSO operacija = new UcitajSveClanoveSO();
        operacija.izvrsi(new Clan(), null);
        List<Clan> clanovi = operacija.getLista();
        System.out.println("Klasa Kontroler: " + clanovi);
        return clanovi;
    }

    public void izmeniClana(Clan c1) throws Exception {
        IzmeniClanaSO operacija = new IzmeniClanaSO();
        operacija.izvrsi(c1, null);
    }

    public void obrisiClana(Clan c2) throws Exception {
        ObrisiClanaSO operacija = new ObrisiClanaSO();
        operacija.izvrsi(c2, null);
    }

    public List<Clan> filtritajClanove(String imeClana) throws Exception {
        Clan c = new Clan();
        c.setIme(imeClana);
        
        PronadjiClanoveSO operacija = new PronadjiClanoveSO();
        operacija.izvrsi(c, null);
        System.out.println("Klasa Kontroler: " + operacija.getLista());
        return operacija.getLista();
    }

    public List<Autor> vratiSveAutore() throws Exception {
        UcitajSveAutoreSO operacija = new UcitajSveAutoreSO();
        operacija.izvrsi(new Autor(), null);
        List<Autor> autori = operacija.getLista();
        System.out.println("Klasa Kontroler: " + autori);
        return autori;
    }

    public void sacuvajKnjigu(Knjiga k) throws Exception {
        KreirajKnjiguSO operacija = new KreirajKnjiguSO();
        operacija.izvrsi(k, null);
    }

    public List<Knjiga> vratiSveKnjige() throws Exception {
        UcitajSveKnjigeSO operacija = new UcitajSveKnjigeSO();
        operacija.izvrsi(new Knjiga(), null);
        List<Knjiga> knjige = operacija.getLista();
        System.out.println("Klasa Kontroler: " + knjige);
        return knjige;
    }

    public void obrisiKnjigu(Knjiga k1) throws Exception {
        ObrisiKnjiguSO operacija = new ObrisiKnjiguSO();
        operacija.izvrsi(k1, null);
    }

    public void izmeniKnjigu(Knjiga k2) throws Exception {
        IzmeniKnjiguSO operacija = new IzmeniKnjiguSO();
        operacija.izvrsi(k2, null);
    }

    public List<Knjiga> filtritajKnjige(Knjiga k3) throws Exception {
        PronadjiKnjigeSO operacija = new PronadjiKnjigeSO();
        if (k3.getAutor() == null) {
            Autor a = new Autor();
            a.setAutorID(-1);
            k3.setAutor(a);
        }
        if (k3.getNaslov() == "") {
            k3.setNaslov("---");
        }
        operacija.izvrsi(k3, null);
        System.out.println("Klasa Kontroler: " + operacija.getLista());
        return operacija.getLista();
    }

    public Clan ucitajClana(Clan c3) throws Exception {
        UcitajClanaSO operacija = new UcitajClanaSO();
        operacija.izvrsi(c3, null);
        System.out.println("Klasa Kontroler, pronadjeni clan: " + operacija.getClan());
        return operacija.getClan();
    }

    public Knjiga ucitajKnjigu(Knjiga k4) throws Exception {
        UcitajKnjiguSO operacija = new UcitajKnjiguSO();
        operacija.izvrsi(k4, null);
        System.out.println("Klasa Kontroler, pronadjena knjiga: " + operacija.getKnjiga());
        return operacija.getKnjiga();
    }

    public List<Izdavac> vratiSveIzdavace() throws Exception {
        UcitajSveIzdavaceSO operacija = new UcitajSveIzdavaceSO();
        operacija.izvrsi(new Izdavac(), null);
        List<Izdavac> izdavaci = operacija.getLista();
        System.out.println("Klasa Kontroler: " + izdavaci);
        return izdavaci;
    }

    public void sacuvajPozajmicu(Pozajmica p) throws Exception {
        KreirajPozajmicuSO operacija = new KreirajPozajmicuSO();
        operacija.izvrsi(p, null);
    }

    public List<Pozajmica> filtrirajPozajmice(Clan cp) throws Exception {
        Pozajmica pp = new Pozajmica();
        pp.setClan(cp);
        
        UcitajPozajmiceClanaSO operacija = new UcitajPozajmiceClanaSO();
        operacija.izvrsi(pp, null);
        System.out.println("Klasa Kontroler: " + operacija.getPozajmice());
        return operacija.getPozajmice();
    }

    public void izmeniPozajmicu(Pozajmica poz) throws Exception {
        IzmeniPozajmicuSO operacija = new IzmeniPozajmicuSO();
        operacija.izvrsi(poz, null);
    }

    public void obrisiPozajmicu(Pozajmica po) throws Exception {
        ObrisiPozajmicuSO operacija = new ObrisiPozajmicuSO();
        operacija.izvrsi(po, null);
    }

  
    

    
    
    
}
