<%-- 
    Document   : login
    Created on : Nov 15, 2018, 6:29:31 PM
    Author     : GEERAFFA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="geeraffa.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login Geeraffa</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/util.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" href="css/style_1.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" href="css/style_1.css">
    <style>
        .error {color:red;} <!--Imposto messaggi di errore della validazione di colore rosso-->
         
    </style>
        <!-- JS -->
    <script src="js/jquery.min.js"></script>
    <script src="js/main_1.js"></script>
    <script>
        //Modifica campo di testo che notifica l'utente che il campo è obbligatorio
        document.addEventListener("DOMContentLoaded", function() {
            var elements = document.getElementsByTagName("INPUT");
            for (var i = 0; i < elements.length; i++) {
                elements[i].oninvalid = function(e) {
                    e.target.setCustomValidity("");
                    if (!e.target.validity.valid) {
                        e.target.setCustomValidity("Compilare questo campo");
                    }
                };
                elements[i].oninput = function(e) {
                    e.target.setCustomValidity("");
                };
            }
        })
    </script>
    
    
    
    <!-- Validazione JQUERY -->
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.min.js"></script>
    <script type="text/javascript" src="js/validazione.js"></script>
    
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>
    
        
        <div class="main">
            <section class="signup">
                <div class="container">
                    <div class="signup-content">
                        <form method="POST" id="signup-form" action="<%= request.getContextPath()%>/Controller" class="signup-form">
                            <input type="hidden" name="toDo" value="login"/>
                            <h2 class="form-title">Effettua il login</h2>
                            <div class="form-group">
                                <input required type="text" class="form-input form-control" name="username" id="username" placeholder="Username o Email"/>
                            </div>
                            <div class="form-group">
                                <input required type="text" class="form-input form-control" name="pwd" id="password" placeholder="Password"/>
                            </div>
                            <div class="form-group">
                            <input type="submit"  name="submit" id="submit" class="form-submit form-control" value="LOGIN"/>
                            </div>
                        </form>
                    </div>
                </div>
            </section>
                
    
    <!--<div class="limiter">
        <div class="container-login100">
            <div class="wrap-login100">
                <div class="login100-pic js-tilt" data-tilt>
                    <img src="img/img-01.png" alt="IMG">
                </div>

                <form class="login100-form validate-form" 
                      action="<%=request.getContextPath()%>/Controller" method="post" style="width: 320px">
                    <input type="hidden" name="toDo" value="login"/>
                    <span class="login100-form-title">
                        LOGIN - GEE<span class="logo-dec">RAFFA</span> 
                    </span>

                    <div class="wrap-input100" >
                        <input class="input100" type="text" tabindex="1" name="username" placeholder="Username or Email">
                        <span class="focus-input100"></span>
                        <span class="symbol-input100">
                            <i class="fa fa-envelope" aria-hidden="true"></i>
                        </span>
                    </div>

                    <div class="wrap-input100" >
                        <input class="input100" type="password" tabindex="2" name="pwd" placeholder="Password">
                        <span class="focus-input100"></span>
                        <span class="symbol-input100">
                            <i class="fa fa-lock" aria-hidden="true"></i>
                        </span>
                    </div>

                    <div class="container-login100-form-btn">
                        <input type="submit" class="login100-form-btn" value="Login"/>
                    </div>
                    
                    <% if(request.getAttribute("logged") == "N"){ %>
                        <br/><h5 align="center" style="color:red">Username o password errati.</h5>
                    <% } %>
                </form>
            </div>
        </div>
    </div>-->
	
		
<!--===============================================================================================-->	
<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="vendor/bootstrap/js/popper.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="vendor/tilt/tilt.jquery.min.js"></script>
<script >
        $('.js-tilt').tilt({
                scale: 1.1
        })
</script>
<!--===============================================================================================-->
<script src="js/main.js"></script>

</body>
</html>
