<%-- 
    Document   : history
    Created on : 7 sty 2026, 10:18:19
    Author     : Dominika
    Version    : 1.0
--%>
<%@ page import="java.util.List" %>
<%@ page import="pl.polsl.bergertablesweb.model.MatchPair" %>
<%@ page import="pl.polsl.bergertablesweb.entities.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<TournamentEntity> tournaments = (List<TournamentEntity>) request.getAttribute("tournaments");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Wyniki z bazy</title>
    </head>
    <body>
        <h1>Turnieje w bazie</h1>
<c:if test="${empty tournaments}">
    <p>Brak zapisanych turniejów.</p>
</c:if>
<table border="1">
    <tr><th>ID</th><th>Lista drużyn</th><th>Liczba meczów</th></tr>
    <%
      if (tournaments != null) {
          for (TournamentEntity t : tournaments) {
    %>
    <tr>
        <td><%= t.getId() %></td>
        <td><%= t.getTeamNames() %></td>
        <td><%= t.getMatches() != null ? t.getMatches().size() : 0 %></td>
    </tr>
    <%
          }
      }
    %>
</table>
    </body>
</html>
