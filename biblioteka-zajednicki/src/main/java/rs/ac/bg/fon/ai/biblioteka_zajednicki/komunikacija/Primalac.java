package rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija;

import java.io.ObjectInputStream;
import java.net.Socket;

public class Primalac {
    
    private Socket socket;

    public Primalac(Socket socket) {
        this.socket = socket;
    }
    
    public Object primi() {
        
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return in.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
}
