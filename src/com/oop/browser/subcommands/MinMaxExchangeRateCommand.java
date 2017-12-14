package com.oop.browser.subcommands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "min-max-exchange-rate"
)
public class MinMaxExchangeRateCommand extends Subcommand implements Runnable {

    @Override
    public void run() {
        System.out.println("max-fluctuations");
    }

}
