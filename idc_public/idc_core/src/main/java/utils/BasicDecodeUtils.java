package utils;

import constant.isp.ISPContant;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Security;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by DELL on 2017/8/29.
 * 解密算法工具
 */
public class BasicDecodeUtils {
    private static final int buffer = 2048;

    private static BasicDecodeUtils ourInstance = new BasicDecodeUtils();

    public static BasicDecodeUtils getInstance() {
        return ourInstance;
    }

    private BasicDecodeUtils() {
    }

    // AES解密
    public  byte[] decrypt(String sSrc) throws Exception {
        String ASE_KEY= ISPContant.AES密钥.value();//AES密码
        String AES_IV =ISPContant.AES偏移量.value();//AES偏移量

        try {
            byte[] raw = ASE_KEY.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            //支持"AES/CBC/PKCS7Padding"模式的填充，Java默认仅仅支持PKCS5Padding
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(ISPContant.AES密码器.value());
            IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return original;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //解压
    public byte[] unzipstr(byte[] str) throws IOException{
        ByteArrayInputStream in = new ByteArrayInputStream(str);
        ByteArrayOutputStream out= new ByteArrayOutputStream();
        ZipInputStream zis = new ZipInputStream (in) ;
        zis.getNextEntry();
        byte[] buffer = new byte[1024];
        String decompressed = null;
        int offset = -1;
        try{
            while ((offset = zis.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed=out.toString();
            System.out.println(decompressed);
            return out.toByteArray();
        }catch(Exception e){e.printStackTrace();
            return null;
        }finally{
            if(zis!=null)
                zis.close();
            if(in!=null)
                in.close();
            if(out!=null)
                out.close();
        }
    }
    //解压
    public String unzipstr(String str){
        try {
            if (null == str || str.length() <= 0) {
                return str;
            }
            // 创建一个新的 byte 数组输出流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
            ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("UTF-8"));
            // 使用默认缓冲区大小创建新的输入流
            ZipInputStream zip = new ZipInputStream(in);
            byte[] buffer = new byte[256];
            int n = 0;
            while ((n = zip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
                // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
                out.write(buffer, 0, n);
            }
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public String decrypt2( byte[] encrypted1) throws Exception {
        String ASE_KEY=ISPContant.AES密钥.value();//AES密码
        String AES_IV =ISPContant.AES偏移量.value();//AES偏移量
        try {
            byte[] raw = ASE_KEY.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(ISPContant.AES密码器.value());
            IvParameterSpec iv = new IvParameterSpec(AES_IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            ex.fillInStackTrace();
            return null;
        }
    }
    /**
     * 解压Zip文件
     * @param path 文件目录
     */
    public static void unZip(String path)
    {
        int count = -1;
        String savepath = "";

        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        savepath = path.substring(0, path.lastIndexOf(".")) + File.separator; //保存解压文件目录
        new File(savepath).mkdir(); //创建保存目录
        ZipFile zipFile = null;
        try
        {
            zipFile = new ZipFile(path); //解决中文乱码问题
            Enumeration<?> entries = zipFile.entries();

            while(entries.hasMoreElements())
            {
                byte buf[] = new byte[buffer];

                ZipEntry entry = (ZipEntry)entries.nextElement();

                String filename = entry.getName();
                boolean ismkdir = false;
                if(filename.lastIndexOf("/") != -1){ //检查此文件是否带有文件夹
                    ismkdir = true;
                }
                filename = savepath + filename;

                if(entry.isDirectory()){ //如果是文件夹先创建
                    file = new File(filename);
                    file.mkdirs();
                    continue;
                }
                file = new File(filename);
                if(!file.exists()){ //如果是目录先创建
                    if(ismkdir){
                        new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); //目录先创建
                    }
                }
                file.createNewFile(); //创建文件

                is = zipFile.getInputStream(entry);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, buffer);

                while((count = is.read(buf)) > -1)
                {
                    bos.write(buf, 0, count);
                }
                bos.flush();
                bos.close();
                fos.close();

                is.close();
            }

            zipFile.close();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            try{
                if(bos != null){
                    bos.close();
                }
                if(fos != null) {
                    fos.close();
                }
                if(is != null){
                    is.close();
                }
                if(zipFile != null){
                    zipFile.close();
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


}
