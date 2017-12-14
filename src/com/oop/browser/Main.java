package com.oop.browser;

import com.oop.browser.subcommands.*;
import picocli.CommandLine;

@CommandLine.Command(
        name = "ForexBrowser",
        sortOptions = false,
        optionListHeading = "Options:\n",
        subcommands = {
                GoldAvgCommand.class,
                GoldTodayCommand.class,
                GoldCommand.class,
                CurrencyTodayCommand.class,
                CurrencyCommand.class,
                MaxFluctuationsCommand.class,
                MinExchangeRateCommand.class,
                MinMaxExchangeRateCommand.class,
                RateChartCommand.class,
                SortedSpreadCommand.class
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
        System.out.println("Done");
    }
}
