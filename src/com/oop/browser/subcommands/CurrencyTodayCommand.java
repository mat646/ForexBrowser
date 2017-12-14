package com.oop.browser.subcommands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "currency-today"
)
public class CurrencyTodayCommand extends Subcommand implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "symbol")
    private String symbol;

    @Override
    public void run() {
        System.out.println("today");
    }

    private String[] generateURL() {
        return new String[]{"http://api.nbp.pl/api/xc/?format=json"};
    }
}
