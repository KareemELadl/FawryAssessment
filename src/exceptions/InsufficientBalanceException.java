package exceptions;

/**
 * Exception thrown when customer doesn't have sufficient balance for checkout
 */
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
