package rs.ac.bg.fon.ai.biblioteka_server.repository.db;

import java.sql.*;
import rs.ac.bg.fon.ai.biblioteka_server.konfiguracija.Konfiguracija;

public class DBConnectionFactory { //Konekcija
    
    private static DBConnectionFactory instanca;
    private Connection connection;

    private DBConnectionFactory() {
        
    }
    
    public static DBConnectionFactory getInstance() {
        if (instanca == null) instanca = new DBConnectionFactory();
        return instanca;
    } 

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = Konfiguracija.getInstance().getProperty("url");
            String username = Konfiguracija.getInstance().getProperty("username");
            String password = Konfiguracija.getInstance().getProperty("password");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
       }
        return connection;
    }
    
}
