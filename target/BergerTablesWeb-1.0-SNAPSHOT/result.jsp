<%-- 
    Document   : result
    Created on : 7 sty 2026, 10:13:16
    Author     : Dominika
--%>
<%@ page import="pl.polsl.bergertablesweb.model.MyTableModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Generated Berger Table</title>
    </head>
    <body>
        <h1 style="color: #1a2938;">Berger Table</h1>
        <%
        MyTableModel tabelModel = (MyTableModel) request.getAttribute("generatedTableModel");
        if(tabelModel != null) {
        %>
        <table border="1">
        <thead>
            <tr>
            <% for(String col : tabelModel.getColumnNames()) { %>
                <th><%= col %></th>
            <% } %>
            </tr>
        </thead>
        <tbody>
            <% for(int i=0; i<tabelModel.getNumberOfRows(); i++) { %>
                <tr>
                    <% for(int j=0; j<tabelModel.getNumberOfColumns(); j++) { %>
                        <td><%= tabelModel.getValueAt(i, j) %></td>
                    <% } %>
                </tr>
            <% } %>
        </tbody>
    </table>
    <% } 
else if (tabelModel == null) {%>
<p>Tablica pusta</p>
<% } %>
    <br>
        <a href="index.html">← Change teams names</a>
        <a href="HistoryServlet">History</a>
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
