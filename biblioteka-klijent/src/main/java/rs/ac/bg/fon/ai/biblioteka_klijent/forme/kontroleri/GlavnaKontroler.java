package rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_klijent.forme.GlavnaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator.Koordinator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija.Komunikacija;

public class GlavnaKontroler {
    
    private final GlavnaForma gf;

    public GlavnaKontroler(GlavnaForma gf) {
        this.gf = gf;
        addActionListeners();
    }
    
    public void otvoriFormu() {
        gf.setVisible(true);
        String imePrezime = Koordinator.getInstance().getUlogovaniBibliotekar().getIme() + " " + Koordinator.getInstance().getUlogovaniBibliotekar().getPrezime();
        gf.getTxtBibliotekar().setText(imePrezime);
    }

    private void addActionListeners() {
        gf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Komunikacija.getInstance().krajRada();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        gf.btnKrajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Komunikacija.getInstance().krajRada();
                    JOptionPane.showMessageDialog(gf, "Dovidjenja!", "Kraj rada", JOptionPane.INFORMATION_MESSAGE);
                    gf.dispose();
                    System.exit(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        gf.jmiDodajClanaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Koordinator.getInstance().otvoriClanFormu();
            }  
        });
        
        gf.jmiPregledClanovaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Koordinator.getInstance().otvoriPregledClanovaFormu();
            }
        });
        
        gf.jmiDodajKnjiguAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Koordinator.getInstance().otvoriKnjigaFormu();
            }
        });
        
        gf.jmiPregledKnjigaaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Koordinator.getInstance().otvoriPregledKnjigaFormu();
            }
        });
        
        gf.jmiKreirajPozajmicuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Koordinator.getInstance().otvotiPozajmicaFormu();
            }
        });
        
        gf.jmiPregledPozajmicaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Koordinator.getInstance().otvoriPregledPozajmicaFormu();
            }
        });
    }

    public GlavnaForma getGf() {
        return gf;
    }
    
    
    
}
