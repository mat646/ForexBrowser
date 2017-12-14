package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.managers.ActionManager;
import picocli.CommandLine;
import java.io.IOException;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@CommandLine.Command(
        name = "gold-avg"
)
public class GoldAvgCommand extends Subcommand implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "START_END",
            description = "Start startDate for computing")
    private Date startDate;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "END_DATE",
            description = "Start endDate for computing")
    private Date endDate;

    @Override
    public void run() {

        try {
            TableBuilder tableBuilder = new TableBuilder();

            String formattedStartDate = df.format(startDate);
            String formattedEndDate = df.format(endDate);

            String[] urls = generateURL(startDate, endDate);

            ArrayList<Serializable[]> table = tableBuilder.setURL(urls).sendRequest().buildSerializable("Gold");

            Double avg = ActionManager.GoldAvg.countAvg(table);

            DecimalFormat dfe = new DecimalFormat("#.##");
            dfe.setRoundingMode(RoundingMode.CEILING);

            System.out.println("Average gold price since " + formattedStartDate +
                    " to " + formattedEndDate + ":\n" + dfe.format(avg));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] generateURL(Date date1, Date date2) {

        ArrayList<String> out = new ArrayList<>();
        Date tempdate = new Date();
        tempdate.setTime(date1.getTime());

        while(getDateDiff(tempdate, date2, TimeUnit.DAYS) > 90) {

            Date tempdate2 = getAddDay(tempdate, 90);

            out.add("http://api.nbp.pl/api/cenyzlota/" + df.format(tempdate) + "/" + df.format(tempdate2) + "/?format=json");

            tempdate.setTime(tempdate2.getTime());

        }

        Date tempdate2 = getAddDay(tempdate, getDateDiff(tempdate, date2, TimeUnit.DAYS));
        out.add("http://api.nbp.pl/api/cenyzlota/" + df.format(tempdate) + "/" + df.format(tempdate2) + "/?format=json");

        return out.toArray(new String[out.size()]);

    }

}
