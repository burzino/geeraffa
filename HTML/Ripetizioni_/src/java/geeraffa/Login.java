/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geeraffa;

import java.io.FileWriter;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author GEERAFFA
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

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
                
        String mobile = request.getParameter("mobile");
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        
        Model.registerDriver();
        ResultSet rs = Model.login(username, pwd);
            
        //Login da pagina web
        if(mobile == null)
        {            
            if(rs.next())   //Username e password validi!
            {
                ses.setAttribute("logged", "Y");
                ses.setAttribute("username", username);
                ses.setAttribute("name", rs.getString("Nome") + " " + rs.getString("Cognome"));
                ses.setAttribute("id", rs.getString("ID_Utente"));
                ses.setAttribute("ruolo", rs.getString("Ruolo"));
                
                if(rs.getString("Ruolo").equals("Admin"))
                    rd = ctx.getRequestDispatcher("/admin.jsp");
                    
                ses.setAttribute("logged", "Y");
            }
            else
            {
                rd = ctx.getRequestDispatcher("/login.jsp");
                request.setAttribute("logged", "N");
            }

            rd.forward(request, response);
        }
        //Login dall'APP
        else
        {
            JSONObject obj = new JSONObject();
            
            if(rs.next())   //Username e password validi!
            {
                obj.put("logged", "Y");
                obj.put("nome", rs.getString("Nome"));
                obj.put("cognome", rs.getString("Cognome"));
                obj.put("email", rs.getString("Email"));
            }
            else
                obj.put("logged", "N");
            
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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