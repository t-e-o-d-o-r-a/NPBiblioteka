package rs.ac.bg.fon.ai.biblioteka_server.repository.db;

import rs.ac.bg.fon.ai.biblioteka_server.repository.Repository;

public interface DBRepository<T> extends Repository<T> {
    
    // metode konkretno vezane za bazu
    default public void connect() throws Exception {
       DBConnectionFactory.getInstance().getConnection();
    }
    
    default public void disconnect() throws Exception {
       DBConnectionFactory.getInstance().getConnection().close();
    }
    
    default public void commit() throws Exception {
       DBConnectionFactory.getInstance().getConnection().commit();
    }
    
    default public void rollback() throws Exception {
       DBConnectionFactory.getInstance().getConnection().rollback();
    }
    
}
