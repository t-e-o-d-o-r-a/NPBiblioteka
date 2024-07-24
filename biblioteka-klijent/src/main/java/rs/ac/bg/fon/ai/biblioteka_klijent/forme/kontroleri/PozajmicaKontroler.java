package rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.StavkaPozajmice;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.PozajmicaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator.Koordinator;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli.ModelTabeleClan;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli.ModelTabeleKnjiga;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli.ModelTabeleStavka;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija.Komunikacija;

public class PozajmicaKontroler {
    
    private final PozajmicaForma pf;
    private Clan izabraniClan = null;
    private Date datum = new Date();

    public PozajmicaKontroler(PozajmicaForma pf) {
        this.pf = pf;
        addActionListener();
    }

    private void addActionListener() {
        pf.btnResetujPretraguAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    popuniTblClanovi();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pf, "Greska pri ucitavanju clanova.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        pf.btnPretraziAddActionListener(new ActionListener() {        
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String imeClana = pf.getTxtImeClana().getText().trim();
                    if (imeClana.isEmpty()) {
                        JOptionPane.showMessageDialog(pf, "Morate uneti ime clana za pretragu.", "Pretraga", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    List<Clan> clanovi = Komunikacija.getInstance().vratiClanoveIme(imeClana);
                    
                    if (clanovi.isEmpty()) {
                        JOptionPane.showMessageDialog(pf, "Sistem ne moze da pronadje clanove po zadatoj vrednosti", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(pf, "Sistem je pronasao clanove po zadatoj vrednosti", "Pretraga", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleClan ctm = (ModelTabeleClan) pf.getTblClanovi().getModel();
                    ctm.setLista(clanovi);
                    
                    pf.getTxtImeClana().setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pf, "Sistem ne moze da pronadje clanove po zadatoj vrednosti", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        pf.btnPostaviClanaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pf.getTblClanovi().getSelectedRow();
                if (red >= 0) {
                    Clan izabrani = ((ModelTabeleClan) pf.getTblClanovi().getModel()).getClanAt(red);
                    pf.getTxtClan().setText(izabrani.getIme() + " " + izabrani.getPrezime());
                    izabraniClan = izabrani;
                } else {
                    JOptionPane.showMessageDialog(pf, "Morate izabrati clana iz tabele.");
                }
            }
        });
        
        pf.btnResetujKnjigeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    popuniTblKnjige();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pf, "Greska pri ucitavanju knjiga.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        pf.btnPretraziKnjigeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (pf.getCbbAutor().getSelectedIndex() == -1 && pf.getTxtNaslov().getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(pf, "Morate uneti naslov knjige ili odabrati autora za pretragu.");
                        return;
                    }
                    
                    String naslov = pf.getTxtNaslov().getText().trim();
                    Autor autor = (Autor) pf.getCbbAutor().getSelectedItem();
                    
                    Knjiga k = new Knjiga();
                    k.setAutor(autor);
                    k.setNaslov(naslov);
                    
                    List<Knjiga> knjige = Komunikacija.getInstance().vratiKnjigeNaslovAutor(k);
                    
                    if (knjige.isEmpty()) {
                        JOptionPane.showMessageDialog(pf, "Sistem ne moze da pronadje knjige po zadatim vrednostima", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    JOptionPane.showMessageDialog(pf, "Sistem je pronasao knjige po zadatim vrednostima", "Pretraga", JOptionPane.INFORMATION_MESSAGE);
                    ModelTabeleKnjiga mtk = (ModelTabeleKnjiga) pf.getTblKnjige().getModel();
                    mtk.setKnjige(knjige);
                    
                    pf.getTxtNaslov().setText("");
                    pf.getCbbAutor().setSelectedIndex(-1);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pf, "Sistem ne moze da pronadje knjige po zadatim vrednostima", "Greska", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
        
        pf.btnUkloniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pf.getTblStavke().getSelectedRow();
                if (red >= 0) {
                    ModelTabeleStavka mts = (ModelTabeleStavka) pf.getTblStavke().getModel();
                    mts.ukloniStavku(red);
                } else {
                    JOptionPane.showMessageDialog(pf, "Morate izabrati stavku iz tabele.");
                }
            }
        });
        
        pf.btnDodajKnjiguAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pf.getTblKnjige().getSelectedRow();
                if (red >= 0 ) {
                    if (!pf.getTxtDatumDo().getText().trim().isEmpty()) {
                        
                            ModelTabeleStavka mts = (ModelTabeleStavka) pf.getTblStavke().getModel();
                            
                            if (!proveraDatuma(pf.getTxtDatumDo().getText().trim())) return;
                            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                            Date datDo = null;
                            try {
                                datDo = sdf.parse(pf.getTxtDatumDo().getText().trim());
                            } catch (ParseException ex) {
                                Logger.getLogger(PozajmicaKontroler.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            Pozajmica p = new Pozajmica();
                            
                            Knjiga k = ((ModelTabeleKnjiga) pf.getTblKnjige().getModel()).getKnjigaAt(red);
                            if (k.getBrojPrimeraka() <= 0 ) {
                                JOptionPane.showMessageDialog(pf, "Ova knjiga trenutno nije dostupna.", "Greska", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            
                            StavkaPozajmice sp = new StavkaPozajmice(p, mts.getStavke().size(), datDo, k);
                            
                            List<StavkaPozajmice> stavke = mts.getStavke();
                            for (StavkaPozajmice s : stavke) {
                                if (s.equals(sp)) {
                                    JOptionPane.showMessageDialog(pf, "Ova stavka je vec uneta.", "Greska", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            mts.dodajStavku(sp);
                            
                         
                    } else {
                        JOptionPane.showMessageDialog(pf, "Morate uneti datum do kojeg je potrebno vratiti knjigu.");
                    }
                } else {
                    JOptionPane.showMessageDialog(pf, "Morate da izaberete knjigu iz tabele.");
                }   
            }
        });
    
        pf.btnKreirajPozajmicuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kreirajPozajmicu();
            }

            private void kreirajPozajmicu() {
                try {
                    if (izabraniClan == null) {
                        JOptionPane.showMessageDialog(pf, "Sistem ne moze da sacuva pozajmicu.", "Greska", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(pf, "Morate da izaberete clana iz tabele.");
                        return;
                    }
                    
                    ModelTabeleStavka mts = (ModelTabeleStavka) pf.getTblStavke().getModel();
                    if (mts.getStavke().isEmpty()) {
                        JOptionPane.showMessageDialog(pf, "Sistem ne moze da sacuva pozajmicu.", "Greska", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(pf, "Pozajmica mora da ima bar jednu stavku.");
                        return;
                    }
                    
                    Pozajmica p = new Pozajmica();
                    p.setBibliotekar(Koordinator.getInstance().getUlogovaniBibliotekar());
                    p.setClan(izabraniClan);
                    p.setDatum(datum);
                    if (pf.getTxtNapomena().getText().trim().isEmpty()) {
                        p.setNapomena("/");
                    } else {
                        p.setNapomena(pf.getTxtNapomena().getText().trim());
                    }
                    List<StavkaPozajmice> stavke = mts.getStavke();
                    for (int i = 0; i < stavke.size(); i++) {
                        stavke.get(i).setStavkaID(i+1);
                    }
                    p.setStavke(stavke);
                    Komunikacija.getInstance().sacuvajPozajmicu(p);
                    JOptionPane.showMessageDialog(pf, "Sistem je sacuvao pozajmicu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    pf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pf, "Sistem ne moze da sacuva pozajmicu.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void otvoriFormu() {
        pripremiFormu();
        pf.setVisible(true);
    }

    private void pripremiFormu() {
        try {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String datumStr = df.format(datum);
            pf.getTxtDatum().setText(datumStr);
            
            popuniTblKnjige();
            
            pf.getCbbAutor().removeAllItems();
            List<Autor> listaAutora = Komunikacija.getInstance().vratiSveAutore();
            pf.getCbbAutor().setModel(new DefaultComboBoxModel(listaAutora.toArray()));
            pf.getCbbAutor().setSelectedIndex(-1);
            
            popuniTblClanovi();
            
            ModelTabeleStavka mts = new ModelTabeleStavka(new ArrayList<>());
            pf.getTblStavke().setModel(mts);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(pf, "Greska pri ucitavanju.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void popuniTblKnjige() throws IOException, Exception {
        List<Knjiga> sveKnjige = Komunikacija.getInstance().vratiSveKnjige();
        ModelTabeleKnjiga mtk = new ModelTabeleKnjiga(sveKnjige);
        pf.getTblKnjige().setModel(mtk);
    }
    
    public void popuniTblClanovi() throws IOException, Exception {
        List<Clan> clanovi = Komunikacija.getInstance().ucitajSveClanove();
        ModelTabeleClan mtc = new ModelTabeleClan(clanovi);
        pf.getTblClanovi().setModel(mtc);
    }
    
    public boolean proveraDatuma(String dat) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date datDo;
        try {
            datDo = sdf.parse(dat);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(pf, "Morate uneti datum do kojeg je potrebno vratiti knjigu u ispravnom formatu.", "Greska datum", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String delovi[] = dat.split("\\.");
        if (delovi.length != 3) {
            return false;
        }
        int dan = Integer.parseInt(delovi[0]);
        int mesec = Integer.parseInt(delovi[1]);
        int godina = Integer.parseInt(delovi[2]);
                            
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (godina < year) {
            JOptionPane.showMessageDialog(pf, "Niste lepo uneli godinu", "Greska", JOptionPane.ERROR_MESSAGE);

            return false;
        }
        if (mesec < 1 || mesec > 12) {
            JOptionPane.showMessageDialog(pf, "Niste lepo uneli mesec", "Greska", JOptionPane.ERROR_MESSAGE);

            return false;
        }
        if (mesec == 2) {
            if (dan < 1 || dan > 28) {
                JOptionPane.showMessageDialog(pf, "Niste lepo uneli dan", "Greska", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (mesec == 1 || mesec == 3 || mesec == 5 || mesec == 7 || mesec == 8 || mesec == 10 || mesec == 12) {
            if (dan < 1 || dan > 31) {
                JOptionPane.showMessageDialog(pf, "Niste lepo uneli dan", "Greska", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (mesec == 4 || mesec == 6 || mesec == 9 || mesec == 11) {
            if (dan < 1 || dan > 30) {
                JOptionPane.showMessageDialog(pf, "Niste lepo uneli dan", "Greska", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        if (datDo.before(datum)) {
            JOptionPane.showMessageDialog(pf, "Datum mora biti u buducnosti.", "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
}

