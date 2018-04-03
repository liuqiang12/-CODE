package com.JH.dgather.frame.common.snmpGather;

/**
 * 主机文件系统信息表
 * 
 * @author yangDS
 *
 */
public class HostFSInfoBean {
	
	private String hrFSIndex;//索引
	private String hrFSType;//文件类型 fat ntfs etc.
	private String hrFSAccess;//文件的访问类型 读写权限
	private String hrFSBootable;//文件是否是主分区(是否可以启动系统)
	private String hrFSStorageIndex;//关联磁盘信息表的StorageIndex
	private String hrFSLastFullBackupDate;//文件最近一次全备份时间
	private String hrFSLastPartialBackupDate;//文件最近一次部分备份时间
	
	public String getHrFSIndex() {
		return hrFSIndex;
	}
	
	public void setHrFSIndex(String hrFSIndex) {
		this.hrFSIndex = hrFSIndex;
	}
	
	public String getHrFSType() {
		return hrFSType;
	}
	
	public void setHrFSType(String hrFSType) {
		this.hrFSType = hrFSType;
	}
	
	public String getHrFSAccess() {
		return hrFSAccess;
	}
	
	public void setHrFSAccess(String hrFSAccess) {
		this.hrFSAccess = hrFSAccess;
	}
	
	public String getHrFSBootable() {
		return hrFSBootable;
	}
	
	public void setHrFSBootable(String hrFSBootable) {
		this.hrFSBootable = hrFSBootable;
	}
	
	public String getHrFSStorageIndex() {
		return hrFSStorageIndex;
	}
	
	public void setHrFSStorageIndex(String hrFSStorageIndex) {
		this.hrFSStorageIndex = hrFSStorageIndex;
	}
	
	public String getHrFSLastFullBackupDate() {
		return hrFSLastFullBackupDate;
	}
	
	public void setHrFSLastFullBackupDate(String hrFSLastFullBackupDate) {
		this.hrFSLastFullBackupDate = hrFSLastFullBackupDate;
	}
	
	public String getHrFSLastPartialBackupDate() {
		return hrFSLastPartialBackupDate;
	}
	
	public void setHrFSLastPartialBackupDate(String hrFSLastPartialBackupDate) {
		this.hrFSLastPartialBackupDate = hrFSLastPartialBackupDate;
	}
	
}
