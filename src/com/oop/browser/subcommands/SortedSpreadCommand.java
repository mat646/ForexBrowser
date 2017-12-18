package com.oop.browser.subcommands;

import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import com.oop.browser.managers.ActionManager;
import com.oop.browser.serializable.Rate;
import com.oop.browser.serializable.Table;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.io.IOException;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;

@Command(
        name = "sorted-spread",
        description = "Prints n currencies sorted by spread for given date"
)
public class SortedSpreadCommand extends Subcommand implements Runnable {

    @Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "Date for spread count")
    private Date date;

    @Parameters(index = "1", arity = "1", paramLabel = "N",
            description = "Amount of head results")
    private int n;

    @Override
    public void run() {

        if (n < 1) {
            System.out.println("n must be positive");
            System.exit(1);
        }

        String[] url = generateURL();

        try {
            ArrayList<Serializable[]> table = tableBuilder.setURL(url).sendRequest().buildSerializable("Tables");
            perform(table);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentsException e) {
            System.out.println("Invalid arguments spread");
            System.exit(1);
        } catch (DataNotFoundException e) {
            System.out.println("No data for spread ");
            System.exit(1);
        }

    }

    public String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/exchangerates/tables/c/" + df.format(date) + "/?format=json"};
    }

    public void perform(ArrayList<Serializable[]> tables) {
        Rate[] rates = ActionManager.SortedSpread.count((Table) tables.get(0)[0], n);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');

        DecimalFormat dfe = new DecimalFormat("#.###", symbols);
        dfe.setRoundingMode(RoundingMode.CEILING);

        for(Rate rate : rates) {
            System.out.println(rate.getCode() + " with spread " + dfe.format(rate.getAsk() - rate.getBid()));
        }
    }

}
