/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geeraffa;

import dao.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * @author Andrea
 */
@WebServlet(name = "ModificaCorsi", urlPatterns = {"/ModificaCorsi"})
public class ModificaCorsi extends HttpServlet {

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
        //Context
        ServletContext ctx = getServletContext();
        
        //Where to move
        RequestDispatcher rd = ctx.getRequestDispatcher("/tab_corsi.jsp");
        
        HttpSession ses = request.getSession();
                
        String titolo = request.getParameter("titolo");
        String descrizione = request.getParameter("descrizione");
        String elimina = request.getParameter("elimina");
        String salva = request.getParameter("salva");
        String aggiungi = request.getParameter("aggiungi");
        String path = request.getParameter("txtPath");
        
        Model model = new Model();
        
        System.out.println("Funziona:" + titolo +" - " + descrizione);
        String sql;
        ResultSet rs;
        if (elimina != null) {
            System.out.println("STO ELIMINANDO IL CORSO");
            sql = "UPDATE corsodocente SET Attivo = 0 WHERE Corso = '" + titolo + "'";
            model.eseguiNonQuery(sql);
            sql = "UPDATE Corso SET Attivo = 0 WHERE Titolo = '" + titolo + "'";
            model.eseguiNonQuery(sql);
            System.out.println("CORSO ELIMINATO CORRETTAMENTE");
        }
        else if(salva != null){ 
            System.out.println("STO AGGIORNANDO IL CORSO");
            sql = "UPDATE Corso SET"
                    + " Descrizione = '" + descrizione + "',"
                    + " Path = 'img/corsi/" + path +"'"
                    + " WHERE Titolo = '" + titolo + "'";
            model.eseguiNonQuery(sql);
            System.out.println("CORSO MODIFICATO CORRETTAMENTE");
        }
        else if(aggiungi != null){
            System.out.println("STO AGGIUNGENDO UN NUOVO CORSO");
            
            sql = "SELECT * FROM Corso WHERE Titolo ='" + titolo + "'";
            rs = model.eseguiQuery(sql);
            if (rs.next()) {
                sql = "UPDATE Corso SET "
                        + "Attivo = 1, "
                        + "Descrizione = '" + descrizione + "', "
                        + "Path = 'img/corsi/" + path +"'"
                        + "WHERE titolo = '" + titolo + "'";
                model.eseguiNonQuery(sql);
            }
            else{
                sql = "INSERT INTO Corso(titolo, descrizione, path) VALUES(" + "'" + titolo + "', '" + descrizione + "', 'img/corsi/" + path + "')";
                model.eseguiNonQuery(sql);
            }
            System.out.println("CORSO INSERITO CORRETTAMENTE");
        }
        else
            System.out.println("ERROR IN MODIFICACORSI");
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
            Logger.getLogger(ModificaCorsi.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModificaCorsi.class.getName()).log(Level.SEVERE, null, ex);
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
