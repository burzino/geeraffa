/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geeraffa;

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
    private static final String URL = "jdbc:mysql://localhost:3308/ripetizione"; // url del DB
    private static final String USER = "root"; // login utente da usare per connettersi
    private static final String PWD = ""; // password utente


    // metodo per registrare il Driver JDBC da usare durante le operazioni
    // di interazione con il DB.
    public static void registerDriver() {
        try { 
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registrato correttamente");
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //Select QUERY
    public static List<Studente> getUtenti()
    {
        List<Studente> lst = new ArrayList<Studente>();
        
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Utente");
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
        };
        
        return lst;
    }
    
    public static ResultSet login(String user, String pwd)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Utente where ((username='"
                    + user + "' or Email='" + user + "')  and Pwd='" + pwd + "')");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("Login ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static ResultSet getCorsi()
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Corso");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getCorsi ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static ResultSet getRuoli_NoAdmin()
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Ruolo where not(Nome = 'Admin')");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getRuoli ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static int getLastID_Utente()
    {
        int id = 0;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT MAX(ID_Utente) FROM Utente");
            ResultSet rs = st.getResultSet();
            if(rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("getLastID_Utente: " + e.getMessage());
        };
        return id;
    }
    public static ResultSet getDocenti()
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Docente");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getDocenti ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    public static ResultSet getPrenotazioni()
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM prenotazione");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getPrenotazioni ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static ResultSet getDocentiCorso(String corso)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM corsodocente WHERE corso='" + corso + "'");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getDocentiCorso ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    //Update QUERY
    public static void insUtente(String username, String pwd, String nome, String cognome, String email, String ruolo) {
        Studente stud = new Studente(getLastID_Utente(), username, pwd, nome, cognome, email, ruolo);
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            sql = "INSERT INTO Utente(Username, Pwd ,Nome, Cognome, Email, Ruolo) VALUES("
                    + "'" + username + "', '" + pwd + "','" + nome 
                    + "', '" + cognome + "', '" + email + "','" + ruolo + "')";
            
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("insUtente ERROR: " + e.getMessage());
        }
        
    }
    public static void updateCorso(String titolo, String descrizione) {
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            sql = "UPDATE CORSO SET TITOLO ='" + titolo + "',DESCRIZIONE = '" + descrizione + "' WHERE titolo ='" + titolo +"'";
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("updateCorso ERROR: " + e.getMessage());
        }
        
    }
    
    public static void deleteCorso(String titolo) {
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            sql = "DELETE FROM corso WHERE titolo ='" + titolo +"'";
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("deleteCorso ERROR: " + e.getMessage());
        }
        
    }
    public static void addCorso(String titolo, String descrizione) {
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            sql = "INSERT INTO Corso(Titolo, Descrizione) VALUES('"
                    + titolo + "', '" + descrizione + "')";
            
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("insCorso ERROR: " + e.getMessage());
        }
        
    }
    public static void insDocente(String nome, String cognome, String email, String dataNascita) {
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            sql = "INSERT INTO Docente(nome, cognome ,email, dataNascita) VALUES("
                    + "'" + nome + "', '" + cognome + "','" + email 
                    + "', '" + dataNascita + "')";
            
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("insDocente ERROR: " + e.getMessage());
        }
        
    }
}
