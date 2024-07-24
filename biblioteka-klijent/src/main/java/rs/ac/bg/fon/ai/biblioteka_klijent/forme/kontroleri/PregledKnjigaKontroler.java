package rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.PregledKnjigaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator.Koordinator;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli.ModelTabeleKnjiga;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija.Komunikacija;

public class PregledKnjigaKontroler {
    
    private final PregledKnjigaForma pkf;

    public PregledKnjigaKontroler(PregledKnjigaForma pkf) {
        this.pkf = pkf;
        addActionListener();
    }
    
    public void otvoriFormu() {
        pripremiFormu();
        pkf.setVisible(true);
    }

    private void addActionListener() {
        pkf.btnDetaljiAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkf.getTblKnjige().getSelectedRow();
                if (red >= 0 ) {
                    Knjiga k = ((ModelTabeleKnjiga) pkf.getTblKnjige().getModel()).getKnjigaAt(red);
                    Koordinator.getInstance().dodajParametar("knjiga detalji", k);
                    Koordinator.getInstance().otvoriDetaljeKnjige();    
                } else {
                    JOptionPane.showMessageDialog(pkf, "Morate da izaberete knjigu iz tabele.");
                }   
            }
        });
        
        pkf.btnResetujPretraguAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
            }
        });
        
        pkf.btnPretraziAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (pkf.getCbbAutor().getSelectedIndex() == -1 && pkf.getTxtNaslov().getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(pkf, "Morate uneti naslov knjige ili odabrati autora za pretragu.");
                        return;
                    }
                    
                    String naslov = pkf.getTxtNaslov().getText().trim();
                    Autor autor = (Autor) pkf.getCbbAutor().getSelectedItem();
                    
                    Knjiga k = new Knjiga();
                    k.setAutor(autor);
                    k.setNaslov(naslov);
                    
                    List<Knjiga> knjige = Komunikacija.getInstance().vratiKnjigeNaslovAutor(k);
                    
                    if (knjige.isEmpty()) {
                        JOptionPane.showMessageDialog(pkf, "Sistem ne moze da pronadje knjige po zadatim vrednostima", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(pkf, "Sistem je pronasao knjige po zadatim vrednostima", "Pretraga", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleKnjiga mtk = (ModelTabeleKnjiga) pkf.getTblKnjige().getModel();
                    mtk.setKnjige(knjige);
                    
                    pkf.getTxtNaslov().setText("");
                    pkf.getCbbAutor().setSelectedIndex(-1);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pkf, "Sistem ne moze da pronadje knjige po zadatim vrednostima", "Greska", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
    }

    public void pripremiFormu() {
        try {
            List<Knjiga> sveKnjige = Komunikacija.getInstance().vratiSveKnjige();
            ModelTabeleKnjiga mtk = new ModelTabeleKnjiga(sveKnjige);
            pkf.getTblKnjige().setModel(mtk);
            
            pkf.getCbbAutor().removeAllItems();
            List<Autor> listaAutora = Komunikacija.getInstance().vratiSveAutore();
            pkf.getCbbAutor().setModel(new DefaultComboBoxModel(listaAutora.toArray()));
            pkf.getCbbAutor().setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pkf, "Sistem ne moze da ucita knjige.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
 
    }
    
}
