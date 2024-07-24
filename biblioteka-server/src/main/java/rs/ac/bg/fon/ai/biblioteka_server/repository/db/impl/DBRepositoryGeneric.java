package rs.ac.bg.fon.ai.biblioteka_server.repository.db.impl;

import rs.ac.bg.fon.ai.biblioteka_zajednicki.domen.ApstraktniDomenskiObjekat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.biblioteka_server.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ai.biblioteka_server.repository.db.DBRepository;

public class DBRepositoryGeneric implements DBRepository<ApstraktniDomenskiObjekat>{

    @Override
    public List<ApstraktniDomenskiObjekat> vratiSve(ApstraktniDomenskiObjekat param, String uslov) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        String upit = "SELECT * FROM " + param.vratiNazivTabele() + param.join();
        if (uslov != null) {
            upit += uslov;
        }
        
        System.out.println(upit);
        
        Statement st = DBConnectionFactory.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(upit);
        
        lista = param.vratiListu(rs);
        
        rs.close();
        st.close();
        return lista;
    }

    @Override
    public void dodaj(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "INSERT INTO " + param.vratiNazivTabele() + " (" + param.vratiKoloneZaUbacivanje() + ") VALUES (" + param.vratiVrednostiZaUbacivanje() + ")";
        System.out.println(upit);
        
        Statement st = DBConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit, Statement.RETURN_GENERATED_KEYS);
        ResultSet kljuc = st.getGeneratedKeys();
        if (kljuc.next()) {
            int id = kljuc.getInt(1);
            System.out.println("Kljuc za pozajmicu: " + id);
            param.postaviId(id);
            System.out.println(param);
        }
        st.close();
    }

    @Override
    public void promeni(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "UPDATE " + param.vratiNazivTabele() + " SET " + param.vratiVrednostiZaIzmenu() + " WHERE " + param.vratiPrimarniKljuc();
        System.out.println(upit);
        
        Statement st = DBConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }

    @Override
    public void obrisi(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "DELETE FROM " + param.vratiNazivTabele() + " WHERE " + param.vratiPrimarniKljuc();
        System.out.println(upit);
        
        Statement st = DBConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiSve() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ApstraktniDomenskiObjekat> filter(ApstraktniDomenskiObjekat param) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        String upit = "SELECT * FROM " + param.vratiNazivTabele() + param.join() + " WHERE " + param.vratiUslovZaFiltriranje(param);
        
        System.out.println(upit);
        
        Statement st = DBConnectionFactory.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(upit);
        
        lista = param.vratiListu(rs);
        
        rs.close();
        st.close();
        
        return lista;
    }

    @Override
    public ApstraktniDomenskiObjekat pronadji(ApstraktniDomenskiObjekat param, int id) throws Exception {
        ApstraktniDomenskiObjekat obj = null;
        
        String upit = "SELECT * FROM " + param.vratiNazivTabele() + param.join() + " WHERE " + param.vratiPrimarniKljuc();
        
        System.out.println(upit);
        
        Statement st = DBConnectionFactory.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(upit);
        
        obj = param.vratiObjekatIzRS(rs);
        
        rs.close();
        st.close();
        return obj;
    }

      
}
