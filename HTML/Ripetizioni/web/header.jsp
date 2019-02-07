<% HttpSession ses = request.getSession(); %>
<nav style="background-color: #444F64" class="navbar navbar-expand-lg navbar-light">
    <a class="navbar-brand" href="<%= request.getContextPath()%>/index.jsp">GEERAFFA</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div style="background-color: #444F64; " class="collapse navbar-collapse" id="navbarSupportedContent" >
      <ul class="navbar-nav mr-auto" style="width:100%; background-color: #444F64">
        <li class="nav-item active">
            <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/index.jsp">Home <span class="sr-only">(current)</span></a>
        </li>

        <% if("Admin".equals(ses.getAttribute("ruolo")) && ses.getAttribute("logged") == "Y") { %>
            <li class="nav-item">
                <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/tab_docenti.jsp">GESTISCI</a>
            </li>
        <% } %>
       
        <% if(ses.getAttribute("logged") == "Y") {%>
            <%  String uri = request.getRequestURI();
                System.out.println("URL: " + uri);
                if(uri.compareTo("/Ripetizioni/prenotazione.jsp") == 0){
            %>
                <li class="nav-item" style="width: 100%">
                    <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=elencoPren&corso=tutti">LE MIE PRENOTAZIONI</a>
                </li>
                <%}else if(uri.compareTo("/Ripetizioni/elencoPren.jsp") == 0){
            %>
                <li class="nav-item" style="width: 100%">
                    <a style="color:#be9e21;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=prenota_a">NUOVA PRENOTAZIONE</a>
                </li>
            <%}%>
            <li class="nav-item" style="width: 100%">
                <a style="color:#be9e21; float: right;" class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=logout">LOGOUT</a>
            </li>
        <% } %>
        
      </ul>  
    </div>
</nav>