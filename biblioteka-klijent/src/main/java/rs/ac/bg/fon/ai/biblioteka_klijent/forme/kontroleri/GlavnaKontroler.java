package rs.ac.bg.fon.ai.biblioteka_klijent.forme.kontroleri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.GlavnaForma;
import rs.ac.bg.fon.ai.biblioteka_klijent.forme.koordinator.Koordinator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ai.biblioteka_klijent.komunikacija.Komunikacija;

public class GlavnaKontroler {
    
    private final GlavnaForma gf;

    public GlavnaKontroler(GlavnaForma gf) {
        this.gf = gf;
        addActionListeners();
    }
    
    public void otvoriFormu() {
        ucitajCitat();
        
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
        
        gf.btnClanoviJsonAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try(FileWriter out = new FileWriter("clanovi.json")){
                    List<Clan> clanovi = Komunikacija.getInstance().ucitajSveClanove();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
                    out.write(gson.toJson(clanovi));
                    JOptionPane.showMessageDialog(gf, "Clanovi su uspesno sacuvani u JSON fajl.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(gf, "Greska prilikom cuvanja clanova.", "Greska", JOptionPane.ERROR_MESSAGE);
		}

                
            }
        });
        
        gf.btnKnjigeJsonAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try(FileWriter out = new FileWriter("knjige.json")){
                    List<Knjiga> knjige = Komunikacija.getInstance().vratiSveKnjige();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
                    out.write(gson.toJson(knjige));
                    JOptionPane.showMessageDialog(gf, "Knjige su uspesno sacuvani u JSON fajl.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(gf, "Greska prilikom cuvanja knjiga.", "Greska", JOptionPane.ERROR_MESSAGE);
		}
                
            }
        });
    }

    public GlavnaForma getGf() {
        return gf;
    }

    private void ucitajCitat() {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/quotes?category=happiness");
            HttpURLConnection konekcija = (HttpURLConnection) url.openConnection(); //otvara se http konekcija
            konekcija.setRequestMethod("GET");
            konekcija.setRequestProperty("X-Api-Key", "n/CxZVW4fT3ES3I7qD0/1Q==BChnJbzwrCpHWzCO"); //postavlja se zaglavlje

            BufferedReader in = new BufferedReader(new InputStreamReader(konekcija.getInputStream())); //za citanje odgovora koji dobijemo
            String linija;
            StringBuffer sadrzaj = new StringBuffer();
            while ((linija = in.readLine()) != null) {
                sadrzaj.append(linija);
            }

            in.close();
            konekcija.disconnect(); //zatvara se konekcija i prekida se veza

            Gson gson = new GsonBuilder().create();
            JsonArray nizCitata = gson.fromJson(sadrzaj.toString(), JsonArray.class); //parsiramo odgovor u JSONArray
            JsonObject citat = nizCitata.get(0).getAsJsonObject();

            String tekst = citat.get("quote").getAsString(); //izvlacimo podatke iz JSON objekta kao String
            String autor = citat.get("author").getAsString();

            System.out.println("Quote: " + tekst);
            System.out.println("Author: " + autor);
            
            gf.getLblCitat().setText(tekst);
            gf.getLblAutor().setText(autor);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
