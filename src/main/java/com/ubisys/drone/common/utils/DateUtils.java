package com.ubisys.drone.common.utils;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 类描述：时间操作定义类
 *
 * @version 1.0
 * @author: yinzhaohua @date： 日期：2012-12-8 时间：下午12:15:03
 */
public class DateUtils extends PropertyEditorSupport {

    // 各种时间格式
    public static final SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat date_sdf_wz = new SimpleDateFormat("yyyy年MM月dd日");
    public static final SimpleDateFormat time_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat yyyymmdd_hhmmss = new SimpleDateFormat("yyyyMMdd HHmmss");
    public static final SimpleDateFormat yyyy_MM_ddHHmmss = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");

    public static final SimpleDateFormat short_time_sdf = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat HHmmss = new SimpleDateFormat("HHmmss");
    // 以毫秒表示的时间""
    private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
    private static final long HOUR_IN_MILLIS = 3600 * 1000;
    private static final long MINUTE_IN_MILLIS = 60 * 1000;
    private static final long SECOND_IN_MILLIS = 1000;

    // 指定模式的时间格式
    private static SimpleDateFormat getSDFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 当前日历，这里用中国时间表示
     *
     * @return 以当地时区表示的系统当前日历
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 指定毫秒数表示的日历
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日历
     */
    public static Calendar getCalendar(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        return cal;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getDate
    // 各种方式获取的Date
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 当前日期
     *
     * @return 系统当前时间
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 指定毫秒数表示的日期
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日期
     */
    public static Date getDate(long millis) {
        return new Date(millis);
    }

    /**
     * 时间戳转换为字符串
     *
     * @param time
     * @return
     */
    public static String timestamptoStr(Timestamp time) {
        /*
         * Date date = null; if (null != time) { date = new
		 * Date(time.getTime()); }
		 */
        return date2Str(date_sdf);
    }

    /**
     * 字符串转换时间戳
     *
     * @param str
     * @return
     */
    public static Timestamp str2Timestamp(String str) {
        Date date = str2Date(str, date_sdf);
        return new Timestamp(date.getTime());
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @param sdf
     * @return
     */
    public static Date str2Date(String str, SimpleDateFormat sdf) {
        if (null == str || "".equals(str)) {
            return null;
        }
        Date date = null;
        try {
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转换为字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return 字符串
     */
    public static String date2Str(SimpleDateFormat date_sdf) {
        Date date = getDate();
        if (null == date) {
            return null;
        }
        return date_sdf.format(date);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateformat(String date, String format) {
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        Date _date = null;
        try {
            _date = sformat.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sformat.format(_date);
    }

    /**
     * 日期转换为字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return 字符串
     */
    public static String date2Str(Date date, SimpleDateFormat date_sdf) {
        if (null == date) {
            return null;
        }
        return date_sdf.format(date);
    }

    /**
     * 日期转换为字符串
     *
     * @param date   日期
     * @param format 日期格式
     * @return 字符串
     */
    public static String getDate(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 指定毫秒数的时间戳
     *
     * @param millis 毫秒数
     * @return 指定毫秒数的时间戳
     */
    public static Timestamp getTimestamp(long millis) {
        return new Timestamp(millis);
    }

    /**
     * 以字符形式表示的时间戳
     *
     * @param time 毫秒数
     * @return 以字符形式表示的时间戳
     */
    public static Timestamp getTimestamp(String time) {
        return new Timestamp(Long.parseLong(time));
    }

    /**
     * 系统当前的时间戳
     *
     * @return 系统当前的时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 指定日期的时间戳
     *
     * @param date 指定日期
     * @return 指定日期的时间戳
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 指定日历的时间戳
     *
     * @param cal 指定日历
     * @return 指定日历的时间戳
     */
    public static Timestamp getCalendarTimestamp(Calendar cal) {
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp gettimestamp() {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(dt);
        Timestamp buydate = Timestamp.valueOf(nowTime);
        return buydate;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getMillis
    // 各种方式获取的Millis
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 系统时间的毫秒数
     *
     * @return 系统时间的毫秒数
     */
    public static long getMillis() {
        return new Date().getTime();
    }

    /**
     * 指定日历的毫秒数
     *
     * @param cal 指定日历
     * @return 指定日历的毫秒数
     */
    public static long getMillis(Calendar cal) {
        // --------------------return cal.getTimeInMillis();
        return cal.getTime().getTime();
    }

    /**
     * 指定日期的毫秒数
     *
     * @param date 指定日期
     * @return 指定日期的毫秒数
     */
    public static long getMillis(Date date) {
        return date.getTime();
    }

    /**
     * 指定时间戳的毫秒数
     *
     * @param ts 指定时间戳
     * @return 指定时间戳的毫秒数
     */
    public static long getMillis(Timestamp ts) {
        return ts.getTime();
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatDate
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月-日
     *
     * @return 默认日期按“年-月-日“格式显示
     */
    public static String formatDate() {
        return date_sdf.format(getCalendar().getTime());
    }

    /**
     * 获取时间字符串
     */
    public static String getDataString(SimpleDateFormat formatstr) {
        return formatstr.format(getCalendar().getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     *
     * @param cal 指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(Calendar cal) {
        return date_sdf.format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     *
     * @param date 指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(Date date) {
        return date_sdf.format(date);
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日“格式显示
     */
    public static String formatDate(long millis) {
        return date_sdf.format(new Date(millis));
    }

    /**
     * 默认日期按指定格式显示
     *
     * @param pattern 指定的格式
     * @return 默认日期按指定格式显示
     */
    public static String formatDate(String pattern) {
        return getSDFormat(pattern).format(getCalendar().getTime());
    }

    /**
     * 指定日期按指定格式显示
     *
     * @param cal     指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Calendar cal, String pattern) {
        return getSDFormat(pattern).format(cal.getTime());
    }

    /**
     * 指定日期按指定格式显示
     *
     * @param date    指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Date date, String pattern) {
        return getSDFormat(pattern).format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatTime
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
     *
     * @return 默认日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime() {
        return time_sdf.format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(long millis) {
        return time_sdf.format(new Date(millis));
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param cal 指定的日期
     * @return 指定日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(Calendar cal) {
        return time_sdf.format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param date 指定的日期
     * @return 指定日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(Date date) {
        return time_sdf.format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatShortTime
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：时：分
     *
     * @return 默认日期按“时：分“格式显示
     */
    public static String formatShortTime() {
        return short_time_sdf.format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：时：分
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“时：分“格式显示
     */
    public static String formatShortTime(long millis) {
        return short_time_sdf.format(new Date(millis));
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     *
     * @param cal 指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(Calendar cal) {
        return short_time_sdf.format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     *
     * @param date 指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(Date date) {
        return short_time_sdf.format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // parseDate
    // parseCalendar
    // parseTimestamp
    // 将字符串按照一定的格式转化为日期或时间
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException
     * @throws AIDateFormatException
     */
    public static Date parseDate(String src, String pattern) throws ParseException {
        return getSDFormat(pattern).parse(src);

    }

    /**
     * 将Date转成指定格式的Date返回
     *
     * @param date
     * @param format
     * @return
     */
    public static Date formatDate(Date date, SimpleDateFormat format) {
        return str2Date(date2Str(getDate(), format), format);
    }

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException
     * @throws AIDateFormatException
     */
    public static Calendar parseCalendar(String src, String pattern) throws ParseException {

        Date date = parseDate(src, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String formatAddDate(String src, String pattern, int amount) throws ParseException {
        Calendar cal;
        cal = parseCalendar(src, pattern);
        cal.add(Calendar.DATE, amount);
        return formatDate(cal);
    }

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的时间戳
     * @throws ParseException
     * @throws AIDateFormatException
     */
    public static Timestamp parseTimestamp(String src, String pattern) throws ParseException {
        Date date = parseDate(src, pattern);
        return new Timestamp(date.getTime());
    }

    // ////////////////////////////////////////////////////////////////////////////
    // dateDiff
    // 计算两个日期之间的差值
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 计算两个时间之间的差值，根据标志的不同而不同
     *
     * @param flag   计算标志，表示按照年/月/日/时/分/秒等计算
     * @param calSrc 减数
     * @param calDes 被减数
     * @return 两个日期之间的差值
     */
    public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {

        long millisDiff = getMillis(calSrc) - getMillis(calDes);

        if (flag == 'y') {
            return (calSrc.get(Calendar.YEAR) - calDes.get(Calendar.YEAR));
        }

        if (flag == 'd') {
            return (int) (millisDiff / DAY_IN_MILLIS);
        }

        if (flag == 'h') {
            return (int) (millisDiff / HOUR_IN_MILLIS);
        }

        if (flag == 'm') {
            return (int) (millisDiff / MINUTE_IN_MILLIS);
        }

        if (flag == 's') {
            return (int) (millisDiff / SECOND_IN_MILLIS);
        }

        return 0;
    }


    /**
     * 计算2个日期的差
     *
     * @param flag
     * @param strDate
     * @param endDate
     * @return
     */
    public static int dateDiff(char flag, Date strDate, Date endDate) {

        long millisDiff = endDate.getTime() - strDate.getTime();


        if (flag == 'd') {
            return (int) (millisDiff / DAY_IN_MILLIS);
        }

        if (flag == 'h') {
            return (int) (millisDiff / HOUR_IN_MILLIS);
        }

        if (flag == 'm') {
            return (int) (millisDiff / MINUTE_IN_MILLIS);
        }

        if (flag == 's') {
            return (int) (millisDiff / SECOND_IN_MILLIS);
        }

        return 0;
    }


    /**
     * 计算个日期的差值 分钟的时候。有余数+1
     *
     * @param flag
     * @param sdate 开始时间
     * @param edate 结束时间
     * @return
     * @throws ParseException 转换异常
     */
    public static long getDatePoor(char flag, String sdate, String edate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = df.parse(sdate);
        Date endDate = df.parse(edate);
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();

        if (flag == 'd') {
            // 计算差多少天
            long day = diff / nd;
            return day;
        }

        if (flag == 'h') {
            // 计算差多少小时
            long hour = diff / nh;
            return hour;
        }

        if (flag == 'm') {
            // 计算差多少分钟
            long min = diff / nm;
            if (diff % nm == 0) {
                return min;
            } else {
                return min + 1;
            }
        }

        if (flag == 's') {
            // 计算差多少秒//输出结果
            long sec = diff / ns;
            return sec;
        }
        return 0l;
    }

    /**
     * 计算个日期的差值 分钟的时候。有余数+1
     *
     * @param flag
     * @param sdate 开始时间
     * @param edate 结束时间
     * @return
     * @throws ParseException 转换异常
     */
    public static long getDatePoor2(char flag, String sdate, String edate) throws ParseException {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date startDate = df.parse(sdate);
        Date endDate = df.parse(edate);
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();

        if (flag == 'd') {
            // 计算差多少天
            long day = diff / nd;
            return day;
        }

        if (flag == 'h') {
            // 计算差多少小时
            long hour = diff / nh;
            return hour;
        }

        if (flag == 'm') {
            // 计算差多少分钟
            long min = diff / nm;
            if (diff % nm == 0) {
                return min;
            } else {
                return min + 1;
            }
        }

        if (flag == 's') {
            // 计算差多少秒//输出结果
            long sec = diff / ns;
            return sec;
        }
        return 0l;
    }

    /**
     * String类型 转换为Date, 如果参数长度为10 转换格式”yyyy-MM-dd“ 如果参数长度为19 转换格式”yyyy-MM-dd
     * HH:mm:ss“ * @param text String类型的时间值
     */
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            try {
                if (text.indexOf(":") == -1 && text.length() == 10) {
                    setValue(date_sdf.parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 19) {
                    setValue(datetimeFormat.parse(text));
                } else {
                    throw new IllegalArgumentException("Could not parse date, date format is error ");
                }
            } catch (ParseException ex) {
                IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
                iae.initCause(ex);
                throw iae;
            }
        } else {
            setValue(null);
        }
    }

    public static int getYear() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(getDate());
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取所给日期减去天数的时间
     *
     * @param startDate 起始时间
     * @param numdate   减去的天数
     * @return 所得的时间
     * @throws ParseException
     */
    public static Date dateReduceDays(Date startDate, int numdate, String pattern) {
        Date endDate;
        try {
            SimpleDateFormat dft = new SimpleDateFormat(pattern);
            Calendar date = Calendar.getInstance();
            date.setTime(startDate);
            date.set(Calendar.DATE, date.get(Calendar.DATE) - numdate);

            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return endDate;
    }

    /**
     * @Description: TODO(计算两个日期【字符串类型】之间的时间距离) @param @param
     * sdate @param @param bdate @param @return 设定文件 @throws
     */
    public static Map<String, Long> timesBetween(String sdate, String bdate) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long day = 0;

        long hour = 0;

        long min = 0;

        long sec = 0;

        long diff = 0;

        try {

            Date startDate = df.parse(sdate);

            Date bindDate = df.parse(bdate);

            long stime = startDate.getTime();

            long btime = bindDate.getTime();

            if (stime > btime) {

                diff = stime - btime;

            } else {

                diff = btime - stime;

            }

            day = diff / (24 * 60 * 60 * 1000);

            hour = diff / (60 * 60 * 1000) - day * 24;

            min = diff / (60 * 1000) - day * 24 * 60 - hour * 60;

            sec = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;

        } catch (ParseException e) {

            e.printStackTrace();

        }

        Map<String, Long> timeMap = new HashMap<String, Long>();

        timeMap.put("Day", day);

        timeMap.put("Hour", hour);

        timeMap.put("Min", min);

        timeMap.put("Sec", sec);

        return timeMap;

    }

    /**
     * 计算2个日期的差值，返回 XX天XX小时XX分XX秒
     *
     * @param startDate 开始时间
     * @param bindDate  结束时间
     * @return
     */
    public static String timesBetweenToString(Date startDate, Date bindDate) {

        long day = 0;

        long hour = 0;

        long min = 0;

        long sec = 0;

        long diff = 0;

        long stime = startDate.getTime();

        long btime = bindDate.getTime();

        if (stime > btime) {

            diff = stime - btime;

        } else {

            diff = btime - stime;

        }

        day = diff / (24 * 60 * 60 * 1000);

        hour = diff / (60 * 60 * 1000) - day * 24;

        min = diff / (60 * 1000) - day * 24 * 60 - hour * 60;

        sec = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;

        StringBuffer sb = new StringBuffer();
        if (day != 0) {
            sb.append(day + "天").append(hour + "时").append(min + "分").append(sec + "秒");
        } else if (hour != 0) {
            sb.append(hour + "时").append(min + "分").append(sec + "秒");
        } else if (min != 0) {
            sb.append(min + "分").append(sec + "秒");
        } else if (sec != 0) {
            sb.append(sec + "秒");
        }
        return sb.toString();

    }


    /***
     * 将毫秒数转换为  时分秒
     * @param diff
     * @return
     */
    public static String timesBetweenByLong(Long diff) {

        if (diff == 0) {
            return " ";
        }

        long day = 0;

        long hour = 0;

        long min = 0;

        long sec = 0;


        day = diff / (24 * 60 * 60 * 1000);

        hour = diff / (60 * 60 * 1000) - day * 24;

        min = diff / (60 * 1000) - day * 24 * 60 - hour * 60;

        sec = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;

        StringBuffer sb = new StringBuffer();
        if (day != 0) {
            sb.append(day + "天").append(hour + "时").append(min + "分").append(sec + "秒");
        } else if (hour != 0) {
            sb.append(hour + "时").append(min + "分").append(sec + "秒");
        } else if (min != 0) {
            sb.append(min + "分").append(sec + "秒");
        } else if (sec != 0) {
            sb.append(sec + "秒");
        }
        return sb.toString();

    }


    /**
     * 判断时间是否在时间段内   不跨天
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断时间是否在时间段内
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * 跨天
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar2(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(end) && date.before(begin)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        System.out.println(timesBetweenByLong(2567000l));
    }


}