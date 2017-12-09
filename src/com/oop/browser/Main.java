package com.oop.browser;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.managers.ActionManager;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        try {

            TableBuilder tableBuilder = new TableBuilder();

            Serializable table = tableBuilder.generateURL(args).sendRequest().buildSerializable();

            // http://api.nbp.pl/api/exchangerates/tables/c/?format=json
            // http://api.nbp.pl/api/cenyzlota/?format=json

            ActionManager.Avg actionManagerAvg = new ActionManager.Avg();

            actionManagerAvg.countAvg(table);

        } catch (IOException e) {

            e.printStackTrace();
        }


    }
}
