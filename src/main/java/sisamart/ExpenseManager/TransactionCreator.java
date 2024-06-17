package sisamart.ExpenseManager;

import sisamart.ExpenseManager.Entities.Category;
import sisamart.ExpenseManager.Entities.Transaction;
import sisamart.ExpenseManager.Entities.TransactionType;
import sisamart.ExpenseManager.Exceptions.InvalidTransactionInputException;
import sisamart.ExpenseManager.IO.InputReader;
import sisamart.ExpenseManager.IO.OutputWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;

/**
 * @author Silvia Martinkovičová
 */
public class TransactionCreator {

    /**
     * Calls method makeTransaction and adds its result to the other transactions
     *
     * @param transactions : set of all transactions
     * @throws InvalidTransactionInputException if user inputs an invalid option
     */
    public static void loadTransaction(Set<Transaction> transactions) throws InvalidTransactionInputException {
        Transaction transaction = makeTransaction();
        transactions.add(transaction);
    }

    /**
     * Collects user input and if it's valid, creates a new Transaction
     *
     * @return new Transaction that has attributes inputted by the user
     * @throws InvalidTransactionInputException if user inputs invalid option
     */
    private static Transaction makeTransaction() throws InvalidTransactionInputException {
        OutputWriter.inputTypeMessage();
        TransactionType type = getType(InputReader.getInput());
        System.out.println();

        OutputWriter.inputAmountMessage();
        double amount = getAmount(InputReader.getInput());
        System.out.println();

        OutputWriter.inputCategoryMessage(Category.getPossibleCategories(type));
        Category category = getCategory(InputReader.getInput());
        if (!category.type.equals(type)) {
            throw new InvalidTransactionInputException("invalid category!");
        }
        System.out.println();

        OutputWriter.inputDateMessage();
        String date = getDate(InputReader.getInput());
        System.out.println();

        OutputWriter.inputDescriptionMessage();
        String description = InputReader.getInput();
        System.out.println();

        OutputWriter.printSuccessMessage("new transaction has been created!");
        return createTransaction(type, amount, category, date, description);
    }

    public static Transaction createTransaction(TransactionType type, double amount, Category category, String date, String description) {
        int id = Transaction.getAndIncrementID();
        return new Transaction(type, date, amount, description, category, id);
    }

    /**
     * Checks the user input for TransactionType and returns it on success
     *
     * @param input : user input
     * @return TransactionType on success
     * @throws InvalidTransactionInputException if the input is invalid
     */
    public static TransactionType getType(String input) throws InvalidTransactionInputException {
        input = input.toUpperCase(Locale.ROOT);
        TransactionType type;

        try {
            type = TransactionType.valueOf(input);
        } catch (IllegalArgumentException e) {
            throw new InvalidTransactionInputException("invalid transaction type!", e);
        }
        return type;
    }

    /**
     * Checks the user input for Category and returns it on success
     *
     * @param input : user input
     * @return Category on success
     * @throws InvalidTransactionInputException if input is invalid
     */
    public static Category getCategory(String input) throws InvalidTransactionInputException {
        input = input.toUpperCase(Locale.ROOT);
        Category category;

        try {
            category = Category.valueOf(input);
        } catch (IllegalArgumentException e) {
            throw new InvalidTransactionInputException("invalid category!", e);
        }
        return category;
    }

    /**
     * Returns the amount for the transaction on success
     *
     * @param input : user input
     * @return the amount on success
     * @throws InvalidTransactionInputException if input is invalid
     */
    public static double getAmount(String input) throws InvalidTransactionInputException {
        double amount;

        try {
            amount = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new InvalidTransactionInputException("invalid amount!", e);
        }

        if (amount <= 0) {
            throw new InvalidTransactionInputException("invalid amount!", null);
        }
        return amount;
    }

    /**
     * Checks date of a transaction and returns it as a String on success
     *
     * @param input : user input
     * @return the given date as a String
     * @throws InvalidTransactionInputException is input is invalid
     */
    public static String getDate(String input) throws InvalidTransactionInputException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formatter.withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidTransactionInputException("invalid date!", e);
        }

        return input;
    }

}
