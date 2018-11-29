/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geeraffa;

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
 * @author GEERAFFA
 */
@WebServlet(name = "VisualizzaCorsi", urlPatterns = {"/VisualizzaCorsi"})
public class VisualizzaCorsi extends HttpServlet {

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
            throws ServletException, IOException, JSONException, SQLException  {
        response.setContentType("text/html;charset=UTF-8");
        
        //Context
        ServletContext ctx = getServletContext();
        HttpSession ses = request.getSession();
        
        String sql = "Select * from Corso";
        ResultSet rs=Model.eseguiQuery(sql);
        JSONArray jsonArray= new JSONArray();
        
        while (rs.next()) {            
            String titolo=rs.getString("Titolo");
            String descrizione=rs.getString("Descrizione");
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("titolo", titolo);
            jsonObject.put("descrizione", descrizione);
            jsonArray.put(jsonObject);
        }
        JSONObject mainJsonObject=new JSONObject();
        mainJsonObject.put("corsi",jsonArray);
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
            throws  ServletException, IOException {
        try{
        processRequest(request, response);
        }catch (SQLException ex) {
            Logger.getLogger(VisualizzaCorsi.class.getName()).log(Level.SEVERE, null, ex);
        }catch (JSONException ex) {
            Logger.getLogger(VisualizzaCorsi.class.getName()).log(Level.SEVERE, null, ex);
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
        try{
        processRequest(request, response);
        }catch (SQLException ex) {
            Logger.getLogger(VisualizzaCorsi.class.getName()).log(Level.SEVERE, null, ex);
        }catch (JSONException ex) {
            Logger.getLogger(VisualizzaCorsi.class.getName()).log(Level.SEVERE, null, ex);
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
