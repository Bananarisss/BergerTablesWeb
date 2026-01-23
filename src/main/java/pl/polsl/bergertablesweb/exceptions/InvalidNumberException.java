/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.bergertablesweb.exceptions;

/**
 * Custom exception thrown when a number does not fulfill the required
 * conditions.
 * <p>
 * This exception is typically thrown to indicate that an input number is
 * invalid, such as being negative when only positive numbers are expected, or
 * smaller than the required number.
 * </p>
 *
 * @author Dominika
 * @version 1.0
 */
public class InvalidNumberException extends Exception {

    /**
     * Constructs a new InvalidNumberException with the specified detail
     * message.
     * <p>
     * This constructor is used to provide an error message that can be
     * retrieved and displayed to the user or utilized for debugging purposes.
     * </p>
     *
     * @param message the detailed message that explains the reason for the
     * exception.
     */
    public InvalidNumberException(String message) {
        super(message);
    }
}
