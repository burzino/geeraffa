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
        <link rel="stylesheet" href="css/newcss.css">

        <title>corsi</title>
    </head>
    <body>
        
            <form>
                <input type="hidden" name="toDo" value="modificaCorsi"/>
                <input type="button" class="btn btn-primary" value="Aggiungi nuovo corso" data-toggle="modal" data-target="#modificaCorsi" onclick="btnVisible(this)" style="margin-top:3%; margin-left: 40%;"/>
            </form>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 98%">
            <input type="hidden" name="toDo" value="tab_corsi"/>
                <%
                Model.registerDriver();
                ResultSet rs = Model.getCorsi();
                %>
                <div class="animated fadeIn" style=" padding-top: 50px">
                    <div class="row">
                        <div class="col-md-12">
                            <table id="bootstrap-data-table" class="table table-striped table-bordered" style="text-align:center;">
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
                                    <td><input type="text" id="titolo" name="titolo"/></td>
                                </tr>
                                <tr>
                                    <td>Descriozione: </td>
                                    <td><input type="text" id="descrizione" name="descrizione"/></td>
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
      
      function getId(btn, desc){
          //alert(btn.id);
          var titolo = document.getElementById("titolo");
          titolo.value = btn.id;
          var descrizione = document.getElementById("descrizione");
          descrizione.value = desc;
          
          document.getElementById("aggiungi").style.display = "none";
          document.getElementById("aggiungiCorsiTitle").style.display = "none";
          document.getElementById("elimina").style.display = "block";
          document.getElementById("salva").style.display = "block";
          document.getElementById("modificaCorsiTitle").style.display = "block";
          document.getElementById("titolo").disabled = true;


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
    </body>
</html>
