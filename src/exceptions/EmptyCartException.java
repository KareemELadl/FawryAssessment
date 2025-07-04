package exceptions;

/**
 * Exception thrown when trying to checkout with an empty cart
 */
public class EmptyCartException extends Exception {
    public EmptyCartException(String message) {
        super(message);
    }
}
