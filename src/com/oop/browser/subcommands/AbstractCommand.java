package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import com.oop.browser.exceptions.DataNotFoundException;
import com.oop.browser.exceptions.InvalidArgumentsException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCommand {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    protected TableBuilder tableBuilder = new TableBuilder();

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    /**
     *
     * @param date1
     * @param days
     * @return
     */
    public static Date getAddDay(Date date1, long days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        c.add(Calendar.DATE, (int) days);  // number of days to add
        return c.getTime();
    }

    /**
     *
     * @return
     */
    abstract String[] generateURL();

    /**
     *
     * @param urls
     * @param typeValue
     */
    void executeBuilder(String[] urls, String typeValue) {
        try {
            tableBuilder.setURL(urls).sendRequest().buildSerializable(typeValue);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentsException e) {
            System.out.println("Invalid arguments");
            System.exit(1);
        } catch (DataNotFoundException e) {
            System.out.println("No data for such arguments");
            System.exit(1);
        }
    }

    /**
     * Performs printing processed values in terminal or in new window
     * @see RateChartGUICommand
     */
    abstract void perform();
}
