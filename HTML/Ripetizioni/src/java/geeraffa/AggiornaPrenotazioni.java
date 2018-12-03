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
@WebServlet(name = "AggiornaPrenotazioni", urlPatterns = {"/AggiornaPrenotazioni"})
public class AggiornaPrenotazioni extends HttpServlet {

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
            
            List<Prenotazione> lstPren;
            response.setContentType("text/plain");
            Model.registerDriver();
            lstPren = Model.listPrenotazioni(request.getParameter("corso"), 
                    Integer.parseInt(ses.getAttribute("id").toString()));
            
            //Creo JSONArray da inviare come risposta con tutte le prenotazioni richieste!
            JSONArray arrRipetizioni = new JSONArray();
            
            for (int i = 0; i < lstPren.size(); i++) {                
                //Modifico formato della DATA in quello standard europeo
                String dataPren = getCorrectData(lstPren, i);
                String oraInizio = lstPren.get(i).getDataInizio().split(" ")[1].substring(0,5);
                String oraFine = lstPren.get(i).getDataFine().split(" ")[1].substring(0,5);
                
                //Ricavo il DOCENTE corrente dal suo ID
                String sql = "Select * from Docente where ID_Docente="+lstPren.get(i).getDocente();
                ResultSet rsDoc = Model.eseguiQuery(sql);
                
                //Creazione array JSON da passare alla chiamata AJAX
                JSONObject ripetizione = new JSONObject();
                if(rsDoc.next()){
                    ripetizione.put("data", dataPren);
                    ripetizione.put("corso", lstPren.get(i).getCorso());
                    ripetizione.put("oraInizio", oraInizio);
                    ripetizione.put("oraFine", oraFine);
                    ripetizione.put("docente", rsDoc.getString("Cognome") +  " " + rsDoc.getString("Nome"));
                    ripetizione.put("stato", getStatoPren(lstPren, i));
                    ripetizione.put("idPren", lstPren.get(i).getID_Prenotazione());
                }
                
                //Aggiungo all'array l'oggetto ripetizione corrente!
                arrRipetizioni.put(ripetizione);
            }
            
            //Invio la risposta con l'array JSON
            out.println(arrRipetizioni);
            out.flush();
        }
    }
    
    private String getStatoPren(List<Prenotazione> lstPren, int i) throws SQLException, ParseException
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
                + "WHERE Prenotazione.ID_Prenotazione=" + lstPren.get(i).getID_Prenotazione();
                    
        ResultSet rs = Model.eseguiQuery(sql);
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
    
    private String getCorrectData(List<Prenotazione> lstPren, int i) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date data = dateFormat.parse(lstPren.get(i).getDataInizio());
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
            Logger.getLogger(AggiornaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(AggiornaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AggiornaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AggiornaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(AggiornaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AggiornaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
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
