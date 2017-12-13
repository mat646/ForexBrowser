package com.oop.browser;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.managers.ActionManager;
import picocli.CommandLine;
import java.io.*;
import java.util.Date;

@CommandLine.Command(
        name = "ForexBrowser",
        sortOptions = false,
        optionListHeading = "Options:\n",
        subcommands = {
                goldAvg.class,
                goldToday.class,
                gold.class,
                currencyToday.class
        }
)
public class Main implements Runnable{

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Shows program help.")
    private Boolean help;



    private String[] args;


    public static void main(String[] args) {

        CommandLine.run(new Main(), System.out, args);

    }

    @Override
    public void run() {
        try {

            TableBuilder tableBuilder = new TableBuilder();

            Serializable[] table = tableBuilder.generateURL(args).sendRequest().buildSerializable();

            // http://api.nbp.pl/api/exchangerates/tables/c/?format=json
            // http://api.nbp.pl/api/cenyzlota/?format=json

            ActionManager.Avg actionManagerAvg = new ActionManager.Avg();

            actionManagerAvg.countAvg(table);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}

@CommandLine.Command(
        name = "gold-avg"
)
class goldAvg implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "START_END",
            description = "Start startDate for computing")
    private Date startDate;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "END_DATE",
            description = "Start endDate for computing")
    private Date endDate;

    @Override
    public void run() {
        System.out.println("avg " + startDate.getMonth());
    }
}

@CommandLine.Command(
        name = "gold-today"
)
class goldToday implements Runnable {

    @Override
    public void run() {
        System.out.println("goldToday");
    }
}

@CommandLine.Command(
        name = "gold"
)
class gold implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "START_END",
            description = "Start date for computing")
    private Date date;

    @Override
    public void run() {
        System.out.println("gold");
    }
}

@CommandLine.Command(
        name = "currency-today"
)
class currencyToday implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "symbol")
    private String symbol;

    @Override
    public void run() {
        System.out.println("today");
    }
}

@CommandLine.Command(
        name = "currency"
)
class currency implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "symbol")
    private String symbol;

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "DATE",
            description = "date")
    private Date date;

    @Override
    public void run() {
        System.out.println("today");
    }
}


