package rs.ac.bg.fon.ai.biblioteka_server.forme.koordinator;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import rs.ac.bg.fon.ai.biblioteka_server.forme.PodesavanjaForma;
import rs.ac.bg.fon.ai.biblioteka_server.forme.ServerskaForma;
import rs.ac.bg.fon.ai.biblioteka_server.forme.kontroleri.PodesavanjaFormaKontroler;
import rs.ac.bg.fon.ai.biblioteka_server.forme.kontroleri.ServerskaFormaKontroler;

public class Koordinator {
    
    private static Koordinator instanca;
    private final ServerskaFormaKontroler sfKontroler;    

    private Koordinator() {
        sfKontroler = new ServerskaFormaKontroler(new ServerskaForma());
    }

    public static Koordinator getInstance() {
        if (instanca == null) {
            instanca = new Koordinator();
        }
        return instanca;
    }

    public void otvoriPodesavanja() {
        PodesavanjaFormaKontroler pfKontroler = new PodesavanjaFormaKontroler(new PodesavanjaForma(sfKontroler.getSf(), true));
        pfKontroler.otvoriFormu();
    }

    public void otvoriServerskuFormu() {
        sfKontroler.otvoriFormu();
    }
    
    public void azurirajUlogovane(Bibliotekar b) {
        sfKontroler.azurirajUlogovane(b);
    }

    public void ukloniIzUlogovanih(Bibliotekar b) {
        sfKontroler.ukloniIzUlogovanih(b);
    }
    
    
    
}
