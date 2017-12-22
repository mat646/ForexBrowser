package com.oop.browser.subcommands;

import com.oop.browser.managers.ActionManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Command(
        name = "gold-avg",
        description = "Average gold price for period"
)
public class GoldAvgCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "START_END",
            description = "Beginning date of period")
    private Date startDate;

    @Parameters(index = "1", arity = "1", paramLabel = "END_DATE",
            description = "Ending date of period")
    private Date endDate;

    @Override
    public void run() {
        String[] urls = generateURL();
        executeBuilder(urls, "Gold");
        perform();
    }

    @Override
    String[] generateURL() {

        ArrayList<String> out = new ArrayList<>();
        Date tempDate = new Date();
        tempDate.setTime(startDate.getTime());

        while(getDateDiff(tempDate, endDate, TimeUnit.DAYS) > 90) {

            Date tempDate2 = getAddDay(tempDate, 90);

            out.add("http://api.nbp.pl/api/cenyzlota/" + DATE_FORMAT.format(tempDate) + "/" + DATE_FORMAT.format(tempDate2) + "/?format=json");

            tempDate.setTime(tempDate2.getTime());

        }

        Date tempDate2 = getAddDay(tempDate, getDateDiff(tempDate, endDate, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/cenyzlota/" + DATE_FORMAT.format(tempDate) + "/" + DATE_FORMAT.format(tempDate2) + "/?format=json");

        return out.toArray(new String[out.size()]);

    }

    @Override
    void perform() {
        Double avg = ActionManager.GoldAverage.count(tableBuilder.serializable);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);

        System.out.println("Average gold price since " + DATE_FORMAT.format(startDate) +
                " to " + DATE_FORMAT.format(endDate) + ":\n" + decimalFormat.format(avg));
    }

}
