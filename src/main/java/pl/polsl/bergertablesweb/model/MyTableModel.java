/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.bergertablesweb.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

/**
 * Represents the table model for the created berger tables.
 * <p>
 * This class represents a custom table model used to represent berger tables.
 * It extends AbstractTableModel and provides necessary methods to interact with
 * a JTable.
 * </p>
 *
 * @author Dominika Zakrzewska
 * @version 1.0
 */
@NoArgsConstructor
public class MyTableModel {

    private Object[][] data = new Object[0][0];
    @Getter
    private List<String> columnNames = new ArrayList<>();
    @Setter
    @Getter
    private int numberOfRows;
    @Setter
    @Getter
    private int numberOfColumns;

    /**
     * Fills the table model with the created berger table.
     * <p>
     * This method computes the number of columns based on the number of match
     * lines and rounds, and places each match string into the correct row and
     * column of the table.
     * </p>
     *
     * @param matches List of strings containing the created berger table.
     */
    public void fillTable(List<MatchPair> matches) {

        columnNames.clear();
        columnNames.add("ROUND");
        for (int i = 1; i < numberOfColumns; i++) {
            columnNames.add("MATCH " + i);
        }

        data = new Object[numberOfRows][numberOfColumns];

        int currentRow = 0;
        int currentCol = 1;

        for (int i = 0; i < numberOfRows; i++) {
            data[i][0] = "ROUND " + (i + 1);
        }

        for (MatchPair match : matches) {
            if (currentRow >= 0 && currentCol < numberOfColumns) {
                data[currentRow][currentCol] = match.team1() + " vs " + match.team2();

                currentCol++;

                if (currentCol >= numberOfColumns) {
                    currentCol = 1;
                    currentRow++;
                }
            }
        }
    }

    /**
     * Returns the value stored in the table at the specified row and column.
     *
     * @param row Index of the requested row.
     * @param col Index of the requested column.
     * @return Value located in the specified row and column of the table.
     */
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

}
