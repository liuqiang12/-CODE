package com.JH.dgather.frame.common.snmpGather;

import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.util.RegexUtil;

/**
 * 
 * 主机磁盘容量信息bean
 * 
 * @author yangDS
 *
 */
public class HostStorageInfoBean {
	
	private String hrStorageIndex;//磁盘index；
	private String hrStorageType;//磁盘类型；
	private String hrStorageDescr;//磁盘名称或描述
	private int hrStorageAllocationUnits;//磁盘分配单元（Byte）
	private int hrStorageSize;//磁盘大小（Byte）
	private int hrStorageUsed;//已用大小
	
	public String getHrStorageIndex() {
		return hrStorageIndex;
	}
	
	public void setHrStorageIndex(String hrStorageIndex) {
		this.hrStorageIndex = hrStorageIndex;
	}
	
	public String getHrStorageType() {
		return hrStorageType;
	}
	
	public void setHrStorageType(String hrStorageType) {
		if (hrStorageType != null && hrStorageType.charAt(0) == '.') {
			try {
				this.hrStorageType = hrStorageType.substring(1);
			} catch (Exception e) {
				
			}
			
		}
		else {
			this.hrStorageType = hrStorageType;
		}
		
	}
	
	public String getHrStorageDescr() {
		
		return hrStorageDescr;
	}
	
	public void setHrStorageDescr(String hrStorageDescr) {
		if (hrStorageDescr == null)
			return;
		if (RegexUtil.isHexString(hrStorageDescr)) {
			hrStorageDescr = GloableDataArea.su.getChineseStr(hrStorageDescr);
		}
		if (hrStorageDescr.length() > 500)
			hrStorageDescr = hrStorageDescr.substring(0, 500);
		this.hrStorageDescr = hrStorageDescr;
	}
	
	public int getHrStorageAllocationUnits() {
		return hrStorageAllocationUnits;
	}
	
	public void setHrStorageAllocationUnits(String hrStorageAllocationUnits) {
		
		if (hrStorageAllocationUnits == null) {
			this.hrStorageAllocationUnits = 0;
		}
		else {
			
			hrStorageAllocationUnits = hrStorageAllocationUnits.trim();
			try {
				this.hrStorageAllocationUnits = (Integer.parseInt(hrStorageAllocationUnits)) >> 10;//将数值计算为KB
				
			} catch (Exception e) {
				
			}
		}
		
	}
	
	public int getHrStorageSize() {
		
		return (hrStorageSize * this.hrStorageAllocationUnits) >> 10;//单位变为MB
	}
	
	public void setHrStorageSize(String hrStorageSize) {
		
		if (hrStorageSize == null) {
			this.hrStorageSize = 0;
		}
		else {
			this.hrStorageSize = Integer.parseInt(hrStorageSize);
		}
		
	}
	
	public int getHrStorageUsed() {
		return (hrStorageUsed * this.hrStorageAllocationUnits) >> 10;//单位变为MB
	}
	
	public void setHrStorageUsed(String hrStorageUsed) {
		
		if (hrStorageUsed == null) {
			
			this.hrStorageUsed = 0;
			
		}
		else {
			this.hrStorageUsed = Integer.parseInt(hrStorageUsed);
		}
		
	}
	
}
