package rs.ac.bg.fon.ai.biblioteka_klijent.forme.modeli;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.Knjiga;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabeleKnjiga extends AbstractTableModel {
    
    private List<Knjiga> knjige;
    private String[] kolone = {"Naslov", "Autor", "Izdavac", "Godina izdanja", "Broj primeraka"};
    
    public ModelTabeleKnjiga(List<Knjiga> lista) {
        this.knjige = lista;
    }

    @Override
    public int getRowCount() {
        if (knjige == null) return 0;
        return knjige.size();
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
        Knjiga k = knjige.get(rowIndex);
        switch (columnIndex) {
            case 0: return k.getNaslov();
            case 1: return k.getAutor().toString();
            case 2: return k.getIzdavac().getNaziv();
            case 3: return k.getGodinaIzdanja()+"";
            case 4: return k.getBrojPrimeraka();
            default:
                throw new AssertionError();
        }
    }

    public List<Knjiga> getKnjige() {
        return knjige;
    }
    
    public Knjiga getKnjigaAt(int red) {
        return knjige.get(red);
    }

    public void setKnjige(List<Knjiga> knjige) {
        this.knjige = knjige;
        fireTableDataChanged();
    }
    
    
}
