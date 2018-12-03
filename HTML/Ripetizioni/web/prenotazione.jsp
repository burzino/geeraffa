<%-- 
    Document   : prenotazione
    Created on : Dec 3, 2018, 4:53:23 PM
    Author     : luca
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
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <span class="login100-form-title" style="margin-top: 15px; padding-bottom: 20px;">
                PRENOTA LA TUA RIPETIZIONE DI <%= corso.toUpperCase() %>
        </span>
        <div class="form-group" id="divSelCorso">
            <input type="date" name="dataPren" onchange="cambioData()" id="dataPren"/>
        </div>
    </body>
</html>
