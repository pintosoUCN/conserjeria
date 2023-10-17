package cl.ucn.disc.as.exceptions;

/**
 * Domain violations exceptions
 */
public class IllegalDomainException extends RuntimeException {
    /**
     * The constructor
     *
     * @param message to use
     */
    public IllegalDomainException(String message) {
        super(message);
    }
}
