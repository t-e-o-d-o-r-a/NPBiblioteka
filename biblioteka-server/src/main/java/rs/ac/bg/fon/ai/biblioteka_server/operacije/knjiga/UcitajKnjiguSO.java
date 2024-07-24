package rs.ac.bg.fon.ai.biblioteka_server.operacije.knjiga;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

public class UcitajKnjiguSO extends ApstraktnaGenerickaOperacija {

    private Knjiga knjiga;
    
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Knjiga)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Knjiga k = (Knjiga) objekat;
        knjiga = (Knjiga) broker.pronadji(k, k.getKnjigaID());
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }
    
    
    
}
