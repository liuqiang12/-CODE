package com.JH.dgather.frame.common.ping;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.log4j.Logger;
import org.opennms.netmgt.ping.PingResponseCallback;
import org.opennms.netmgt.ping.Pinger;

import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;

public class PingUtil {
	private static Logger logger = Logger.getLogger(PingUtil.class);

	public static boolean ping(String ip) throws IOException,
			InterruptedException {
		return Pinger.ping(InetAddress.getByName(ip),
				GloableDataArea.pingTimeout, GloableDataArea.pingRetries) != null;
	}

	public static void ping(String ip, PingResponseCallback cb)
			throws IOException {
		Pinger.ping(InetAddress.getByName(ip), GloableDataArea.pingTimeout,
				GloableDataArea.pingRetries, (short) 1, cb);
	}

	public static boolean ping(DeviceInfoBean device) {
		if(GloableDataArea.ignorePing){
			return false;
		}
		boolean result = false;
		try {
			result = PingUtil.ping(device.getIPAddress());
		} catch (Exception e) {
			logger.error(device.getDeviceName() + "设备ID："
					+ device.getDeviceId() + " ping " + device.getIPAddress()
					+ " 发生异常：" + e.getMessage());
		}
		if (!result) {
			logger.error(device.getDeviceName() + "设备ID："
					+ device.getDeviceId() + " ping " + device.getIPAddress()
					+ "测试结果：" + result);
		}
		return result;
	}

}
