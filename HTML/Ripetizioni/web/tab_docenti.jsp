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

        <title>docenti</title>
    </head>
    <body>
        <form>
                <input type="hidden" name="toDo" value="modificaDocenti"/>
                <input type="button" class="btn btn-primary" value="Aggiungi nuovo docente" data-toggle="modal" data-target="#modificaDocenti" onclick="btnVisible(this)" style="margin-top:3%; margin-left: 40%;"/>
        </form>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%">
            <input type="hidden" name="toDo" value="tab_docenti"/>
                <%
                Model.registerDriver();
                String sql = "SELECT * FROM Docente WHERE Attivo = 1";
                ResultSet rs = Model.eseguiQuery(sql);
                ResultSet rsCorsi = null;
                %>
                <div class="animated fadeIn" style=" padding-top: 50px">
                    <div class="row">
                        <div class="col-md-12">
                            <table id="bootstrap-data-table" class="table table-striped table-bordered" style="text-align:center;">
                                <thead>
                                    <th>Nome</th>
                                    <th>Cognome</th>
                                    <th>Email</th>
                                    <th>Gestione</th>
                                </thead>
                                <tbody>
                                <%
                                    String corsi;
                                    while(rs.next()){
                                        sql = "SELECT * FROM CorsoDocente WHERE attivo = 1 AND Docente = " + rs.getInt("ID_Docente");
                                        rsCorsi = Model.eseguiQuery(sql);
                                        corsi = "";
                                        while(rsCorsi.next()){
                                           corsi+=rsCorsi.getString("Corso") +";";
                                        }
                                        %>
                                <tr>
                                    <td><%= rs.getString("Nome")%></td>
                                    <td><%= rs.getString("Cognome")%></td>
                                    <td><%= rs.getString("Email")%></td>
                                    <td><input type="button" class="btn btn-warning" value="gestisci" id="<%=rs.getInt("ID_Docente")%>" data-toggle="modal" data-target="#modificaDocenti" onClick="getId(this,'<%=rs.getString("ID_Docente")%>','<%=rs.getString("Nome")%>','<%=rs.getString("Cognome")%>','<%=rs.getString("Email")%>','<%=corsi%>')" ></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
        </form>
        <form class="login100-form validate-form" action="<%=request.getContextPath()%>/Controller" method="post" style="width: 100%">
                 <!-- Modal -->
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
                                    <td><input type="text" id="idDocente" name="idDocente"/></td>
                                </tr>
                                <tr>
                                    <td>Nome: </td>
                                    <td><input type="text" id="nome" name="nome"/></td>
                                </tr>
                                <tr>
                                    <td>Cognome: </td>
                                    <td><input type="text" id="cognome" name="cognome"/></td>
                                </tr>
                                <tr>
                                    <td>Email: </td>
                                    <td><input type="text" id="email" name="email"/></td>
                                </tr>
                                <tr>
                                    <td>Corso: </td>
                                    <td>
                                        <table>
                                        <% 
                                            sql = "SELECT * FROM Corso";
                                            rs = Model.eseguiQuery(sql);
                                            int i = 0;
                                            while(rs.next()){
                                            %>
                                            <tr>
                                                <td>
                                                    <input type="checkbox" class="corsi" name="corsi" id="<%= rs.getString("titolo")%>" value="<%= rs.getString("titolo")%>" /><%= rs.getString("titolo")%>
                                                </td>
                                                <%  i = 0;
                                                    while(i<2 && rs.next()){%>
                                                <td>
                                                    <input type="checkbox" class="corsi" name="corsi" id="<%= rs.getString("titolo")%>" value="<%= rs.getString("titolo")%>"/><%= rs.getString("titolo")%>
                                                </td>
                                                <% i++;
                                                }%>
                                            </tr>
                                                
                                        <%}%>
                                        </table>
                                    </td>
                                </tr>
                          </table>
                      </div>
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
       
      function getId(btn, idDocente, nome, cognome, email, corso){
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
          var corsi = corso.split(";");
            $('.corsi').attr('checked', false);
          //alert(corsi);
          document.getElementsByName("corsi").checked = false;
          for (var i = 0; i < corsi.length-1; i++) {
            document.getElementById(corsi[i]).checked = true;
          }   
          
        var checked = $(this).prop('checked');
          $('#checkboxes').find('input:checkbox').prop('checked', checked);
    
        for(var i =0; i< checks.length;i++){
            var check = checks[i];
            if(!check.disabled){
                check.checked = false;
            }
        }
            
      }
      function btnVisible(btn){
          document.getElementById("idDocente").value = "";
          document.getElementById("nome").value = "";
          document.getElementById("cognome").value = "";
          document.getElementById("email").value = "";
          
          document.getElementById("elimina").style.display = "none";
          document.getElementById("salva").style.display = "none";
          document.getElementById("modificaDocentiTitle").style.display = "none";
          document.getElementById("aggiungi").style.display = "block";
          document.getElementById("aggiungiDocentiTitle").style.display = "block";
      }
  </script>
    </body>
</html>
