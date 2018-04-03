//package system.data.datetool;
//
//import org.springframework.format.Formatter;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
///**
// * Created by mylove on 2017/1/10.
// */
//public class DateFormatter implements Formatter<Date> {
//    @Override
//    public String print(Date object, Locale locale) {
//        return null;
//    }
//
//    @Override
//    public Date parse(String text, Locale locale) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = null;
//        try {
//            date = format.parse(text);
//        } catch (Exception e) {
//            format = new SimpleDateFormat("yyyy-MM-dd");
//            date = format.parse(text);
//        }
//        return date;
//    }
//
//}
