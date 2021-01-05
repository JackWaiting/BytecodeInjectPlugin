package com.sf.module.liblog.utils;

import android.text.TextUtils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日志文件时间格式
 * Created by 01383436
 * on 2019.01.29 14:36
 */
public class FileTimeUtil {

    /**
     * 片段间隔，单位小时，24%LOG_SEGMENT=0.
     */
    public static final int LOG_SEGMENT = 1;


    public static String getCurrentTime() {
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return timeFmt.format(new Date());
    }

    /**
     * 获取日期
     * 
     * @return
     */
    public static String getCurrentDay() {
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String d = dateFmt.format(new Date());
        return d;
    }


    /**
     * 当前时间段.
     *
     * @return  比如“0001”表示00:00-01:00
     */
    public static String getHourSegment() {
        int delta = LOG_SEGMENT;
        int bef, aft;

        //小时
        int h = TimeUtil.getHour(TimeUtil.getCurrentHour());
        //小时
        bef = h - h % delta;
        aft = bef + delta;
        if (aft == 24)
            aft = 0;

        return getMatchNumStr(bef) + getMatchNumStr(aft);
    }

    /**
     * 前一个时间段.
     *
     * @param hourSegment 参考时间
     * @return 比如“0001”表示00:00-01:00
     */
    public static String getPreHourSegment(String hourSegment) {
        int delta = LOG_SEGMENT;
        String bef = hourSegment.substring(0, 2);
        String aft = bef;

        if (TextUtils.equals("00", bef)) {
            bef = getMatchNumStr(24 - delta);
        } else {
            bef = getMatchNumStr(Integer.valueOf(bef) - delta);
        }

        return bef + aft;
    }

    /**
     * 对于1-9小时的数值进行前置补0
     * @param num 小时
     * @return num在[0, 9]时前置补0, num在[10, 23]返回原值
     */
    private static String getMatchNumStr(int num) {
        return 10 > num ? "0" + num : "" + num;
    }


    /**
     * 获取当前时分秒
     *
     * @return HH:mm:ss
     */
    public static String getCurrentHour() {
        SimpleDateFormat hourFmt = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        hourFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String d = hourFmt.format(new Date());
        return d;
    }



    public static String addDay(int days) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, days);
        String currentTimeUp = dateTimeFormat.format(c.getTime()); // 增加后的时间
        return currentTimeUp;
    }

    /**
     * 将 hh:mm:ss 格式的时间转为时
     *
     * @param time
     * @return
     */
    public static int getHour(String time) {
        String[] oo = time.split(":");
        int hour = Integer.parseInt(oo[0]);
        return hour;
    }

}
