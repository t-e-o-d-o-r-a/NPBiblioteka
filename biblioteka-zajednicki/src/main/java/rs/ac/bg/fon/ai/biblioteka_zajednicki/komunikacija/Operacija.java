package rs.ac.bg.fon.ai.biblioteka_zajednicki.komunikacija;

import java.io.Serializable;

public enum Operacija implements Serializable {
    
    LOGIN,
    KRAJ_RADA,
    KRAJ_RADA_LOGIN,
    SACUVAJ_CLANA,
    VRATI_SVE_CLANOVE,
    IZMENI_CLANA,
    OBRISI_CLANA,
    NADJI_CLANOVE,
    UCITAJ_CLANA,
    VRATI_SVE_AUTORE,
    VRATI_SVE_IZDAVACE,
    SACUVAJ_KNJIGU,
    VRATI_SVE_KNJIGE,
    IZMENI_KNJIGU,
    OBRISI_KNJIGU,
    NADJI_KNJIGE,
    UCITAJ_KNJIGU,
    SACUVAJ_POZAJMICU,
    VRATI_POZAJMICE_CLANA,
    IZMENI_POZAJMICU,
    OBRISI_POZAJMICU,
    
}
