<%-- 
    Document   : index
    Created on : Nov 15, 2018, 6:20:54 PM
    Author     : GEERAFFA
--%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="geeraffa.*"%>
<%@ page import="dao.*"%>


<!doctype html>

<html class="no-js" lang=""> <!--<![endif]-->
    
    
    
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>GEERAFFA - Admin</title>
    
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


</head>

<body style="width:98%; background-color: #abc; ">
    <%
    HttpSession ses = request.getSession();
    if(!("Admin".equals(ses.getAttribute("ruolo"))) || ses.getAttribute("logged") == "N") { %>
    <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%">
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

        <div style="background-color: #475369" class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="<%= request.getContextPath()%>/index.jsp">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_docenti" target="">Visualizza Docenti</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_corsi" target="">Visualizza Corsi</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_prenotazioni" target="">Visualizza Prenotazioni</a>
                </li>

            </ul>
        </div>
    </nav>

    <!-- Content -->
    <div class="content">  
        <div class="animated fadeIn">
            <div class="row">
                <span class="login100-form-title" style="margin: auto; padding-top: 2%; color: #475369; width: 90%">
                    Benvenuto nella sezione admin, da qui puoi visualizzare e modificare le informazioni salvate sulla piattaforma.
                    <br>
                    <a class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_docenti" target="" style="font-size: 30px"> Visualizza Docenti</a>
                    <a class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_corsi" target="" style="font-size: 30px">Visualizza Corsi</a>
                    <a class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_prenotazioni" target="" style="font-size: 30px">Visualizza Prenotazioni</a>
                </span>
            </div>
        </div>
    </div>
    <!-- /#content-->
    
    
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<%}%>
</body>
</html>
