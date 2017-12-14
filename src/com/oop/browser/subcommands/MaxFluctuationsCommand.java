package com.oop.browser.subcommands;


import picocli.CommandLine;

@CommandLine.Command(
        name = "max-fluctuations"
)
public class MaxFluctuationsCommand extends Subcommand implements Runnable {

    @Override
    public void run() {
        System.out.println("max-fluctuations");
    }

}
