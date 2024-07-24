package rs.ac.bg.fon.ai.biblioteka_server.operacije;

import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;
import rs.ac.bg.fon.ai.biblioteka_server.repository.db.DBRepository;
import rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl.DBRepositoryGeneric;

public abstract class ApstraktnaGenerickaOperacija {
    
    protected final Repository broker;

    public ApstraktnaGenerickaOperacija() {
        this.broker = new DBRepositoryGeneric();
    }
    
    public final void izvrsi(Object objekat, String kljuc) throws Exception {
        try {
            preduslovi(objekat);
            zapocniTransakciju();
            izvrsiOperaciju(objekat, kljuc);
            potvrdiTransakciju();            
        } catch(Exception e) {
            e.printStackTrace();
            ponistiTransakciju();
            throw e;
        } finally {
            ugasiKonekciju();
        }
    }

    protected abstract void preduslovi(Object objekat) throws Exception;

    protected abstract void izvrsiOperaciju(Object objekat, String kljuc) throws Exception;

    private void zapocniTransakciju() throws Exception {
        ((DBRepository) broker).connect();
    }

    private void potvrdiTransakciju() throws Exception {
        ((DBRepository) broker).commit();
    }

    private void ponistiTransakciju() throws Exception {
        ((DBRepository) broker).rollback();
    }

    private void ugasiKonekciju() throws Exception {
        ((DBRepository) broker).disconnect();
    }
    
}
