package com.idc.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by mylove on 2017/7/11.
 */
public class FlowUtil {
//    public static double ByteToKb(double bytes) {
//        return bytes / 1000;
//    }
//
//    public static long ByteToKb(long bytes) {
//        return bytes / 1000;
//    }
//
//    public static double KbToMb(double kbs) {
//        return kbs / 1000;
//    }
//
//    public static long KbToMb(long kbs) {
//        return kbs / 1000;
//    }
//
//    public static double MbToGb(double mbs) {
//        return KbToMb(mbs);
//    }
//
//    public static double byteToMb(double kbs) {
//        return KbToMb(ByteToKb(kbs));
//    }

    public static DecimalFormat df2 = new DecimalFormat("######0.00");
    public static DecimalFormat df4 = new DecimalFormat("######0.0000");
    public static DecimalFormat df1 = new DecimalFormat("######0.0");
    public static DecimalFormat df3 = new DecimalFormat("######0.000");

//    /***
//     * 利用率
//     *
//     * @param bytes
//     * @return
//     */
//    public static double usage(double bytes, double bandWidth) {
//        return Double.parseDouble(df4.format(KbToMb(ByteToKb(bytes)) * 8 / bandWidth));
//    }

    public static double format2(double number) {
        return Double.parseDouble(df2.format(number));
    }

    public static double format3(double number) {
        return Double.parseDouble(df3.format(number));
    }

    public static double format1(double number) {
        return Double.parseDouble(df1.format(number));
    }

    public static double bytesToMbps(double number) {
        return number * 8 / 1024 / 1024;
    }

    public static double bytesToKbps(double number) {
        return number * 8 / 1024;
    }
    public static double bandWidthUsage(double flows, Double bandWidth) {
        if(bandWidth==null||bandWidth==0){
            return 0;
        }
        return Double.parseDouble(df4.format(flows/bandWidth*100));
    }
    public static double bandWidthUsage(double flows, BigDecimal bandWidth) {
        return bandWidthUsage(flows,bandWidth==null?0:bandWidth.doubleValue());
    }
}
