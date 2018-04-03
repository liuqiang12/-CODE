package com.idc.model;

import java.math.BigDecimal;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import java.sql.Timestamp;

import java.sql.Clob;

import java.text.SimpleDateFormat;

import javax.persistence.GeneratedValue;

import javax.persistence.Column;

import javax.persistence.Id;



/**

 * <br>

 * <b>实体类</b><br>

 * <b>功能：业务表</b>IDC_LOCATION_COUNT:${tableData.tableComment}<br>

 * <b>作者：Administrator</b><br>

 * <b>日期：</b> Aug 08,2017 <br>

 * <b>版权所有：<b>版权所有(C) 2016<br>

 */

@SuppressWarnings("serial")

public class IdcLocationCount implements Serializable {



    public static final String tableName = "IDC_LOCATION_COUNT";



    @Id@GeneratedValue

    private Long id; //



    private Long racktotal; //



    private Float rackusage; //



    private Long iptotal; //



    private Float ipusage; //



    private Long coreporttotal; //



    private Float coreportusage; //



    private Long accessporttotal; //



    private Float accessportusage; //



    private Long customertotal; //



    private Long bandwidthtotal; //



    private Float bandwidthusage; //



    private Long customerOffTotal; //



    private Long paTotal; //



    private Float paGoodrate; //



    private Long ktTotal; //



    private Float ktGoodrate; //



    private Long baTotal; //



    private Float baGoograte; //



    private Long bkTotal; //



    private Float bkGoodrate; //



    private Long xxTotal; //


    private Long usedracknum;

    private Long usedipnum;

    private Long usedcoreport;

    private Long usedaccessport;

    private float usedbandwidth;

    /************************get set method**************************/


    public Long getId() {
        return this.id;
    }


    @Column(name = "ID" , nullable =  false, length = 22)
    public void setId(Long id) {
        this.id=id;
    }


    public Long getRacktotal() {
        return this.racktotal;
    }


    @Column(name = "RACKTOTAL" , nullable =  false, length = 22)
    public void setRacktotal(Long racktotal) {
        this.racktotal=racktotal;
    }


    public Float getRackusage() {
        return this.rackusage;
    }


    @Column(name = "RACKUSAGE" , nullable =  false, length = 22)
    public void setRackusage(Float rackusage) {
        this.rackusage=rackusage;
    }


    public Long getIptotal() {
        return this.iptotal;
    }


    @Column(name = "IPTOTAL" , nullable =  false, length = 22)
    public void setIptotal(Long iptotal) {
        this.iptotal=iptotal;
    }


    public Float getIpusage() {
        return this.ipusage;
    }


    @Column(name = "IPUSAGE" , nullable =  false, length = 22)
    public void setIpusage(Float ipusage) {
        this.ipusage=ipusage;
    }


    public Long getCoreporttotal() {
        return this.coreporttotal;
    }


    @Column(name = "COREPORTTOTAL" , nullable =  false, length = 22)
    public void setCoreporttotal(Long coreporttotal) {
        this.coreporttotal=coreporttotal;
    }


    public Float getCoreportusage() {
        return this.coreportusage;
    }


    @Column(name = "COREPORTUSAGE" , nullable =  false, length = 22)
    public void setCoreportusage(Float coreportusage) {
        this.coreportusage=coreportusage;
    }


    public Long getAccessporttotal() {
        return this.accessporttotal;
    }


    @Column(name = "ACCESSPORTTOTAL" , nullable =  false, length = 22)
    public void setAccessporttotal(Long accessporttotal) {
        this.accessporttotal=accessporttotal;
    }


    public Float getAccessportusage() {
        return this.accessportusage;
    }


    @Column(name = "ACCESSPORTUSAGE" , nullable =  false, length = 22)
    public void setAccessportusage(Float accessportusage) {
        this.accessportusage=accessportusage;
    }


    public Long getCustomertotal() {
        return this.customertotal;
    }


    @Column(name = "CUSTOMERTOTAL" , nullable =  false, length = 22)
    public void setCustomertotal(Long customertotal) {
        this.customertotal=customertotal;
    }


    public Long getBandwidthtotal() {
        return this.bandwidthtotal;
    }


    @Column(name = "BANDWIDTHTOTAL" , nullable =  false, length = 22)
    public void setBandwidthtotal(Long bandwidthtotal) {
        this.bandwidthtotal=bandwidthtotal;
    }


    public Float getBandwidthusage() {
        return this.bandwidthusage;
    }


    @Column(name = "BANDWIDTHUSAGE" , nullable =  false, length = 22)
    public void setBandwidthusage(Float bandwidthusage) {
        this.bandwidthusage=bandwidthusage;
    }


    public Long getCustomerOffTotal() {
        return this.customerOffTotal;
    }


    @Column(name = "CUSTOMER_OFF_TOTAL" , nullable =  false, length = 22)
    public void setCustomerOffTotal(Long customerOffTotal) {
        this.customerOffTotal=customerOffTotal;
    }


    public Long getPaTotal() {
        return this.paTotal;
    }


    @Column(name = "PA_TOTAL" , nullable =  false, length = 22)
    public void setPaTotal(Long paTotal) {
        this.paTotal=paTotal;
    }


    public Float getPaGoodrate() {
        return this.paGoodrate;
    }


    @Column(name = "PA_GOODRATE" , nullable =  false, length = 22)
    public void setPaGoodrate(Float paGoodrate) {
        this.paGoodrate=paGoodrate;
    }


    public Long getKtTotal() {
        return this.ktTotal;
    }


    @Column(name = "KT_TOTAL" , nullable =  false, length = 22)
    public void setKtTotal(Long ktTotal) {
        this.ktTotal=ktTotal;
    }


    public Float getKtGoodrate() {
        return this.ktGoodrate;
    }


    @Column(name = "KT_GOODRATE" , nullable =  false, length = 22)
    public void setKtGoodrate(Float ktGoodrate) {
        this.ktGoodrate=ktGoodrate;
    }


    public Long getBaTotal() {
        return this.baTotal;
    }


    @Column(name = "BA_TOTAL" , nullable =  false, length = 22)
    public void setBaTotal(Long baTotal) {
        this.baTotal=baTotal;
    }


    public Float getBaGoograte() {
        return this.baGoograte;
    }


    @Column(name = "BA_GOOGRATE" , nullable =  false, length = 22)
    public void setBaGoograte(Float baGoograte) {
        this.baGoograte=baGoograte;
    }


    public Long getBkTotal() {
        return this.bkTotal;
    }


    @Column(name = "BK_TOTAL" , nullable =  false, length = 22)
    public void setBkTotal(Long bkTotal) {
        this.bkTotal=bkTotal;
    }


    public Float getBkGoodrate() {
        return this.bkGoodrate;
    }


    @Column(name = "BK_GOODRATE" , nullable =  false, length = 22)
    public void setBkGoodrate(Float bkGoodrate) {
        this.bkGoodrate=bkGoodrate;
    }


    public Long getXxTotal() {
        return this.xxTotal;
    }


    @Column(name = "XX_TOTAL" , nullable =  false, length = 22)
    public void setXxTotal(Long xxTotal) {
        this.xxTotal=xxTotal;
    }

    public Long getUsedracknum() {
        return usedracknum;
    }

    @Column(name = "USEDRACKNUM" , nullable =  false)
    public void setUsedracknum(Long usedracknum) {
        this.usedracknum = usedracknum;
    }

    public Long getUsedipnum() {
        return usedipnum;
    }

    @Column(name = "USEDIPNUM" , nullable =  false)
    public void setUsedipnum(Long usedipnum) {
        this.usedipnum = usedipnum;
    }

    public Long getUsedcoreport() {
        return usedcoreport;
    }

    @Column(name = "USEDCOREPORT" , nullable =  false)
    public void setUsedcoreport(Long usedcoreport) {
        this.usedcoreport = usedcoreport;
    }

    public Long getUsedaccessport() {
        return usedaccessport;
    }

    @Column(name = "USEDACCESSPORT" , nullable =  false)
    public void setUsedaccessport(Long usedaccessport) {
        this.usedaccessport = usedaccessport;
    }

    public float getUsedbandwidth() {
        return usedbandwidth;
    }

    @Column(name = "USEDBANDWIDTH" , nullable =  false)
    public void setUsedbandwidth(float usedbandwidth) {
        this.usedbandwidth = usedbandwidth;
    }

    @Override
    public String toString() {
        return  "idcLocationCount [id = "+this.id+",racktotal = "+this.racktotal+",rackusage = "+this.rackusage+",iptotal = "+this.iptotal+",ipusage = "+this.ipusage+

                ",coreporttotal = "+this.coreporttotal+",coreportusage = "+this.coreportusage+",accessporttotal = "+this.accessporttotal+",accessportusage = "+this.accessportusage+",customertotal = "+this.customertotal+

                ",bandwidthtotal = "+this.bandwidthtotal+",bandwidthusage = "+this.bandwidthusage+",customerOffTotal = "+this.customerOffTotal+",paTotal = "+this.paTotal+",paGoodrate = "+this.paGoodrate+

                ",ktTotal = "+this.ktTotal+",ktGoodrate = "+this.ktGoodrate+",baTotal = "+this.baTotal+",baGoograte = "+this.baGoograte+",bkTotal = "+this.bkTotal+

                ",bkGoodrate = "+this.bkGoodrate+",xxTotal = "+this.xxTotal+",usedracknum="+this.usedracknum+",usedipnum="+this.usedipnum+",usedcoreport="+this.usedcoreport+"" +
                ",usedaccessport="+this.usedaccessport+",usedbandwidth="+this.usedbandwidth+" ]";
    }





}