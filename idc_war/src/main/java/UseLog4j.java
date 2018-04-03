import org.apache.log4j.*;
import org.apache.log4j.spi.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by DELL on 2017/8/30.
 */
public class UseLog4j {
    private static final String configFile = "log4j.xml";

    static{
        BasicConfigurator.configure();
    }

    public UseLog4j() {
        super();
    }

    public static String getConfigFile(){
        return configFile;
    }

    public static Logger getLogger(Class clazz){
        return Logger.getLogger(clazz);
    }

    public static Logger getLogger(String strClass){
        return Logger.getLogger(strClass);
    }

    public static Logger getLogger(String strClass,LoggerFactory loggerFactory){
        return Logger.getLogger(strClass, loggerFactory);
    }
}
