package com.oop.browser;

import com.oop.browser.subcommands.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
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

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Shows program help.")
    private Boolean help;

    public static void main(String[] args) {

        CommandLine.run(new Main(), System.out, args);

    }

    @Override
    public void run() {
        System.out.println("Type -h or --help for instructions");
    }
}
