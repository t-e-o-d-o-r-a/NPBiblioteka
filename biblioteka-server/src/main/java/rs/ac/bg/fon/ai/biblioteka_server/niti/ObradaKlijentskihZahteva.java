package rs.ac.bg.fon.ai.biblioteka_server.niti;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_server.forme.koordinator.Koordinator;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija.Odgovor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija.Posiljalac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija.Primalac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija.Zahtev;
import rs.ac.bg.fon.ai.biblioteka_server.kontroler.Kontroler;

public class ObradaKlijentskihZahteva extends Thread {
    
    Socket socket;
    Primalac primalac;
    Posiljalac posiljalac;
    boolean kraj = false;

    public ObradaKlijentskihZahteva(Socket socket) {
        this.socket = socket;
        this.posiljalac = new Posiljalac(socket);
        this.primalac = new Primalac(socket);
    }

    @Override
    public void run() {
        try{
            while (!kraj) {
                Zahtev zahtev = (Zahtev) primalac.primi();
                Odgovor odgovor = new Odgovor();

                try {
                    switch (zahtev.getOperacija()) {
                        case LOGIN:
                            Bibliotekar b = (Bibliotekar) zahtev.getParametar();
                            b = Kontroler.getInstance().login(b.getKorisnickoIme(), b.getLozinka());
                            if (b == null) {
                                Exception e = new Exception("Korisnik nema pravo pristupa sistemu.");
                                odgovor.setGreska(e);
                            } else {
                                if (!Kontroler.getInstance().getPrijavljeniBibliotekari().contains(b)) {
                                    Kontroler.getInstance().getPrijavljeniBibliotekari().add(b);
                                    Koordinator.getInstance().azurirajUlogovane(b);
                                    odgovor.setOdgovor(b);
                                } else {
                                    Exception e = new Exception("Bibliotekar je vec prijavljen.");
                                    odgovor.setGreska(e);
                                }
                            }
                            break;
                        case SACUVAJ_CLANA:
                            Clan c = (Clan) zahtev.getParametar();
                            Kontroler.getInstance().sacuvajClana(c);
                            break;
                        case VRATI_SVE_CLANOVE:
                            List<Clan> clanovi = Kontroler.getInstance().vratiSveClanove();
                            odgovor.setOdgovor(clanovi);
                            break;
                        case IZMENI_CLANA: 
                            Clan c1 = (Clan) zahtev.getParametar();
                            Kontroler.getInstance().izmeniClana(c1);
                            break;
                        case OBRISI_CLANA:    
                            Clan c2 = (Clan) zahtev.getParametar();
                            Kontroler.getInstance().obrisiClana(c2);
                            break;
                        case NADJI_CLANOVE: 
                            String imeClana = (String) zahtev.getParametar();
                            List<Clan> filtriraniClanovi = Kontroler.getInstance().filtritajClanove(imeClana);
                            odgovor.setOdgovor(filtriraniClanovi);
                            break;
                        case UCITAJ_CLANA:
                            Clan c3 = (Clan) zahtev.getParametar();
                            c3 = Kontroler.getInstance().ucitajClana(c3);
                            odgovor.setOdgovor(c3);
                            break;
                        case VRATI_SVE_AUTORE:
                            List<Autor> autori = Kontroler.getInstance().vratiSveAutore();
                            odgovor.setOdgovor(autori);
                            break;
                        case VRATI_SVE_IZDAVACE:
                            List<Izdavac> izdavaci = Kontroler.getInstance().vratiSveIzdavace();
                            odgovor.setOdgovor(izdavaci);
                            break;
                        case SACUVAJ_KNJIGU:
                            Knjiga k = (Knjiga) zahtev.getParametar();
                            Kontroler.getInstance().sacuvajKnjigu(k);
                            break;
                        case VRATI_SVE_KNJIGE:
                            List<Knjiga> knjige = Kontroler.getInstance().vratiSveKnjige();
                            odgovor.setOdgovor(knjige);
                            break;
                        case OBRISI_KNJIGU: 
                            Knjiga k1 = (Knjiga) zahtev.getParametar();
                            Kontroler.getInstance().obrisiKnjigu(k1);
                            break;
                        case IZMENI_KNJIGU:
                            Knjiga k2 = (Knjiga) zahtev.getParametar();
                            Kontroler.getInstance().izmeniKnjigu(k2);
                            break;
                        case NADJI_KNJIGE: 
                            Knjiga k3 = (Knjiga) zahtev.getParametar();
                            List<Knjiga> filtriraneKnjige = Kontroler.getInstance().filtritajKnjige(k3);
                            odgovor.setOdgovor(filtriraneKnjige);
                            break;
                        case UCITAJ_KNJIGU:
                            Knjiga k4 = (Knjiga) zahtev.getParametar();
                            k4 = Kontroler.getInstance().ucitajKnjigu(k4);
                            odgovor.setOdgovor(k4);
                            break;
                        case SACUVAJ_POZAJMICU:
                            Pozajmica p = (Pozajmica) zahtev.getParametar();
                            Kontroler.getInstance().sacuvajPozajmicu(p);
                            break;
                        case VRATI_POZAJMICE_CLANA:    
                            Clan cp = (Clan) zahtev.getParametar();
                            List<Pozajmica> filtriranePozajmice = Kontroler.getInstance().filtrirajPozajmice(cp);
                            odgovor.setOdgovor(filtriranePozajmice);
                            break;
                        case IZMENI_POZAJMICU:
                            Pozajmica poz = (Pozajmica) zahtev.getParametar();
                            Kontroler.getInstance().izmeniPozajmicu(poz);
                            break;
                        case OBRISI_POZAJMICU:    
                            Pozajmica po = (Pozajmica) zahtev.getParametar();
                            Kontroler.getInstance().obrisiPozajmicu(po);
                            break;
                        case KRAJ_RADA:
                            Bibliotekar bibliotekar = (Bibliotekar) zahtev.getParametar();
                            Kontroler.getInstance().getPrijavljeniBibliotekari().remove(bibliotekar);
                            Koordinator.getInstance().ukloniIzUlogovanih(bibliotekar);
                            prekiniNit();
                            System.out.println("Bibliotekar se iskljucio.");
                            break;
                        case KRAJ_RADA_LOGIN:
                            prekiniNit();
                            System.out.println("Klijent se iskljucio.");
                            break;
                        default:
                            throw new AssertionError();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    odgovor.setGreska(e);
                }
                posiljalac.posalji(odgovor);

            } 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void prekiniNit() {
        kraj = true;
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        interrupt();
    }
    
    
}
