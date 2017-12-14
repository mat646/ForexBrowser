package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import picocli.CommandLine;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

            String readydate = df.format(startDate);
            String readydate2 = df.format(endDate);


            String[] urls = generateURL(startDate, endDate);

            Serializable[] table = tableBuilder
                    .setURL(urls)
                    .sendRequest().buildSerializable("Gold");

            System.out.println("avg " + startDate);
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
