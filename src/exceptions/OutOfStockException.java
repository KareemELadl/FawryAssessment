package exceptions;

/**
 * Exception thrown when trying to add more items than available in stock
 */
public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}
