package com.sf.module.liblog.utils;


import android.support.annotation.NonNull;

import com.sf.module.liblog.BoxLogger;
import com.sf.module.liblog.strategy.LogConfigs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    // static SimpleDateFormat timeFmt;
    //
    // static SimpleDateFormat dateFmt;
    //
    // static SimpleDateFormat secondFmt;
    //
    // static SimpleDateFormat hourFmt;
    // static SimpleDateFormat photoFmt;
    // static {
    // timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
    // Locale.getDefault());
    // timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    //
    // dateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    // dateFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    //
    // secondFmt = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    // secondFmt.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    //
    // hourFmt = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    // hourFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    //
    // photoFmt = new SimpleDateFormat("yyyyMMddHHmmss");
    // photoFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    //
    // }

    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static int getMonth() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.MONTH);
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getDay() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getWeek() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取时
     *
     * @return
     */
    public static int getHour() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分
     *
     * @return
     */
    public static int getMinute() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     *
     * @return
     */
    public static int getSecond() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return c.get(Calendar.SECOND);
    }





    /**
     * 获取时间(包含日期)
     * 
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return timeFmt.format(new Date());
    }

    /**
     * 获取时间(包含日期格式photo)
     * 
     * @return
     */
    public static String getCurrentTime2() {
        SimpleDateFormat photoFmt = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        photoFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return photoFmt.format(new Date());
    }

    /**
     * 获取时间(包含日期)
     *
     * @return
     */
    public static String getCurrentTime3() {
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
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
    
    /**
     * 按照格式获取时间
     * 
     * @return HH:mm
     */
    public static String getCurrentTime(String template) {
        SimpleDateFormat hourFmt = new SimpleDateFormat(template, Locale.getDefault());
        hourFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String d = hourFmt.format(new Date());
        return d;
    }

    /**
     * 获取时间(包含日期)
     *
     * @return
     */
    public static long getCurrentTimeMillis(String currentTime) {
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try {
            return timeFmt.parse(currentTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L ;
    }

    /**
     * 转换支付时间
     *
     * @param oldPayTime
     * @return
     */
    public static String changePayTimeFormat(String oldPayTime) {
        String payTime;
        if (oldPayTime == null || oldPayTime.length() == 0) {
            payTime = TimeUtil.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        } else {
            try {
                if (oldPayTime.trim().length() == 14) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    payTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sdf.parse(oldPayTime));
                } else {
                    payTime = TimeUtil.format(Long.parseLong(oldPayTime), "yyyy-MM-dd HH:mm:ss");
                }
            } catch (Exception e) {
                payTime = TimeUtil.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
            }
        }
        return payTime;
    }


    /**
     * 日期加一天
     */
    public static String addCurrentDay() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        String currentTimeUp = sf.format(c.getTime()); // 增加后的时间
        return currentTimeUp;
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
     * 某个日期上 加 几天
     * @param startDate
     * @param days
     */
    public static String addDay(String startDate, int days) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        f.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try   {
            Date  d  =  new Date(f.parse(startDate).getTime() + days * 3600 * 24 * 1000);
            return  f.format(d);
        }
        catch(Exception e) {
            BoxLogger.wError("addDay 日期相加错误!", LogConfigs.Module.LOG, LogConfigs.Fun.LOG);
        }
        return "";
    }

    public static String addHour(int hour) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH", Locale.getDefault());
        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, hour);
        String currentTimeUp = dateTimeFormat.format(c.getTime()); // 增加后的时间
        currentTimeUp = currentTimeUp + ":00:00";
        return currentTimeUp;
    }

    /**
     * 将 hh:mm:ss 格式的时间转为秒
     * 
     * @param time
     * @return
     */
    public static long getSecond(String time) {
        String[] oo = time.split(":");
        long hour = Integer.parseInt(oo[0]);
        long min = Integer.parseInt(oo[1]);
        long s = Integer.parseInt(oo[2]);
        return s + min * 60 + hour * 60 * 60;
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

    /**
     * 
     * @Title: getDeltaT
     * @Description: 得到两个时间的差值 (days)
     * @param @param startDate 起始时间
     * @param @param endDate 截至时间
     * @return long 时间差
     * @throws
     */
    public static int getDeltaTimeDays(String startDate, String endDate) {
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        DateFormat df = dateFmt;
        try {
            Date d1 = df.parse(startDate);
            Date d2 = df.parse(endDate);
            long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
            int days = (int) (diff / (1000 * 60 * 60 * 24));
            return days;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取照片格式的日期差
     */
    public static int getDeltaTimeDaysPhoto(String startDate, String endDate) {
        SimpleDateFormat photoFmt = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        photoFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        DateFormat df = photoFmt;
        try {
            Date d1 = df.parse(startDate);
            Date d2 = df.parse(endDate);
            long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
            int days = (int) (diff / (1000 * 60 * 60 * 24));
            return days;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 
     * @Title: getDeltaT
     * @Description: 得到两个时间的差值(hours)
     * @param @param startDate 起始时间
     * @param @param endDate 截至时间
     * @return long 时间差
     * @throws
     */
    public static int getDeltaTimeHours(String startDate, String endDate) {
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        DateFormat df = timeFmt;
        try {
            Date d1 = df.parse(startDate);
            Date d2 = df.parse(endDate);
            long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
            int hours = (int) (diff / (1000 * 60 * 60));
            return hours;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @Title: getDeltaTimeHoursByMs
     * @param startDate 毫秒数
     * @param endDate
     * @return 小时数
     */
    public static int getDeltaTimeHoursByMs(String startDate, String endDate) {
        try {
            long diff = Long.parseLong(endDate) - Long.parseLong(startDate);
            int hours = (int) (diff / (1000 * 60 * 60));
            return hours;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @Description: 得到两个时间的差值(min)
     * @param @param startDate 起始时间
     * @param @param endDate 截至时间
     * @return long 时间差
     */
    public static long getDeltaTimeMins(String startDate, String endDate) {
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        DateFormat df = timeFmt;
        try {
            Date d1 = df.parse(startDate);
            Date d2 = df.parse(endDate);
            long diff = d2.getTime() - d1.getTime();
            long mins = (diff / (1000 * 60));
            return mins;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * @Description: 得到两个时间的差值(s)
     * @param startDate
     *            yyyy-MM-dd HH:mm:ss
     * @param endDate
     *            yyyy-MM-dd HH:mm:ss
     * @return String 时间差单位(s)
     * @throws ParseException
     */
    public static long getDeltaTimeSeconds(String startDate, String endDate) throws IllegalArgumentException {
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        DateFormat df = timeFmt;
        try {
            Date d1 = df.parse(startDate);
            Date d2 = df.parse(endDate);
            long diff = d2.getTime() - d1.getTime();
            return diff / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    /**
     * @Description: 得到两个时间的差值(ms)
     * @param startDate
     *            yyyy-MM-dd HH:mm:ss
     * @param endDate
     *            yyyy-MM-dd HH:mm:ss
     * @return long 时间差单位(ms)
     * @throws ParseException
     */
    public static long getDeltaTimeMills(String startDate, String endDate) throws IllegalArgumentException {
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        DateFormat df = timeFmt;
        try {
            Date d1 = df.parse(startDate);
            Date d2 = df.parse(endDate);
            long diff = d2.getTime() - d1.getTime();
//            if (diff < 0) {
//                throw new IllegalArgumentException("时间差为负");
//            }
            return diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @Description: 判断当前时间是否超出截至日期
     * @param currentDate 当前时间
     *            yyyy-MM-dd HH:mm:ss
     * @param endDate 截至时间
     *            yyyy-MM-dd HH:mm:ss
     * @return {@code true} 当前时间未到截至日期<br>
     *         {@code false} otherwise.
     */
    public static Boolean compareTime(String currentDate, String endDate) {
        boolean result = false;
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        DateFormat df = timeFmt;
        try {
            Date d1 = df.parse(currentDate);
            Date d2 = df.parse(endDate);
            long diff = d2.getTime() - d1.getTime();
            if (diff > 0) {
                result = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @Description: 判断当前时间是否超出截至日期
     * @param currentDate 当前时间
     *            yyyy-MM-dd HH:mm:ss
     * @param endDate 截至时间
     *            yyyy-MM-dd HH:mm:ss
     * @return {@code true} 当前时间未到截至日期<br>
     *         {@code false} otherwise.
     */
    public static boolean compareDate(Date currentDate, Date endDate) {
        boolean result = false;
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        DateFormat df = timeFmt;
        try {
            Date d1 = df.parse(df.format(currentDate));
            Date d2 = df.parse(df.format(endDate));
            long diff = d2.getTime() - d1.getTime();
            if (diff > 0) {
                result = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }



    /**
     * @Description: 判断当前时间是否超出截至日期
     * @param currentDate 当前时间
     *            yyyy-MM-dd
     * @param endDate 截至时间
     *            yyyy-MM-dd
     * @return {@code true} 当前时间未到截至日期<br>
     *         {@code false} otherwise.
     */
    public static boolean compareDate(String currentDate, String endDate) {
        boolean result = false;
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dateCurrent = timeFmt.parse(currentDate);
            Date dateEnd = timeFmt.parse(endDate);
            if(dateEnd.getTime()- dateCurrent.getTime()>= 0){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }



    public static String getCurrentMs() {
        SimpleDateFormat hourFmt = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        hourFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String d = hourFmt.format(new Date());
        return d;
    }

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date dateValue = timeFmt.parse(dateString, position);
        return dateValue;
    }

    public static Date stringToDate2(String dateString){
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat timeFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        timeFmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date dateValue = timeFmt.parse(dateString, position);
        return dateValue;
    }

    /**
     * 格式化时间戳.
     *
     * @param millis 时间戳
     * @param fmt    时间格式
     * @return 格式化时间文本
     */
    public static String format(long millis, @NonNull String fmt) {
        return android.text.format.DateFormat.format(fmt, millis).toString();
    }

    /**
     * 将输入时间文本格式化为其他格式的文本.
     *
     * @param inDateText 输入时间文本
     * @param inFmt      输入时间格式
     * @param outFmt     输出时间格式
     * @return 格式化后的时间文本
     * @throws ParseException 时间转换异常
     */
    public static String format(@NonNull String inDateText, @NonNull String inFmt, @NonNull String outFmt)
            throws ParseException {
        long millis = parseMillis(inDateText, inFmt);
        return format(millis, outFmt);
    }

    /**
     * 把时间文本转换成时间戳.
     *
     * @param dateText 时间文本
     * @param fmt      时间格式
     * @return 时间戳
     */
    public static long parseMillis(@NonNull String dateText, @NonNull String fmt) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.getDefault());
        Date date = sdf.parse(dateText);
        return date.getTime();
    }
}
