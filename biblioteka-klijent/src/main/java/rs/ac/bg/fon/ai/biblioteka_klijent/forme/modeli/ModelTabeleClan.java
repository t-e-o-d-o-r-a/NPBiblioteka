package rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Clan;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleClan extends AbstractTableModel {

    private List<Clan> lista;
    private String[] kolone = {"Id", "Ime", "Prezime", "Broj telefona"};
    
    public ModelTabeleClan(List<Clan> lista) {
        this.lista = lista;
    }
        
    @Override
    public int getRowCount() {
        if (lista == null) return 0;
        return lista.size();
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
        Clan c = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return c.getClanID();
            case 1: return c.getIme();
            case 2: return c.getPrezime();
            case 3: return c.getBrojTelefona();
            default:
                return "N/A";
        }
    }

    public List<Clan> getLista() {
        return lista;
    }
    
    public Clan getClanAt(int red) {
        return lista.get(red);
    }

    public void setLista(List<Clan> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }
    
    
}
