package utils.typeHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import utils.DevContext;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.SocketException;

/**
 * Created by DELL on 2017/9/26.
 * 目前这个类只支持本地业务附件数据跨主机上传信息。不支持其他。除非稍作改造
 */
public class FTPUtils {
    private static final Log log = LogFactory.getLog(FTPUtils.class);
    //获取字符编码
    private static String encoding = System.getProperty("file.encoding");
    private static String FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE = DevContext.FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE;
    private static String FTP_TEMPFIELPATH_ABSOLUTE = DevContext.FTP_TEMPFIELPATH_ABSOLUTE;
    private static String FTP_TEMPFIELPATH = DevContext.FTP_TEMPFIELPATH;

    private static FTPUtils ourInstance = new FTPUtils();
    /*用户名和密码等一些列的数据都作为属性显示*/
    private static FTPClient ftpClient = null;

    private  FTPClient ftp;  //wcg add

    public static FTPUtils getInstance() {
        /*if(ftpClient == null){*/
            log.debug("地址:"+DevContext.FTP_ADDR+";" +
                        "端口号:"+DevContext.FTP_PORT+";" +
                        "用户名:"+DevContext.FTP_USERNAME+";" +
                        "密码:"+DevContext.FTP_PASSWORD+";" +
                        "是否删除本地文件标志:"+DevContext.FTP_ISP_UPLOAD_SUCCESS_THEN_DELLOCALFILE);
            log.debug("----------------[初始化ftp的客户链接类 start]---------------------");
            try {
                ftpClient = new FTPClient();
                ftpClient.connect(DevContext.FTP_ADDR, DevContext.FTP_PORT);// 连接FTP服务器
                ftpClient.login(DevContext.FTP_USERNAME, DevContext.FTP_PASSWORD);// 登陆FTP服务器
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
    private FTPUtils() {
    }
    //接下来就进行处理
    /**
     * @param filePath 本地文件路径
     * @return
     * @throws Exception
     */
    public Boolean upload(String FTP_PATH,String filePath){
        boolean success = false;
        log.debug("本地文件路径["+filePath+"]");

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
            Boolean changeWorkingDirectory = createDirOverChangeWorkingDirectory(FTP_PATH);
            if(!changeWorkingDirectory){
                log.debug("没有切换到ftp的相应目录。。。。。。");
                return success;
            }

            // 先把文件写在本地。在上传到FTP上最后在删除
            File file = new File(filePath);
            //若不是文件夹
            if(!file.isDirectory()){
                log.debug("上传的是文件");
                //传递的是文件流。
                FileInputStream input = new FileInputStream(file);
                //上传，ftp必须是iso-8859-1编码
                success = ftpClient.storeFile(new String(file.getName().getBytes(encoding),"iso-8859-1"), input);
                /*如果上传成功，则删除本地文件*/
                System.out.println(success);
                if(FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE!= null && "true".equalsIgnoreCase(FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE) && success){
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
                    if(FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE != null && "true".equalsIgnoreCase(FTP_UPLOAD_SUCCESS_THEN_DELLOCALFILE) && success){
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
     * @param input:文件流
     * @param fileName:文件名称:[该名称可以任意。只要数据库中保存与其一一对应即可]
     * @return outFilePath:返回输出的文件路径。
     */
    public String wirteLocalFile(InputStream input,String fileName){
        String localFilePath = "";
        if("true".equalsIgnoreCase(FTP_TEMPFIELPATH_ABSOLUTE)){
            log.debug("本地临时文件保存[采用绝对路径]");
            localFilePath = FTP_TEMPFIELPATH;
        }else{
            log.debug("本地临时文件保存[采用相对路径]");
            localFilePath = this.getClass().getClassLoader().getResource("").getPath()+File.separator+"localFile";
        }
        log.debug("保存本地文件目录:["+FTP_TEMPFIELPATH+"]");
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
            log.debug("保存本地文件:[----------------失败--------------]");
            return null;
        }
        log.debug("保存本地文件:[----------------成功--------------]");
        return outFilePath;
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




    /*
    * wcg add FTP,上传文件
    * */
    public Boolean uploadFTPNew(String path,String filePath) throws Exception {
        /*下面的连接FTP服务器、关闭连接等可以抽取公共方法！！！
        * 后面看情况抽取
        * */
        boolean result=true;

        ftpClient = new FTPClient();
        int reply;
        // 连接FTP服务器
        ftpClient.connect(DevContext.FTP_ONLINE_ADDR,DevContext.FTP_ONLINE_PORT);
        // 登录
        ftpClient.login(DevContext.FTP_ONLINE_USERNAME,DevContext.FTP_ONLINE_PASSWORD);
        // 检验是否连接成功
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            System.out.println("连接失败");
        }
        log.debug("-------------------连接FTP服务器成功！----------------------");

        // 转移工作目录至指定目录下
        ftpClient.changeWorkingDirectory("");
        //设置为二进制
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        File file = new File(filePath);
        //若不是文件夹
        if(!file.isDirectory()){
            FileInputStream input = new FileInputStream(file);
            /*
            * 说明：FTPClient.enterLocalPassiveMode();这个方法的意思就是每次数据连接之前，ftp client告诉ftp server开通一个端口
            * 来传输数据。为什么要这样做呢，因为ftp server可能每次开启不同的端口来传输数据，但是在linux上或者其他服务器上面，由于安全
            * 限制，可能某些端口没有开启，所以就出现阻塞。 */
            ftpClient.enterLocalPassiveMode();
            //上传，ftp必须是iso-8859-1编码
            /*
            * ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            * */
            boolean flag = ftpClient.storeFile(new String(file.getName().getBytes(encoding),"iso-8859-1"), input);
            if(!flag){
                log.debug("-------------------附件上传失败！----------------------");
                result=false;
            }else{
                log.debug("-------------------附件上传成功！----------------------");
            }
            file.delete();
            input.close();
        }else{
            //System.out.println("文件夹");
            String [] files = file.list();
            for(int i = 0;i<files.length;i++){
                File tpFile = new File(file.getAbsolutePath()+File.separator+files[i]);
                FileInputStream input = new FileInputStream(tpFile);
                boolean flag = ftpClient.storeFile(new String(tpFile.getName().getBytes(encoding),"iso-8859-1"), input);
                System.out.println(flag);

                input.close();
            }
            ftpClient.logout();
        }
        if(ftpClient.isConnected()){
            ftpClient.disconnect();
        }

        return result;
    }

    /*
    * wcg add FTP，删除文件
    * */
    public boolean deleteFileNew(String pathname, String filename){
        /*下面的连接FTP服务器、关闭连接等可以抽取公共方法！！！
        * 后面看情况抽取
        * */

        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            //连接FTP服务器
            ftpClient.connect(DevContext.FTP_ONLINE_ADDR,DevContext.FTP_ONLINE_PORT);
            //登录FTP服务器
            ftpClient.login(DevContext.FTP_ONLINE_USERNAME,DevContext.FTP_ONLINE_PASSWORD);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                return flag;
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                }
            }
        }
        return flag;
    }

    /**
     ** wcg add FTP，下载文件
     * @param pathname FTP服务器文件目录
     * @param filename 文件名称
     * @param localpath 下载后的文件路径
     */
    public boolean downloadFileNew(HttpServletResponse response, String pathname, String filename, String localpath, String fileRealName){
        /*下面的连接FTP服务器、关闭连接等可以抽取公共方法！！！
        * 后面看情况抽取
        * */

        //fileRealName说明：它是文件真正的名字
        //filename说明：它是文件保存在FTP服务器上的名字，不是真名字
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            //连接FTP服务器
            ftpClient.connect(DevContext.FTP_ONLINE_ADDR,DevContext.FTP_ONLINE_PORT);
            //登录FTP服务器
            ftpClient.login(DevContext.FTP_ONLINE_USERNAME,DevContext.FTP_ONLINE_PASSWORD);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                return flag;
            }
            //切换FTP目录
            boolean b = ftpClient.changeWorkingDirectory(new String(pathname.getBytes(),"ISO-8859-1"));
            System.out.println(b);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                String fName = new String(file.getName().getBytes("iso-8859-1"), "UTF-8");
                System.out.println(fName);
                if(filename.equalsIgnoreCase(fName)){

                    File xxxxx = null;//如果文件夹不存在，就创建文件夹
                    xxxxx = new File("");
                    if (!xxxxx.exists()) {
                        xxxxx.mkdirs();
                    }
                    File localFile = new File(localpath + "/" + fileRealName);
                    //localFile.setn
                    OutputStream os = new FileOutputStream(localFile);
                    ftpClient.changeWorkingDirectory("/");
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            log.debug("下载附件成功！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                }
            }
        }
        return flag;
    }

    /**
     ** wcg add FTP，下载文件
     * @param pathname FTP服务器文件目录
     * @param filenameWWW 文件在ftp服务器上的真实名字
     * @param fileName 文件在本地的真实名字
     */
    public Boolean downloadFileNewXXX(HttpServletResponse response, String pathname, String filenameWWW, String ftpPath, String fileName){
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        OutputStream out = null;
        InputStream in = null;
        try {
            /*下面的连接FTP服务器、关闭连接等可以抽取公共方法！！！
            * 后面看情况抽取
            * */
            //连接FTP服务器
            ftpClient.connect(DevContext.FTP_ONLINE_ADDR,DevContext.FTP_ONLINE_PORT);
            //登录FTP服务器
            ftpClient.login(DevContext.FTP_ONLINE_USERNAME,DevContext.FTP_ONLINE_PASSWORD);
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                return flag;
            }
            //切换FTP目录
            boolean changeFile = ftpClient.changeWorkingDirectory(DevContext.FTP_PATH_NAME);
            if(changeFile){
                System.out.println("切换FTP目录成功！！！！");
            }
            ftpClient.enterLocalPassiveMode();   //设置PassiveMode传输
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);   //不能设置BINARY_FILE_TYPE

            //切换工作目录到文件所在的目录
            Boolean changeWorkingDirectory = filedirOverChangeWorkingDirectory("/");
            Integer count=1;   //用于计算是否在数据库中查询到此附件
            if(changeWorkingDirectory){
                FTPFile[] fs = ftpClient.listFiles();
                for(FTPFile ff:fs){
                    if(ff.getName().equals(filenameWWW)){
                        count++;
                        //File localFile = new File(DevContext.FTP_DOWNLOAD_PATH_NAME+ff.getName());
                        File localFile = new File(ff.getName());
                        out= new FileOutputStream(localFile);
                        in = new FileInputStream(localFile);
                        ftpClient.retrieveFile(ff.getName(), out);
                    }
                }
            }
            if(count == 1){
                //如果数据库中没有此文件,就报错提示数据库中没有此附件
                try {
                    PrintWriter outXXXX = response.getWriter();
                    response.setContentType("text/html; charset=utf-8");
                    outXXXX.print("<script>" +
                            "top.layer.msg('没有此附件', {\n" +
                            "                icon: 2,\n" +
                            "                time: 1500\n" +
                            "          });</script>");
                    outXXXX.flush();
                    outXXXX.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                log.debug("没有此文件");
                return false;
            }

            //把文件通过弹出让用户选择下载路径的方式下载
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ));
            BufferedInputStream br = new BufferedInputStream(in);
            byte[] buf = new byte[1024];
            int len = 0;
            out = response.getOutputStream();
            while ((len = br.read(buf)) > 0)
                    out.write(buf, 0, len);
                    out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(ftpClient != null){
                    try {
                        ftpClient.logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        return true;
    }
}
