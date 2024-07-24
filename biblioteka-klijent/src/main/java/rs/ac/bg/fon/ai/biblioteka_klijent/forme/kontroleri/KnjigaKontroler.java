package rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Autor;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Format;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Izdavac;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.KnjigaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator.Koordinator;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.util.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija.Komunikacija;

public class KnjigaKontroler {
    
    private final KnjigaForma kf;

    public KnjigaKontroler(KnjigaForma kf) {
        this.kf = kf;
        addActionListener();
    }
    
    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        kf.setVisible(true);
    }

    private void addActionListener() {
        kf.btnOmoguciIzmeneAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu(FormaMod.IZMENA);
            }
        });
        
        kf.btnSacuvajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacuvaj();
            }

            private void sacuvaj() {
                try {
                    if (kf.getCbbAutor().getSelectedIndex() == -1 || 
                            kf.getCbbIzdavac().getSelectedIndex() == -1 || kf.getCbbFormat().getSelectedIndex() == -1) {
                        JOptionPane.showMessageDialog(kf, "Sistem ne moze da zapamti knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(kf, "Morate popuniti sva polja.", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int godina,br;
                    try {
                        godina = Integer.valueOf(kf.getTxtGodina().getText().trim());
                        br = Integer.valueOf(kf.getTxtBrojPrimeraka().getText().trim());
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(kf, "Sistem ne moze da zapamti knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(kf, "Morate uneti broj za godinu i broj izdanja.", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String naslov = kf.getTxtNaslov().getText().trim();
                    Autor autor = (Autor) kf.getCbbAutor().getSelectedItem();
                    Izdavac izdavac = (Izdavac) kf.getCbbIzdavac().getSelectedItem();
                    Format format = (Format) kf.getCbbFormat().getSelectedItem();
                    
                    if (!validacija(naslov, godina, br)) return;
                    
                    Knjiga knjiga = new Knjiga(-1, naslov, br, godina, autor, izdavac, format);
                    
                    // proveriti da li vec postoji u bazi
                    List<Knjiga> knjigeIzBaze = Komunikacija.getInstance().vratiKnjigeNaslovAutor(knjiga);
                    for (Knjiga k : knjigeIzBaze) {
                        if (knjiga.equals(k)) {
                            JOptionPane.showMessageDialog(kf, "Sistem ne moze da zapamti knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(kf, "Knjiga sa ovim podacima vec postoji.", "Greska", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    
                    Komunikacija.getInstance().sacuvajKnjigu(knjiga);
                    JOptionPane.showMessageDialog(kf, "Sistem je zapamtio knjigu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    kf.dispose();
                    
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kf, "Sistem ne moze da zapamti knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                }   
            }
        });
        
        kf.btnObrisiAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisi();
            }

            private void obrisi() {
                try {
                    Knjiga k = (Knjiga) Koordinator.getInstance().vratiParametar("knjiga detalji");
                    Komunikacija.getInstance().obrisiKnjigu(k);
                    JOptionPane.showMessageDialog(kf, "Sistem je uspesno obrisao knjigu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    kf.dispose();
                    Koordinator.getInstance().osveziPregledKnjigaFormu();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kf, "Sistem ne moze da obrise knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        kf.btnIzmeniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni();
            }

            private void izmeni() {
                    try {
                        if (kf.getCbbAutor().getSelectedIndex() == -1 || 
                                kf.getCbbIzdavac().getSelectedIndex() == -1 || kf.getCbbFormat().getSelectedIndex() == -1) {
                            JOptionPane.showMessageDialog(kf, "Sistem ne moze da izmeni knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(kf, "Morate popuniti sva polja.", "Greska", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        int godina,br;
                        try {
                            godina = Integer.valueOf(kf.getTxtGodina().getText().trim());
                            br = Integer.valueOf(kf.getTxtBrojPrimeraka().getText().trim());
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(kf, "Sistem ne moze da izmeni knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(kf, "Morate uneti broj za godinu i broj izdanja.", "Greska", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String naslov = kf.getTxtNaslov().getText().trim();
                        Autor autor = (Autor) kf.getCbbAutor().getSelectedItem();
                        Izdavac izdavac = (Izdavac) kf.getCbbIzdavac().getSelectedItem();
                        Format format = (Format) kf.getCbbFormat().getSelectedItem();
                        int id = Integer.valueOf(kf.getTxtId().getText());
                        
                        if (!validacija(naslov, godina, br)) return;
                        
                        Knjiga knjiga = new Knjiga(id, naslov, br, godina, autor, izdavac, format);
                        
                        // proveriti da li vec postoji u bazi
                        List<Knjiga> knjigeIzBaze = Komunikacija.getInstance().vratiKnjigeNaslovAutor(knjiga);
                        for (Knjiga k : knjigeIzBaze) {
                            if (knjiga.equals(k)) {
                                JOptionPane.showMessageDialog(kf, "Sistem ne moze da izmeni knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                                JOptionPane.showMessageDialog(kf, "Knjiga sa ovim podacima vec postoji.", "Greska", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        
                        try {
                            Komunikacija.getInstance().izmeniKnjigu(knjiga);
                            JOptionPane.showMessageDialog(kf, "Sistem je uspesno izmenio knjigu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                            kf.dispose();
                            Koordinator.getInstance().osveziPregledKnjigaFormu();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(kf, "Sistem nije uspeo da izmeni knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(kf, "Sistem ne moze da izmeni knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                    
            }
        });
    }
    
    
    private boolean validacija(String naslov, int godina, int br) {
        if (godina > 2024) {
            JOptionPane.showMessageDialog(kf, "Godina izdanja ne sme biti veca od tekuce godine.", "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (br < 1) {
            JOptionPane.showMessageDialog(kf, "Uneta knjiga mora imati bar jedno izdanje.", "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (naslov.isEmpty()) {
            JOptionPane.showMessageDialog(kf, "Morate uneti naslov knjige.", "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    private void pripremiFormu(FormaMod mod) {
        popuniCbb();
        
        switch (mod) {
            case DODAVANJE:
                kf.getBtnIzmeni().setEnabled(false);
                kf.getBtnObrisi().setEnabled(false);
                kf.getBtnSacuvaj().setEnabled(true);
                kf.getBtnOmoguciIzmene().setEnabled(false);
                kf.getTxtId().setEditable(false);
                kf.getTxtNaslov().setEditable(true);
                kf.getTxtGodina().setEditable(true);
                kf.getTxtBrojPrimeraka().setEditable(true);
                kf.getCbbAutor().setEnabled(true);
                kf.getCbbFormat().setEnabled(true);
                kf.getCbbIzdavac().setEnabled(true);
                break;
            case IZMENA:
                kf.getBtnIzmeni().setEnabled(true);
                kf.getBtnObrisi().setEnabled(false);
                kf.getBtnSacuvaj().setEnabled(false);
                kf.getBtnOmoguciIzmene().setEnabled(false);
                kf.getTxtId().setEditable(false);
                kf.getTxtNaslov().setEditable(true);
                kf.getTxtGodina().setEditable(true);
                kf.getTxtBrojPrimeraka().setEditable(true);
                kf.getCbbAutor().setEnabled(true);
                kf.getCbbFormat().setEnabled(true);
                kf.getCbbIzdavac().setEnabled(true);
                
                int knjigaIdIzmena = ((Knjiga) Koordinator.getInstance().vratiParametar("knjiga detalji")).getKnjigaID();
                Knjiga k1 = ucitajKnjigu(knjigaIdIzmena, mod);
                kf.getTxtId().setText(k1.getKnjigaID()+"");
                kf.getTxtNaslov().setText(k1.getNaslov());
                kf.getTxtGodina().setText(k1.getGodinaIzdanja()+"");
                kf.getTxtBrojPrimeraka().setText(k1.getBrojPrimeraka()+"");
                kf.getCbbAutor().setSelectedItem(k1.getAutor());
                kf.getCbbFormat().setSelectedItem(k1.getFormat());
                kf.getCbbIzdavac().setSelectedItem(k1.getIzdavac());
                break;    
            case PRIKAZ:
                kf.getBtnIzmeni().setEnabled(false);
                kf.getBtnObrisi().setEnabled(true);
                kf.getBtnSacuvaj().setEnabled(false);
                kf.getBtnOmoguciIzmene().setEnabled(true);
                kf.getTxtId().setEditable(false);
                kf.getTxtNaslov().setEditable(false);
                kf.getTxtGodina().setEditable(false);
                kf.getTxtBrojPrimeraka().setEditable(false);
                kf.getCbbAutor().setEnabled(false);
                kf.getCbbFormat().setEnabled(false);
                kf.getCbbIzdavac().setEnabled(false);
                
                int knjigaIdPrikaz = ((Knjiga) Koordinator.getInstance().vratiParametar("knjiga detalji")).getKnjigaID();
                System.out.println("Knjiga id:" + knjigaIdPrikaz);
                Knjiga k = ucitajKnjigu(knjigaIdPrikaz, mod);
                kf.getTxtId().setText(k.getKnjigaID()+"");
                kf.getTxtNaslov().setText(k.getNaslov());
                kf.getTxtGodina().setText(k.getGodinaIzdanja()+"");
                kf.getTxtBrojPrimeraka().setText(k.getBrojPrimeraka()+"");
                kf.getCbbAutor().setSelectedItem(k.getAutor());
                kf.getCbbFormat().setSelectedItem(k.getFormat());
                kf.getCbbIzdavac().setSelectedItem(k.getIzdavac());
                break;
            default:
                throw new AssertionError();
        }
    }

    private void popuniCbb() {
        //format
        kf.getCbbFormat().removeAll();
        kf.getCbbFormat().setModel(new DefaultComboBoxModel(Format.values()));
        kf.getCbbFormat().setSelectedIndex(-1);
        //izdavac
        try {
        kf.getCbbIzdavac().removeAllItems();
        List<Izdavac> listaIzdavaca = Komunikacija.getInstance().vratiSveIzdavace();
        kf.getCbbIzdavac().setModel(new DefaultComboBoxModel(listaIzdavaca.toArray()));
        kf.getCbbIzdavac().setSelectedIndex(-1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kf, "Sistem ne moze da ucita listu izdavaca.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
        //autor
        try {
        kf.getCbbAutor().removeAllItems();
        List<Autor> listaAutora = Komunikacija.getInstance().vratiSveAutore();
        kf.getCbbAutor().setModel(new DefaultComboBoxModel(listaAutora.toArray()));
        kf.getCbbAutor().setSelectedIndex(-1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(kf, "Sistem ne moze da ucita listu autora.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Knjiga ucitajKnjigu(int knjigaIdIzmena, FormaMod mod) {
        try {
            Knjiga k = Komunikacija.getInstance().ucitajKnjigu(knjigaIdIzmena);
            if (k == null) throw new Exception();
            if (mod == FormaMod.PRIKAZ) JOptionPane.showMessageDialog(kf, "Sistem je ucitao knjigu.", "Greska", JOptionPane.INFORMATION_MESSAGE);
            return k;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(kf, "Sistem ne moze da ucita knjigu.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
        
        kf.dispose();
        return null;
    }
    
}