package rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.StavkaPozajmice;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleStavka extends AbstractTableModel {

    private List<StavkaPozajmice> stavke;
    private String[] kolone = {"Rbr", "Naslov", "Autor", "Izdavac", "Datum vracanja"};
    
    public ModelTabeleStavka(List<StavkaPozajmice> lista) {
        this.stavke = lista;
    }
    
    @Override
    public int getRowCount() {
        if (stavke == null) return 0;
        return stavke.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaPozajmice s = stavke.get(rowIndex);
        switch (columnIndex) {
            case 0: return rowIndex + 1;
            case 1: return s.getKnjiga().getNaslov();
            case 2: return s.getKnjiga().getAutor();
            case 3: return s.getKnjiga().getIzdavac();
            case 4: 
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                String datumStr = df.format(s.getDatumDo());
                return datumStr;
            default:
                return "N/A";
        }
    }

    public List<StavkaPozajmice> getStavke() {
        return stavke;
    }
    
    public StavkaPozajmice getStavkaAt(int red) {
        return stavke.get(red);
    }
    
    public void dodajStavku(StavkaPozajmice s) {
        stavke.add(s);
        fireTableDataChanged();
    }
    
    public void ukloniStavku(int index) {
        stavke.remove(index);
        fireTableDataChanged();
    }

    public void setStavke(List<StavkaPozajmice> stavke) {
        this.stavke = stavke;
        fireTableDataChanged();
    }
    
    
    
}

