
package aem.pokego.lure.exceptions;

/**
 * This is meant to be a checked exception thrown when an error
 * occurs persisting or retrieving PokeStops.
 */
public class PokeStopManageException extends Exception {
    
    public PokeStopManageException(String message) {
        super(message);
    }
    
    public PokeStopManageException(String message, Throwable cause) {
        super(message, cause);
    }
}
