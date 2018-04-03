package com.test;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/6/9.
 * 执行下一步
 */
public class Test {
    public static void main(String args[]) {
        //开始、结束
//        int start = 0;
//        int end = 540;
//        List<Integer> usetimeFinal = timehandler(start, end);
//        List<Integer> usetime = new ArrayList<Integer>();
//
//        //加班时间段--真实环境start-end为一组
//        int[] overtimeStart = new int[]{0, 480, 1080};
//        int[] overtimeEnd = new int[]{60, 540, 1140};
//
////      overtimeDTO otd = new overtimeDTO();
////      List<overtimeDTO> list = new ArrayList<overtimeDTO>();
//
//        int unit = overtimeStart.length;
//        int n = 0;
//        for (int i = 0; i < unit; i++) {
//            List<Integer> usetimeTemp = new ArrayList<Integer>();
//            usetimeTemp.addAll(usetimeFinal);
////          List<Integer> overtime = timehandler(list.get(i).getStart(), list.get(i).getEnd());
//            List<Integer> overtime = timehandler(overtimeStart[i], overtimeEnd[i]);
//            usetimeTemp.retainAll(overtime);
//
//            if (usetimeTemp.size() != 0) {
//                n++;//结合四分五裂原理，避免组合数组中间拼接产生的时间段
//                usetime.addAll(usetimeTemp);
//            }
//        }
//
//        int overtime = usetime.size() - n;
//        usetimeFinal.removeAll(usetime);
//        int normaltime = usetimeFinal.size() - 1 + n;//去除0点
//
//        System.out.println("加班时间：" + overtime * 15 + " , 正常时间：" + normaltime * 15);
        double d=100;
        for (int i = 0; i < 100; i++) {
            double t = Math.random() > 0.5 ? d + d * 0.1 : d - d * 0.1;
            System.out.println(t);
        }
//        try {
//            Date start = DateUtils.parseDate("2017/08/08 11:03:05", "yyyy/MM/dd HH:mm:ss");
//            Date end = DateUtils.parseDate("2017/08/08 12:01:05", "yyyy/MM/dd HH:mm:ss");
//            List<Long> times = getTimes(start, end);
//            for (Long time : times) {
//                System.out.println(DateFormatUtils.format(new Date(time), "yyyy/MM/dd HH:mm:ss"));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    public static List<Long> getTimes(Date start, Date end) {
        //DateFormatUtils.format(new Date(calendar.getTime().getTime()), "yyyy/MM/dd HH:mm:ss")
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        int  fix = calendar.get(Calendar.MINUTE)%5;
        if(fix!=0){
            calendar.add(Calendar.SECOND,-((fix)*60));
        }
//        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        List<Long> longs = new ArrayList<>();
        getLongTime(longs, calendar, end);
        return longs;
    }

    private static void getLongTime(List<Long> longs, Calendar calendar, Date end) {
        if (calendar.getTime().before(end)) {
            longs.add(calendar.getTime().getTime());
            calendar.add(Calendar.MINUTE, 5);
            getLongTime(longs, calendar, end);
        }
    }

    /**
     * 时间以指定15分钟分割
     *
     * @author 张恩备
     * @date 2016-8-25 下午02:26:58
     */
    public static List<Integer> timehandler(int start, int end) {
        List<Integer> point = new ArrayList<Integer>();
        for (int i = start / 15; i <= end / 15; i++) {
            point.add(i);
        }
        return point;
    }
}
