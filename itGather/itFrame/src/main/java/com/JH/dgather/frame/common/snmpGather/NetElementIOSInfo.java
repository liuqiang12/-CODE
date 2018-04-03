package com.JH.dgather.frame.common.snmpGather;

/**
 * 设备的ios版本信息
 * 
 * @author yangDS
 *
 */
public class NetElementIOSInfo {
	
	private String image;
	private String family;
	private String version;
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		if (image.endsWith("$")) {
			this.image = image.substring(image.indexOf('$') + 1, image.lastIndexOf('$'));
		}
		
	}
	
	public String getFamily() {
		
		return family;
	}
	
	public void setFamily(String family) {
		if (family.endsWith("$")) {
			this.family = family.substring(family.indexOf('$') + 1, family.lastIndexOf('$'));
		}
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		if (version.endsWith("$")) {
			this.version = version.substring(version.indexOf('$') + 1, version.lastIndexOf('$'));
		}
	}
	
}
