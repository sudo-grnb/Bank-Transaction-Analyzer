import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BankStatementAnalyzer {
    private static final String RESOURCES = "src/main/resources/";

    public void analyze(final String fileName, final BankStatementParser bankStatementParser) throws IOException {
        final Path path = Paths.get(RESOURCES + fileName);
        final List<String> lines = Files.readAllLines(path);

        final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);
        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);

        collectSummary(bankStatementProcessor);
        printHistogram(bankStatementProcessor);
    }

    private void collectSummary(BankStatementProcessor bankStatementProcessor) {
        System.out.println("The total for all transactions is " + bankStatementProcessor.calculateTotalAmount());
        System.out.println("Transactions in January " + bankStatementProcessor.selectInMonth(Month.JANUARY));
        System.out.println("The total for all transactions in February is "
                + bankStatementProcessor.calculateTotalInMonth(Month.FEBRUARY));
        System.out.println("The total salary received is " + bankStatementProcessor.calculateTotalForCategory("Salary"));
        System.out.println("The transaction with the highest cost in February is "
                + bankStatementProcessor.selectHighestCostInMonth(Month.FEBRUARY));
        System.out.println("The transaction with the lowest cost in February is "
                + bankStatementProcessor.selectLowestCostInMonth(Month.FEBRUARY));
    }

    private void printHistogram(BankStatementProcessor bankStatementProcessor) {
        final Map<Month, Double> groupedCostsByMonths = bankStatementProcessor.groupCostsByMonths();
        final int scale = 50;
        System.out.println();
        groupedCostsByMonths.forEach((month, cost) -> {
            System.out.print(month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + "|");
            System.out.print("*".repeat((int) Math.abs(cost) / scale) + cost + "\n");
        });
    }
}
