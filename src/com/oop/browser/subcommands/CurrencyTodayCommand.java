package com.oop.browser.subcommands;

import com.oop.browser.serializable.Table;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.Calendar;

@Command(
        name = "currency-today",
        description = "Shows currency price for today"
)
public class CurrencyTodayCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "currency symbol")
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void run() {
        String[] urls = generateURL();
        executeBuilder(urls, "Table");
        perform();
    }

    @Override
    String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/exchangerates/rates/a/" + symbol + "/?format=json"};
    }

    @Override
    void perform() {
        System.out.println(symbol.toUpperCase() + " price on " + DATE_FORMAT.format(Calendar.getInstance().getTime()) + ":");
        System.out.println(((Table[])tableBuilder.serializable.get(0))[0].getRates()[0].getMid());
    }
}
