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
        <title>GEERAFFA - Admin - Docenti</title>

        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="shortcut icon" href="img/Logo_Round.jpg">

        <!--<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/style.css">-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
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
    <body style="padding-right: 0; padding-left:0; width: 100%; background-color: #abc;">
        <!-- se l'utente non è un admin oppure se non c'è nessun utente loggato segnalo l'errore e metto bottone per tornare alla home-->
        <%
            Model model = new Model();
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
         <!-- se l'utente loggato è un admin stampo la tabella con le varie informazioni e permetto la manipolazione di tali dati-->
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
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_corsi" target="">VISUALIZZA CORSI</a>
                    </li>
                    <li class="nav-item">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=tab_prenotazioni&stud=tutti&corso=tutti" target="">VISUALIZZA PRENOTAZIONI</a>
                    </li>
                    <li class="nav-item">
                        <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=prenota_a" target="">NUOVA PRENOTAZIONE</a>
                    </li>
                </ul>
                <li class="nav-item">
                    <a style="color:#be9e21; float: right;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=logout">LOGOUT</a>
               </li>
            </div>
        </nav>
        <span class="login100-form-title" style="margin: auto; padding-top: 1%;padding-bottom: 1%;">
            GESTIONE DOCENTI
        </span>
        <form>
            <input type="hidden" name="toDo" value="modificaDocenti"/>
            <input type="button" class="btn btn-primary" value="Aggiungi nuovo docente" data-toggle="modal" data-target="#modificaDocenti" onclick="addDocente(this)" style="display: block; margin: 0 auto;"/>
        </form>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%">
            <input type="hidden" name="toDo" value="tab_docenti"/>
                <%
                String sql = "SELECT * FROM Docente WHERE Attivo = 1 ORDER BY Docente.Cognome";
                ResultSet rs = model.eseguiQuery(sql);
                ResultSet rsCorsi = null;
                %>
                <div class="animated fadeIn" style=" padding-top: 1%">
                    <div class="row">
                        <div class="col-md-12">
                            <!-- stampo la dabella in modo dinamico dopo la lettura dei dati sul db-->
                            <table id="bootstrap-data-table" class="table table-hover" style="text-align:left; padding-left: 2%; padding-right: 2%; width: 90%; margin: auto;">
                                <thead>
                                    <th>Nome</th>
                                    <th>Cognome</th>
                                    <th>Email</th>
                                    <th>Corsi</th>
                                    <th>Gestione</th>
                                </thead>
                                <tbody>
                                <%
                                    String corsi;
                                    while(rs.next()){
                                        sql = "SELECT * FROM CorsoDocente WHERE attivo = 1 AND Docente = " + rs.getInt("ID_Docente");
                                        rsCorsi = model.eseguiQuery(sql);
                                        corsi = "";
                                        while(rsCorsi.next()){
                                           corsi+=rsCorsi.getString("Corso") +" ";
                                        }
                                        %>
                                <tr>
                                    <td><%= rs.getString("Nome")%></td>
                                    <td><%= rs.getString("Cognome")%></td>
                                    <td><%= rs.getString("Email")%></td>
                                    <td><%=corsi%></td>
                                    <td><input type="button" class="btn btn-warning" value="gestisci" id="<%=rs.getInt("ID_Docente")%>" data-toggle="modal" data-target="#modificaDocenti" onClick="modificaDocente(this,'<%=rs.getString("ID_Docente")%>','<%=rs.getString("Nome")%>','<%=rs.getString("Cognome")%>','<%=rs.getString("Email")%>','<%=corsi%>')" ></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
        </form>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%">
                 <!-- Modal che viene popolato dinamicamente dopo aver fatto una lettura sul db (modificato anche con funzione javascript-->
                <input type="hidden" name="toDo" value="modificaDocenti"/>
                <div class="modal fade" id="modificaDocenti" tabindex="-1" role="dialog" aria-labelledby="modificaDocentiLabel" aria-hidden="true">
                  <div class="modal-dialog" role="document">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                        <h5 class="modal-title" id="modificaDocentiTitle">Modifica Docenti</h5>
                        <h5 class="modal-title" id="aggiungiDocentiTitle">Aggiungi Nuovo Docente</h5>
                      </div>
                      <div class="modal-body">
                          <table style="width: 100%;">
                              <tr id="hiddenID" style="display: none;">
                                    <td>ID docente </td>
                                    <td><input type="text" id="idDocente" name="idDocente" class="inputTxt rounded"/></td>
                                </tr>
                                <tr>
                                    <td>Nome: </td>
                                    <td><input type="text" id="nome" name="nome" class="inputTxt rounded"/></td>
                                </tr>
                                <tr>
                                    <td>Cognome: </td>
                                    <td><input type="text" id="cognome" name="cognome" class="inputTxt rounded"/></td>
                                </tr>
                                <tr>
                                    <td>Email: </td>
                                    <td><input type="text" id="email" name="email" class="inputTxt rounded"/></td>
                                </tr>
                                <tr>
                                    <td>Corso: </td>
                                    <td>
                                        <!-- popolo il modal i corsi leggendo su db quali sono quelli attivi-->
                                        <table>
                                        <% 
                                            sql = "SELECT * FROM Corso ORDER BY Attivo DESC, Titolo";
                                            rs = model.eseguiQuery(sql);
                                            int i = 0;
                                            while(rs.next()){
                                            %>
                                            <tr>
                                                <td>
                                                    <input type="checkbox" class="corsi" name="corsi" id="<%= rs.getString("titolo")%>" value="<%= rs.getString("titolo")%>"/>
                                                </td>
                                                <td>
                                                    <p style="
                                                           <%if(rs.getInt("Attivo") == 0){%>
                                                            color:red;
                                                           <%}%>
                                                    ">
                                                        <%= rs.getString("titolo")%>
                                                   </p>
                                                </td>
                                            
                                        <%}%>
                                        
                                        </table>
                                       </td>
                                </tr>
                          </table>
                            <br>
                            <p style="color:red;">I CORSI SCRITTI IN ROSSO SONO ATTUALMENTE DISABILITATI</p>       
                      </div>
                      <!-- bottoni del modal che vengono disattivati in base a quello che devo fare (modificare o aggiungere un docente)-->
                      <div class="modal-footer">
                          <input type="button" class="btn btn-secondary" data-dismiss="modal" value="Annulla" id="annulla"/>
                          <input type="submit" class="btn btn-danger" value="Elimina" name="elimina" id="elimina"/>
                          <input type="submit" class="btn btn-success" value="Salva Modifiche" name="salva" id="salva"/>
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
<!--    <script src="assets/js/init/datatables-init.js"></script><!-- gestisce ricerca e paginazione della tabella ma non da problema al padding-->

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    <script type="text/javascript">
      //funzioni che servono a popolare e gestire i campi del modal infatti se devo modificare un docente i campi vengono popolati con le informazioni già salvate 
      function modificaDocente(btn, idDocente, nome, cognome, email, corso){
          //alert(btn.id);
          document.getElementById("idDocente").value = idDocente;
          document.getElementById("nome").value = nome;
          document.getElementById("cognome").value = cognome;
          document.getElementById("email").value = email;
          
          document.getElementById("aggiungi").style.display = "none";
          document.getElementById("aggiungiDocentiTitle").style.display = "none";
          document.getElementById("elimina").style.display = "block";
          document.getElementById("salva").style.display = "block";
          document.getElementById("modificaDocentiTitle").style.display = "block";
          var corsi = corso.split(" ");
          //$('.corsi').attr('checked', false);
          $(".corsi").prop("checked", false);
          for (var i = 0; i < corsi.length-1; i++) {
            document.getElementById(corsi[i]).checked = true;
          }
          //alert(corsi);
      }
      function addDocente(btn){
          //alert("ci sono");
          document.getElementById("idDocente").value = "";
          document.getElementById("nome").value = "";
          document.getElementById("cognome").value = "";
          document.getElementById("email").value = "";
          document.getElementById("elimina").style.display = "none";
          document.getElementById("salva").style.display = "none";
          document.getElementById("modificaDocentiTitle").style.display = "none";
          document.getElementById("aggiungi").style.display = "block";
          document.getElementById("aggiungiDocentiTitle").style.display = "block";
          //$('.corsi').attr('checked', false);
          $(".corsi").prop("checked", false);
          //alert( $('.corsi'));
      }
  </script>
  <%}%>
    </body>
</html>
