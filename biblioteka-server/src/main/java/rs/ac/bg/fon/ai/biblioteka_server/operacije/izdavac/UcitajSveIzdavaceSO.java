package rs.ac.bg.fon.ai.biblioteka_server.operacije.izdavac;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

public class UcitajSveIzdavaceSO extends ApstraktnaGenerickaOperacija {
    
    List<Izdavac> lista;

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Izdavac)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        lista = broker.vratiSve((Izdavac) objekat, kljuc);
    }

    public List<Izdavac> getLista() {
        return lista;
    }
    
    
}
