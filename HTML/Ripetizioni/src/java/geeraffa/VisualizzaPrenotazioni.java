/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geeraffa;

import dao.Model;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author masiu
 */
@WebServlet(name = "VisualizzaPrenotazioni", urlPatterns = {"/VisualizzaPrenotazioni"})
public class VisualizzaPrenotazioni extends HttpServlet {

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
            throws ServletException, IOException, SQLException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        
        ServletContext ctx = getServletContext();
        HttpSession ses = request.getSession();
        
        Model model = new Model();
        
        String idS = request.getParameter("id");
        String sql = "Select Prenotazione.ID_Prenotazione,Prenotazione.Corso,Docente.Nome,Docente.Cognome,Prenotazione.DTInizio,Prenotazione.DTFine,Prenotazione.Disdetta"
                + " From Prenotazione,Docente,Utente "
                + "Where Utente.ID_Utente=Prenotazione.Studente and "
                + "Docente.ID_Docente=Prenotazione.Docente and "
                + "Utente.ID_Utente='"+idS+"' and "
                + "Prenotazione.DTFine>NOW() and "
                + "Disdetta=0 order by Prenotazione.DTInizio DESC";
        ResultSet rs=model.eseguiQuery(sql);
        JSONArray jsonArray= new JSONArray();
        
        while (rs.next()) {            
            String corso=rs.getString("Corso");
            String docente=rs.getString("Cognome")+" "+rs.getString("Nome");
            String dataIni=rs.getString("DTInizio");
            String dataFin=rs.getString("DTFine");
            String id=rs.getString("ID_Prenotazione");
            String disdetta=rs.getString("Disdetta");
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("corso", corso);
            jsonObject.put("docente", docente);
            jsonObject.put("dataIni", dataIni);
            jsonObject.put("dataFin", dataFin);
            jsonObject.put("id", id);
            jsonObject.put("disdetta", disdetta);
            jsonArray.put(jsonObject);
        }
        JSONObject mainJsonObject=new JSONObject();
        mainJsonObject.put("prenotazioni",jsonArray);
        try (PrintWriter out = response.getWriter()) {
                out.println(mainJsonObject.toString());
            }
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
        } catch (SQLException ex) {
            Logger.getLogger(VisualizzaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(VisualizzaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(VisualizzaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(VisualizzaPrenotazioni.class.getName()).log(Level.SEVERE, null, ex);
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
