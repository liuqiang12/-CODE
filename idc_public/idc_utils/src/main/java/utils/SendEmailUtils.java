package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendEmailUtils {

    public static void sendEmail(String email_Adress_From,
                                 String email_Adress_To,
                                 String email_Title,
                                 String email_Text,
                                 String email_User,
                                 String email_Password,
                                 String server_mail_smtp_host,
                                 String server_mail_smtp_auth,
                                 String server_mail_smtp_port,
                                 Boolean server_mail_smtp_ssl_enable
                                 ){

        Properties props = new Properties();

        // 设置发送邮件的邮件服务器的属性（这里使用qq的smtp服务器）
        props.put("mail.smtp.host", server_mail_smtp_host);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证
        props.put("mail.smtp.auth", server_mail_smtp_auth);
        props.put("mail.smtp.port", server_mail_smtp_port);
        props.put("mail.smtp.ssl.enable", server_mail_smtp_ssl_enable);

        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);

        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true);

        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);

        if(true){
            try {
                // 加载发件人地址
                message.setFrom(new InternetAddress(email_Adress_From));
                // 加载收件人地址
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                        email_Adress_To));
                // 加载标题
                message.setSubject(email_Title);

                // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
                Multipart multipart = new MimeMultipart();

                // 设置邮件的文本内容
                BodyPart contentPart = new MimeBodyPart();

                contentPart.setText(email_Text);
                multipart.addBodyPart(contentPart);

                // 将multipart对象放到message中
                message.setContent(multipart);
                // 保存邮件
                message.saveChanges();
                // 发送邮件
                Transport transport = session.getTransport("smtp");
                // 连接服务器的邮箱
                transport.connect(server_mail_smtp_host, email_User, email_Password);
                // 把邮件发送出去
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                System.out.println("邮件发送成功！！！");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("邮件发关失败");
            }
        }
    }
}
