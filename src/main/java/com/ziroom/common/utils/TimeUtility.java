package com.ziroom.common.utils;

import com.google.common.collect.Lists;
import com.ziroom.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 时间转换工具类
 *
 * @author gt
 **/
public class TimeUtility {

    private static Logger logger = LoggerFactory.getLogger(TimeUtility.class);

    public static String TIME_FORMAT_YYYY_MM_DD2 = "yyyy/MM/dd";
    public static String TIME_FORMAT_YYYY_MM_DD_HH = "yyyy/MM/dd/HH";
    public static String TIME_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static String TIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String TIME_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static String TIME_FORMAT_YYYYMM = "yyyyMM";
    public static String TIME_FORMAT_YYYY_MM = "yyyy/MM";
    public static String TIME_FORMAT_YYYY_MM2 = "yyyy-MM";
    public static String TIME_FORMAT_HH_MM_SS = "HH:mm:ss";
    public static String TIME_FORMAT_HHMMSS = "HHmmss";
    public static String TIME_FORMAT_YYYYMMDDHHMMss = "yyyyMMddHHmmss";
    public static String TIME_FORMAT_YMD = "yyyy年MM月dd日";

    /**
     * 获取指定月份的天数
     *
     * @param date       日期
     * @param dateFormat 日期格式
     * @return 天数
     */
    public static int getDaysOfMonth(String date, String dateFormat) throws ParseException {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(date));
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 格式化时间 yyyy-MM-dd HH:mm:ss
     *
     * @param time 需要格式化的date对象
     * @return 格式化后的字符串
     */
    public static String formatTimeStr(Object time) {
        //想要得到的日期显示格式  
        DateFormat to_type = new SimpleDateFormat(TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);
        String str = to_type.format(time);
        return str;
    }

    /**
     * 格式化时间
     *
     * @param time   需要格式化的date对象
     * @param format 时间格式
     * @return 格式化后的字符串
     */
    public static String formatTimeStr(Object time, String format) {
        DateFormat to_type = new SimpleDateFormat(format);
        String str = to_type.format(time);
        return str;
    }

    /**
     * 获得一天的时间（Long）
     *
     * @return
     */
    public static Long getADayLongTime() {
        return 24 * 60 * 60 * 1000L;
    }

    @SuppressWarnings("deprecation")
    public static Date dateAddYears(Date time, int year) {
        if (time == null) {
            return null;
        }
        //Date result = (Date)time.clone();
        //result.setMonth(time.getYear()+year);

        return dateAddMonths(time, year * 12);
    }

    @SuppressWarnings("deprecation")
    public static Date dateAddMonths(Date time, int month) {
        if (time == null) {
            return null;
        }
        Date result = (Date) time.clone();
        result.setMonth(time.getMonth() + month);

        return result;
    }


    /**
     * 给指定date对象增加指定天数
     *
     * @param time 要操作的时间对象
     * @param days 增加的天数
     * @return 增加后的date对象
     */
    @SuppressWarnings("deprecation")
    public static Date dateAddDays(Date time, int days) {
        if (time == null) {
            return null;
        }
        Date result = (Date) time.clone();
        result.setDate(time.getDate() + days);

        return result;
    }

    public static Date dateAddMinutes(Date time, int minutes) {
        if (time == null) {
            return null;
        }
        Date result = (Date) time.clone();

        result.setMinutes(time.getMinutes() + minutes);
        return result;
    }

    /**
     * 获取某天的00:00:00点时间
     *
     * @param time
     * @return
     */
    public static Date getZeroDateByDate(Date time) {
        if (time == null) {
            return null;
        }

        String timeStr = formatTimeStr(time, TIME_FORMAT_YYYY_MM_DD) + " 00:00:00";

        Date result = getDateByStr(timeStr, TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);

        return result;
    }

    /**
     * 获取某天的23:59:59点时间
     *
     * @param time
     * @return
     */
    public static Date get23DateByDate(Date time) {
        if (time == null) {
            return null;
        }
        String timeStr = formatTimeStr(time, TIME_FORMAT_YYYY_MM_DD) + " 23:59:59";

        Date result = getDateByStr(timeStr, TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);

        return result;
    }

    /**
     * 根据时间字符串转化为date对象
     *
     * @param dateStr 要转化的字符串
     * @param format  时间格式
     * @return 转化后的date对象
     */
    public static Date getDateByStr(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            logger.error(e.getMessage(), new Throwable(e));
        }
        return null;
    }

    /**
     * 获取某一个月第一天和最后一天
     *
     * @param date
     * @return
     */
    public static Map<String, String> getFirstDayLastDayMonth(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }

    /**
     * 获取 当月第一天
     *
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }

    /**
     * 获取 当月最后一天
     *
     * @return
     */
    public static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();

    }

    /**
     * 获得string 对应的long时间
     *
     * @param str
     * @return
     */
    public static Long getLongByStr(String str) {

        if (str == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = f.parse(str);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        Long time = date.getTime();
        return time;
    }

    /**
     * 获得string 对应的long时间
     *
     * @param str
     * @return
     */
    public static Long getLongByStr(String str, String format) {

        if (str == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = f.parse(str);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        Long time = date.getTime();
        return time;
    }

    /**
     * 获得long 对应的String时间
     *
     * @param
     * @return
     */
    public static String getStrByLong(Long date, String format) {
        if (date == null || format == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    /**
     * 获取两个时间差值
     *
     * @param timest 开始时间
     * @param timeed 结束时间
     * @param type   1 天，2 时， 3 分， 4 秒， 5 毫秒（ms）
     * @return 成功返回时间差值，不成功返回null
     */
    public static Long getTimeDiff(Date timest, Date timeed, int type) {
        try {
            if (timest == null) {
                logger.info("getTimeDiff timest 为空！");
                return null;
            }
            if (timeed == null) {
                logger.info("getTimeDiff timeed 为空！");
                return null;
            }

            Long result = null;
            switch (type) {
                case 1:
                    result = getDiffDays(timest, timeed);
                    break;
                case 2:
                    result = getDiffHours(timest, timeed);
                    break;
                case 3:
                    result = getDiffMinutes(timest, timeed);
                    break;
                case 4:
                    result = getDiffSeconds(timest, timeed);
                    break;
                case 5:
                    result = getDiffStamp(timest, timeed);
                    break;
                case 6:
                    result = getDiffMonth(timest, timeed);
                    break;
                default:
                    logger.info("getTimeDiff type未定义！");
                    break;
            }
            return result;

        } catch (Exception e) {
            logger.error("getTimeDiff 异常：" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取两个时间相差月数
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     * @Description
     */
    private static Long getDiffMonth(Date start, Date end)
            throws ParseException {

        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return Long.parseLong(String.valueOf(year * 12 + month + 1));
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return Long.parseLong(String.valueOf(year * 12 + month));
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return Long.parseLong(String.valueOf(year * 12 + month));
        } else {
            return Long.parseLong(String.valueOf((year * 12 + month - 1) < 0 ? 0 : (year * 12 + month)));
        }
    }

    /**
     * 获取两个时间相差的天数
     *
     * @param timest 开始时间
     * @param timeed 结束时间
     * @return 成功返回相差天数，不成功返回null
     */
    private static Long getDiffDays(Date timest, Date timeed) {
        try {
            Calendar calst = Calendar.getInstance();
            Calendar caled = Calendar.getInstance();
            calst.setTime(timest);
            caled.setTime(timeed);
            logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd"));
            logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd"));

            //设置时间为0时
            calst.set(Calendar.HOUR_OF_DAY, 0);
            calst.set(Calendar.MINUTE, 0);
            calst.set(Calendar.SECOND, 0);

            caled.set(Calendar.HOUR_OF_DAY, 0);
            caled.set(Calendar.MINUTE, 0);
            caled.set(Calendar.SECOND, 0);
            //得到两个日期相差的天数
            Long days = ((Long) (caled.getTime().getTime() / 1000) - (Long) (calst.getTime().getTime() / 1000)) / 3600 / 24;

            return days;
        } catch (Exception e) {
            logger.error("LongDiffDays 异常：" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取两个时间相差的小时数
     *
     * @param timest 开始时间
     * @param timeed 结束时间
     * @return 成功返回相差分钟数，不成功返回null
     */
    private static Long getDiffHours(Date timest, Date timeed) {
        try {
            Calendar calst = Calendar.getInstance();
            Calendar caled = Calendar.getInstance();
            calst.setTime(timest);
            caled.setTime(timeed);
            logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd HH:mm:ss"));
            logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd HH:mm:ss"));

            calst.set(Calendar.MINUTE, 0);
            calst.set(Calendar.SECOND, 0);
            caled.set(Calendar.MINUTE, 0);
            caled.set(Calendar.SECOND, 0);
            //得到两个日期相差的天数
            Long hoerss = ((Long) (caled.getTime().getTime() / 1000) - (Long) (calst.getTime().getTime() / 1000)) / 3600;

            return hoerss;
        } catch (Exception e) {
            logger.error("getDiffHours 异常：" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取两个时间相差的分钟数
     *
     * @param timest 开始时间
     * @param timeed 结束时间
     * @return 成功返回相差分钟数，不成功返回null
     */
    private static Long getDiffMinutes(Date timest, Date timeed) {
        try {
            Calendar calst = Calendar.getInstance();
            Calendar caled = Calendar.getInstance();
            calst.setTime(timest);
            caled.setTime(timeed);
            logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd HH:mm:ss"));
            logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd HH:mm:ss"));

            //设置时间为0时

            calst.set(Calendar.SECOND, 0);
            caled.set(Calendar.SECOND, 0);
            //得到两个日期相差的天数
            Long minutess = ((Long) (caled.getTime().getTime() / 1000) - (Long) (calst.getTime().getTime() / 1000)) / 60;

            return minutess;
        } catch (Exception e) {
            logger.info("getDiffMinutes 异常：" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取两个时间相差的数据刻度
     *
     * @param timest 开始时间
     * @param timeed 结束时间
     * @return 成功返回时间刻度，不成功返回null
     */
    private static Long getDiffSeconds(Date timest, Date timeed) {
        try {
            Calendar calst = Calendar.getInstance();
            Calendar caled = Calendar.getInstance();
            calst.setTime(timest);
            caled.setTime(timeed);
            logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd HH:mm:ss"));
            logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd HH:mm:ss"));

            //得到两个日期相差的天数
            Long minutess = ((Long) (caled.getTime().getTime() / 1000) - (Long) (calst.getTime().getTime() / 1000));

            return minutess;
        } catch (Exception e) {
            logger.error("getDiffSeconds 异常：" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取两个时间的相差时间刻度
     *
     * @param timest 开始时间
     * @param timeed 结束时间
     * @return 成功返回时间刻度，不成功返回null
     */
    private static Long getDiffStamp(Date timest, Date timeed) {
        try {
            Calendar calst = Calendar.getInstance();
            Calendar caled = Calendar.getInstance();
            calst.setTime(timest);
            caled.setTime(timeed);
            logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd HH:mm:ss"));
            logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd HH:mm:ss"));

            //得到两个日期相差的天数
            Long stamps = ((Long) (caled.getTime().getTime()) - (Long) (calst.getTime().getTime()));

            return stamps;
        } catch (Exception e) {
            logger.error("getDiffStamp 异常：" + e.getMessage());
        }
        return null;
    }


    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间    
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间    
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后    
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量    
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }


    public static Date getEveryMonthPayDate(Date dateTime, int period) {
        Calendar date = Calendar.getInstance();
        date.setTime(dateTime);
        date.add(Calendar.MONTH, period);
        date.add(Calendar.DATE, 0);
        return date.getTime();
    }

    /**
     * 获取两个日期范围内的所有月份
     * @param minDate
     * @param maxDate
     * @return
     * @throws Exception
     */
    public static List<String> getMonthBetween(String minDate, String maxDate, String formatType) {
        ArrayList<String> result = new ArrayList<String>();
        //格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        //获取日期对象
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
            //设置最小日期
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            //设置最大日期
            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("日期格式化异常！");
        }

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    /**
    * @Description: 获取上个月的时间字符串
    * @Param: [period]
    * @return: java.lang.String
    * @Author:GuanTao
    * @Date: 2019-06-17 14:32
    */
    public static String getPeriodBeforeStr(String period, String forMat){

        try {
            if(StringUtils.isEmpty(period)){
                throw new ServiceException("日期字符串为空，请确认！");
            }
            DateFormat sdf = new SimpleDateFormat(forMat);
            Date date = sdf.parse(period);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            //System.out.println(new SimpleDateFormat("yyyyMM").format(cal.getTime()));
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }

    }

    /**
     * @Description: 获取下个月的时间字符串
     * @Param: [period]
     * @return: java.lang.String
     * @Author:GuanTao
     * @Date: 2019-06-17 14:32
     */
    public static String getPeriodAfterStr(String period, String forMat){

        try {
            if(StringUtils.isEmpty(period)){
                throw new ServiceException("日期字符串为空，请确认！");
            }
            DateFormat sdf = new SimpleDateFormat(forMat);
            Date date = sdf.parse(period);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);
            //System.out.println(new SimpleDateFormat("yyyyMM").format(cal.getTime()));
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }


    }

    /**
     * 获取想要的某一天
     * @param date
     * @param amount
     * @return
     */
    public static String getSomeDateStr(Date date, int amount, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        Date d = cal.getTime();
        // 日期格式
        SimpleDateFormat sp = new SimpleDateFormat(pattern);
        //获取昨天日期
        return sp.format(d);
    }

    /**
     * 判断两个日期是否在同一周
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(String date1, String date2)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try
        {
            d1 = format.parse(date1);
            d2 = format.parse(date2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        //西方周日为一周的第一天，咱得将周一设为一周第一天
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        cal2.setFirstDayOfWeek(Calendar.MONDAY);
        cal1.setTime(d1);
        cal2.setTime(d2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        // subYear==0,说明是同一年
        if (subYear == 0)
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        //subYear==1,说明cal比cal2大一年;java的一月用"0"标识，那么12月用"11"
        else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11)
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        //subYear==-1,说明cal比cal2小一年
        else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11)
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 获取一个日期为星期几
     * @param dates
     */
    public static int getWeek(String dates) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date d=null;
        try {
            d=f.parse(dates);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        cal.setTime(d);
        int w=cal.get(Calendar.DAY_OF_WEEK)-1;
        if(w==0) w=7;
        //System.out.println("星期"+w);
        return w;
    }

    /**
     * 计算两个日期间的月数
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static int getMonthDiff(String minDate, String maxDate) throws ParseException {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(minDate);//定义起始日期


        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(maxDate);//定义结束日期

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if(c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if(month1 < month2 || month1 == month2 && day1 < day2) yearInterval --;
        // 获取月数差值
        int monthInterval =  (month1 + 12) - month2  ;
        if(day1 < day2) monthInterval --;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     * 获取两个日期之间的所有日期
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static List<String> getBetweenDates(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //定义起始日期
        Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        //定义结束日期
        Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        List<String> result = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }
    /**
     * 获取周一和周日的结果集
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static List<Map<String, String>> getBetweenWeeks(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(startDate);//定义起始日期
        Date end = sdf.parse(endDate);//定义结束日期

        // List<Date> result = new ArrayList<Date>();
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
            Map<String, String> map = new HashMap<String,String>();
            int we = tempStart.get(Calendar.DAY_OF_WEEK);
            if ( we == 2) {
                map.put("mon", sdf.format(tempStart.getTime()));
            }

            if (map.isEmpty()) { //检测map是否为空
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }else {
                tempStart.add(Calendar.DAY_OF_YEAR, 6);
                map.put("sun", sdf.format(tempStart.getTime()));
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 判断该日期对应的星期与传入的星期是否一致。
     * @param dates
     * @param weekNum
     * @return
     */
    public static boolean isWeekNum(String dates, int weekNum) {
        int weekCount = getWeek(dates);
        if(weekCount == weekNum){
            return true;
        }
        return false; }

    /**
     * 获取两个日期间所有月分的起始日期和结束日期
     * @param minDate
     * @param maxDate
     * @param formatType
     * @return
     */
    public static List<Map<String, String>> getBetweenMonthsStartAndEndDate(String minDate, String maxDate, String formatType) {
        List<Map<String, String>> result = Lists.newArrayList();
        List<String> monthBetween = getMonthBetween(minDate, maxDate, formatType);
        Optional.ofNullable(monthBetween).ifPresent((list) ->{
            list.forEach(item ->{
                Map<String, String> startAndEndDateStr = getFirstDayLastDayMonth(getDateByStr(item, formatType), formatType);
                result.add(startAndEndDateStr);
            });
        });
        return result;
    }

    /**
     * 判断该日期是否是该月的第一天
     *
     * @param date
     *            需要判断的日期
     * @return
     */
    public boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.MONTH));
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;

    }

    /**
     * 判断该日期是否是该月的最后一天
     *
     * @param date
     *            需要判断的日期
     * @return
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH) == calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static void main(String[] args) throws ParseException {
        String someDateStr = getSomeDateStr(new Date(), -1, TimeUtility.TIME_FORMAT_YYYY_MM_DD);
        System.out.println(someDateStr);
    }


}
