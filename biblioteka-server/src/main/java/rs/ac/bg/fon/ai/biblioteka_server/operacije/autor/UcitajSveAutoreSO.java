package rs.ac.bg.fon.ai.biblioteka_server.operacije.autor;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

public class UcitajSveAutoreSO extends ApstraktnaGenerickaOperacija {
    
    List<Autor> lista;

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Autor)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        lista = broker.vratiSve((Autor) objekat, kljuc);
    }

    public List<Autor> getLista() {
        return lista;
    }
    
    
}
