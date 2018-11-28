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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        

        <link rel="apple-touch-icon" href="https://i.imgur.com/QRAUqs9.png">
        <link rel="shortcut icon" href="https://i.imgur.com/QRAUqs9.png">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/normalize.css@8.0.0/normalize.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/lykmapipo/themify-icons@0.1.2/css/themify-icons.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pixeden-stroke-7-icon@1.2.3/pe-icon-7-stroke/dist/pe-icon-7-stroke.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.2.0/css/flag-icon.min.css">
        <link rel="stylesheet" href="assets/css/cs-skin-elastic.css">
        <link rel="stylesheet" href="assets/css/lib/datatable/dataTables.bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <title>prenptazioni</title>
    </head>
    <body>
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
                    
                ResultSet rs = Model.eseguiQuery(sql);
                %>
                <div class="animated fadeIn" style=" padding-top: 50px">
                    <div class="row">
                        <div class="col-md-12">
                            <table id="bootstrap-data-table" class="table table-striped table-bordered" style="text-align:center;">
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
                                    
                                    
                                    DateFormat dataCorrente = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    Date date = new Date();
                                    System.out.println(dataCorrente.format(date));
                                    Date dataFine = dateFormat.parse(rs.getString("DTFine"));
                                    System.out.println(dataCorrente.format(date));
                                    String stato;
                                    if (date.toString().compareTo(dataFine.toString()) > 0) {
                                            stato = "ESPLETATA";
                                        }
                                    else if(date.toString().compareTo(dataFine.toString()) < 0)
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
                                    <td><%= stato%></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                
        </form>
    <script src="https://cdn.jsdelivr.net/npm/jquery@2.2.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.4/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/jquery-match-height@0.7.2/dist/jquery.matchHeight.min.js"></script>
    <script src="assets/js/main.js"></script>


    <script src="assets/js/lib/data-table/datatables.min.js"></script>
    <script src="assets/js/lib/data-table/dataTables.bootstrap.min.js"></script>
    <script src="assets/js/lib/data-table/dataTables.buttons.min.js"></script>
    
    <script src="assets/js/lib/data-table/buttons.bootstrap.min.js"></script>
    <script src="assets/js/lib/data-table/jszip.min.js"></script>
    <script src="assets/js/lib/data-table/vfs_fonts.js"></script>
    <script src="assets/js/lib/data-table/buttons.html5.min.js"></script>
    <script src="assets/js/lib/data-table/buttons.print.min.js"></script>
    <script src="assets/js/lib/data-table/buttons.colVis.min.js"></script>
    <script src="assets/js/init/datatables-init.js"></script>


    <script type="text/javascript">
        $(document).ready(function() {
          $('#bootstrap-data-table-export').DataTable();
      } );
  </script>
    </body>
</html>
