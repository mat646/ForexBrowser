package com.oop.browser.subcommands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "min-exchange-rate"
)
public class MinExchangeRateCommand extends Subcommand implements Runnable {

    @Override
    public void run() {
        System.out.println("max-fluctuations");
    }

}
