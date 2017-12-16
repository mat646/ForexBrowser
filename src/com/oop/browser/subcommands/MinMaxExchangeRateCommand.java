package com.oop.browser.subcommands;

import picocli.CommandLine;

import java.util.Date;

@CommandLine.Command(
        name = "min-max-exchange-rate"
)
public class MinMaxExchangeRateCommand extends Subcommand implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "symbol")
    private String symbol;

    @Override
    public void run() {

    }

    public String[] generateURL(Date date) {
        //TODO finish it
        return new String[]{"http://api.nbp.pl/api/exchangerates/rates/a/" + symbol + "/{startDate}/{endDate}/"};
    }

    public void perform() {

    }

}
