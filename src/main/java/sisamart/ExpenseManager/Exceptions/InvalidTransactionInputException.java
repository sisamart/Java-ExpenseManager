package sisamart.ExpenseManager.Exceptions;

/**
 * Custom checked exception for fails in user input during transaction creation
 *
 * @author Silvia Martinkovičová
 */
public class InvalidTransactionInputException extends Exception {

    public InvalidTransactionInputException() {
    }

    public InvalidTransactionInputException(String message) {
        super(message);
    }

    public InvalidTransactionInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTransactionInputException(Throwable cause) {
        super(cause);
    }
}
