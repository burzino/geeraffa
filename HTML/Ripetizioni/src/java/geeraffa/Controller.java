/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geeraffa;

import dao.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author GEERAFFA
 */
@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        
        String action = request.getParameter("toDo");
        
        //Context
        ServletContext ctx = getServletContext();
        //Where to move
        RequestDispatcher rd = ctx.getRequestDispatcher("/index.jsp");
        
        HttpSession ses = request.getSession();
        
        Model model = new Model();
        
        switch(action){
            case "getCorsi":
                ses.setAttribute("lstCorsi", model.listCorsiAttivi());
                break;
            case "login":
                request.setAttribute("lstCorsi", model.listCorsiAttivi());
                rd = ctx.getRequestDispatcher("/Login");
                break;
            case "prenota":
                rd = ctx.getRequestDispatcher("/PrenotaRipetizione");
                break;
            case "prenotaIndex":
                request.setAttribute("lstCorsi", model.listCorsiAttivi());
                rd = ctx.getRequestDispatcher("/prenotazione.jsp");
                break;
            case "logout":
                ses.invalidate();
                break;
            case "elencoPren":
                request.setAttribute("lstCorsi", model.listCorsi());
                rd = ctx.getRequestDispatcher("/elencoPren.jsp");
                break;
            case "aggiornaPren":
                rd = ctx.getRequestDispatcher("/AggiornaPrenotazioni");
                break;
            case "aggiornaPren_admin":
                rd = ctx.getRequestDispatcher("/AggiornaPrenotazioni_Admin");
                break;
            case "salvaPren":
                request.setAttribute("lstCorsi", model.listCorsiAttivi());
                rd = ctx.getRequestDispatcher("/SalvaPren");
                break;
            case "disdici":
                String sql = "UPDATE Prenotazione set Disdetta=1 where ID_Prenotazione="+request.getParameter("id");
                model.eseguiNonQuery(sql);
                rd = ctx.getRequestDispatcher("/AggiornaPrenotazioni");
                break;
            case "elencoCorsi":
                rd = ctx.getRequestDispatcher("/VisualizzaCorsi");
                break;
            case "elencoPrenotazioni":
                rd = ctx.getRequestDispatcher("/VisualizzaPrenotazioni");
                break;
            case "registration":
                rd = ctx.getRequestDispatcher("/Registration");
                break;
            case "tab_docenti":
                System.out.println("ARRIVO DALL'ADMIN!!!");
                request.setAttribute("lstDocenti", model.listDocenti());                
                request.setAttribute("lstCorsi", model.listCorsi());
                
                rd = ctx.getRequestDispatcher("/tab_docenti.jsp");
                break;
            case "tab_corsi":
                System.out.println("ARRIVO DALL'ADMIN!!!");
                request.setAttribute("lstCorsi", model.listCorsiAttivi());
                rd = ctx.getRequestDispatcher("/tab_corsi.jsp");
                break;
            case "tab_prenotazioni":
                System.out.println("ARRIVO DALL'ADMIN!!!");
                request.setAttribute("lstUtenti", model.getUtenti());
                request.setAttribute("lstCorsi", model.listCorsi());
                rd = ctx.getRequestDispatcher("/tab_prenotazioni.jsp");
                break;
            case "prenota_a":
                System.out.println("ARRIVO DALL'ADMIN!!!");
                request.setAttribute("lstCorsi", model.listCorsiAttivi());
                rd = ctx.getRequestDispatcher("/prenotazione.jsp");
                break;
            case "modificaCorsi":
                System.out.println("ARRIVO DA TAB CORSI!!!");
                request.setAttribute("lstCorsi", model.listCorsiAttivi());
                rd = ctx.getRequestDispatcher("/ModificaCorsi");
                break; 
            case "modificaDocenti":
                System.out.println("ARRIVO DA TAB DOCENTI!!!");
                rd = ctx.getRequestDispatcher("/ModificaDocenti");
                break;
            case "noAdmin":
                System.out.println("ACCESSO AREA ADMIN NEGATO");
                rd = ctx.getRequestDispatcher("/index.jsp");
                break;
            //Casistiche in cui si richiama direttamente il MODEL
            case "corsi":
                request.setAttribute("lstCorsi", model.listCorsiAttivi());
                break;
            case "getCorsiDoc":
                String idDoc = request.getParameter("idDoc");
                rd = ctx.getRequestDispatcher("/getCorsi?idDoc="+ idDoc);
                rd.include(request, response);
                break;
            case "elencoOreDocente":
                rd = ctx.getRequestDispatcher("/ElencoOreDocenti");
                break;
            case "elencoDocenti":
                rd = ctx.getRequestDispatcher("/VisualizzaDocenti");
                break;
            
            case "elencoStorico":
                rd = ctx.getRequestDispatcher("/VisualizzaStorico");
                break;

        }
        if(!action.equals("getCorsiDoc"))
            rd.forward(request, response);        
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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller";
    }// </editor-fold>

}
