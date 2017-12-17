package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.serializable.Table;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.io.IOException;
import java.util.Calendar;

@Command(
        name = "currency-today",
        description = "Shows currency price for today"
)
public class CurrencyTodayCommand extends Subcommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "currency symbol")
    private String symbol;

    private TableBuilder tableBuilder = new TableBuilder();

    @Override
    public void run() {
        String[] url = generateURL();

        try {
            tableBuilder.setURL(url).sendRequest().buildSerializable("Table");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentsException e) {
            System.out.println("Invalid arguments for currency-today");
            System.exit(1);
        } catch (DataNotFoundException e) {
            System.out.println("No data for currency-today");
            System.exit(1);
        }

        perform();
    }

    private String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/exchangerates/rates/a/" + symbol + "/?format=json"};
    }

    private void perform() {
        System.out.println(symbol.toUpperCase() + " price on " + df.format(Calendar.getInstance().getTime()) + ":");
        System.out.println(((Table[])tableBuilder.serializable.get(0))[0].getRates()[0].getMid());
    }
}
