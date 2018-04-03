import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Created by DELL on 2017/8/30.
 */
public class HelloLog4j {
    private static final Logger LOG = Logger.getLogger(HelloLog4j.class);
    public static void main(String[] args) {
        //2.读取配置文件，次步骤可以省略，系统会自动的默认的使用
        //BasicConfigurator.configure ()去src目录下自动快速地使用缺省Log4j环境log4j.properties
        //当log4j的配置文件没有在默认的src下面时，需要使用PropertyConfigurator.configure(String configFilename)
        //读取使用Java的特性文件log4j.properties编写的配置文件。
        //例：
        //PropertyConfigurator.configure(".\\src\\com\\log4j\\test\\log4j.properties");
        //读取XML形式的配置文件。
        DOMConfigurator.configure (".\\src\\com\\log4j\\test\\log4j.xml");
        //3.插入记录信息（格式化日志信息）
        //例：
        LOG.debug ("我是debug信息");
        LOG.info ("我是info信息");
        LOG.warn ("我是warn信息");
        LOG.error ("我是error信息");

    }
}
