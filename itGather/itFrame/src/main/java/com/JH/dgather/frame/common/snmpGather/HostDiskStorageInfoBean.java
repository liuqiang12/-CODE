package com.JH.dgather.frame.common.snmpGather;

/**
 * @author yangDS
 *
 */
public class HostDiskStorageInfoBean {
	
	private String hrDeviceIndex;
	private int hrDiskStorageMedia = 1;
	private int hrDiskStorageCapacity;
	
	public String getHrDeviceIndex() {
		return hrDeviceIndex;
	}
	
	public void setHrDeviceIndex(String hrDeviceIndex) {
		this.hrDeviceIndex = hrDeviceIndex;
	}
	
	public int getHrDiskStorageMedia() {
		return hrDiskStorageMedia;
	}
	
	public void setHrDiskStorageMedia(String hrDiskStorageMedia) {
		
		if (hrDiskStorageMedia != null) {
			try {
				this.hrDiskStorageMedia = Integer.parseInt(hrDiskStorageMedia.trim());
				
			} catch (Exception e) {
				
			}
		}
		
	}
	
	public int getHrDiskStorageCapacity() {
		return hrDiskStorageCapacity;
	}
	
	public void setHrDiskStorageCapacity(String hrDiskStorageCapacity) {
		
		if (hrDiskStorageCapacity != null) {
			try {
				this.hrDiskStorageCapacity = Integer.parseInt(hrDiskStorageCapacity.trim());
			} catch (Exception e) {
				
			}
		}
		
	}
	
}
