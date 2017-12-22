package com.oop.browser.subcommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.Date;

@Command(
        name = "currency-gold",
        description = "Shows currency price and gold price for given day"
)
public class CurrencyAndGoldCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "currency symbol")
    private String symbol;

    @Parameters(index = "1", arity = "1", paramLabel = "DATE",
            description = "date")
    private Date date;

    @Override
    String[] generateURL() {
        return new String[0];
    }

    @Override
    void perform() {

    }

    @Override
    public void run() {
        CurrencyCommand currencyCommand = new CurrencyCommand();
        currencyCommand.date = new Date(date.getTime());
        currencyCommand.symbol = symbol;
        currencyCommand.run();

        GoldCommand goldCommand = new GoldCommand();
        goldCommand.date = new Date(date.getTime());
        goldCommand.run();
    }
}
