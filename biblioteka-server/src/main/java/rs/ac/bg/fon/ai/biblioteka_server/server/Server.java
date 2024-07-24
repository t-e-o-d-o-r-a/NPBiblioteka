package rs.ac.bg.fon.ai.biblioteka_server.server;

import java.io.IOException;
import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ai.biblioteka_server.kontroler.Kontroler;
import rs.ac.bg.fon.ai.biblioteka_server.niti.ObradaKlijentskihZahteva;

public class Server extends Thread {
    
    private boolean kraj = false;
    private ServerSocket serverSocket;
    private List<ObradaKlijentskihZahteva> klijenti;

    public Server() {
        klijenti = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(9000);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
            while(!kraj) {
                try {
                    System.out.println("Cekanje konekcije...");
                    Socket s = serverSocket.accept();
                    System.out.println("Klijent se povezao.");
                
                    ObradaKlijentskihZahteva klijent = new ObradaKlijentskihZahteva(s);
                    klijenti.add(klijent);
                    klijent.start();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
    }
    
    public void zaustaviServer() {
        try {
            kraj = true;
            for (ObradaKlijentskihZahteva k : klijenti) {
                k.prekiniNit();
            }
            Kontroler.getInstance().setPrijavljeniBibliotekari(new ArrayList<>());
            serverSocket.close();
            //interrupt();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
