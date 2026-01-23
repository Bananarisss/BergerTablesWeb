/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.bergertablesweb.entities;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 * Represents a single match entity in the database.
 * <p>
 * This class stores information about a match between two teams and is linked
 * to a specific tournament.
 * </p>
 *
 * @author Dominika
 * @version 1.0
 */
@Entity
public class MatchEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String team1;
    private String team2;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

    public MatchEntity() {
    }

    /**
     * Parametrized constructor to create a match with specific teams.
     *
     * @param team1 Name of the first team.
     * @param team2 Name of the second team.
     */
    public MatchEntity(String team1, String team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    /**
     * This method returns the tournament associated with this match.
     *
     * @return the tournament.
     */
    public TournamentEntity getTournament() {
        return tournament;
    }

    /**
     * Set the tournament associated with this match.
     *
     * @param tournament the tournament.
     */
    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }

    /**
     * Get the value of team1
     *
     * @return the value of team1
     */
    public String getTeam1() {
        return team1;
    }

    /**
     * Get the value of team2
     *
     * @return the value of team2
     */
    public String getTeam2() {
        return team2;
    }

    /**
     * Set the value of team2
     *
     * @param team2 new value of team2
     */
    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    /**
     * Set the value of team1
     *
     * @param team1 new value of team1
     */
    public void setTeam1(String team1) {
        this.team1 = team1;

    }

    /**
     * Get the value of the Id
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the value of the Id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Computes the hash code for this object based on its ID.
     *
     * @return An integer hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares this object with another object.
     *
     * @param object the object to compare with.
     * @return true if objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MatchEntity)) {
            return false;
        }
        MatchEntity other = (MatchEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the object.
     *
     * * @return A string containing the class name and ID.
     */
    @Override
    public String toString() {
        return "pl.polsl.bergertablesweb.entities.MatchEntity[ id=" + id + " ]";
    }

}
