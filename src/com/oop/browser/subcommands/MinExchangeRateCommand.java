package com.oop.browser.subcommands;

import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.managers.ActionManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.io.IOException;
import java.util.Date;

@Command(
        name = "min-exchange-rate",
        description = "Shows currency with smallest bid rate on given day"
)
public class MinExchangeRateCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "date")
    private Date date;

    @Override
    public void run() {
        String[] url = generateURL();
        executeBuilder(url, "Tables");
        perform();
    }

    public String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/exchangerates/tables/c/" + df.format(date) + "/?format=json"};
    }

    public void perform() {
        String label = ActionManager.MinExchangeRate.count(tableBuilder.serializable);

        System.out.println(label);
    }

}
