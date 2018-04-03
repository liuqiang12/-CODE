package system.data.listener;

import utils.DevContext;
import utils.DevLog;
import utils.PropertyConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;

/**
 * Created by DELL on 2017/9/1.
 */
public class ContextListener implements ServletContextListener {

    public ContextListener(){
        System.out.println("调用了构造方法");
    }
    public void contextInitialized(ServletContextEvent event) {
        //配置接口地址
        /*System.out.println(" ----------创建了Context created on " +new Date() + "."+event.getServletContext());
        System.out.println(" ----------创建了Context created on " +new Date() + "."+event.getServletContext());
        PropertyUtils，
        System.out.println(" ----------创建了Context created on " +new Date() + "."+event.getServletContext());*/
        String ISP_WSDL_URL = PropertyConfig.getInstance().getPropertyValue("ISP_WSDL_URL");
        String REST_URL = PropertyConfig.getInstance().getPropertyValue("REST_URL");

        String COMMING_EXPIRE_TICKET_COUNTCONFIG = PropertyConfig.getInstance().getPropertyValue("COMMING_EXPIRE_TICKET_COUNT");
        String EGNORE_REDIS_SYNCH = PropertyConfig.getInstance().getPropertyValue("EGNORE_REDIS_SYNCH");
        String PROJECT_QUERY_USE_REDIS = PropertyConfig.getInstance().getPropertyValue("PROJECT_QUERY_USE_REDIS");
        String PROJECT_MANAGER_USE_REDIS = PropertyConfig.getInstance().getPropertyValue("PROJECT_MANAGER_USE_REDIS");
        /**ftp其他参数**/
        String FTP_ADDR =  PropertyConfig.getInstance().getPropertyValue("FTP_ADDR");
        String FTP_PORT =  PropertyConfig.getInstance().getPropertyValue("FTP_PORT");
        String FTP_USERNAME =  PropertyConfig.getInstance().getPropertyValue("FTP_USERNAME");
        String FTP_PASSWORD =  PropertyConfig.getInstance().getPropertyValue("FTP_PASSWORD");
        String FTP_TEMPFIELPATH =  PropertyConfig.getInstance().getPropertyValue("FTP_TEMPFIELPATH");
        String FTP_TEMPFIELPATH_ABSOLUTE =  PropertyConfig.getInstance().getPropertyValue("FTP_TEMPFIELPATH_ABSOLUTE");
        String FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE =  PropertyConfig.getInstance().getPropertyValue("FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE");
        String FTP_DIR =  PropertyConfig.getInstance().getPropertyValue("FTP_DIR");
        String IDC_UPLOADFILE_USE_FTP =  PropertyConfig.getInstance().getPropertyValue("IDC_UPLOADFILE_USE_FTP");

        String REMOTE_FTP_ADDR =  PropertyConfig.getInstance().getPropertyValue("REMOTE_FTP_ADDR");
        String REMOTE_FTP_PORT =  PropertyConfig.getInstance().getPropertyValue("REMOTE_FTP_PORT");
        String REMOTE_FTP_USERNAME =  PropertyConfig.getInstance().getPropertyValue("REMOTE_FTP_USERNAME");
        String REMOTE_FTP_DIR =  PropertyConfig.getInstance().getPropertyValue("REMOTE_FTP_DIR");
        String REMOTE_FTP_PASSWORD =  PropertyConfig.getInstance().getPropertyValue("REMOTE_FTP_PASSWORD");
        String LOCAL_FTP_TEMPFIELPATH =  PropertyConfig.getInstance().getPropertyValue("LOCAL_FTP_TEMPFIELPATH");
        String LOCAL_ISP_TEMPFIELPATH =  PropertyConfig.getInstance().getPropertyValue("LOCAL_ISP_TEMPFIELPATH");
        String ISP_TEMPFIELPATH =  PropertyConfig.getInstance().getPropertyValue("ISP_TEMPFIELPATH");

        String FTP_ISP_IP =  PropertyConfig.getInstance().getPropertyValue("FTP_ISP_IP");
        String FTP_ISP_USERNAME =  PropertyConfig.getInstance().getPropertyValue("FTP_ISP_USERNAME");
        String FTP_ISP_PWD =  PropertyConfig.getInstance().getPropertyValue("FTP_ISP_PWD");
        String FTP_ISP_PORT =  PropertyConfig.getInstance().getPropertyValue("FTP_ISP_PORT");
        String FTP_ISP_REMOTE_PATH =  PropertyConfig.getInstance().getPropertyValue("FTP_ISP_REMOTE_PATH");
        String FTP_ISP_TEMPLATE_FILE =  PropertyConfig.getInstance().getPropertyValue("FTP_ISP_TEMPLATE_FILE");
        String FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE =  PropertyConfig.getInstance().getPropertyValue("FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE");

        String LOCALFTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE =  PropertyConfig.getInstance().getPropertyValue("LOCALFTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE");


        String FTP_ONLINE_ADDR =  PropertyConfig.getInstance().getPropertyValue("FTP_ONLINE_ADDR");   //线上FTP地址  wcg
        Integer FTP_ONLINE_PORT =  Integer.valueOf(PropertyConfig.getInstance().getPropertyValue("FTP_ONLINE_PORT"));   //线上FTP端口  wcg
        String FTP_ONLINE_USERNAME =  PropertyConfig.getInstance().getPropertyValue("FTP_ONLINE_USERNAME");    //线上FTP账号  wcg
        String FTP_ONLINE_PASSWORD =  PropertyConfig.getInstance().getPropertyValue("FTP_ONLINE_PASSWORD");   //线上FTP密码  wcg
        String FTP_PATH_NAME =  PropertyConfig.getInstance().getPropertyValue("FTP_PATH_NAME");    //线上FTP服务器文件目录  wcg
        String FTP_DOWNLOAD_PATH_NAME =  PropertyConfig.getInstance().getPropertyValue("FTP_DOWNLOAD_PATH_NAME");    //下载的路径windows写 D:/    linux写 idc/attachment   wcg



        String EMAIL_ADRESS_FROM =  PropertyConfig.getInstance().getPropertyValue("EMAIL_ADRESS_FROM");    //邮件发件人地址  wcg
        String EMAIL_USER =  PropertyConfig.getInstance().getPropertyValue("EMAIL_USER");    //邮件发件人账号  wcg
        String EMAIL_PASSWORD =  PropertyConfig.getInstance().getPropertyValue("EMAIL_PASSWORD");    //邮件发件人密码  wcg
        String SERVER_MAIL_SMTP_HOST =  PropertyConfig.getInstance().getPropertyValue("SERVER_MAIL_SMTP_HOST");    //邮件服务器地址  wcg
        String SERVER_MAIL_SMTP_AUTH =  PropertyConfig.getInstance().getPropertyValue("SERVER_MAIL_SMTP_AUTH");    //邮件服务器  wcg
        String SERVER_MAIL_SMTP_PORT =  PropertyConfig.getInstance().getPropertyValue("SERVER_MAIL_SMTP_PORT");    //邮件服务器端口  wcg
        String SERVER_MAIL_SMTP_SSL_ENABLE =  PropertyConfig.getInstance().getPropertyValue("SERVER_MAIL_SMTP_SSL_ENABLE");    //邮件服务器  wcg

        DevLog.debug("****************************************************手动配置了接口地址://r//n"+ISP_WSDL_URL+"//r//n"+REST_URL+"//r//n start");
        if(EMAIL_ADRESS_FROM != null && !"".equals(EMAIL_ADRESS_FROM)){
            DevContext.EMAIL_ADRESS_FROM = EMAIL_ADRESS_FROM;
        }
        if(EMAIL_USER != null && !"".equals(EMAIL_USER)){
            DevContext.EMAIL_USER = EMAIL_USER;
        }
        if(EMAIL_PASSWORD != null && !"".equals(EMAIL_PASSWORD)){
            DevContext.EMAIL_PASSWORD = EMAIL_PASSWORD;
        }
        if(SERVER_MAIL_SMTP_HOST != null && !"".equals(SERVER_MAIL_SMTP_HOST)){
            DevContext.SERVER_MAIL_SMTP_HOST = SERVER_MAIL_SMTP_HOST;
        }
        if(SERVER_MAIL_SMTP_AUTH != null && !"".equals(SERVER_MAIL_SMTP_AUTH)){
            DevContext.SERVER_MAIL_SMTP_AUTH = SERVER_MAIL_SMTP_AUTH;
        }
        if(SERVER_MAIL_SMTP_PORT != null && !"".equals(SERVER_MAIL_SMTP_PORT)){
            DevContext.SERVER_MAIL_SMTP_PORT = SERVER_MAIL_SMTP_PORT;
        }
        if(SERVER_MAIL_SMTP_SSL_ENABLE != null && !"".equals(SERVER_MAIL_SMTP_SSL_ENABLE)){
            DevContext.SERVER_MAIL_SMTP_SSL_ENABLE = SERVER_MAIL_SMTP_SSL_ENABLE;
        }

        if(FTP_ONLINE_ADDR != null && !"".equals(FTP_ONLINE_ADDR)){
            DevContext.FTP_ONLINE_ADDR = FTP_ONLINE_ADDR;
        }
        if(FTP_ONLINE_PORT != null && !"".equals(FTP_ONLINE_PORT)){
            DevContext.FTP_ONLINE_PORT = FTP_ONLINE_PORT;
        }
        if(FTP_ONLINE_USERNAME != null && !"".equals(FTP_ONLINE_USERNAME)){
            DevContext.FTP_ONLINE_USERNAME = FTP_ONLINE_USERNAME;
        }
        if(FTP_ONLINE_PASSWORD != null && !"".equals(FTP_ONLINE_PASSWORD)){
            DevContext.FTP_ONLINE_PASSWORD = FTP_ONLINE_PASSWORD;
        }
        if(FTP_PATH_NAME != null && !"".equals(FTP_PATH_NAME)){
            DevContext.FTP_PATH_NAME = FTP_PATH_NAME;
        }
        if(FTP_DOWNLOAD_PATH_NAME != null && !"".equals(FTP_DOWNLOAD_PATH_NAME)){
            DevContext.FTP_DOWNLOAD_PATH_NAME = FTP_DOWNLOAD_PATH_NAME;
        }
        if(ISP_WSDL_URL != null && !"".equals(ISP_WSDL_URL)){
            DevContext.ISP_WSDL_URL = ISP_WSDL_URL;
        }
        if(REST_URL != null && !"".equals(REST_URL)){
            DevContext.REST_URL = REST_URL;
        }
        /*这个是必须要配置的.且URLPATH也是必须要配置的*/
        System.out.println("****************************************************手动配置了接口地址://r//n"+ISP_WSDL_URL+"//r//n"+REST_URL+"//r//n end");
        DevLog.debug("****************************************************手动配置了过期时间://r//n"+COMMING_EXPIRE_TICKET_COUNTCONFIG+"//r//n");
        if(COMMING_EXPIRE_TICKET_COUNTCONFIG != null && !"".equals(COMMING_EXPIRE_TICKET_COUNTCONFIG)){
            DevContext.COMMING_EXPIRE_TICKET_COUNT = Integer.valueOf(COMMING_EXPIRE_TICKET_COUNTCONFIG);
        }
        if(EGNORE_REDIS_SYNCH != null && !"".equals(EGNORE_REDIS_SYNCH)){
            DevContext.EGNORE_REDIS_SYNCH = EGNORE_REDIS_SYNCH;
        }
        if(PROJECT_QUERY_USE_REDIS != null && !"".equals(PROJECT_QUERY_USE_REDIS)){
            DevContext.PROJECT_QUERY_USE_REDIS = PROJECT_QUERY_USE_REDIS;
        }
        if(PROJECT_MANAGER_USE_REDIS != null && !"".equals(PROJECT_MANAGER_USE_REDIS)){
            DevContext.PROJECT_MANAGER_USE_REDIS = PROJECT_MANAGER_USE_REDIS;
        }
        if(FTP_ADDR != null && !"".equalsIgnoreCase(FTP_ADDR)){
            DevContext.FTP_ADDR = FTP_ADDR;
        }
        if(FTP_PORT != null && !"".equalsIgnoreCase(FTP_PORT)){
            DevContext.FTP_PORT = Integer.valueOf(FTP_PORT);
        }
        if(FTP_USERNAME != null){
            DevContext.FTP_USERNAME = FTP_USERNAME;
        }
        if(FTP_PASSWORD != null){
            DevContext.FTP_PASSWORD = FTP_PASSWORD;
        }
        if(FTP_TEMPFIELPATH != null){
            DevContext.FTP_TEMPFIELPATH = FTP_TEMPFIELPATH;
        }
        if(FTP_TEMPFIELPATH_ABSOLUTE != null){
            DevContext.FTP_TEMPFIELPATH_ABSOLUTE = FTP_TEMPFIELPATH_ABSOLUTE.toLowerCase();
        }

        if(FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE != null && !"".equalsIgnoreCase(FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE)){
            DevContext.FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE = FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE.toLowerCase();
        }

        if(FTP_DIR != null){
            DevContext.FTP_DIR = FTP_DIR;
        }

        if(REMOTE_FTP_ADDR != null && !"".equalsIgnoreCase(REMOTE_FTP_ADDR)){
            DevContext.REMOTE_FTP_ADDR = REMOTE_FTP_ADDR.toLowerCase();
        }

        if(REMOTE_FTP_PORT != null && !"".equalsIgnoreCase(REMOTE_FTP_PORT)){
            DevContext.REMOTE_FTP_PORT = Integer.valueOf(REMOTE_FTP_PORT.toLowerCase());
        }

        if(REMOTE_FTP_USERNAME != null){
            DevContext.REMOTE_FTP_USERNAME = REMOTE_FTP_USERNAME;
        }

        if(REMOTE_FTP_DIR != null){
            DevContext.REMOTE_FTP_DIR = REMOTE_FTP_DIR;
        }

        if(REMOTE_FTP_PASSWORD != null){
            DevContext.REMOTE_FTP_PASSWORD = REMOTE_FTP_PASSWORD;
        }

        if(LOCAL_FTP_TEMPFIELPATH != null){
            DevContext.LOCAL_FTP_TEMPFIELPATH = LOCAL_FTP_TEMPFIELPATH;
        }

        if(LOCAL_ISP_TEMPFIELPATH != null){
            DevContext.LOCAL_ISP_TEMPFIELPATH = LOCAL_ISP_TEMPFIELPATH;
        }

        if(ISP_TEMPFIELPATH != null){
            DevContext.ISP_TEMPFIELPATH = ISP_TEMPFIELPATH;
        }


        if(LOCALFTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE != null){
            DevContext.LOCAL_FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE = LOCALFTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE;
        }

        if(IDC_UPLOADFILE_USE_FTP != null){
            DevContext.IDC_UPLOADFILE_USE_FTP = IDC_UPLOADFILE_USE_FTP;
        }
        //asdfasfasdf
        if(FTP_ISP_IP != null){
            DevContext.FTP_ISP_IP = FTP_ISP_IP;
        }if(FTP_ISP_USERNAME != null){
            DevContext.FTP_ISP_USERNAME = FTP_ISP_USERNAME;
        }if(FTP_ISP_PWD != null){
            DevContext.FTP_ISP_PWD = FTP_ISP_PWD;
        }if(FTP_ISP_PORT != null){
            DevContext.FTP_ISP_PORT = FTP_ISP_PORT;
        }if(FTP_ISP_REMOTE_PATH != null){
            DevContext.FTP_ISP_REMOTE_PATH = FTP_ISP_REMOTE_PATH;
        }if(FTP_ISP_TEMPLATE_FILE != null){
            DevContext.FTP_ISP_TEMPLATE_FILE = FTP_ISP_TEMPLATE_FILE;
        }if(FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE != null){
            DevContext.FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE = FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE;
        }

        //redisManager
    }
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("--------销毁了Context destroyed on " + new Date() + ".");
    }
}
