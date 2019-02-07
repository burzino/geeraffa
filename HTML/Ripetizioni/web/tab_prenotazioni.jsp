<%-- 
    Document   : tab_docenti
    Created on : 21-nov-2018, 9.13.45
    Author     : Geeraffa
--%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="geeraffa.*"%>
<%@ page import="dao.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>GEERAFFA - Admin - Corsi</title>

        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="shortcut icon" href="img/Logo_Round.jpg">

        <!--<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/style.css">-->

        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!--===============================================================================================-->	
        <link rel="icon" type="image/png" href="img/icons/favicon.ico"/>
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="css/bootstrapPren.min.css">
        <link rel="stylesheet" type="text/css" href="css/elencoPren.css">
       
        <link rel="stylesheet" type="text/css" href="css/util.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <!--===============================================================================================-->  
        <script type="text/javascript" src="js/tab_prenotazioni.js"></script>

        <link rel="stylesheet" href="css/newcss.css">
    </head>
    <%
            Model model = new Model();
            HttpSession ses = request.getSession();
            String stud = request.getParameter("stud");
            String corso = request.getParameter("corso");
    %>
    <body style="background-color: #abc;"
          onload="aggiornaTabella('<%= stud%>', '<%= corso%>','<%= request.getContextPath()%>/Controller?toDo=aggiornaPren_admin')">
        
            <%
            if(!("Admin".equals(ses.getAttribute("ruolo"))) || ses.getAttribute("logged") == "N") { %>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" 
              style="width: 100%">
                <input type="hidden" name="toDo" value="noAdmin"/>
                <h1 style="color: red; text-align: center;">ACCESSO NEGATO</h1>
                <br><br>
                <br><br><br>
                <div style="text-align:center;">
                    <input type="submit" class="btn btn-danger" value="Ritorna alla home" name="home" id="home"/>  
                </div>

        </form>
         <%}else{ %>
         <nav style="background-color: #475369; " class="navbar navbar-expand-lg navbar-light">
            <a class="navbar-brand" href="<%= request.getContextPath()%>/index.jsp">GEERAFFA</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div style="background-color:#475369; color:#be9e21;" class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/index.jsp">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_docenti" target="">VISUALIZZA DOCENTI</a>
                    </li>
                    <li class="nav-item">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_corsi" target="">VISUALIZZA CORSI</a>
                    </li>
                    <li class="nav-item">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=prenota_a" target="">NUOVA PRENOTAZIONE</a>
                    </li>
                </ul>
                <li class="nav-item">
                    <a style="color:#be9e21; float: right;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=logout">LOGOUT</a>
                </li>
            </div>
        </nav>
        <% } %>
        
            <input type="hidden" name="toDo" value="tab_prenotazioni"/>
                
            <div id="container-select">
            <div class="form-group" id="divSelCorso">
            <label for="selStudente"> Studente </label>
            <select style="background-color: #abc" id="selStudente" class="form-control form-control-lg"
                    onchange="aggiornaTabella(this.value, document.getElementById('selCorso').value, '<%= request.getContextPath()%>/Controller?toDo=aggiornaPren_admin&page=tab_pren')"
                    >
            <option <% if(stud.equals("tutti")) { %>
                            selected="selected"
                        <% } %>
                    value="tutti">TUTTI</option>
            <%
                List<Studente> studenti = model.getUtenti();
                for(Studente s : studenti)
                {
            %>
            <option<% if(stud.equals(s.getID())) { %> 
                        selected="selected"
                    <% } %>
                    value="<%= s.getID()%>"> <%= s.getCognome()%> <%=s.getNome()%></option>
            <% } %>    

            </select>
            </div>
            <div class="form-group" id="divSelCorso">
            <label for="selStudente"> Studente </label>
            <select style="background-color: #abc" id="selCorso" class="form-control form-control-lg"
                    onchange="aggiornaTabella(document.getElementById('selStudente').value,this.value, '<%= request.getContextPath()%>/Controller?toDo=aggiornaPren_admin&page=tab_pren')"
                    >
            <option <% if(stud.equals("tutti")) { %>
                            selected="selected"
                        <% } %>
                    value="tutti">TUTTI</option>
            <%
                List<Corso> corsi = model.listCorsi();
                for(Corso c : corsi)
                {
            %>
            <option<% if(corso.equals((c.getTitolo()))) { %> 
                        selected="selected"
                    <% } %>
                    value="<%= c.getTitolo()%>"> <%=c.getTitolo()%></option>
            <% } %>    

            </select>
            </div>
            </div>                
            <table id="bootstrap-data-table" class="table table-hover" 
                   style="text-align:center; padding-left: 2%; 
                   padding-right: 2%; width: 90%; margin: auto; margin-top: 20px;">
                <thead>
                    <th>Docente</th>
                    <th>Corso</th>
                    <th>Studente</th>
                    <th>Data</th>  
                    <th>Ora</th>
                    <th>Stato</th>

                </thead>
                <tbody id="bodyTable">

                </tbody>
            </table>                       
                
        
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
