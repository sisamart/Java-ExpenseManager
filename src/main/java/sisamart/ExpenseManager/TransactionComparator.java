package sisamart.ExpenseManager;

import sisamart.ExpenseManager.Entities.Transaction;

import java.util.Comparator;

/**
 * Comparator that compares transactions by their dates
 *
 * @author Silvia Martinkovičová
 */
public class TransactionComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction o1, Transaction o2) {
        return o1.date().compareTo(o2.date());
    }
}
