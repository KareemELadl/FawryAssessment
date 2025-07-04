package exceptions;

/**
 * Exception thrown when trying to checkout with expired products
 */
public class ExpiredProductException extends Exception {
    public ExpiredProductException(String message) {
        super(message);
    }
}
