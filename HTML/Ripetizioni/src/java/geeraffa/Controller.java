/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geeraffa;

import dao.Studente;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author GEERAFFA
 */
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
        System.out.println("AAAAAAAANNNNNNN " + request.getParameter("toDo"));
        
        String action = request.getParameter("toDo");
        //Request PAGE
        //String page_request = request.getParameter("url").split("/")[4];
        
        //Context
        ServletContext ctx = getServletContext();
        //Where to move
        RequestDispatcher rd = ctx.getRequestDispatcher("/index.jsp");
        
        String username;
        String pwd;
        
        HttpSession ses = request.getSession();
        
        switch(action){
            case "login":
                username = request.getParameter("username");
                pwd = request.getParameter("pwd");

                Model.registerDriver();
                ResultSet rs = Model.login(username, pwd);            

                if(rs.next())   //Username e password validi!
                {
                    ses.setAttribute("logged", "Y");
                    ses.setAttribute("username", username);
                    ses.setAttribute("name", rs.getString("Nome") + " " + rs.getString("Cognome"));
                    ses.setAttribute("id", rs.getString("ID_Utente"));
                    ses.setAttribute("ruolo", rs.getString("Ruolo"));
                    
                    ses.setAttribute("logged", "Y");
                }
                else
                {
                    rd = ctx.getRequestDispatcher("/login.jsp");
                    request.setAttribute("logged", "N");
                }
            
                break;
            case "index":
                System.out.println("ARRIVO DALL'INDEX!!!");
                break;
            case "registration":
                username = request.getParameter("username");
                String name = request.getParameter("name");
                String cognome = request.getParameter("cognome");
                String email = request.getParameter("email");
                pwd = request.getParameter("password");
                String ruolo = "Studente"; //request.getParameter("ruolo");

                //Inserimento nel DB
                Model.registerDriver();
                Model.insUtente(username, pwd, name, cognome, email, ruolo);
                System.out.println("Utente INSERITO!");

                ses.setAttribute("logged", "Y");
                ses.setAttribute("name", name);
                ses.setAttribute("id", Model.getLastID_Utente()-1);
                ses.setAttribute("ruolo", ruolo);
                System.err.println(Model.getLastID_Utente()-1);
                break;
            case "tab_docenti":
                System.out.println("ARRIVO DALL'ADMIN!!!");
                rd = ctx.getRequestDispatcher("/tab_docenti.jsp");
                break;
            case "tab_corsi":
                System.out.println("ARRIVO DALL'ADMIN!!!");
                rd = ctx.getRequestDispatcher("/tab_corsi.jsp");
                break;
            case "tab_prenotazioni":
                System.out.println("ARRIVO DALL'ADMIN!!!");
                rd = ctx.getRequestDispatcher("/tab_prenotazioni.jsp");
                break;
            case "logout":
                ses.invalidate();
                break;

        }
        
        rd.forward(request, response);
        //From login.jsp
        /*
        if(page_request.equals("login.jsp"))
        {
            String username = request.getParameter("username");
            String pwd = request.getParameter("pwd");

            Model.registerDriver();
            ResultSet rs = Model.login(username, pwd);            

            if(rs.next())   //Username e password validi!
            {
                request.setAttribute("logged", "Y");
                request.setAttribute("username", username);
                request.setAttribute("name", rs.getString("Nome") + " " + rs.getString("Cognome"));
                request.setAttribute("id", rs.getString("ID_Utente"));
            }
            else
            {
                rd = ctx.getRequestDispatcher("/login.jsp");
                request.setAttribute("logged", "N");
            }

        }
        else if(page_request.equals("index.jsp"))
        {
            System.out.println("ARRIVO DALL'INDEX!!!");
            
        }
        else if(page_request.equals("registration.jsp"))
        {
            String username = request.getParameter("username");
            String name = request.getParameter("name");
            String cognome = request.getParameter("cognome");
            String email = request.getParameter("email");
            String pwd = request.getParameter("password");
            String ruolo = request.getParameter("ruolo");
            
            Model.registerDriver();
            Model.insUtente(username, pwd, name, cognome, email, ruolo);
            System.out.println("Utente INSERITO!");
            
            request.setAttribute("logged", "Y");
            request.setAttribute("name", name);
            request.setAttribute("id", Model.getLastID_Utente()-1);
            System.err.println(Model.getLastID_Utente()-1);
        }
        */        
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
