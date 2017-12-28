package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.serializable.Rate;
import com.oop.browser.serializable.Table;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.util.*;
import java.util.stream.Collectors;

@Command(
        name = "rate-chart-gui",
        description = "Prints n currencies sorted by spread for given date in javafx"
)
public class RateChartGUICommand extends Application implements Runnable{

    @CommandLine.Parameters(index = "0", arity = "1", paramLabel = "SYMBOL",
            description = "currency symbol")
    private String symbol;

    @CommandLine.Parameters(index = "1", arity = "1", paramLabel = "START_DATE",
            description = "start date")
    private Date startDate;

    @CommandLine.Parameters(index = "2", arity = "1", paramLabel = "END_DATE",
            description = "end date")
    private Date endDate;

    static private String[] week = {"None","Sun","Mon","Tue","Wed","Thu","Fri","Sat"};

    TableBuilder tableBuilder;

    @Override
    public void run() {
        launch(symbol, String.valueOf(startDate.getTime()), String.valueOf(endDate.getTime()));
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Bar Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("Currency rate chart");
        xAxis.setLabel("Weekday");
        yAxis.setLabel("Value");

        RateChartCommand rateChartCommand = new RateChartCommand();
        rateChartCommand.setSymbol(getParameters().getRaw().get(0));
        rateChartCommand.setStartDate(new Date(Long.valueOf(getParameters().getRaw().get(1))));
        rateChartCommand.setEndDate(new Date(Long.valueOf(getParameters().getRaw().get(2))));

        rateChartCommand.run();

        tableBuilder = rateChartCommand.tableBuilder;

        ArrayList<Table[]> mappedTables = tableBuilder.serializable.stream().map(e -> (Table[]) e)
                .collect(Collectors.toCollection(ArrayList::new));

        Scene scene  = new Scene(bc,800,600);
        Map<Integer, XYChart.Series> mappedSeries = new HashMap<>();

        for (Table[] table : mappedTables) {
            for (Rate rate : table[0].getRates()) {
                Calendar c = Calendar.getInstance();
                c.setTime(rate.getEffectiveDate());
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
                XYChart.Series series = mappedSeries.get(weekOfYear);
                if (series == null) {
                    XYChart.Series series1 = new XYChart.Series();

                    series1.getData().add(new XYChart.Data(week[dayOfWeek], rate.getMid()));
                    series1.setName(String.valueOf(weekOfYear));
                    mappedSeries.put(weekOfYear, series1);
                } else {

                    series.getData().add(new XYChart.Data(week[dayOfWeek], rate.getMid()));
                }

            }
        }

        bc.getData().addAll(mappedSeries.values().toArray(new XYChart.Series[mappedSeries.values().toArray().length]));
        stage.setScene(scene);
        stage.show();
    }
}
