package sisamart.ExpenseManager.IO;

import sisamart.ExpenseManager.Entities.Category;
import sisamart.ExpenseManager.Entities.Transaction;
import sisamart.ExpenseManager.Entities.TransactionType;
import sisamart.ExpenseManager.StatisticsService;
import sisamart.ExpenseManager.TransactionComparator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that is responsible for almost all the output to the terminal.
 *
 * @author Silvia Martinkovičová
 */
public class OutputWriter {

    private static final String inputString = " => ";

    public static void inputTypeMessage() {
        System.out.println("Enter the type of transaction (INCOME/EXPENSE):");
        System.out.print(inputString);
    }

    public static void inputCategoryMessage(Set<Category> categories) {
        System.out.println("Available categories: " + categories.toString());
        System.out.println("Enter the category of transaction:");
        System.out.print(inputString);
    }

    public static void inputAmountMessage() {
        System.out.println("Enter the amount (number > 0):");
        System.out.print(inputString);
    }

    public static void inputDateMessage() {
        System.out.println("Enter the date (dd-MM-yyyy):");
        System.out.print(inputString);
    }

    public static void inputDescriptionMessage() {
        System.out.println("Enter the description:");
        System.out.print(inputString);
    }

    public static void inputIDMessage() {
        System.out.println("Enter the ID to delete:");
        System.out.print(inputString);
    }

    public static void inputPathMessage(String entity) {
        System.out.println("Enter the absolute path to the " + entity + ":");
        System.out.print(inputString);
    }

    public static void inputFilenameMessage() {
        System.out.println("Enter the filename (without file format, will be .csv by default):");
        System.out.print(inputString);
    }

    public static void inputHasHeaderMessage() {
        System.out.println("Does the file have header? (Y/N)");
        System.out.print(inputString);
    }

    public static void printStats(Set<Transaction> transactions) {
        System.out.println(String.format("Total amount: %.2f", StatisticsService.getTotal(transactions)));
        System.out.println(String.format("Total income: %.2f", StatisticsService.getTotalOfType(transactions, TransactionType.INCOME)));
        System.out.println(String.format("Total expense: %.2f", StatisticsService.getTotalOfType(transactions, TransactionType.EXPENSE)));
        System.out.println();
        System.out.println(String.format("Average income: %.2f", StatisticsService.getAverageOfType(transactions, TransactionType.INCOME)));
        System.out.println(String.format("Average expense: %.2f", StatisticsService.getAverageOfType(transactions, TransactionType.EXPENSE)));
        System.out.println();
        System.out.println("Most popular income category: " + StatisticsService.getMostPopularCategory(transactions, TransactionType.INCOME));
        System.out.println("Most popular expense category: " + StatisticsService.getMostPopularCategory(transactions, TransactionType.EXPENSE));
    }

    public static void printCommands() {
        String[] names = {
                "add-transaction",
                "delete-transaction",
                "show-transactions",
                "statistics",
                "import-data",
                "export-data",
                "exit"};

        String[] descriptions = {
                "let's you add a new expense/income",
                "let's you delete an existing transaction",
                "shows all saved transactions",
                "calculates some statistics based on your transactions",
                "imports data from a file",
                "exports all current data",
                "ends the application and saves current transactions"};

        for (int i = 0; i < names.length; i++) {
            String msg = String.format("%-18s - %s", names[i], descriptions[i]);
            System.out.println(msg);
        }
        System.out.println();
    }

    public static void printErrorMessage(String msg) {
        System.err.println("[Error]: " + msg);
        System.out.println();
    }

    public static void printSuccessMessage(String msg) {
        System.out.println("[Success]: " + msg);
        System.out.println();
    }

    /**
     * Printing method that shows all available information about user's transactions
     *
     * @param showID: decides whether to show IDs of transactions
     */
    public static void printTransactions(Set<Transaction> transactions, boolean showID) {

        List<Transaction> transactionsList = transactions.stream()
                .sorted(new TransactionComparator())
                .toList();

        if (showID) {
            System.out.print(String.format(" %-5s |", "ID"));
        }

        int amountLen = String.valueOf(StatisticsService.biggestTransaction(transactionsList)).length() + 1;
        String border = String.join("", Collections.nCopies(70, String.valueOf('-')));

        System.out.println(String.format(" %-10s | %-" + (amountLen + 2) + "s | %-7s | %-13s | Description ", "Date", "Amount", "Type", "Category"));
        System.out.println(border);

        for (var t : transactionsList) {
            String msg = String.format(" %s | %-" + amountLen + ".2f $ | %-7s | %-13s | %s", t.date(), t.amount(), t.type(), t.category(), t.description());
            if (showID) {
                System.out.printf(String.format(" %-5d |", t.id()));
            }
            System.out.println(msg);
        }
        System.out.println(border);
        System.out.println();
    }

}
