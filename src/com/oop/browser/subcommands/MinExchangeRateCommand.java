package com.oop.browser.subcommands;

import com.oop.browser.managers.ActionManager;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.Date;

@Command(
        name = "min-exchange-rate",
        description = "Shows currency with smallest bid rate on given day"
)
public class MinExchangeRateCommand extends AbstractCommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "date")
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
        executeBuilder(url, "Tables");
        perform();
    }

    public String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/exchangerates/tables/c/" + DATE_FORMAT.format(date) + "/?format=json"};
    }

    public void perform() {
        String label = ActionManager.MinExchangeRate.count(tableBuilder.serializable);

        System.out.println(label);
    }

}
