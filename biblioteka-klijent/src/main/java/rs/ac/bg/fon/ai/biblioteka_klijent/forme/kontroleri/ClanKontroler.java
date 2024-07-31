package rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.ClanForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator.Koordinator;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.util.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija.Komunikacija;

public class ClanKontroler {
    
    private final ClanForma cf;

    public ClanKontroler(ClanForma cf) {
        this.cf = cf;
        addActionListeners();
    }
    
    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        cf.setVisible(true);
    }
    
    private void pripremiFormu(FormaMod mod) {
        switch (mod) {
            case DODAVANJE:
                cf.getBtnIzmeni().setEnabled(false);
                cf.getBtnObrisi().setEnabled(false);
                cf.getBtnOmoguciIzmene().setEnabled(false);
                cf.getBtnSacuvaj().setEnabled(true);
                cf.getTxtId().setEnabled(false);
                break;
            case IZMENA:
                cf.getBtnIzmeni().setEnabled(true);
                cf.getBtnSacuvaj().setEnabled(false);
                cf.getBtnObrisi().setEnabled(false);
                cf.getBtnOmoguciIzmene().setEnabled(false);
                cf.getTxtId().setEnabled(false);
                cf.getTxtIme().setEditable(true);
                cf.getTxtPrezime().setEditable(true);
                cf.getTxtBrTel().setEditable(true);
                cf.getLblObjasnjenje().setVisible(true);
                
                int clanIdIzmena = ((Clan) Koordinator.getInstance().vratiParametar("clan pregled")).getClanID();
                Clan zaIzmenu = ucitajClana(clanIdIzmena, mod);
                cf.getTxtId().setText(zaIzmenu.getClanID()+"");
                cf.getTxtIme().setText(zaIzmenu.getIme());
                cf.getTxtPrezime().setText(zaIzmenu.getPrezime());
                cf.getTxtBrTel().setText(zaIzmenu.getBrojTelefona());
                break;
            case PRIKAZ:
                cf.getBtnIzmeni().setEnabled(false);
                cf.getBtnSacuvaj().setEnabled(false);
                cf.getBtnObrisi().setEnabled(true);
                cf.getBtnOmoguciIzmene().setEnabled(true);
                cf.getTxtId().setEditable(false);
                cf.getTxtIme().setEditable(false);
                cf.getTxtPrezime().setEditable(false);
                cf.getTxtBrTel().setEditable(false);
                cf.getLblObjasnjenje().setVisible(false);
                
                int clanIdPrikaz = ((Clan) Koordinator.getInstance().vratiParametar("clan pregled")).getClanID();
                Clan odabrani = ucitajClana(clanIdPrikaz, mod);
                cf.getTxtId().setText(odabrani.getClanID()+"");
                cf.getTxtIme().setText(odabrani.getIme());
                cf.getTxtPrezime().setText(odabrani.getPrezime());
                cf.getTxtBrTel().setText(odabrani.getBrojTelefona());
                break;
            default:
                throw new AssertionError();
        }
    }

    private void addActionListeners() {
        cf.btnSacuvajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacuvaj();
            }

            private void sacuvaj() {
            	System.out.println("Cuvanje...");
                try {
                    String ime = cf.getTxtIme().getText().trim();
                    String prezime = cf.getTxtPrezime().getText().trim();
                    String brojTelefona = cf.getTxtBrTel().getText().trim();
                    
                    if (!validacija(ime, prezime, brojTelefona)) return;
                    
                    Clan c = new Clan(0, ime, prezime, brojTelefona);
                    
                    //proveriti da li vec postoji taj clan u bazi:
                    System.out.println("ovde");
                    List<Clan> clanoviIzBaze = Komunikacija.getInstance().vratiClanoveIme(c.getIme());
                    for (Clan cb : clanoviIzBaze) {
                        if (cb.equals(c)) {
                            JOptionPane.showMessageDialog(cf, "Sistem ne moze da kreira clana.", "Greska", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(cf, "Clan sa ovim podacima vec postoji.", "Greska", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    
                    try {
                        Komunikacija.getInstance().dodajClana(c);
                        JOptionPane.showMessageDialog(cf, "Sistem je uspesno kreirao clana.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        cf.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(cf, "Sistem ne moze da kreira clana.", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(cf, "Sistem ne moze da kreira clana.", "Greska", JOptionPane.ERROR_MESSAGE);
                }   
            }       
        });
        
        cf.btnIzmeniAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.valueOf(cf.getTxtId().getText());
                    String ime = cf.getTxtIme().getText().trim();
                    String prezime = cf.getTxtPrezime().getText().trim();
                    String brojTelefona = cf.getTxtBrTel().getText().trim();
                    
                    if (!validacija(ime, prezime, brojTelefona)) return;
                    
                    Clan c = new Clan(id, ime, prezime, brojTelefona);
                    
                    //proveriti da li vec postoji taj clan u bazi:
                    List<Clan> clanoviIzBaze = Komunikacija.getInstance().vratiClanoveIme(c.getIme());
                    for (Clan cb : clanoviIzBaze) {
                        if (cb.equals(c)) {
                            JOptionPane.showMessageDialog(cf, "Sistem ne moze da izmeni clana.", "Greska", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(cf, "Clan sa ovim podacima vec postoji.", "Greska", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    
                    try {
                        Komunikacija.getInstance().izmeniClana(c);
                        JOptionPane.showMessageDialog(cf, "Sistem je uspesno izmenio clana.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        cf.dispose();
                        Koordinator.getInstance().osveziPregledClanovaFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(cf, "Sistem ne moze da izmeni clana.", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cf, "Sistem ne moze da izmeni clana.", "Greska", JOptionPane.ERROR_MESSAGE);
                }  
            }
        });
        
        cf.btnObrisiAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.valueOf(cf.getTxtId().getText());
                    String ime = cf.getTxtIme().getText().trim();
                    String prezime = cf.getTxtPrezime().getText().trim();
                    String brojTelefona = cf.getTxtBrTel().getText().trim();
                    
                    Clan c = new Clan(id, ime, prezime, brojTelefona);
                    
                    Komunikacija.getInstance().obrisiClana(c);
                    JOptionPane.showMessageDialog(cf, "Sistem je uspesno obrisao clana.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    cf.dispose();
                    Koordinator.getInstance().osveziPregledClanovaFormu();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cf, "Sistem ne moze da obrise clana.", "Greska", JOptionPane.ERROR_MESSAGE);
                }   
            }
        });
        
        cf.btnOmoguciIzmeneAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu(FormaMod.IZMENA);
            }
        });
        
    }
    
    private boolean validacija(String ime, String prezime, String brojTelefona) {
        if (ime.isEmpty()) {
            JOptionPane.showMessageDialog(cf, "Morate uneti ime clana.", "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (prezime.isEmpty()) {
            JOptionPane.showMessageDialog(cf, "Morate uneti prezime clana.", "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        } 
        if (brojTelefona.isEmpty()) {
            JOptionPane.showMessageDialog(cf, "Morate uneti broj telefona clana.", "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!brojTelefona.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(cf, "Broj telefona mora da sadrzi samo cifre.", "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (brojTelefona.length() > 10) {
            JOptionPane.showMessageDialog(cf, "Broj telefona ne sme da bude duzi od 10 cifara", "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        } 

        return true;
    }

    private Clan ucitajClana(int clanId, FormaMod mod) {
        try {
            Clan c = Komunikacija.getInstance().ucitajClana(clanId);
            if (c == null) throw new Exception();
            if (mod == FormaMod.PRIKAZ) JOptionPane.showMessageDialog(cf, "Sistem je ucitao clana.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            return c;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(cf, "Sistem ne moze da ucita clana.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
        
        cf.dispose();
        return null;
    }
}
