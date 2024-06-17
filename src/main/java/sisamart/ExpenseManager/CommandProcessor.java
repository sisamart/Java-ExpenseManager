package sisamart.ExpenseManager;

import sisamart.ExpenseManager.Entities.Transaction;
import sisamart.ExpenseManager.Exceptions.InvalidTransactionInputException;
import sisamart.ExpenseManager.IO.DataExporter;
import sisamart.ExpenseManager.IO.DataImporter;
import sisamart.ExpenseManager.IO.InputReader;
import sisamart.ExpenseManager.IO.OutputWriter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class is responsible for handling user input
 *
 * @author Silvia Martinkovičová
 */
public class CommandProcessor {

    private final Set<Transaction> transactions;
    private boolean isGoing;
    private final String savePath;

    /**
     * Constructor that loads all saved transactions from the saves file
     */
    public CommandProcessor() {
        transactions = new HashSet<>();

        System.out.println("Importing saved data...");
        String currentPath = System.getProperty("user.dir");
        Path directoryPath = Paths.get(currentPath, "data");
        savePath = directoryPath + File.separator + "saves.csv";
        DataImporter.importData(transactions, savePath, true);
        System.out.println();

        isGoing = true;
    }

    /**
     * The loop that takes users input. It runs until exit is called.
     */
    public void process() {

        while (isGoing) {
            System.out.print("> ");
            String command = InputReader.getInput();
            System.out.println();
            resolveCommand(command);
        }
    }

    /**
     * Decides the validity of command and sends it off to the appropriate method or prints error
     *
     * @param command: String command that the user inputted
     */
    private void resolveCommand(String command) {
        switch (command) {
            case "add-transaction":
                try {
                    TransactionCreator.loadTransaction(transactions);
                } catch (InvalidTransactionInputException e) {
                    OutputWriter.printErrorMessage(e.getMessage());
                }
                return;
            case "show-transactions":
                OutputWriter.printTransactions(transactions, false);
                return;
            case "delete-transaction":
                try {
                    deleteTransaction();
                } catch (InvalidTransactionInputException e) {
                    OutputWriter.printErrorMessage(e.getMessage());
                }
                return;
            case "export-data":
                exportData();
                return;
            case "import-data":
                importData();
                return;
            case "statistics":
                OutputWriter.printStats(transactions);
                return;
            case "help":
                OutputWriter.printCommands();
                return;
            case "exit":
                DataExporter.exportData(transactions, savePath);
                isGoing = false;
                return;
            default:
                OutputWriter.printErrorMessage("invalid command!");
        }
    }

    /**
     * Method that handles user's input for data import and starts the import method in DataImporter class
     */
    private void importData() {
        OutputWriter.inputPathMessage("input file");
        String path = InputReader.getInput();
        System.out.println();
        OutputWriter.inputHasHeaderMessage();
        boolean hasHeader;

        try {
            hasHeader = InputReader.getHeader();
        } catch (IllegalArgumentException e) {
            OutputWriter.printErrorMessage(e.getMessage());
            return;
        }
        DataImporter.importData(transactions, path, hasHeader);
    }

    /**
     * Method that handles user's input for data export and starts the export method in DataExporter class
     */
    private void exportData() {

        OutputWriter.inputPathMessage("export directory");
        String path = InputReader.getInput();
        System.out.println();

        OutputWriter.inputFilenameMessage();
        String filename = InputReader.getInput();
        System.out.println();

        if (filename.isEmpty()) {
            filename = "export";
        }

        String finalPath = path + File.separator + filename + ".csv";

        DataExporter.exportData(transactions, finalPath);
    }

    /**
     * Deletes the transaction based on its ID that was inputted by the user.
     *
     * @throws InvalidTransactionInputException is thrown if the inputted ID is invalid
     */
    private void deleteTransaction() throws InvalidTransactionInputException {
        OutputWriter.printTransactions(transactions, true);
        System.out.println();
        OutputWriter.inputIDMessage();
        int id = InputReader.getID(InputReader.getInput());

        Transaction toDelete = transactions.stream().filter(t -> t.id() == id).findFirst().orElse(null);
        if (toDelete == null) {
            OutputWriter.printErrorMessage("invalid transaction ID. Nothing will be deleted!");
            return;
        }
        transactions.remove(toDelete);
        OutputWriter.printSuccessMessage("Transaction with ID " + toDelete.id() + " has been deleted!");
    }

}
