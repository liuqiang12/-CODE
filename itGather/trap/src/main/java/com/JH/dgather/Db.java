package com.JH.dgather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.JH.dgather.comm.ConnectionManager;

public class Db {
	Logger logger = Logger.getLogger(Db.class);
//提取可以接收trap报文的设备
	public HashMap<String,DeviceInfo> getTrapDevice(){
		HashMap<String,DeviceInfo> deviceHs=null;
		DeviceInfo device = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = ConnectionManager.getConnection();
		String sql = "select routName,ipAddress from Net_DEVICE where status=0";//认为激活状态设备都是需要接收trap的
		try{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			deviceHs = new HashMap<String,DeviceInfo>();
			while(rs.next()){
				device = new DeviceInfo();
				device.setDeviceName(rs.getString("routName"));
				device.setIpaddress(rs.getString("ipAddress"));
				deviceHs.put(device.getIpaddress(), device);
			}
		}catch(Exception e){
			logger.error("提取设备信息失败！" );
			e.printStackTrace();
			
		}
		return deviceHs;
	}
}
