import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class BankStatementCSVParserTest {
    private final BankStatementParser statementParser = new BankStatementCSVParser();

    private void compareTransactions(BankTransaction expected, BankTransaction actual) {
        final double tolerance = 0.0d;
        Assert.assertEquals(expected.getDate(), actual.getDate());
        Assert.assertEquals(expected.getAmount(), actual.getAmount(), tolerance);
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    public void shouldParseOneCorrectLine() {
        final String line = "30-01-2017,-50,Tesco";

        final BankTransaction result = statementParser.parseFrom(line);
        final  BankTransaction expected = new BankTransaction(
                LocalDate.of(2017, Month.JANUARY, 30),
                -50, "Tesco"
        );

        compareTransactions(expected,result);
    }

    @Test
    public void shouldParseMultipleCorrectLine() {
        final List<String> lines = List.of(
                "30-01-2017,-100,Deliveroo",
                "30-01-2017,-50,Tesco"
        );

        final List<BankTransaction> result = statementParser.parseLinesFrom(lines);
        final List<BankTransaction> expected = List.of(
                new BankTransaction(
                        LocalDate.of(2017, Month.JANUARY, 30),
                        -100, "Deliveroo"
                ),
                new BankTransaction(
                        LocalDate.of(2017, Month.JANUARY, 30),
                        -50, "Tesco"
                )
        );

        for (int i = 0; i < expected.size(); i++) {
            compareTransactions(expected.get(i),  result.get(i));
        }
    }
}
