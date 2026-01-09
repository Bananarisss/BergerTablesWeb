/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package pl.polsl.bergertablesweb.model;

/**
 * Record that represents a single match between two teams.
 *
 * @param team1 name of the first team.
 * @param team2 name of the second team.
 * @author Dominika
 */
public record MatchPair(String team1, String team2) {

}
