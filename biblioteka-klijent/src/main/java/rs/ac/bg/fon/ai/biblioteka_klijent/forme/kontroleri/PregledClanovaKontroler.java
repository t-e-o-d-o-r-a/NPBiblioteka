package rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.PregledClanovaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator.Koordinator;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli.ModelTabeleClan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija.Komunikacija;

public class PregledClanovaKontroler {
    
    private final PregledClanovaForma pcf;

    public PregledClanovaKontroler(PregledClanovaForma pcf) {
        this.pcf = pcf;
        addActionListener();
    }
    
    public void otvoriFormu() {
        pripremiFormu();
        pcf.setVisible(true);
    }

    public PregledClanovaForma getPcf() {
        return pcf;
    }
    
    public void pripremiFormu() {
        try {
            List<Clan> clanovi = Komunikacija.getInstance().ucitajSveClanove();
            ModelTabeleClan mtc = new ModelTabeleClan(clanovi);
            pcf.getTblClanovi().setModel(mtc);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pcf, "Greska pri ucitavanju clanova.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListener() {
        pcf.btnDetaljiAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pcf.getTblClanovi().getSelectedRow();
                if (red >= 0) {
                    Clan izabrani = ((ModelTabeleClan) pcf.getTblClanovi().getModel()).getClanAt(red);
                    Koordinator.getInstance().dodajParametar("clan pregled", izabrani);
                    Koordinator.getInstance().otvoriDetaljeClana();
                } else {
                    JOptionPane.showMessageDialog(pcf, "Morate izabrati clana iz tabele.");
                }
            }  
        });
        
        pcf.btnResetujPretraguAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
            }
        });
        
        pcf.btnPretraziAddActionListener(new ActionListener() {        
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String imeClana = pcf.getTxtImePretraga().getText().trim();
                    if (imeClana.isEmpty()) {
                        JOptionPane.showMessageDialog(pcf, "Morate uneti ime clana za pretragu.", "Pretraga", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    List<Clan> clanovi = Komunikacija.getInstance().vratiClanoveIme(imeClana);
                    
                    if (clanovi.isEmpty()) {
                        JOptionPane.showMessageDialog(pcf, "Sistem ne moze da pronadje clanove po zadatoj vrednosti", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(pcf, "Sistem je pronasao clanove po zadatoj vrednosti", "Pretraga", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleClan ctm = (ModelTabeleClan) pcf.getTblClanovi().getModel();
                    ctm.setLista(clanovi);
                    
                    pcf.getTxtImePretraga().setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pcf, "Sistem ne moze da pronadje clanove po zadatoj vrednosti", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    }

}
