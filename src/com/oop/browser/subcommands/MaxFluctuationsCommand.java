package com.oop.browser.subcommands;

import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.managers.ActionManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Command(
        name = "max-fluctuations",
        description = "Shows currency which has had greatest fluctuations since given date"
)
public class MaxFluctuationsCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "Start date for fluctuations period")
    private Date date;

    private Date today = Calendar.getInstance().getTime();

    @Override
    public void run() {
        String[] urls = generateURL();
        executeBuilder(urls, "Tables");
        perform();
    }

    @Override
    String[] generateURL() {
        ArrayList<String> out = new ArrayList<>();
        Date tempDate = new Date();
        tempDate.setTime(date.getTime());

        while(getDateDiff(tempDate, today, TimeUnit.DAYS) > 90) {

            Date tempDate2 = getAddDay(tempDate, 90);

            out.add("http://api.nbp.pl/api/exchangerates/tables/a/" + df.format(tempDate) + "/" + df.format(tempDate2) + "/?format=json");

            tempDate.setTime(tempDate2.getTime());
        }

        Date tempdate2 = getAddDay(tempDate, getDateDiff(tempDate, today, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/exchangerates/tables/a/" + df.format(tempDate) + "/" + df.format(tempdate2) + "/?format=json");

        return out.toArray(new String[out.size()]);
    }

    @Override
    void perform() {

        String symbol = ActionManager.MaxFluctuations.count(tableBuilder.serializable);
        System.out.println(symbol + " since " + df.format(date));
    }

}
