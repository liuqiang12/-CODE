package com.idc.cxf.isp.service.impl;

import com.idc.cxf.isp.service.IEventService;
import com.utils.FileLoad;
import constant.isp.ISPContant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import utils.BasicEncodeUtils;
import utils.XMLParser;
import utils.typeHelper.DateHelper;
import utils.typeHelper.FileHelper;

import javax.jws.WebService;
import java.io.File;
import java.util.Arrays;

/**
 * Created by DELL on 2017/9/1.
 */
@WebService(endpointInterface= "com.idc.cxf.isp.service.IEventService",
targetNamespace="http://impl.service.isp.cxf.idc.com/",serviceName = "ispEventService")
public class EventServiceImpl implements IEventService {
    private static final Log log = LogFactory.getLog(EventServiceImpl.class);
    public int addition_yyyy(int opr1, int opr2) {
        return opr1+opr2;
    }

    @Override
    public String xmlToFile(String xmlContent, String directory, String suffixAliasName) {
        try {
            //生成的文件名称.
            String file_path = this.getClass().getClassLoader().getResource("").getPath()+File.separator+directory;

            File file = new File(file_path);
            if(!file.exists()){
                file.mkdirs();
            }
                /*yyyy-MM-dd HH:mm:ss*/
            file_path = file_path+File.separator+ DateHelper.getCurrDateTimeForFile(suffixAliasName)+".xml";

            System.out.println("文件生成的路径<<=========="+file_path+"==========>>");

            FileHelper.getInstance().textToFile(file_path,xmlContent);
            return file_path;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }
    @Override
    public String createISPEventService(String xmlContent, String suffixAliasName) {
        FileLoad fileLoad = new FileLoad();
        fileLoad.setIdcId(ISPContant.许可证号.value());
        fileLoad.setCompressionFormat(ISPContant.压缩格式_Zip压缩格式.value());
        fileLoad.setHashAlgorithm(ISPContant.哈希算法_MD5.value());
        fileLoad.setEncryptAlgorithm(ISPContant.对称加密算法_AES加密.value());
        fileLoad.setCommandVersion(ISPContant.版本.value());
        //添加xmlstr：dataUpload
        byte[] zipByte = BasicEncodeUtils.getInstance().zipstr(xmlContent);//打包zip
        //外加偏移量问题
        log.debug("压缩的二进制:"+ Arrays.toString(zipByte));
        try {
            String aesEncode = BasicEncodeUtils.getInstance().AESEncode(zipByte,ISPContant.AES密钥.value(),ISPContant.AES偏移量.value(),ISPContant.AES密码器.value());
            fileLoad.setDataUpload(aesEncode);
            try {

                String baseEncoder = BasicEncodeUtils.getInstance().baseEncoder(zipByte);

                fileLoad.setDataHash(baseEncoder);
                String XML_STR = XMLParser.marshal(fileLoad, FileLoad.class,"UTF-8");
                //生成的文件名称.
                String file_path = this.getClass().getClassLoader().getResource("").getPath()+"/xml/";

                System.out.println("压缩文件生成的路径<<=========="+file_path+"==========>>");

                File file = new File(file_path);
                if(!file.exists()){
                    file.mkdirs();
                }
                /*yyyy-MM-dd HH:mm:ss*/
                file_path = file_path+ DateHelper.getCurrDateTimeForFile(suffixAliasName)+".xml";
                FileHelper.getInstance().textToFile(file_path,XML_STR);
                return XML_STR;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
