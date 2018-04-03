package com.idc.model;

import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/5.
 */
public class IdcDeviceVo implements Serializable {

    @Id
    @GeneratedValue
    private Long deviceid; //
    @Excel(name="设备编码")
    private String code; //   设备编码
    @Excel(name="设备名称")
    private String name; //   设备名称
    @Excel(name="设备类型")
    private String businesstypeId; //   设备类型
    @Excel(name="规格")
    private String model; //   规格
    @Excel(name="设备状态")
    private Long status; //   设备状态20-可用 30-空闲 50-预占 55-已停机 60-在服 110-不可用
    @Excel(name="产权性质")
    private String ownertype; //   产权性质1-自建 2-租用 3-客户 4-自有业务
    @Excel(name="设备高度")
    private Long uheight; //   设备高度(U)
    @Excel(name="安装位置")
    private Long uinstall; //   安装位置(U)
    @Excel(name="IP")
    private String serverIpaddress; //   IP
    @Excel(name="厂商")
    private String vendor; //   厂商
    @Excel(name="联系人")
    private String owner; //   联系人
    @Excel(name="电源类型")
    private String pwrPowertype; //   电源类型,Dict:AC,AC;DC,DC
    @Excel(name="上架日期")
    private Date uplinedate; //   上架日期
    @Excel(name="设备描述")
    private String description; //   设备描述
    @Excel(name="设备功耗")
    private Long power; //   设备功耗
    @Excel(name="联系人电话")
    private String phone; //   联系人电话
    @Excel(name="所属机架")
    private Long rackId; //   所属机架
    @Excel(name="工单编号")
    private Long ticketId; //   工单编号

    public Long getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Long deviceid) {
        this.deviceid = deviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getUinstall() {
        return uinstall;
    }

    public void setUinstall(Long uinstall) {
        this.uinstall = uinstall;
    }

    public Long getUheight() {
        return uheight;
    }

    public void setUheight(Long uheight) {
        this.uheight = uheight;
    }

    public String getOwnertype() {
        return ownertype;
    }

    public void setOwnertype(String ownertype) {
        this.ownertype = ownertype;
    }

    public String getServerIpaddress() {
        return serverIpaddress;
    }

    public void setServerIpaddress(String serverIpaddress) {
        this.serverIpaddress = serverIpaddress;
    }

    public String getPwrPowertype() {
        return pwrPowertype;
    }

    public void setPwrPowertype(String pwrPowertype) {
        this.pwrPowertype = pwrPowertype;
    }

    public Date getUplinedate() {
        return uplinedate;
    }

    public void setUplinedate(Date uplinedate) {
        this.uplinedate = uplinedate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPower() {
        return power;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinesstypeId() {
        return businesstypeId;
    }

    public void setBusinesstypeId(String businesstypeId) {
        this.businesstypeId = businesstypeId;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
