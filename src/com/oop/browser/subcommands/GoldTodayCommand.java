package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.serializable.Gold;
import picocli.CommandLine;
import java.io.IOException;
import java.util.Calendar;

@CommandLine.Command(
        name = "gold-today",
        description = "Shows gold price for today"
)
public class GoldTodayCommand extends Subcommand implements Runnable {

    @Override
    public void run() {

        TableBuilder tableBuilder = new TableBuilder();

        String[] url = generateURL();

        try {
            tableBuilder.setURL(url).sendRequest().buildSerializable("Gold");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Gold price on " + df.format(Calendar.getInstance().getTime()) + ":");
        System.out.println(((Gold[])tableBuilder.serializable.get(0))[0].getPrice());
    }

    private String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/cenyzlota/?format=json"};
    }

}
