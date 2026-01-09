/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.bergertablesweb.model;

import pl.polsl.bergertablesweb.model.BergerTablesModel;
import pl.polsl.bergertablesweb.model.MatchPair;
import java.util.List;
import java.util.Arrays;
import pl.polsl.bergertablesweb.exceptions.InvalidNumberException;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test class for the BergerTablesModel class.
 * <p>
 * Contains unit tests that cover standard, edge and invalid cases for the
 * Berger tables generation logic.
 * </p>
 *
 * @author Dominika
 * @version 1.2
 */
public class BergerTablesModelTest {

    private BergerTablesModel model;

    /**
     * Method that creates a new model before each test.
     */
    @BeforeEach
    public void setUp() {
        model = new BergerTablesModel();
    }

    /**
     * Parametrized test that checks if the Berger table is generated correctly.
     * <p>
     * ^ The test checks whether all expected match pairs are present and
     * whether the total number of generated matches is correct.
     * </p>
     *
     * @param names The list of team names from which the berger table is
     * created.
     * @param expectedMatches The expected matches created for the given list of
     * team names.
     * @throws InvalidNumberException if the input validation fails.
     */
    @ParameterizedTest
    @MethodSource("generateTableTestCases")
    public void testGenerateTable(List<String> names, List<MatchPair> expectedMatches) throws InvalidNumberException {
        model.teamNames(new ArrayList<>(names));
        List<MatchPair> result = model.generateTable();
        assertEquals(expectedMatches.size(), result.size());
        assertTrue(result.containsAll(expectedMatches));
    }

    /**
     * Provides a stream of arguments for generateTable method test cases.
     * <p>
     * Each test case consists of a list of team names and the expected list of
     * match pairs that should be generated according to the Berger algorithm.
     * </p>
     *
     * @return Stream of arguments including lists of names and expected created
     * matches.
     */
    private static Stream<Arguments> generateTableTestCases() {
        return Stream.of(
                Arguments.of(new ArrayList<>(Arrays.asList("1", "2", "3", "4")),
                        new ArrayList<>(Arrays.asList(
                                new MatchPair("1", "4"),
                                new MatchPair("2", "3"),
                                new MatchPair("1", "3"),
                                new MatchPair("4", "2"),
                                new MatchPair("1", "2"),
                                new MatchPair("3", "4")))),
                Arguments.of(new ArrayList<>(Arrays.asList("1", "2", "3")),
                        new ArrayList<>(Arrays.asList(
                                new MatchPair("1", "3"),
                                new MatchPair("1", "2"),
                                new MatchPair("2", "3")))),
                Arguments.of(new ArrayList<>(Arrays.asList("A", "B")),
                        new ArrayList<>(Arrays.asList(
                                new MatchPair("A", "B"))))
        );
    }

    /**
     * Parametrized test that checks if an exception is thrown when invalid team
     * name data is provided.
     * <p>
     * The test verifies that the model correctly rejects invalid input, such as
     * an empty list or a list containing only one team.
     * </p>
     *
     * @param names The list of team names expected to cause an exception.
     */
    @ParameterizedTest
    @MethodSource("invalidTeamNamesTestCases")
    public void testTeamNamesThrowsException(List<String> names) {
        assertThrows(InvalidNumberException.class, () -> model.teamNames(names));
    }

    /**
     * Provides a stream of arguments of invalid input data for testing team
     * name validation.
     * <p>
     * All provided test cases represent incorrect input data and should result
     * in an InvalidNumberException.
     * </p>
     *
     * @return Stream of arguments containing invalid team name lists.
     */
    private static Stream<Arguments> invalidTeamNamesTestCases() {
        return Stream.of(
                Arguments.of(new ArrayList<>(Arrays.asList("1"))),
                Arguments.of(new ArrayList<>(Collections.emptyList()))
        );
    }

    /**
     * Parametrized test that checks whether the correct number of matches is
     * calculated after generating the Berger table.
     * <p>
     * The test verifies the correctness of the match count for both even and
     * odd numbers of teams.
     * </p>
     *
     * @param names The list of team names used to generate the Berger table.
     * @param expectedNumberOfMatches The expected number of generated matches.
     * @throws InvalidNumberException if the input validation fails.
     */
    @ParameterizedTest
    @MethodSource("getNumberOfMatchesTestCases")
    public void testGetNumberOfMatches(List<String> names, int expectedNumberOfMatches) throws InvalidNumberException {
        model.teamNames(names);
        model.generateTable();
        assertEquals(expectedNumberOfMatches, model.getNumberOfMatches());
    }

    /**
     * Provides test data for verifying the number of matches generated by the
     * Berger table algorithm.
     * <p>
     * Each test case defines a list of team names and the expected number of
     * matches produced by the model.
     * </p>
     *
     * @return Stream of arguments containing team lists and expected match
     * counts.
     */
    private static Stream<Arguments> getNumberOfMatchesTestCases() {
        return Stream.of(
                Arguments.of(new ArrayList<>(Arrays.asList("1", "2", "3", "4")), 3),
                Arguments.of(new ArrayList<>(Arrays.asList("1", "2", "3")), 2),
                Arguments.of(new ArrayList<>(Arrays.asList("A", "TW", "gh", "43", "000", "56")), 4)
        );
    }
}
