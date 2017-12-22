package com.oop.browser.subcommands;

import com.oop.browser.serializable.Rate;
import com.oop.browser.serializable.Table;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Command(
        name = "rate-chart",
        description = "Draws rate chart for given currency and period"
)
public class RateChartCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "currency symbol")
    String symbol;

    @Parameters(index = "1", arity = "1", paramLabel = "START_DATE",
            description = "start date")
    Date startDate;

    @Parameters(index = "2", arity = "1", paramLabel = "END_DATE",
            description = "end date")
    Date endDate;

    @Override
    public void run() {
        String[] urls = generateURL();
        executeBuilder(urls, "Table");
        perform();
    }

    @Override
    String[] generateURL() {

        ArrayList<String> out = new ArrayList<>();
        Date tempDate = new Date();
        tempDate.setTime(startDate.getTime());

        while(getDateDiff(tempDate, endDate, TimeUnit.DAYS) > 90) {

            Date tempDate2 = getAddDay(tempDate, 90);

            out.add("http://api.nbp.pl/api/exchangerates/rates/a/" + symbol + "/" + DATE_FORMAT.format(tempDate) + "/" + DATE_FORMAT.format(tempDate2) + "/?format=json");

            tempDate.setTime(tempDate2.getTime());
        }

        Date tempDate2 = getAddDay(tempDate, getDateDiff(tempDate, endDate, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/exchangerates/rates/a/" + symbol + "/" + DATE_FORMAT.format(tempDate) + "/" + DATE_FORMAT.format(tempDate2) + "/?format=json");

        return out.toArray(new String[out.size()]);
    }

    @Override
    void perform() {
        ArrayList<Table[]> mappedTables = tableBuilder.serializable.stream().map(e -> (Table[]) e).collect(Collectors.toCollection(ArrayList::new));

        for (Table[] table : mappedTables) {
            Table t1 = table[0];

            for (Rate rate : t1.getRates()) {

                System.out.print(DATE_FORMAT.format(rate.getEffectiveDate()));

                Double xd = rate.getMid()*10;

                for (int i=0; i<xd; i++) {
                    System.out.print("*");
                }

                System.out.print("\n");
            }
        }
    }

}
