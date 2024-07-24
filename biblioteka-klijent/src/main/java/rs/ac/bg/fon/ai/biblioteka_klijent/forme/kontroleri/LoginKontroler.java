package rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.LoginForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator.Koordinator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija.Komunikacija;

public class LoginKontroler {
    
    private final LoginForma lf;

    public LoginKontroler(LoginForma lf) {
        this.lf = lf;
        addActionListener();
    }
    
    public void otvoriFormu() {
        lf.setVisible(true);
    }

    private void addActionListener() {
        lf.btnLoginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(e);
            }

            private void login(ActionEvent e) {
                try {
                    String korisnickoIme = lf.getTxtKorisnickoIme().getText().trim();
                    String lozinka = String.valueOf(lf.getTxtLozinka().getPassword());
                    
                    if (!validacijaForme(korisnickoIme, lozinka)) return;
                    
                    Bibliotekar bibliotekar = Komunikacija.getInstance().login(korisnickoIme, lozinka);
                    
                    JOptionPane.showMessageDialog(lf, "Uspesno ste se prijavili.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    lf.dispose();
                    
                    Koordinator.getInstance().setUlogovaniBibliotekar(bibliotekar);
                    Koordinator.getInstance().otvoriGlavnuFormu();
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(lf, "Neuspesno prijavljivanje.", "Greska", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }

            private boolean validacijaForme(String korisnickoIme, String lozinka) {
                if (korisnickoIme.isEmpty()) {
                    JOptionPane.showMessageDialog(lf, "Morate uneti korisnicko ime.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (lozinka.isEmpty()) {
                    JOptionPane.showMessageDialog(lf, "Morate uneti lozinku.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                
                return true;
            }
        
        });
        
        lf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Komunikacija.getInstance().krajRadaLogin();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    
    
}

