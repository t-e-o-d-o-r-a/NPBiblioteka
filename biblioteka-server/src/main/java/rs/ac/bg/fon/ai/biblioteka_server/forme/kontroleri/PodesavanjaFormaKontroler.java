package rs.ac.bg.fon.ai.biblioteka_server.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_server.forme.PodesavanjaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_server.konfiguracija.Konfiguracija;

public class PodesavanjaFormaKontroler {
    
    private final PodesavanjaForma pf;

    public PodesavanjaFormaKontroler(PodesavanjaForma pf) {
        this.pf = pf;
        addActionListener();
    }
    
    public void otvoriFormu() {
        pf.setVisible(true);
    }

    private void addActionListener() {
        pf.btnSacuvajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = pf.getTxtUrl().getText().trim();
                String username = pf.getTxtUsername().getText().trim();
                String password = String.valueOf(pf.getTxtPassword().getText().trim());
                
                if (url.isEmpty()) {
                    JOptionPane.showMessageDialog(pf, "URL mora da bude unet!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(pf, "Username mora da bude uneto!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(!(url.contains("jdbc:mysql://"))) {
                    JOptionPane.showMessageDialog(pf, "URL format nije ispravan! \nIspravan format je: jdbc:mysql://host_adresa/broj_porta/ime_baze", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(!(url.contains("jdbc:mysql://localhost:"))) {
                    JOptionPane.showMessageDialog(pf, "URL format nije ispravan! \nIspravan format je: jdbc:mysql://host_adresa/broj_porta/ime_baze", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Konfiguracija.getInstance().setProperty("url", url);
                Konfiguracija.getInstance().setProperty("username", username);
                Konfiguracija.getInstance().setProperty("password", password);
                Konfiguracija.getInstance().sacuvajIzmene();
                
                JOptionPane.showMessageDialog(pf, "Promene su sacuvane.");
                pf.dispose();
            }
        });
    }
  
}
