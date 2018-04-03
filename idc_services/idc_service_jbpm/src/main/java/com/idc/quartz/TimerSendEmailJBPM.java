package com.idc.quartz;

import com.idc.model.IdcHisTicket;
import com.idc.model.SysUserinfo;
import com.idc.service.IdcContractService;
import com.idc.service.IdcHisTicketService;
import com.idc.service.SysUserinfoService;
import com.idc.utils.SendEmailEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.DevContext;
import utils.SendEmailUtils;

import java.util.List;
import java.util.Map;

/*
*
* wcg add 合同过期时，给客户发送邮件
*
* */

@Component
public class TimerSendEmailJBPM {

    @Autowired
    private IdcContractService idcContractService;
    @Autowired
    private SysUserinfoService sysUserinfoService;
    @Autowired
    private IdcHisTicketService idcHisTicketService;

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

    @Scheduled(cron ="00 00 01 * * ? ")   //每天凌晨一点执行一次
    public void sendEmail() throws Exception {
        //查询所有合同到期时间不足七天的工单号和剩余天数
        List<Map<String, Object>> emailContractList = idcContractService.queryContractRemainingDays();

        System.out.println("有 "+emailContractList.size()+" 个合同要到期了，我们需要发送"+emailContractList.size()+" 封邮件！");

        if(1==1 && emailContractList != null && emailContractList.size()>0){
            for (Map<String,Object> emailContract: emailContractList) {
                String ticket_inst_id = String.valueOf(emailContract.get("TICKET_INST_ID"));  //工单号
                String contractname = String.valueOf(emailContract.get("CONTRACTNAME"));  //合同名
                String apply_id = String.valueOf(emailContract.get("APPLY_ID"));  //申请人ID
                String apply_name = String.valueOf(emailContract.get("APPLY_NAME"));  //申请人名称
                String contractNo = String.valueOf(emailContract.get("CONTRACTNO"));   //合同号
                String customerName = String.valueOf(emailContract.get("CUSTOMER_NAME"));   //客户名称
                String contractStart = String.valueOf(emailContract.get("CONTRACTSTART"));   //合同开始时间
                String contractPeriod = String.valueOf(emailContract.get("CONTRACTPERIOD"));   //合同期限

                //判断，如果合同表没有申请人的ID，就通过工单ID查工单表把申请人ID查出来
                if(apply_id == null || apply_id == "null" || apply_id == "" || apply_id.length()<1){
                    apply_id = sysUserinfoService.queryApplyIDByTicket(ticket_inst_id);
                }

                //得到工单相关信息
                IdcHisTicket idcHisTicket = idcHisTicketService.getIdcHisTicketByIdTicketInstId(Long.valueOf(ticket_inst_id));
                String serialNumber = idcHisTicket.getSerialNumber();
                if(customerName == null || customerName == "null" || customerName.length() < 1){
                    customerName = idcHisTicket.getCustomerName();
                }

                //通过申请人的ID查询到他的Email
                SysUserinfo contractUserinfo = sysUserinfoService.queryUserById(apply_id);
                //收件人邮箱
                String receiverEmail = contractUserinfo.getEmail();

                TimerSendEmailJBPM timerSendEmailJBPM = new TimerSendEmailJBPM();

                // 设置发件人地址、收件人地址和邮件标题
                email_Title="合同名称为： 《  "+contractname+"  》 的合同即将到期提醒";
                // 设置邮件内容
                email_Text="尊敬的 " +apply_name+"  ， 您好，"
                        +"您客户的合同期限已不足30天，请注意查看！"
                        +" 客户名称："+customerName
                        +" ；工单号："+serialNumber
                        +" ；工单ID："+ticket_inst_id
                        +" ；合同名称：《 "+contractname
                        +" 》 ；合同编码："+contractNo
                        +" 。立即查看： http://223.85.57.86:88/idc/login.jsp"
                        +" 。 ";
                System.out.println("准备发邮件了！！！");
                //设置收件人的地址
                //email_Adress_To=email;

                //调用工具类，发送邮件
                if(1==1){
                    SendEmailUtils.sendEmail(DevContext.EMAIL_ADRESS_FROM,
                            receiverEmail,
                            email_Title,
                            email_Text,
                            DevContext.EMAIL_USER,
                            DevContext.EMAIL_PASSWORD,
                            DevContext.SERVER_MAIL_SMTP_HOST,
                            DevContext.SERVER_MAIL_SMTP_AUTH,
                            DevContext.SERVER_MAIL_SMTP_PORT,
                            Boolean.valueOf(DevContext.SERVER_MAIL_SMTP_SSL_ENABLE));
                }
                //测试用，测试完请删除
                return;
            }
        }
    }
}









/*

package com.idc.quartz;

import com.idc.model.SysUserinfo;
import com.idc.service.IdcContractService;
import com.idc.service.SysUserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

*/
/*
*
* wcg add 合同过期时，给客户发送邮件
*
* *//*

@Component
public class TimerSendEmailUtil{

    @Autowired
    private IdcContractService idcContractService;
    @Autowired
    private SysUserinfoService sysUserinfoService;

    private String host = ""; // smtp服务器
    private String from = ""; // 发件人地址
    private String to = ""; // 收件人地址
    private String affix = ""; // 附件地址
    private String affixName = ""; // 附件名称
    private String user = ""; // 用户名
    private String pwd = ""; // 密码
    private String subject = ""; // 邮件标题

    public void setAddress(String from, String to, String subject) {
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

    public void setAffix(String affix, String affixName) {
        this.affix = affix;
        this.affixName = affixName;
    }


    public void sendEmail() throws Exception {
        //查询所有合同到期时间不足七天的工单号和剩余天数
        List<Map<String, Object>> emailContractList = idcContractService.queryContractRemainingDays();
        if(emailContractList != null && emailContractList.size()>0){
            for (Map<String,Object> emailContract: emailContractList) {
                String ticket_inst_id = String.valueOf(emailContract.get("TICKET_INST_ID"));
                String contractname = String.valueOf(emailContract.get("CONTRACTNAME"));
                String apply_id = String.valueOf(emailContract.get("APPLY_ID"));
                String apply_name = String.valueOf(emailContract.get("APPLY_NAME"));

                //判断，如果合同表没有申请人的ID，就通过工单ID查工单表把申请人ID查出来
                if(apply_id == null || apply_id == "" || apply_id.length()<1){
                    apply_id = sysUserinfoService.queryApplyIDByTicket(ticket_inst_id);
                }

                //通过申请人的ID查询到
                SysUserinfo contractUserinfo = sysUserinfoService.queryUserById(apply_id);

                //sendBefore();



            }
        }

    }

    */
/*private String host = ""; // smtp服务器
    private String from = ""; // 发件人地址
    private String to = ""; // 收件人地址
    private String affix = ""; // 附件地址
    private String affixName = ""; // 附件名称
    private String user = ""; // 用户名
    private String pwd = ""; // 密码
    private String subject = ""; // 邮件标题*//*



    public  void sendBefore() throws Exception{

        File file = new File("/root/EmailFile/content.csv");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        String pushInfoContent = "成功了";

        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(file, true);
            writer.write(pushInfoContent + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TimerSendEmailUtil cn = new TimerSendEmailUtil();
        // 设置发件人地址、收件人地址和邮件标题
        cn.setAddress("发件人地址", "收件人地址", "一个带附件的JavaMail邮件(标题)");
        // 设置要发送附件的位置和标题
        cn.setAffix("附件的位置", "附件的文件名");
        // 设置smtp服务器以及邮箱的帐号和密码
        cn.send("smtp.qq.com", "帐号", "密码");

        if (file.exists()) {
            file.delete();
        }


    }

    public void send(String host, String user, String pwd) {
        this.host = host;
        this.user = user;
        this.pwd = pwd;

        Properties props = new Properties();

        // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", host);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.ssl.enable", true);
        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);

        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true);

        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(from));
            // 加载收件人地址
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));
            // 加载标题
            message.setSubject(subject);

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setText("第二种方法···");
            multipart.addBodyPart(contentPart);
            // 添加附件
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(affix);
            // 添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            // 添加附件的标题
            // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            messageBodyPart.setFileName("=?GBK?B?"
                    + enc.encode(affixName.getBytes()) + "?=");
            multipart.addBodyPart(messageBodyPart);

            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect(host, user, pwd);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

*/





