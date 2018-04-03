package utils;

import net.sf.json.JSONArray;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    // 日期格式化
    private final static String sdf_date_time = "yyyy-MM-dd HH:mm:ss";
    private final static String sdf_date_minute = "yyyy-MM-dd HH:mm";
    private final static String sdf_date_hour = "yyyy-MM-dd HH";
    private final static String sdf_date = "yyyy-MM-dd";
    private final static String sdf_year = "yyyy";
    private final static String sdf_month = "MM";
    private final static String sdf_day = "dd";
    private final static String sdf_year_month = "yyyy-MM";
    private final static String sdf_month_day = "MM-dd";
    private final static String sdf_hour_minute = "HH:mm";
    private final static String sdf_hour = "HH";
    private final static String sdf_minute = "mm";
    private final static String sdf_date_time_near = "yyyyMMddHHmmss";
    private final static String sdf_date_near = "yyyyMMdd";

    private final static String sdf_date_hour_minute = "yyyy/MM/dd HH:mm";
    private final static String sdf_date_hour_minute_str = "yy/MM/dd HH:mm";
    private final static String sdf_date_month_day_hour_minute = "MM/dd HH:mm";

    // 星期
    private final static String[] weeks_ch = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private final static String[] weeks_ch_two = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private final static String[] weeks_ch_one = {"日", "一", "二", "三", "四", "五", "六"};

    // public method

    /**
     * 获取当前时间(yyyy-MM-dd HH:mm:ss)
     */
    public static Date getNow() throws Exception {
        return parseDateTime(new Date());
    }

    /**
     * 获取今天的日期(yyyy-MM-dd)
     */
    public static Date getToday() throws Exception {
        return parseDate(new Date());
    }

    /**
     * 获取指定日期的年份（yyyy）
     */
    public static String getYear(Date date) {
        return null != date ? new SimpleDateFormat(sdf_year).format(date) : null;
    }

    /**
     * 获取指定日期的月份(MM)
     */
    public static String getMonth(Date date) {
        return null != date ? new SimpleDateFormat(sdf_month).format(date) : null;
    }

    /**
     * 获取指定日期的天(dd)
     */
    public static String getDay(Date date) {
        return null != date ? new SimpleDateFormat(sdf_day).format(date) : null;
    }

    /**
     * 获取当前年份(yyyy)
     */
    public static String getYear() {
        return new SimpleDateFormat(sdf_year).format(new Date());
    }

    /**
     * 获取当前月份(MM)
     */
    public static String getMonth() {
        return new SimpleDateFormat(sdf_month).format(new Date());
    }

    /**
     * 获取当前天(dd)
     */
    public static String getDay() {
        return new SimpleDateFormat(sdf_day).format(new Date());
    }

    /**
     * 获取当前日期(yyyy-MM-dd)
     */
    public static String getDate() {
        return new SimpleDateFormat(sdf_date).format(new Date());
    }

    /**
     * 获取当前日期(yyyy-MM-dd HH:mm:ss)
     **/
    public static String getDateTime() {
        return new SimpleDateFormat(sdf_date_time).format(new Date());
    }

    /**
     * 获取当前日期(yyyyMMdd)
     **/
    public static String getDate_near() {
        return new SimpleDateFormat(sdf_date_near).format(new Date());
    }

    /**
     * 获取当前日期(yyyyMMddHHmmss)
     **/
    public static String getDateTime_near() {
        return new SimpleDateFormat(sdf_date_time_near).format(new Date());
    }

    /**
     * 将指定日期格式化为字符串(yyyy-MM-dd)
     */
    public static String formatDate(Date date) {
        return null != date ? new SimpleDateFormat(sdf_date).format(date) : null;
    }

    /**
     * 将指定日期格式化为字符串(yyyy-MM-dd)
     */
    public static String formatDate(String date) throws Exception {
        return Tools.notEmpty(date) ? formatDate(parseDate(date)) : null;
    }

    /**
     * 将指定日期格式化为字符串(HH:mm)
     */
    public static Date formatDateHourMinute(String dateStr) throws Exception {
        return null != dateStr ? new SimpleDateFormat(sdf_hour_minute).parse(dateStr) : null;
    }

    /**
     * 将指定日期格式化为字符串(HH:mm)
     */
    public static String formatDateHourMinute(Date date) {
        return null != date ? new SimpleDateFormat(sdf_hour_minute).format(date) : null;
    }

    /**
     * 将指定日期格式化为字符串(HH)
     */
    public static String formatDateHour(Date date) {
        return null != date ? new SimpleDateFormat(sdf_hour).format(date) : null;
    }

    /**
     * 将指定日期格式化为字符串(mm)
     */
    public static String formatDateMinute(Date date) {
        return null != date ? new SimpleDateFormat(sdf_minute).format(date) : null;
    }

    /**
     * 将指定日期格式化为字符串(yyyyMMdd)
     */
    public static String formatDate_near(Date date) {
        return null != date ? new SimpleDateFormat(sdf_date_near).format(date) : null;
    }

    /**
     * 将指定日期格式化为字符串(yyyy-MM-dd HH:mm:ss)
     */
    public static String formatDateTime(Date date) {
        return null != date ? new SimpleDateFormat(sdf_date_time).format(date) : "";
    }

    /**
     * 将字符串转换为指定格式的日期(yyyy-MM-dd HH:mm)
     */
    public static String parseDateMinute(Date date) throws Exception {
        return null != date ? new SimpleDateFormat(sdf_date_minute).format(date) : "";
    }

    /**
     * 将字符串转换为指定格式的日期(HH:mm)
     */
    public static String parseHourMinute(Date date) throws Exception {
        String dateStr = String.format("%tH:%tM", date, date, date);
        return dateStr;
    }

    /**
     * 将指定日期格式化为字符串(MM-dd)
     */
    public static String formatMonthDay(Date date) {
        return null != date ? new SimpleDateFormat(sdf_month_day).format(date) : null;
    }

    /**
     * 校验日期字符串是是否合法(yyyy-MM-dd)
     */
    public static boolean validDate(String dateStr) {
        try {
            new SimpleDateFormat(sdf_date).parse(dateStr);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验日期字符串是是否合法(yyyy-MM-dd HH:mm:ss)
     */
    public static boolean validDateTime(String dateStr) {
        try {
            new SimpleDateFormat(sdf_date_time).parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将 java.sql.Timestamp 格式化为字符串(yyyy-MM-dd HH:mm:ss)
     */
    public static String formatTimestamp(Timestamp time) {
        return null != time ? new SimpleDateFormat(sdf_date_time).format(time) : null;
    }

    /**
     * 将字符串转换为 java.sql.Timestamp
     */
    public static Timestamp parseTimestamp(String time) {
        return Timestamp.valueOf(time);
    }

    /**
     * 将指定日期转换为指定格式(yyyy-MM-dd HH:mm:ss)
     */
    public static Date parseDateTime(Date date) throws Exception {
        String dateStr = null;

        if (null != date) {
            dateStr = new SimpleDateFormat(sdf_date_time).format(date);
        }

        return Tools.notEmpty(dateStr) ? parseDateTime(dateStr) : null;
    }

    /**
     * 将字符串转换为指定格式的日期(yyyy-MM-dd HH:mm:ss)
     */
    public static Date parseDateTime(String dateStr) throws Exception {
        return Tools.notEmpty(dateStr) ? new SimpleDateFormat(sdf_date_time).parse(dateStr) : null;
    }

    /**
     * 将字符串转换为指定格式的日期(yyyy-MM-dd HH:mm)
     */
    public static Date parseDateMinute(String dateStr) throws Exception {
        return Tools.notEmpty(dateStr) ? new SimpleDateFormat(sdf_date_minute).parse(dateStr) : null;
    }

    /**
     * 将指定日期转换为指定格式(yyyy-MM-dd)
     */
    public static Date parseDate(Date date) throws Exception {
        String dateStr = null;

        if (null != date) {
            dateStr = new SimpleDateFormat(sdf_date).format(date);
        }

        return Tools.notEmpty(dateStr) ? parseDate(dateStr) : null;
    }

    /**
     * 将指定日期转换为指定格式(yyyy-MM-dd)
     */
    public static String parseDay(Date date) throws Exception {
        return null != date ? new SimpleDateFormat(sdf_date).format(date) : null;
    }

    /**
     * 将字符串转换为指定格式的日期(yyyy-MM-dd)
     */
    public static Date parseDate(String dateStr) throws Exception {
        return Tools.notEmpty(dateStr) ? new SimpleDateFormat(sdf_date).parse(dateStr) : null;
    }

    /**
     * 将指定日期转换为指定格式(yyyy-MM-dd HH)
     */
    public static Date parseDateHour(Date date) throws Exception {
        return null != date ? parseDateHour(new SimpleDateFormat(sdf_date_hour).format(date)) : null;
    }

    /**
     * 将指定日期转换为指定格式(yy/MM/dd HH:mm)
     */
    public static Date parseDateHM(String dateStr) throws Exception {
        return Tools.notEmpty(dateStr) ? new SimpleDateFormat(sdf_date_hour_minute_str).parse(dateStr) : null;
    }

    /**
     * 将字符串转换为指定格式的日期(yyyy/MM/dd HH:mm)
     */
    public static String parseStrHM(Date date) throws Exception {
        return null != date ? new SimpleDateFormat(sdf_date_hour_minute).format(date) : "";
    }

    /**
     * 先转换为 yyyy/MM/dd HH:mm
     * 将字符串转换为指定格式的日期(MM/dd HH:mm)
     */
    public static String parseStrMonthDay(Date date) throws Exception {
        return null != date ? new SimpleDateFormat(sdf_date_month_day_hour_minute).format(date) : "";
    }

    /**
     * 将指定日期转换为指定格式(yyyy-MM-dd HH:mm)
     */
    public static Date parseDateHourMinute(String dateStr) throws Exception {
        return Tools.notEmpty(dateStr) ? new SimpleDateFormat(sdf_date_minute).parse(dateStr) : null;
    }

    /**
     * 将指定日期按照指定格式转换
     *
     * @return Date 返回指定日期-指定小时数的组合(yyyy-MM-dd HH)
     * @Param date 指定日期(至少包含年-月-日)
     * @Param hour 指定小时数(HH 24小时制)
     */
    public static Date parseDateHour(Date date, String hour) throws Exception {
        String dateStr = null != date ? (new SimpleDateFormat(sdf_date).format(date) + " " + hour.trim()) : null;

        return parseDateHour(dateStr);
    }

    /**
     * 将字符串转换为指定格式的日期(yyyy-MM-dd HH)
     */
    public static Date parseDateHour(String dateStr) throws Exception {
        return Tools.notEmpty(dateStr) ? new SimpleDateFormat(sdf_date_hour).parse(dateStr) : null;
    }

    /**
     * 将指定日期按照指定格式转换
     *
     * @return Date 返回指定日期-指定时间的组合(yyyy-MM-dd HH:mm:ss)
     * @Param date 指定日期(至少包含年-月-日)
     * @Param hour 指定时间(HH:mm:ss 24小时制)
     */
    public static Date parseDateTimeByTime(Date date, String time) throws Exception {
        if (null != date && Tools.notEmpty(time)) {
            String dateTimeStr = new SimpleDateFormat(sdf_date).format(date) + " " + time.trim();

            return parseDateTime(dateTimeStr);
        }

        return null;
    }

    /**
     * 获取某年某月的最大天数
     */
    public static int getMaxDayOfMonth(Date date) {
        if (null != date) {
            String year_month = new SimpleDateFormat(sdf_year_month).format(date);

            if (Tools.notEmpty(year_month) && year_month.indexOf("-") > 0) {
                String[] dateArr = year_month.split("-");

                if (null != dateArr && dateArr.length >= 2) {
                    return getMaxDayOfMonth(dateArr[0], dateArr[1]);
                }
            }
        }

        return 0;
    }

    /**
     * 获取某年某月的最大天数
     */
    public static int getMaxDayOfMonth(String year, String month) {
        if (Tools.notEmpty(year) && Tools.notEmpty(month)) {
            Integer year_int = new Integer(year.trim());
            Integer month_int = new Integer(month.trim());

            return getMaxDayOfMonth(year_int, month_int);
        }

        return 0;
    }

    /**
     * 获取某年某月的最大天数
     */
    public static int getMaxDayOfMonth(int year, int month) {
        if (year > 0 && month > 0 && month <= 12) {
            Calendar cal = Calendar.getInstance();

            // 设置年份
            cal.set(Calendar.YEAR, year);

            // 设置月份
            int calendarMonth = month - 1; // Calendar的month默认从0开始
            cal.set(Calendar.MONTH, calendarMonth);

            // 设置天，将日期设置为该月的第一天
            cal.set(Calendar.DATE, 1);

            // 将日期回滚一天，即为最后一天
            cal.roll(Calendar.DATE, -1); // method roll

            int maxDay = cal.get(Calendar.DATE);

            return maxDay;
        }

        return 0;
    }

    /**
     * 获取指定日期是周几（返回两个中文，如“周一”）
     */
    public static String getWeek_two(String year, String month, String day) throws Exception {
        if (Tools.notEmpty(year) && Tools.notEmpty(month) && Tools.notEmpty(day)) {
            String dateStr = year.trim() + "-" + month.trim() + "-" + day.trim();

            return getWeek_two(new SimpleDateFormat(sdf_date).parse(dateStr));
        }

        return null;
    }

    /**
     * 获取指定日期是周几（返回两个中文，如“周一”）
     */
    public static String getWeek_two(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }

        return weeks_ch_two[week_index];
    }

    /**
     * 获取指定日期是周几（返回一个中文，周一即 “一” ）
     */
    public static String getWeek_one(String year, String month, String day) throws Exception {
        if (Tools.notEmpty(year) && Tools.notEmpty(month) && Tools.notEmpty(day)) {
            String dateStr = year.trim() + "-" + month.trim() + "-" + day.trim();

            return getWeek_one(new SimpleDateFormat(sdf_date).parse(dateStr));
        }

        return null;
    }

    /**
     * 获取指定日期是周几（返回一个中文，周一即 “一” ）
     */
    public static String getWeek_one(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }

        return weeks_ch_one[week_index];
    }

    /**
     * 获取指定日期是周几（返回中文，如“星期一”）
     */
    public static String getWeek_three(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }

        return weeks_ch[week_index];
    }

    /**
     * 获取指定日期的日历面板（默认星期日为一周的第一天）
     *
     * @param date 指定日期
     * @returns List<Date> 当前日立面板要显示的日期集合
     */
    public static List<Date> getDateRangeByCalendar(Date date) throws Exception {
        if (null != date) {
            Date startDate = null;
            Date endDate = null;

            String dateStr = formatDate(date);
            String[] dateArr = dateStr.split("-");

            int year = (null != dateArr && dateArr.length >= 3) ? Integer.valueOf(dateArr[0]) : 0;
            int month = (null != dateArr && dateArr.length >= 3) ? Integer.valueOf(dateArr[1]) : 0;
            int day = (null != dateArr && dateArr.length >= 3) ? Integer.valueOf(dateArr[2]) : 0;

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);

            int calendarMonth = month - 1; // Calendar的month默认从0开始
            cal.set(Calendar.MONTH, calendarMonth);
            cal.set(Calendar.DATE, 1); // 设置为该月第一天

            Date firstDay = parseDate(cal.getTime()); // 获得该月第一天

            int week_index = cal.get(Calendar.DAY_OF_WEEK); // 默认从1开始，1对应星期天，至7结束，7对应星期六
            int beforeNums = week_index - 1;

            // 获得开始日期
            startDate = getDateBeforeNums(firstDay, beforeNums);

            Calendar newCal = (Calendar) cal.clone();
            newCal.roll(Calendar.DATE, -1); // method roll
            Date endDay = parseDate(newCal.getTime()); // 获得该月最后一天

            int end_index = newCal.get(Calendar.DAY_OF_WEEK); // 默认从1开始，1对应星期天，至7结束，7对应星期六
            int afterNums = Calendar.DAY_OF_WEEK - end_index;

            // 获得结束日期
            endDate = getDateAfterNums(endDay, afterNums);

            return getDateRangeByStartDateAndEndDate(startDate, endDate);
        }

        return null;
    }

    /**
     * 获取在指定日期之前指定天数的日期
     *
     * @return Date 比指定日期早指定天数的日期(yyyy-MM-dd)
     * @params date 指定日期
     * @params nums 指定天数
     */
    public static Date getDateBeforeNums(Date date, int nums) throws Exception {
        if (null != date) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int dayIndex = cal.get(Calendar.DATE);
            int beforeIndex = dayIndex - nums;
            cal.set(Calendar.DATE, beforeIndex);
            Date beforeDate = cal.getTime();

            return parseDate(beforeDate);
        }

        return null;
    }

    /**
     * 获取在指定日期之后指定天数的日期
     *
     * @retrun Date 比指定日期晚指定天数的日期(yyyy-MM-dd)
     * @params date 指定日期
     * @params nums 指定天数
     */
    public static Date getDateAfterNums(Date date, int nums) throws Exception {
        if (null != date) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int dayIndex = cal.get(Calendar.DATE);
            int afterIndex = dayIndex + nums;
            cal.set(Calendar.DATE, afterIndex);
            Date afterDate = cal.getTime();

            return parseDate(afterDate);
        }

        return null;
    }


    /**
     * 判断指定日期是否为今天
     */
    public static boolean isToday(String year, String month, String day) throws Exception {
        if (Tools.notEmpty(year) && Tools.notEmpty(month) && Tools.notEmpty(day)) {
            Date date = new SimpleDateFormat(sdf_date).parse((year.trim() + "-" + month.trim() + "-" + day.trim()));

            return isToday(date);
        }

        return false;
    }

    /**
     * 判断指定日期是否为今天
     */
    public static boolean isToday(Date date) throws Exception {
        if (null != date) {
            if (parseDate(date).compareTo(getToday()) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断指定日期是否在今天之前
     */
    public static boolean isBeforeToday(String year, String month, String day) throws Exception {
        if (Tools.notEmpty(year) && Tools.notEmpty(month) && Tools.notEmpty(day)) {
            String dateStr = year.trim() + "-" + month.trim() + "-" + day.trim();

            return isBeforeToday(new SimpleDateFormat(sdf_date).parse(dateStr));
        }

        return false;
    }

    /**
     * 判断指定日期是否在今天之前
     */
    public static boolean isBeforeToday(Date date) throws Exception {
        if (null != date) {
            if (date.compareTo(getToday()) < 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断指定日期是否在今天之后
     */
    public static boolean isAfterToday(String year, String month, String day) throws Exception {
        if (Tools.notEmpty(year) && Tools.notEmpty(month) && Tools.notEmpty(day)) {
            String dateStr = year.trim() + "-" + month.trim() + "-" + day.trim();

            return isAfterToday(new SimpleDateFormat(sdf_date).parse(dateStr));
        }

        return false;
    }

    /**
     * 判断指定日期是否在今天之后
     */
    public static boolean isAfterToday(Date date) throws Exception {
        if (null != date) {
            if (date.compareTo(getToday()) > 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断指定日期是否属于“今天”所在的月份
     */
    public static Boolean isCurrentMonth(String year, String month) throws ParseException {
        if (Tools.notEmpty(year) && Tools.notEmpty(month)) {
            String dateStr = year.trim() + "-" + month.trim();

            return isCurrentMonth(new SimpleDateFormat(sdf_year_month).parse(dateStr));
        }

        return false;
    }

    /**
     * 判断指定日期是否属于“今天”所在的月份
     */
    public static Boolean isCurrentMonth(Date date) throws ParseException {
        if (null != date) {
            Date curMonthDate = new SimpleDateFormat(sdf_year_month).parse(new SimpleDateFormat(sdf_year_month).format(new Date()));
            Date date_clo = new SimpleDateFormat(sdf_year_month).parse(new SimpleDateFormat(sdf_year_month).format(date));

            if (date_clo.compareTo(curMonthDate) == 0) {
                return true;
            }
        }

        return false;
    }

    public static Boolean isCurrentMonth(Date sourceDate, Date date) throws ParseException {
        if (null != sourceDate && null != date) {
            Date sourceDateMonth = new SimpleDateFormat(sdf_year_month).parse(new SimpleDateFormat(sdf_year_month).format(sourceDate));
            Date dateMonth = new SimpleDateFormat(sdf_year_month).parse(new SimpleDateFormat(sdf_year_month).format(date));

            if (sourceDateMonth.compareTo(dateMonth) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取指定日期的前一天(yyyy-MM-dd)
     */
    public static Date getYesterday(Date date) throws Exception {
        if (date != null) {
            Date d = parseDate(date);

            Calendar cal = Calendar.getInstance();
            cal.setTime(d);

            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
            Date yesterday = cal.getTime();

            return parseDate(yesterday);
        }

        return null;
    }

    /**
     * 获取指定日期的前一天(yyyy-MM-dd)
     */
    public static String getNextDay(Date date) throws Exception {
        if (date != null) {
            Date d = parseDate(date);

            Calendar cal = Calendar.getInstance();
            cal.setTime(d);

            cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
            Date yesterday = cal.getTime();

            return formatDate(yesterday);
        }

        return null;
    }

    /**
     * 获取指定日期的前一天(yyyy-MM-dd)
     */
    public static String getBeforeYesterday(Date date, int nightCount) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - nightCount);
        Date yesterday = cal.getTime();

        return formatDate(yesterday);
    }

    /**
     * 获取指定日期的后一天(yyyy-MM-dd)
     */
    public static Date getTomorrow(Date date) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
        Date tomorrow = cal.getTime();

        return parseDate(tomorrow);
    }

    /**
     * 获取指定日期的后一天(yyyy-MM-dd)
     */
    public static String getAfterTomorrow(Date date, int nightCount) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + nightCount);
        Date tomorrow = cal.getTime();

        return formatDate(tomorrow);
    }

    /**
     * 获取日期范围（前闭后闭，即包含开始日期和结束日期）
     * 日期格式为: yyyy-MM-dd
     *
     * @return List<Date> 开始日期至结束日期的日期集合（含开始日期和结束日期）
     * @Param startDate 开始日期
     * @Param endDate 结束日期
     */
    public static List<Date> getDateRangeByStartDateAndEndDate(Date startDate, Date endDate) throws Exception {
        if (null != startDate && null != endDate) {
            List<Date> dateList = new ArrayList<Date>();

            startDate = parseDate(startDate);
            endDate = parseDate(endDate);

            dateList.add(parseDate(((Date) startDate.clone())));

            for (; startDate.compareTo(endDate) < 0; ) {
                Calendar cal = Calendar.getInstance();

                cal.setTime(startDate);
                cal.set(Calendar.DATE, (cal.get(Calendar.DATE) + 1));
                startDate = parseDate(cal.getTime());

                Date tempDate = (Date) startDate.clone();

                dateList.add(tempDate);
            }

            return dateList;
        }

        return null;
    }

    public static int daysBetween(Date startDate, Date endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        startDate = sdf.parse(sdf.format(startDate));
        endDate = sdf.parse(sdf.format(endDate));

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();

        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();

        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String calculateHourMinute(Date startDate, int clockHour) {
        int hourage = StringUtil.getIntByString(formatDateHour(startDate));

        int newClockHour = hourage + clockHour;

        String endDateStr = formatDate(startDate) + " " + newClockHour + ":" + formatDateMinute(startDate);

        return endDateStr;
    }

    public static String theDiffByDate(Date endDate, Date sysDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        endDate = sdf.parse(sdf.format(endDate));
        sysDate = sdf.parse(sdf.format(sysDate));

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        String dateBetween = "";

        // 获得两个时间的毫秒时间差异
        if (endDate.getTime() < sysDate.getTime()) {

            long diff = sysDate.getTime() - endDate.getTime();

            // 计算差多少天
            long day = diff / nd;
            // 计算差多少小时
            long hour = diff % nd / nh;
            // 计算差多少分钟
            long min = diff % nd % nh / nm;

            int days = Integer.parseInt(String.valueOf(day));
            int hours = Math.abs(Integer.parseInt(String.valueOf(hour)));
            int minute = Math.abs(Integer.parseInt(String.valueOf(min)));

            if (hours > 0 && minute > 0) {
                dateBetween = hours + "小时" + minute + "分钟";
            } else if (hours > 0 && minute <= 0) {
                dateBetween = hours + "小时";
            } else if (hours <= 0 && minute > 0) {
                dateBetween = minute + "分钟";
            } else if (hours <= 0 && minute <= 0) {
                dateBetween = "";
            }
        }

        return dateBetween;
    }

    public static Date reSetLeaveTime(Date leaveTime, int halfOfDay) throws Exception {

        Calendar c = Calendar.getInstance();

        if (leaveTime != null) {
            c.setTime(leaveTime);
            c.add(Calendar.MINUTE, halfOfDay * 60);
        }

        return c.getTime();
    }

    public static JSONArray getMinAndMaxValue(List<Date> arriveDateList, List<Date> leaveDateList) throws Exception {
        JSONArray dateList = new JSONArray();

        Date arriveDate = Collections.min(arriveDateList);
        Date leaveDate = Collections.max(leaveDateList);

        dateList.add(parseDateMinute(arriveDate));
        dateList.add(parseDateMinute(leaveDate));

        return dateList;
    }

    /**
     * 获取当前时间的前一月
     *
     * @return
     */
    public static String getPreMonth() {
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();

        return formatDate(m);
    }

    /**
     * 根据一个日期获取以当前日期的前n位和后n位
     */
    public static List<Date> buildLmPmsDateList(Date currentDate, int beforeDay, int totalDay) throws Exception {
        if (null == currentDate) {
            currentDate = DateUtil.getToday();
        }

        Date startDate = DateUtil.getDateBeforeNums(currentDate, beforeDay); // 开始时间
        Date endDate = DateUtil.getDateAfterNums(currentDate, (totalDay - beforeDay - 1)); // 结束时间

        return DateUtil.getDateRangeByStartDateAndEndDate(startDate, endDate);
    }
}
