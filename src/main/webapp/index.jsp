<%-- 
    Document   : index
    Created on : 9 sty 2026, 13:17:24
    Author     : Dominika
    Version    : 2.0
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="pl.polsl.bergertablesweb.model.BergerTablesHistory" %>
<%@ page import="pl.polsl.bergertablesweb.model.BergerTablesHistory.HistoryEntry" %>
<!DOCTYPE html>
<html>
<head>
    <title>Berger Tables Generator</title>
    <meta charset="UTF-8">
    <style>
        body { 
            font-family: sans-serif; 
            padding: 20px;
            text-align: center;
        }
        textarea { 
            width: 80%; 
            height: 150px; 
            padding: 10px; 
            border-radius: 2px;
            border: 1px solid #213448;
        }
        .container { 
            width: 50%;
            margin: 32px auto; 
            padding: 50px 20px 30px 20px;
            border-radius: 2px;
            background: white;
            background-color: #94B4C1;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border: 1px solid #213448;
        }
        button{ 
            background-color: #213448;
            color: white;
            padding: 10px 20px; 
            font-size: 16px; 
            margin-top: 25px; 
            cursor: pointer;
            border-radius: 2px;
            border: none;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        button:hover{
            background-color: #547792;
            color: #f4f4f4;
        }
        .hint { 
            font-size: 16px; 
            color: #333; 
            margin-bottom: 5px; 
        }
    </style>
</head>
<body>
    <h1 style="color: #1a2938;">Berger Table Generator</h1>
    <p style="color: #1a2938;">Enter team names (one per line):</p>
    <% String lastTeamsNames = "";
    String numberOfLastTeams = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("lastTeamCount".equals(c.getName())) {
                numberOfLastTeams = c.getValue();
            }
            if (c.getName().equals("lastTeams")) {
                try {
                    lastTeamsNames = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    lastTeamsNames = "";
                }
            }
        }
    }
    %>
    <div class="container">
        <form action="BergerTablesServlet" method="get">
            <% if (!lastTeamsNames.isEmpty()) { %>
            <div class="hint">Previous names of <%=numberOfLastTeams %> teams have been inserted.</div>
            <% } %>
            <textarea id="teamsNames" name="teamsNames" placeholder="Team A&#10;Team B&#10;Team C"><%=lastTeamsNames %></textarea>
            <br>
            <button type="submit">Generate Table</button>
        </form>
        <form action="HistoryServlet" method="get">
            <button type="submit">Show History</button>
        </form>
        <form action="DatabaseServlet" method="get">
            <button type="submit">Show Database</button>
        </form>
    </div>

</body>
</html>
