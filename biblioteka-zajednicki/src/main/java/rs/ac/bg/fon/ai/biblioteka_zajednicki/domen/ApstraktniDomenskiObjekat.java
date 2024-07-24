package rs.ac.bg.fon.ai.biblioteka_zajednicki.domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface ApstraktniDomenskiObjekat extends Serializable{
    
    public String vratiNazivTabele();
    
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception;
    
    public String vratiKoloneZaUbacivanje();
    
    public String vratiVrednostiZaUbacivanje();
    
    public String vratiPrimarniKljuc();
    
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception;
    
    public String vratiVrednostiZaIzmenu();
    
    public String vratiUslovZaFiltriranje(ApstraktniDomenskiObjekat obj);
    
    public String join();
    
    public void postaviId(int id);
    
}