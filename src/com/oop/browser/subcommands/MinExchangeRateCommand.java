package com.oop.browser.subcommands;

import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.managers.ActionManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Command(
        name = "min-exchange-rate",
        description = "Shows currency with smallest bid rate on given day"
)
public class MinExchangeRateCommand extends Subcommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "date")
    private Date date;

    @Override
    public void run() {
        String[] url = generateURL();

        try {
            ArrayList<Serializable[]> table = tableBuilder.setURL(url).sendRequest().buildSerializable("Tables");
            perform(table);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentsException e) {
            System.out.println("Invalid arguments ");
            System.exit(1);
        } catch (DataNotFoundException e) {
            System.out.println("No data for that ");
            System.exit(1);
        }
    }

    public String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/exchangerates/tables/c/" + df.format(date) + "/?format=json"};
    }

    public void perform(ArrayList<Serializable[]> tables) {
        String label = ActionManager.MinExchangeRate.count(tables);

        System.out.println(label);
    }

}
