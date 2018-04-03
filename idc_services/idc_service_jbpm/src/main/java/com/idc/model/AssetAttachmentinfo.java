package com.idc.model;import javax.persistence.Column;import javax.persistence.GeneratedValue;import javax.persistence.Id;import java.io.Serializable;import java.text.SimpleDateFormat;import java.util.Date;/** * <br> * <b>实体类</b><br> * <b>功能：业务表</b>ASSET_ATTACHMENTINFO:ASSET_ATTACHMENTINFO<br> * <b>作者：Administrator</b><br> * <b>日期：</b> Jul 05,2017 <br> * <b>版权所有：<b>版权所有(C) 2016<br> */@SuppressWarnings("serial")public class AssetAttachmentinfo implements Serializable {			public static final String tableName = "ASSET_ATTACHMENTINFO";	        @Id@GeneratedValue 	 	private Long id; //   主键 					private String attachName; //   附件名称	private String ftpAttachName; //   FTP附件名称ftp_attach_name	private String ftpInfo; //FTP信息	private String logicTablename; //   逻辑表名称 	 					private String logicColumn; //   逻辑列名称 	 					private String attachSuffix; //   附件后缀 	 					private Long prodInstId; //   产品实例id	private Long attachSize; //   附件大小	private String relationalValue; //   弱关联[业务子表列值]	 					private byte[] attachByte; //   附件二进制[扩展信息,一般不使用该字段]	private String aesAttachByte; //   附件二进制字符串[加密压缩后的数据]	private byte[] aesAttachByteForceByte; //   附件二进制[加密压缩后的数据]	 					private Long createUserid; //   创建人ID 	 					private Date createTime; //   创建时间		private String createTimeStr; //   创建时间 [日期格式化后的字符串]	private Long ticketInstId;//增加工单id字段信息	private String nick;//增加工单id字段信息	private String author;//增加工单id字段信息	private String attachmentType;// 添加资源附件("1111")；添加IDC_ISP附件("2222"),；添加其他附件("3333"),；添加合同附件("4444"),；	private String attachmentMaker;  // 资源操作人的信息	/************************get set method**************************/	public String getAttachmentMaker() {		return attachmentMaker;	}	public void setAttachmentMaker(String attachmentMaker) {		this.attachmentMaker = attachmentMaker;	}	public String getAttachmentType() {		return attachmentType;	}	public void setAttachmentType(String attachmentType) {		this.attachmentType = attachmentType;	}	public static String getTableName() {		return tableName;	}	public String getAesAttachByte() {		return aesAttachByte;	}	public void setAesAttachByte(String aesAttachByte) {		this.aesAttachByte = aesAttachByte;	}	public byte[] getAesAttachByteForceByte() {		return aesAttachByteForceByte;	}	public void setAesAttachByteForceByte(byte[] aesAttachByteForceByte) {		this.aesAttachByteForceByte = aesAttachByteForceByte;	}	public void setCreateTimeStr(String createTimeStr) {		this.createTimeStr = createTimeStr;	}	public Long getTicketInstId() {		return ticketInstId;	}	public void setTicketInstId(Long ticketInstId) {		this.ticketInstId = ticketInstId;	}	public Long getProdInstId() {		return prodInstId;	}	public void setProdInstId(Long prodInstId) {		this.prodInstId = prodInstId;	}	public Long getId() {	    return this.id;	} 		@Column(name = "ID" , columnDefinition="主键" , nullable =  false, length = 22)	public void setId(Long id) {	    this.id=id;	} 		public String getAttachName() {	    return this.attachName;	} 		@Column(name = "ATTACH_NAME" , columnDefinition="附件名称" , nullable =  false, length = 80)	public void setAttachName(String attachName) {	    this.attachName=attachName;	} 		public String getLogicTablename() {	    return this.logicTablename;	} 		@Column(name = "LOGIC_TABLENAME" , columnDefinition="逻辑表名称" , nullable =  false, length = 30)	public void setLogicTablename(String logicTablename) {	    this.logicTablename=logicTablename;	} 		public String getLogicColumn() {	    return this.logicColumn;	} 		@Column(name = "LOGIC_COLUMN" , columnDefinition="逻辑列名称" , nullable =  false, length = 30)	public void setLogicColumn(String logicColumn) {	    this.logicColumn=logicColumn;	} 		public String getAttachSuffix() {	    return this.attachSuffix;	} 		@Column(name = "ATTACH_SUFFIX" , columnDefinition="附件后缀" , nullable =  false, length = 10)	public void setAttachSuffix(String attachSuffix) {	    this.attachSuffix=attachSuffix;	} 		public Long getAttachSize() {	    return this.attachSize;	} 		@Column(name = "ATTACH_SIZE" , columnDefinition="附件大小" , nullable =  false, length = 22)	public void setAttachSize(Long attachSize) {	    this.attachSize=attachSize;	} 		public String getRelationalValue() {	    return this.relationalValue;	} 		@Column(name = "RELATIONAL_VALUE" , columnDefinition="弱关联[业务子表列值]" , nullable =  false, length = 240)	public void setRelationalValue(String relationalValue) {	    this.relationalValue=relationalValue;	} 		public byte[] getAttachByte() {	    return this.attachByte;	} 		@Column(name = "ATTACH_BYTE" , columnDefinition="附件二进制[扩展信息,一般不使用该字段]" , nullable =  false, length = 4000)	public void setAttachByte(byte[] attachByte) {	    this.attachByte=attachByte;	} 		public Long getCreateUserid() {	    return this.createUserid;	} 		@Column(name = "CREATE_USERID" , columnDefinition="创建人ID" , nullable =  false, length = 22)	public void setCreateUserid(Long createUserid) {	    this.createUserid=createUserid;	} 		public Date getCreateTime() {	    return this.createTime;	} 		@Column(name = "CREATE_TIME" , columnDefinition="创建时间" , nullable =  false, length = 11)	public void setCreateTime(Date createTime) {	    this.createTime=createTime;	}		public String getCreateTimeStr() {	    return this.createTimeStr;	} 		public void setCreateTimeStr(Date createTime) {		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		String date_ = null;		try {			date_ = sdf.format(createTime);		} catch (Exception e) {			e.printStackTrace();		}		this.createTimeStr = date_;	}	public String getFtpAttachName() {		return ftpAttachName;	}	public void setFtpAttachName(String ftpAttachName) {		this.ftpAttachName = ftpAttachName;	}	public String getFtpInfo() {		return ftpInfo;	}	public void setFtpInfo(String ftpInfo) {		this.ftpInfo = ftpInfo;	}	@Override	public String toString() {			return  "assetAttachmentinfo [id = "+this.id+",attachName = "+this.attachName+",logicTablename = "+this.logicTablename+",logicColumn = "+this.logicColumn+",attachSuffix = "+this.attachSuffix+		",attachSize = "+this.attachSize+",relationalValue = "+this.relationalValue+",attachByte = "+this.attachByte+",createUserid = "+this.createUserid+",createTime = "+this.createTime+		" ]"; 	}	public String getNick() {		return nick;	}	public void setNick(String nick) {		this.nick = nick;	}	public String getAuthor() {		return author;	}	public void setAuthor(String author) {		this.author = author;	}}