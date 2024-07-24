package rs.ac.bg.fon.ai.biblioteka_server.forme.modeli;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Bibliotekar;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleUlogovani extends AbstractTableModel {
    
    List<Bibliotekar> ulogovani;
    String[] kolone = {"Ulogovani bibliotekari"};
    
    public ModelTabeleUlogovani(List<Bibliotekar> ulogovani) {
        this.ulogovani = ulogovani;
    }

    @Override
    public int getRowCount() {
        if (ulogovani == null) return 0;
        return ulogovani.size();
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
        Bibliotekar b = ulogovani.get(rowIndex);
        switch (columnIndex) {
            case 0: return b.getIme() + " " + b.getPrezime();
            default:
                return "N/A";
        }
    }

    public List<Bibliotekar> getUlogovani() {
        return ulogovani;
    }

    public void dodajBibliotekara(Bibliotekar b) {
        ulogovani.add(b);
        fireTableDataChanged();
    }

    public void ukloniBibliotekara(Bibliotekar b) {
        ulogovani.remove(b);
        fireTableDataChanged();
    }
    
    
    
}
