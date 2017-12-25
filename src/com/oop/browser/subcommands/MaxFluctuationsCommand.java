package com.oop.browser.subcommands;

import com.oop.browser.managers.ActionManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
        Date midStartDate = new Date();
        midStartDate.setTime(date.getTime());

        while(getDateDiff(midStartDate, today, TimeUnit.DAYS) > 90) {

            Date midEndDate = getAddDay(midStartDate, 90);

            out.add("http://api.nbp.pl/api/exchangerates/tables/a/" + DATE_FORMAT.format(midStartDate) + "/" +
                    DATE_FORMAT.format(midEndDate) + "/?format=json");

            midStartDate.setTime(midEndDate.getTime());
        }

        Date midEndDate = getAddDay(midStartDate, getDateDiff(midStartDate, today, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/exchangerates/tables/a/" + DATE_FORMAT.format(midStartDate) + "/" +
                DATE_FORMAT.format(midEndDate) + "/?format=json");

        return out.toArray(new String[out.size()]);
    }

    @Override
    void perform() {

        String symbol = ActionManager.MaxFluctuations.count(tableBuilder.serializable);
        System.out.println(symbol + " since " + DATE_FORMAT.format(date));
    }

}
