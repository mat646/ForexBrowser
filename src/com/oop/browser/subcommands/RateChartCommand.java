package com.oop.browser.subcommands;

import com.oop.browser.serializable.Rate;
import com.oop.browser.serializable.Table;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
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
        Date tempdate = new Date();
        tempdate.setTime(startDate.getTime());

        while(getDateDiff(tempdate, endDate, TimeUnit.DAYS) > 90) {

            Date tempDate2 = getAddDay(tempdate, 90);

            out.add("http://api.nbp.pl/api/exchangerates/rates/a/" + symbol + "/" + df.format(tempdate) + "/" + df.format(tempDate2) + "/?format=json");

            tempdate.setTime(tempDate2.getTime());
        }

        Date tempdate2 = getAddDay(tempdate, getDateDiff(tempdate, endDate, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/exchangerates/rates/a/" + symbol + "/" + df.format(tempdate) + "/" + df.format(tempdate2) + "/?format=json");

        return out.toArray(new String[out.size()]);
    }

    @Override
    void perform() {
        ArrayList<Table[]> mappedTables = tableBuilder.serializable.stream().map(e -> (Table[]) e).collect(Collectors.toCollection(ArrayList::new));

        for (Table[] table : mappedTables) {
            Table t1 = table[0];

            for (Rate rate : t1.getRates()) {

                System.out.print(df.format(rate.getEffectiveDate()));

                Double xd = rate.getMid()*10;

                for (int i=0; i<xd; i++) {
                    System.out.print("*");
                }

                System.out.print("\n");
            }
        }

    }

}
