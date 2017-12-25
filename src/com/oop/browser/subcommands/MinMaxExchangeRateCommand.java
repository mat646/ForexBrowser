package com.oop.browser.subcommands;

import com.oop.browser.managers.ActionManager;
import com.oop.browser.serializable.Rate;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Command(
        name = "min-max-exchange-rate",
        description = "For given currency shows day with lowest and highest mid rate"
)
public class MinMaxExchangeRateCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "currency symbol")
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private Date today = Calendar.getInstance().getTime();

    @Override
    public void run() {
        String[] urls = generateURL();
        executeBuilder(urls, "Table");
        perform();
    }

    @Override
    public String[] generateURL() {
        ArrayList<String> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012,Calendar.FEBRUARY,1);

        Date midStartDate = calendar.getTime();

        while(getDateDiff(midStartDate, today, TimeUnit.DAYS) > 90) {

            Date midEndDate = getAddDay(midStartDate, 90);

            result.add("http://api.nbp.pl/api/exchangerates/rates/a/"  + symbol + "/" + DATE_FORMAT.format(midStartDate) +
                    "/" + DATE_FORMAT.format(midEndDate) + "/?format=json");

            midStartDate.setTime(midEndDate.getTime());
        }

        Date midEndDate = getAddDay(midStartDate, getDateDiff(midStartDate, today, TimeUnit.DAYS));
        result.add("http://api.nbp.pl/api/exchangerates/rates/a/"  + symbol + "/" + DATE_FORMAT.format(midStartDate) +
                "/" + DATE_FORMAT.format(midEndDate) + "/?format=json");

        return result.toArray(new String[result.size()]);
    }

    @Override
    void perform() {
        Rate[] result = ActionManager.MinMaxExchangeRate.count(tableBuilder.serializable);

        System.out.println("For " + symbol.toUpperCase() + ":");
        System.out.println("Minimal rate on: " + DATE_FORMAT.format(result[0].getEffectiveDate()));
        System.out.println("Maximal rate on: " + DATE_FORMAT.format(result[1].getEffectiveDate()));
    }

}
