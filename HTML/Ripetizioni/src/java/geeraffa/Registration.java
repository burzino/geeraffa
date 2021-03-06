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
@WebServlet(name = "Registration", urlPatterns = {"/Registration"})
public class Registration extends HttpServlet {

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
            
        //Context
        ServletContext ctx = getServletContext();
        
        //Where to move
        RequestDispatcher rd = ctx.getRequestDispatcher("/index.jsp");
        
        HttpSession ses = request.getSession();
        
        Model model = new Model();
        
        String mobile = request.getParameter("mobile");
        
        String username = request.getParameter("username");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        String ruolo = "Studente";
        
        //Registrazione da pagina web
        if(mobile == null)
        {
            //Inserimento nel DB
            String sql =    "INSERT INTO Utente(Username, Pwd ,Nome, Cognome, Email, Ruolo) VALUES("
                            + "'" + username + "', '" + pwd + "','" + nome 
                            + "', '" + cognome + "', '" + email + "','" + ruolo + "')";
            model.eseguiNonQuery(sql);
            System.out.println("Utente INSERITO!");

            ses.setAttribute("logged", "Y");
            ses.setAttribute("name", nome + " " + cognome);
            sql = "SELECT MAX(ID_Utente) as ID from Utente";
            ResultSet rs = model.eseguiQuery(sql);
            if(rs.next())
                ses.setAttribute("id", rs.getInt("ID"));
            ses.setAttribute("ruolo", ruolo);

            rd = ctx.getRequestDispatcher("/index.jsp");

            rd.forward(request, response);
        }
        //Registrazione da APP
        else
        {
            JSONObject obj = new JSONObject();
            
            obj.put("logged", "Y");
            obj.put("nome", nome);
            obj.put("cognome", cognome);
            obj.put("email", email);
            
            //Invio oggetto JSON all'app
            try (PrintWriter out = response.getWriter()) {
                out.println(obj.toString());
            }
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
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
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
