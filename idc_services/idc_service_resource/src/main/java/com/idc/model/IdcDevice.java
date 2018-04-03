package com.idc.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_DEVICE:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 01,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcDevice implements Serializable {

    public static final String tableName = "IDC_DEVICE";

    private Integer deviceclass; //   设备类别1-网络 2-主机 3-MCB

    private String vendor; //   厂商

    private String owner; //   联系人

    private Long uinstall; //   安装位置(U)

    private Long uheight; //   设备高度(U)

    private String ownertype; //   产权性质1-自建 2-租用 3-客户 4-自有业务

    private String pwrPowertype; //   电源类型,Dict:AC,AC;DC,DC

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp uplinedate; //   上架日期

    private String uplinedateStr; //   上架日期 [日期格式化后的字符串]

    private String description; //   设备描述

    private Long power; //   设备功耗

    private String phone; //   联系人电话

    @Id@GeneratedValue
    private Long deviceid; //

    private String name; //   设备名称

    private String code; //   设备编码

    private String businesstypeId; //   设备类型

    private Long rackId; //   所属机架

    private Long status; //   设备状态20-可用 30-空闲 50-预占 55-已停机 60-在服 110-不可用

    private Long ticketId; //   工单编号

    private String model; //   规格

    private Long customerid;//客户ID

    private String customername;//客户名称

    private String deviceVersion;//设备版本号

    private Date insurancedate;//出保时间

    private String insurancedateStr;//出保时间str

    private Integer isdelete;//是否删除  0-未删除   1-已删除

    private Integer isvirtualdevice;//1-不是虚拟设备  2-是虚拟设备

    private NetDevice netDevice;//网络设备详细信息

    private IdcHost idcHost;//主机设备信息

    /************************get set method**************************/

    public NetDevice getNetDevice() {
        return netDevice;
    }

    public void setNetDevice(NetDevice netDevice) {
        this.netDevice = netDevice;
    }

    public IdcHost getIdcHost() {
        return idcHost;
    }

    public void setIdcHost(IdcHost idcHost) {
        this.idcHost = idcHost;
    }

    public Integer getDeviceclass() {
        return this.deviceclass;
    }

    @Column(name = "DEVICECLASS" , columnDefinition="设备类别1-网络 2-主机 3-MCB" , nullable =  false, length = 22)
    public void setDeviceclass(Integer deviceclass) {
        this.deviceclass=deviceclass;
    }

    public String getVendor() {
        return this.vendor;
    }

    @Column(name = "VENDOR" , columnDefinition="厂商" , nullable =  false, length = 64)
    public void setVendor(String vendor) {
        this.vendor=vendor;
    }

    public String getOwner() {
        return this.owner;
    }

    @Column(name = "OWNER" , columnDefinition="联系人" , nullable =  false, length = 64)
    public void setOwner(String owner) {
        this.owner=owner;
    }

    public Long getUinstall() {
        return this.uinstall;
    }

    @Column(name = "UINSTALL" , columnDefinition="安装位置(U)" , nullable =  false, length = 22)
    public void setUinstall(Long uinstall) {
        this.uinstall=uinstall;
    }

    public Long getUheight() {
        return this.uheight;
    }

    @Column(name = "UHEIGHT" , columnDefinition="设备高度(U)" , nullable =  false, length = 22)
    public void setUheight(Long uheight) {
        this.uheight=uheight;
    }

    public String getOwnertype() {
        return this.ownertype;
    }

    @Column(name = "OWNERTYPE" , columnDefinition="产权性质1-自建 2-租用 3-客户 4-自有业务" , nullable =  false, length = 64)
    public void setOwnertype(String ownertype) {
        this.ownertype=ownertype;
    }

    public String getPwrPowertype() {
        return this.pwrPowertype;
    }

    @Column(name = "PWR_POWERTYPE" , columnDefinition="电源类型,Dict:AC,AC;DC,DC" , nullable =  false, length = 64)
    public void setPwrPowertype(String pwrPowertype) {
        this.pwrPowertype=pwrPowertype;
    }

    public Timestamp getUplinedate() {
        return this.uplinedate;
    }

    @Column(name = "UPLINEDATE" , columnDefinition="上架日期" , nullable =  false, length = 7)
    public void setUplinedate(Timestamp uplinedate) {
        this.uplinedate=uplinedate;
    }

    public String getDescription() {
        return this.description;
    }

    @Column(name = "DESCRIPTION" , columnDefinition="设备描述" , nullable =  false, length = 500)
    public void setDescription(String description) {
        this.description=description;
    }

    public Long getPower() {
        return this.power;
    }

    @Column(name = "POWER" , columnDefinition="设备功耗" , nullable =  false, length = 22)
    public void setPower(Long power) {
        this.power=power;
    }

    public String getPhone() {
        return this.phone;
    }

    @Column(name = "PHONE" , columnDefinition="联系人电话" , nullable =  false, length = 255)
    public void setPhone(String phone) {
        this.phone=phone;
    }

    public Long getDeviceid() {
        return this.deviceid;
    }

    @Column(name = "DEVICEID" , nullable =  false, length = 22)
    public void setDeviceid(Long deviceid) {
        this.deviceid=deviceid;
    }

    public String getName() {
        return this.name;
    }

    @Column(name = "NAME" , columnDefinition="设备名称" , nullable =  false, length = 64)
    public void setName(String name) {
        this.name=name;
    }

    public String getCode() {
        return this.code;
    }

    @Column(name = "CODE" , columnDefinition="设备编码" , nullable =  false, length = 64)
    public void setCode(String code) {
        this.code=code;
    }

    public String getBusinesstypeId() {
        return this.businesstypeId;
    }

    @Column(name = "BUSINESSTYPE_ID" , columnDefinition="设备类型" , nullable =  false, length = 64)
    public void setBusinesstypeId(String businesstypeId) {
        this.businesstypeId=businesstypeId;
    }

    public Long getRackId() {
        return this.rackId;
    }

    @Column(name = "RACK_ID" , columnDefinition="所属机架" , nullable =  false, length = 22)
    public void setRackId(Long rackId) {
        this.rackId=rackId;
    }

    public Long getStatus() {
        return this.status;
    }

    @Column(name = "STATUS" , columnDefinition="设备状态20-可用 30-空闲 50-预占 55-已停机 60-在服 110-不可用" , nullable =  false, length = 22)
    public void setStatus(Long status) {
        this.status=status;
    }

    public Long getTicketId() {
        return this.ticketId;
    }

    @Column(name = "TICKET_ID" , columnDefinition="工单编号" , nullable =  false, length = 22)
    public void setTicketId(Long ticketId) {
        this.ticketId=ticketId;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public Date getInsurancedate() {
        return insurancedate;
    }

    public void setInsurancedate(Date insurancedate) {
        this.insurancedate = insurancedate;
    }

    public String getModel() {
        return this.model;
    }

    @Column(name = "MODEL" , columnDefinition="规格" , nullable =  false, length = 64)
    public void setModel(String model) {
        this.model=model;
    }

    public String getUplinedateStr() {
        return this.uplinedateStr;
    }

    public void setUplinedateStr(Date uplinedate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_ = null;
        try {
            date_ = sdf.format(uplinedate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.uplinedateStr = date_;
    }

    public String getInsurancedateStr() {
        return this.insurancedateStr;
    }

    public void setInsurancedateStr(Date insurancedate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_ = null;
        try {
            date_ = sdf.format(insurancedate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.insurancedateStr = date_;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getIsvirtualdevice() {
        return isvirtualdevice;
    }

    public void setIsvirtualdevice(Integer isvirtualdevice) {
        this.isvirtualdevice = isvirtualdevice;
    }

    @Override
    public String toString() {
        return  "idcDevice [deviceclass = "+this.deviceclass+",vendor = "+this.vendor+",owner = "+this.owner+",uinstall = "+this.uinstall+",uheight = "+this.uheight+

                ",ownertype = " + this.ownertype + ",pwrPowertype = " + this.pwrPowertype + ",uplinedate = " + this.uplinedate + ",description = " + this.description +

                ",power = "+this.power+",phone = "+this.phone+",deviceid = "+this.deviceid+",name = "+this.name+",code = "+this.code+

                ",businesstypeId = "+this.businesstypeId+",rackId = "+this.rackId+",status = "+this.status+",ticketId = "+this.ticketId+",model = "+this.model+

                ",customerid= " + this.customerid + ",customername=" + this.customername + ",deviceVersion = " + this.deviceVersion + ",insurancedate = " + this.insurancedate +
                ",isdelete=" + this.isdelete + ",isvirtualdevice="+this.isvirtualdevice+" ]";
    }


}