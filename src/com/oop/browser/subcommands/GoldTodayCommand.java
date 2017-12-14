package com.oop.browser.subcommands;

import picocli.CommandLine;

@CommandLine.Command(
        name = "gold-today"
)
public class GoldTodayCommand extends Subcommand implements Runnable {

    @Override
    public void run() {
        System.out.println("GoldTodayCommand");
    }
}
