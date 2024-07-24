package rs.ac.bg.fon.ai.biblioteka_server.repository;

import java.util.List;

public interface Repository<T> {
    
    // genericke metode koje ce se izvrsavati nad bilo kojim objektom i bilo kojom tabelom
    List<T> vratiSve(T param, String uslov) throws Exception;
    void dodaj(T param) throws Exception;
    void promeni(T param) throws Exception;
    void obrisi(T param) throws Exception;
    List<T> vratiSve();
    List<T> filter(T param) throws Exception;
    T pronadji(T param, int id) throws Exception;
    
}
