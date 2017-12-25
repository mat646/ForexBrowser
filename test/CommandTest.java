import com.oop.browser.subcommands.*;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    private static final Calendar calendar1 = Calendar.getInstance();
    private static final Calendar calendar2 = Calendar.getInstance();
    static {
        calendar1.set(2017, Calendar.SEPTEMBER, 12);
        calendar2.set(2017, Calendar.OCTOBER, 14);
    }

    @Test
    void CurrencyCommandTest() {
        CurrencyCommand mockCommand = new CurrencyCommand();
        mockCommand.setDate(calendar1.getTime());
        mockCommand.setSymbol("USD");
        mockCommand.run();

        String result = outContent.toString().split("\n")[1];
        assertEquals("3.5552", result);
    }

    @Test
    void GoldCommandTest() {
        GoldCommand mockCommand = new GoldCommand();
        mockCommand.setDate(calendar1.getTime());
        mockCommand.run();

        String result = outContent.toString().split("\n")[1];
        assertEquals("151.41", result);
    }

    @Test
    void CurrencyAndGoldCommandTest() {
        CurrencyAndGoldCommand mockCommand = new CurrencyAndGoldCommand();
        mockCommand.setDate(calendar1.getTime());
        mockCommand.setSymbol("USD");
        mockCommand.run();

        String result = outContent.toString().split("\n")[1];
        assertEquals("3.5552", result);
    }

    @Test
    void GoldAvgCommandTest() {
        GoldAvgCommand mockCommand = new GoldAvgCommand();
        mockCommand.setStartDate(calendar1.getTime());
        mockCommand.setEndDate(calendar2.getTime());
        mockCommand.run();

        String result = outContent.toString().split("\n")[1];
        assertEquals("150,68", result);
    }

    @Test
    void MaxFluctuationsCommandTest() {
        MaxFluctuationsCommand mockCommand = new MaxFluctuationsCommand();
        mockCommand.setDate(calendar1.getTime());
        mockCommand.run();

        String result = outContent.toString();
        assertEquals("NZD since 2017-09-12\n", result);
    }

    @Test
    void MinExchangeRateCommandTest() {
        MinExchangeRateCommand mockCommand = new MinExchangeRateCommand();
        mockCommand.setDate(calendar1.getTime());
        mockCommand.run();

        String result = outContent.toString();
        assertEquals("HUF\n", result);
    }

    @Test
    void MinMaxExchangeRateCommandTest() {
        MinMaxExchangeRateCommand mockCommand = new MinMaxExchangeRateCommand();
        mockCommand.setSymbol("USD");
        mockCommand.run();

        String result = outContent.toString().split("\n")[1];
        assertEquals("Minimal rate on: 2014-05-08", result);
    }

    @Test
    void SortedSpreadCommandTest() {
        SortedSpreadCommand mockCommand = new SortedSpreadCommand();
        mockCommand.setDate(calendar1.getTime());
        mockCommand.setN(5);
        mockCommand.run();

        String result = outContent.toString().split("\n")[1];
        assertEquals("GBP with spread 0.094", result);
    }

    @AfterEach
    void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}
