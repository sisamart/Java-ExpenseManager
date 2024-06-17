package sisamart.ExpenseManager.IO;

import sisamart.ExpenseManager.Exceptions.InvalidTransactionInputException;

import java.util.Scanner;


/**
 * Class responsible for most of input
 *
 * @author Silvia Martinkovičová
 */
public class InputReader {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static String getInput() {
        return SCANNER.nextLine().trim();
    }

    /**
     * Checks the inputted ID's format and returns it on success
     *
     * @param input : user input
     * @return inputted ID
     * @throws InvalidTransactionInputException if the input has invalid format
     */
    public static int getID(String input) throws InvalidTransactionInputException {
        int id;

        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidTransactionInputException("[Error]: invalid ID format!", e);
        }
        return id;
    }

    /**
     * Checks user's input for header option
     *
     * @return true or false based on the input on success
     * @throws IllegalArgumentException if the option is invalid
     */
    public static boolean getHeader() {
        String input = getInput().toLowerCase();

        return switch (input) {
            case "y" -> true;
            case "n" -> false;
            default -> throw new IllegalArgumentException("invalid option!");
        };
    }
}