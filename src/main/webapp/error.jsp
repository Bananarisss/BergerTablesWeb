<%-- 
    Document   : error
    Created on : 7 sty 2026, 10:18:07
    Author     : Dominika
    Version    : 1.0
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>
    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null && !errorMessage.trim().isEmpty()) {
            out.println("<div class='error-message'>" + errorMessage + "</div>");
        } else {
            out.println("<div class='error-message'>An unknown error occurred.</div>");
        }
    %>
    <a href="index.jsp">← Back to teams names</a>
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
