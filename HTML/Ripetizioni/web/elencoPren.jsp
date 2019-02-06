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
        Model model = new Model();
        HttpSession ses = request.getSession();
        String corso = request.getParameter("corso");
        String utente = ses.getAttribute("id").toString();
        int ID_Utente = Integer.parseInt(utente);
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        

        <link rel="apple-touch-icon" href="https://i.imgur.com/QRAUqs9.png">    
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrapPren.min.css">
        <link rel="stylesheet" type="text/css" href="css/elenncoPren.css">
        
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/elencoPren.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--===============================================================================================-->	
        <link rel="icon" type="image/png" href="img/icons/favicon.ico"/>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
        <!--===============================================================================================-->	
        <link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="css/util.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <!--===============================================================================================-->  

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
                        List<Corso> corsi = model.listCorsi();

                        for (Corso c : corsi) 
                        {
                    %>
                    <option <% if(corso.equals(c.getTitolo())) { %> 
                            selected="selected"
                        <% } %>
                        value="<%= c.getTitolo()%>">
                            <%= c.getTitolo()%>
                    </option>
                    <% } %>
                </select>
            </div>
            <!--<div class="form-group" id="divAggiorna">
            <input type="submit" class="login100-form-btn" id="aggiorna-pren" value="CERCA"/>
            </div>-->
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

<!--    <script src="assets/js/lib/data-table/datatables.min.js"></script><!-- gestisce ricerca e paginazione della tabella  da problemi al padding-->
<!--    <script src="assets/js/lib/data-table/dataTables.bootstrap.min.js"></script><!-- script che gestisce la ricerca e paginazione della tabella, inoltre non da il problema del padding quando viene aperto e chiuso il modal-->  
<!--    <script src="assets/js/init/datatables-init.js"></script><!-- gestisce ricerca e paginazione della tabella ma non da problema al padding-->

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    </body>

</html>