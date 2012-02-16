package org.cvut.skischool.core;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author matej
 */
public class DateTools {

    public static int getMinutes(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c.get(Calendar.MINUTE);
    }

    public static int getHours(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static Date makeDate(Date date, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return c.getTime();
    }

    public static Date makeDateTime(Date date, int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);

        return c.getTime();
    }

    public static Date makeDateTime(Date date, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);

        return c.getTime();
    }

    public static Date addMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 1);

        return c.getTime();
    }

    public static Date addHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 1);

        return c.getTime();
    }

    public static Date addDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);

        return c.getTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + days);

        return c.getTime();
    }

    public static Date subtractMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - 1);

        return c.getTime();
    }

    public static Date subtractDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);

        return c.getTime();
    }

    public static double timeToHours(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        double hours = c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) / 60.0;

        return hours;
    }

    public static long hoursDifference(Date startTime, Date endTime) {
        long hours = 0;
        Calendar cStartTime = Calendar.getInstance();
        Calendar cEndTime = Calendar.getInstance();
        cStartTime.setTime(startTime);
        cEndTime.setTime(endTime);
        long startHour = cStartTime.get(Calendar.HOUR_OF_DAY);
        long endHour = cEndTime.get(Calendar.HOUR_OF_DAY);
        hours = endHour - startHour;
        return hours;
    }

    public static boolean equalsTime(Date time1, Date time2) {
        if ((time1 == null) || (time2 == null)) {
            return false;
        }

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(time1);
        c2.setTime(time2);

        if (c1.get(Calendar.HOUR_OF_DAY) != c2.get(Calendar.HOUR_OF_DAY)) {
            return false;
        }
        if (c1.get(Calendar.MINUTE) != c2.get(Calendar.MINUTE)) {
            return false;
        }

        return true;
    }
}
