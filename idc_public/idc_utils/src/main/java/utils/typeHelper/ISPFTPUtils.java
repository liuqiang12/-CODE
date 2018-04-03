package utils.typeHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import utils.DevContext;

import java.io.*;
import java.net.SocketException;

/**
 * Created by DELL on 2017/9/26.
 * 目前这个类只支持本地业务附件数据跨主机上传信息。不支持其他。除非稍作改造
 */
public class ISPFTPUtils {
    private static final Log log = LogFactory.getLog(ISPFTPUtils.class);
    //获取字符编码
    private static String encoding = System.getProperty("file.encoding");
    private static String FTP_ISP_IP = DevContext.FTP_ISP_IP;
    private static String FTP_ISP_PORT = DevContext.FTP_ISP_PORT;
    private static String FTP_ISP_USERNAME = DevContext.FTP_ISP_USERNAME;
    private static String FTP_ISP_PWD = DevContext.FTP_ISP_PWD;
    private static String FTP_ISP_REMOTE_PATH = DevContext.FTP_ISP_REMOTE_PATH;
    private static String FTP_ISP_TEMPLATE_FILE = DevContext.FTP_ISP_TEMPLATE_FILE;
    private static String FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE = DevContext.FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE;

    private static ISPFTPUtils ourInstance = new ISPFTPUtils();
    /*用户名和密码等一些列的数据都作为属性显示*/
    private static FTPClient ftpClient = null;

    public static ISPFTPUtils getInstance() {
        /*if(ftpClient == null){*/
            log.debug("地址:"+FTP_ISP_IP+";" +
                        "端口号:"+FTP_ISP_PORT+";" +
                        "用户名:"+FTP_ISP_USERNAME+";" +
                        "密码:"+FTP_ISP_PWD+";" +
                        "是否删除本地文件标志:"+FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE);
            log.debug("----------------[初始化ftp的客户链接类 start]---------------------");
            try {
                ftpClient = new FTPClient();
                ftpClient.connect(FTP_ISP_IP, Integer.valueOf(String.valueOf(FTP_ISP_PORT)));// 连接FTP服务器
                ftpClient.login(FTP_ISP_USERNAME, FTP_ISP_PWD);// 登陆FTP服务器
                if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                    log.info("未连接到FTP，用户名或密码错误。");
                    ftpClient.disconnect();
                } else {
                    log.info("FTP连接成功。");
                }
            } catch (SocketException e) {
                e.printStackTrace();
                log.info("FTP的IP地址可能错误，请正确配置。");
            } catch (IOException e) {
                e.printStackTrace();
                log.info("FTP的端口错误,请正确配置。");
            }
            log.debug("----------------[初始化ftp的客户链接类 end]---------------------");
        /*}*/
        return ourInstance;
    }
    private ISPFTPUtils() {
    }
    //接下来就进行处理
    /**
     * @param filePath 本地文件路径
     * @param targetFile 文件名称
     * @return
     * @throws Exception
     */
    public Boolean upload(String FTP_PATH,String filePath,String targetFile){
        boolean success = false;
        log.debug("本地文件路径["+filePath+File.separator+targetFile+"]");

        // 连接FTP服务器
        try {
            // 设置PassiveMode传输
            try{
                ftpClient.enterLocalPassiveMode();
            }catch (Exception e){
                log.error("ftpClient.enterLocalPassiveMode();不能设置PassiveMode传输");
                // e.printStackTrace();
            }
            //设置为二进制
            try{
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            }catch (Exception e){
                log.error("ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);不能设置BINARY_FILE_TYPE");
                // e.printStackTrace();
            }

            log.debug("转移工作目录至指定目录下");
            //程序创建文件信息
            log.debug("！！！！！！！！判断指定的文件目录是否存在！！！！！！！！");
            //今天的日期

            Boolean changeWorkingDirectory = createDirOverChangeWorkingDirectory(FTP_PATH+File.separator+DateHelper.getNowFormateDate());
            if(!changeWorkingDirectory){
                log.debug("没有切换到ftp的相应目录。。。。。。");
                return success;
            }

            // 先把文件写在本地。在上传到FTP上最后在删除
            File file = new File(filePath+File.separator+targetFile);
            //若不是文件夹
            if(!file.isDirectory()){
                log.debug("上传的是文件");
                //传递的是文件流。
                FileInputStream input = new FileInputStream(file);
                //上传，ftp必须是iso-8859-1编码
                success = ftpClient.storeFile(new String(file.getName().getBytes(encoding),"iso-8859-1"), input);
                /*如果上传成功，则删除本地文件*/
                System.out.println(success);
                if(FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE!= null && "true".equalsIgnoreCase(FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE) && success){
                    file.delete();
                }
                input.close();
            }else{
                log.debug("上传的是文件夹");
                String [] files = file.list();
                for(int i = 0;i<files.length;i++){
                    File tpFile = new File(file.getAbsolutePath()+File.separator+files[i]);
                    FileInputStream input = new FileInputStream(tpFile);
                    success = ftpClient.storeFile(new String(tpFile.getName().getBytes(encoding),"iso-8859-1"), input);
                    if(!success){
                        log.debug("批量上传时，出现个别的上传失败!上传失败------后期可以采用再次上传【"+tpFile+"】");
                    }
                    if(FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE != null && "true".equalsIgnoreCase(FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE) && success){
                        tpFile.delete();
                    }
                    input.close();
                }
                ftpClient.logout();
            }
            if(ftpClient.isConnected()){
                ftpClient.disconnect();
            }
        }catch (SocketException e) {
            e.printStackTrace();
            log.error("FTP的IP地址可能错误，请正确配置，请用getsebool -a|grep ftp 查看是否allow_ftpd_full_access创建文件的权限关闭了" +
                    "；" +
                    "请用【 setsebool allow_ftpd_full_access=1  】开启");
        }catch (IOException e) {
            e.printStackTrace();
            log.error("FTP的端口错误,请正确配置。");
        }
        return success;
    }

    public static void main(String[] args) {
        System.out.println(DateHelper.getNowFDate());
    }
    public boolean filedirOverChangeWorkingDirectory(String FTP_PATH){
        if(StringHelper.isNullOrEmpty(FTP_PATH))
            return true;
        String d;
        try {
            //目录编码，解决中文路径问题
            d = new String(FTP_PATH.toString().getBytes("GBK"),"iso-8859-1");
            //尝试切入目录
            if(ftpClient.changeWorkingDirectory(d)){
                log.debug("已经切换到了相应的目录》》》》》》》》》");
                return true;
            }
            FTP_PATH = StringHelper.trimStart(FTP_PATH, "/");
            FTP_PATH = StringHelper.trimEnd(FTP_PATH, "/");
            String[] arr =  FTP_PATH.split("/");
            StringBuffer sbfDir=new StringBuffer();
            //循环生成子目录
            for(String s : arr){
                if(s != null && !"".equalsIgnoreCase(s)){
                    sbfDir.append("/");
                    sbfDir.append(s);
                    //目录编码，解决中文路径问题
                    d = new String(sbfDir.toString().getBytes("GBK"),"iso-8859-1");
                    //尝试切入目录
                    if(ftpClient.changeWorkingDirectory(d))
                        continue;
                    System.out.println("[成功]创建ftp目录："+sbfDir.toString());
                }
            }
            //将目录切换至指定路径
            return ftpClient.changeWorkingDirectory(d);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 判断Ftp目录是否存在 */
    /**
     * 创建目录(有则切换目录，没有则创建目录)
     * @return
     */
    public boolean createDirOverChangeWorkingDirectory(String FTP_PATH){
        if(StringHelper.isNullOrEmpty(FTP_PATH))
            return true;
        String d;
        try {
            //目录编码，解决中文路径问题
            d = new String(FTP_PATH.toString().getBytes("GBK"),"iso-8859-1");
            //尝试切入目录
            if(ftpClient.changeWorkingDirectory(d)){
                log.debug("已经切换到了相应的目录》》》》》》》》》");
                return true;
            }
            FTP_PATH = StringHelper.trimStart(FTP_PATH, "/");
            FTP_PATH = StringHelper.trimEnd(FTP_PATH, "/");
            String[] arr =  FTP_PATH.split("/");
            StringBuffer sbfDir=new StringBuffer();
            //循环生成子目录
            for(String s : arr){
                if(s != null && !"".equalsIgnoreCase(s)){
                    sbfDir.append("/");
                    sbfDir.append(s);
                    //目录编码，解决中文路径问题
                    d = new String(sbfDir.toString().getBytes("GBK"),"iso-8859-1");
                    //尝试切入目录
                    if(ftpClient.changeWorkingDirectory(d))
                        continue;
                    if(!ftpClient.makeDirectory(d)){
                        System.out.println("[失败]ftp创建目录："+sbfDir.toString());
                        return false;
                    }
                    System.out.println("[成功]创建ftp目录："+sbfDir.toString());
                }
            }
            //将目录切换至指定路径
            return ftpClient.changeWorkingDirectory(d);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param ftpPath 相对路径  小写
     * @param fileName 文件名称
     * @return
     */
    public InputStream downLoadFile(String ftpPath,String fileName) {
        InputStream in = null;
        log.info("开始读取相对路径路径" + ftpPath + "文件!");
        try {

            try{
                ftpClient.enterLocalPassiveMode();
            }catch (Exception e){
                log.error("ftpClient.enterLocalPassiveMode();不能设置PassiveMode传输");
            }

            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            try{
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            }catch (Exception e){
                log.error("ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);不能设置BINARY_FILE_TYPE");
            }

            //切换到相应文件
            Boolean changeWorkingDirectory = filedirOverChangeWorkingDirectory(ftpPath);
            if(changeWorkingDirectory){
                in = ftpClient.retrieveFileStream(fileName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("请联系管理员.没有找到" + ftpPath+File.separator+fileName+"文件");
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
            log.error("连接FTP失败.");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("请联系管理员.文件读取错误");
            return null;
        }
        return in;
    }

    /**
     * 获取FTPClient对象
     * @param ftpHost FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort FTP端口 默认为21
     * @return
     */
    @Deprecated
    public FTPClient getFTPClient(String ftpHost, String ftpPassword,
                                         String ftpUserName, int ftpPort) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            log.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }
    /**
     * 把配置文件先写到本地的一个文件中取
     **/
    public boolean write(String fileName, String fileContext,
                         String writeTempFielPath) {
        try {
            log.info("开始写配置文件");
            File f = new File(writeTempFielPath + "/" + fileName);
            if(!f.exists()){
                if(!f.createNewFile()){
                    log.info("文件不存在，创建失败!");
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            bw.write(fileContext.replaceAll("\n", "\r\n"));
            bw.flush();
            bw.close();
            return true;
        } catch (Exception e) {
            log.error("写文件没有成功");
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除文件  deleteFile
     */
    /**
     * 删除文件-FTP方式
     * @param FTP_PATH FTP相对目录下的文件
     * @param fileName FTP服务器上要删除的文件名
     * @return
     */
    public boolean deleteFile(String FTP_PATH,String fileName) {
        boolean success = false;
        try {
            Boolean changeWorkingDirectory = filedirOverChangeWorkingDirectory(FTP_PATH);
            if(changeWorkingDirectory){
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
                ftpClient.deleteFile(fileName);
                ftpClient.logout();
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
