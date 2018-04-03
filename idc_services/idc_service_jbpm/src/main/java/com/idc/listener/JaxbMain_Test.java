package com.idc.listener;

import constant.isp.ISPContant;
import sun.misc.BASE64Encoder;
import utils.BasicDecodeUtils;
import utils.BasicEncodeUtils;

import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by DELL on 2017/8/29.
 */
public class JaxbMain_Test {
    //
    public static final String pathfile = "E:\\IntelliJ\\CODE\\idc_war\\target\\idc\\WEB-INF\\classes\\xml\\repository\\2017_09_22_09_21_57getAddHouseFun.xml";

    public static String filetext() {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
        try {
            String str = "";
            String str1 = "";
            fis = new FileInputStream(pathfile);// FileInputStream
            // 从文件系统中的某个文件中获取字节
            isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
            br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
            while ((str = br.readLine()) != null) {
                str1 += str + "\n";
            }
            // 当读取的一行不为空时,把读到的str的值赋给str1
            return str1;
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main1(String[] args) {

    }
    public static void main(String[] args) {

         JaxbMain_Test jt = new JaxbMain_Test();
        try{
             String fileText =  filetext();
            System.out.println("开始加密。。。。。。。。。。。");
            byte[] zipByte = BasicEncodeUtils.getInstance().zipstr(fileText);//打包zip
            System.out.println("压缩的二进制:"+ Arrays.toString(zipByte));
            String aesEncode = BasicEncodeUtils.getInstance().AESEncode(zipByte, ISPContant.AES密钥.value(),ISPContant.AES偏移量.value(),ISPContant.AES密码器.value());
            System.out.println("aes1:"+aesEncode);
            String baseEncoder = BasicEncodeUtils.getInstance().baseEncoder(zipByte);
            System.out.println("HASH:"+baseEncoder);
            System.out.println("开始解密。。。。。。。。。。。");

            //Base64解码 -  AES解码
            System.out.println("解密需要传递的加密参数："+aesEncode);
            byte[] decrypt = BasicDecodeUtils.getInstance().decrypt(aesEncode);
            System.out.println(Arrays.toString(decrypt));
            System.out.println("=============start");
            //System.out.println(BasicDecodeUtils.getInstance().unzipstr(decrypt));
            System.out.println("=============end");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            byte[] b2 = "LLX239".getBytes("utf8" );
            //串接字符串
            byte[] b3 = new byte[decrypt.length+b2.length];
            System.arraycopy(decrypt, 0, b3, 0, decrypt.length);
            System.arraycopy(b2, 0, b3,decrypt.length, b2.length);
            byte[] bs1 = md5.digest(b3);
            System.out.println("md5原始=" + Arrays.toString(bs1));//MD5
            System.out.println("正确的 md5="+ MD5(b3));

            System.out.println("64="+new BASE64Encoder().encode(MD5(b3).getBytes()));


            //System.out.println(BasicDecodeUtils.getInstance().unzipstr(decrypt));


            /*FileLoad fileLoad = (FileLoad) XMLParser.unmarshal(inputStream,FileLoad.class);
            System.out.println(fileLoad.getCommandVersion());
            String dataUpload = fileLoad.getDataUpload();
            System.out.println("**************************");
            //Base64解码 -  AES解码
            byte[] decrypt = BasicDecodeUtils.getInstance().decrypt(dataUpload);
            System.out.println(Arrays.toString(decrypt));
            System.out.println("=============start");
            System.out.println(BasicDecodeUtils.getInstance().unzipstr(decrypt));
            System.out.println("=============end");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            byte[] b2 = "LLX239".getBytes("utf8" );
            //串接字符串
            byte[] b3 = new byte[decrypt.length+b2.length];
            System.arraycopy(decrypt, 0, b3, 0, decrypt.length);
            System.arraycopy(b2, 0, b3,decrypt.length, b2.length);
            byte[] bs1 = md5.digest(b3);
            System.out.println("md5原始=" + Arrays.toString(bs1));//MD5
            System.out.println("正确的 md5="+ MD5(b3));
            System.out.println(BasicDecodeUtils.getInstance().unzipstr(decrypt));*/

        }catch (Exception e){
            e.fillInStackTrace();
        }
    }
    public static String MD5(byte[] b){
        StringBuilder sb = new StringBuilder();
        try {
            byte[] btInput = b;
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            md5.update(btInput);
            // 获得密文
            byte[] md = md5.digest();
            for (byte b1 : md) {
                sb.append(String.format("%02x", b1)); // 10进制转16进制，x表示以十六进制形式小写输出，02 表示不足两位前面补0输出
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
