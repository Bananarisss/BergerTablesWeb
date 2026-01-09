/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.bergertablesweb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class to manage the history of berger tables generation.
 * @author Dominika
 */
public class BergerTablesHistory {
    private final List<HistoryEntry> history = new ArrayList<>();
    
    /**
     * Adds a new entry to the history.
     * 
     * @param names list of teams names.
     * @param matches generated matches for given teams.
     */
    public void addEntry(List<String> names, List<MatchPair> matches) {
        history.add(new HistoryEntry(names, matches));
    }

    /**
     * Retrieves the entire history.
     * 
     * @return A copy of the list containing all history entries.
     */
    public List<HistoryEntry> getHistory() {
        return new ArrayList<>(history); // Return a copy to maintain encapsulation
    }
    
    /**
     * Inner class representing a single history entry.
     */
    public class HistoryEntry {
        private final List<String> names;
        private final List<MatchPair> matches;
        
        /**
         * Constructs a new HistoryEntry with the specified teams names and their matches.
         * 
         * @param names list of teams names.
         * @param matches generated matches for given teams.
         */
        public HistoryEntry(List<String> names, List<MatchPair> matches) {
            this.names = names;
            this.matches = matches;
        }
        
        /**
         * Retrieves the list of teams names.
         * 
         * @return The list of teams names.
         */
        public List<String> getNames() {
            return names;
        }

        /**
         * Retrieves the generated matches for given teams.
         * 
         * @return The list of matches.
         */
        public List<MatchPair> getMatches() {
            return matches;
        }
    }
}

