package com.oop.browser.subcommands;

import com.oop.browser.managers.ActionManager;
import com.oop.browser.serializable.Rate;
import com.oop.browser.serializable.Table;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;

@Command(
        name = "sorted-spread",
        description = "Prints n currencies sorted by spread for given date"
)
public class SortedSpreadCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "Date for spread count")
    private Date date;

    @Parameters(index = "1", arity = "1", paramLabel = "N",
            description = "Amount of head results")
    private int n;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        if (n < 1) {
            System.out.println("n must be positive");
            System.exit(1);
        }

        String[] url = generateURL();
        executeBuilder(url, "Tables");
        perform();
    }

    @Override
    String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/exchangerates/tables/c/" + DATE_FORMAT.format(date) + "/?format=json"};
    }

    @Override
    void perform() {
        Rate[] rates = ActionManager.SortedSpread.count((Table) tableBuilder.serializable.get(0)[0], n);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');

        DecimalFormat dfe = new DecimalFormat("#.###", symbols);
        dfe.setRoundingMode(RoundingMode.CEILING);

        for(Rate rate : rates) {
            System.out.println(rate.getCode() + " with spread " + dfe.format(rate.getAsk() - rate.getBid()));
        }
    }

}
