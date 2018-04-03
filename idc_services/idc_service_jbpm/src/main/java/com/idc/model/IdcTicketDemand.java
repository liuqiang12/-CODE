package com.idc.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_TICKET_DEMAND:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jan 11,2018 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
@SuppressWarnings("serial")
public class IdcTicketDemand implements Serializable {

    public static final String tableName = "IDC_TICKET_DEMAND";

    @Id@GeneratedValue
    private Long id; //   主键ID
    private Long ticketInstId; //   工单ID
    private Long prodInstId; //   产品ID
    private Timestamp createTime; //   创建时间
    private String createTimeStr; //   创建时间 [日期格式化后的字符串]

    /*--------------变更的机架、机位字段------------------*/
    private String rack_Spec; // 机架规格
    private String rack_Num; // 机架个数
    private String other_Msg; //   机位个数
    private String rack_supplyType; // 供电类型
    private String rack_desc; // 机架描述

    /*--------------端口带宽字段------------------*/
    private String port_portMode; // 端口带宽占用方式
    private String port_bandwidth; // 带宽总需求
    private String port_num; // 端口数量
    private String port_desc; // 端口描述

    /*--------------IP字段------------------*/
    private String ip_portMode; // IP性质
    private String ip_num; // IP数量
    private String ip_desc; // IP描述

    /*--------------主机租赁字段------------------*/
    private String server_typeMode; // 主机资源类型
    private String server_specNumber; // 主机设备型号
    private String server_num; // 主机数量
    private String server_desc; // 主机描述

    /*--------------业务增值字段------------------*/
    private String newly_name; // 业务增值名称
    private String newly_category; // 业务增值业务分类
    private String newly_desc; // 业务增值描述


    /************************get set method**************************/
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getRack_Spec() {
        return rack_Spec;
    }

    public void setRack_Spec(String rack_Spec) {
        this.rack_Spec = rack_Spec;
    }

    public String getRack_Num() {
        return rack_Num;
    }

    public void setRack_Num(String rack_Num) {
        this.rack_Num = rack_Num;
    }

    public String getRack_supplyType() {
        return rack_supplyType;
    }

    public void setRack_supplyType(String rack_supplyType) {
        this.rack_supplyType = rack_supplyType;
    }

    public String getRack_desc() {
        return rack_desc;
    }

    public void setRack_desc(String rack_desc) {
        this.rack_desc = rack_desc;
    }

    public String getPort_portMode() {
        return port_portMode;
    }

    public void setPort_portMode(String port_portMode) {
        this.port_portMode = port_portMode;
    }

    public String getPort_bandwidth() {
        return port_bandwidth;
    }

    public void setPort_bandwidth(String port_bandwidth) {
        this.port_bandwidth = port_bandwidth;
    }

    public String getPort_num() {
        return port_num;
    }

    public void setPort_num(String port_num) {
        this.port_num = port_num;
    }

    public String getPort_desc() {
        return port_desc;
    }

    public void setPort_desc(String port_desc) {
        this.port_desc = port_desc;
    }

    public String getIp_portMode() {
        return ip_portMode;
    }

    public void setIp_portMode(String ip_portMode) {
        this.ip_portMode = ip_portMode;
    }

    public String getIp_num() {
        return ip_num;
    }

    public void setIp_num(String ip_num) {
        this.ip_num = ip_num;
    }

    public String getIp_desc() {
        return ip_desc;
    }

    public void setIp_desc(String ip_desc) {
        this.ip_desc = ip_desc;
    }

    public String getServer_typeMode() {
        return server_typeMode;
    }

    public void setServer_typeMode(String server_typeMode) {
        this.server_typeMode = server_typeMode;
    }

    public String getServer_specNumber() {
        return server_specNumber;
    }

    public void setServer_specNumber(String server_specNumber) {
        this.server_specNumber = server_specNumber;
    }

    public String getServer_num() {
        return server_num;
    }

    public void setServer_num(String server_num) {
        this.server_num = server_num;
    }

    public String getServer_desc() {
        return server_desc;
    }

    public void setServer_desc(String server_desc) {
        this.server_desc = server_desc;
    }

    public String getNewly_name() {
        return newly_name;
    }

    public void setNewly_name(String newly_name) {
        this.newly_name = newly_name;
    }

    public String getNewly_category() {
        return newly_category;
    }

    public void setNewly_category(String newly_category) {
        this.newly_category = newly_category;
    }

    public String getNewly_desc() {
        return newly_desc;
    }

    public void setNewly_desc(String newly_desc) {
        this.newly_desc = newly_desc;
    }

    public Long getId() {
        return this.id;
    }

    @Column(name = "ID" , columnDefinition="主键ID" , nullable =  false, length = 22)
    public void setId(Long id) {
        this.id=id;
    }

    public Long getTicketInstId() {
        return this.ticketInstId;
    }

    @Column(name = "TICKET_INST_ID" , columnDefinition="工单ID" , nullable =  false, length = 22)
    public void setTicketInstId(Long ticketInstId) {
        this.ticketInstId=ticketInstId;
    }

    public Long getProdInstId() {
        return this.prodInstId;
    }

    @Column(name = "PROD_INST_ID" , columnDefinition="产品ID" , nullable =  false, length = 22)
    public void setProdInstId(Long prodInstId) {
        this.prodInstId=prodInstId;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    @Column(name = "CREATE_TIME" , columnDefinition="创建时间" , nullable =  false, length = 11)
    public void setCreateTime(Timestamp createTime) {
        this.createTime=createTime;
    }

    public String getOther_Msg() {
        return this.other_Msg;
    }

    @Column(name = "OTHER_MSG" , columnDefinition="其他信息，备用字段" , nullable =  false, length = 2000)
    public void setOther_Msg(String other_Msg) {
        this.other_Msg=other_Msg;
    }


    public String getCreateTimeStr() {
        return this.createTimeStr;
    }

    public void setCreateTimeStr(Timestamp createTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_ = null;
        try {
            date_ = sdf.format(createTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.createTimeStr = date_;
    }
}