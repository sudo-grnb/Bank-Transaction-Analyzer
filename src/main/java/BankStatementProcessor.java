import java.time.Month;
import java.util.*;

public class BankStatementProcessor {
    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public List<BankTransaction> selectInMonth(final Month month) {
        final List<BankTransaction> bankTransactionsInMonth = new ArrayList<>();
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() == month) {
                bankTransactionsInMonth.add(bankTransaction);
            }
        }

        return bankTransactionsInMonth;
    }

    public double calculateTotalAmount() {
        double total = 0d;
        for (final BankTransaction bankTransaction : bankTransactions) {
            total += bankTransaction.getAmount();
        }

        return total;
    }

    public double calculateTotalInMonth(final Month month) {
        double total = 0d;
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() == month) {
                total += bankTransaction.getAmount();
            }
        }

        return total;
    }

    public double calculateTotalForCategory(final String category) {
        double total = 0d;
        for (final BankTransaction bankTransaction : bankTransactions) {
            if(bankTransaction.getDescription().equals(category)) {
                total += bankTransaction.getAmount();
            }
        }

        return total;
    }

    public BankTransaction selectHighestCostInMonth(final Month month) {
        BankTransaction currentHighestCost = null;
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() != month || bankTransaction.getAmount() >= 0) continue;

            if (currentHighestCost == null) {
                currentHighestCost = bankTransaction; continue;
            }

            currentHighestCost = currentHighestCost.getAmount() >  bankTransaction.getAmount() ?
                    bankTransaction : currentHighestCost;
        }

        return currentHighestCost;
    }

    public BankTransaction selectLowestCostInMonth(final Month month) {
        BankTransaction currentLowestCost = null;
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() != month || bankTransaction.getAmount() >= 0) continue;

            if (currentLowestCost == null) {
                currentLowestCost = bankTransaction; continue;
            }

            currentLowestCost = currentLowestCost.getAmount() <  bankTransaction.getAmount() ?
                    bankTransaction : currentLowestCost;
        }

        return currentLowestCost;
    }

    public Map<Month, Double> groupCostsByMonths() {
        Map<Month, Double> result = new LinkedHashMap<>();
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getAmount() >= 0) continue;

            if (result.containsKey(bankTransaction.getDate().getMonth())) {
                result.compute(
                        bankTransaction.getDate().getMonth(),
                        (month, amount) -> amount + bankTransaction.getAmount()
                );
            }
            result.putIfAbsent(bankTransaction.getDate().getMonth(), bankTransaction.getAmount());
        }
        return result;
    }
}
