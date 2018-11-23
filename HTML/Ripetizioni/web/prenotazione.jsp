<%-- 
    Document   : prenotazione
    Created on : 23-nov-2018, 10.24.45
    Author     : Geeraffa
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="geeraffa.*"%>
<%@ page import="dao.*"%>

<!DOCTYPE html>
<html>
    <%
        HttpSession ses = request.getSession();
        String upperCorso = request.getParameter("corso").toUpperCase();
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        

        <link rel="apple-touch-icon" href="https://i.imgur.com/QRAUqs9.png">
        

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/normalize.css@8.0.0/normalize.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/lykmapipo/themify-icons@0.1.2/css/themify-icons.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pixeden-stroke-7-icon@1.2.3/pe-icon-7-stroke/dist/pe-icon-7-stroke.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.2.0/css/flag-icon.min.css">
        <link rel="stylesheet" href="assets/css/cs-skin-elastic.css">
        <link rel="stylesheet" href="assets/css/lib/datatable/dataTables.bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="css/newcss.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">

        <title>Prenotazione - <%= request.getParameter("corso")%></title>
    </head>
    <body>
        
        <span class="login100-form-title" style="margin-top: 15px;">
            PRENOTA LA TUA RIPETIZIONE DI <%= upperCorso %>
        </span>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 98%">
        <div id="container-select">
            <div class="form-group" id="divSelCorso">
                <label for="selCorsoPrenota"> Corso </label>
                <select id="selCorsoPrenota" class="form-control">
                    
                </select>
            </div>
            <div class="form-group" id="divSelCorso2">
                <label for="selCorsoPrenota"> Docente </label>
                <select id="selCorsoPrenota" class="form-control">
                    
                </select>
            </div>
        </div>
            <input type="hidden" name="toDo" value="tab_corsi"/>
                <%
                    Model.registerDriver();
                    ResultSet rs = Model.getPrenotazioni();
                %>
                <div class="animated fadeIn" style=" padding-top: 50px">
                    <div class="row">
                        <div class="col-md-12">
                            <table id="bootstrap-data-table" class="table table-striped table-bordered" style="text-align:center;">
                                <thead>
                                    <th>Giorno</th>
                                    <th>Fascia Oraria</th>
                                    <th>Conferma</th>
                                </thead>
                                <tbody>
                                    <%while(rs.next()){ 
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        Date data = dateFormat.parse(rs.getString("DTInizio"));
                                        dateFormat.applyPattern("dd-MM-yyyy");
                                        String dataOK = dateFormat.format(data);
                                        String oraInizio = rs.getString("DtInizio").split(" ")[1].substring(0,5);
                                        String oraFine = rs.getString("DTFine").split(" ")[1].substring(0,5);
                                    %>
                                    <tr>
                                    
                                    <td><%= dataOK%></td>
                                    <td><%= oraInizio %> - <%= oraFine %></td>
                                    <td>
                                        <input type="button" class="btn btn-warning" data-toggle="modal" 
                                               data-target="#modificaCorsi"
                                               value="Prenota"/>
                                    </td>
                                </tr>
                                <% } %>
                                
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
    </body>
</html>
