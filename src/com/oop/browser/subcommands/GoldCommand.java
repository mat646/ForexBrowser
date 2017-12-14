package com.oop.browser.subcommands;

import picocli.CommandLine;

import java.util.Date;

@CommandLine.Command(
        name = "gold"
)
public class GoldCommand extends Subcommand implements Runnable {

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "START_END",
            description = "Start date for computing")
    private Date date;

    @Override
    public void run() {
        System.out.println("GoldCommand");
    }
}