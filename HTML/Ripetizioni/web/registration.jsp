<%-- 
    Document   : registration
    Created on : Nov 15, 2018, 6:29:31 PM
    Author     : GEERAFFA
--%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="geeraffa.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Registrazione - GEERAFFA</title>

    <!-- Font Icon -->
    <link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

    <!-- Main css -->
    <link rel="stylesheet" href="css/style_1.css">
    <style>
        .error {color:red;} <!--Imposto messaggi di errore della validazione di colore rosso-->
    </style>
        <!-- JS -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="js/main_1.js"></script>
    
    <!-- Validazione JQUERY -->
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.min.js"></script>
    <script type="text/javascript" src="js/validazione.js"></script>
</head>
<body>

    <div class="main">

        <section class="signup">
            <!--<img src="img/signup-bg.jpg" alt=""> -->
            <div class="container">
                <div class="signup-content">
                    <form method="POST" id="signup-form" action="<%= request.getContextPath()%>/Controller" class="signup-form">
                        <input type="hidden" name="toDo" value="registration"/>
                        <h2 class="form-title">Crea il tuo account</h2>
                        <div class="form-group">
                            <input required type="text" class="form-input form-control" name="nome" id="nome" placeholder="Nome"/>
                        </div>
                        <div class="form-group">
                            <input required type="text" class="form-input form-control" name="cognome" id="cognome" placeholder="Cognome"/>
                        </div>
                        <div class="form-group">
                            <input required type="email" class="form-input  form-control" name="email" id="email" placeholder="Email"/>
                        </div>
                        <!--<div class="form-group">
                            <input required type="text" class="form-input" name="password" id="password" placeholder="Password"/>
                            <span toggle="#password" class="zmdi zmdi-eye field-icon toggle-password"></span>
                        </div>-->
                        <div class="form-group">
                            <input required type="text" class="form-input form-control" name="username" id="username" placeholder="Username"/>
                        </div>
                        <div class="form-group">
                            <input required type="password" class="form-input form-control" name="password" id="password" placeholder="Password"/>
                        </div>
                       <!-- <div class="form-group">
                            <input type="checkbox" name="agree" id="agree" class="agree" />
                            <label for="agree"  class="label-agree form-control"><span><span></span></span>Accetto tutti i <a href="#" class="term-service">Termini di Servizio</a></label>
                        </div>-->
                        <div class="form-group">
                            <input type="submit"  name="submit" id="submit" class="form-submit form-control" value="Registrati"/>
                        </div>
                    </form>
                    <p class="loginhere">
                        Hai già un account ? <a href="<%= request.getContextPath()%>/login.jsp" class="loginhere-link">Esegui l'accesso</a>
                    </p>
                </div>
            </div>
        </section>

    </div>
</body>
</html>