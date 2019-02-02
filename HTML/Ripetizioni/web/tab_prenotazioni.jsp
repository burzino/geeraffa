<%-- 
    Document   : tab_docenti
    Created on : 21-nov-2018, 9.13.45
    Author     : Geeraffa
--%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
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
    <body style="background-color: #abc;">
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
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%">
            <input type="hidden" name="toDo" value="tab_prenotazioni"/>
                <%
                    String sql;
                    sql = "SELECT Docente.nome AS nDocente, "
                            + "Docente.cognome AS cDocente, "
                            + "Prenotazione.corso AS corso, "
                            + "Prenotazione.disdetta, "
                            + "Prenotazione.DTInizio, "
                            + "Prenotazione.DTFine, "
                            + "Utente.nome AS nStudente, "
                            + "Utente.cognome AS cStudente "
                            + "FROM Prenotazione "
                            + "JOIN Docente ON Docente.ID_Docente = Prenotazione.docente "
                            + "JOIN Utente ON Utente.ID_Utente = Prenotazione.studente";
                    
                ResultSet rs = model.eseguiQuery(sql);
                %>
                <div class="animated fadeIn" style=" padding-top: 50px">
                    <div class="row">
                        <div class="col-md-12">
                            <table id="bootstrap-data-table" class="table table-hover" style="text-align:left; padding-left: 2%; padding-right: 2%; width: 90%; margin: auto;">
                                <thead>
                                    <th>Docente</th>
                                    <th>Corso</th>
                                    <th>Studente</th>
                                    <th>Data</th>  
                                    <th>Ora</th>
                                    <th>Stato</th>

                                </thead>
                                <tbody>
                                <%while(rs.next()){ 
                                
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date data = dateFormat.parse(rs.getString("DTInizio"));
                                    dateFormat.applyPattern("dd-MM-yyyy");
                                    
                                    String dataOK = dateFormat.format(data);
                                    String oraInizio = rs.getString("DTInizio").split(" ")[1].substring(0,5);
                                    String oraFine = rs.getString("DTFine").split(" ")[1].substring(0,5);
                                    
                                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                    Date date = new Date();
                                    Date dataCorrente = dateFormat1.parse(dateFormat1.format(date));
                                    
                                    Date dataFine = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("DTFine"));
                                    
                                    String stato = "";
                                    
                                   if (dataFine.before(dataCorrente)) {
                                            stato = "CONCLUSA";
                                        }
                                    else if(dataCorrente.before(dataFine))
                                        stato = "ATTIVA";
                                    else
                                        stato ="IN CORSO";
                                    
                                    if (rs.getInt("disdetta") == 1 )
                                        stato = "DISDETTA";
                                %>
                                <tr>
                                    <td><%= rs.getString("cDocente")%> <%= rs.getString("nDocente")%></td>
                                    <td><%= rs.getString("corso")%></td>
                                    <td><%= rs.getString("cStudente")%> <%= rs.getString("nStudente")%></td>
                                    <td><%= dataOK%></td>
                                    <td><%= oraInizio%> - <%= oraFine%></td>
                                    <td class="<%= stato%>"><%= stato%></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
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
        $(document).ready(function() {
          $('.CONCLUSA').css('background-color', '#b2ff59');
          $('.ATTIVA').css('background-color', '#ffca28');
          $('.DISDETTA').css('background-color', '#ff7043');
          $('.IN CORSO').css('background-color', '#ffca28');
      } );
  </script>
  <%}%>

    </body>
</html>
