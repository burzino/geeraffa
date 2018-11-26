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
    HttpSession ses = request.getSession();
    Model.registerDriver();
    List<Studente> lstStud = Model.getUtenti();
    for (int i = 0; i < lstStud.size(); i++) {
            //System.out.println(lstStud.get(i).getUsername());
        }
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

  <div class="loader"></div>
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
                    - BUONA NAVIGAZIONE, <%=ses.getAttribute("name")%>! <%}%>
                </a>
                 
                <!--<img src="img/Logo_Round.jpg"/>-->
              </div>
                <div class="collapse navbar-collapse menutendasmart" id="myNavbar" >
                <ul class="nav navbar-nav navbar-right">                    
                    <li class="active"><a href="#main-header">Home</a></li>
                    
                  <!----<li class=""><a href="#service">Services</a></li>-->
                  <li class=""><a href="#corsi">Corsi</a></li>
                  <!--<li class=""><a href="#testimonial">Testimonial</a></li>-->
                  <li class=""><a href="#contact">Contact Us</a></li>
                  <% if("Admin".equals(ses.getAttribute("ruolo")) && ses.getAttribute("logged") == "Y") { %>
                  <li class=""><a href="<%= request.getContextPath()%>/admin.jsp">
                          GESTISCI</a></li>
                  <% } %>
                  
                  <% if(ses.getAttribute("logged") == "Y") {%>
                  <li><a href="<%= request.getContextPath()%>/Controller?toDo=visualizza&corso=tutti">LE MIE PRENOTAZIONI</a></li>
                  <li><a href="<%= request.getContextPath()%>/Controller?toDo=logout">LOGOUT</a></li>
                  <!--<form action="<%= request.getContextPath()%>/Controller" id="frmout" method="post">
                  <li class="">
                    
                        <input type="hidden" name="toDo" value="logout"/>
                      <!--<li>a href="#" onclick="document.getElementById('frmout').submit();">LOGOUT</a><!--</li>-->
                    
                  <!--</li>-->
                  <!--</form>-->
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
                  <h1 class="bnr-title">Semplifica la gestione delle tue prenotazioni con <span class="logo-dec">GE</span>E<span class="logo-dec">RA</span>F<span class="logo-dec">FA</span></h1>
                <h2 class="bnr-sub-title"></h2>
                <p class="bnr-para">Effettua la registrazione facile e veloce per poter subito usufruire del nostro servizio.<br>se sei già registrato effettua il login    </p>
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
    <!--/ HEADER-->
    <!---->
    <!--<section id="feature" class="section-padding wow fadeIn delay-05s">
      <div class="container">
        <div class="row">
          <div class="col-md-3 col-sm-6 col-xs-12">
            <div class="wrap-item text-center">
              <div class="item-img">
                <img src="img/ser01.png">
              </div>
              <h3 class="pad-bt15">Creative Concept</h3>
              <p>La piattaforma si basa su un idea innovativa e mai sviluppata in precedenza.</p>
            </div>
          </div>
          <div class="col-md-3 col-sm-6 col-xs-12">
            <div class="wrap-item text-center">
              <div class="item-img">
                <img src="img/ser02.png">
              </div>
              <h3 class="pad-bt15">Amazing Design</h3>
              <p>Il design è semplice ed intuitivo per semplificarne l'utilizzo da parte di ogni utente.</p>
            </div>
          </div>
          <div class="col-md-3 col-sm-6 col-xs-12">
            <div class="wrap-item text-center">
              <div class="item-img">
                <img src="img/ser03.png">
              </div>
              <h3 class="pad-bt15">Cost effective</h3>
              <p>La registrazione è gratuita e lo sarà per sempre.</p>
            </div>
          </div>
          <div class="col-md-3 col-sm-6 col-xs-12">
            <div class="wrap-item text-center">
              <div class="item-img">
                <img src="img/ser04.png">
              </div>
              <h3 class="pad-bt15">Secure</h3>
              <p>La sicurezza dei vostri dati è la nostra priorità prima di ogni cosa.</p>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!---->
    <!----
    <section id="service" class="section-padding wow fadeInUp delay-05s">
      <div class="container">
        <div class="row">
          <div class="col-md-12 text-center">
            <h2 class="service-title pad-bt15">What We Do?</h2>
            <p class="sub-title pad-bt15">Offriamo un servizio gratuito, facile e comodo per la prenotazione delle ripetizioni.</p>
            <hr class="bottom-line">
          </div>
          <div class="col-md-3 col-sm-6 col-xs-12">
            <div class="service-item">
              <h3><span>D</span>esign And Developement</h3>
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
              <a href="">learn more...</a>
            </div>
          </div>
          <div class="col-md-3 col-sm-6 col-xs-12">
            <div class="service-item">
              <h3><span>W</span>ebsite Maintenance</h3>
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
              <a href="">learn more...</a>
            </div>
          </div>
          <div class="col-md-3 col-sm-6 col-xs-12">
            <div class="service-item">
              <h3><span>S</span>eo Optimization</h3>
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
              <a href="">learn more...</a>
            </div>
          </div>
          <div class="col-md-3 col-sm-6 col-xs-12">
            <div class="service-item">
              <h3><span>D</span>igital Marketing</h3>
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
              <a href="">Learn more...</a>
            </div>
          </div>
        </div>
      </div>
    </section>-->
    <!---->
    <!---->
    <!---->
    <!---->
    <section id="corsi" class="section-padding wow fadeInUp delay-05s">
    <div class="container">
        <div class="row" style="padding-top: 50px">
          <div class="col-md-12 text-center">
            <h2 class="service-title pad-bt15" <% if(ses.getAttribute("logged") == "Y"){ %> style="color:white"<%}%>>Corsi</h2>
            <p class="sub-title pad-bt15"<% if(ses.getAttribute("logged") == "Y"){ %> style="color:white"<%}%>>Di seguito i principali corsi di cui puoi prentoare le ripetizioni </p>
            <hr class="bottom-line">
          </div>
        <%
            Model.registerDriver();
            ResultSet rs = Model.getCorsi();
            while(rs.next())
            {
        %>
          <div class="col-md-4 col-sm-6 col-xs-12 portfolio-item padding-right-zero mr-btn-15">
            <figure>
                <img src="img/<%= rs.getString("Titolo")%>.jpg" class="img-responsive">
              <figcaption>
                <h2><%= rs.getString("Titolo")%></h2>
                <p><%= rs.getString("Descrizione")%></p>
                <% if(ses.getAttribute("logged") == "Y") { %>
                <form action="<%=request.getContextPath()%>/Controller" method="post">
                    <input type="hidden" name="toDo" value="prenota"/>
                    <input type="hidden" name="docente" value="tutti"/>
                    <input type="hidden" name="corso" value="<%= rs.getString("Titolo")%>" />
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
    <!---->
    <section id="testimonial" class="wow fadeInUp delay-05s">
      <div class="bg-testicolor">
        <div class="container section-padding">
          <div class="row">
            <div class="testimonial-item">
              <ul class="bxslider">
                <li>
                  <blockquote>
                    <img src="img/favole.png" class="img-responsive">
                    <p>Il vero banco di prova per il nostro progresso non è tanto se riusciamo a far crescere l’abbondanza di coloro che già hanno troppo, ma piuttosto consiste nel cercare di fornire abbastanza a coloro che hanno troppo poco.</p>
                  </blockquote>
                  <small>Federico Favole, CEO and Founder</small>
                </li>
                <li>
                  <blockquote>
                    <img src="img/gerbaudo.png" class="img-responsive">
                    <p>Il genere umano sarebbe rimasto nel suo stato originario se non fossero esistite altro che le donne. Ogni progresso è opera dell'uomo.</p>
                  </blockquote>
                  <small>Gerbaudo Luca, COO and Founder</small>
                </li>
                <li>
                  <blockquote>
                    <img src="img/racca.png" class="img-responsive">
                    <p>Progresso significa che per tutto occorre sempre meno tempo e sempre più denaro.</p>
                  </blockquote>
                  <small>Racca Andrea Rocco, CTO and Founder</small>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!----
    <section id="blog" class="section-padding wow fadeInUp delay-05s">
      <div class="container">
        <div class="row">
          <div class="col-md-12 text-center">
            <h2 class="service-title pad-bt15">Latest from our blog</h2>
            <p class="sub-title pad-bt15">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod<br>tempor incididunt ut labore et dolore magna aliqua.</p>
            <hr class="bottom-line">
          </div>
          <div class="col-md-4 col-sm-6 col-xs-12">
            <div class="blog-sec">
              <div class="blog-img">
                <a href="">
                  <img src="img/blog01.jpg" class="img-responsive">
                </a>
              </div>
              <div class="blog-info">
                <h2>This is Lorem ipsum heading.</h2>
                <div class="blog-comment">
                  <p>Posted In: <span>Legal Advice</span></p>
                  <p>
                    <span><a href="#"><i class="fa fa-comments"></i></a> 15</span>
                    <span><a href="#"><i class="fa fa-eye"></i></a> 11</span></p>
                </div>
                <p>We cannot expect people to have respect for laws and orders until we teach respect to those we have entrusted to enforce those laws all the time. we always want to help people cordially.</p>
                <a href="" class="read-more">Read more →</a>
              </div>
            </div>
          </div>
          <div class="col-md-4 col-sm-6 col-xs-12">
            <div class="blog-sec">
              <div class="blog-img">
                <a href="">
                  <img src="img/blog02.jpg" class="img-responsive">
                </a>
              </div>
              <div class="blog-info">
                <h2>This is Lorem ipsum heading.</h2>
                <div class="blog-comment">
                  <p>Posted In: <span>Legal Advice</span></p>
                  <p>
                    <span><a href="#"><i class="fa fa-comments"></i></a> 15</span>
                    <span><a href="#"><i class="fa fa-eye"></i></a> 11</span></p>
                </div>
                <p>We cannot expect people to have respect for laws and orders until we teach respect to those we have entrusted to enforce those laws all the time. we always want to help people cordially.</p>
                <a href="" class="read-more">Read more →</a>
              </div>
            </div>
          </div>
          <div class="col-md-4 col-sm-6 col-xs-12">
            <div class="blog-sec">
              <div class="blog-img">
                <a href="">
                  <img src="img/blog03.jpg" class="img-responsive">
                </a>
              </div>
              <div class="blog-info">
                <h2>This is Lorem ipsum heading.</h2>
                <div class="blog-comment">
                  <p>Posted In: <span>Legal Advice</span></p>
                  <p>
                    <span><a href="#"><i class="fa fa-comments"></i></a> 15</span>
                    <span><a href="#"><i class="fa fa-eye"></i></a> 11</span></p>
                </div>
                <p>We cannot expect people to have respect for laws and orders until we teach respect to those we have entrusted to enforce those laws all the time. we always want to help people cordially.</p>
                <a href="" class="read-more">Read more →</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!---->
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
              <p><i class="fa fa-phone fa-fw pull-left fa-2x"></i>num telefono</p>
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