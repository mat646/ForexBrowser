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

        public static String count(ArrayList<Serializable[]> tables) {
            Table table = ((Table[]) tables.get(0))[0];

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

        public static Rate[] count(ArrayList<Serializable[]> tables, String symbol) {
            ArrayList<Table> mappedTables = tables.stream().map(e -> (Table) e[0]).collect(Collectors.toCollection(ArrayList::new));

            Rate minRate = mappedTables.get(0).getRates()[0];
            Rate maxRate = mappedTables.get(0).getRates()[0];

            for (Table table : mappedTables) {
                for (Rate rate : table.getRates()) {
                    if (rate.getMid() < minRate.getMid()) {
                        minRate = rate;
                    }
                    if (rate.getMid() > maxRate.getMid()) {
                        maxRate = rate;
                    }
                }
            }

            return new Rate[]{minRate, maxRate};
        }

    }

    public static class MaxFluctuations {

        public static String count(ArrayList<Serializable[]> tables) {
            ArrayList<Table[]> mappedTables = tables.stream().map(e -> (Table[]) e).collect(Collectors.toCollection(ArrayList::new));

            Map<String, Double> min = new HashMap<>();
            Map<String, Double> max = new HashMap<>();

            for (Table[] table : mappedTables) {
                for (Table tab : table) {
                    for (Rate rate : tab.getRates()) {

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

            if(n > table.getRates().length) {
                n = table.getRates().length;
            }

            RateComparator rateComparator = new RateComparator();
            PriorityQueue<Rate> queue =
                    new PriorityQueue<>(rateComparator);

            queue.addAll(Arrays.asList(table.getRates()));

            ArrayList<Rate> out = new ArrayList<>();

            for (int i=0; i<n; i++) {
                out.add(queue.poll());
            }

            return out.toArray(new Rate[n]);
        }

        private static class RateComparator implements Comparator<Rate> {
            @Override
            public int compare(Rate x, Rate y) {
                return Double.compare(y.getAsk() - y.getBid(), x.getAsk() - x.getBid());
            }
        }

    }

}
