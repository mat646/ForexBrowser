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
        description = "Average gold price for given period"
)
public class GoldAvgCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "START_END",
            description = "Beginning date of period")
    private Date startDate;

    @Parameters(index = "1", arity = "1", paramLabel = "END_DATE",
            description = "Ending date of period")
    private Date endDate;

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
        executeBuilder(urls, "Gold");
        perform();
    }

    @Override
    String[] generateURL() {
        ArrayList<String> result = new ArrayList<>();
        Date midStartDate = new Date();
        midStartDate.setTime(startDate.getTime());

        while(getDateDiff(midStartDate, endDate, TimeUnit.DAYS) > 90) {

            Date midEndDate = getAddDay(midStartDate, 90);

            result.add("http://api.nbp.pl/api/cenyzlota/" + DATE_FORMAT.format(midStartDate) + "/" +
                    DATE_FORMAT.format(midEndDate) + "/?format=json");

            midStartDate.setTime(midEndDate.getTime());

        }

        Date midEndDate = getAddDay(midStartDate, getDateDiff(midStartDate, endDate, TimeUnit.DAYS));
        result.add("http://api.nbp.pl/api/cenyzlota/" + DATE_FORMAT.format(midStartDate) + "/" +
                DATE_FORMAT.format(midEndDate) + "/?format=json");

        return result.toArray(new String[result.size()]);
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
