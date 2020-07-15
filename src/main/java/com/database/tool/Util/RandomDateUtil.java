package com.database.tool.Util;

// The original link：https://blog.csdn.net/seeds_home/article/details/7922848

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomDateUtil {
    /**
     * Get random date
     *
     * @param beginDate format as ：yyyy-MM-dd HH:mm:ss
     * @param endDate   format as ：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String beginDate = "2019-05-01 00:00:00";
    public static String endDate = "2020-05-30 00:00:00";
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat getFormat() {
        return format;
    }

    public static Date randomDate(String beginDate, String endDate) {
        Date start = new Date(), end = new Date();
        try {
            start = format.parse(beginDate);
            end = format.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (start.getTime() >= end.getTime()) {
            return new Date(start.getTime());
        }

        long date = random(start.getTime(), end.getTime());

        return new Date(date);
    }

    public static String dateToString(Date date) {
        return format.format(date);
    }

    public static Date StringToDate(String str) {
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date LongToDate(long time) {
        return new Date(time);
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }


}
