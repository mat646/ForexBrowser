package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.serializable.Gold;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.io.IOException;
import java.util.Date;

@Command(
        name = "gold",
        description = "Shows gold price in given name"
)
public class GoldCommand extends Subcommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "Date for gold price")
    private Date date;

    private TableBuilder tableBuilder = new TableBuilder();

    @Override
    public void run() {

        String[] url = generateURL(date);

        try {
            tableBuilder.setURL(url).sendRequest().buildSerializable("Gold");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentsException e) {
            System.out.println("Invalid arguments");
            System.exit(1);
        } catch (DataNotFoundException e) {
            System.out.println("No data for that");
            System.exit(1);
        }

        perform();
    }

    private String[] generateURL(Date date) {
        return new String[]{"http://api.nbp.pl/api/cenyzlota/" + df.format(date) + "/?format=json"};
    }

    private void perform() {
        System.out.println("Gold price on " + df.format(date) + ":");
        System.out.println(((Gold[])tableBuilder.serializable.get(0))[0].getPrice());
    }

}