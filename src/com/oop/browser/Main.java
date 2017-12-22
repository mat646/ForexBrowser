package com.oop.browser;

import com.oop.browser.subcommands.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author Mateusz Sokol
 * @version 1.0
 */
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
                SortedSpreadCommand.class,
                RateChartGUICommand.class,
                CurrencyAndGoldCommand.class
        }
)
public class Main implements Runnable{

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Shows program help")
    private Boolean help;

    public static void main(String[] args) {

        CommandLine.run(new Main(), System.out, args);

    }

    @Override
    public void run() {

        System.out.print("\033[0;37m" +
                " _______                     ______                                    \n" +
                "(_______)                   (____  \\                                   \n" +
                " _____ ___   ____ ____ _   _ ____)  ) ____ ___  _ _ _  ___  ____  ____ \n" +
                "|  ___) _ \\ / ___) _  | \\ / )  __  ( / ___) _ \\| | | |/___)/ _  )/ ___)\n" +
                "| |  | |_| | |  ( (/ / ) X (| |__)  ) |  | |_| | | | |___ ( (/ /| |    \n" +
                "|_|   \\___/|_|   \\____|_/ \\_)______/|_|   \\___/ \\____(___/ \\____)_|    \n" +
                "                                                                       \n" +
                "\n" + "\033[0m");
        System.out.println("Type -h or --help for instructions\n");
    }
}
