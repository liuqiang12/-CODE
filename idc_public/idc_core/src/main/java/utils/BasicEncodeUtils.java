package utils;

import constant.isp.ISPContant;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 加密算法
 */
public class BasicEncodeUtils {
    private static BasicEncodeUtils ourInstance = new BasicEncodeUtils();

    public static BasicEncodeUtils getInstance() {
        return ourInstance;
    }

    private BasicEncodeUtils() {
    }
    /**/
    //字符串压缩
    public byte[] zipstr(String str){
        byte[] compressed = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream   zout = new ZipOutputStream(out);
        try{
            zout.putNextEntry(new ZipEntry(""));
            zout.write(str.getBytes("utf-8"));
            zout.closeEntry();
            compressed = out.toByteArray();
            //System.out.println("压缩结果："+Arrays.toString(compressed));
        }catch(Exception e){}
        return compressed;
    }
    public byte[] zipBytes(String zipEntryName,byte[] bytes){
        byte[] compressed = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream   zout = new ZipOutputStream(out);
        try{
            zout.putNextEntry(new ZipEntry(zipEntryName));
            zout.write(bytes);
            zout.closeEntry();
            compressed = out.toByteArray();
            System.out.println("压缩结果："+Arrays.toString(compressed));
        }catch(Exception e){}
        return compressed;
    }
    public String unZipstr(String str){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("utf-8"));
            ZipInputStream gzip = new ZipInputStream(in);
            byte[] buffer = new byte[4808];
            int n = 0;
            while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
                // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
                out.write(buffer, 0, n);
            }
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toString("utf-8");
        }catch(Exception e){}
        return null;
    }
    //AES加密
    /**
     * 使用参数compressionFormat指定的压缩格式对需要上报的数据进行压缩；
     * 对压缩后的信息使用参数encryptAlgorithm指定的加密算法加密，并对加密结果进行base64编码运算后得到的结果。
     * 上报的数据包括基础数据、监测日志、过滤日志、信息安全管理指令查询结果以及ISMS活动状态等（数据内容见第9章）。
     压缩前的上报数据应符合9章相应的数据上报内容要求，且小于12M。
     */
    /**
     *AES加密；同时进行base64编码
     * @param str
     * @param ASE_KEY ASE密码
     * @param AES_IV ASE偏移量
     * @param CIPHER_ALGORITHM_ECB
     * @return
     */
    public String AESEncode(byte[] str,String ASE_KEY,String AES_IV,String CIPHER_ALGORITHM_ECB){
        {
            byte[] bs = null;
            String s = null;
            try{
                //支持"AES/CBC/PKCS7Padding"模式的填充，Java默认仅仅支持PKCS5Padding
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                //根据指定算法AES自成密码器
                Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM_ECB);
                //加密操作,返回加密后的字节数组，然后需要编码
                SecretKeySpec skeySpec = new SecretKeySpec(ASE_KEY.getBytes(), "AES");
                IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
                // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
                bs =  cipher.doFinal(str);

                s= new BASE64Encoder().encode(bs);

            }catch(Exception e){
                e.printStackTrace();
            }
            return s;
        }
    }

    /**
     * 将上报的数据按照compressionFormat要求进行压缩后串接消息认证密钥，
     * 再根据hashAlgorithm参数进行哈希运算，
     * 并进行base64编码后的数据结果。
     * 消息认证密钥由控制平台和省端执行系统通过配置确定，长度至少为20位，最多32位
     * @param zipByte
     * @return
     * @throws Exception
     */
    public String baseEncoder(byte[] zipByte) throws Exception{

        try{
            byte[] b2 = ISPContant.认证密钥.value().getBytes("utf8" );
            //串接字符串
            byte[] b3 = new byte[zipByte.length+b2.length];
            System.arraycopy(zipByte, 0, b3, 0, zipByte.length);
            System.arraycopy(b2, 0, b3,zipByte.length, b2.length);
            return new BASE64Encoder().encode(MD5(b3).getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public byte[] contactByte(byte[] zipByte) throws Exception{

        try{
            byte[] b2 = ISPContant.认证密钥.value().getBytes("utf8" );
            //串接字符串
            byte[] b3 = new byte[zipByte.length+b2.length];
            System.arraycopy(zipByte, 0, b3, 0, zipByte.length);
            System.arraycopy(b2, 0, b3,zipByte.length, b2.length);

            return b3;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        byte[] bs ={80, 75, 3, 4, 20, 0, 8, 8, 8, 0, -72, 84, -27, 74, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 102, 105, 108, 101, 76, 111, 97, 100, 46, 120, 109, 108, -43, 87, 95, 111, 27, 69, 16, 127, 54, 18, -33, -63, -14, -69, 125, 127, 125, -2, -93, -53, -75, -119, 35, -127, 43, 26, -112, 82, 68, -53, -117, 117, -66, -37, 56, 43, -20, 59, -9, 110, 29, 55, 60, 81, 85, 45, -108, 23, -14, 0, 15, 37, 32, 84, 36, 4, 72, 20, 30, 64, 106, 69, 64, -3, 50, 117, -99, 124, 11, 102, -9, 118, -9, 118, 29, 55, -55, 43, -106, 37, -17, -52, -4, 118, 111, 102, -10, 55, 51, 62, -1, -38, -67, -55, -72, 122, -128, -78, 28, -89, -55, 70, -51, 106, -104, -75, 42, 74, -94, 52, -58, -55, 104, -93, 54, 35, 123, -11, 118, -19, 90, -16, -10, 91, -2, 48, -52, 113, -44, 79, -10, -46, 42, 108, 72, -14, -18, -67, 28, 111, -44, -10, 9, -103, 118, 13, 99, 62, -97, 55, -26, 78, 35, -51, 70, -122, 109, -102, -106, 113, -5, -26, 123, -69, -47, 62, -102, -124, 117, -100, -28, 36, 76, 34, 84, -109, -69, -30, -53, 119, -43, -32, 121, 21, 127, -102, -91, 7, -3, -19, -64, 110, -101, -66, -63, -41, 84, -99, -96, 57, -11, -126, -82, 43, 62, -114, -93, 126, 28, 108, -38, -115, 45, -85, -79, 101, -41, 109, -45, 50, -31, 99, -7, 70, 97, 16, -104, -99, 112, -126, -126, 87, 47, -98, 45, -114, -1, 93, -2, 124, -78, -8, -14, -105, -2, 118, 111, 121, -14, -49, -30, -24, -117, -27, -17, 79, -105, 71, -113, -106, 127, -98, 44, 79, 126, 96, -69, 24, 84, -20, -37, -116, -29, -128, 105, -23, 66, 40, 63, -58, -45, -64, 99, -49, 97, 38, 42, 50, 83, -108, 102, 83, 64, -77, 31, -127, 125, 127, 111, 15, 71, 40, 99, 50, -72, 78, -49, 30, -29, -60, 55, 18, -15, 20, -118, -70, 117, 56, 69, 1, -13, -103, -83, -124, 58, -24, 88, 77, -10, -104, -106, -45, 108, 123, -98, -35, -10, 110, 80, 12, -73, 19, 52, 14, 76, -85, -19, -72, 29, -37, -78, 93, -41, -13, 13, -86, 41, 108, -109, 116, -120, -57, 112, -92, 106, -27, -70, 2, 0, 57, -58, -29, -64, 109, 53, -37, -90, -45, -79, -67, -21, 119, -17, 54, -94, 116, -30, 27, -123, -98, 57, 111, -84, 120, 15, 123, 80, 54, 2, 102, 28, -10, -46, -124, -124, 17, -7, 63, -58, -76, 54, 6, 127, 63, -99, -27, -88, -105, -50, 18, 66, 61, 86, -92, 74, 105, -106, -108, 19, 114, 28, 0, -39, 60, -41, 109, -13, 29, -3, 88, 53, 51, 22, -35, -39, 30, -20, -10, 6, -67, -19, -63, -19, 59, -125, 45, -45, -83, 91, 102, -109, -125, 119, -54, 84, 49, -103, -27, -56, -26, 70, 37, 97, 76, -2, 0, -72, -113, -95, -124, -126, 38, 39, -99, -82, 85, -112, 61, 76, 14, 117, 20, -45, -88, 8, 26, 23, -61, 88, -74, -83, -58, -86, -95, 40, -37, 23, 15, 127, 91, 60, -4, -21, -20, -101, -49, -105, -113, 127, 61, -67, -1, -11, -23, 79, 47, 79, -97, -1, -63, 119, -120, 106, -32, 112, -75, 32, -92, 66, -79, 107, 69, -64, 25, 115, -10, -32, -39, -21, 39, 127, -85, -92, -111, -84, -23, 116, 58, 58, 111, 24, 113, -52, -110, 39, 5, 81, 20, 114, -108, -20, 112, 90, 102, 7, -82, 5, 126, 116, 118, 8, 122, 40, -128, -21, -106, -45, 89, 101, 72, -123, 7, 32, 60, -82, 20, -54, 81, 72, -48, 60, 60, 44, 57, -64, 60, -78, 77, -37, -19, 56, -82, -22, -41, 48, 76, -30, -113, 112, 76, -10, -63, -69, 114, -51, -115, -30, -108, 41, 37, 113, -61, -74, -99, -122, 101, 121, 13, 11, -126, 45, 45, 28, 10, -11, -12, 9, -117, -33, -15, 13, -71, -26, -74, 48, -118, 80, -98, 127, -104, 96, 114, -107, -114, -90, -96, 121, -128, -25, -126, -15, -15, 116, 23, -115, -92, 92, -27, 31, 30, 98, -77, -43, -78, 100, -120, -46, 6, 77, 61, 35, 43, -111, 56, -64, 110, -95, -41, -64, 40, -119, -41, 64, 11, -83, 6, 36, 52, 76, 72, 40, 17, -31, 74, 11, 45, 11, 12, 76, -127, -102, 107, -43, 77, -81, 110, 55, -85, -74, -45, 117, -67, -82, 5, 23, 45, -116, -70, -121, -23, 44, -117, -48, 85, -45, -92, -96, -75, 83, -62, -15, 56, -115, 66, 2, -93, -15, -36, 73, 103, -97, 125, -5, -22, -27, -45, -77, -29, 71, -117, -29, 31, 23, -57, -57, -117, -25, -33, -65, -2, -18, -15, -39, -109, 35, 90, 56, 95, -67, -128, -52, -21, 91, 121, -10, -11, 92, 87, -4, -67, 12, 10, -32, 77, -71, -73, 59, 78, -21, 124, -18, -107, 126, 33, -106, 85, 13, 16, -29, -100, 100, 120, 56, -93, -49, -90, 40, 77, -42, -95, 105, 20, -51, -90, 48, -97, 15, 41, -82, 20, -86, 58, -118, 57, -71, -82, -91, 13, -96, -91, 13, -34, 49, -95, 21, -105, 16, 30, -88, 22, 88, 85, 20, 22, 83, 20, 109, 21, -92, 76, 37, 33, -115, -40, -15, -84, -74, 82, 80, -48, 42, -56, 44, 99, 115, -124, -81, -86, 28, 76, -9, 21, 78, -46, -109, 32, -65, 59, 114, 12, 73, 73, -77, -78, -35, -82, -80, 50, 73, -17, 58, -42, -7, -98, -77, 51, -101, 12, -95, 9, -84, 31, 89, -36, -56, -63, -87, -42, -30, -42, 79, -59, 55, 63, -21, -46, -55, 120, -15, 108, -68, -62, 116, -68, -46, 124, -92, -41, -90, 7, -30, -121, -20, 15, 80, 24, -53, 14, -9, 41, -98, -10, -46, 24, -55, 126, 47, 100, 122, -55, 28, 2, -9, 122, 0, 103, 40, -35, -78, -44, 49, 90, 123, 38, 101, 76, -87, 90, 1, -47, -39, -116, 96, -6, -46, 49, -80, -94, 18, -56, 12, -115, -8, -88, -16, 13, -79, 86, 108, 112, -26, -114, -79, -55, 76, -38, -15, -28, 38, 117, -43, -90, -57, 22, 75, 97, 26, -50, 114, -100, 64, -97, -92, 55, 35, -41, -62, 24, -89, -112, -95, 68, -120, -20, -82, 44, -11, 106, -8, 109, -61, -65, -39, -107, -37, 54, -12, -99, -59, 48, 124, 55, 29, -57, 106, 106, 64, -67, -33, 87, -72, -49, -92, 74, -91, -76, 94, -8, 111, -125, -7, 39, -54, -5, -126, 22, -71, -126, 41, -113, -105, 101, -38, 87, 58, -114, -86, -108, -56, -117, 102, 27, 27, 34, -73, -78, 48, -55, -53, -93, 105, -107, 18, -108, 37, -120, -56, -71, 86, 92, -61, 21, 70, 7, -121, 94, 54, 56, 120, -102, -41, 60, -121, 54, -38, -62, 31, 121, 25, -21, -78, 95, -78, 80, -10, 37, 78, 33, -56, 23, -54, 88, -82, 24, -115, 74, 81, -12, 113, -7, 10, 98, -56, 70, -58, 42, 0, 56, -64, 95, 80, -24, 113, 62, -127, 61, -69, 36, -100, 76, -7, -67, -64, -73, 89, -75, -52, -82, -45, -22, -70, -112, -23, -46, 12, 111, 88, -122, 124, -59, 10, -2, 3, 80, 75, 7, 8, 44, 91, -67, 104, -77, 4, 0, 0, -106, 13, 0, 0, 80, 75, 1, 2, 20, 0, 20, 0, 8, 8, 8, 0, -72, 84, -27, 74, 44, 91, -67, 104, -77, 4, 0, 0, -106, 13, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 102, 105, 108, 101, 76, 111, 97, 100, 46, 120, 109, 108, 80, 75, 5, 6, 0, 0, 0, 0, 1, 0, 1, 0, 58, 0, 0, 0, -19, 4, 0, 0, 0, 0};
        String AESStr = "asdf";//AES 加密
        System.out.println(AESStr);
        try{//获取hash值
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            byte[] b2 = "LLX239".getBytes("utf8" );
            //串接字符串
            byte[] b3 = new byte[bs.length+b2.length];
            System.arraycopy(bs, 0, b3, 0, bs.length);
            System.arraycopy(b2, 0, b3,bs.length, b2.length);
            byte[] bs1 = md5.digest(b3);
            System.out.println("md5原始=" +Arrays.toString(bs1));//MD5
            System.out.println("正确的 md5="+ MD5(b3));
            System.out.println("正确的 hash="+base64en.encode( MD5(b3).getBytes()));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * MD5
     * @param b
     * @return
     */
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
