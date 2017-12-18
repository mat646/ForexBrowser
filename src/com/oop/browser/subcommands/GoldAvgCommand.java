package com.oop.browser.subcommands;

import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.managers.ActionManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.io.IOException;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Command(
        name = "gold-avg",
        description = "Average gold price for period"
)
public class GoldAvgCommand extends Subcommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "START_END",
            description = "Beginning date of period")
    private Date startDate;

    @Parameters(index = "1", arity = "1", paramLabel = "END_DATE",
            description = "Ending date of period")
    private Date endDate;

    @Override
    public void run() {

            String formattedStartDate = df.format(startDate);
            String formattedEndDate = df.format(endDate);

            String[] urls = generateURL();

        try {
            ArrayList<Serializable[]> table = tableBuilder.setURL(urls).sendRequest().buildSerializable("Gold");
            perform(table, formattedStartDate, formattedEndDate);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentsException e) {
            System.out.println("Invalid arguments");
            System.exit(1);
        } catch (DataNotFoundException e) {
            System.out.println("No data for that");
            System.exit(1);
        }
    }

    String[] generateURL() {

        ArrayList<String> out = new ArrayList<>();
        Date tempdate = new Date();
        tempdate.setTime(startDate.getTime());

        while(getDateDiff(tempdate, endDate, TimeUnit.DAYS) > 90) {

            Date tempDate2 = getAddDay(tempdate, 90);

            out.add("http://api.nbp.pl/api/cenyzlota/" + df.format(tempdate) + "/" + df.format(tempDate2) + "/?format=json");

            tempdate.setTime(tempDate2.getTime());

        }

        Date tempdate2 = getAddDay(tempdate, getDateDiff(tempdate, endDate, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/cenyzlota/" + df.format(tempdate) + "/" + df.format(tempdate2) + "/?format=json");

        return out.toArray(new String[out.size()]);

    }

    private void perform(ArrayList<Serializable[]> table, String formattedStartDate, String formattedEndDate) {
        Double avg = ActionManager.GoldAvg.countAvg(table);

        DecimalFormat dfe = new DecimalFormat("#.##");
        dfe.setRoundingMode(RoundingMode.CEILING);

        System.out.println("Average gold price since " + formattedStartDate +
                " to " + formattedEndDate + ":\n" + dfe.format(avg));
    }

}
