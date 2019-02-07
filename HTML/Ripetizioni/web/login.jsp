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
    <link rel="stylesheet" type="text/css" href="css/bootstrapPren.min.css">
    <link rel="stylesheet" type="text/css" href="css/util.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" href="css/style_1.css">
    <link rel="stylesheet" href="css/login.css">
    <!--===============================================================================================-->   
    
    <!-- JS -->
    <script src="js/jquery.min.js"></script>
    <script src="js/main_1.js"></script>
    <script src="js/login.js"></script>
    <script>
        
    </script>
    
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>
    
        
        <div class="main">
            <section class="signup">
                <div class="container">
                    <div class="signup-content" id="divLogin">                        
                        <h5 style="visibility: hidden"> SPAZIO </h5>
                        <h2 class="form-title">Effettua il login</h2>
                        <div class="form-group">
                            <input type="text" tabindex="1"
                                   class="form-input form-control" 
                                   name="username" id="username" 
                                   placeholder="Username o Email"/>
                        </div>
                        <div class="form-group">
                            <input type="password" tabindex="2"
                                   class="form-input form-control" 
                                   name="pwd" id="password" 
                                   placeholder="Password"/>
                        </div>

                        <div class="form-group">
                            <input type="button" tabindex="3"
                                   disabled
                                   onclick="verifica('<%= request.getContextPath()%>/Controller')" 
                                   id="submit" class="login100-form-btn" 
                                   value="LOGIN"/>
                        </div>
                        <h5 id="errorLogin" 
                            align="center" 
                            style="visibility:hidden; color:red">
                            Username o password errati.
                        </h5>
                    </div>
                </div>
            </section>
    </div>
</body>
</html>
