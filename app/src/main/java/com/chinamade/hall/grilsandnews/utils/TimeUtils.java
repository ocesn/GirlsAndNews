package com.chinamade.hall.grilsandnews.utils;


import com.chinamade.hall.grilsandnews.MainApplication;
import com.chinamade.hall.grilsandnews.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ivan on 2016/8/10.
 */
public class TimeUtils {

    public static String timeFormat(long time, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormat(long time) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatDate(long time) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatWeek(long time) {
        DateFormat formatter = new SimpleDateFormat("EEEE");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatMin(long time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatSecond(long time) {
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatTime(long time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatOnlySecond(long time) {
        DateFormat formatter = new SimpleDateFormat("ss");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatYear(long time) {
        DateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatMonth(long time) {
        DateFormat formatter = new SimpleDateFormat("MM");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatDay(long time) {
        DateFormat formatter = new SimpleDateFormat("dd");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String timeFormatYearDateCh(long time) {
        DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(time * 1000);
        return formatter.format(date);
    }

    public static String formatTime(long duration) {
        long sec = duration % 60;
        long min = (duration / 60) % 60;
        long hour = (duration / 60 / 60);
        if (hour > 0) {
            return String.format("%d:%d:%d", hour, min, sec);
        }
        if (min > 0) {
            return min + " min";
        }
        if (sec > 0) {
            return sec + " sec";
        }
        return "00";
    }

    public static String formatTimeDuration(long duration) {
        long sec = duration % 60;
        long min = (duration / 60) % 60;
        long hour = (duration / 60 / 60) % 24;
        if (hour > 0) {
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
        if (min > 0) {
            return String.format("%02d:%02d", min, sec);
        }
        if (sec > 0) {
            return String.format("%02d:%02d", 0, sec);
        }
        return "00";
    }

    public static String formatTimeDurationMin(long duration) {
        long min = (duration / 60) % 60;
        long hour = (duration / 60 / 60) % 24;
        if (hour > 0) {
            return String.format("%02d:%02d", hour, min);
        }
        if (min > 0) {
            return String.format("%02d:%02d", 0, min);
        }
        return "00:00";
    }

    public static String formatTimeDurationSec(long duration) {
        long sec = duration % 60;
        long min = (duration / 60) % 60;
        long hour = (duration / 60 / 60) % 24;
        if (hour > 0) {
            min += hour * 60;
        }
        if (duration > 0){
            return String.format("%02d:%02d", min, sec);
        }
        return "00:00";
    }

    public static String timeDifference(long startTime) {

        long timeLong = System.currentTimeMillis() / 1000 - startTime;
        if (timeLong < 60)
            return timeLong + MainApplication.getContext().getString(R.string.second_ago);
        else if (timeLong < 60 * 60) {
            timeLong = timeLong / 60;
            return timeLong + MainApplication.getContext().getString(R.string.minute_ago);
        } else if (timeLong < 60 * 60 * 24) {
            timeLong = timeLong / 60 / 60;
            return timeLong + MainApplication.getContext().getString(R.string.hour_ago);
        } else if (timeLong < 60 * 60 * 24 * 7) {
            timeLong = timeLong / 60 / 60 / 24;
            return timeLong + MainApplication.getContext().getString(R.string.day_ago);
        } else if (timeLong < 60 * 60 * 24 * 7 * 4) {
            timeLong = timeLong / 60 / 60 / 24 / 7;
            return timeLong + MainApplication.getContext().getString(R.string.week_ago);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return sdf.format(startTime * 1000);
        }
    }

}
