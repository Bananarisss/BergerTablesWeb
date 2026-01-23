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
 *
 * @author Domi
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

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<MatchEntity> matches = new ArrayList<>();
    
    
    public void addMatch(MatchEntity match) {
        matches.add(match);
        match.setTournament(this);
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MatchEntity> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchEntity> matches) {
        this.matches = matches;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return "pl.polsl.bergertablesweb.entities.TournamentEntity[ id=" + id + " ]";
    }
    
}
