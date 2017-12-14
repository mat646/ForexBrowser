package com.oop.browser.managers;

import com.oop.browser.serializable.Gold;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ActionManager implements IManager {


    public static class GoldAvg {

        static public Double countAvg(ArrayList<Serializable[]> golds) {

            ArrayList<Gold[]> table = golds.stream().map(e -> (Gold[]) e).collect(Collectors.toCollection(ArrayList::new));

            Double sum = .0;
            Integer count = 0;

            for (Gold[] tab : table) {
                count = count + tab.length;
                for (Gold gold : tab) {
                    sum = sum + gold.getPrice();
                }
            }

            return sum/count;
        }

    }

}
