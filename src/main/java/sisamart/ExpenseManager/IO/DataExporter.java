package sisamart.ExpenseManager.IO;

import sisamart.ExpenseManager.Entities.Transaction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Class responsible for data export
 *
 * @author Silvia Martinkovičová
 */
public class DataExporter {

    /**
     * Creates a file and writes into it a header and all transactions, each on a separate line. The separator is ;
     *
     * @param transactions : set of all current transactions
     * @param path         : absolute path to the export file
     */
    public static void exportData(Set<Transaction> transactions, String path) {

        File file = new File(path);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Date;Amount;Type;Category;Description");
            writer.write(System.lineSeparator());

            for (Transaction t : transactions) {
                writer.write(String.format("%s;%.2f;%s;%s;%s%n", t.date(), t.amount(), t.type(), t.category(), t.description()));
            }
        } catch (IOException e) {
            OutputWriter.printErrorMessage("invalid file!");
        }

        OutputWriter.printSuccessMessage("data have been exported to " + path);
    }
}
