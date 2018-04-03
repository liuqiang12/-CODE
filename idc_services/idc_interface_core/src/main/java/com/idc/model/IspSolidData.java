package com.idc.model;

import java.io.Serializable;

public class IspSolidData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String tableName = "ISP_SOLID_DATA";
    private Long id;
    private String idcId;
    private String idcName;
    private String idcAdd;
    private String idcZip;
    private String corp;
    private Long houseCount ;
    private Long houseId ;
    private String houseName;
    private Integer houseType;
    private Integer houseProvincel;
    private Integer houseCity;
    private Integer houseCounty;
    private String houseAdd;
    private String houseZip;

    private String offerName; //   OFFER_NAME
    private Long offerIdType; //   人员的证件类型，见8.3节，有效类型只有身份证、护照、军官证、台胞证
    private String offerId; //   对应的证件号码
    private String offerTel; //   固定电话
    private String offerMobile; //   移动电话
    private String offerEmail; //   email地址

    private String emergencyName; //   OFFER_NAME
    private Long emergencyIdType; //   人员的证件类型，见8.3节，有效类型只有身份证、护照、军官证、台胞证
    private String emergencyId; //   对应的证件号码
    private String emergencyTel; //   固定电话
    private String emergencyMobile; //   移动电话
    private String emergencyEmail; //   email地址

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdcId() {
        return idcId;
    }

    public void setIdcId(String idcId) {
        this.idcId = idcId;
    }

    public String getIdcName() {
        return idcName;
    }

    public void setIdcName(String idcName) {
        this.idcName = idcName;
    }

    public String getIdcAdd() {
        return idcAdd;
    }

    public void setIdcAdd(String idcAdd) {
        this.idcAdd = idcAdd;
    }

    public String getIdcZip() {
        return idcZip;
    }

    public void setIdcZip(String idcZip) {
        this.idcZip = idcZip;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public Long getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(Long houseCount) {
        this.houseCount = houseCount;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public Integer getHouseProvincel() {
        return houseProvincel;
    }

    public void setHouseProvincel(Integer houseProvincel) {
        this.houseProvincel = houseProvincel;
    }

    public Integer getHouseCity() {
        return houseCity;
    }

    public void setHouseCity(Integer houseCity) {
        this.houseCity = houseCity;
    }

    public Integer getHouseCounty() {
        return houseCounty;
    }

    public void setHouseCounty(Integer houseCounty) {
        this.houseCounty = houseCounty;
    }

    public String getHouseAdd() {
        return houseAdd;
    }

    public void setHouseAdd(String houseAdd) {
        this.houseAdd = houseAdd;
    }

    public String getHouseZip() {
        return houseZip;
    }

    public void setHouseZip(String houseZip) {
        this.houseZip = houseZip;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public Long getOfferIdType() {
        return offerIdType;
    }

    public void setOfferIdType(Long offerIdType) {
        this.offerIdType = offerIdType;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferTel() {
        return offerTel;
    }

    public void setOfferTel(String offerTel) {
        this.offerTel = offerTel;
    }

    public String getOfferMobile() {
        return offerMobile;
    }

    public void setOfferMobile(String offerMobile) {
        this.offerMobile = offerMobile;
    }

    public String getOfferEmail() {
        return offerEmail;
    }

    public void setOfferEmail(String offerEmail) {
        this.offerEmail = offerEmail;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public Long getEmergencyIdType() {
        return emergencyIdType;
    }

    public void setEmergencyIdType(Long emergencyIdType) {
        this.emergencyIdType = emergencyIdType;
    }

    public String getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(String emergencyId) {
        this.emergencyId = emergencyId;
    }

    public String getEmergencyTel() {
        return emergencyTel;
    }

    public void setEmergencyTel(String emergencyTel) {
        this.emergencyTel = emergencyTel;
    }

    public String getEmergencyMobile() {
        return emergencyMobile;
    }

    public void setEmergencyMobile(String emergencyMobile) {
        this.emergencyMobile = emergencyMobile;
    }

    public String getEmergencyEmail() {
        return emergencyEmail;
    }

    public void setEmergencyEmail(String emergencyEmail) {
        this.emergencyEmail = emergencyEmail;
    }
}
