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
<!DOCTYPE html>
<html lang="it">
    <%
        Model model = new Model();
        HttpSession ses = request.getSession();        
    %>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>GEERAFFA -
    <% if(ses.getAttribute("logged") == "Y") {%>
        <%= ses.getAttribute("name")%>
    <% } else {%>
        ripetizioni
    <%}%>
  </title>
  <meta name="description" content="Free Bootstrap Theme by BootstrapMade.com">
  <meta name="keywords" content="free website templates, free bootstrap themes, free template, free bootstrap, free website template">

  <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:400,300|Raleway:300,400,900,700italic,700,300,600">
  <link rel="stylesheet" type="text/css" href="css/jquery.bxslider.css">
  <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/animate.css">
  <link rel="stylesheet" type="text/css" href="css/style.css">
  <!--<link rel="stylesheet" type="text/css" href="css/main.css">-->
</head> 

<body>

  <!--<div class="loader"></div>-->
  <div id="myDiv">
    <!--HEADER-->
    <div class="header">
      <div class="bg-color">
        <header id="main-header">
          <nav class="navbar navbar-default navbar-fixed-top">
              <div class="container" >
              <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
                <a class="navbar-brand" href="#main-header">GEE<span class="logo-dec">RAFFA</span>
                    <% if(ses.getAttribute("logged") == "Y"){ %>
                    - CIAO, <%=ses.getAttribute("name")%>! <%}%>
                </a>
                 
              </div>
                <div class="collapse navbar-collapse menutendasmart" id="myNavbar" >
                <ul class="nav navbar-nav navbar-right">    
                    <% if(ses.getAttribute("logged") == null) { %>
                        <li class="active"><a href="#main-header">Home</a></li>
                    <% } %>
                  <li class=""><a href="#corsi">Corsi</a></li>
                  <% if(ses.getAttribute("logged") == "Y") {%>
                  <li><a href="<%= request.getContextPath()%>/Controller?toDo=elencoPren&corso=tutti">LE MIE PRENOTAZIONI</a></li>
                  <li><a href="<%= request.getContextPath()%>/Controller?toDo=prenota_a">NUOVA PRENOTAZIONE</a></li>
                  <%}%>
                  <li class=""><a href="#contact">Contact Us</a></li>
                  <% if("Admin".equals(ses.getAttribute("ruolo")) && ses.getAttribute("logged") == "Y") { %>
                  <li class=""><a href="<%= request.getContextPath()%>/tab_docenti.jsp">
                          GESTISCI</a></li>
                  <% } %>
                  
                  <% if(ses.getAttribute("logged") == "Y") {%>                
                  <li><a href="<%= request.getContextPath()%>/Controller?toDo=logout">LOGOUT</a></li>               
                  <% } %>                                      
                    
                </ul>
              </div>
            </div>
          </nav>
        </header>
        <% if(ses.getAttribute("logged") != "Y"){ %>
        <div class="wrapper">
          <div class="container">
            <div class="row" >
              <div class="banner-info text-center wow fadeIn delay-05s">
                  <h1 class="bnr-title">Semplifica la gestione delle tue prenotazioni con 
                      <span class="logo-dec">GE</span>E
                      <span class="logo-dec">RA</span>F
                      <span class="logo-dec">FA</span><br>
                      Prenota ripetizioni dei corsi disponibili, tutto in pochi click!
                  </h1>
                <h2 class="bnr-sub-title"></h2>
                <p class="bnr-para">Effettua la registrazione facile e veloce per poter subito usufruire del nostro servizio.<br>Se sei già registrato effettua il login    </p>
                <div class="brn-btn">
                  <a href="<%= request.getContextPath()%>/login.jsp" class="btn btn-download">LOG IN!</a>
                  <a href="<%= request.getContextPath()%>/registration.jsp" class="btn btn-more">Registrati</a>
                </div>
                <div class="overlay-detail">
                  <a href="#corsi"><i class="fa fa-angle-down"></i></a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <% } %>
    <section id="corsi" class="section-padding wow fadeInUp delay-05s" style="background-color: rgb(73, 84, 105);">
    <div class="container">
        <div class="row" style="padding-top: 50px">
          <div class="col-md-12 text-center">
            <h2 class="service-title pad-bt15" style="color:white">Corsi</h2>
            <p class="sub-title pad-bt15"style="color:white;">Di seguito i principali corsi di cui puoi prentoare le ripetizioni </p>
            <hr class="bottom-line">
          </div>
        <%
            List<Corso> corsi = model.listCorsi();
            
            for(Corso c : corsi)
            {
        %>
          <div class="col-md-4 col-sm-6 col-xs-12 portfolio-item padding-right-zero mr-btn-15">
            <figure>
                <img src="<%= c.getPath() %>" class="img-responsive">
              <figcaption>
                <h2><%= c.getTitolo() %></h2>
                <p><%= c.getDescrizione() %></p>
                <% if(ses.getAttribute("logged") == "Y") { %>
                <form action="<%=request.getContextPath()%>/Controller" method="post">
                    <input type="hidden" name="toDo" value="prenotaIndex"/>
                    <input type="hidden" name="corso" value="<%= c.getTitolo() %>" />
                    <br/>
                    <input type="submit" style="border:2px solid #444F64; color: white" class="btn-submit" value="PRENOTA"/>
                </form>
                <% } %>
              </figcaption>              
            </figure>
          </div>
        <%}%>
        </div>
      </div>
    </section>
   
   
    <section id="contact" class="section-padding wow fadeInUp delay-05s">
      <div class="container">
        <div class="row">
          <div class="col-md-12 text-center white">
            <h2 class="service-title pad-bt15">Keep in touch with us</h2>
            <p class="sub-title pad-bt15"></p>
            <hr class="bottom-line white-bg">
          </div>
          <div class="col-md-6 col-sm-6 col-xs-12">
            <div class="loction-info white">
              <p><i class="fa fa-map-marker fa-fw pull-left fa-2x"></i>Via sballapiero<br>Torino (TO) 10149 ITALY</p>
              <p><i class="fa fa-envelope-o fa-fw pull-left fa-2x"></i>info@geeraffa.com</p>
              <p><i class="fa fa-phone fa-fw pull-left fa-2x"></i>3334455566</p>
            </div>
          </div>
          <div class="col-md-6 col-sm-6 col-xs-12">
            <div class="contact-form">
              <div id="sendmessage">Your message has been sent. Thank you!</div>
              <div id="errormessage"></div>
              <form action="" method="post" role="form" class="contactForm">
                <div class="col-md-6 padding-right-zero">
                  <div class="form-group">
                    <input type="text" name="name" class="form-control" id="name" placeholder="Il tuo nome" data-rule="minlen:4" data-msg="Inserire un nome di almeno 4 caratteri" />
                    <div class="validation"></div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <input type="email" class="form-control" name="email" id="email" placeholder="La tua Email" data-rule="email" data-msg="Inserire una mail valida" />
                    <div class="validation"></div>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <input type="text" class="form-control" name="subject" id="subject" placeholder="Oggetto" data-rule="minlen:4" data-msg="Inserire un oggetto di almeno 8 caratteri" />
                    <div class="validation"></div>
                  </div>
                </div>
                <div class="col-md-12">
                  <div class="form-group">
                    <textarea class="form-control" name="message" rows="5" data-rule="required" data-msg="Inserire un testo del messaggio" placeholder="Messaggio"></textarea>
                    <div class="validation"></div>
                  </div>
                  <button type="submit" class="btn btn-primary btn-submit">INVIA</button>
                </div>
              </form>

            </div>
          </div>
        </div>
      </div>
    </section>
    <!---->
    <!---->
    <footer id="footer">
      <div class="container">
        <div class="row text-center">
          <p>&copy; GEERAFFA Theme. All Rights Reserved.</p>
          <div class="credits">
            Designed by GEERAFFA
          </div>
        </div>
      </div>
    </footer>
    <!---->
  </div>

  <script src="js/jquery.min.js"></script>
  <script src="js/jquery.easing.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/wow.js"></script>
  <script src="js/jquery.bxslider.min.js"></script>
  <script src="js/custom.js"></script>
  <script src="contactform/contactform.js"></script>

</body>
</html>