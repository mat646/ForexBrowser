import com.oop.browser.subcommands.CurrencyCommand;
import com.oop.browser.subcommands.GoldCommand;
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

    @Test
    void GoldCommandTest() {
        GoldCommand goldCommand = new GoldCommand();
        goldCommand.date = calendar.getTime();
        goldCommand.run();

        String result = outContent.toString().split("\n")[1];
        assertEquals("151.41", result);
    }

    //TODO tests for all commands

    @AfterEach
    void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}
