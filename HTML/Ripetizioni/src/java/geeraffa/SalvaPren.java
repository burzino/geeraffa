/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geeraffa;

import dao.Model;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author GEERAFFA
 */
@WebServlet(name = "SalvaPren", urlPatterns = {"/SalvaPren"})
public class SalvaPren extends HttpServlet {

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
            throws ServletException, IOException, SQLException, JSONException, ParseException {
            
        //Context
        ServletContext ctx = getServletContext();
        
        //Where to move
        RequestDispatcher rd = ctx.getRequestDispatcher("/index.jsp");
        
        HttpSession ses = request.getSession();
        
        String mobile = request.getParameter("mobile");
        
        String corso = request.getParameter("corso");
        String docente = request.getParameter("docente");
        String dataPren = request.getParameter("dataPren");
        String oraInizio = request.getParameter("oraInizio");
        String oraFine = request.getParameter("oraFine");
        
        //Registrazione da pagina web
        if(mobile == null)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
            Date data = dateFormat.parse(dataPren);
            dateFormat.applyPattern("yyyy-MM-dd");
            String dataOK = dateFormat.format(data);
            
            String dataInizio = dataOK + " " + oraInizio + ":00.000000";
            String dataFine = dataOK + " " + oraFine + ":00.000000";
            
            //Inserimento nel DB
            String sql =      "INSERT INTO Prenotazione "
                            + "(Studente, Docente, Corso, DTInizio, DTFine) "
                            + "VALUES("
                            + ses.getAttribute("id") + ", " + docente + ", "
                            + "'" + corso + "', '" + dataInizio + "' ,"
                            + "'" + dataFine + "')";                    
            Model.eseguiNonQuery(sql);
            System.out.println("Prenotazione SALVATA!");
            
            rd = ctx.getRequestDispatcher("/elencoPren.jsp");

            rd.forward(request, response);
        }
        //Registrazione da APP
        else
        {
            
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
            Logger.getLogger(SalvaPren.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(SalvaPren.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SalvaPren.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SalvaPren.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(SalvaPren.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SalvaPren.class.getName()).log(Level.SEVERE, null, ex);
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