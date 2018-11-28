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
    
    public static ResultSet eseguiQuery(String sql){
        ResultSet rs = null;
        try {
            registerDriver();
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            System.out.println(sql);
            st.executeQuery(sql);
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("ESEGUIQUERY ERROR: " + e.getMessage());
        };
        return rs;
    }
    
    public static void eseguiNonQuery(String sql){
        try {
            registerDriver();
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("ESEGUINONQUERYERROR: " + e.getMessage());
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
    
    public static List<Prenotazione> listPrenotazioni(String corso)
    {
        List<Prenotazione> lst = new ArrayList<Prenotazione>();
        
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            if(corso.equals("tutti"))
                st.executeQuery("SELECT * FROM Prenotazione where Disdetta=0");
            else
                st.executeQuery("SELECT * FROM Prenotazione where Corso='" + corso + "' and Disdetta=0");
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
    public static int getLastID_Docente()
    {
        int id = 0;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT MAX(ID_Docente) FROM Docente");
            ResultSet rs = st.getResultSet();
            if(rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("getLastID_Docente: " + e.getMessage());
        };
        return id;
    }
    
    public static ResultSet getDocenti()
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Docente WHERE Attivo = 1");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getDocenti ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    public static ResultSet getIDDocenti(String email)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Docente WHERE email ='" + email + "'");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getDocenti ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static ResultSet getCorsoDocente(String corso)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM CorsoDocente where Corso='" + corso + "'");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getCorsoDocente ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static ResultSet getPrenotazioniPersonali(int ID_Utente)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Prenotazione where Studente=" + ID_Utente + " and Disdetta=0");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getPrenotazioni ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static ResultSet getPrenotazioniCorso(String corso, int ID_Utente)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Prenotazione where Corso='" + corso +"' and Studente=" + ID_Utente + " and Disdetta=0");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getPrenotazioniCorso ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static ResultSet getPrenotazioniCorsoDocente(String corso, int ID_Docente, int ID_Utente)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Prenotazione where Corso='" + corso +"'"
                            + " and Docente=" + ID_Docente + " and Studente=" + ID_Utente);
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getPrenotazioniCorsoDocente ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static ResultSet getDocentiPrenotazione(int ID_Docente)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Docente where ID_Docente=" + ID_Docente);
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getDocentiPrenotazione ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    public static ResultSet getCorsoPrenotazione(String titolo)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Corso where Titolo='" + titolo + "'");
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getCorsoPrenotazione ERROR: " + e.getMessage());
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
    
    public static ResultSet getDocentiCorsoTabDocenti(int docente)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM corsodocente WHERE docente =" + docente);
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getDocentiCorsoTabDocenti ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    public static ResultSet getCorsiDocente(int idDocente)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM CorsoDocente WHERE Docente ="+idDocente);
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getDocentiCorsoTabDocenti ERROR: " + e.getMessage());
        };
        
        return rs;
    }
    
    
    
    public static ResultSet getNomeDocente(int idDocente)
    {
        ResultSet rs = null;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            st.executeQuery("SELECT * FROM Docente where ID_Docente =" + idDocente);
            
            rs = st.getResultSet();
        }
        catch (SQLException e) {
            System.err.println("getNomeDocente ERROR: " + e.getMessage());
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
            titolo = titolo.toLowerCase();
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
    
    public static void insDocente(String nome, String cognome, String email, String[] corsi) {
        String sql = "";
        ResultSet rs = null;
        int id_docente = 21;
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            rs = getIDDocenti(email);
            if(rs.next()){
                id_docente = rs.getInt("ID_docente");
                sql = "UPDATE Docente set Attivo = 1 WHERE ID_Docente = " + id_docente;
            }
            else{
                sql = "INSERT INTO Docente(nome, cognome ,email) VALUES("
                    + "'" + nome + "', '" + cognome + "','" + email + "')";
                id_docente = getLastID_Docente();
            }
            
            st.executeUpdate(sql);
            System.out.println(sql);
            
            st.close();
            conn.close();
            System.out.println(id_docente);
            for (int i = 0; i < corsi.length; i++) {
                sql = "INSERT INTO corsodocente(Docente,corso) VALUES(" + id_docente + ",'" + corsi[i] + "')";
                System.err.println(sql);
                insDocenteCorso(sql);
            }
        } 
        catch (Exception e) {
            System.out.println("insDocente ERROR: " + e.getMessage());
        }
        
    }
    public static void insDocenteCorso(String sql) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("insCorso ERROR: " + e.getMessage());
        }
        
    }
    public static void disdiciPren(int ID_Pren) {
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            sql = "UPDATE Prenotazione set Disdetta=1 where ID_Prenotazione=" + ID_Pren;
            System.out.println(sql + " - PRENOTAZIONE DISDETTA");
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("disdiciPren ERROR: " + e.getMessage());
        }
        
    }
    
    public static void updateDocente(String id_docente, String nome, String cognome, String email, String[] corsi) {
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            int idDocente = Integer.parseInt(id_docente);
            sql = "UPDATE Docente SET Nome ='" + nome + "',Cognome = '" + cognome + "',email = '" + email + "', attivo = 1 WHERE id_docente =" + idDocente;
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            conn.close();
            
            for (int i = 0; i < corsi.length; i++){
                sql = "INSERT INTO corsodocente(Docente,corso) VALUES(" + id_docente + ",'" + corsi[i] + "')";
                System.err.println(sql);
                insDocenteCorso(sql);
            }
        } 
        catch (Exception e) {
            System.out.println("updateCorso ERROR: " + e.getMessage());
        }
        
    }
    
    public static void deleteDocente(String id_docente) {
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            int idDocente = Integer.parseInt(id_docente);
            sql = "UPDATE Docente SET Attivo = 0 WHERE ID_Docente = " + id_docente;
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("deleteCorso ERROR: " + e.getMessage());
        }
        
    }    
    
    public static void deleteCorsoDocente(String id_docente) {
        String sql = "";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            Statement st = conn.createStatement();
            int idDocente = Integer.parseInt(id_docente);
            sql = "UPDATE corsodocente SET Attivo = 0 WHERE Docente = " + id_docente;
            System.out.println(sql);
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } 
        catch (Exception e) {
            System.out.println("deleteCorso ERROR: " + e.getMessage());
        }
        
    }    
}
