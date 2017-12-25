package com.oop.browser.subcommands;

import com.oop.browser.serializable.Gold;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.Date;

@Command(
        name = "gold",
        description = "Shows gold price in given day"
)
public class GoldCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "Date for gold price")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void run() {
        String[] url = generateURL();
        executeBuilder(url, "Gold");
        perform();
    }

    @Override
    String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/cenyzlota/" + DATE_FORMAT.format(date) + "/?format=json"};
    }

    @Override
    void perform() {
        System.out.println("Gold price on " + DATE_FORMAT.format(date) + ":");
        System.out.println(((Gold[])tableBuilder.serializable.get(0))[0].getPrice());
    }

}