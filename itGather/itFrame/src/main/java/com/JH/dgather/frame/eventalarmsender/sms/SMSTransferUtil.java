package com.JH.dgather.frame.eventalarmsender.sms;

import inf.SmsService;
import inf.SmsServiceFactory;

import java.util.Date;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.util.DateFormate;

public class SMSTransferUtil {
	private static Logger logger = Logger.getLogger(SMSTransferUtil.class);

	/**
	 * 短信发送
	 * @param phoneNumber
	 * @param message
	 * @param sender
	 */
	public static int smsSender(String phoneNumber, String message) {
		Date d = new Date();
		SmsService smsService = SmsServiceFactory.getInstance().getService();
		String sender = "sys0000003"; //发送人,输入：’sys0000003’
		String sendtext = message;
		String flagno = "0030"; //短信业务类型，每个应用需统筹分配,不可空,（网络监控短信，输入：’0030’)
		String gwid = DateFormate.dateToStr(d, "yyyyMMdd") + "34"; //短信批次，批次号需统筹分配,不可空（格式:yyyymmdd+批次号, “2013062430”为客户界面发的批次） （网络监控短信，输入：yyyymmdd+’34’）
		String chdrnum = ""; //关联的保单号,可空
		String back_no = ""; //备用键值，可以记录产生短信的相关业务主键，可空
		String ind04 = "";

		int flag = smsService.sendFamilyPolicyRealTimeMessage(phoneNumber, sendtext, sender, flagno, gwid, chdrnum, back_no, ind04);
		String str = transCode(flag);
		logger.error("[Controller] 短信结果: " + str + ", code: " + flag + ", 短信内容: " + message);
		return flag;
	}

	public static String transCode(int flag) {
		String str = "";
		switch (flag) {
		case 1:
			str = "发送成功";
			break;
		case 0:
			str = "发送失败：系统原因";
			break;
		case 4:
			str = "发送失败：手机号为黑名单";
			break;
		case 7:
			str = "发送失败：手机号非法";
			break;
		case 8:
			str = "发送失败：短信内容为空或其他原因";
			break;
		default:
			str = "发送失败: 其它原因";
			break;
		}
		return str;
	}

	public static void main(String[] args) {
		int flag = SMSTransferUtil.smsSender("18620790245", "ICS测试短信发送平台");
		System.out.println(">>>>>发送手机号：18620790245, 平台反馈结果: " + flag + ", " + transCode(flag));
		flag = SMSTransferUtil.smsSender("1519821128", "ICS测试短信发送平台");
		System.out.println(">>>>> 发送手机号：1519821128, 平台反馈结果: " + flag + ", " + transCode(flag));
	}
}
