package rs.ac.bg.fon.ai.biblioteka_server.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import rs.ac.bg.fon.ai.biblioteka_server.forme.ServerskaForma;
import rs.ac.bg.fon.ai.biblioteka_server.forme.koordinator.Koordinator;
import rs.ac.bg.fon.ai.biblioteka_server.forme.modeli.ModelTabeleUlogovani;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import rs.ac.bg.fon.ai.biblioteka_server.kontroler.Kontroler;

public class ServerskaFormaKontroler {
    
    private final ServerskaForma sf;

    public ServerskaFormaKontroler(ServerskaForma sf) {
        this.sf = sf;
        addActionListener();
    }
    
    public void otvoriFormu() {
        ModelTabeleUlogovani mtu = new ModelTabeleUlogovani(new ArrayList<>());
        sf.getTblUlogovani().setModel(mtu);
        sf.setVisible(true);
        sf.getBtnZaustavi().setEnabled(false);
    }

    private void addActionListener() {
        sf.jmiPodesavanjaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Koordinator.getInstance().otvoriPodesavanja();
            } 
        });
        
        sf.btnPokreniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kontroler.getInstance().pokreniServer();
                sf.getBtnPokreni().setEnabled(false);
                sf.getBtnZaustavi().setEnabled(true);
                sf.getLblStatus().setText("Status: Server je pokrenut.");
            }   
        });
  
        sf.btnZaustaviAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kontroler.getInstance().zaustaviServer();
                sf.getBtnPokreni().setEnabled(true);
                sf.getBtnZaustavi().setEnabled(false);
                sf.getLblStatus().setText("Status: Server je zaustavljen.");
            }      
        });
    }

    public ServerskaForma getSf() {
        return sf;
    }

    public void azurirajUlogovane(Bibliotekar b) {
        ModelTabeleUlogovani mtu = (ModelTabeleUlogovani) sf.getTblUlogovani().getModel();
        mtu.dodajBibliotekara(b);
    }

    public void ukloniIzUlogovanih(Bibliotekar b) {
        ModelTabeleUlogovani mtu = (ModelTabeleUlogovani) sf.getTblUlogovani().getModel();
        mtu.ukloniBibliotekara(b);
    }
    
}
