package com.idc.cxf.isp.service.impl;

import com.idc.cxf.isp.service.ICommandService;
import constant.isp.ISPContant;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import utils.BasicDecodeUtils;
import utils.BasicEncodeUtils;
import utils.typeHelper.StringUtil;

import javax.jws.WebService;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by DELL on 2017/9/1.
 */
@WebService(endpointInterface= "com.idc.cxf.isp.service.ICommandService",
targetNamespace="http://impl.service.isp.cxf.idc.com/",serviceName = "IdcCommand")
public class CommandServiceImpl implements ICommandService {
    private static final Log log = LogFactory.getLog(CommandServiceImpl.class);
    public String checkPwdHash(String randVal, String pwdHash, String hashAlgorithm)
            throws UnsupportedEncodingException, NoSuchAlgorithmException
    {

        byte[] password_bytes = ISPContant.认证密钥.value().getBytes("utf8" );
        byte[] randVal_bytes = randVal.getBytes("UTF-8");
        byte[] result = new byte[password_bytes.length + randVal_bytes.length];
        System.arraycopy(password_bytes, 0, result, 0, password_bytes.length);
        System.arraycopy(randVal_bytes, 0, result, password_bytes.length, randVal_bytes.length);

        String dataHash = null;
        if ("0".equals(hashAlgorithm))
        {
            log.info(new StringBuilder().append("hash encoded dataHash result is ")
                    .append(Arrays.toString(result))
                    .toString());
            dataHash = new BASE64Encoder().encode(result);
            log.info(new StringBuilder().append("base64 encoded dataHash result is ").append(dataHash).toString());
            return dataHash;
        }
        if ("1".equals(hashAlgorithm)){
            dataHash = BasicEncodeUtils.MD5(result);
        }else if ("2".equals(hashAlgorithm))
        {
            dataHash = new ShaPasswordEncoder().encodePassword(new String(result, "UTF-8"), null);
        }else
        {
            throw new UnsupportedOperationException(new StringBuilder().append("invalid hashAlgorithm ").append(hashAlgorithm).toString());
        }

        log.info(new StringBuilder().append("hash encoded dataHash result is ").append(dataHash).toString());

        dataHash = new BASE64Encoder().encode(dataHash.getBytes("UTF-8"));
        log.info(new StringBuilder().append("base64 encoded dataHash result is ").append(dataHash).toString());

        return dataHash;
    }

    private byte[] assemableEncryptdData(String command, String encryptAlgorithm)
            throws Exception
    {
        byte[] data = new BASE64Decoder().decodeBuffer(command);
        log.info(new StringBuilder().append("start assemable encrypt data with encryptAlgorithm = ").append(encryptAlgorithm).toString());

        byte[] decrypted = null;
        if ("0".equals(encryptAlgorithm)) {
            decrypted = data;
            log.info("none encrypt used");
        } else if ("1".equals(encryptAlgorithm))
        {
            decrypted = BasicDecodeUtils.getInstance().decrypt(command);
            log.info(new StringBuilder().append("[only test] decrypted original data is ")
                    .append(Arrays.toString(decrypted))
                    .toString());
        } else {
            throw new IllegalArgumentException(new StringBuilder().append("invalid encryptAlgorithm ").append(encryptAlgorithm).toString());
        }

        return decrypted;
    }
    private String checkCommandHash(String authentication_key, byte[] encrypted, String hashAlgorithm)
            throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        String dataHash = null;
        if (!StringUtil.isEmpty(authentication_key)) {
            log.info(new StringBuilder().append("start assemable dataHash with authentication_key ").append(authentication_key).toString());

            byte[] key_bytes = authentication_key.getBytes("UTF-8");
            byte[] result = new byte[encrypted.length + key_bytes.length];
            System.arraycopy(encrypted, 0, result, 0, encrypted.length);
            System.arraycopy(key_bytes, 0, result, encrypted.length, key_bytes.length);

            log.info(new StringBuilder().append("hash raw key_bytes is ").append(Arrays.toString(key_bytes)).toString());
            log.info(new StringBuilder().append("hash raw data is ").append(Arrays.toString(result)).toString());

            if ("0".equals(hashAlgorithm))
            {
                log.info(new StringBuilder().append("hash encoded dataHash result is ")
                        .append(Arrays.toString(result))
                        .toString());
                dataHash = new BASE64Encoder().encode(result);
                log.info(new StringBuilder().append("base64 encoded dataHash result is ").append(dataHash).toString());
                return dataHash;
            }if ("1".equals(hashAlgorithm))
                dataHash = BasicEncodeUtils.MD5(result);
            else if ("2".equals(hashAlgorithm))
            {
                dataHash = new ShaPasswordEncoder()
                        .encodePassword(new String(result, "UTF-8"), null);
            }
            else {
                throw new UnsupportedOperationException(new StringBuilder().append("invalid hashAlgorithm ").append(hashAlgorithm).toString());
            }

            log.info(new StringBuilder().append("hash encoded dataHash result is ").append(dataHash).toString());
            dataHash = new BASE64Encoder().encode(dataHash.getBytes("UTF-8"));
            log.info(new StringBuilder().append("base64 encoded dataHash result is ").append(dataHash).toString());
        }
        return dataHash;
    }
    private byte[] assemableCompressedData(byte[] xml, String compressionFormat)
            throws IOException
    {
        log.info(new StringBuilder().append("start assemable compress data with compressionFormat = ").append(compressionFormat).toString());

        byte[] data = null;
        if ("0".equals(compressionFormat)) {
            log.info("none compress used");
        } else if ("1".equals(compressionFormat)) {
            ZipArchiveOutputStream out = null;
            Reader ir = null;
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            ZipArchiveInputStream is = null;
            try {
                is = new ZipArchiveInputStream(new BufferedInputStream(new ByteArrayInputStream(xml)));

                ZipArchiveEntry entry = null;
                if ((entry = is.getNextZipEntry()) != null) {
                    OutputStream os = null;
                    ByteArrayOutputStream to = new ByteArrayOutputStream();
                    try {
                        os = new BufferedOutputStream(to);
                        IOUtils.copy(is, os);
                    }
                    finally {
                    }
                    xml = to.toByteArray();
                    log.info(new StringBuilder().append("[only test]unzip test result is ")
                            .append(new String(to
                                    .toByteArray(), "UTF-8")).toString());
                }
            }
            finally {
                IOUtils.closeQuietly(is);
            }
        } else {
            throw new RuntimeException(new StringBuilder().append("invalid compressionFormat ").append(compressionFormat).toString());
        }

        log.info(new StringBuilder().append("compressed data result is : ").append(Arrays.toString(xml)).toString());

        return xml;
    }
    /**
     * 获取相应的配置信息
     */
    @Override
    public String idc_command(String idcId,
                              String randVal,
                              String pwdHash,
                              String command,
                              String commandHash,
                              int commandType,
                              Long commandSequence,
                              String encryptAlgorithm,
                              String hashAlgorithm,
                              String compressionFormat,
                              String commandVersion) {
        Response response = new Response();
        log.info(new StringBuilder().append(" idcId :").append(idcId).append("randVal :").append(randVal).append("pwdHash:").append(pwdHash).append("command:").append(command).append("commandHash:").append(commandHash).append("commandType:").append(commandType).append("commandSequence:").append(commandSequence).append("encryptAlgorithm:").append(encryptAlgorithm).append("hashAlgorithm:").append(hashAlgorithm).append("compressionFormat:").append(compressionFormat).append("commandVersion:").append(commandVersion).toString());
        try {
            String decode_pwdHash = checkPwdHash(randVal, pwdHash, hashAlgorithm);

            if (!decode_pwdHash.equals(pwdHash)) {
                response.setCode("900");
                response.setMessage(new StringBuilder().append("平台认证失败 decode_pwdHash:").append(decode_pwdHash).append("pwdHash:").append(pwdHash).toString());
                return response.toString();
            }


            byte[] encrypted = assemableEncryptdData(command, encryptAlgorithm);
            String authentication_key = ISPContant.认证密钥.value();

            String dataHash = checkCommandHash(authentication_key, encrypted, hashAlgorithm);

            if (!dataHash.equals(commandHash)) {
                response.setCode("2");
                response.setMessage(new StringBuilder().append("文件校验失败 dataHash:").append(dataHash).append("commandHash:").append(commandHash).toString());

                return response.toString();
            }

            byte[] data = assemableCompressedData(encrypted, compressionFormat);

            log.info(new String(data, "UTF-8"));

            String s = new String(data);

            return response.toString();


        } catch (IOException e) {
            response.setCode("3");
            response.setMessage(e.getMessage());
            return response.toString();
        } catch (NoSuchAlgorithmException e) {
            response.setCode("900");
            response.setMessage(e.getMessage());
            return response.toString();
        } catch (IllegalArgumentException e) {
            response.setCode("1");
            response.setMessage(e.getMessage());
            return response.toString();
        } catch (UnsupportedOperationException e) {
            response.setCode("900");
            response.setMessage(e.getMessage());
            return response.toString();
        } catch (RuntimeException e) {
            response.setCode("3");
            response.setMessage(e.getMessage());
            return response.toString();
        } catch (Exception e) {
            response.setCode("1");
            response.setMessage(e.getMessage());
        }return response.toString();
    }

    /**
     * 内部类
     */
    public class Response{
        String code;
        String message;

        public Response()
        {
        }

        public String getCode()
        {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String toString()
        {
            StringBuffer sb = new StringBuffer("<return><resultCode>");
            sb.append(StringUtil.isEmpty(getCode()) ? "" : getCode())
                    .append("</resultCode><msg>")
                    .append(StringUtil.isEmpty(getMessage()) ? "" :
                            getMessage()).append("</msg></return>");
            XmlUtil xmlUtil = new XmlUtil();
            return xmlUtil.String2Xml(sb.toString());
        }
    }
    class XmlUtil{
        public  String String2Xml(String xml)
        {
            Document document = null;
            try {
                document = DocumentHelper.parseText(xml);
                return asXml(document);
            } catch (DocumentException e) {
                e.printStackTrace();
            }return xml;
        }
        //转换为标准格式（避免自闭合的问题）
        private String asXml(Document document){
            OutputFormat format = new OutputFormat();
            format.setEncoding("UTF-8");
            format.setExpandEmptyElements(true);
            StringWriter out = new StringWriter();
            XMLWriter writer = new XMLWriter(out, format);
            try {
                writer.write(document);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return out.toString();
        }
        public XMLConfiguration getXmlConfiguration(String xml)
        {
            XMLConfiguration configuration = new XMLConfiguration();
            InputStream in = null;
            try {
                in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
                configuration.load(in);
                return configuration;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
