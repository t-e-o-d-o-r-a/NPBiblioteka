package rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.StavkaPozajmice;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli.ModelTabeleClan;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli.ModelTabelePozajmica;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli.ModelTabeleStavka;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.PregledPozajmicaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija.Komunikacija;

public class PregledPozajmicaKontroler {
    
    private final PregledPozajmicaForma ppf;
    private List<StavkaPozajmice> zaVracanje = new ArrayList<>();

    public PregledPozajmicaKontroler(PregledPozajmicaForma ppf) {
        this.ppf = ppf;
        addActionListener();
    }
    
    public void otvoriFormu() {
        pripremiFormu();
        ppf.setVisible(true);
    }

    private void addActionListener() {
        ppf.btnResetujPretraguAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
            }
        });
        
        ppf.btnPretraziAddActionListener(new ActionListener() {        
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String imeClana = ppf.getTxtIme().getText().trim();
                    if (imeClana.isEmpty()) {
                        JOptionPane.showMessageDialog(ppf, "Morate uneti ime clana za pretragu.", "Pretraga", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    List<Clan> clanovi = Komunikacija.getInstance().vratiClanoveIme(imeClana);
                    
                    if (clanovi.isEmpty()) {
                        JOptionPane.showMessageDialog(ppf, "Sistem ne moze da pronadje clanove po zadatoj vrednosti", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(ppf, "Sistem je pronasao clanove po zadatoj vrednosti", "Pretraga", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleClan ctm = (ModelTabeleClan) ppf.getTblClanovi().getModel();
                    ctm.setLista(clanovi);
                    
                    ppf.getTxtIme().setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ppf, "Sistem ne moze da pronadje clanove po zadatoj vrednosti", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        ppf.btnPrikaziPozajmiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int red = ppf.getTblClanovi().getSelectedRow();
                    if (red < 0) {
                        JOptionPane.showMessageDialog(ppf, "Morate izabrati clana iz tabele.");
                        return;
                    }
                    
                    ((ModelTabeleStavka) ppf.getTblStavke().getModel()).setStavke(new ArrayList<>());
                    zaVracanje.clear();
                    
                    Clan clan = ((ModelTabeleClan) ppf.getTblClanovi().getModel()).getClanAt(red);
                    List<Pozajmica> pozajmiceClana = Komunikacija.getInstance().vratiPozajmiceClana(clan);
                    System.out.println("pozajmice: " + pozajmiceClana);
                    ModelTabelePozajmica mtp = (ModelTabelePozajmica) ppf.getTblPozajmice().getModel();
                    mtp.setPozajmice(pozajmiceClana);
                    
                    if (pozajmiceClana.isEmpty()) {
                        JOptionPane.showMessageDialog(ppf, "Sistem ne moze da pronadje pozajmice zeljenog clana.", "Greska", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(ppf, "Ovaj clan nema ni jednu pozajmicu.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(ppf, "Sistem je ucitao pozajmice zeljenog clana.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ppf, "Sistem ne moze da pronadje pozajmice zeljenog clana.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        ppf.btnDetaljiPozajmiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ppf.getTblPozajmice().getSelectedRow();
                if (red < 0) {
                    JOptionPane.showMessageDialog(ppf, "Sistem ne moze da ucita pozajmicu.", "Pozajmica", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(ppf, "Morate izabrati pozajmicu iz tabele.");
                    return;
                }
                
                ppf.getBtnSacuvajPromene().setEnabled(true);
                ppf.getBtnVratiKnjigu().setEnabled(true);
                zaVracanje.clear();
                
                Pozajmica p = ((ModelTabelePozajmica) ppf.getTblPozajmice().getModel()).getPozajmicaAt(red);
                ppf.getTxtNapomena().setText(p.getNapomena());
                List<StavkaPozajmice> stavke = p.getStavke();
                for (StavkaPozajmice sp : stavke) {
                    sp.setPozajmica(p);
                }
                
                JOptionPane.showMessageDialog(ppf, "Sistem je ucitao pozajmicu.", "Pozajmica", JOptionPane.INFORMATION_MESSAGE);
                
                ModelTabeleStavka mts = (ModelTabeleStavka) ppf.getTblStavke().getModel();
                mts.setStavke(stavke);
                
            }
        });
        
        ppf.btnVratiKnjiguAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ppf.getTblStavke().getSelectedRow();
                if (red < 0) {
                    JOptionPane.showMessageDialog(ppf, "Morate izabrati stavku iz tabele.");
                    return;
                }
                
                ModelTabeleStavka mts = (ModelTabeleStavka) ppf.getTblStavke().getModel();
                StavkaPozajmice sp = mts.getStavkaAt(red);
                zaVracanje.add(sp);
                mts.ukloniStavku(red);
                
                JOptionPane.showMessageDialog(ppf, "Knjiga ce biti razduzena iz pozajmice.", "Knjiga",  JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        ppf.btnSacuvajPromenePozajmiceAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ppf.getTblPozajmice().getSelectedRow();
                Pozajmica p = ((ModelTabelePozajmica) ppf.getTblPozajmice().getModel()).getPozajmicaAt(red);
                p.setNapomena(ppf.getTxtNapomena().getText().trim());
                
                
                try {
                    if (zaVracanje.size() == p.getStavke().size()) {
                    	System.out.println(zaVracanje);
                    	System.out.println(p.getStavke());
                    	p.setStavke(zaVracanje);
                        Komunikacija.getInstance().obrisiPozajmicu(p);
                    } else {
                    	System.out.println(zaVracanje);
                    	System.out.println(p.getStavke());
                    	p.setStavke(zaVracanje);
                        Komunikacija.getInstance().izmeniPozajmicu(p);
                    }
                    
                    JOptionPane.showMessageDialog(ppf, "Sistem je uspesno izmenio pozajmicu.", "Pozajmica",  JOptionPane.INFORMATION_MESSAGE);
                    ppf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ppf, "Sistem ne moze da izmeni pozajmicu.", "Greska",  JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
    }

    private void pripremiFormu() {
        try {
            List<Clan> clanovi = Komunikacija.getInstance().ucitajSveClanove();
            ModelTabeleClan mtc = new ModelTabeleClan(clanovi);
            ppf.getTblClanovi().setModel(mtc);
            
            ModelTabelePozajmica mtp = new ModelTabelePozajmica(new ArrayList<>());
            ppf.getTblPozajmice().setModel(mtp);
            
            ModelTabeleStavka mts = new ModelTabeleStavka(new ArrayList<>());
            ppf.getTblStavke().setModel(mts);
            
            ppf.getBtnSacuvajPromene().setEnabled(false);
            ppf.getBtnVratiKnjigu().setEnabled(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ppf, "Greska pri ucitavanju clanova.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
}
