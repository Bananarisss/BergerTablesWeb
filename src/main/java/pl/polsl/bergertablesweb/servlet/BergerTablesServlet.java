/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.bergertablesweb.servlet;

import pl.polsl.bergertablesweb.exceptions.InvalidNumberException;
import pl.polsl.bergertablesweb.model.*;
//import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Dominika
 */
public class BergerTablesServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
        BergerTablesModel model =
                (BergerTablesModel) getServletContext().getAttribute("model");
        if (model == null) {
            model = new BergerTablesModel();
            getServletContext().setAttribute("model", model);
        }
        BergerTablesHistory history =
                (BergerTablesHistory) getServletContext().getAttribute("history");
        if (history == null) {
            history = new BergerTablesHistory();
            getServletContext().setAttribute("history", history);
        }

        String teamsNames = request.getParameter("teamsNames");
            if (teamsNames == null || teamsNames.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Please enter some names.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
        try {
            List<String> teams = Arrays.stream(teamsNames.split("\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
            
            model.teamNames(teams);
            List<MatchPair> matches = model.generateTable();
            
            List<String> filteredTeams = Arrays.stream(teamsNames.split("\n"))
                    .map(String::trim)
                    .filter(s -> !s.equals("PAUSE"))
                    .collect(Collectors.toList());
            history.addEntry(filteredTeams, matches);
            
            MyTableModel tableModel = new MyTableModel();
            tableModel.setNumberOfRows(model.getNumberOfRounds());
            tableModel.setNumberOfColumns(model.getNumberOfMatches()); 
            tableModel.fillTable(matches);

            // COOKIE – number of calculations
            String cookieValue = URLEncoder.encode(String.valueOf(teams.size()), StandardCharsets.UTF_8);
            Cookie counter = new Cookie("calculations",
                    String.valueOf(history.getHistory().size()));
            response.addCookie(counter);
            
            request.setAttribute("generatedTableModel", tableModel);
            RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
            dispatcher.forward(request, response);

        } catch (InvalidNumberException e) {
            request.setAttribute("errorMessage", "Invalid input: There must be at least 2 teams.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while generating berger table" + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}


