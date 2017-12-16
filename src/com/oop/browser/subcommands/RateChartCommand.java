package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.serializable.Rate;
import com.oop.browser.serializable.Table;
import picocli.CommandLine;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@CommandLine.Command(
        name = "rate-chart"
)
public class RateChartCommand extends Subcommand implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "symbol")
    private String symbol;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "START_DATE",
            description = "date")
    private Date startDate;

    @CommandLine.Parameters(index = "2", arity = "1", paramLabel = "END_DATE",
            description = "date")
    private Date endDate;

    private TableBuilder tableBuilder = new TableBuilder();

    @Override
    public void run() {

        String[] urls = generateURL();

        try {
            ArrayList<Serializable[]> table = tableBuilder.setURL(urls).sendRequest().buildSerializable("Table");
            perform(table);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentsException e) {
            System.out.println("Invalid arg chart");
            System.exit(1);
        } catch (DataNotFoundException e) {
            System.out.println("No data for chart");
            System.exit(1);
        }

    }

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

    void perform(ArrayList<Serializable[]> tables) {
        ArrayList<Table[]> mappedTables = tables.stream().map(e -> (Table[]) e).collect(Collectors.toCollection(ArrayList::new));

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
