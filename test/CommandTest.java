import com.oop.browser.subcommands.CurrencyCommand;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeAll
    static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    static Calendar calendar = Calendar.getInstance();
    static {
        calendar.set(2017, Calendar.SEPTEMBER, 12);
    }

    @Test
    void CurrencyCommandTest() {
        CurrencyCommand currencyCommand = new CurrencyCommand();
        currencyCommand.date = calendar.getTime();
        currencyCommand.symbol = "USD";
        currencyCommand.run();

        String result = outContent.toString().split("\n")[1];
        assertEquals("3.5552", result);
    }

    //TODO tests for all commands


    @AfterAll
    static void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}
