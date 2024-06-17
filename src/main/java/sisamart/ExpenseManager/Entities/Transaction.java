package sisamart.ExpenseManager.Entities;

import java.util.Objects;

/**
 * Record that represents one transaction
 *
 * @author Silvia Martinkovičová
 */
public record Transaction(TransactionType type, String date, double amount, String description, Category category,
                          int id) {

    private static int transactionCount = 0;

    public static int getAndIncrementID() {
        int rv = transactionCount;
        transactionCount++;
        return rv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
