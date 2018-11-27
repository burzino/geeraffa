<%-- 
    Document   : prenotazione
    Created on : 23-nov-2018, 10.24.45
    Author     : Geeraffa
--%>
<%@page import="javafx.beans.property.IntegerPropertyBase"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="geeraffa.*"%>
<%@ page import="dao.*"%>

<!DOCTYPE html>
<html>
    <%
        HttpSession ses = request.getSession();
        String corso = request.getParameter("corso");
        String utente = ses.getAttribute("id").toString();
        int ID_Utente = Integer.parseInt(utente);
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

        <title>Prenotazione - <%= ses.getAttribute("name") %></title>
    </head>
    <body>
        <% if(corso.equals("tutti")) {%>
            <span class="login100-form-title" style="margin-top: 15px; padding-bottom: 20px;">
                ELENCO DELLE TUE RIPETIZIONI PRENOTATE
            </span>
        <% } else { %>
            <span class="login100-form-title" style="margin-top: 15px;">
                    ELENCO RIPETIZIONI PRENOTATE DI <%= corso.toUpperCase()%>
            </span>
        <% } %>
        <form class="login100-form validate-form" 
              action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%">
        <input type="hidden" name="toDo" value="elencoPren"/>
            <div id="container-select">
            <div class="form-group" id="divSelCorso">
                <label for="selCorsoPrenota"> Corso </label>
                <select id="selCorsoPrenota" name="corso" class="form-control">
                    <option <% if(corso.equals("tutti")) {%>
                                selected="selected"
                            <% } %>
                        value="tutti">TUTTI</option>
                    <% 
                        Model.registerDriver();
                        ResultSet rsCorsi = Model.getCorsi();
                        while(rsCorsi.next())
                        {
                    %>
                    <option value="<%= rsCorsi.getString("Titolo")%>"
                        <% if(rsCorsi.getString("Titolo").equals(corso)) {%> 
                            selected="selected"
                        <% } %> >
                            <%= rsCorsi.getString("Titolo")%>
                    </option>
                    <% } %>
                </select>
            </div>
            <div class="form-group" id="divAggiorna">
            <input type="submit" class="login100-form-btn" id="aggiorna-pren" value="CERCA"/>
            </div>
        </div>
        </form>
                <div class="animated fadeIn">
                    <div class="row" style="margin-right: 15px; margin-left: 15px;">
                        <div class="col-md-12">
                            <table id="bootstrap-data-table" class="table table-striped table-bordered" style="text-align:center;">
                                <thead>
                                    <th>Giorno</th>
                                    <th>Fascia Oraria</th>
                                    <% if(corso.equals("tutti")) {%>
                                        <th>Corso</th>
                                    <% } %>
                                    <th>Docente</th>
                                    <th>Prenota</th>
                                </thead>
                                <tbody>
                                    <%
                                        Model.registerDriver();
                                        ResultSet rsPren;
                                        if(corso.equals("tutti"))
                                            rsPren = Model.getPrenotazioniPersonali(ID_Utente);
                                        else
                                            rsPren = Model.getPrenotazioniCorso(corso, ID_Utente);
                                        
                                        while(rsPren.next()){ 
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            Date data = dateFormat.parse(rsPren.getString("DTInizio"));
                                            dateFormat.applyPattern("dd-MM-yyyy");
                                            String dataOK = dateFormat.format(data);
                                            String oraInizio = rsPren.getString("DtInizio").split(" ")[1].substring(0,5);
                                            String oraFine = rsPren.getString("DTFine").split(" ")[1].substring(0,5);                                            
                                            
                                            ResultSet rsCorso = Model.getCorsoPrenotazione(rsPren.getString("Corso"));
                                            ResultSet rsDocente = Model.getDocentiPrenotazione(rsPren.getInt("Docente"));
                                            if(rsDocente.next() && rsCorso.next()){
                                    %>
                                        <tr>

                                            <td><%= dataOK%></td>
                                            <td><%= oraInizio %> - <%= oraFine %></td>
                                            <% if(corso.equals("tutti")) {%>
                                                <td> <%= rsCorso.getString("Titolo") %>
                                            <% } %>
                                            <td><%= rsDocente.getString("Cognome") %> <%= rsDocente.getString("Nome") %></td>
                                            <td>
                                                <input type="button" class="btn btn-danger" data-toggle="modal" 
                                                    onclick="window.location.href='<%= request.getContextPath()%>/Controller?toDo=disdici&id=<%= rsPren.getInt("ID_Prenotazione")%>'"
                                                value="Disdici"/>
                                            </td>
                                        </tr>
                                    <% }
                                    } %>
                                
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div> 
        <!--</form>-->
                                
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
