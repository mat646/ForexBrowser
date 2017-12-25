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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
        currencyCommand.setDate(new Date(date.getTime()));
        currencyCommand.setSymbol(symbol);
        currencyCommand.run();

        GoldCommand goldCommand = new GoldCommand();
        goldCommand.setDate(new Date(date.getTime()));
        goldCommand.run();
    }
}
