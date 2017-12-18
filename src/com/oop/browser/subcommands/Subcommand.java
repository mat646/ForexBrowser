package com.oop.browser.subcommands;

import com.oop.browser.builders.TableBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Subcommand {

    public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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

    public static Date getAddDay(Date date1, long days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        c.add(Calendar.DATE, (int) days);  // number of days to add
        return c.getTime();
    }

    abstract String[] generateURL();

}
