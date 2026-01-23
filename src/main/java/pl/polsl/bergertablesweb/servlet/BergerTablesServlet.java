/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.bergertablesweb.servlet;

import pl.polsl.bergertablesweb.exceptions.InvalidNumberException;
import pl.polsl.bergertablesweb.model.*;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.annotation.WebServlet;

/**
 * The BergerTablesServlet class is a servlet implementation for generating a
 * berger table for given teams names.
 *
 * @author Dominika
 * @version 2.0
 */
@WebServlet("/BergerTablesServlet")
public class BergerTablesServlet extends HttpServlet {

    private DatabaseServlet dbServlet = new DatabaseServlet();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * <p>
     * This method processes the team names given by te user, generates a berger
     * table for them, updates the history, manages cookies, session data and
     * forwards the results to the appropriate JSP.
     * </p>
     *
     * @param request HttpServletRequest object that contains the request the
     * client has made of the servlet.
     * @param response HttpServletResponse object that contains the response the
     * servlet sends to the client.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        BergerTablesModel model
                = (BergerTablesModel) getServletContext().getAttribute("model");
        if (model == null) {
            model = new BergerTablesModel();
            getServletContext().setAttribute("model", model);
        }
//        BergerTablesHistory history
//                = (BergerTablesHistory) getServletContext().getAttribute("history");
//        if (history == null) {
//            history = new BergerTablesHistory();
//            getServletContext().setAttribute("history", history);
//        }

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
            
            dbServlet.saveTournament(teams, matches);
            //history.addEntry(filteredTeams, matches);

            MyTableModel tableModel = new MyTableModel();
            tableModel.setNumberOfRows(model.getNumberOfRounds());
            tableModel.setNumberOfColumns(model.getNumberOfMatches());
            tableModel.fillTable(matches);

            String teamsCookieValue = URLEncoder.encode(teamsNames, StandardCharsets.UTF_8);
            Cookie teamNamesCookie = new Cookie("lastTeams", teamsCookieValue);
            teamNamesCookie.setMaxAge(60);
            response.addCookie(teamNamesCookie);

            String numberCookieValue = URLEncoder.encode(String.valueOf(filteredTeams.size()), StandardCharsets.UTF_8);
            Cookie numberOfLastTeams = new Cookie("lastTeamCount", numberCookieValue);
            numberOfLastTeams.setMaxAge(60);
            response.addCookie(numberOfLastTeams);

            request.setAttribute("generatedTableModel", tableModel);
            RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
            dispatcher.forward(request, response);

        } catch (InvalidNumberException e) {
            request.setAttribute("errorMessage", "Invalid input: There must be at least 2 teams.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while generating berger table: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> requests.
     * <p>
     * By forwarding GET requests to processRequest method it allows the servlet
     * to handle both GET and POST requests in the same manner</p>
     *
     * @param request HttpServletRequest object that contains the request the
     * client has made of the servlet.
     * @param response HttpServletResponse object that contains the response the
     * servlet sends to the client.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> requests.
     * <p>
     * By forwarding POST requests to processRequest method it allows the
     * servlet to handle both GET and POST requests in the same manner</p>
     *
     * @param request HttpServletRequest object that contains the request the
     * client has made of the servlet.
     * @param response HttpServletResponse object that contains the response the
     * servlet sends to the client.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
