/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.bergertablesweb.servlet;

import jakarta.servlet.RequestDispatcher;
import pl.polsl.bergertablesweb.model.*;
import pl.polsl.bergertablesweb.model.BergerTablesHistory.HistoryEntry;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;

/**
 * The HistoryServlet class is responsible for handling HTTP request related to
 * history of matches.
 * <p>
 * It retrieves and displays the given teams names an the matches created for
 * them.</p>
 *
 * @author Dominika
 * @version 1.0
 */
@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request The HttpServletRequest object that contains the request
     * the client made of the servlet.
     * @param response The HttpServletResponse object that contains the response
     * the servlet returns to the client.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        BergerTablesHistory history
                = (BergerTablesHistory) getServletContext().getAttribute("history");
        if (history == null) {
            history = new BergerTablesHistory();
            getServletContext().setAttribute("history", history);
        }

        // Retrieve calculation history and set it as a request attribute
        List<HistoryEntry> historyList = history.getHistory();
        request.setAttribute("historyList", historyList);

        // Forward the request to the history.jsp page
        RequestDispatcher dispatcher = request.getRequestDispatcher("history.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> requests.
     * <p>
     * By forwarding GET requests to processRequest method it allows the servlet
     * to handle both GET and POST requests in the same manner</p>
     *
     * @param request The HttpServletRequest object that contains the request
     * the client made of the servlet.
     * @param response The HttpServletResponse object that contains the response
     * the servlet returns to the client.
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
     * @param request The HttpServletRequest object that contains the request
     * the client made of the servlet.
     * @param response The HttpServletResponse object that contains the response
     * the servlet returns to the client.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
