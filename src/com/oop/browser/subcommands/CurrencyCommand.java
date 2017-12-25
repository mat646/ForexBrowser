package com.oop.browser.subcommands;

import com.oop.browser.serializable.Table;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.Date;

@Command(
        name = "currency",
        description = "Shows currency price for given day"
)
public class CurrencyCommand extends AbstractCommand implements Runnable {

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
    public void run() {
        String[] urls = generateURL();
        executeBuilder(urls, "Table");
        perform();
    }

    @Override
    String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/exchangerates/rates/a/" + symbol +
                "/" + DATE_FORMAT.format(date) + "/?format=json"};
    }

    @Override
    void perform() {
        System.out.println(symbol.toUpperCase() + " price on " + DATE_FORMAT.format(date) + ":");
        System.out.println(((Table[])tableBuilder.serializable.get(0))[0].getRates()[0].getMid());
    }

}