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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author GEERAFFA
 */
@WebServlet(name = "PrenotaRipetizione", urlPatterns = {"/PrenotaRipetizione"})
public class PrenotaRipetizione extends HttpServlet {

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
            throws ServletException, IOException, JSONException, SQLException, ParseException {
        
        try (PrintWriter out = response.getWriter()) {
            //Context
            ServletContext ctx = getServletContext();

            //Where to move
            RequestDispatcher rd = ctx.getRequestDispatcher("/index.jsp");

            HttpSession ses = request.getSession();

            String mobile = request.getParameter("mobile");
            String corso = request.getParameter("corso");
            String doc = request.getParameter("docente");
            String popolaCmb = request.getParameter("cmb");


            //Prenotazione da pagina web
            if(mobile == null)
            {
                String sql = "";
                //Richiesta di popolazione del cmb dei docenti!
                if(popolaCmb != null)
                {
                    sql =     "SELECT * FROM Docente "
                            + "INNER JOIN CorsoDocente ON ID_Docente = Docente "
                            + "WHERE Corso='" + corso + "'";
                    ResultSet rs = Model.eseguiQuery(sql);
                    JSONArray arrDocenti = new JSONArray();
                    
                    //Ciclo sui risultati della query
                    while(rs.next())
                    {
                        JSONObject docente = new JSONObject();
                        docente.put("nome", rs.getString("Cognome") + " " + rs.getString("Nome"));
                        docente.put("corso", corso);
                        docente.put("idDoc", rs.getString("ID_Docente"));
                        
                        arrDocenti.put(docente);
                    }      
                    
                    //Non trovo nessun Docente associato al corso selezionato
                    if(arrDocenti.length() == 0)
                    {
                        JSONArray arr = new JSONArray();
                        JSONObject corsoJSON = new JSONObject();
                        corsoJSON.put("corso", corso);
                        arr.put(corsoJSON);
                        //Invio la risposta con l'array JSON
                        out.println(arr);
                        out.flush();
                    }
                    else
                    {
                        //Invio la risposta con l'array JSON
                        out.println(arrDocenti);
                        out.flush();
                    }
                }
                //Ricerco le ripetizioni di un determinato docente
                else if(doc != null)
                {
                    String dataPren = request.getParameter("data");
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
                    Date data = dateFormat.parse(dataPren);
                    dateFormat.applyPattern("yyyy-MM-dd");
                    String dataOK = dateFormat.format(data);
                    
                    sql =     "SELECT * from Prenotazione "
                            + "inner join Docente on Docente=ID_Docente "
                            + "where Corso='" + corso + "' "
                            + "and datediff(DTInizio, '" + dataOK + "') = 0 "
                            + "and Disdetta=0 "
                            + "and Docente=" + doc;
                    ResultSet rs = Model.eseguiQuery(sql);
                    
                    //Creo JSONArray da inviare come risposta
                    JSONArray arrPren = new JSONArray();
                    int res = 0;
                    
                    while(rs.next())
                    {
                        JSONObject pren = new JSONObject();
                        //Salvo solo le due cifre dell'ora (es 17:00 --> salvo 17)
                        pren.put("oraInizio", rs.getString("DTInizio").split(" ")[1].substring(0, 2));
                        pren.put("oraFine", rs.getString("DTFine").split(" ")[1].substring(0, 2));
                        arrPren.put(pren);
                        res++;
                    }
                    
                    if(res == 0)
                    {
                        //Invio la risposta con l'array JSON
                        JSONArray arr = new JSONArray();
                        JSONObject obj = new JSONObject();
                        obj.put("oraInizio", "none");
                        arr.put(obj);
                        
                        out.println(arr);
                        out.flush();
                    }
                    else
                    {
                        //Invio la risposta con l'array JSON
                        out.println(arrPren);
                        out.flush();
                    }
                    
                    
                }
                

                /*rd = ctx.getRequestDispatcher("/prenotazione.jsp");

                rd.forward(request, response);*/
            }
            //Prenotazione dal'APP
            else
            {

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
        } catch (JSONException ex) {
            Logger.getLogger(PrenotaRipetizione.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrenotaRipetizione.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PrenotaRipetizione.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException ex) {
            Logger.getLogger(PrenotaRipetizione.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PrenotaRipetizione.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PrenotaRipetizione.class.getName()).log(Level.SEVERE, null, ex);
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
