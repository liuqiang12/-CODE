package com.idc.service;

import utils.typeHelper.FTPUtils;

import java.io.InputStream;

/**
 * 传递FTP服务
 */
public interface FTPService {
    /**
     *
     * @param ftpUtils ftp工具类
     * @param in 文件流
     * @param fileName  保存本地或者上传到服务器的文件名称
     * @param FTP_PATH  上传到服务器的相对路径
     * @return
     */
    Boolean pushLocalFileUploadFTP(FTPUtils ftpUtils, InputStream in, String fileName, String FTP_PATH);

    /**
     * 保存文件到本地[]
     * @param in 文件流
     * @param fileName 保存本地或者上传到服务器的文件名称
     * @return
     */
    String pushSystemUploadFile(InputStream in, String fileName);
}
