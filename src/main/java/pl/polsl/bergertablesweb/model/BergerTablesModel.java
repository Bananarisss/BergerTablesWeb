/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.bergertablesweb.model;

import pl.polsl.bergertablesweb.exceptions.InvalidNumberException;
//import pl.polsl.bergertables.annotations.MinLength;
import java.util.List;
import java.util.ArrayList;
import lombok.*;

/**
 * Represents a base model for managing tournament team data.
 * <p>
 * This class holds information about the teams and provides methods for setting
 * and retrieving information about the teams. It also checks if the number of
 * teams is correct (minimum two teams).
 * </p>
 *
 * @author Dominika
 * @version 1.2
 */
@Getter
@NoArgsConstructor
abstract class TeamModel {

    /**
     * The number of teams participating in the tournament.
     */
    @Setter
    private int numberOfTeams = 0;
    /**
     * A list containing the names of all teams.
     */
    //@MinLength(value = 2, message = "There must be at least 2 teams",
    //    necessity = MinLength.Necessity.HIGH)
    private List<String> teams;

    /**
     * This method sets the list of team names and the number of teams.
     *
     * @param names List of teams names.
     * @throws InvalidNumberException if the list is empty or there are less
     * than 2 teams.
     */
    public void teamNames(List<String> names) throws InvalidNumberException {
        if (names.isEmpty()) {
            throw new InvalidNumberException("The list of teams can't be empty!");
        }
        if (names.size() < 2) {
            throw new InvalidNumberException("There has to be at least 2 teams!");
        }
        this.teams = names;
        this.numberOfTeams = names.size();
    }
}

/**
 * Provides methods to create a berger table of a list of teams.
 * <p>
 * This class contains methods to create a berger table from a provided list of
 * Strings containing names of the teams
 * </p>
 *
 * @author Dominika Zakrzewska
 * @version 2.0
 */
@NoArgsConstructor
@Setter
public class BergerTablesModel extends TeamModel {

    @Getter
    //@MinLength(value = 1, message = "There should be at least 1 round")
    private int numberOfRounds;
    //@MinLength(value = 1, message = "There should be at least 1 round")
    private int numberOfMatches;

    /**
     * Rotates the list of teams
     * <p>
     * This method rotates the list of teams by moving the last element of the
     * list to the front.
     * </p>
     *
     * @param teams List of team names.
     */
    private void rotate(List<String> teams) {
        String lastTeam = teams.remove(teams.size() - 1);
        teams.add(1, lastTeam);
    }

    /**
     * Creates the berger table for the given list of teams.
     * <p>
     * The method implements a round-robin tournament algorithm, also known as
     * the Berger algorithm. Each team plays against every other team exactly
     * once. If the number of teams is odd, a virtual "PAUSE" team is added,
     * meaning that a team assigned to "PAUSE" has a free round.
     * </p>
     *
     * @return List of Strings representing the created berger table.
     */
    public List<MatchPair> generateTable() {
        List<MatchPair> games = new ArrayList<>();
        List<String> teams = getTeams();
        int numberOfTeams = getNumberOfTeams();

        boolean odd = (numberOfTeams % 2 != 0);
        String team1;
        String team2;

        if (odd) {
            numberOfTeams++;
            teams.add("PAUSE");
        }

        numberOfRounds = numberOfTeams - 1;
        numberOfMatches = numberOfTeams / 2;

        for (int r = 0; r < numberOfRounds; r++) {
            for (int m = 0; m < numberOfMatches; m++) {
                team1 = teams.get(m);
                team2 = teams.get(numberOfTeams - m - 1);
                if (!team1.equals("PAUSE") && !team2.equals("PAUSE")) {
                    games.add(new MatchPair(team1, team2));
                }
            }
            rotate(teams);
        }

        return games;
    }

    /**
     * This method retrives the number of matches.
     * <p>
     * Depending on the number of teams it retrives the number of matches.
     * </p>
     *
     * @return Number of matches.
     */
    public int getNumberOfMatches() {
        if (getNumberOfTeams() % 2 == 0) {
            return numberOfMatches + 1;
        } else {
            return numberOfMatches;
        }
    }
}
