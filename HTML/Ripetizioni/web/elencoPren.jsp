<%-- 
    Document   : prenotazione
    Created on : 23-nov-2018, 10.24.45
    Author     : Geeraffa
--%>
<%@page import="javafx.beans.property.IntegerPropertyBase"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="geeraffa.*"%>
<%@ page import="dao.*"%>

<!DOCTYPE html>
<html>
    <%
        HttpSession ses = request.getSession();
        String corso = request.getParameter("corso");
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        

        <link rel="apple-touch-icon" href="https://i.imgur.com/QRAUqs9.png">    
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrapPren.min.css">
        <link rel="stylesheet" type="text/css" href="css/elencoPren.css">
        
        <meta name="viewport" content="width=device-width, initial-scale=1">	

        <link rel="stylesheet" href="css/newcss.css">
        <title>Prenotazione - <%= ses.getAttribute("name") %></title>
    </head>
    
    <body style="background-color: #abc" onload="aggiornaTabella('<%=corso%>','/Ripetizioni/Controller?toDo=aggiornaPren'); 
          orderSelect(document.getElementById('selCorso'), corso)">
    <jsp:include page="header.jsp"></jsp:include>    
            <span class="login100-form-title" style="margin-top: 15px; padding-bottom: 20px;">
                ELENCO DELLE TUE RIPETIZIONI PRENOTATE
            </span>
        <input type="hidden" name="toDo" value="elencoPren"/>
            <div id="container-select">
            <div class="form-group" id="divSelCorso">
                <label for="selCorso"> Corso </label>
                <select style="background-color: #abc" id="selCorso" name="corso" class="form-control form-control-lg" 
                        onchange="aggiornaTabella(this.value, '<%= request.getContextPath()%>/Controller?toDo=aggiornaPren')" >
                    <option <% if(corso.equals("tutti")) { %>
                                selected="selected"
                            <% } %>
                        value="tutti">TUTTI</option>
                    
                    <% 
                        //Ricavo lista corsi messa nella request dal controller
                        List<Corso> corsi = (List<Corso>)request.getAttribute("lstCorsi");
                        
                        for (Corso c : corsi) 
                        {
                    %>
                        <option <% if(corso.equals(c.getTitolo())) { %> selected="selected" <% } %>                             
                                value="<%= c.getTitolo()%>">
                            <%= c.getTitolo()%>
                        </option>
                    
                    <% } %>          
                        
                </select>
            </div>
        </div>
          
        <table class="table table-hover" style="width:80%; margin: 20px auto;text-align: center;">
            <thead>
                <th scope="col">Giorno</th>
                <th scope="col">Fascia Oraria</th>
                <th scope="col">Corso</th>
                <th scope="col">Docente</th>
                <th scope="col">Stato</th>
                <th scope="col"></th>
            </thead>
            <tbody id="tablePren">
            </tbody>
        </table>
                
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%">
                 <!-- Modal che viene popolato dinamicamente dopo aver fatto una lettura sul db (modificato anche con funzione javascript-->
                <input type="hidden" name="toDo" value="disdici"/>
                    <div class="modal fade" id="modalDisdici" tabindex="-1" role="dialog" aria-labelledby="modificaDocentiLabel" aria-hidden="true">
                  <div class="modal-dialog" role="document">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="aggiungiDocentiTitle">Conferma disdetta</h5>
                      </div>
                      <div class="modal-footer">
                          <input type="text" name="corso" id="corso" style="display:none;">
                          <input type="text" name="url" id="url" style="display:none;">
                          <input type="button" class="btn btn-secondary" data-dismiss="modal" value="Annulla" id="annulla"/>
                          <input type="button" class="btn btn-success" data-dismiss="modal" value="Conferma" name="conferma" id="conferma" onclick="getCorsoUrl()"/>
                      </div>
                    </div>
                  </div>
                </div>
        </form>
    <script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>
    <script src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/elencoPren.js"></script>
    </body>

</html>
