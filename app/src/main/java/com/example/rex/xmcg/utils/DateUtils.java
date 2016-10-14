package com.example.rex.xmcg.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

    /**
     * 获取时间戳
     */
    public static long formatDateStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeStemp = 0;
        try {
            Date date = simpleDateFormat.parse(time);
            timeStemp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStemp;
    }

    /**
     * 获取时间戳（年月日）
     */
    public static long formatYearStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long timeStemp = 0;
        try {
            Date date = simpleDateFormat.parse(time);
            timeStemp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStemp;
    }

    /**
     * 获取时间戳（年月日）
     */
    public static String getYmdStr(long time) {
        SimpleDateFormat monthDayTimeFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(time);
        String strTime = monthDayTimeFormat.format(date);
        date = null;
        return strTime;
    }

    //获取当前时间与星期几
    public static String getYmdwStr(){
        String mYear,mMonth,mDay,mWay;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return mYear + "年" + mMonth + "月" + mDay+"日"+"/星期"+mWay;
    }

    //获取星期几
    public static String getWeek(long time){
        String mWay;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTimeInMillis(time);
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return "星期"+mWay;
    }

    /**
     * 将时间戳转成月-日格式
     */
    public static String getMonDay(long time) {
        SimpleDateFormat monthDayTimeFormat = new SimpleDateFormat("MM-dd");
        Date date = new Date(time);
        String strTime = monthDayTimeFormat.format(date);
        date = null;
        return strTime;
    }

    /**
     * 将时间戳转成年-月-日-时-分格式
     */
    public static String getYearMonthDayHourMinute(long time) {
        SimpleDateFormat monthDayTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        String strTime = monthDayTimeFormat.format(date);
        date = null;
        return strTime;
    }

    /**
     * 将时间戳转成时-分格式
     */
    public static String getHourMinute(long time) {
        SimpleDateFormat monthDayTimeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(time);
        String strTime = monthDayTimeFormat.format(date);
        date = null;
        return strTime;
    }

    /**
     * 将时间戳转成年-月-日-时-分-秒格式
     */
    public static String getAll(long time) {
        SimpleDateFormat monthDayTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String strTime = monthDayTimeFormat.format(date);
        date = null;
        return strTime;
    }

    /**
     * 年月日时分字符串转换成时间戳
     */
    public static long getTimeStamp(String time) {
        SimpleDateFormat fulTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long l = 0;
        try {
            Date d = fulTimeFormat.parse(time);
            l = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 通过时分字符串，获取当天时分的时间戳
     */
    public static long getTimeStampToday(String time) {

        String[] time_split = time.split(":");
        if (time_split.length < 2)
            return 0;

        int hour = Integer.parseInt(time_split[0]);
        int minute = Integer.parseInt(time_split[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        return cal.getTimeInMillis();
    }

    /**
     * 年月日时分字符串转换成Date
     */
    public static Date getDateFromStr(String time) {
        SimpleDateFormat fulTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = fulTimeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 年月日字符串转换成Date
     */
    public static Date getDateFromStr2(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Date对象转化为年月日时分字符串
     */
    public static String getTimeFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * Date对象转化为月日时分字符串
     */
    public static String getTimeFromDate2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日HH时mm分");
        return format.format(date);
    }

    /**
     * 时间戳转化为月日时分
     */
    public static String getTimeFromStamp(long time) {
        SimpleDateFormat monthDayTimeFormat = new SimpleDateFormat("MM月dd日HH时mm分");
        Date date = new Date(time);
        String strTime = monthDayTimeFormat.format(date);
        date = null;
        return strTime;
    }

    /**
     * 时间戳转化为月日时分
     */
    public static String getTimeFromStamp2(long time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH时mm分");
        Date date = new Date(time);
        String strTime = timeFormat.format(date);
        date = null;
        return strTime;
    }
}
