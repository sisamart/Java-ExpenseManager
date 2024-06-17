package sisamart.ExpenseManager;

import sisamart.ExpenseManager.Entities.Category;
import sisamart.ExpenseManager.Entities.Transaction;
import sisamart.ExpenseManager.Entities.TransactionType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class that calculates statistics based on the given data
 *
 * @author Silvia Martinkovičová
 */
public class StatisticsService {

    /**
     * Calculates the total sum of given transactions
     *
     * @param transactions : set of transactions to work with
     * @return the sum of all transactions
     */
    public static double getTotal(Set<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::amount)
                .sum();
    }

    /**
     * The same as getTotal but only for given TransactionType
     *
     * @param transactions : set of transactions to work with
     * @param type         : type of transactions to work with (INCOME/EXPENSE)
     * @return the sum of all transactions of given type
     */
    public static double getTotalOfType(Set<Transaction> transactions, TransactionType type) {
        SortedSet<Transaction> filteredSet = transactions.stream()
                .filter(t -> t.type().equals(type))
                .collect(Collectors.toCollection(() -> new TreeSet<>(new TransactionComparator())));
        return getTotal(filteredSet);
    }

    /**
     * Calculates the average value of a transaction of a given type (INCOME/EXPENSE)help
     *
     * @param transactions : set of transactions we want the average of
     * @param type         : type of transaction we want to work with
     * @return the average value of a transaction
     */
    public static double getAverageOfType(Set<Transaction> transactions, TransactionType type) {
        return transactions.stream()
                .filter(t -> t.type().equals(type))
                .mapToDouble(Transaction::amount)
                .average()
                .orElse(0);
    }

    /**
     * Finds the set of most used categories for the given transaction type
     *
     * @param transactions : set of transactions we want the most popular category of
     * @param type         : type of transaction we want to work with
     * @return set of most popular category (multiple if they have the same amount of occurrences)
     */
    public static Set<Category> getMostPopularCategory(Set<Transaction> transactions, TransactionType type) {
        Map<Category, Long> result = transactions.stream()
                .filter(t -> t.type().equals(type))
                .collect(Collectors.groupingBy(Transaction::category, Collectors.counting()));

        long maxCount = result.values().stream()
                .max(Long::compare)
                .orElse((0L));

        return result.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .takeWhile(e -> e.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * Calculates the maximum transaction
     *
     * @param transactions : set of transactions we want to work with
     * @return the biggest transaction
     */
    public static double biggestTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(Transaction::amount)
                .max()
                .orElse(0);
    }
}
