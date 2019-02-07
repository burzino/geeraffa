/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import dao.*;

/**
 *
 * @author GEERAFFA
 */
public class Model {
    private  final String URL = "jdbc:mysql://localhost:3308/ripetizione"; // url del DB
    private  final String USER = "root"; // login utente da usare per connettersi
    private  final String PWD = ""; // password utente
    
    public Model(){
        registerDriver();
    }
    
    // metodo per registrare il Driver JDBC da usare durante le operazioni
    // di interazione con il DB.
    public  void registerDriver() {
        try { 
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registrato correttamente");
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public ResultSet eseguiQuery(String sql) throws SQLException{
        ResultSet rs = null;
        Statement st = null;
        Connection conn = null;
        try {
            ////registerDriver();
            conn = DriverManager.getConnection(URL, USER, PWD);
            st = conn.createStatement();
            System.out.println(sql);
            st.executeQuery(sql);
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("ESEGUIQUERY ERROR: " + e.getMessage());
        }
        
        return rs;
    }
    
    public  void eseguiNonQuery(String sql) throws SQLException{
        Statement st = null;
        Connection conn = null;
        try {
            //registerDriver();
            conn = DriverManager.getConnection(URL, USER, PWD);
            st = conn.createStatement();
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("ESEGUINONQUERYERROR: " + e.getMessage());
        }
        finally { 
            st.close();
            conn.close();
        }
    }
    
    //Select QUERY
    public  List<Studente> getUtenti() throws SQLException
    {
        List<Studente> lst = new ArrayList<Studente>();
        
        ResultSet rs = null;
        Connection conn = null;
        Statement st = null;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            st = conn.createStatement();
            st.executeQuery("SELECT * FROM Utente order by Cognome, Nome");
            rs = st.getResultSet();
            
            while(rs.next())
            {
                Studente stud = new Studente(rs.getInt("ID_Utente"), rs.getString("Username"),
                        rs.getString("Pwd"), rs.getString("Nome"), rs.getString("Cognome"),
                        rs.getString("Email"), rs.getString("Ruolo"));
                lst.add(stud);
            }
        }
        catch (SQLException e) {
            System.err.println("Login ERROR: " + e.getMessage());
        }
        finally { 
            
        }
        
        return lst;
    }
    
     public  List<Prenotazione> listPrenotazioni() throws SQLException
    {
        List<Prenotazione> lst = new ArrayList<Prenotazione>();
        
        ResultSet rs = null;
        Connection conn = null;
        Statement st = null;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            st = conn.createStatement();            
            
            st.executeQuery("SELECT * FROM Prenotazione "
                + " ORDER BY DTInizio DESC");
            rs = st.getResultSet();
            
            while(rs.next())
            {
                Prenotazione stud = new Prenotazione(rs.getInt("ID_Prenotazione"), rs.getInt("Studente"),
                        rs.getInt("Docente"), rs.getString("Corso"), rs.getString("DTInizio"),
                        rs.getString("DTFine"), rs.getInt("Disdetta"));
                lst.add(stud);
            }
        }
        catch (SQLException e) {
            System.err.println("listPrenotazioni ERROR: " + e.getMessage());
        }
        finally { 
            rs.close();
            st.close();
            conn.close();
        }
        
        return lst;
    }
    
    public  List<Prenotazione> listPrenotazioni(int ID_Studente) throws SQLException
    {
        List<Prenotazione> lst = new ArrayList<Prenotazione>();
        
        ResultSet rs = null;
        Connection conn = null;
        Statement st = null;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            st = conn.createStatement();            
            
            st.executeQuery("SELECT * FROM Prenotazione where " 
                + "Studente=" + ID_Studente
                + " ORDER BY DTInizio DESC");
            rs = st.getResultSet();
            
            while(rs.next())
            {
                Prenotazione stud = new Prenotazione(rs.getInt("ID_Prenotazione"), rs.getInt("Studente"),
                        rs.getInt("Docente"), rs.getString("Corso"), rs.getString("DTInizio"),
                        rs.getString("DTFine"), rs.getInt("Disdetta"));
                lst.add(stud);
            }
        }
        catch (SQLException e) {
            System.err.println("listPrenotazioni ERROR: " + e.getMessage());
        }
        finally { 
            rs.close();
            st.close();
            conn.close();
        }
        
        return lst;
    }
    
    public  List<Prenotazione> listPrenotazioni(String corso, int ID_Studente) throws SQLException
    {
        List<Prenotazione> lst = new ArrayList<Prenotazione>();
        
        ResultSet rs = null;
        Connection conn = null;
        Statement st = null;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            st = conn.createStatement();
            if(corso.equals("tutti") || corso.equals("TUTTI"))
                st.executeQuery("SELECT * FROM Prenotazione where Studente=" 
                        + ID_Studente + " ORDER BY DTInizio DESC");
            else
                st.executeQuery("SELECT * FROM Prenotazione where Corso='" + corso 
                        + "' and Studente=" + ID_Studente
                        + " ORDER BY DTInizio DESC");
            rs = st.getResultSet();
            
            while(rs.next())
            {
                Prenotazione stud = new Prenotazione(rs.getInt("ID_Prenotazione"), rs.getInt("Studente"),
                        rs.getInt("Docente"), rs.getString("Corso"), rs.getString("DTInizio"),
                        rs.getString("DTFine"), rs.getInt("Disdetta"));
                lst.add(stud);
            }
        }
        catch (SQLException e) {
            System.err.println("listPrenotazioni ERROR: " + e.getMessage());
        }
        finally { 
            rs.close();
            st.close();
            conn.close();
        }
        
        return lst;
    }
    
    public List<Corso> listCorsiAttivi() throws SQLException
    {
        List<Corso> lst = new ArrayList<Corso>();
        
        ResultSet rs = null;
        Connection conn = null;
        Statement st = null;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            st = conn.createStatement();
            st.executeQuery("SELECT * FROM Corso where Attivo=1 order by Titolo");
            
            rs = st.getResultSet();
            while(rs.next())
            {
                Corso corso = new Corso(rs.getString("Titolo"), 
                        rs.getString("Descrizione"), rs.getString("path"));
                lst.add(corso);
            }
        }
        catch (SQLException e) {
            System.err.println("listCorsi ERROR: " + e.getMessage());
        }
        finally { 
            rs.close();
            st.close();
            conn.close();
        }
        
        return lst;
    }
    
    public List<Corso> listCorsi() throws SQLException
    {
        List<Corso> lst = new ArrayList<Corso>();
        
        ResultSet rs = null;
        Connection conn = null;
        Statement st = null;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            st = conn.createStatement();
            st.executeQuery("SELECT * FROM Corso order by Titolo");
            
            rs = st.getResultSet();
            while(rs.next())
            {
                Corso corso = new Corso(rs.getString("Titolo"), 
                        rs.getString("Descrizione"), rs.getString("path"));
                lst.add(corso);
            }
        }
        catch (SQLException e) {
            System.err.println("listCorsi ERROR: " + e.getMessage());
        }
        finally { 
            rs.close();
            st.close();
            conn.close();
        }
        
        return lst;
    }
    
    public  List<Docente> listDocenti() throws SQLException
    {
        List<Docente> lst = new ArrayList<Docente>();
        
        ResultSet rs = null;
        Connection conn = null;
        Statement st = null;
        
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            st = conn.createStatement();
            st.executeQuery("SELECT * FROM Docente where Attivo=1 order by Cognome");
            
            rs = st.getResultSet();
            while(rs.next())
            {
                Docente docente = new Docente(rs.getInt("ID_Docente"),
                        rs.getString("Cognome"), rs.getString("Nome"), rs.getString("Email"),
                        rs.getInt("Attivo"));
                lst.add(docente);
            }
        }
        catch (SQLException e) {
            System.err.println("listDocenti ERROR: " + e.getMessage());
        }
        finally { 
            rs.close();
            st.close();
            conn.close();
        }
        
        return lst;
    }
}