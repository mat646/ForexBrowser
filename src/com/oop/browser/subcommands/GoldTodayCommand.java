package com.oop.browser.subcommands;

import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.serializable.Gold;
import picocli.CommandLine.Command;
import java.io.IOException;
import java.util.Calendar;

@Command(
        name = "gold-today",
        description = "Shows gold price for today"
)
public class GoldTodayCommand extends AbstractCommand implements Runnable {

    @Override
    public void run() {
        String[] url = generateURL();
        executeBuilder(url, "Gold");
        perform();
    }

    @Override
    String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/cenyzlota/?format=json"};
    }

    @Override
    void perform() {
        System.out.println("Gold price on " + df.format(Calendar.getInstance().getTime()) + ":");
        System.out.println(((Gold[])tableBuilder.serializable.get(0))[0].getPrice());
    }

}
