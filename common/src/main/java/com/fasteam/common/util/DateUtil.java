package com.fasteam.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:  com.fasteam.common.util
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public final class DateUtil {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {
    }

    public static String long2NewDateString(long dateLong, String sdf) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        return simpleDateFormat.format(long2Date(dateLong));
    }

    public static Date long2Date(long dateLong) {
        try {
            return new Date(dateLong);
        } catch (Exception var3) {
            LOG.error("时间转换出错！" + var3.getMessage());
            return null;
        }
    }

    public static String getDefaultSearchTime() {
        Calendar calendar = Calendar.getInstance();
        int beforeYear = 1;
        calendar.set(1, calendar.get(1) - beforeYear);
        SimpleDateFormat fordate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String calendarStr = fordate.format(calendar.getTime());
        return calendarStr;
    }

    public static Date preMonthFirstDate(Date date) {
        int month = getMonth(date);
        int year = getYear(date);
        --month;
        if (month == 0) {
            month = 12;
            --year;
        }

        String strDate = year + "-" + month + "-1";
        return getDate(strDate);
    }

    public static Date preMonthLastDate(Date date) {
        int month = getMonth(date);
        int year = getYear(date);
        --month;
        if (month == 0) {
            month = 12;
            --year;
        }

        int[] daysInMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String strDate = year + "-" + month + "-" + daysInMonth[month - 1];
        return getDate(strDate);
    }

    public static Date translateStrToDate(String str, String patten) {
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(patten);

            try {
                Date date = format.parse(str);
                return date;
            } catch (ParseException var5) {
                LOG.error("时间转换出错！");
                return null;
            }
        }
    }

    public static Date getTime(String value) {
        Date date = null;
        if (value == null) {
            return new Date();
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date = format.parse(value);
            } catch (ParseException var4) {
                LOG.error("DateUtil getTime error!", var4);
            }

            return date;
        }
    }

    public static Date getDate(String value) {
        Date date = null;
        if (value == null) {
            return new Date();
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                date = format.parse(value);
                return date;
            } catch (ParseException var4) {
                LOG.error("DateUtil getDate error!", var4);
                return null;
            }
        }
    }

    public static Date getDateTwo(String value) {
        Date date = null;
        if (value == null) {
            return new Date();
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date = format.parse(value);
                return date;
            } catch (ParseException var4) {
                LOG.error("DateUtil getDateTwo error!", var4);
                return null;
            }
        }
    }

    public static Date getDate(String value, String fmt) {
        Date date = null;
        if (value == null) {
            return new Date();
        } else {
            SimpleDateFormat format = new SimpleDateFormat(fmt);

            try {
                date = format.parse(value);
                return date;
            } catch (ParseException var5) {
                LOG.error("DateUtil getDate error(value,fmt)!", var5);
                return null;
            }
        }
    }

    public static Date getDate2(String value) {
        Date date = null;
        if (value == null) {
            return new Date();
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

            try {
                date = format.parse(value);
                return date;
            } catch (ParseException var4) {
                LOG.error("DateUtil getDate2 error!", var4);
                return null;
            }
        }
    }

    public static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static Date preDate(Date date) {
        long time = date.getTime();
        time -= 86400000L;
        Date ytd = new Date(time);
        return ytd;
    }

    public static Date yesterday() {
        Date now = new Date();
        long time = now.getTime();
        time -= 86400000L;
        Date ytd = new Date(time);
        return ytd;
    }

    public static String yesterdayFromZ() {
        Date date = new Date();
        long time = date.getTime();
        time -= 86400000L;
        Date ytd = new Date(time);
        SimpleDateFormat fordate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = fordate.format(ytd);
        timeStr = timeStr.substring(0, timeStr.lastIndexOf("-") + 4);
        timeStr = timeStr + " 00:00:00";
        return timeStr;
    }

    public static String recent7Day() {
        Date now = new Date();
        long time = now.getTime();
        time -= 604800000L;
        Date ytd = new Date(time);
        SimpleDateFormat fordate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = fordate.format(ytd);
        timeStr = timeStr.substring(0, timeStr.lastIndexOf("-") + 4);
        timeStr = timeStr + " 00:00:00";
        return timeStr;
    }

    public static Date nextDate(Date date) {
        long time = date.getTime();
        time += 86400000L;
        Date tomorrow = new Date(time);
        return tomorrow;
    }

    public static String getDateBefore(Date date, int millisecond) {
        long time = date.getTime();
        time -= (long)millisecond;
        Date newDate = new Date(time);
        SimpleDateFormat fordate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fordate.format(newDate);
    }

    public static Date tomorrow() {
        Date now = new Date();
        long time = now.getTime();
        time += 86400000L;
        Date tomorrow = new Date(time);
        return tomorrow;
    }

    public static Date recentNDay(int dayNum) {
        Date now = new Date();
        long time = now.getTime();
        time += (long)(dayNum * 24 * 60 * 60 * 1000);
        Date date = new Date(time);
        return getMorningTime(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        if (date == null) {
            return null;
        } else {
            String dateString = bartDateFormat.format(date);
            return dateString;
        }
    }

    public static String formatDate(String oldDate, String format) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        if (oldDate == null) {
            return "";
        } else {
            SimpleDateFormat bartDateFormatNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = null;

            try {
                dateString = bartDateFormatNew.format(bartDateFormat.parse(oldDate));
            } catch (ParseException var6) {
                LOG.error("DateUtil formatDate error!");
            }

            return dateString;
        }
    }

    public static String getYearMonth() {
        String dateStr = formatDate(new Date(), "yyyy.MM");
        return dateStr;
    }

    public static String getFormatDate(String format) {
        String dateStr = formatDate(new Date(), format);
        return dateStr;
    }

    public static Date getDateFromMonth(String value) {
        Date date = null;
        DateFormat df = DateFormat.getDateInstance(3, Locale.CHINA);
        Date d = null;
        if (value == null) {
            return new Date();
        } else {
            try {
                d = df.parse(value);
            } catch (ParseException var7) {
                try {
                    d = df.parse("1900-1-1");
                } catch (ParseException var6) {
                    LOG.error("DateUtil getDateFromMonth error!", var7);
                }
            }

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(d);
            date = new java.sql.Date(calendar.getTimeInMillis());
            return date;
        }
    }

    public static String getYearStr(Date date) {
        String year = formatDate(date, "yyyy");
        return year;
    }

    public static int getYear(Date date) {
        String a = formatDate(date, "yyyy");
        int b = Integer.parseInt(a);
        return b;
    }

    public static String getDayStr(Date date) {
        String a = formatDate(date, "dd");
        return a;
    }

    public static int getDay(Date date) {
        String a = formatDate(date, "dd");
        int b = Integer.parseInt(a);
        return b;
    }

    public static int getHour(Date date) {
        String a = formatDate(date, "HH");
        int b = Integer.parseInt(a);
        return b;
    }

    public static String getMonthStr(Date date) {
        String a = formatDate(date, "MM");
        return a;
    }

    public static int getMonth(Date date) {
        String a = formatDate(date, "MM");
        int b = Integer.parseInt(a);
        return b;
    }

    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(5, now.get(5) + day);
        return now.getTime();
    }

    public static Date getDateBeforeDay(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(5, now.get(5) - day);
        return now.getTime();
    }

    public static String getDateTime(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(date);
    }

    public static String getDateForStr(Date date) {
        if (date == null) {
            return "";
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.format(date);
        }
    }

    public static String getDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(date);
    }

    public static Date getTonightTime(Date date) {
        String formatPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        String timeStr = format.format(date);
        timeStr = timeStr.substring(0, timeStr.lastIndexOf("-") + 4);
        timeStr = timeStr + " 23:59:59";
        return translateStrToDate(timeStr, formatPattern);
    }

    public static Date getMorningTime(Date date) {
        String formatPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        String timeStr = format.format(date);
        timeStr = timeStr.substring(0, timeStr.lastIndexOf("-") + 4);
        timeStr = timeStr + " 00:00:00";
        return translateStrToDate(timeStr, formatPattern);
    }

    public static String getMorningTimeStr() {
        Date now = new Date();
        String nowStr = getDateForStr(now);
        String subStr = nowStr.substring(0, nowStr.lastIndexOf("-") + 4);
        subStr = subStr + " 00:00:00";
        return subStr;
    }

    public static Integer between(Date start, Date end) {
        if (start.getTime() >= end.getTime()) {
            return 0;
        } else {
            int i = 1;
            start = getDate(formatDate(start));

            for(end = getDate(formatDate(end)); start.getTime() < end.getTime(); ++i) {
                start.setTime(start.getTime() + 86400000L);
            }

            return i;
        }
    }

    public static Integer betweenSecond(Date start, Date end) {
        if (start.getTime() >= end.getTime()) {
            return 0;
        } else {
            int i;
            for(i = 1; start.getTime() < end.getTime(); ++i) {
                start.setTime(start.getTime() + 1000L);
            }

            return i;
        }
    }

    public static Integer getNowDiff24() {
        Date start = new Date();
        Date end = getTonightTime(start);
        if (start.getTime() >= end.getTime()) {
            return 0;
        } else {
            int i;
            for(i = 1; start.getTime() < end.getTime(); ++i) {
                start.setTime(start.getTime() + 1000L);
            }

            return i;
        }
    }

    public static Date getLastWeekFirst(Date date) {
        return null;
    }

    public static long getHttpTime() {
        long time = 0L;

        try {
            URL url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();
            uc.connect();
            time = uc.getDate();
        } catch (Exception var4) {
            time = (new Date()).getTime();
        }

        return time;
    }

    public static List<Date> dateToWeek(Date mdate) {
        int b = mdate.getDay();
        List<Date> list = new ArrayList();
        Long fTime = 0L;
        if (b == 0) {
            fTime = mdate.getTime() - 518400000L;
        } else {
            fTime = mdate.getTime() - (long)((b - 1) * 24 * 3600000);
        }

        for(int a = 0; a <= 6; ++a) {
            Date fdate = new Date();
            fdate.setTime(fTime + (long)(a * 24 * 3600000));
            list.add(a, fdate);
        }

        return list;
    }

    public static Date getThisWeekFirst() {
        Date currentDate = new Date();
        List<Date> days = dateToWeek(currentDate);
        return (Date)days.get(0);
    }

    public static Integer time2second(String timeStr) {
        if (timeStr == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

            try {
                Date time = sdf.parse(timeStr);
                return time.getHours() * 60 * 60 + time.getMinutes() * 60 + time.getSeconds();
            } catch (ParseException var3) {
                LOG.error("DateUtil getSecond error!", var3);
                return null;
            }
        }
    }

    public static void main(String[] args) throws ParseException {
    }
}
