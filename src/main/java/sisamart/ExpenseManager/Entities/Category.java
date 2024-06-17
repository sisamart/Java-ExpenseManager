package sisamart.ExpenseManager.Entities;

import java.util.EnumSet;
import java.util.Set;

/**
 * Enum that represents Category of a transaction
 *
 * @author Silvia Martinkovičová
 */
public enum Category {
    FOOD(TransactionType.EXPENSE),
    HOUSEHOLD(TransactionType.EXPENSE),
    ENTERTAINMENT(TransactionType.EXPENSE),
    HEALTH(TransactionType.EXPENSE),

    WORK(TransactionType.INCOME),
    ROYALTIES(TransactionType.INCOME);

    private static final EnumSet<Category> incomeCategories = EnumSet.of(WORK, ROYALTIES);
    private static final EnumSet<Category> expenseCategories = EnumSet.of(FOOD, HOUSEHOLD, ENTERTAINMENT, HEALTH);

    public final TransactionType type;

    Category(TransactionType type) {
        this.type = type;
    }

    public static Set<Category> getPossibleCategories(TransactionType type) {
        return switch (type) {
            case INCOME -> incomeCategories;
            case EXPENSE -> expenseCategories;
        };
    }

}
