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
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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
            throws ServletException, IOException, ParseException {
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession ses = request.getSession();
            
            List<Prenotazione> lstPren;
            response.setContentType("text/plain");
            Model.registerDriver();
            lstPren = Model.listPrenotazioni(request.getParameter("corso"), Integer.parseInt(ses.getAttribute("id").toString()));

            //Creo stringa con dati da passare alla pagina delle prenotazioni
            String str = "";
            for (int i = 0; i < lstPren.size(); i++) {
                //Modifico formato della DATA in quello standard europeo
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date data = dateFormat.parse(lstPren.get(i).getDataInizio());
                dateFormat.applyPattern("dd-MM-yyyy");
                String dataOK = dateFormat.format(data);
                String oraInizio = lstPren.get(i).getDataInizio().split(" ")[1].substring(0,5);
                String oraFine = lstPren.get(i).getDataInizio().split(" ")[1].substring(0,5);
                str +=  dataOK
                        + ";" + oraInizio + " - " + oraFine 
                        + ";" + lstPren.get(i).getCorso()
                        + ";" + lstPren.get(i).getDocente()
                        + ";" + lstPren.get(i).getID_Prenotazione()
                        +"?";
            }
            //Invio la risposta
            out.println(str);
            out.flush();
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
        } catch (ParseException ex) {
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
