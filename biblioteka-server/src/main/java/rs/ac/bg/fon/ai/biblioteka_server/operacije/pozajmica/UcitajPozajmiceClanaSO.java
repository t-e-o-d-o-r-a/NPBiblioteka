package rs.ac.bg.fon.ai.biblioteka_server.operacije.pozajmica;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.StavkaPozajmice;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.operacije.ApstraktnaGenerickaOperacija;

public class UcitajPozajmiceClanaSO extends ApstraktnaGenerickaOperacija {
    
    List<Pozajmica> pozajmice;

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Pozajmica)) {
            throw new Exception("Neispravni parametar.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Pozajmica p = (Pozajmica) objekat;
        pozajmice = broker.filter(p);
        
        for (Pozajmica pozajmica : pozajmice) {
            StavkaPozajmice sp = new StavkaPozajmice();
            sp.setPozajmica(pozajmica);
            List<StavkaPozajmice> stavke = broker.filter((StavkaPozajmice) sp);
            pozajmica.setStavke(stavke);
        }
    }

    public List<Pozajmica> getPozajmice() {
        return pozajmice;
    }
    
    
}
