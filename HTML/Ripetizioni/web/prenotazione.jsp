<%-- 
    Document   : prenotazione
    Created on : Dec 3, 2018, 4:53:23 PM
    Author     : luca
--%>

<%@page import="javafx.beans.property.IntegerPropertyBase"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
        
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/prenotazione.js"></script>
        
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrapPren.min.css">
        
        
        
        <title>Prenotazione per <%= corso %></title>
    </head>
    <body onload="cercaRipetizioni('<%= corso %>', document.getElementById('selDocente').value, 
                '<%= request.getContextPath()%>/Controller?toDo=prenota')">
        <jsp:include page="header.jsp"></jsp:include>
        <span class="login100-form-title" style="margin-top: 15px; padding-bottom: 20px;">
                PRENOTA LA TUA RIPETIZIONE DI <%= corso.toUpperCase() %>
        </span>
        <div id="container-select" style="border: 1px red solid; width: 20%">
            <div class="form-group">
                <label for="selCorso"> Corso: </label>
                <select id="selCorso" class="form-control">
                <% 
                    List<Corso> corsi = Model.listCorsi();
                    for (int i = 0; i < corsi.size(); i++) {
                %>
                <option <% if(corsi.get(i).getTitolo().equals(corso)){%>
                            selected
                        <% } %>>
                        <%= corsi.get(i).getTitolo() %></option>
                <% } %>
                </select>
                
            </div>
            <div class="form-group">
                <label for="selDocente"> Docente: </label>
                <select id="selDocente" class="form-control" onchange="cercaRipetizioni('<%= corso %>', document.getElementById('selDocente').value, 
                '<%= request.getContextPath()%>/Controller?toDo=prenota')">
                <% 
                    List<Docente> docenti = Model.listDocenti();
                    System.out.println("Stampo docenti");
                    for (int j = 0; j < docenti.size(); j++) {
                %>
                    <option> <%= docenti.get(j).getCognome()%> <%= docenti.get(j).getNome()%></option>
                <% } %>
                </select>
            </div>
            <div class="form-group" id="selData">
                <label for="selData"> Data: </label>            
                <input type="date" name="dataPren" class="form-control" onchange="cambioData()" id="dataPren"/>
            </div>
            <div class="form-group" style="float:left;">
                <label for="selDalle"> Dalle: </label>
                <select id="selDalle" class="form-control">
                    <option>1</option>
                    <option>1</option>
                    <option>1</option>
                    <option>1</option>
                </select>
            </div>
            <div class="form-group" style="float:right;">
                <label for="selAlle"> Alle: </label>
                <select id="selAlle" class="form-control">
                    <option>1</option>
                    <option>1</option>
                    <option>1</option>
                    <option>1</option>
                </select>
            </div>
        </div>
        <table class="table table-hover" style="width:40%; margin: 20px auto;text-align: center;">
            <thead>
                <th scope="col">Giorno</th>
                <th scope="col">Fascia Oraria</th>
                <th scope="col">Corso</th>
                <th scope="col">Docente</th>
                <th scope="col">Azione</th>
            </thead>
            <tbody id="tablePren">
            </tbody>
        </table>
    </body>
</html>
