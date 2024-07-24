package rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija;

import java.io.Serializable;

public class Zahtev implements Serializable{
    
    private Operacija operacija;
    private Object parametar;

    public Zahtev() {
    }

    public Zahtev(Operacija operacija, Object parametar) {
        this.operacija = operacija;
        this.parametar = parametar;
    }

    public Operacija getOperacija() {
        return operacija;
    }

    public void setOperacija(Operacija operacija) {
        this.operacija = operacija;
    }

    public Object getParametar() {
        return parametar;
    }

    public void setParametar(Object parametar) {
        this.parametar = parametar;
    }
    
    
    
}
