package com.oop.browser.managers;

import com.oop.browser.serializable.Gold;
import com.oop.browser.serializable.Rate;
import com.oop.browser.serializable.Table;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ActionManager implements IManager {


    public static class GoldAvg {

        public static Double countAvg(ArrayList<Serializable[]> golds) {
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

    public static class MinExchangeRate {

        public static String count(Table[] tables) {
            Table table = tables[0];

            Rate minRate = table.getRates()[0];

            for (Rate rate : table.getRates()) {
                if (rate.getBid() < minRate.getBid()) {
                    minRate = rate;
                }
            }

            return minRate.getCode();
        }

    }

    public static class MinMaxExchangeRate {

        public static Rate[] count(Table table, String symbol) {
            Rate minRate = table.getRates()[0];
            Rate maxRate = table.getRates()[0];

            for (Rate rate : table.getRates()) {
                if (rate.getMid() < minRate.getMid()) {
                    minRate = rate;
                }
                if (rate.getMid() > maxRate.getMid()) {
                    maxRate = rate;
                }
            }

            return new Rate[]{minRate, maxRate};
        }

    }

    public static class MaxFluctuations {

        public static String count(Table[] tables) {
            Map<String, Double> min = new HashMap<>();
            Map<String, Double> max = new HashMap<>();

            for (Table table : tables) {

                for (Rate rate : table.getRates()) {

                    if (min.get(rate.getCode()) == null ||
                            min.get(rate.getCode()) > rate.getMid()) {
                        min.put(rate.getCode(), rate.getMid());
                    }

                    if (max.get(rate.getCode()) == null ||
                            max.get(rate.getCode()) < rate.getMid()) {
                        max.put(rate.getCode(), rate.getMid());
                    }

                }

            }

            return findMaxFluctuations(min, max);
        }

        private static String findMaxFluctuations(Map<String, Double> min,
                                                  Map<String, Double> max) {

            String maxFluctuationsCode = min.keySet().toArray(new String[1])[0];

            for (String code : min.keySet()) {
                Double diff = max.get(code) - min.get(code);
                Double xd = max.get(maxFluctuationsCode) - min.get(maxFluctuationsCode);
                if (diff > xd) {
                    maxFluctuationsCode = code;
                }
            }

            return maxFluctuationsCode;
        }


    }

    public static class SortedSpread {

        public static Rate[] count(Table table, int n) {

            RateComparator rateComparator = new RateComparator();
            PriorityQueue<Rate> queue =
                    new PriorityQueue<>(rateComparator);

            queue.addAll(Arrays.asList(table.getRates()));

            return queue.toArray(new Rate[n]);
        }

        private static class RateComparator implements Comparator<Rate> {
            @Override
            public int compare(Rate x, Rate y) {
                return Double.compare(x.getAsk() - x.getBid(), y.getAsk() - y.getBid());
            }
        }

    }

    public static class RateChart {

        public void count(Table[] tables) {

        }

    }

}
