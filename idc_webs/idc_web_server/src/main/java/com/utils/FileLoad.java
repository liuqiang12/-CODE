package com.utils;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 文件上报格式类
 * Created by DELL on 2017/8/15.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlRootElement
@XmlType(name = "fileLoad",propOrder = {
        "idcId",
        "dataUpload",
        "encryptAlgorithm",
        "compressionFormat",
        "hashAlgorithm",
        "dataHash",
        "commandVersion"})
public class FileLoad implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idcId;//电信管理部门颁发的IDC/ISP许可证号
    private String dataUpload;//使用参数compressionFormat指定的压缩格式对需要上报的数据进行压缩；对压缩后的信息使用参数encryptAlgorithm指定的加密算法加密，并对加密结果进行base64编码运算后得到的结果。上报的数据包括基础数据、监测日志、过滤日志、信息安全管理指令查询结果以及ISMS活动状态等（数据内容见第9章）。压缩前的上报数据应符合9章相应的数据上报内容要求，且小于12M
    private String encryptAlgorithm;//对称加密算法：0：不进行加密，明文传输；            1：AES加密算法；    加密密钥由控制平台和省端执行系统通过配置确定，长度至少为20字节，最多32字节。
    private String compressionFormat;//压缩格式
    private String hashAlgorithm;//哈希算法
    private String dataHash;//数据的哈希结果
    private String commandVersion;//接口方法版本

    @XmlElement(name = "idcId" )
    @JsonProperty
    public String getIdcId() {
        return idcId;
    }
    public void setIdcId(String idcId) {
        this.idcId = idcId;
    }

    @XmlElement(name = "dataUpload" )
    @JsonProperty
    public String getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(String dataUpload) {
        this.dataUpload = dataUpload;
    }

    @XmlElement(name = "encryptAlgorithm" )
    @JsonProperty
    public String getEncryptAlgorithm() {
        return encryptAlgorithm;
    }

    public void setEncryptAlgorithm(String encryptAlgorithm) {
        this.encryptAlgorithm = encryptAlgorithm;
    }

    @XmlElement(name = "compressionFormat" )
    @JsonProperty
    public String getCompressionFormat() {
        return compressionFormat;
    }

    public void setCompressionFormat(String compressionFormat) {
        this.compressionFormat = compressionFormat;
    }

    @XmlElement(name = "hashAlgorithm" )
    @JsonProperty
    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    @XmlElement(name = "dataHash" )
    @JsonProperty
    public String getDataHash() {
        return dataHash;
    }

    public void setDataHash(String dataHash) {
        this.dataHash = dataHash;
    }

    @XmlElement(name = "commandVersion" )
    @JsonProperty
    public String getCommandVersion() {
        return commandVersion;
    }

    public void setCommandVersion(String commandVersion) {
        this.commandVersion = commandVersion;
    }
}
