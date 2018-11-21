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
    private static final String URL = "jdbc:mysql://localhost:3306/ripetizione"; // url del DB
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
            st.executeQuery("SELECT MAX(ID) FROM Utente");
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
    
    //Update QUERY
    public static void insUtente(String username, String pwd, String nome, String cognome, String email, String ruolo) {
        Studente stud = new Studente(getLastID_Utente(), username, pwd, nome, cognome, email, ruolo);
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            sql = "INSERT INTO Utente(ID_Utente, Username, Pwd ,Nome, Cognome, Email, Ruolo) VALUES("
                    + getLastID_Utente() + ",'" + username + "', '" + pwd + "','" + nome 
                    + "', '" + cognome + "', '" + email + "','" + ruolo + "')";
            
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("insUtente ERROR: " + e.getMessage());
        }
        
    }
    public static ResultSet getDocenti()
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Utente WHERE ruolo = 'Docente'");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getCorsi ERROR: " + e.getMessage());
        };
        
        return rs;
    }
}
