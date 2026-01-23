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
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.polsl.bergertablesweb.entities.*;
import pl.polsl.bergertablesweb.model.*;

/**
 *
 * @author Domi
 */
@WebServlet(name = "DatabaseServlet", urlPatterns = {"/DatabaseServlet"})
public class DatabaseServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(DatabaseServlet.class.getName());
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_persistence_unit");
    
    public List<TournamentEntity> getAllTournaments() {
        List<TournamentEntity> tournamentList = null;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("SELECT t FROM TournamentEntity t", TournamentEntity.class);
            tournamentList = query.getResultList();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error reading tournaments from DB", e);
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

    public void saveTournament(List<String> teams, List<MatchPair> matches) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            TournamentEntity tournament = new TournamentEntity();
            tournament.setTeamNames(String.join(", ", teams));

        // Konwersja z rekordu MatchPair na Encję MatchEntity
            for (MatchPair pair : matches) {
                MatchEntity matchEntity = new MatchEntity(pair.team1(), pair.team2());
                tournament.addMatch(matchEntity);
            }

            em.persist(tournament); // Zapisuje Turniej i kaskadowo wszystkie Mecze
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error saving tournament", e);
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    void persistObject(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error persisting object", e);
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
            logger.log(Level.SEVERE, "Database error in doGet", ex);
            request.setAttribute("errorMessage", "Błąd podczas pobierania danych z bazy: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unexpected error in doGet", ex);
            request.setAttribute("errorMessage", "Wystąpił błąd: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    } 
}
