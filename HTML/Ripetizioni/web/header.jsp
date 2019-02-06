<% HttpSession ses = request.getSession(); %>
<nav style="background-color: #475369" class="navbar navbar-expand-lg navbar-light">
  <a class="navbar-brand" href="<%= request.getContextPath()%>/index.jsp">GEERAFFA</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div style="background-color: #475369" class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="<%= request.getContextPath()%>/index.jsp">Home <span class="sr-only">(current)</span></a>
      </li>

    <% if("Admin".equals(ses.getAttribute("ruolo")) && ses.getAttribute("logged") == "Y") { %>
        <li class="nav-item">
            <a class="nav-link" href="<%= request.getContextPath()%>/tab_docenti.jsp">GESTISCI</a>
        </li>
    <% } %>
       
    <% if(ses.getAttribute("logged") == "Y") {%>
        <li class="nav-item">
            <a class="nav-link" href="<%= request.getContextPath()%>/Controller?toDo=logout">LOGOUT</a>
        </li>
    <% } %>
        
</nav>