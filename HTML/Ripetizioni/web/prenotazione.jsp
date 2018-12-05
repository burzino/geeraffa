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
    <body onload="salvaDati('<%= corso %>', document.getElementById('selDocente'), 
                '<%= request.getContextPath()%>/Controller?toDo=prenota')">
        <jsp:include page="header.jsp"></jsp:include>
        <span id="titolo" class="login100-form-title" style="margin-top: 15px; padding-bottom: 20px;">
                PRENOTA LA TUA RIPETIZIONE DI <%= corso.toUpperCase() %>
        </span>
        <form action="<%=request.getContextPath()%>/Controller" method="post">
            <input type="hidden" name="toDo" value="salvaPren" />
            <div id="container-select" style="width: 60%">
                <div class="form-group">
                    <label for="selCorso"> Corso: </label>
                    <select id="selCorso" name="corso" class="form-control"
                            onchange="popolaCmbDocenti(this.value, document.getElementById('selDocente'))"
                            >
                    <% 
                        List<Corso> corsi = Model.listCorsi();
                        for (int i = 0; i < corsi.size(); i++) {
                    %>
                    <option <% if(corsi.get(i).getTitolo().equals(corso)){%>
                                selected="selected"
                            <% } %>>
                            <%= corsi.get(i).getTitolo() %></option>
                    <% } %>
                    </select>

                </div>
                <div class="form-group">
                    <label for="selDocente"> Docente: </label>
                    <select id="selDocente" name="docente" class="form-control" disabled>
                    </select>
                </div>
                <div class="form-group" id="selData">
                    <label for="selData"> Data: </label>            
                    <input disabled type="date" name="dataPren" class="form-control" 
                           onchange="cambioData(document.getElementById('selDocente').value)" id="dataPren"/>
                </div>
                <div class="form-group" style="float:left;">
                    <label for="selDalle"> Dalle: </label>
                    <select disabled name="oraInizio" id="selDalle" class="form-control"
                            onchange="popolaCmbOrario(this.value)">
                    </select>
                </div>
                <div class="form-group" style="float:right;">
                    <label for="selAlle"> Alle: </label>
                    <select disabled name="oraFine" id="selAlle" class="form-control">                    
                    </select>
                </div>
                <div class="container-login100-form-btn">
                        <input type="submit" class="login100-form-btn" value="PRENOTA"/>
                </div>
            </div>
    </form>
    <!--    <table class="table table-hover" style="width:40%; margin: 20px auto;text-align: center;">
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
    -->
    </body>
</html>
