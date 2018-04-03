package com.idc.service.impl;

import com.idc.service.FTPService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import utils.DevContext;
import utils.typeHelper.FTPUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
@Component("fTPService")
public class FTPServiceImpl implements FTPService{
    private static final Log log = LogFactory.getLog(FTPServiceImpl.class);
    /**
     *
     * @param ftpUtils ftp工具类
     * @param in 文件流
     * @param fileName  保存本地或者上传到服务器的文件名称
     * @param FTP_PATH  上传到服务器的相对路径
     * @return
     */
    @Override
    public Boolean pushLocalFileUploadFTP(FTPUtils ftpUtils, InputStream in, String fileName, String FTP_PATH){
        //是否需要保存到本地
        log.debug("-------------------保存到本地----------------------");
        String outFilePath = ftpUtils.wirteLocalFile(in,fileName);
        log.debug("-------------------开始上传FTP---------------------");

        //return ftpUtils.upload(FTP_PATH,outFilePath); //这是LQ的方法

        Boolean uploadResult = false;
        try {
            uploadResult = ftpUtils.uploadFTPNew(FTP_PATH,outFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadResult;
    }
    /**
     * 保存文件到本地[]
     * @param in 文件流
     * @param fileName 保存本地或者上传到服务器的文件名称
     * @return
     */
    @Override
    public String pushSystemUploadFile(InputStream in, String fileName){
        log.debug("-------------------保存到本地----------------------");
        String outFilePath = wirteLocalFile(in,fileName);
        return outFilePath;
    }

    public String wirteLocalFile(InputStream input,String fileName){
        String localFilePath = "";
        if("true".equalsIgnoreCase(DevContext.FTP_TEMPFIELPATH_ABSOLUTE)){
            log.debug("本地临时文件保存[采用绝对路径]");
            localFilePath = DevContext.FTP_TEMPFIELPATH;
        }else{
            log.debug("本地临时文件保存[采用相对路径]");
            localFilePath = this.getClass().getClassLoader().getResource("").getPath()+ File.separator+"localFile";
        }
        log.debug("保存本地文件目录:["+localFilePath+"]");
        File file = new File(localFilePath);
        if(!file.exists()){
            file.mkdirs();
        }
        //fileName
        //创建的文件名称是:文件名称任意
        String outFilePath = file.getPath()+File.separator+fileName;
        try {
            FileOutputStream out = new FileOutputStream(outFilePath);
            byte[] buf = new byte[1024 * 8];
            while (true) {
                int read = 0;
                if (input != null) {
                    read = input.read(buf);
                }
                if (read == -1) {
                    break;
                }
                out.write(buf, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("上传文件:[----------------失败--------------]");
            return null;
        }
        log.debug("上传文件:[----------------成功--------------]");
        return outFilePath;
    }
    //删除附件信息
    public static void main(String[] args) {
        FTPUtils ftpUtils = FTPUtils.getInstance();
        boolean delsuccess = ftpUtils.deleteFile(DevContext.FTP_CONTRACT_PATH,"IDC_CONTRACT[ID_12][2017-09-26 12-38-51].doc");
        System.out.println(delsuccess);
    }
}
