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

        Date tempdate = calendar.getTime();

        while(getDateDiff(tempdate, today, TimeUnit.DAYS) > 90) {

            Date tempDate2 = getAddDay(tempdate, 90);

            out.add("http://api.nbp.pl/api/exchangerates/rates/a/"  + symbol + "/" + df.format(tempdate) +  "/" + df.format(tempDate2) + "/?format=json");

            tempdate.setTime(tempDate2.getTime());
        }

        Date tempdate2 = getAddDay(tempdate, getDateDiff(tempdate, today, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/exchangerates/rates/a/"  + symbol + "/" + df.format(tempdate) +  "/" + df.format(tempdate2) + "/?format=json");

        return out.toArray(new String[out.size()]);
    }

    @Override
    void perform() {

        Rate[] result = ActionManager.MinMaxExchangeRate.count(tableBuilder.serializable);

        System.out.println("For " + symbol.toUpperCase() + ":");
        System.out.println("Minimal rate on: " + df.format(result[0].getEffectiveDate()));
        System.out.println("Maximal rate on: " + df.format(result[1].getEffectiveDate()));
    }

}
