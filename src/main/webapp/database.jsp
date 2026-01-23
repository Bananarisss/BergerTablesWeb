<%-- 
    Document   : history
    Created on : 23 sty 2026, 19:18:19
    Author     : Dominika
    Version    : 1.0
--%>
<%@ page import="java.util.List" %>
<%@ page import="pl.polsl.bergertablesweb.entities.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<TournamentEntity> tournaments = (List<TournamentEntity>) request.getAttribute("tournaments");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Database</title>
    </head>
    <body>
        <h1 style="color: #1a2938;">Database of tournaments</h1>
        <% if(tournaments != null & !tournaments.isEmpty()) { %>
        <table border="1">
            <thead>
                <tr>
                    <th>Tournament</th>
                    <th>Teams</th>
                    <th>Matches</th>
                </tr>
            </thead>
            <tbody>
                <% for(TournamentEntity t: tournaments) { %>
                <tr>
                    <td><%= t.getId() %></td>
                    <td><%= t.getTeamNames() %></td>
                    <td><% 
                            List<MatchEntity> matches = t.getMatches();
                            if (matches != null && !matches.isEmpty()) {
                                for (int i = 0; i < matches.size(); i++) {
                                    MatchEntity m = matches.get(i);
                                    out.print(m.getTeam1() + " vs " + m.getTeam2());
                                    if (i < matches.size() - 1) {
                                        out.print(", ");
                                    }
                                }
                            } else {
                                out.print("No matches");
                            }
                        %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } 
        else if (tournaments == null) { %>
        <p>History is empty</p>
        <% } %>
        <br>
        <a href="index.jsp">← Change teams names</a>
    </body>
    <style>
        body { 
            font-family: sans-serif; 
            padding: 20px; 
            text-align: center;
        }
        table { 
            margin: 20px auto; 
            border-collapse: collapse;
            width: 80%; 
            color: #1a2938;
        }
        th, td { 
            border: 1px solid #213448; 
            padding: 10px; 
            text-align: center; 
        }
        th { 
            background-color: #94B4C1; 
            color: #1a2938; 
        }
        td:first-child { 
            font-weight: bold; 
            background-color: #f9f9f9; 
        } /* Pierwsza kolumna */
        a { 
            display: inline-block; 
            margin-top: 20px; 
            text-decoration: none; 
            color: white;
            font-style: normal;
            font-weight: bold;
            border-radius: 2px;
            background-color: #213448;
            padding: 10px 20px;
        }
        a:hover {
            background-color: #547792;
            color: #f4f4f4;
        }
    </style>
</html>
