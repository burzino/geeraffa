<%-- 
    Document   : prenotazione
    Created on : 23-nov-2018, 10.24.45
    Author     : Geeraffa
--%>
<%@page import="javafx.beans.property.IntegerPropertyBase"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="geeraffa.*"%>
<%@ page import="dao.*"%>

<!DOCTYPE html>
<html>
    <%
        HttpSession ses = request.getSession();
        String corso = request.getParameter("corso");
        String utente = ses.getAttribute("id").toString();
        int ID_Utente = Integer.parseInt(utente);
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        

        <link rel="apple-touch-icon" href="https://i.imgur.com/QRAUqs9.png">    
        <!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">-->
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrapPren.min.css">
        <script type="text/javascript" src="js/prenotazioni.js"></script>
        <title>Prenotazione - <%= ses.getAttribute("name") %></title>
    </head>
    
    <body style="background-color: #abc" onload="aggiornaTabella('tutti','/Ripetizioni/Controller?toDo=aggiornaPren')">
    <jsp:include page="header.jsp"></jsp:include>    
            <span class="login100-form-title" style="margin-top: 15px; padding-bottom: 20px;">
                ELENCO DELLE TUE RIPETIZIONI PRENOTATE
            </span>
        <form class="login100-form validate-form" 
              action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%">
        <input type="hidden" name="toDo" value="elencoPren"/>
            <div id="container-select">
            <div class="form-group" id="divSelCorso">
                <label for="selCorsoPrenota"> Corso </label>
                <select style="background-color: #abc" id="selCorsoPrenota" name="corso" class="form-control"
                        onchange="aggiornaTabella(this.value, '<%= request.getContextPath()%>/Controller?toDo=aggiornaPren')" >
                    <option <% if(corso.equals("tutti")) {%>
                                selected="selected"
                            <% } %>
                        value="tutti">TUTTI</option>
                    <% 
                        ResultSet rsCorsi = Model.eseguiQuery("Select * from Corso where Attivo=1");
                        while(rsCorsi.next())
                        {
                    %>
                    <option value="<%= rsCorsi.getString("Titolo")%>"
                        <% if(rsCorsi.getString("Titolo").equals(corso)) {%> 
                            selected="selected"
                        <% } %> >
                            <%= rsCorsi.getString("Titolo")%>
                    </option>
                    <% } %>
                </select>
            </div>
            <!--<div class="form-group" id="divAggiorna">
            <input type="submit" class="login100-form-btn" id="aggiorna-pren" value="CERCA"/>
            </div>-->
        </div>
        </form>
        <table class="table table-hover" style="width:95%; margin: 20px auto;text-align: center;">
            <thead>
                <th scope="col">Giorno</th>
                <th scope="col">Fascia Oraria</th>
                <th scope="col">Corso</th>
                <th scope="col">Docente</th>
                <th scope="col">Prenota</th>
            </thead>
            <tbody id="tablePren">
            </tbody>
        </table>
                             
    
        
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    
    </body>
</html>