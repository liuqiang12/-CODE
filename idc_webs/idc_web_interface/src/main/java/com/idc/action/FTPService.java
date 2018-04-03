package com.idc.action;

import utils.DevContext;
import utils.plugins.excel.Guid;
import utils.typeHelper.FTPUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by DELL on 2017/9/25.
 */
public class FTPService {

    public static void main(String[] args) throws Exception{
        FTPUtils ftpUtils = FTPUtils.getInstance();
        //先手动上传一个文件
        InputStream in = new FileInputStream(new File("D:\\ftp\\OrderTransfer.log"));
        String fileName = Guid.getAttchUUID(DevContext.IDC_CONTRACT+"[ID_"+12+"]",".doc");
        String outFilePath = ftpUtils.wirteLocalFile(in,fileName);
        //然后获取该文件上传ftp信息
        /*附件表就只保存 物理文件名称 和转换后的文件名称:[上传的ftp地址 和 ftp路径] 以及其他的参数信息*/
        //上传ftp信息
        ftpUtils.upload(DevContext.FTP_CONTRACT_PATH,outFilePath);
        //删除FTP上的文件信息[删除文件]
        ftpUtils.deleteFile(DevContext.FTP_CONTRACT_PATH,fileName);
    }

}
