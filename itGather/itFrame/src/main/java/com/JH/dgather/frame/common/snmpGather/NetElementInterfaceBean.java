package com.JH.dgather.frame.common.snmpGather;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.snmp4j.smi.OctetString;

import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.util.RegexUtil;



/**
 * @author muyp
 *add ifName 2011-07-08
 */

public class NetElementInterfaceBean {
	private String ifIndex;
	private String ifDescr;
	private String ifType;
	private String ifPhysAddress;
	private String ifAdminStatus;
	private String ifOperStatus;
	private String ifLastChange;
	private String ifSpeed;
	private String ifHighSpeed;
	private String ifAlias;
	private String ifName;
	private String note;
	private boolean used;
	
	public String getNote() {
		
		return note;
	}
	
	public void setNote(String note) {
		
		this.note = note;
	}
	
	public boolean isUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	public String getIfIndex() {
		return ifIndex;
	}
	
	public void setIfIndex(String ifIndex) {
		this.ifIndex = ifIndex;
	}
	
	public String getIfDescr() {
		return ifDescr;
	}
	
	public void setIfDescr(String ifDescr) {
		if (RegexUtil.isHexString(ifDescr)) {
			ifDescr = GloableDataArea.su.getChineseStr(ifDescr);
		}
		int index = ifDescr.indexOf("'");
		String temp = null;
		if (index == -1) {
			temp = ifDescr;
		}
		else {
			temp = ifDescr.substring(ifDescr.indexOf("'") + 1, ifDescr.length());
			index = temp.indexOf("'");
			if (index > -1)
				temp = temp.substring(0, temp.indexOf("'"));
		}
		this.ifDescr = temp;
		setNote(ifDescr);
		
	}
	
	public String getIfType() {
		return ifType;
	}
	
	public void setIfType(String ifType) {
		this.ifType = ifType;
	}
	
	public String getIfPhysAddress() {
		return ifPhysAddress;
	}
	
	public void setIfPhysAddress(String ifPhysAddress) {
		if(ifPhysAddress == null||ifPhysAddress.equals("")){
			this.ifPhysAddress = "";
			return;
		}
		Pattern pattern = Pattern.compile("[0-9a-fA-F]{1,2}(:[0-9a-fA-F]{1,2}){5,}"); 
		Matcher matcher = pattern.matcher(ifPhysAddress); 
		//匹配一次 
		if(matcher.find()){
			this.ifPhysAddress = ifPhysAddress;
		}else{
			byte[] sb = ifPhysAddress.getBytes();
			String str="";
			String temp="";
			for (byte b : sb) {
				temp = Integer.toHexString(b);
				if(temp!=null && temp.length()==1){
					str = str + "0" + temp +":";
				}else{
					str = str + temp +":";
				}
			}
			this.ifPhysAddress = str.substring(0, str.length()-1);
		}
	}
	
	public String getIfAdminStatus() {
		return ifAdminStatus;
	}
	
	public void setIfAdminStatus(String ifAdminStatus) {
		this.ifAdminStatus = ifAdminStatus;
	}
	
	public String getIfOperStatus() {
		return ifOperStatus;
	}
	
	public void setIfOperStatus(String ifOperStatus) {
		this.ifOperStatus = ifOperStatus;
	}
	
	public String getIfLastChange() {
		return ifLastChange;
	}
	
	public void setIfLastChange(String ifLastChange) {
		this.ifLastChange = ifLastChange;
	}
	
	public String getIfSpeed() {
		return ifSpeed;
	}
	
	public void setIfSpeed(String ifSpeed) {
		this.ifSpeed = ifSpeed;
	}
	
	public String getIfAlias() {
		return ifAlias==null?"":ifAlias;
	}
	
	public void setIfAlias(String ifAlias) {
		if (ifAlias != null) {
			this.ifAlias = ifAlias;
			if (ifAlias.indexOf(":") != -1) {
				boolean isHexString = true;
				String[] fields = ifAlias.split(":");
				for (String str : fields) {
					if (str.length() != 2) {
						isHexString = false;
						break;
					}
				}
				if (isHexString) {
					try {
						this.ifAlias = new String(OctetString.fromHexString(
								ifAlias).getValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			//限制别名字段的最大长度为200
			if (this.ifAlias.length() > 200) {
				this.ifAlias = this.ifAlias.substring(0, 200);
			}
		}
	}
	
	public String getIfName() {
		return ifName;
	}
	
	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	public String getIfHighSpeed() {
		return ifHighSpeed;
	}

	public void setIfHighSpeed(String ifHighSpeed) {
		this.ifHighSpeed = ifHighSpeed;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("ifIndex:").append(ifIndex).append(",");
		buf.append("ifDescr:").append(ifDescr).append(",");
		buf.append("ifType:").append(ifType).append(",");
		buf.append("ifPhysAddress:").append(ifPhysAddress).append(",");
		buf.append("ifAdminStatus:").append(ifAdminStatus).append(",");
		buf.append("ifOperStatus:").append(ifOperStatus).append(",");
		buf.append("ifLastChange:").append(ifLastChange).append(",");
		buf.append("ifSpeed:").append(ifSpeed).append(",");
		buf.append("ifHighSpeed:").append(ifHighSpeed).append(",");
		buf.append("ifAlias:").append(ifAlias).append(",");
		buf.append("ifName:").append(ifName).append(",");
		buf.append("note:").append(note).append(",");
		buf.append("used:").append(used);
		return buf.toString();
	}
	
}
