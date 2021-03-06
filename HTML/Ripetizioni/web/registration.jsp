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
        <!-- Main css -->
        <link rel="stylesheet" href="css/style_1.css">
        <style>
            .error {color:red;} <!--Imposto messaggi di errore della validazione di colore rosso-->
        </style>
        <!-- JS -->
        <script src="js/jquery.min.js"></script>
        <script src="js/main_1.js"></script>
        <!-- Validazione JQUERY -->
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.min.js"></script>
        <script type="text/javascript" src="js/validazione.js"></script>
    </head>
    <body>
        <div class="main">
            <section class="signup">
                <div class="container">
                    <div class="signup-content" style="padding-top:3px;">
                        <form method="POST" id="signup-form" action="<%= request.getContextPath()%>/Controller" class="signup-form">
                            <input type="hidden" name="toDo" value="registration"/>
                            <p class="loginhere">Vuoi tornare nella homepage? <a href="<%= request.getContextPath()%>/index.jsp" class="loginhere-link">HOMEPAGE</a></p>
                            <br>
                            <h2 class="form-title">Crea il tuo account</h2>
                            <div class="form-group">
                                <input type="text" class="form-input form-control" name="nome" id="nome" placeholder="Nome"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-input form-control" name="cognome" id="cognome" placeholder="Cognome"/>
                            </div>
                            <div class="form-group">
                                <input type="email" class="form-input  form-control" name="email" id="email" placeholder="Email"/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-input form-control" name="username" id="username" placeholder="Username"/>
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-input form-control" name="password" id="password" placeholder="Password"/>
                            </div>
                            <div class="form-group">
                                <input type="submit" name="submit" id="submit" class="form-submit form-control" value="Registrati"/>
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