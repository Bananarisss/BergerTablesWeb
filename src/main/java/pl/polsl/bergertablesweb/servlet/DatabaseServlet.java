/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.bergertablesweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.persistence.*;
import jakarta.servlet.ServletConfig;
import java.util.List;
import pl.polsl.bergertablesweb.entities.*;
import pl.polsl.bergertablesweb.model.*;

/**
 * The DatabaseServlet class is a servlet responsible for handling database
 * operations.
 *
 * @author Dominika
 * @version 2.0
 */
@WebServlet(name = "DatabaseServlet", urlPatterns = {"/DatabaseServlet"})
public class DatabaseServlet extends HttpServlet {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_persistence_unit");

    /**
     * This method retrives all tournament entities from database.
     *
     * @return A list of all tournaments found in the database.
     * @throws PersistenceException if the database operation fails.
     */
    public List<TournamentEntity> getAllTournaments() {
        List<TournamentEntity> tournamentList = null;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("SELECT t FROM TournamentEntity t", TournamentEntity.class);
            tournamentList = query.getResultList();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return tournamentList;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * This method saves a new tournament and its generated matches to the
     * database.
     * <p>
     * It converts the input lists into TournamentEntity and MatchEntity
     * objects.
     * </p>
     *
     * @param teams List of teams names.
     * @param matches List of generated matches for given teams.
     * @throws PersistenceException if the databese transaction fails.
     */
    public void saveTournament(List<String> teams, List<MatchPair> matches) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            TournamentEntity tournament = new TournamentEntity();
            tournament.setTeamNames(String.join(", ", teams));

            for (MatchPair pair : matches) {
                MatchEntity matchEntity = new MatchEntity(pair.team1(), pair.team2());
                tournament.addMatch(matchEntity);
            }

            em.persist(tournament);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Generic method to persist any object to the database.
     *
     * @param object The entity object to be saved.
     * @throws PersistenceException if the save operation fails.
     */
    void persistObject(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

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
        try {
            List<TournamentEntity> tournaments = getAllTournaments();
            request.setAttribute("tournaments", tournaments);
            // forward do JSP, które wyświetla listę
            request.getRequestDispatcher("/database.jsp").forward(request, response);
        } catch (PersistenceException ex) {
            request.setAttribute("errorMessage", "Błąd podczas pobierania danych z bazy: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Wystąpił błąd: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
