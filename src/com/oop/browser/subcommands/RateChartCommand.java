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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public void run() {
        String[] urls = generateURL();
        executeBuilder(urls, "Table");
        perform();
    }

    @Override
    String[] generateURL() {

        ArrayList<String> out = new ArrayList<>();
        Date midStartDate = new Date();
        midStartDate.setTime(startDate.getTime());

        while(getDateDiff(midStartDate, endDate, TimeUnit.DAYS) > 90) {

            Date midEndDate = getAddDay(midStartDate, 90);

            out.add("http://api.nbp.pl/api/exchangerates/rates/a/" + symbol + "/" + DATE_FORMAT.format(midStartDate) + "/" +
                    DATE_FORMAT.format(midEndDate) + "/?format=json");

            midStartDate.setTime(midEndDate.getTime());
        }

        Date midEndDate = getAddDay(midStartDate, getDateDiff(midStartDate, endDate, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/exchangerates/rates/a/" + symbol + "/" + DATE_FORMAT.format(midStartDate) + "/" +
                DATE_FORMAT.format(midEndDate) + "/?format=json");

        return out.toArray(new String[out.size()]);
    }

    @Override
    void perform() {
        ArrayList<Table[]> mappedTables = tableBuilder.serializable.stream().map(e -> (Table[]) e)
                .collect(Collectors.toCollection(ArrayList::new));

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
