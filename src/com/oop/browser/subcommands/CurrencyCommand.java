package com.oop.browser.subcommands;

import picocli.CommandLine;

import java.util.Date;

@CommandLine.Command(
        name = "currency"
)
public class CurrencyCommand extends Subcommand implements Runnable {

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