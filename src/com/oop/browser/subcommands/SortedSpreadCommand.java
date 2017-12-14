package com.oop.browser.subcommands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "rate-chart"
)
public class SortedSpreadCommand extends Subcommand implements Runnable {

    @Override
    public void run() {
        System.out.println("spread");
    }

}
