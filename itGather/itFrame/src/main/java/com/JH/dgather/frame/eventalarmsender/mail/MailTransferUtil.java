package com.JH.dgather.frame.eventalarmsender.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.JH.dgather.frame.eventalarmsender.db.bean.ServiceBean;

public class MailTransferUtil {
	public Logger logger = Logger.getLogger(MailTransferUtil.class.toString());

	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator(String userName, String password) {
			authentication = new PasswordAuthentication(userName, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}

	public MailTransferUtil() {
	}

	/**
	 * 发送邮件信息
	 * 
	 * @param mailhost 发送邮箱smtp
	 * @param from 发送邮箱地址
	 * @param userName 发送用户名
	 * @param password 发送用户密码
	 * @param to 目标邮箱地址
	 * @param cc 抄送邮箱地址
	 * @param subject 标题
	 * @param message 邮件内容
	 * @throws SendFailedException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	public int sendMessage(ServiceBean serviceBean,String to,String cc, String subject, String message) throws SendFailedException, MessagingException, UnsupportedEncodingException {
		int flag = 0;
		logger.info("发送邮件message: " + message);
		try {
			String mailhost = serviceBean.getServiceAPIConn();
			String from = serviceBean.getServicAPIIde();
			String userName = serviceBean.getServiceLoginName();
			String password = serviceBean.getServcieLoginPwd();
			String personal = serviceBean.getPersonal();
			String auth = serviceBean.getServiceAuth();
			String port = serviceBean.getServicePort();
			String ssl = serviceBean.getSsl();
			String socketFactory = serviceBean.getSocketFactory();
			
			MailTransferUtil.Authenticator authenticator = new Authenticator(userName, password);
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
			properties.setProperty("mail.smtp.host", mailhost);
			properties.setProperty("mail.smtp.auth", auth);
			properties.setProperty("mail.smtp.port", port);

			if(ssl.equalsIgnoreCase("true")){
				properties.setProperty("mail.smtp.socketFactory.class",socketFactory);
				properties.setProperty("mail.smtp.ssl", ssl); 
			}
			
			Properties props = System.getProperties();
			if (props.getProperty("mail.smtp.host") == null) {
				props.put("mail.smtp.host", mailhost);
			}
			
			//			Session session = Session.getDefaultInstance(props, null);
			Session session = Session.getInstance(properties, authenticator);
			MimeMessage msg = new MimeMessage(session);
			// Add the from address only iff it is not null
			if (!(from.equals(null)) && !(from.trim().equals(""))) {
				InternetAddress address1 = null;
				if(!(personal.equals(null)) && !(personal.trim().equals(""))){
				    address1 = new InternetAddress(from,personal);
				}else{
					address1 = new InternetAddress(from);
				}
				msg.setFrom(address1);
			}

			// Add the to address to the recipient list only if it is not null
			if (!(to.equals(null)) && !(to.trim().equals(""))) {
				InternetAddress[] taddress = InternetAddress.parse(to);
				msg.setRecipients(Message.RecipientType.TO, taddress);
			}

			// Add the Cc address to the recipient list only if it is not null
			if (cc != null && cc.trim().equals("") == false) {
				StringTokenizer str = new StringTokenizer(cc, ",");

				int i = 0;
				String temp = "";
				InternetAddress[] ccaddress = new InternetAddress[str.countTokens()];
				while (str.hasMoreTokens()) {
					temp = str.nextToken();
					ccaddress[i] = new InternetAddress(temp);
					i++;
				}
				msg.setRecipients(Message.RecipientType.CC, ccaddress);
			}

			String charset = "UTF-8";
			// set the subject
			//由于乌鲁木齐的邮件系统只能查看标题，因此这里把内容发送到标题了
			if(serviceBean.isTextSubject()){
			    msg.setSubject(message, charset);
			}else{
				msg.setSubject(subject, charset);
			}
			msg.setText(message, charset);
			try {
				// send the message
				Transport.send(msg);
				flag = 1;
			} catch (SendFailedException re) {
				// if the message could not be sent to some or any of the
				// recipients.
				flag = 0;
				//logger.warning(re.getMessage());
				re.printStackTrace();
				throw re;
			}
		} catch (MessagingException mex) {
			flag = 0;
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
			throw mex;
		}

		return flag;
	}
}
