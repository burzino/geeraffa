/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geeraffa;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.json.*;

/**
 *
 * @author GEERAFFA
 */
@WebServlet(name = "AggiornaPrenotazioni_Admin", urlPatterns = {"/AggiornaPrenotazioni_Admin"})
public class AggiornaPrenotazioni_Admin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, JSONException, SQLException {
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession ses = request.getSession();
            
            response.setContentType("text/plain");
            String stud = request.getParameter("stud");
            String corso = request.getParameter("corso");
            if(corso.equals("TUTTI")) corso = corso.toLowerCase();
            if(stud.equals("TUTTI")) corso = stud.toLowerCase();
            
            Model model = new Model();
            
            String sql;
            
            if(stud.equals("tutti") && corso.equals("tutti"))
            {
                sql = createQuery("1");                
            }                      
            else if(corso.equals("tutti") && !stud.equals("tutti"))
            {
                sql = createQuery("Utente.ID_Utente= " + stud);
            }
            else if(!corso.equals("tutti") && stud.equals("tutti"))
            {
                sql = createQuery("corso= '" + corso + "'");
            }
            else //Selezionato studente e corso specifico
            {
                sql = createQuery("corso= '" + corso + "' and Utente.ID_Utente= " + stud);
            }
            
            System.out.println("BSGDSSDFBD  " + sql);
            System.out.println("asasaa " + corso + " loooo" + stud);
            
            
            //Creo JSONArray da inviare come risposta con tutte le prenotazioni richieste!
            JSONArray arrRipetizioni = new JSONArray();
            
            ResultSet rsPrenStud = model.eseguiQuery(sql);
            
            while(rsPrenStud.next())
            {
                //Modifico formato della DATA in quello standard europeo
                String dataPren = getCorrectData(rsPrenStud.getString("DTInizio"));
                String oraInizio = rsPrenStud.getString("DTInizio").split(" ")[1].substring(0,5);
                String oraFine = rsPrenStud.getString("DTFine").split(" ")[1].substring(0,5);


                //Creazione array JSON da passare alla chiamata AJAX
                JSONObject ripetizione = new JSONObject();                

                
                ripetizione.put("docPren", rsPrenStud.getString("cDocente") + " " + rsPrenStud.getString("nDocente"));                        
                ripetizione.put("studPren", rsPrenStud.getString("cStudente") + " " + rsPrenStud.getString("nStudente"));                        
                ripetizione.put("data", dataPren);
                ripetizione.put("corso", rsPrenStud.getString("corso"));
                ripetizione.put("oraInizio", oraInizio);
                ripetizione.put("oraFine", oraFine);
                ripetizione.put("stato", getStatoPren(rsPrenStud.getString("pren"), model));
                ripetizione.put("idPren", rsPrenStud.getString("pren"));
                
                //Aggiungo all'array l'oggetto ripetizione corrente!
                arrRipetizioni.put(ripetizione);
            }            
            
            System.out.println(arrRipetizioni);

        //Invio la risposta con l'array JSON
        out.println(arrRipetizioni);
        out.flush();
    }
    }
    
    private String createQuery(String condition)
    {
        String sql = "";
        sql = "SELECT Docente.nome AS nDocente, "
            + "Docente.Cognome AS cDocente, "
            + "Prenotazione.Corso AS corso, "
            + "Prenotazione.Disdetta, "
            + "Prenotazione.DTInizio, "
            + "Prenotazione.DTFine, "
            + "Prenotazione.ID_Prenotazione as pren, "
            + "Utente.Nome AS nStudente, "
            + "Utente.Cognome AS cStudente "
            + "FROM Prenotazione "
            + "JOIN Docente ON Docente.ID_Docente = Prenotazione.Docente "
            + "JOIN Utente ON Utente.ID_Utente = Prenotazione.Studente "
            + "WHERE " + condition
            + " ORDER BY DTInizio DESC";
        
        return sql;
    }
    
    private String getStatoPren(String id, Model model) throws SQLException, ParseException
    {     
        String sql;
        sql = "SELECT Docente.nome AS nDocente, "
                + "Docente.cognome AS cDocente, "
                + "Prenotazione.corso AS corso, "
                + "Prenotazione.disdetta, "
                + "Prenotazione.DTInizio, "
                + "Prenotazione.DTFine, "
                + "Utente.nome AS nStudente, "
                + "Utente.cognome AS cStudente "
                + "FROM Prenotazione "
                + "JOIN Docente ON Docente.ID_Docente = Prenotazione.docente "
                + "JOIN Utente ON Utente.ID_Utente = Prenotazione.studente "
                + "WHERE Prenotazione.ID_Prenotazione=" + id;
        
        ResultSet rs = model.eseguiQuery(sql);
        String stato = "";
        
        while(rs.next()){                   
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dataPren = dateFormat.parse(rs.getString("DTInizio"));
            dateFormat.applyPattern("dd/MM/yyyy HH:mm");
            
            DateFormat dataCorrente = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();

            Date dCorrente = dataCorrente.parse(dataCorrente.format(date));

            
            //La ripetizione è già stata fatta!
            if (dCorrente.after(dataPren))
                stato = "CONCLUSA";
            //La ripetizione deve ancora essere fatta!
            else if(dCorrente.before(dataPren))
                stato = "ATTIVA";
            //La ripetizione è in corso!
            else if(dCorrente == dataPren)
                stato ="IN CORSO";

            if (rs.getInt("disdetta") == 1 )
                stato = "DISDETTA";
        }
        return stato;
    }
    
    private String getCorrectData(String dataQuery) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date data = dateFormat.parse(dataQuery);
        dateFormat.applyPattern("dd/MM/yyyy");
        String dataOK = dateFormat.format(data);
        return dataOK;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(AggiornaPrenotazioni_Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(AggiornaPrenotazioni_Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AggiornaPrenotazioni_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(AggiornaPrenotazioni_Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(AggiornaPrenotazioni_Admin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AggiornaPrenotazioni_Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
