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
        List<Corso> corsi = (List<Corso>)request.getAttribute("lstCorsi");
        if(corso == null)
            corso = corsi.get(0).getTitolo();
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- JS -->
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/prenotazione.js"></script>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <!-- CSS -->
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" type="text/css" href="css/prenotazione.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrapPren.min.css">
        <link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <%if(corso != null){%>
            <title>Prenotazione per <%= corso %></title>
        <%}else{%>
            <title>Prenotazione</title>
        <%}%>
    </head>
    <body style="background-color: #abc;" 
        onload="salvaDati('<%= corso %>', document.getElementById('selDocente'), 
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
                    <select
                        style="background-color: #abc;" 
                        id="selCorso" name="corso" class="form-control"
                        onchange="popolaCmbDocenti(this.value, document.getElementById('selDocente'))"
                            >
                        <% 
                            for(Corso c: corsi){
                        %>
                        <option <% if(c.getTitolo().equals(corso)){%>
                                selected="selected"
                            <% } %>>
                            <%= c.getTitolo() %></option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="selDocente"> Docente: </label>
                    <select 
                        style="background-color: #abc;" 
                        id="selDocente" 
                        name="docente" onchange="cambioDocente()" class="form-control" disabled>
                    </select>
                </div>
                <div class="form-group">
                    <label for="dataPren"> Data: </label><br>
                    <input readonly style="background-color: rgba(255,255,255,0);" 
                           type="text" name="dataPren" id="dataPren" class="form-control"
                           onchange="cambioData(document.getElementById('selDocente').value)"/>
                </div>
                <div class="from-group" id="divOrari">
                </div>
                <input type="hidden" name="orario" />
                <input type="hidden" name="data" value="ANO"/>
                <div class="container-login100-form-btn">
                    <input type="submit" style="display: none;" onclick="setFasciaOraria()" id="btnPren" class="login100-form-btn" disabled value="PRENOTA"/>
                </div>
            </div>
        </form>
        <input type="text" name="session" id="session" style="display:none;" value="<%=ses.getAttribute("logged")%>">
    </body>
</html>
