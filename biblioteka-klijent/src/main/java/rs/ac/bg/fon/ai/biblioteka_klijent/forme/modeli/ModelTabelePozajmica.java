package rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Pozajmica;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabelePozajmica extends AbstractTableModel {
    
    List<Pozajmica> pozajmice;
    String[] kolone = {"Id pozajmice", "Clan", "Datum", "Evidentirao"};
    
    public ModelTabelePozajmica(List<Pozajmica> lista) {
        this.pozajmice = lista;
    }

    @Override
    public int getRowCount() {
        if (kolone == null) return 0;
        return pozajmice.size();
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
        Pozajmica p = pozajmice.get(rowIndex);
        switch (columnIndex) {
            case 0: return p.getPozajmicaID();
            case 1: return p.getClan().getIme() + " " + p.getClan().getPrezime();
            case 2: return p.getDatum();
            case 3: return p.getBibliotekar().getIme() + " " + p.getBibliotekar().getPrezime();
            default:
                return "N/A";
        }
    }

    public List<Pozajmica> getPozajmice() {
        return pozajmice;
    }

    public void setPozajmice(List<Pozajmica> lista) {
        pozajmice = lista;
        fireTableDataChanged();
    }
    
    public Pozajmica getPozajmicaAt(int red) {
        return pozajmice.get(red);
    } 
    
}
