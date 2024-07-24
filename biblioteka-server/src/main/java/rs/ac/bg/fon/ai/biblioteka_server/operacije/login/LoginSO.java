package rs.ac.bg.fon.ai.biblioteka_server.operacije.login;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

public class LoginSO extends ApstraktnaGenerickaOperacija{
    
    private Bibliotekar bibliotekar;

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Bibliotekar)) {
            throw new Exception("Neispravan parametar.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        List<Bibliotekar> lista = broker.vratiSve((Bibliotekar) objekat, kljuc);
        System.out.println("Klasa LoginSO, svi bibliotekari: " + lista);
        
        for (Bibliotekar b : lista) {
            if (b.equals((Bibliotekar) objekat)) {
                bibliotekar = b;
                return;
            }
        }
        
        bibliotekar = null;
    }

    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }
    
}
