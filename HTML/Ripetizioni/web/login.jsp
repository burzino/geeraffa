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
    <!--===============================================================================================-->
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>
    <div class="limiter">
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

                    <div class="wrap-input100 validate-input" data-validate = "Valid username is required">
                        <input class="input100" type="text" tabindex="1" name="username" placeholder="Username or Email">
                        <span class="focus-input100"></span>
                        <span class="symbol-input100">
                            <i class="fa fa-envelope" aria-hidden="true"></i>
                        </span>
                    </div>

                    <div class="wrap-input100 validate-input" data-validate = "Password is required">
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
    </div>
	
		
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/tilt.jquery.min.js"></script>
    <script src="js/jquery.min.js"></script>
<script >
        $('.js-tilt').tilt({
                scale: 1.1
        })
</script>
<!--===============================================================================================-->
<script src="js/main.js"></script>

</body>
</html>
