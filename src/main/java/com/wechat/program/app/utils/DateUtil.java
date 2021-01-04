package com.wechat.program.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 */
public class DateUtil {

    private DateUtil() {
    }

    private static long nd = 1000 * 24 * 60 * 60;
    private static long nh = 1000 * 60 * 60;
    private static long nm = 1000 * 60;
    /**
     * 按照指定的格式返回日期字符串，默认"yyyy-MM-dd"
     *
     * @param date    日期
     * @param pattern 指定格式
     * @return 指定格式的日期字符串
     */
    @SuppressWarnings("all")
    public static String formatDate(Date date, String pattern) {
        if (date == null) return "";
        if (pattern == null) pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return (sdf.format(date));
    }

    public static String formatDate(Date date) {
        return formatDate(date, null);
    }

    /**
     * 获取指定日期对象中的年
     * @param date 日期对象
     * @return 年
     */
    public static Integer getYear(Date date) {
        if(null == date) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获得指定日期中的月份(1-12)
     * @param date 日期对象
     * @return 月
     */
    public static Integer getMonth(Date date) {
        if(null == date) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }

    /**
     * 获取指定日期中的在该月中的天（从1开始）
     * @param date 日期对象
     * @return 日
     */
    public static Integer getDay(Date date) {
        if(null == date) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得上个月
     * @param date 当前期间
     * @return 上个月
     */
    public static Integer getBeforeMonth(Date date){
        if(null == date) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static Date getDate(int year, int month, int day) {
        switch (month) {
            case 1:
                month = Calendar.JANUARY;
                break;
            case 2:
                month = Calendar.FEBRUARY;
                break;
            case 3:
                month = Calendar.MARCH;
                break;
            case 4:
                month = Calendar.APRIL;
                break;
            case 5:
                month = Calendar.MAY;
                break;
            case 6:
                month = Calendar.JUNE;
                break;
            case 7:
                month = Calendar.JULY;
                break;
            case 8:
                month = Calendar.AUGUST;
                break;
            case 9:
                month = Calendar.SEPTEMBER;
                break;
            case 10:
                month = Calendar.OCTOBER;
                break;
            case 11:
                month = Calendar.NOVEMBER;
                break;
            case 12:
                month = Calendar.DECEMBER;
                break;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(int year, int month) {
        Date date = getDate(year, month, 1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        return getLastDayOfMonth(getYear(date), getMonth(date));
    }

    public static Date getFirstDayOfMonth(Date date) {
        return getDate(getYear(date), getMonth(date), 1);
    }
    /**
     * 字符串转时间
     * @param strdate 字符串时间（如：2017-01-01）
     * @param format 格式（如：yyyy-MM-dd）
     * @return Date时间
     */
    public static Date StringToDate(String strdate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(strdate);
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 给时间加上或减去指定毫秒，秒，分，时，天、月或年等，返回变动后的时间
     *
     * @param date   要加减前的时间，如果不传，则为当前日期
     * @param field  时间域，有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,<br>
     *               Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
     * @param amount 按指定时间域加减的时间数量，正数为加，负数为减。
     * @return 变动后的时间
     */
    public static Date add(Date date, int field, int amount) {
        if (date == null) {
            date = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);

        return cal.getTime();
    }

    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 指定日期加上天数
     * @param date 指定日
     * @param day 天数
     * @return 计算后的天数
     */
    public static Date addDate(Date date, int day){
        return add(date, Calendar.DATE, day);
    }

    /**
     * 获得上个期间
     * @param year 年
     * @param month 月
     * @return 上个期间
     */
    public static Date getBeforePeriod(int year, int month){
        Date date = StringToDate(year + "-" + month , "yyyy-MM");
        int beforeMonth = DateUtil.getBeforeMonth(date);
        if(12 == beforeMonth){
            return StringToDate(year-1 + "-" + beforeMonth, "yyyy-MM");
        }
        return StringToDate(year + "-" + beforeMonth, "yyyy-MM");
    }

    /**
     * 获得季度
     * @param date 期间
     * @return 季度
     */
    public static int getQuarter(Date date) {
        return ((getMonth(date) - 1) / 3 + 1);
    }

    /**
     * 取得季度月
     * @param date 期间
     * @return 季度月范围
     */
    public static Date[] getSeasonDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer year = DateUtil.getYear(date);
        int nSeason = getQuarter(date);
        return getSeasonDateArray(nSeason, year);
    }

    private static Date[] getSeasonDateArray(int nSeason, int year) {
        int[] monthArr = new int[]{1, 2, 3};
        Date[] season = new Date[3];
        switch (nSeason) {
            case 1:
                monthArr = new int[]{1, 2, 3};
                break;
            case 2:
                monthArr = new int[]{4, 5, 6};
                break;
            case 3:
                monthArr = new int[]{7, 8, 9};
                break;
            case 4:
                monthArr = new int[]{10, 11, 12};
                break;
        }

        for (int i = 0; i < monthArr.length; i++) {
            season[i] = DateUtil.getLastDayOfMonth(year, monthArr[i]);
        }
        return season;
    }

    /**
     * 比较两个期间相差几个月
     * @param start 起始期间
     * @param end 结束期间
     * @return 月份
     */
    public static int comparePeriod(Date start, Date end){
        int endYear = getYear(end);
        int endMonth = getMonth(end);
        int startYear = getYear(start);
        int startMonth = getMonth(start);
        return endYear*12+endMonth-startYear*12-startMonth;
    }

    public static Map getQuarterToMonth(int quarter){
        Map<String,String> map = new HashMap<>();
        String startMonth = null;
        String endMonth = null;
        if (quarter==1){
            startMonth = "01";
            endMonth = "03";
        }else if (quarter==2){
            startMonth = "04";
            endMonth = "06";
        }else if (quarter == 3){
            startMonth = "07";
            endMonth = "09";
        }else if (quarter == 4){
            startMonth = "10";
            endMonth = "12";
        }
        map.put("startMonth",startMonth);
        map.put("endMonth",endMonth);
        return map;
    }

    /**
     * 获取指定日期的开始日期 00:00:00或者结束日期23:59:59
     * @param date
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     *       1 返回yyyy-MM-dd 23:59:59日期
     * @return
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
        }
        return cal.getTime();
    }

    /**
     * 获取两个期间之间所有期间
     * @param minDate 最小期间
     * @param maxDate 最大期间
     * @return 期间
     */
    public static List<Date> getMonthBetween(String minDate, String maxDate){
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        try {
            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        }catch (ParseException e){
            e.printStackTrace();
        }

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        List<Date> list = new ArrayList<>();
        if(null != result && result.size()>0){
            for (String str : result){
                list.add(StringToDate(str,"yyyy-MM"));
            }
        }

        return list;
    }
    
    /**
     * 获取传入月份所在季度的最后一个月份(1,2,3月对应03月;4,5,6月对应06月;7,8,9月对应09月;10,11,12月对应12月)
     * @param strdate 字符串时间（如：1)   
     * @return 月份(如：03)
     */
    public static String getSeasonLastDate(Integer strdate) {
    	String monthString= "";
    	switch (strdate) {
        case 1:
            monthString = "03";
            break;
        case 2:
            monthString = "03";
            break;
        case 3:
            monthString = "03";
            break;
        case 4:
            monthString = "06";
            break;
        case 5:
            monthString = "06";
            break;
        case 6:
            monthString = "06";
            break;
        case 7:
            monthString = "09";
            break;
        case 8:
            monthString = "09";
            break;
        case 9:
            monthString = "09";
            break;
        case 10:
            monthString = "12";
            break;
        case 11:
            monthString = "12";
            break;
        case 12:
            monthString = "12";
            break;
        default:           
            break;
        }  
        return monthString;
    }

    /**
     * 当前期间加amount个月
     *
     * @param period 期间，格式：201801
     * @param amount 数量
     * @return 期间，格式：201801
     */
    public static Integer addMonth(Integer period, Integer amount) {
        String currentPeriodPointerStr = Integer.toString(period);
        int currentPeriodPointerYearInt = Integer.parseInt(currentPeriodPointerStr.substring(0, 4));
        int currentPeriodPointerMonthInt = Integer.parseInt(currentPeriodPointerStr.substring(4, 6));
        Date currentPeriodPointerDate = DateUtil.getDate(currentPeriodPointerYearInt, currentPeriodPointerMonthInt, 1);
        Date beforePeriod = addMonth(currentPeriodPointerDate, amount);
        return Integer.parseInt(DateUtil.formatDate(beforePeriod, "yyyyMM"));
    }
    /**
     * 获取日期的当前天
     */
    public static Date getCurrentDateOfMonth(Date date){
        return getDate(getYear(date), getMonth(date), getDay(new Date()));
    }

    /**
     * 获取日期的开始时间: 00:00:00
     */
    public static Date getDateStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取日期的结束时间:23:59:59
     */
    public static Date getDateEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    public static boolean compareDate(Date start, Date end){

        return start.compareTo(end) > 0;
    }

    /**
     * 计算两个期间相差的天数
     * @param start 开始日期
     * @param end 结束日期
     * @return 天数
     */
    public static int getDayBetween(Date start, Date end){
        return (int) ((end.getTime() - start.getTime())/(1000*60*60*24));
    }

    public static int getMin(Date start, Date end) {
        // 获得两个时间的毫秒时间差异
        long diff = end.getTime() - start.getTime();
//        long day = diff / (24 * 60 * 60 * 1000);
//        long hour = (diff/ (60 * 60 * 1000) - day * 24);
//        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long min = diff / 1000/ 60;
        return (int)min;
    }

    public static int getSeconds(Date start, Date end) {
        // 获得两个时间的毫秒时间差异
        long diff = end.getTime() - start.getTime();
//        long day = diff / (24 * 60 * 60 * 1000);
//        long hour = (diff/ (60 * 60 * 1000) - day * 24);
//        long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long min = diff / 1000;
        return (int)min;
    }


    public static void main(String[] args) {
        Date date = StringToDate("2020-10-29 12:19:01", "yyyy-MM-dd HH:mm:ss");
        int min = getMin(date, new Date());
        System.out.println(min);
    }
}
