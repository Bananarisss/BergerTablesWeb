/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.bergertablesweb.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a tournament entity in the database.
 * <p>
 * This class stores information about the tournament, including the
 * participating teams and the list of matches generated for it.
 * </p>
 *
 * @author Dominika
 * @version 1.0
 */
@Entity
public class TournamentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String teamNames;

    public TournamentEntity() {
    }

    /**
     * Get the value of teamNames
     *
     * @return the value of teamNames
     */
    public String getTeamNames() {
        return teamNames;
    }

    /**
     * Set the value of teamNames
     *
     * @param teamNames new value of teamNames
     */
    public void setTeamNames(String teamNames) {
        this.teamNames = teamNames;
    }

    /**
     * The list of matches associated with this tournament.
     * <p>
     * Defines a One-to-Many relationship with CascadeType.ALL, meaning
     * operations on the tournament cascade to its matches.</p>
     */
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<MatchEntity> matches = new ArrayList<>();

    /**
     * This method adds a match to the tournament.
     * <p>
     * This method ensures the relationship between tournament and matches is
     * set correctly by adding the match to the list and setting the tournament
     * reference in the match.
     * </p>
     *
     * @param match
     */
    public void addMatch(MatchEntity match) {
        matches.add(match);
        match.setTournament(this);
    }

    /**
     * Gets the unique identifier of the tournament.
     *
     * * @return The ID of the tournament.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the tournament.
     *
     * * @param id The new ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method returns the list of matches in this tournament.
     *
     * @return list of matches.
     */
    public List<MatchEntity> getMatches() {
        return matches;
    }

    /**
     * This method sets the list of matches for this tournament.
     *
     * @param matches the list of matches.
     */
    public void setMatches(List<MatchEntity> matches) {
        this.matches = matches;
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
        if (!(object instanceof TournamentEntity)) {
            return false;
        }
        TournamentEntity other = (TournamentEntity) object;
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
        return "pl.polsl.bergertablesweb.entities.TournamentEntity[ id=" + id + " ]";
    }

}
