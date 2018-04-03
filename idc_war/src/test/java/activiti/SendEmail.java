package activiti;//package activiti;

import com.idc.utils.SendEmailEnum;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import utils.DevContext;
import utils.SendEmailUtils;

/**
 * 单元测试
 */
// 告诉spring怎样执行
//@RunWith(SpringJUnit4ClassRunner.class)
// 来标明是web应用测试
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:config/spring-applicationContext.xml" })
// 声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
@TransactionConfiguration(defaultRollback = false)
@Transactional

public class SendEmail {

    private static String email_Adress_From = "15902857434@139.com";   //发件人
    private static String email_Adress_To = "295252343@qq.com";      //收件人
    private static String email_Title = "请输入合同标题";
    private static String email_Text = "请输入合同内容";

    //发送邮件的发送人信息
    private static String email_User = "15902857434@139.com";
    private static String email_Password = "hcw@IDC308"; //！！！QQ邮箱：授权码。 139邮箱：发件人的密码。

    //下面是邮件服务器信息
    private static String server_mail_smtp_host = "smtp.139.com";
    private static String server_mail_smtp_auth = "true";
    private static String server_mail_smtp_port = "465";
    private static Boolean server_mail_smtp_ssl_enable = true;

    @Test
    public void sedEmailTest1() throws Exception {

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
        SendEmailUtils.sendEmail(DevContext.EMAIL_ADRESS_FROM,
                SendEmailEnum.邮件收件人地址_测试用.value(),
                "标题",
                "文本内容，请输入",
                DevContext.EMAIL_USER,
                DevContext.EMAIL_PASSWORD,
                DevContext.SERVER_MAIL_SMTP_HOST,
                DevContext.SERVER_MAIL_SMTP_AUTH,
                DevContext.SERVER_MAIL_SMTP_PORT,
                Boolean.valueOf(DevContext.SERVER_MAIL_SMTP_SSL_ENABLE));

        System.out.println("结束！！！！！！！！");


    }

}