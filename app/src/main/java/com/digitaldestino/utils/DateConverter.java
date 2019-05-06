package com.digitaldestino.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateConverter {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat formatter;

    public static Date stringToDate(String dtStart) {
        Date date = null;
        try {
            date = new SimpleDateFormat(DATE_FORMAT).parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convert24HrTo12Hr(String _24HourTime) {
        String _12HourDt = "";
        try {
            return new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("HH:mm").parse(_24HourTime));
        } catch (Exception e) {
            e.printStackTrace();
            return _12HourDt;
        }
    }

    public static String convert12HrTo24Hr(String _12HourTime) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).format(new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(_12HourTime));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertDateTime24HrTo12Hr(String _24HourDateTime) {
        String _12HourDt = "";
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(new SimpleDateFormat(DATE_FORMAT).parse(_24HourDateTime));
        } catch (Exception e) {
            e.printStackTrace();
            return _12HourDt;
        }
    }

    public static String dateToString(Date date) {
        formatter = new SimpleDateFormat("d MMM, yyyy");
        return formatter.format(date);
    }

    public static String dateToStringWithYMD(Date date) {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String dateToStringInDateTime(Date date) {
        formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }

    public static String onlyDate(Date date) {
        formatter = new SimpleDateFormat("d MMM yyyy");
        return formatter.format(date);
    }

    public static String compareDate(Date date1) throws ParseException {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(11, 23);
        currentDate.set(12, 59);
        currentDate.set(13, 59);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date1);
        return String.valueOf((currentDate.getTimeInMillis() - cal2.getTimeInMillis()) / 86400000);
    }

    public static long getDiffBetweenDateInSec(Date endDate, Date startDate) {
        return TimeUnit.MILLISECONDS.toSeconds(endDate.getTime() - startDate.getTime());
    }
}
