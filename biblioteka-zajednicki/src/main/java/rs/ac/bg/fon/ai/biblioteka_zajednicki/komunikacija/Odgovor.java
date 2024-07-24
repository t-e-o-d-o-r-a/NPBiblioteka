package rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija;

import java.io.Serializable;

public class Odgovor implements Serializable {
    
    private Object odgovor;
    private Exception greska;

    public Odgovor() {
    }

    public Odgovor(Object odgovor, Exception greska) {
        this.odgovor = odgovor;
        this.greska = greska;
    }

    public Object getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(Object odgovor) {
        this.odgovor = odgovor;
    }

    public Exception getGreska() {
        return greska;
    }

    public void setGreska(Exception greska) {
        this.greska = greska;
    }
    
    
    
}
