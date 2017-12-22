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

    private Date today = Calendar.getInstance().getTime();

    @Override
    public void run() {
        String[] urls = generateURL();
        executeBuilder(urls, "Table");
        perform();
    }

    @Override
    public String[] generateURL() {

        ArrayList<String> out = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012,1,1);

        Date tempDate = calendar.getTime();

        while(getDateDiff(tempDate, today, TimeUnit.DAYS) > 90) {

            Date tempDate2 = getAddDay(tempDate, 90);

            out.add("http://api.nbp.pl/api/exchangerates/rates/a/"  + symbol + "/" + DATE_FORMAT.format(tempDate) +
                    "/" + DATE_FORMAT.format(tempDate2) + "/?format=json");

            tempDate.setTime(tempDate2.getTime());
        }

        Date tempDate2 = getAddDay(tempDate, getDateDiff(tempDate, today, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/exchangerates/rates/a/"  + symbol + "/" + DATE_FORMAT.format(tempDate) +
                "/" + DATE_FORMAT.format(tempDate2) + "/?format=json");

        return out.toArray(new String[out.size()]);
    }

    @Override
    void perform() {

        Rate[] result = ActionManager.MinMaxExchangeRate.count(tableBuilder.serializable);

        System.out.println("For " + symbol.toUpperCase() + ":");
        System.out.println("Minimal rate on: " + DATE_FORMAT.format(result[0].getEffectiveDate()));
        System.out.println("Maximal rate on: " + DATE_FORMAT.format(result[1].getEffectiveDate()));
    }

}
