package sisamart.ExpenseManager.IO;

import sisamart.ExpenseManager.Entities.Category;
import sisamart.ExpenseManager.Entities.Transaction;
import sisamart.ExpenseManager.Entities.TransactionType;
import sisamart.ExpenseManager.Exceptions.InvalidTransactionInputException;
import sisamart.ExpenseManager.TransactionCreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Class responsible for data import
 *
 * @author Silvia Martinkovičová
 */
public class DataImporter {

    /**
     * Method that reads from file and calls helper methods to load transactions from it
     *
     * @param transactions : set of all current transactions
     * @param path         : absolute path to the file we want to import from
     * @param hasHeader    : flag that says, if the file has a header
     */
    public static void importData(Set<Transaction> transactions, String path, boolean hasHeader) {

        File file = new File(path);
        Set<Transaction> loadedTransactions = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            if (hasHeader) {
                reader.readLine();
            }
            while (reader.ready()) {
                String line = reader.readLine();
                try {
                    String[] parts = lineParser(line);
                    saveTransaction(parts, loadedTransactions);
                } catch (IllegalArgumentException e) {
                    OutputWriter.printErrorMessage(e.getMessage());
                    System.err.println("No transactions have been imported!");
                    return;
                }
            }
        } catch (IOException e) {
            OutputWriter.printErrorMessage("invalid file!");
            return;
        }

        transactions.addAll(loadedTransactions);
        System.out.println();
        OutputWriter.printSuccessMessage("transactions have been imported!");
    }

    /**
     * Parses line by ; character
     *
     * @param line : input line
     * @return array of transaction properties in String form
     */
    private static String[] lineParser(String line) {
        String[] result = line.split(";", -1);
        if (result.length != 5) {
            throw new IllegalArgumentException("[Error]: invalid file format!");
        }
        return result;
    }

    /**
     * Transforms String parts into their valid types and creates a Transaction from them
     *
     * @param parts        : transaction properties in String form
     * @param transactions : list of newly imported transactions
     * @throws IllegalArgumentException if any of the parts if invalid
     */
    private static void saveTransaction(String[] parts, Set<Transaction> transactions) throws IllegalArgumentException {
        Transaction t;

        try {
            String date = TransactionCreator.getDate(parts[0]);
            double amount = TransactionCreator.getAmount(parts[1]);
            TransactionType type = TransactionCreator.getType(parts[2]);
            Category category = TransactionCreator.getCategory(parts[3]);
            String description = parts[4];

            t = TransactionCreator.createTransaction(type, amount, category, date, description);

        } catch (InvalidTransactionInputException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        transactions.add(t);
    }
}
