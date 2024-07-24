package rs.ac.bg.fon.ai.biblioteka_server.konfiguracija;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class Konfiguracija {
    
    private static Konfiguracija instanca;
    private Properties konfiguracija;

    private Konfiguracija() {
        try {
        	String projectRootPath = Paths.get("").toAbsolutePath().toString();
            String configFilePath = Paths.get(projectRootPath, "src", "main", "resources", "config", "dbconfig.properties").toString();
            konfiguracija = new Properties();
            konfiguracija.load(new FileInputStream(configFilePath));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Konfiguracija getInstance() {
        if (instanca == null) instanca = new Konfiguracija();
        return instanca;
    }
    
    public String getProperty(String key) {
        return konfiguracija.getProperty(key, "N/A"); //ako ne postoji kljuc vraca "N/A"
    }
    
    public void setProperty(String key, String value) {
        konfiguracija.setProperty(key, value);
    }
    
    public void sacuvajIzmene() {
        try {
        	String projectRootPath = Paths.get("").toAbsolutePath().toString();
            String configFilePath = Paths.get(projectRootPath, "src", "main", "resources", "config", "dbconfig.properties").toString();
        	
            konfiguracija.store(new FileOutputStream(configFilePath), null);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
