package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.managers.ActionManager;
import com.oop.browser.serializable.Table;
import picocli.CommandLine;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@CommandLine.Command(
        name = "max-fluctuations"
)
public class MaxFluctuationsCommand extends Subcommand implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "Start date for currency")
    private Date date;

    private TableBuilder tableBuilder = new TableBuilder();

    private Date today = Calendar.getInstance().getTime();

    @Override
    public void run() {
        String[] urls = generateURL(date);

        try {
            ArrayList<Serializable[]> table = tableBuilder.setURL(urls).sendRequest().buildSerializable("Tables");
            perform(table, date);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentsException e) {
            System.out.println("Invalid arg");
            System.exit(1);
        } catch (DataNotFoundException e) {
            System.out.println("No data for t");
            System.exit(1);
        }

    }

    private String[] generateURL(Date date) {
        ArrayList<String> out = new ArrayList<>();
        Date tempdate = new Date();
        tempdate.setTime(date.getTime());

        while(getDateDiff(tempdate, today, TimeUnit.DAYS) > 90) {

            Date tempDate2 = getAddDay(tempdate, 90);

            out.add("http://api.nbp.pl/api/exchangerates/tables/a/" + df.format(tempdate) + "/" + df.format(tempDate2) + "/?format=json");

            tempdate.setTime(tempDate2.getTime());
        }

        Date tempdate2 = getAddDay(tempdate, getDateDiff(tempdate, today, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/exchangerates/tables/a/" + df.format(tempdate) + "/" + df.format(tempdate2) + "/?format=json");

        return out.toArray(new String[out.size()]);
    }

    private void perform(ArrayList<Serializable[]> tables, Date since) {

        String symbol = ActionManager.MaxFluctuations.count(tables);
        System.out.println(symbol + " since " + df.format(since));
    }

}
