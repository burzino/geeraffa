<%-- 
    Document   : tab_docenti
    Created on : 21-nov-2018, 9.13.45
    Author     : Geeraffa
--%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="geeraffa.*"%>
<%@ page import="dao.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>GEERAFFA - Admin - Corsi</title>

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
    <body style="width: 100%; background-color: #abc;">
        <%
            Model model = new Model();
            HttpSession ses = request.getSession();
            if(!("Admin".equals(ses.getAttribute("ruolo"))) || ses.getAttribute("logged") == "N") { %>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%;">
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
            <div style="background-color:#475369; color:#be9e21;" class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/index.jsp">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_docenti" target="">VISUALIZZA DOCENTI</a>
                    </li>
                    <li class="nav-item">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_prenotazioni" target="">VISUALIZZA PRENOTAZIONI</a>
                    </li>
                    <li class="nav-item">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=prenota_a" target="">NUOVA PRENOTAZIONE</a>
                    </li>
                </ul>
            </div>
        </nav>
        <span class="login100-form-title" style="margin: auto; padding-top: 2%;">
            GESTIONE CORSI
        </span>
        <form>
            <input type="hidden" name="toDo" value="modificaCorsi"/>
            <input type="button" class="btn btn-primary" value="Aggiungi nuovo corso" data-toggle="modal" data-target="#modificaCorsi" onclick="btnVisible(this)" style="display: block; margin: 0 auto;"/>
        </form>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 98%">
            <input type="hidden" name="toDo" value="tab_corsi"/>
                <%
                    String sql;
                    sql = "SELECT * FROM Corso WHERE Attivo = 1";
                ResultSet rs = model.eseguiQuery(sql);
                %>
                <div class="animated fadeIn" style=" padding-top: 50px">
                    <div class="row">
                        <div class="col-md-12">
                            <table id="bootstrap-data-table" class="table table-hover" style="text-align:left; padding-left: 2%; padding-right: 2%; width: 90%; margin: auto;">
                                <thead>
                                    <th>Titolo</th>
                                    <th>Descrizione</th>
                                    <th>Gestione</th>
                                </thead>
                                <tbody>
                                <%while(rs.next()){ %>
                                <tr>
                                    <td><%= rs.getString("Titolo")%></td>
                                    <td><%= rs.getString("Descrizione")%></td>
                                    <td>
                                        <input type="button" class="btn btn-warning" data-toggle="modal" data-target="#modificaCorsi" id="<%=rs.getString("Titolo")%>" onClick="getId(this,'<%=rs.getString("Descrizione")%>')" value="Gestisci"/>
                                    </td>
                                </tr>
                                <% } %>
                                
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                                
                
        </form>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 98%">
                                    <!-- Modal -->
                <input type="hidden" name="toDo" value="modificaCorsi"/>
                <div class="modal fade" id="modificaCorsi" tabindex="-1" role="dialog" aria-labelledby="modificaCorsiLabel" aria-hidden="true">
                  <div class="modal-dialog" role="document">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                        <h5 class="modal-title" id="modificaCorsiTitle">Modifica Corsi</h5>
                        <h5 class="modal-title" id="aggiungiCorsiTitle">Aggiungi Nuovo Corso</h5>

                        
                      </div>
                      <div class="modal-body">
                          <table style="width: 98%;">
                                <tr>
                                    <td>Titolo: </td>
                                    <td>
                                        <input type="text" id="titolo" name="titolo" class="inputTxt rounded"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Descriozione: </td>
                                    <td>
                                        <input type="text" id="descrizione" name="descrizione" class="inputTxt rounded"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Path immagine: </td>
                                    <td >
                                        <input type='file' id="path" class="btn btn-primary inputTxt" onchange="getFile(this)"/>
                                    </td>
                                </tr>
                                <tr style="visibility: hidden;">
                                    <td>Path immagine: </td>
                                    <td>
                                        <input type='text' id="txtPath" name="txtPath" class="inputTxt rounded"  />
                                    </td>
                                </tr>
                          </table>
                          
                      </div>
                      <div class="modal-footer">
                          <input type="button" class="btn btn-secondary" data-dismiss="modal" value="Annulla" id="annulla"/>
                          <input type="submit" class="btn btn-danger" value="Elimina" name="elimina" id="elimina"/>
                          <input type="submit" class="btn btn-success" value="Salva" name="salva" id="salva"/>
                          <input type="submit" class="btn btn-success" value="Aggiungi" name="aggiungi" id="aggiungi"/>


                      </div>
                    </div>
                  </div>
                </div>
            </form>
                                    
    <script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>
    <script src="js/jquery.min.js"></script>

<!--    <script src="assets/js/lib/data-table/datatables.min.js"></script><!-- gestisce ricerca e paginazione della tabella  da problemi al padding-->
<!--    <script src="assets/js/lib/data-table/dataTables.bootstrap.min.js"></script><!-- script che gestisce la ricerca e paginazione della tabella, inoltre non da il problema del padding quando viene aperto e chiuso il modal-->  
<!--  <script src="assets/js/init/datatables-init.js"></script><!-- gestisce ricerca e paginazione della tabella ma non da problema al padding-->

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    
    <script type="text/javascript">
        function getId(btn, desc){
            var titolo = document.getElementById("titolo");
            titolo.value = btn.id;
            var descrizione = document.getElementById("descrizione");
            descrizione.value = desc;        
            document.getElementById("aggiungi").style.display = "none";
            document.getElementById("aggiungiCorsiTitle").style.display = "none";
            document.getElementById("elimina").style.display = "block";
            document.getElementById("salva").style.display = "block";
            document.getElementById("modificaCorsiTitle").style.display = "block";
            document.getElementById('titolo').onkeydown = function(e){
                e.preventDefault();
            }
      }
      
        function getFile(file){
            var path = $("#path").val();
            var splitPath = path.split('\\');
            var imgName= splitPath[splitPath.length - 1];
            var txtPath=document.getElementById("txtPath");
            txtPath.value=imgName;
        }
      
        function btnVisible(btn){
            document.getElementById("elimina").style.display = "none";
            document.getElementById("salva").style.display = "none";
            document.getElementById("modificaCorsiTitle").style.display = "none";
            document.getElementById("aggiungi").style.display = "block";
            document.getElementById("aggiungiCorsiTitle").style.display = "block";
            document.getElementById("titolo").disabled = false;
            document.getElementById("titolo").value = "";
            document.getElementById("descrizione").value = "";
        }
  </script>
  <%}%>

    </body>
</html>
