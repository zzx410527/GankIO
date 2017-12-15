package com.zzx.gank.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private DateUtil() {

    }

    public static String formatDate(String date) {

        String dateFormat = null;
        try {
            dateFormat = date.substring(4, 6) + "月" + date.substring(6, 8) + "日";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormat;
    }

    public static String getTime(long date) {
        if (date <= 0) {
            return "";
        }
        String d = formatDateTime.format(date);

        return d;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return 年月日
     */
    public static String formatDate(Date date) {
        return formatDate.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return 年月日 时分秒
     */
    public static String formatDateTime(Date date) {
        return formatDateTime.format(date);
    }

    /**
     * 判断是不是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(date);
        return year == calendar.get(Calendar.YEAR)
                && month == calendar.get(Calendar.MONTH)
                && day == calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 将时间戳解析成日期
     *
     * @param timeInMillis
     * @return 年-月-日
     */
    public static String parseDate(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        return formatDate(date);
    }

    /**
     * 将时间戳解析成日期
     *
     * @param date
     * @return 年-月-日
     */
    public static String parseDate(Date date) {
        return formatDate(date);
    }

    /**
     * 将时间戳解析成日期
     *
     * @param timeInMillis
     * @return 年-月-日 时:分:秒
     */
    public static String parseDateTime(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        return formatDateTime(date);
    }

    /**
     * 解析日期
     *
     * @param date 年-月-日
     * @return
     */
    public static Date parseDate(String date) {
        Date mDate = null;
        try {
            mDate = formatDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mDate;
    }

    /**
     * 解析日期
     *
     * @param datetime
     * @return
     */
    public static Date parseDateTime(String datetime) {
        Date mDate = null;
        try {
            mDate = formatDateTime.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mDate;
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate) {
        Date time = parseDateTime(sdate);
        return friendlyTime(time);
    }

    /**
     * 以友好的方式显示时间
     *
     * @param date
     * @return
     */
    public static String friendlyTime(Date date) {

        if (date == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = formatDate.format(cal.getTime());
        String paramDate = formatDate.format(date);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - date.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = date.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - date.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - date.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = formatDate.format(date);
        }
        return ftime;
    }
}
