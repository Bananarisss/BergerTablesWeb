<%-- 
    Document   : history
    Created on : 7 sty 2026, 10:18:19
    Author     : Dominika
    Version    : 1.0
--%>
<%@ page import="pl.polsl.bergertablesweb.model.BergerTablesHistory.HistoryEntry" %>
<%@ page import="java.util.List" %>
<%@ page import="pl.polsl.bergertablesweb.model.MatchPair" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>History</title>
    </head>
    <body>
        <h1>History</h1>
        <%
            List<HistoryEntry> historyList = (List<HistoryEntry>) request.getAttribute("historyList");
            
            if (historyList != null && !historyList.isEmpty()) {
        %>
            <ol>
                <% for (HistoryEntry entry : historyList) { %>
                    <li>
                        <strong>Teams:</strong> <%= entry.getNames() %> <br>
                        <strong>Matches:</strong> 
                        <% 
                           // Wyświetlanie meczów w czytelniejszy sposób
                           for(MatchPair match : entry.getMatches()) {
                               out.print("[" + match.team1() + " vs " + match.team2() + "] ");
                           }
                        %>
                    </li>
                <% } %>
            </ol>
        <% } else { %>
            <p>No history available.</p>
        <% } %>
        
        <br><br>
        <a href="index.jsp">← Back to team names</a>
    </body>
    <style>
    body { 
        font-family: sans-serif; 
        padding: 20px;
        text-align: center;
    }
    .errorMessage {
        color: #D8000C;
        background-color: #FFD2D2;
        border: 1px solid #D8000C;
        margin: 20px 0;
        padding: 10px;
        border-radius: 2px;
    }
    a {
        display: inline-block;
        margin-top: 20px;
        text-decoration: none;
        font-weight: bold;
        color: white;
        padding: 8px 16px;
        border: 1px solid #0056b3;
        border-radius: 2px;
        background-color: #213448;
    }
    a:hover {
        background-color: #547792;
        color: #f4f4f4;
    }
</style>
</html>
